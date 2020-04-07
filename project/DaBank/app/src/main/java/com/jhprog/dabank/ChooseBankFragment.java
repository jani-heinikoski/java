/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/guide/components/fragments
 */
package com.jhprog.dabank;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.jhprog.dabank.LoginViewModel.IBankChosenCallback;

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
        binding.chooseBankFragmentImageButtonDabank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO define DaBank b_id
                viewModel.setB_id(1);
                viewModel.setB_name("DaBank");
                onChoose();
            }
        });
        // Star Bank onClickListener
        binding.chooseBankFragmentImageButtonStarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO define Star Bank b_id
                viewModel.setB_id(2);
                viewModel.setB_name("Star Bank");
                onChoose();
            }
        });
        // Flash Bank onClickListener
        binding.chooseBankFragmentImageButtonFlashBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO define Flash Bank b_id
                viewModel.setB_id(3);
                viewModel.setB_name("Flash Bank");
                onChoose();
            }
        });
        // Sun Bank onClickListener
        binding.chooseBankFragmentImageButtonSunBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO define Sun Bank b_id
                viewModel.setB_id(4);
                viewModel.setB_name("Sun Bank");
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
