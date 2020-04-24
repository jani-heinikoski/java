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
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.admin.AdminActivity;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.PasswordHandler;
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
        this.bank = viewModel.getBank();
        binding.fragmentLoginHeader.setText(
                String.format(
                        Locale.getDefault(),
                        "%s%s",
                        getString(R.string.fragment_login_header_text),
                        bank.getBank_name()
                )
        );
    }

    private void initButtons() {

        binding.framentLoginButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.framentLoginButtonLogin.startAnimation(AnimationProvider.getOnClickAnimation());
                // Get dataManager and passwordHandler in order to authenticate the user
                DataManager dataManager = DataManager.getInstance();
                PasswordHandler passwordHandler = PasswordHandler.getInstance();
                // Get the customer from the database by his/her username
                Customer customer =
                        dataManager.getCustomerByUsername(
                                viewModel.getBank(), binding.fragmentLoginEdittextUsername.getText().toString().trim()
                        );
                // See if such customer even exists
                if (customer != null &&
                        passwordHandler.passwordMatches(
                                customer.getCust_passwd(), binding.fragmentLoginEdittextPassword.getText().toString().trim()
                        )
                ) { // Customer authenticated successfully
                    Intent intent;
                    // See if user is admin
                    if (customer.getCust_user().equals("1337")) {
                        intent = new Intent(getActivity(), AdminActivity.class);
                        intent.putExtra("b_id", bank.getBank_id());
                        intent.putExtra("cust_id", customer.getCust_id());
                    } else {
                        intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("b_id", bank.getBank_id());
                        intent.putExtra("b_name", bank.getBank_name());
                        intent.putExtra("b_bic", bank.getBank_bic());
                        intent.putExtra("cust_id", customer.getCust_id());
                    }
                    binding.fragmentLoginTextviewInvalidCredentials.setVisibility(View.INVISIBLE);
                    binding.fragmentLoginEdittextUsername.setText("");
                    binding.fragmentLoginEdittextPassword.setText("");
                    startActivity(intent);
                } else {
                    binding.fragmentLoginTextviewInvalidCredentials.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
