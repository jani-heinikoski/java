/*
 * Author: Jani Olavi Heinikoski
 * Date: 09.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.databinding.FragmentMainBinding;
import com.jhprog.dabank.utility.AnimationProvider;

// This fragment is purely a cross point for navigating into different functionalities
public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private Animation onClickAnimation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onClickAnimation = AnimationProvider.getOnClickAnimation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {

        binding.fragmentMainButtonNewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentMainButtonNewPayment.startAnimation(onClickAnimation);
                if (getActivity() instanceof IFragmentOwner) {
                    ((IFragmentOwner) getActivity()).changeFragment(new NewPaymentFragment(), true);
                }
            }
        });

        binding.fragmentMainButtonNewTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentMainButtonNewTransfer.startAnimation(onClickAnimation);
            }
        });

        binding.fragmentMainButtonAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentMainButtonAccounts.startAnimation(onClickAnimation);
            }
        });

        binding.fragmentMainButtonSimulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentMainButtonSimulations.startAnimation(onClickAnimation);
            }
        });

    }
}
