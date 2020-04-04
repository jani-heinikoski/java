package com.jhprog.dabank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private int b_id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get a reference to MainActivity's viewModel (loginViewModel)
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        /* TODO change this snippet if not necessary
        // Set an observer in order to have the correct b_id in realtime.
        viewModel.getB_id().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    b_id = integer;
                }
            }
        });
         */

        if (viewModel.getB_id().getValue() != null) {
            this.b_id = viewModel.getB_id().getValue();
            System.out.println("LOGGER: b_id = " + this.b_id);
        }

    }

    private void initButtons() {
        // TODO login button
    }

}
