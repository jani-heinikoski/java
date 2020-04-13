/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.databinding.FragmentNavigationBinding;
import com.jhprog.dabank.main.IFragmentOwner;
import com.jhprog.dabank.utility.AnimationProvider;

public class NavigationFragment extends Fragment {

    private FragmentNavigationBinding binding;
    private IFragmentOwner activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        if (getActivity() instanceof IFragmentOwner) {
            activity = (IFragmentOwner) getActivity();
        } else {
            throw new ClassCastException();
        }
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        // Changes fragment to the NewCustomerFragment
        binding.fragmentAdminButtonNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAdminButtonNewCustomer.startAnimation(AnimationProvider.getOnClickAnimation());
                activity.changeFragment(new NewCustomerFragment(), true);
            }
        });
        // Changes fragment to the AddAccountFragment
        binding.fragmentAdminButtonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAdminButtonNewCustomer.startAnimation(AnimationProvider.getOnClickAnimation());
                activity.changeFragment(new AddAccountFragment(), true);
            }
        });

    }
}
