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
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.CurrentAccount;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.SavingsAccount;
import com.jhprog.dabank.databinding.FragmentAddAccountBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddAccountFragment extends Fragment {
    
    private FragmentAddAccountBinding binding;
    private ArrayList<Customer> customers;
    private int chosenCustomerID;
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
        initSwitches();
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
        // Called when user chooses a customer from the spinner
        binding.fragmentAddAccountSpinnerCustomers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customerArrayAdapter.getItem(position) != null) {
                    chosenCustomerID = Objects.requireNonNull(customerArrayAdapter.getItem(position)).getCust_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chosenCustomerID = 0;
            }
        });
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
                String selected = accountTypeArrayAdapter.getItem(position);
                if (selected != null) {
                    if (selected.equals(accountTypes[0])) {
                        accountType = Account.TYPE_CURRENT;
                    } else if (selected.equals(accountTypes[1])) {
                        accountType = Account.TYPE_SAVING;
                    } else if (selected.equals(accountTypes[2])) {
                        accountType = Account.TYPE_FIXED_TERM;
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid account type", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                accountType = 0;
            }
        });
    }

    private void initSwitches() {
        binding.fragmentAddAccountSwitchIsCredit.setChecked(false);
        binding.fragmentAddAccountSwitchIsCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.fragmentAddAccountEdittextCreditAmount.setVisibility(View.VISIBLE);
                } else {
                    binding.fragmentAddAccountEdittextCreditAmount.setVisibility(View.INVISIBLE);
                }
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
                    ArrayList<Customer> temp = DataManager.getInstance().getCustomersByName(AdminActivity.getB_id(), cust_name);
                    customers.clear();
                    if (temp != null) {
                        customers.addAll(temp);
                    }
                    customerArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.fragmentAddAccountButtonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    Toast.makeText(getActivity(), "Invalid form data", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("LOGGER: " + accountType);
                Account account;

                switch (accountType) { // TODO calendar for the fixed-term account!
                    case Account.TYPE_CURRENT:
                        if (chosenCustomerID != 0) {
                            account = new CurrentAccount(
                                    AdminActivity.getB_id(),
                                    chosenCustomerID,
                                    Double.parseDouble(binding.fragmentAddAccountEdittextAmount.getText().toString().trim()),
                                    binding.fragmentAddAccountSwitchIsCredit.isChecked() ? Double.parseDouble(binding.fragmentAddAccountEdittextCreditAmount.getText().toString().trim()) : 0,
                                    binding.fragmentAddAccountEdittextAccountNumber.getText().toString().trim()
                            );
                            DataManager.getInstance().insertAccount(account);
                        }
                        break;

                    case Account.TYPE_SAVING:
                        if (chosenCustomerID != 0) {
                            account = new SavingsAccount(
                                    AdminActivity.getB_id(),
                                    chosenCustomerID,
                                    Double.parseDouble(binding.fragmentAddAccountEdittextAmount.getText().toString().trim()),
                                    10,
                                    binding.fragmentAddAccountEdittextAccountNumber.getText().toString().trim()
                            );
                            DataManager.getInstance().insertAccount(account);
                        }
                        break;
                    default:
                        return;
                }

            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        String tempString;
        // Account number check
        tempString = binding.fragmentAddAccountEdittextAccountNumber.getText().toString().trim();
        if (tempString.length() != 18 || !tempString.matches("^[A-Z]{2}[0-9]{16}$")) {
            binding.fragmentAddAccountEdittextAccountNumber.setError("Value invalid");
            valid = false;
        } else {
            if (DataManager.getInstance().accountExists(tempString)) {
                valid = false;
                binding.fragmentAddAccountEdittextAccountNumber.setError("Acc. num. already exists!");
            }
        }
        // Money amount check
        tempString = binding.fragmentAddAccountEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z][a-z]")) {
            binding.fragmentAddAccountEdittextAmount.setError("Amount invalid");
            valid = false;
        } else {
            try {
                Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentAddAccountEdittextAmount.setError("Amount invalid");
            }
        }
        // Credit amount check
        if (binding.fragmentAddAccountSwitchIsCredit.isChecked()) {
            tempString = binding.fragmentAddAccountEdittextCreditAmount.getText().toString().trim();
            if (tempString.isEmpty() || tempString.matches("[A-Z][a-z]")) {
                valid = false;
                binding.fragmentAddAccountEdittextCreditAmount.setError("Amount invalid");
            } else {
                try {
                    Double.parseDouble(tempString);
                } catch (Exception e) {
                    valid = false;
                    binding.fragmentAddAccountEdittextCreditAmount.setError("Amount invalid");
                }
            }
        }

        return valid;
    }
}
