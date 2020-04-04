package com.jhprog.dabank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.databinding.FragmentChooseBankBinding;

public class ChooseBankFragment extends Fragment {

    private FragmentChooseBankBinding binding;
    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseBankBinding.inflate(inflater, container, false);

        initButtons();

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
    }

    private void initButtons() {
        binding.chooseBankFragmentImageButtonDabank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO define DaBank b_id
                viewModel.setB_id(1);
            }
        });
    }
}
