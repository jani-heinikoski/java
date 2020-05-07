/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.05.2020
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentAtmSimulationBinding;
import com.jhprog.dabank.utility.AnimationProvider;

import java.util.ArrayList;
import java.util.Objects;

public final class AtmSimulationFragment extends Fragment {

    private DataManager dataManager;
    private FragmentAtmSimulationBinding binding;
    private MainViewModel viewModel;
    private ArrayAdapter<BankCard> bankCardArrayAdapter;
    private ArrayAdapter<String> countryLimitAdapter;
    private ArrayList<BankCard> bankCards;

    private BankCard selectedBankCard;

    private final String[] countryChoices = {"Finland", "Nordic Countries", "Europe", "Whole World"};
    private int countryLimit;
    private double amount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankCards = new ArrayList<>();
        dataManager = DataManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAtmSimulationBinding.inflate(inflater, container, false);

        countryLimitAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                countryChoices);
        countryLimitAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAtmSimulationSpinnerCountryLimit.setAdapter(countryLimitAdapter);

        binding.fragmentAtmSimulationSpinnerCountryLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sel = countryChoices[position];

                switch (sel) {
                    case "Finland":
                        countryLimit = BankCard.LIMIT_FINLAND;
                        break;
                    case "Nordic Countries":
                        countryLimit = BankCard.LIMIT_NORDIC_COUNTRIES;
                        break;
                    case "Europe":
                        countryLimit = BankCard.LIMIT_EUROPE;
                        break;
                    default:
                        countryLimit = BankCard.LIMIT_WHOLE_WORLD;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryLimit = BankCard.LIMIT_WHOLE_WORLD;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        ArrayList<Account> accounts = viewModel.getAccounts();
        bankCards.clear();

        for (Account account : accounts) {
            bankCards.addAll(dataManager.getBankCardsByOwner(account.getAcc_id()));
        }

        bankCardArrayAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                bankCards);
        bankCardArrayAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAtmSimulationSpinnerChooseBankCard.setAdapter(bankCardArrayAdapter);

        binding.fragmentAtmSimulationSpinnerChooseBankCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBankCard = bankCardArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedBankCard = null;
            }
        });

        binding.fragmentAtmSimulationButtonDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAtmSimulationButtonDeposit.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    return;
                }

                if (selectedBankCard.deposit(amount)) {
                    Toast toast = Toast.makeText(getActivity(),"Deposited " + amount, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(),"Deposit failed!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                viewModel.loadAccounts();
            }
        });

        binding.fragmentAtmSimulationButtonWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAtmSimulationButtonWithdraw.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    return;
                }

                if (selectedBankCard.withdraw(amount, countryLimit)) {
                    Toast toast = Toast.makeText(getActivity(),"Withdrew " + amount, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(),"Withdraw failed!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                viewModel.loadAccounts();
            }
        });

    }

    private boolean validateFormData() {
        String tempString = "";
        boolean valid = true;
        // Money amount check
        tempString = binding.fragmentAtmSimulationEdittextAmount.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentAtmSimulationEdittextAmount.setError("Non-valid");
            valid = false;
        } else {
            try {
                amount = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentAtmSimulationEdittextAmount.setError("Non-valid");
            }
        }

        if (selectedBankCard == null) {
            valid = false;
        }

        return valid;
    }
}
