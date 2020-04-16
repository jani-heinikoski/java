package com.jhprog.dabank.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.databinding.FragmentTransactionsBinding;

import java.util.Objects;

public final class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;
    private MainViewModel viewModel;
    private IFragmentOwner fragmentOwner; // TODO Open transaction details fragment

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentOwner) {
            fragmentOwner = (IFragmentOwner) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        viewModel.getClickedAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                binding.fragmentTransactionsTextviewAccountClicked.setText(account.getAcc_number());
            }
        });
    }
}
