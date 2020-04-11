/*
 * Author: Jani Olavi Heinikoski
 * Date: 09.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.Transaction;
import com.jhprog.dabank.databinding.FragmentNewPaymentBinding;
import com.jhprog.dabank.utility.AnimationProvider;
import com.jhprog.dabank.utility.TimeManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPaymentFragment extends Fragment {

    private TimeManager timeManager;
    private FragmentNewPaymentBinding binding;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeManager = TimeManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewPaymentBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.fragmentNewPaymentButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                binding.fragmentNewPaymentButtonContinue.startAnimation(AnimationProvider.getOnClickAnimation());
                // trans_id and trans_date_time can be anything when object is used for insertTransaction -function
                Transaction transaction = new Transaction(
                    0,
                    Transaction.TYPE_PAYMENT,
                    "'MAKSAJA'",
                    "'MAKSUNSAAJA'",
                    0,
                    "'" + formatter.format(new Date(binding.fragmentNewPaymentCalendarviewDueDate.getDate())) + "'",
                    Double.parseDouble(binding.fragmentNewPaymentEdittextAmount.getText().toString()),
                    1
                );

                dataManager.insertTransaction(transaction);
            }
        });
    }

    @Override
    public void onDestroy() {
        dataManager.closeDatabaseConnection();
        super.onDestroy();
    }
}
