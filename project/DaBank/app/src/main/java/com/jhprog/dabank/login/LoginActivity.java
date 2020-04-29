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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.R;
import com.jhprog.dabank.databinding.ActivityLoginBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class LoginActivity extends AppCompatActivity implements IFragmentOwner {

    private LoginViewModel loginViewModel;
    private Animation onClickAnimation;
    private ActivityLoginBinding binding;
    private FragmentManager fragmentManager;

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
                onBackPressed();
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

    @Override // Fragments use this callback to switch to a new fragment
    public void changeFragment(Fragment newFragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_activity_fragment_container, newFragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

}
