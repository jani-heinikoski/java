package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.Gravity;
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
import com.jhprog.dabank.data.NormalTransaction;
import com.jhprog.dabank.data.Transaction;
import com.jhprog.dabank.databinding.FragmentMyTransferBinding;
import com.jhprog.dabank.utility.AnimationProvider;
import com.jhprog.dabank.utility.TimeManager;

import java.util.ArrayList;
import java.util.Objects;

public final class MyTransferFragment extends Fragment {

    private FragmentMyTransferBinding binding;
    private MainViewModel viewModel;

    private ArrayAdapter<Account> fromAccountsAdapter;
    private ArrayAdapter<Account> toAccountsAdapter;

    private ArrayList<Account> fromAccounts;
    private ArrayList<Account> toAccounts;
    private Account selectedFromAccount;
    private Account selectedToAccount;

    private double amount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyTransferBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        fromAccounts = viewModel.getAccounts();
        toAccounts = new ArrayList<>();
        toAccounts.addAll(fromAccounts);
        // Array adapter for fromAccounts
        fromAccountsAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                fromAccounts);
        fromAccountsAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentMyTransferSpinnerFromAccount.setAdapter(fromAccountsAdapter);
        // Array adapter for toAccounts
        toAccountsAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                toAccounts);
        toAccountsAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentMyTransferSpinnerToAccount.setAdapter(toAccountsAdapter);
        // Listener for selected account
        binding.fragmentMyTransferSpinnerFromAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectedFromAccount == null) {
                    selectedFromAccount = fromAccountsAdapter.getItem(position);
                    toAccountsAdapter.remove(selectedFromAccount);
                    toAccountsAdapter.notifyDataSetChanged();
                } else {
                    toAccountsAdapter.add(selectedFromAccount);
                    selectedFromAccount = fromAccountsAdapter.getItem(position);
                    toAccountsAdapter.remove(selectedFromAccount);
                    toAccountsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFromAccount = null;
            }
        });

        binding.fragmentMyTransferSpinnerToAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedToAccount = toAccountsAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedToAccount = null;
            }
        });

        binding.fragmentMyTransferButtonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentMyTransferButtonTransfer.startAnimation(AnimationProvider.getOnClickAnimation());
                if (selectedFromAccount != null && selectedToAccount != null && validateAmount()) {
                    NormalTransaction transaction = new NormalTransaction(
                            Transaction.TYPE_TRANSFER,
                            Transaction.REF_NUM_NULL,
                            selectedFromAccount,
                            selectedToAccount,
                            viewModel.getCustomer().getCust_name(),
                            Transaction.MESSAGE_NULL,
                            viewModel.getBank().getBank_bic(),
                            amount,
                            TimeManager.getInstance().todayString()
                    );
                    System.out.println("LOGGER: fromAccount: " + selectedFromAccount.getAcc_number() + "|" + selectedFromAccount.getAcc_balance());
                    System.out.println("LOGGER: toAccount: " + selectedToAccount.getAcc_number() + "|" + selectedToAccount.getAcc_balance());
                    if (viewModel.getBank().handleTransaction(transaction, selectedFromAccount, selectedToAccount)) {
                        Toast toast = Toast.makeText(getActivity(),"Transfer successful!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getActivity(),"Transfer failed!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    fromAccountsAdapter.notifyDataSetChanged();
                    toAccountsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private boolean validateAmount() {
        String tempString = binding.fragmentMyTransferEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentMyTransferEdittextAmount.setError("Non-valid");
            return false;
        } else {
            try {
                amount = Double.parseDouble(tempString);
            } catch (Exception e) {
                binding.fragmentMyTransferEdittextAmount.setError("Non-valid");
                return false;
            }
        }
        return true;
    }
}
