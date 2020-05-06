package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.dabank.R;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBankCardSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        countryLimitAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                countryChoices);
        countryLimitAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentBankCardSettingsSpinnerCountryLimit.setAdapter(countryLimitAdapter);

        viewModel.getClickedBankCard().observe(getViewLifecycleOwner(), new Observer<BankCard>() {
            @Override
            public void onChanged(BankCard bankCard) {
                clickedBankCard = bankCard;
                withdrawLimit = bankCard.getWithdrawLimit();
                paymentLimit = bankCard.getPaymentLimit();
                countryLimit = bankCard.getCountryLimit();

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

            }
        });

    }

    private boolean validateFormData() {
        // TODO: 06/05/2020 this
        return true;

    }
}
