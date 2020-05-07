/*
 * Author: Jani Olavi Heinikoski
 * Date: 16.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.PasswordHandler;
import com.jhprog.dabank.databinding.FragmentNewCustomerBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class NewCustomerFragment extends Fragment {

    private FragmentNewCustomerBinding binding;
    private AdminViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewCustomerBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AdminViewModel.class);
    }

    private void initButtons() {
        binding.fragmentNewCustomerButtonNewCustomer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast") // Toast is shown in try statement's finally block
            @Override
            public void onClick(View v) {
                binding.fragmentNewCustomerButtonNewCustomer.startAnimation(AnimationProvider.getOnClickAnimation());
                if (!validateFormData()) {
                    Toast.makeText(getActivity(), "Form data invalid!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast toast = Toast.makeText(getActivity(),"Inserting customer!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                Customer customer = new Customer(
                        viewModel.getBank_id(),
                        binding.fragmentNewCustomerEdittextUser.getText().toString().trim(),
                        PasswordHandler.getInstance().newPassword(binding.fragmentNewCustomerEdittextPassword.getText().toString().trim()),
                        binding.fragmentNewCustomerEdittextName.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextAddress.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextZipcode.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextPhone.getText().toString().trim()
                );

                DataManager dataManager = DataManager.getInstance();
                Toast toast = null;
                try {
                    dataManager.insertCustomer(customer);
                    toast = Toast.makeText(getActivity(),"Insertion succeeded!", Toast.LENGTH_SHORT);
                } catch (Exception e) {
                    toast = Toast.makeText(getActivity(),"Insertion failed!", Toast.LENGTH_SHORT);
                } finally {
                    if (toast != null) {
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });
    }

    private boolean validateFormData() {
        String tempString = "";
        boolean valid = true;
        boolean lowerCase = false;
        boolean upperCase = false;
        boolean number = false;
        boolean specialChar = false;
        // Name
        tempString = binding.fragmentNewCustomerEdittextName.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("['\".]")) {
            valid = false;
            binding.fragmentNewCustomerEdittextName.setError("Name invalid!");
        }
        // Username
        tempString = binding.fragmentNewCustomerEdittextUser.getText().toString().trim();
        if (
                tempString.isEmpty()
                || tempString.length() < 4
                || tempString.matches("['\".]")
                || DataManager.getInstance().customerExists(viewModel.getBank_id(), tempString)
                || tempString.toLowerCase().equals("admin")
                || tempString.toLowerCase().equals("username")
        ) {
            valid = false;
            binding.fragmentNewCustomerEdittextUser.setError("Invalid");
        }
        // Password check
        tempString = binding.fragmentNewCustomerEdittextPassword.getText().toString().trim();
        if (tempString.matches("\\s+") || tempString.matches("['\".]") || tempString.length() < 12) {
            valid = false;
            binding.fragmentNewCustomerEdittextPassword.setError("Invalid password");
        } else {
            for (char c : tempString.toCharArray()) {
                if (Character.isDigit(c)) {
                    number = true;
                } else if (Character.isLowerCase(c) && Character.isLetter(c)) {
                    lowerCase = true;
                } else if (Character.isUpperCase(c) && Character.isLetter(c)) {
                    upperCase = true;
                } else if (!Character.isLetterOrDigit(c)) {
                    specialChar = true;
                }
            }
            if (!(number && lowerCase && upperCase && specialChar)) {
                valid = false;
                binding.fragmentNewCustomerEdittextPassword.setError("Invalid password");
            }
        }
        // Address check
        tempString = binding.fragmentNewCustomerEdittextAddress.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("['\".]")) {
            valid = false;
            binding.fragmentNewCustomerEdittextAddress.setError("Invalid address");
        }
        // Zipcode check
        tempString = binding.fragmentNewCustomerEdittextZipcode.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("\\s+" ) || tempString.matches("['\".]")) {
            valid = false;
            binding.fragmentNewCustomerEdittextZipcode.setError("Invalid zipcode");
        } else {
            for (char c : tempString.toCharArray()) {
                if (!Character.isDigit(c)) {
                    valid = false;
                    binding.fragmentNewCustomerEdittextZipcode.setError("Invalid zipcode");
                    break;
                }
            }
        }
        // Phone number check
        tempString = binding.fragmentNewCustomerEdittextPhone.getText().toString().trim();
        if (tempString.isEmpty() || tempString.length() < 6 || tempString.matches("['\".]")) {
            valid = false;
            binding.fragmentNewCustomerEdittextPhone.setError("Invalid phone number");
        } else {
            for (char c : tempString.toCharArray()) {
                if (!(Character.isDigit(c) || c == '+')) {
                    valid = false;
                    binding.fragmentNewCustomerEdittextPhone.setError("Invalid phone number");
                    break;
                }
            }
        }
        return valid;
    }

}
