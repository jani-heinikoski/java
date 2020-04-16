package com.jhprog.dabank.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.PasswordHandler;
import com.jhprog.dabank.databinding.FragmentNewCustomerBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class NewCustomerFragment extends Fragment {

    private FragmentNewCustomerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewCustomerBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.fragmentNewCustomerButtonNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentNewCustomerButtonNewCustomer.startAnimation(AnimationProvider.getOnClickAnimation());
                if (!validateFormData()) {
                    Toast.makeText(getActivity(), "Form data invalid!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Customer customer = new Customer(
                        AdminActivity.getB_id(),
                        binding.fragmentNewCustomerEdittextUser.getText().toString().trim(),
                        PasswordHandler.getInstance().newPassword(binding.fragmentNewCustomerEdittextPassword.getText().toString().trim()),
                        binding.fragmentNewCustomerEdittextName.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextAddress.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextZipcode.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextPhone.getText().toString().trim()
                );

                DataManager dataManager = DataManager.getInstance();
                dataManager.insertCustomer(customer);

            }
        });
    }

    private boolean validateFormData() { // TODO escape ' characters! MyPassword1! 1999
        String tempString = "";
        boolean valid = true;
        boolean lowerCase = false;
        boolean upperCase = false;
        boolean number = false;
        boolean specialChar = false;
        // Name
        tempString = binding.fragmentNewCustomerEdittextName.getText().toString().trim();
        if (tempString.isEmpty()) {
            valid = false;
            Toast.makeText(getActivity(), "Enter name for customer", Toast.LENGTH_SHORT).show();
        }
        // Username
        tempString = binding.fragmentNewCustomerEdittextUser.getText().toString().trim();
        if (tempString.isEmpty() || tempString.length() < 4) {
            valid = false;
            Toast.makeText(getActivity(), "Username must be >=4 characters", Toast.LENGTH_SHORT).show();
        }
        // Password check
        tempString = binding.fragmentNewCustomerEdittextPassword.getText().toString().trim();
        if (tempString.matches("\\s+")) {
            valid = false;
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
                Toast.makeText(getActivity(), "Invalid password!", Toast.LENGTH_SHORT).show();
            }
        }
        // Address check
        tempString = binding.fragmentNewCustomerEdittextAddress.getText().toString().trim();
        if (tempString.isEmpty()) {
            valid = false;
            Toast.makeText(getActivity(), "Empty address!", Toast.LENGTH_SHORT).show();
        }
        // Zipcode check
        tempString = binding.fragmentNewCustomerEdittextZipcode.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("\\s+")) {
            valid = false;
            Toast.makeText(getActivity(), "Invalid zipcode!", Toast.LENGTH_SHORT).show();
        } else {
            for (char c : tempString.toCharArray()) {
                if (!Character.isDigit(c)) {
                    valid = false;
                    Toast.makeText(getActivity(), "Invalid zipcode!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        // Phone number check
        tempString = binding.fragmentNewCustomerEdittextPhone.getText().toString().trim();
        if (tempString.isEmpty() || tempString.length() < 6) {
            valid = false;
            Toast.makeText(getActivity(), "Invalid phone number!", Toast.LENGTH_SHORT).show();
        } else {
            for (char c : tempString.toCharArray()) {
                if (!(Character.isDigit(c) || c == '+')) {
                    valid = false;
                    Toast.makeText(getActivity(), "Invalid phone number!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        return valid;
    }

}
