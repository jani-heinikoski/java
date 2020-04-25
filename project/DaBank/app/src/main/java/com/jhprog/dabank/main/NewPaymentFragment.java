/*
 * Author: Jani Olavi Heinikoski
 * Date: 09.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.NormalTransaction;
import com.jhprog.dabank.data.PendingTransaction;
import com.jhprog.dabank.data.Transaction;
import com.jhprog.dabank.databinding.FragmentNewPaymentBinding;
import com.jhprog.dabank.utility.AnimationProvider;
import com.jhprog.dabank.utility.TimeManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class NewPaymentFragment extends Fragment { // TODO might want to organize this clusterfuck

    private FragmentNewPaymentBinding binding;
    private ArrayAdapter<Account> payerAdapter;
    private ArrayAdapter<String> recurrenceAdapter;
    private ArrayList<Account> accounts;
    private int recurrence;
    private Account selectedAccount;
    private MainViewModel viewModel;
    private ArrayList<String> recurrenceTypes;
    private String dueDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recurrenceTypes = new ArrayList<>();
        recurrenceTypes.add("None");
        recurrenceTypes.add("Daily");
        recurrenceTypes.add("Weekly");
        recurrenceTypes.add("Monthly");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewPaymentBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initSpinners();
        initCalendars();
    }

    private void initSpinners() {
        accounts = viewModel.getAccounts();
        payerAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                accounts);
        payerAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentNewPaymentSpinnerPayer.setAdapter(payerAdapter);

        recurrenceAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                recurrenceTypes);
        recurrenceAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentNewPaymentSpinnerRecurrence.setAdapter(recurrenceAdapter);

        binding.fragmentNewPaymentSpinnerPayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAccount = payerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAccount = null;
            }
        });

        binding.fragmentNewPaymentSpinnerRecurrence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = recurrenceAdapter.getItem(position);
                if (s != null && !s.equals("") && !s.equals("None")) {
                    switch (s) {
                        case "Daily":
                            recurrence = PendingTransaction.RECURRENCE_DAILY;
                            break;
                        case "Weekly":
                            recurrence = PendingTransaction.RECURRENCE_WEEKLY;
                            break;
                        case "Monthly":
                            recurrence = PendingTransaction.RECURRENCE_MONTHLY;
                            break;
                        default:
                            recurrence = PendingTransaction.RECURRENCE_NONE;
                            break;
                    }
                } else {
                    recurrence = PendingTransaction.RECURRENCE_NONE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                recurrence = PendingTransaction.RECURRENCE_NONE;
            }
        });
    }

    private void initCalendars() {
        TimeManager timeManager = TimeManager.getInstance();

        binding.fragmentNewPaymentCalendarviewDueDate.setMinDate(
                timeManager.todayDate().getTime()
        );

        binding.fragmentNewPaymentCalendarviewDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dueDate = String.format("%d-%02d-%d", year, (month + 1), dayOfMonth);
            }
        });

        dueDate = timeManager.todayString();
    }

    private void initButtons() {
        binding.fragmentNewPaymentButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentNewPaymentButtonContinue.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    Toast toast = Toast.makeText(getActivity(),"Form data invalid!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                if (!sendTransaction()) {
                    Toast toast = Toast.makeText(getActivity(),"Payment failed!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    clearFormData();
                    payerAdapter.notifyDataSetChanged();
                    Toast toast = Toast.makeText(getActivity(),"Payment succeeded!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private boolean sendTransaction() {
        Transaction transaction;
        TimeManager timeManager = TimeManager.getInstance();
        String today = timeManager.todayString();
        Account receivingAccount = DataManager.getInstance().getAccountByAccountNumber(
                binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim()
        );

        String receivingAccountNumber = binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim();

        double amount = Double.parseDouble(binding.fragmentNewPaymentEdittextAmount.getText().toString().trim());

        int compareDueDateAndToday;

        try {
            compareDueDateAndToday = timeManager.compareDates(dueDate, today);
        } catch (ParseException ex) {
            return false;
        }

        if (compareDueDateAndToday == TimeManager.AFTER) {
            // Due date in the future (after today)
            transaction = new PendingTransaction(
                Transaction.TYPE_PAYMENT,
                selectedAccount.getAcc_number(),
                receivingAccountNumber,
                amount,
                recurrence,
                PendingTransaction.NEVER_PAID,
                dueDate
            );
        } else {
            // Due date now or before
            if (recurrence != PendingTransaction.RECURRENCE_NONE) {
                transaction = new PendingTransaction(
                        Transaction.TYPE_PAYMENT,
                        selectedAccount.getAcc_number(),
                        receivingAccountNumber,
                        amount,
                        recurrence,
                        PendingTransaction.NEVER_PAID,
                        today
                );
            } else {
                transaction = new NormalTransaction(
                        Transaction.TYPE_PAYMENT,
                        selectedAccount.getAcc_number(),
                        receivingAccountNumber,
                        amount,
                        today
                );
            }
        }

        return viewModel.getBank().handleTransaction(transaction, selectedAccount, receivingAccount);
    }

    private void clearFormData() {
        binding.fragmentNewPaymentEdittextPayeeAccount.setText("");
        binding.fragmentNewPaymentEdittextPayeeName.setText("");
        binding.fragmentNewPaymentEdittextReference.setText("");
        binding.fragmentNewPaymentEdittextAmount.setText("");
    }


    private boolean validateFormData() { // Validate all form fields
        String tempString = "";
        boolean valid = true;
        // Payee's account number check
        tempString = binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim().toUpperCase();
        if (tempString.length() != 18 || !tempString.matches("^[A-Z]{2}[0-9]{16}$")) {
            binding.fragmentNewPaymentEdittextPayeeAccount.setError("Non-valid");
            valid = false;
        }
        // Payee's name check
        tempString = binding.fragmentNewPaymentEdittextPayeeName.getText().toString().trim().replaceAll("\\s+", "");
        if (tempString.isEmpty() || tempString.matches("[0-9]")) {
            binding.fragmentNewPaymentEdittextPayeeName.setError("Non-valid");
            valid = false;
        }
        // Reference number check
        tempString = binding.fragmentNewPaymentEdittextReference.getText().toString().trim();
        if ((tempString.isEmpty() && binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty())
                || tempString.matches("[A-Za-z]")
                || (!tempString.isEmpty() && !binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty()))
        {
            binding.fragmentNewPaymentEdittextReference.setError("Non-valid");
            valid = false;
        }
        // Money amount check
        tempString = binding.fragmentNewPaymentEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentNewPaymentEdittextAmount.setError("Non-valid");
            valid = false;
        } else {
            try {
                Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentNewPaymentEdittextAmount.setError("Non-valid");
                e.printStackTrace();
            }
        }
        // Check if payer account has been selected from the spinner
        if (selectedAccount == null) {
            valid = false;
            Toast.makeText(getActivity(), "Please select an account", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


}
