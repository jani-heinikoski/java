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

public class NewPaymentFragment extends Fragment {

    private TimeManager timeManager = TimeManager.getInstance();

    private FragmentNewPaymentBinding binding;
    private ArrayAdapter<Account> payerAdapter;
    private ArrayAdapter<String> recurrenceAdapter;
    private ArrayList<Account> accounts;
    private MainViewModel viewModel;

    private final String[] recurrenceTypes = {"None", "Weekly", "Monthly"};

    /* FORM DATA */
    private String payeeAccountNumber, payeeName, message, dueDate;
    private int referenceNumber, recurrence;
    private double amount;
    private Account payerAccount;

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
        accounts = viewModel.getAccounts(); // MainViewModel loads accounts if needed.
        // ArrayAdapter for the payer account spinner.
        payerAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                accounts);
        payerAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentNewPaymentSpinnerPayer.setAdapter(payerAdapter);
        // ArrayAdapter for recurrence spinner
        recurrenceAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                recurrenceTypes);
        recurrenceAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentNewPaymentSpinnerRecurrence.setAdapter(recurrenceAdapter);
        // Listener for the payer account spinner
        binding.fragmentNewPaymentSpinnerPayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payerAccount = payerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                payerAccount = null;
            }
        });
        // Listener for the recurrence spinner
        binding.fragmentNewPaymentSpinnerRecurrence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = recurrenceAdapter.getItem(position);
                if (s != null && !s.equals("") && !s.equals("None")) {
                    switch (s) {
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
        // Set minimum date to today (dueDate can't be in the past)
        binding.fragmentNewPaymentCalendarviewDueDate.setMinDate(
                timeManager.todayDate().getTime()
        );
        // Listener for the dueDate CalendarView
        binding.fragmentNewPaymentCalendarviewDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dueDate = String.format("%d-%02d-%d", year, (month + 1), dayOfMonth);
            }
        });

        dueDate = timeManager.todayString(); // TODO: 29.4.2020 check if this is even needed
    }

    private void initButtons() {
        binding.fragmentNewPaymentButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentNewPaymentButtonContinue.startAnimation(AnimationProvider.getOnClickAnimation());
                // Send a transaction from the form data to the bank to handle
                if (sendTransaction()) {
                    clearFormData();
                    Toast toast = Toast.makeText(getActivity(),"Payment succeeded!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(),"Payment failed!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                payerAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean sendTransaction() {
        // Check if the form data is valid
        if (!validateFormData()) {
            Toast toast = Toast.makeText(getActivity(),"Form data invalid!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        // Variable declarations
        Transaction transaction;
        String today = timeManager.todayString();
        int compareDueDateAndToday;
        // Compare the selected due date to today
        try {
            compareDueDateAndToday = timeManager.compareDates(dueDate, today);
        } catch (ParseException ex) {
            return false;
        }
        // Due date in the future (after today)
        if (compareDueDateAndToday == TimeManager.AFTER) {
            transaction = new PendingTransaction(
                Transaction.TYPE_PAYMENT,
                referenceNumber,
                payerAccount.getAcc_number(),
                payeeAccountNumber,
                payeeName,
                message,
                viewModel.getBank().getBank_bic(),
                amount,
                recurrence,
                PendingTransaction.NEVER_PAID,
                dueDate
            );
        }
        // Due date now or before
        else {
            // If transaction recurs
            if (recurrence != PendingTransaction.RECURRENCE_NONE) {
                transaction = new PendingTransaction(
                        Transaction.TYPE_PAYMENT,
                        referenceNumber,
                        payerAccount.getAcc_number(),
                        payeeAccountNumber,
                        payeeName,
                        message,
                        viewModel.getBank().getBank_bic(),
                        amount,
                        recurrence,
                        PendingTransaction.NEVER_PAID,
                        today
                );
            } else {
                transaction = new NormalTransaction(
                        Transaction.TYPE_PAYMENT,
                        referenceNumber,
                        payerAccount.getAcc_number(),
                        payeeAccountNumber,
                        payeeName,
                        message,
                        viewModel.getBank().getBank_bic(),
                        amount,
                        today
                );
            }
        }
        // ReceivingAccount can be null
        return viewModel.getBank().handleTransaction(transaction, payerAccount, DataManager.getInstance().getAccountByAccountNumber(
                binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim()
        ));
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
        payeeAccountNumber = binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim().toUpperCase();
        if (payeeAccountNumber.length() != 18 || !payeeAccountNumber.matches("^[A-Z]{2}[0-9]{16}$")) {
            binding.fragmentNewPaymentEdittextPayeeAccount.setError("Non-valid");
            valid = false;
        }
        // Payee's name check
        payeeName = binding.fragmentNewPaymentEdittextPayeeName.getText().toString().trim();
        if (payeeName.isEmpty() || payeeName.matches("[0-9]")) {
            binding.fragmentNewPaymentEdittextPayeeName.setError("Non-valid");
            valid = false;
        }
        // Reference number check
        referenceNumber = Transaction.REF_NUM_NULL;
        tempString = binding.fragmentNewPaymentEdittextReference.getText().toString().trim();
        if ((tempString.isEmpty() && binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty())
                || tempString.matches("[A-Za-z]")
                || (!tempString.isEmpty() && !binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty()))
        {
            binding.fragmentNewPaymentEdittextReference.setError("Non-valid");
            valid = false;
        } else {
            if (!tempString.isEmpty()) {
                try {
                    referenceNumber = Integer.parseInt(tempString);
                } catch (Exception e) {
                    binding.fragmentNewPaymentEdittextReference.setError("Must be an integer");
                    referenceNumber = Transaction.REF_NUM_NULL;
                    valid = false;
                }
            }
        }
        // Money amount check
        tempString = binding.fragmentNewPaymentEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentNewPaymentEdittextAmount.setError("Non-valid");
            valid = false;
        } else {
            try {
                amount = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentNewPaymentEdittextAmount.setError("Non-valid");
            }
        }
        // Message check
        message = binding.fragmentNewPaymentEdittextMessage.getText().toString().trim();
        if (message.matches("['\";]")) {
            valid = false;
            message = Transaction.MESSAGE_NULL;
            binding.fragmentNewPaymentEdittextMessage.setError("Can't contain ' \" ;");
        } else if (message.isEmpty()) {
            message = Transaction.MESSAGE_NULL;
        }
        // Check if payer account has been selected from the spinner
        if (payerAccount == null) {
            valid = false;
            Toast.makeText(getActivity(), "Please select an account", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


}
