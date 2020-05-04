package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.databinding.FragmentProfileBinding;


public final class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.fragmentProfileEdittextName.setText(
                viewModel.getCustomer().getCust_name()
        );
        binding.fragmentProfileEdittextAddress.setText(
                viewModel.getCustomer().getCust_address()
        );
    }

    private boolean validateFormData() {
        return true;
    }
}
