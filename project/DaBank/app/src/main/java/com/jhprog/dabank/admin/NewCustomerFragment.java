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
import com.jhprog.dabank.databinding.FragmentNewCustomerBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class NewCustomerFragment extends Fragment {


    private FragmentNewCustomerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewCustomerBinding.inflate(inflater, container, false);
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

                // TODO Insert the customer row using DataManager
                Customer customer = new Customer(
                        AdminActivity.getB_id(),
                        binding.fragmentNewCustomerEdittextUser.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextPassword.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextName.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextAddress.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextZipcode.getText().toString().trim(),
                        binding.fragmentNewCustomerEdittextPhone.getText().toString().trim()
                );

            }
        });
    }

    private boolean validateFormData() {
        // TODO validate new customer form data
        return false;
    }

}
