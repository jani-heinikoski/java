/*
 * Author: Jani Olavi Heinikoski
 * Date: 02.05.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

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
import com.jhprog.dabank.databinding.FragmentProfileBinding;
import com.jhprog.dabank.utility.AnimationProvider;


public final class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainViewModel viewModel;
    private DataManager dataManager;

    private String name, address, phoneNumber, zipCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getInstance();
    }

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
        binding.fragmentProfileEdittextZipcode.setText(
                viewModel.getCustomer().getCust_zipcode()
        );
        binding.fragmentProfileEdittextPhone.setText(
                viewModel.getCustomer().getCust_phone()
        );

        binding.fragmentProfileButtonApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentProfileButtonApplyChanges.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    return;
                }

                Customer customer = viewModel.getCustomer();
                Customer customerChanged = new Customer(
                        customer.getCust_id(),
                        customer.getCust_bank_id(),
                        customer.getCust_user(),
                        customer.getCust_passwd(),
                        name,
                        address,
                        zipCode,
                        phoneNumber
                );

                dataManager.updateCustomer(customerChanged);
                viewModel.setCustomer(customerChanged);

                Toast toast = Toast.makeText(getActivity(),"Information updated!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private boolean validateFormData() {
        boolean valid = true;
        // Name check
        name = binding.fragmentProfileEdittextName.getText().toString().trim();
        if (name.matches("[0-9]")) {
            binding.fragmentProfileEdittextName.setError("Invalid name");
            valid = false;
        }
        // Address check
        address = binding.fragmentProfileEdittextAddress.getText().toString().trim();
        if (address.isEmpty() || address.matches("['\".]")) {
            binding.fragmentProfileEdittextAddress.setError("Invalid address");
            valid = false;
        }
        // Zipcode check
        zipCode = binding.fragmentProfileEdittextZipcode.getText().toString().trim();
        if (zipCode.isEmpty() || zipCode.matches("\\s+" ) || zipCode.matches("['\".]")) {
            valid = false;
            binding.fragmentProfileEdittextZipcode.setError("Invalid zipcode");
        } else {
            for (char c : zipCode.toCharArray()) {
                if (!Character.isDigit(c)) {
                    valid = false;
                    binding.fragmentProfileEdittextZipcode.setError("Invalid zipcode");
                    break;
                }
            }
        }
        // Phone number check
        phoneNumber = binding.fragmentProfileEdittextPhone.getText().toString().trim();
        if (phoneNumber.isEmpty() || phoneNumber.length() < 6 || phoneNumber.matches("['\".]")) {
            valid = false;
            binding.fragmentProfileEdittextPhone.setError("Invalid phone number");
        } else {
            for (char c : phoneNumber.toCharArray()) {
                if (!(Character.isDigit(c) || c == '+')) {
                    valid = false;
                    binding.fragmentProfileEdittextPhone.setError("Invalid phone number");
                    break;
                }
            }
        }
        return valid;
    }
}
