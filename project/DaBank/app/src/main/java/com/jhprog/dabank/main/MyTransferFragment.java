package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.databinding.FragmentMyTransferBinding;

import java.util.ArrayList;

public final class MyTransferFragment extends Fragment {

    private FragmentMyTransferBinding binding;
    private MainViewModel viewModel;
    private ArrayList<Account> fromAccounts;
    private ArrayList<Account> toAccounts;
    private Account selectedAccount;

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
    }
}
