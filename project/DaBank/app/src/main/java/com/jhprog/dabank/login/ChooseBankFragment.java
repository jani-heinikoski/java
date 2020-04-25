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

import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentChooseBankBinding;

public class ChooseBankFragment extends Fragment {

    private FragmentChooseBankBinding binding;
    private IFragmentOwner fragmentOwner;
    private LoginViewModel viewModel;
    private DataManager dataManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // See if context (LoginActivity in this case) has implemented IBankChosenCallback,
        // throws ClassCastException if it hasn't.
        if (context instanceof IFragmentOwner) {
            fragmentOwner = (IFragmentOwner) context;
        } else {
            throw new ClassCastException(context.toString() +
                    " must implement LoginViewModel.IBankChosenCallback!");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getInstance();
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
                viewModel.setBank(dataManager.getBankByName("DaBank"));
                fragmentOwner.changeFragment(new LoginFragment(), true);
            }
        });
        // Star Bank onClickListener
        binding.chooseBankFragmentImageButtonStarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(dataManager.getBankByName("Star Bank"));
                fragmentOwner.changeFragment(new LoginFragment(), true);
            }
        });
        // Flash Bank onClickListener
        binding.chooseBankFragmentImageButtonFlashBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(dataManager.getBankByName("Flash Bank"));
                fragmentOwner.changeFragment(new LoginFragment(), true);
            }
        });
        // Sun Bank onClickListener
        binding.chooseBankFragmentImageButtonSunBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBank(dataManager.getBankByName("Sun Bank"));
                fragmentOwner.changeFragment(new LoginFragment(), true);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentOwner = null;
    }
}
