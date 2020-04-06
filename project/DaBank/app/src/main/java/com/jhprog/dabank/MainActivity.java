/*
* Author: Jani Olavi Heinikoski
* Date: 03.04.2020
* Version: alpha
* Sources:
* https://material.io/
* https://developer.android.com/training/data-storage/sqlite
* */
package com.jhprog.dabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jhprog.dabank.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoginViewModel.IBankChosenCallback {

    private LoginViewModel loginViewModel;
    private Animation onClickScaleAnim;
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;
    private DataManager dataManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize loginViewModel to MainActivity's scope
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // Initialize UI components
        initAnims();
        initButtons();
        initFragments();
        // Initialize the DataManager Singleton in order to authenticate user login
        dataManager = DataManager.getInstance();
    }

    private void initAnims() {
        // Get the onClickAnimation from resources and initialize it
        onClickScaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_down_animation);
        onClickScaleAnim.setDuration(100);
        // Plays backwards after completion
        onClickScaleAnim.setRepeatCount(1);
        onClickScaleAnim.setRepeatMode(Animation.REVERSE);
    }

    // Initializes all buttons in MainActivity
    private void initButtons() {
        binding.mainActivityExpandBanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainActivityExpandBanksButton.startAnimation(onClickScaleAnim);
            }
        });
    }

    // Initializes all fragments in MainActivity
    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        // Display chooseBankFragment inside of main_activity_fragment_container
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container,
                new ChooseBankFragment(), "chooseBankFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onChoose() {
        // Replace choose bank fragment with login fragment and save the transaction to back stack.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, new LoginFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
