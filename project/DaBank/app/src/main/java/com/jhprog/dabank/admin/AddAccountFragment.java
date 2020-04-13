/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentAddAccountBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddAccountFragment extends Fragment {
    
    private FragmentAddAccountBinding binding;
    private ArrayList<Customer> customers;
    private ArrayAdapter<Customer> arrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customers = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddAccountBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                customers);
        arrayAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAddAccountSpinnerCustomers.setAdapter(arrayAdapter);
    }

    private void initButtons() {
        binding.fragmentAddAccountButtonSearchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cust_name = binding.fragmentAddAccountEdittextName.getText().toString().trim();
                if (cust_name.isEmpty()) {
                    Toast.makeText(getActivity(), "Give a name to search for", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    customers = DataManager.getInstance().getCustomersByName(AdminActivity.getB_id(), cust_name);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
