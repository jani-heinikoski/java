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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentBankCardSettingsBinding;

import java.util.Objects;

public final class BankCardSettingsFragment extends Fragment {

    private FragmentBankCardSettingsBinding binding;
    private MainViewModel viewModel;
    private ArrayAdapter<String> countryLimitAdapter;
    private DataManager dataManager;

    private final String[] countryChoices = {"Finland", "Nordic Countries", "Europe", "Whole World"};

    private double withdrawLimit, paymentLimit;
    private int countryLimit;

    private BankCard clickedBankCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBankCardSettingsBinding.inflate(inflater, container, false);

        countryLimitAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                countryChoices);
        countryLimitAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentBankCardSettingsSpinnerCountryLimit.setAdapter(countryLimitAdapter);

        binding.fragmentBankCardSettingsSpinnerCountryLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = countryChoices[position];
                switch (s) {
                    case "Finland":
                        countryLimit = BankCard.LIMIT_FINLAND;
                        break;
                    case "Nordic Countries":
                        countryLimit = BankCard.LIMIT_NORDIC_COUNTRIES;
                        break;
                    case "Europe":
                        countryLimit = BankCard.LIMIT_EUROPE;
                        break;
                    case "Whole World":
                        countryLimit = BankCard.LIMIT_WHOLE_WORLD;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryLimit = BankCard.LIMIT_FINLAND;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        viewModel.getClickedBankCard().observe(getViewLifecycleOwner(), new Observer<BankCard>() {
            @Override
            public void onChanged(BankCard bankCard) {
                clickedBankCard = bankCard;

                withdrawLimit = bankCard.getWithdrawLimit();
                paymentLimit = bankCard.getPaymentLimit();
                countryLimit = bankCard.getCountryLimit();

                binding.fragmentBankCardSettingsSwitchFrozen.setChecked(bankCard.isFrozen());
                System.out.println("LOGGER: bankcard:" + bankCard.isFrozen());

                binding.fragmentBankCardSettingsEdittextWithdrawLimit.setText(
                        String.valueOf(withdrawLimit)
                );
                binding.fragmentBankCardSettingsEdittextPaymentLimit.setText(
                        String.valueOf(paymentLimit)
                );

                switch (countryLimit) {
                    case BankCard.LIMIT_FINLAND:
                        binding.fragmentBankCardSettingsSpinnerCountryLimit.setSelection(
                                countryLimitAdapter.getPosition("Finland")
                        );
                        break;
                    case BankCard.LIMIT_NORDIC_COUNTRIES:
                        binding.fragmentBankCardSettingsSpinnerCountryLimit.setSelection(
                                countryLimitAdapter.getPosition("Nordic Countries")
                        );
                        break;
                    case BankCard.LIMIT_EUROPE:
                        binding.fragmentBankCardSettingsSpinnerCountryLimit.setSelection(
                                countryLimitAdapter.getPosition("Europe")
                        );
                        break;
                    case BankCard.LIMIT_WHOLE_WORLD:
                        binding.fragmentBankCardSettingsSpinnerCountryLimit.setSelection(
                                countryLimitAdapter.getPosition("Whole World")
                        );
                        break;

                }
            }
        });

        binding.fragmentBankCardSettingsButtonUpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFormData()) {
                    return;
                }

                clickedBankCard.setCountryLimit(countryLimit);
                clickedBankCard.setFrozen(binding.fragmentBankCardSettingsSwitchFrozen.isChecked());
                clickedBankCard.setPaymentLimit(paymentLimit);
                clickedBankCard.setWithdrawLimit(withdrawLimit);

                dataManager.updateBankCard(clickedBankCard);
                viewModel.setClickedBankCard(clickedBankCard);

                Toast toast = Toast.makeText(getActivity(),"Card updated!" + (clickedBankCard.isFrozen() ? 1 : 0), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

    }

    private boolean validateFormData() {
        boolean valid = true;
        // Withdraw limit check
        String tempString = binding.fragmentBankCardSettingsEdittextWithdrawLimit.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentBankCardSettingsEdittextWithdrawLimit.setError("Non-valid");
            valid = false;
        } else {
            try {
                withdrawLimit = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentBankCardSettingsEdittextWithdrawLimit.setError("Non-valid");
            }
        }
        // Payment limit check
        tempString = binding.fragmentBankCardSettingsEdittextPaymentLimit.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentBankCardSettingsEdittextPaymentLimit.setError("Non-valid");
            valid = false;
        } else {
            try {
                paymentLimit = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentBankCardSettingsEdittextPaymentLimit.setError("Non-valid");
            }
        }

        return valid;
    }
}
