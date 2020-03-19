/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.8
 */
package com.jhprog.menudemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.menudemo.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        // TODO edittext changed listener

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getFontColor().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    binding.mainTextview.setTextColor(integer);
                }
            }
        });

        viewModel.getFontSize().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    binding.mainTextview.setTextSize(integer);
                }
            }
        });

        viewModel.getTextVisible().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.mainTextview.setVisibility(View.VISIBLE);
                } else {
                    binding.mainTextview.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.getTextAllCaps().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.mainTextview.setAllCaps(aBoolean);
            }
        });

        viewModel.getTextEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // TODO FIX THIS BULLSHIT
                if (aBoolean) {
                    binding.mainEdittext.setFocusableInTouchMode(true);
                } else {
                    binding.mainEdittext.setFocusableInTouchMode(false);
                    binding.mainEdittext.clearFocus();
                }
                binding.mainEdittext.setFocusable(aBoolean);
            }
        });

        viewModel.getEditText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mainTextview.setText(s);
            }
        });
    }

}
