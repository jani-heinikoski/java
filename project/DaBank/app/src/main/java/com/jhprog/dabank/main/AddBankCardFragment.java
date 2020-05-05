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
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.CurrentAccount;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentAddBankCardBinding;
import com.jhprog.dabank.utility.AnimationProvider;

import java.util.Objects;

import static com.jhprog.dabank.data.BankCard.NEVER_USED;
import static com.jhprog.dabank.data.BankCard.TYPE_CREDIT;
import static com.jhprog.dabank.data.BankCard.TYPE_DEBIT;

public final class AddBankCardFragment extends Fragment {

    private FragmentAddBankCardBinding binding;
    private MainViewModel viewModel;
    private Account ownerAccount;

    private ArrayAdapter<String> countryLimitAdapter;
    private final String[] countryChoices = {"Finland", "Nordic Countries", "Europe", "Whole World"};

    private double withdrawLimit, paymentLimit;
    private String cardNumber;
    private int countryLimit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddBankCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryLimitAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(),
                R.layout.fragment_new_payment_spinner_item,
                countryChoices);
        countryLimitAdapter.setDropDownViewResource(R.layout.fragment_new_payment_spinner_dropdown_item);
        binding.fragmentAddBankCardSpinnerCountryLimit.setAdapter(countryLimitAdapter);

        binding.fragmentAddBankCardSpinnerCountryLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lim = countryLimitAdapter.getItem(position);
                if (lim != null) {
                    switch (lim) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryLimit = BankCard.LIMIT_WHOLE_WORLD;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        viewModel.getClickedAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                ownerAccount = account;
            }
        });

        binding.fragmentAddBankCardButtonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentAddBankCardButtonAddCard.startAnimation(AnimationProvider.getOnClickAnimation());

                if (!validateFormData()) {
                    return;
                }

                int cardType = (ownerAccount instanceof CurrentAccount && ((CurrentAccount) ownerAccount).getAcc_creditlimit() > 0)
                                ? TYPE_CREDIT : TYPE_DEBIT;

                DataManager.getInstance().insertBankCard(
                    new BankCard(
                        cardType,
                        ownerAccount.getAcc_id(),
                        countryLimit,
                        withdrawLimit,
                        paymentLimit,
                        0,
                        0,
                        NEVER_USED,
                        NEVER_USED,
                        cardNumber,
                        false
                    )
                );

                Toast toast = Toast.makeText(getActivity(),"Card added!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


    }

    private boolean validateFormData() {
        boolean valid = true;
        // Card number check
        cardNumber = binding.fragmentAddBankCardEdittextCardNumber.getText().toString().trim();
        if (cardNumber.length() != 16 || cardNumber.matches("['\";]")) {
            valid = false;
            binding.fragmentAddBankCardEdittextCardNumber.setError("Must be 16 digits");
        } else {
            for (char c : cardNumber.toCharArray()) {
                if (!Character.isDigit(c)) {
                    valid = false;
                    binding.fragmentAddBankCardEdittextCardNumber.setError("Must be 16 digits");
                    break;
                }
            }
        }
        // Withdraw limit check
        String tempString = binding.fragmentAddBankCardEdittextWithdrawLimit.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentAddBankCardEdittextWithdrawLimit.setError("Non-valid");
            valid = false;
        } else {
            try {
                withdrawLimit = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentAddBankCardEdittextWithdrawLimit.setError("Non-valid");
            }
        }
        // Payment limit check
        tempString = binding.fragmentAddBankCardEdittextPaymentLimit.getText().toString().trim();
        if (tempString.isEmpty() || tempString.matches("[A-Z]|[a-z]")) {
            binding.fragmentAddBankCardEdittextPaymentLimit.setError("Non-valid");
            valid = false;
        } else {
            try {
                paymentLimit = Double.parseDouble(tempString);
            } catch (Exception e) {
                valid = false;
                binding.fragmentAddBankCardEdittextPaymentLimit.setError("Non-valid");
            }
        }

        return valid;
    }
}
