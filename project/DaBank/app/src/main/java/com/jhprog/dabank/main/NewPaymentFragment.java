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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewPaymentFragment extends Fragment {

    private FragmentNewPaymentBinding binding;
    private ArrayAdapter<Account> payerAdapter;
    private ArrayAdapter<String> recurrenceAdapter;
    private ArrayList<Account> accounts;
    private int recurrence;
    private Account selectedAccount;
    private MainViewModel viewModel;
    private ArrayList<String> recurrenceTypes;

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
        binding.fragmentNewPaymentCalendarviewDueDate.setMinDate(
                Calendar.getInstance().getTime().getTime()
        );
    }

    private void initButtons() {
        binding.fragmentNewPaymentButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentNewPaymentButtonContinue.startAnimation(AnimationProvider.getOnClickAnimation());



                if (!validateFormData()) {
                    Toast.makeText(getActivity(), "Form data invalid!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO Handle transaction now OR insert a payment
                Transaction tr = fromFormData();


                //dataManager.insertTransaction(transaction);
            }
        });
    }

    private Transaction fromFormData() {
        Transaction transaction;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String due_date = simpleDateFormat.format(new Date(binding.fragmentNewPaymentCalendarviewDueDate.getDate()));
        String today = simpleDateFormat.format(Calendar.getInstance().getTime());

        Account receivingAccount = DataManager.getInstance().getAccountByAccountNumber(
                binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim());
        double amount =
                Double.parseDouble(binding.fragmentNewPaymentEdittextAmount.getText().toString().trim());

        if (binding.fragmentNewPaymentCalendarviewDueDate.getDate() > Calendar.getInstance().getTimeInMillis()) {
            // Due date in the future
            transaction = new PendingTransaction(
                Transaction.TYPE_PAYMENT,
                selectedAccount.getAcc_id(),
                receivingAccount.getAcc_id(),
                amount,
                recurrence,
                PendingTransaction.NEVER_PAID,
                due_date
            );
        } else {
            // Due date now
            transaction = new NormalTransaction(
                    Transaction.TYPE_PAYMENT,
                    selectedAccount.getAcc_id(),
                    receivingAccount.getAcc_id(),
                    amount,
                    today
            );
        }
        return transaction;
    }


    private boolean validateFormData() {
        String tempString = "";
        boolean valid = true;
        // Payee's account number check
        tempString = binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim().toUpperCase();
        if (tempString.length() != 18 || !tempString.matches("^[A-Z]{2}[0-9]{16}$")) {
            Toast.makeText(getActivity(), "Payee acc non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            // TODO motherfucker what, check if account exists and possibly put da mahafaka into a attribbi
        }
        // Payee's name check
        tempString = binding.fragmentNewPaymentEdittextPayeeName.getText().toString().trim().replaceAll("\\s+", "");
        if (tempString.isEmpty() || tempString.matches("[0-9]")) {
            Toast.makeText(getActivity(), "Payee name non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        // Reference number check
        tempString = binding.fragmentNewPaymentEdittextReference.getText().toString().trim();
        if ((tempString.isEmpty() && binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty())
                || tempString.matches("[A-Za-z]")
                || (!tempString.isEmpty() && !binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty()))
        {
            Toast.makeText(getActivity(), "Ref non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        // Money amount check
        tempString = binding.fragmentNewPaymentEdittextAmount.getText().toString().trim(); // TODO check this regex
        if (tempString.isEmpty() || !tempString.matches("^[0-9]{1,12}$|\\.[0-9]{1,2}$") || tempString.matches("[A-Z]|[a-z]")) {
            Toast.makeText(getActivity(), "Amount non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            try {
                Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                Toast.makeText(getActivity(), "Amount non-valid", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if (binding.fragmentNewPaymentSpinnerPayer.getSelectedItem() == null) {
            valid = false;
        }
        // Check if payer account has been selected from the spinner
        if (selectedAccount == null) {
            valid = false;
            Toast.makeText(getActivity(), "Please select an account", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


}
