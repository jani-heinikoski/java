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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.CurrentAccount;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.Transaction;
import com.jhprog.dabank.databinding.FragmentNewPaymentBinding;
import com.jhprog.dabank.utility.AnimationProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class NewPaymentFragment extends Fragment {

    private FragmentNewPaymentBinding binding;
    private ArrayAdapter<Account> payerAdapter;
    private ArrayList<Account> accounts;
    private MainViewModel viewModel;

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
        viewModel.getAccounts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Account>>() {
            @Override
            public void onChanged(ArrayList<Account> accounts) {
                payerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        // TODO tie db conn to the Activity's lifecycle and remove this
        //dataManager.closeDatabaseConnection();
        super.onDestroy();
    }

    private void initSpinners() {
        accounts = viewModel.getAccounts().getValue();
        payerAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                accounts);
        payerAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentNewPaymentSpinnerPayer.setAdapter(payerAdapter);
    }

    private void initButtons() {
        binding.fragmentNewPaymentButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                binding.fragmentNewPaymentButtonContinue.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    return;
                }

                Transaction transaction = new Transaction(
                    Transaction.TYPE_PAYMENT,
                    "'MAKSAJA'",
                    "'MAKSUNSAAJA'",
                    "'" + formatter.format(new Date(binding.fragmentNewPaymentCalendarviewDueDate.getDate())) + "'",
                    Double.parseDouble(binding.fragmentNewPaymentEdittextAmount.getText().toString()),
                    1
                );

                //dataManager.insertTransaction(transaction);
            }
        });
    }


    private boolean validateFormData() {
        String tempString = "";
        boolean valid = true;
        // Payee's account
        tempString = binding.fragmentNewPaymentEdittextPayeeAccount.getText().toString().trim().toUpperCase();
        if (tempString.length() != 18 || !tempString.matches("^[A-Z]{2}[0-9]{16}$")) {
            Toast.makeText(getActivity(), "Payee acc non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        tempString = binding.fragmentNewPaymentEdittextPayeeName.getText().toString().trim().replaceAll("\\s+", "");
        if (tempString.isEmpty() || tempString.matches("[0-9]")) {
            Toast.makeText(getActivity(), "Payee name non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        tempString = binding.fragmentNewPaymentEdittextReference.getText().toString().trim();
        if ((tempString.isEmpty() && binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty())
                || tempString.matches("[A-Za-z]")
                || (!tempString.isEmpty() && !binding.fragmentNewPaymentEdittextMessage.getText().toString().trim().isEmpty()))
        {
            Toast.makeText(getActivity(), "Ref non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        tempString = binding.fragmentNewPaymentEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z][a-z]")) {
            Toast.makeText(getActivity(), "Amount non-valid", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            try {
                Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                e.printStackTrace();
            }
        }

        if (binding.fragmentNewPaymentSpinnerPayer.getSelectedItem() == null) {
            valid = false;
        }

        return valid;
    }


}
