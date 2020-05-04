/*
 * Author: Jani Olavi Heinikoski
 * Date: 04.05.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.NormalTransaction;
import com.jhprog.dabank.data.Transaction;
import com.jhprog.dabank.databinding.FragmentTransactionDetailBinding;

import java.util.Locale;
import java.util.Objects;

public final class TransactionDetailFragment extends Fragment {

    private FragmentTransactionDetailBinding binding;
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        viewModel.getClickedTransaction().observe(getViewLifecycleOwner(), new Observer<NormalTransaction>() {
            @Override
            public void onChanged(NormalTransaction transaction) {
                DataManager dataManager = DataManager.getInstance();
                String fromAccNum = transaction.getTrans_from_acc_number();
                String toAccNum = transaction.getTrans_to_acc_number();
                Account fromAccount = dataManager.getAccountByAccountNumber(fromAccNum);
                Account toAccount = dataManager.getAccountByAccountNumber(toAccNum);
                String fromBankBIC, toBankBIC;

                binding.fragmentTransactionDetailTextviewFromAccountNumber.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_from_account_number_text), fromAccNum)
                );
                binding.fragmentTransactionDetailTextviewToAccountNumber.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_to_account_number_text), toAccNum)
                );
                binding.fragmentTransactionDetailTextviewAmount.setText(
                        String.format(Locale.getDefault(), "%s %.2f€", getString(R.string.fragment_transaction_detail_amount_text), transaction.getTrans_amount())
                );
                binding.fragmentTransactionDetailTextviewDate.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_date_text), transaction.getTrans_date())
                );

                String type = "unknown";

                switch (transaction.getTrans_type()) {
                    case Transaction.TYPE_PAYMENT:
                        type = "payment";
                        break;
                    case Transaction.TYPE_TRANSFER:
                        type = "transfer";
                        break;
                    case Transaction.TYPE_DEPOSIT:
                        type = "deposit";
                        break;
                    case Transaction.TYPE_WITHDRAW:
                        type = "withdraw";
                        break;
                }

                binding.fragmentTransactionDetailTextviewTransactionType.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_transaction_type_text), type)
                );

                // Transactions always have the "from" bank's BIC
                fromBankBIC = (fromAccount == null) ? "unknown" : transaction.getTrans_bank_bic();
                toBankBIC = (toAccount == null) ? "unknown" : dataManager.getBankByID(toAccount.getAcc_bank_id()).getBank_bic();

                binding.fragmentTransactionDetailTextviewFromBankBic.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_from_bank_bic_text), fromBankBIC)
                );
                binding.fragmentTransactionDetailTextviewToBankBic.setText(
                        String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_to_bank_bic_text), toBankBIC)
                );

                if (transaction.getTrans_ref_number() != Transaction.REF_NUM_NULL) {
                    binding.fragmentTransactionDetailTextviewReferenceNumber.setText(
                            String.format(Locale.getDefault(), "%s %d", getString(R.string.fragment_transaction_detail_reference_number_text), transaction.getTrans_ref_number())
                    );
                }
                if (!transaction.getTrans_message().equals(Transaction.MESSAGE_NULL)) {
                    binding.fragmentTransactionDetailTextviewMessage.setText(
                            String.format(Locale.getDefault(), "%s %s", getString(R.string.fragment_transaction_detail_message_text), transaction.getTrans_message())
                    );
                }
            }
        });
    }
}
