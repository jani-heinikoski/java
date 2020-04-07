/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/guide/components/fragments
 */
package com.jhprog.dabank.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.data.Bank;

import com.jhprog.dabank.databinding.FragmentChooseBankBinding;

public class ChooseBankFragment extends Fragment implements IBankChosenCallback {

    private FragmentChooseBankBinding binding;
    private IBankChosenCallback onChooseBankListener;
    private LoginViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // See if context (LoginActivity in this case) has implemented IBankChosenCallback,
        // throws ClassCastException if it hasn't.
        if (context instanceof IBankChosenCallback) {
            onChooseBankListener = (IBankChosenCallback) context;
        } else {
            throw new ClassCastException(context.toString() +
                    " must implement LoginViewModel.IBankChosenCallback!");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseBankBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get a reference to LoginActivity's viewModel (loginViewModel)
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    private void initButtons() {
        // DaBank onClickListener
        // TODO Refactor code below to utilize database
        binding.chooseBankFragmentImageButtonDabank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(new Bank(1, "DaBank"));
                onChoose();
            }
        });
        // Star Bank onClickListener
        binding.chooseBankFragmentImageButtonStarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(new Bank(2, "Star Bank"));
                onChoose();
            }
        });
        // Flash Bank onClickListener
        binding.chooseBankFragmentImageButtonFlashBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(new Bank(3, "Flash Bank"));
                onChoose();
            }
        });
        // Sun Bank onClickListener
        binding.chooseBankFragmentImageButtonSunBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(new Bank(4, "Sun Bank"));
                onChoose();
            }
        });
    }

    @Override
    public void onChoose() {
        onChooseBankListener.onChoose();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onChooseBankListener = null;
    }
}
