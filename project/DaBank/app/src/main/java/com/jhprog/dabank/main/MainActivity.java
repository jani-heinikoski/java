/*
 * Author: Jani Olavi Heinikoski
 * Date: 07.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/training/basics/firstapp/starting-activity
 * */
package com.jhprog.dabank.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.databinding.ActivityMainBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private FragmentManager fragmentManager;
    private Animation onClickAnimation;
    private Bank bank;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize mainViewModel to LoginActivity's scope
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // Get the intent used to start this activity and construct the passed bank
        Intent intent = getIntent();
        bank = new Bank(intent.getIntExtra("b_id", 0), intent.getStringExtra("b_name"));
        // Handle error in passing bank by value
        if (bank.getId() == 0) {
            // TODO Handle error in passing bank byVal
            finish();
        }
        binding.mainActivityHeaderText.setText(bank.getName());
        // Initialize UI components
        onClickAnimation = AnimationProvider.getOnClickAnimation();
        initButtons();
        initFragments();
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        // Display MainFragment inside of main_activity_fragment_container
        



    }

    private void initButtons() {
        binding.mainActivityExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainActivityExitButton.startAnimation(onClickAnimation);
            }
        });
    }

}
