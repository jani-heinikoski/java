/*
* Author: Jani Olavi Heinikoski
* Date: 03.04.2020
* Version: alpha
* Sources:
* https://material.io/
* https://developer.android.com/topic/libraries/view-binding
* https://developer.android.com/guide/components/fragments
* */
package com.jhprog.dabank.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.R;
import com.jhprog.dabank.databinding.ActivityLoginBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class LoginActivity extends AppCompatActivity implements IBankChosenListener {

    private LoginViewModel loginViewModel;
    private Animation onClickAnimation;
    private ActivityLoginBinding binding;
    private FragmentManager fragmentManager;
    private DataManager dataManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize loginViewModel to LoginActivity's scope
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // Initialize UI components
        onClickAnimation = AnimationProvider.getOnClickAnimation();
        initButtons();
        initFragments();
        // Initialize the DataManager Singleton in order to authenticate user login
        dataManager = DataManager.getInstance();
    }

    // Initializes all buttons in LoginActivity
    private void initButtons() {
        binding.loginActivityExpandBanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loginActivityExpandBanksButton.startAnimation(onClickAnimation);
            }
        });

        binding.loginActivityExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loginActivityExitButton.startAnimation(onClickAnimation);
            }
        });
    }

    // Initializes all fragments in LoginActivity
    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        // Display ChooseBankFragment inside of login_activity_fragment_container
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_activity_fragment_container,
                new ChooseBankFragment(), "chooseBankFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onChoose() {
        // Replace choose bank fragment with login fragment and save the transaction to back stack.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_activity_fragment_container, new LoginFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
