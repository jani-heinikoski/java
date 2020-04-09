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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.databinding.FragmentNewPaymentBinding;

public class NewPaymentFragment extends Fragment {

    private FragmentNewPaymentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
