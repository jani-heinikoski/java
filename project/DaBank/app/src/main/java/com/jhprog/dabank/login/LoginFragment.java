/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/guide/components/fragments
 */
package com.jhprog.dabank.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.databinding.FragmentLoginBinding;
import com.jhprog.dabank.main.MainActivity;
import com.jhprog.dabank.utility.AnimationProvider;

import java.util.Locale;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private Bank bank;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
        // Get a reference to LoginActivity's viewModel (LoginViewModel)
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        // Get the id of the chosen bank and set the correct login header
        this.bank = viewModel.getBank().getValue();
        binding.fragmentLoginHeader.setText(
                String.format(
                        Locale.getDefault(),
                        "%s%s",
                        getString(R.string.fragment_login_header_text),
                        bank.getName()
                )
        );
    }

    private void initButtons() {
        binding.framentLoginButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.framentLoginButtonLogin.startAnimation(AnimationProvider.getOnClickAnimation());
                if (viewModel.getBank().getValue().handleAuthentication(
                        binding.fragmentLoginEdittextUsername.getText().toString(),
                        binding.fragmentLoginEdittextPassword.getText().toString()
                )) {
                    // If user authenticates successfully, start MainActivity and give it
                    // the bank by value.
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("b_id", bank.getId());
                    intent.putExtra("b_name", bank.getName());
                    intent.putExtra("cust_id", 1);
                    binding.fragmentLoginTextviewInvalidCredentials.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                } else {
                    binding.fragmentLoginTextviewInvalidCredentials.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
