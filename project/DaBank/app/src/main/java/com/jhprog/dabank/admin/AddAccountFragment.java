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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentAddAccountBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddAccountFragment extends Fragment {
    
    private FragmentAddAccountBinding binding;
    private ArrayList<Customer> customers;
    private ArrayAdapter<Customer> customerArrayAdapter;
    private ArrayAdapter<String> accountTypeArrayAdapter;
    private int accountType;
    private final String[] accountTypes = {"Current Account", "Savings Account", "Fixed-term Account"};

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
        initSpinners();
    }

    private void initSpinners() {
        // Array adapter for the customer selection Spinner
        customerArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                customers);
        customerArrayAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAddAccountSpinnerCustomers.setAdapter(customerArrayAdapter);
        // Array adapter for the Account type Spinner
        accountTypeArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                accountTypes);
        accountTypeArrayAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAddAccountSpinnerType.setAdapter(accountTypeArrayAdapter);
        // Called when user chooses an account type from the spinner
        binding.fragmentAddAccountSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = view.toString();
                if (selected.equals(accountTypes[0])) {
                    accountType = Account.TYPE_CURRENT;
                } else if (selected.equals(accountTypes[1])) {
                    accountType = Account.TYPE_SAVING;
                } else if (selected.equals(accountTypes[2])) {
                    accountType = Account.TYPE_FIXED_TERM;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                accountType = 0;
            }
        });
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
                    customerArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
