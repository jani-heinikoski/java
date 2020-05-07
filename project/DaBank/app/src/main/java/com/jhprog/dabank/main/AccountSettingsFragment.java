/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.05.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentAccountSettingsBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public final class AccountSettingsFragment extends Fragment {

    private FragmentAccountSettingsBinding binding;
    private MainViewModel viewModel;
    private Account clickedAccount;

    private String accountName;
    private boolean accountFrozen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getClickedAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                clickedAccount = account;

                if (account.getAcc_name() != null) {
                    accountName = account.getAcc_name();
                    binding.fragmentAccountSettingsEdittextName.setText(
                            accountName
                    );
                }

                accountFrozen = account.isAcc_frozen();
                binding.fragmentAccountSettingsSettingsSwitchFrozen.setChecked(accountFrozen);
            }
        });

        binding.fragmentAccountSettingsButtonUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAccountSettingsButtonUpdateAccount.startAnimation(AnimationProvider.getOnClickAnimation());
                if (validateFormData()) {
                    clickedAccount.setAcc_name(accountName);
                }

                clickedAccount.setAcc_frozen(accountFrozen);

                DataManager.getInstance().updateAccount(clickedAccount);
                viewModel.setClickedAccount(clickedAccount);

                Toast toast = Toast.makeText(getActivity(),"Account updated!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        binding.fragmentAccountSettingsButtonDeletePendings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAccountSettingsButtonDeletePendings.startAnimation(AnimationProvider.getOnClickAnimation());

                DataManager.getInstance().deletePendingPayments(clickedAccount.getAcc_number());
                Toast toast = Toast.makeText(getActivity(),"Pendings deleted!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private boolean validateFormData() {
        boolean valid = true;
        accountFrozen = binding.fragmentAccountSettingsSettingsSwitchFrozen.isChecked();
        // Name check
        accountName = binding.fragmentAccountSettingsEdittextName.getText().toString().trim();
        if (accountName.isEmpty()) {
            valid = false;
        }
        return valid;
    }


}
