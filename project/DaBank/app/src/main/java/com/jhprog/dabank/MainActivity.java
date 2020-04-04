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

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jhprog.dabank.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Animation onClickScaleAnim;

    private ActivityMainBinding binding;

    private ChooseBankFragment chooseBankFragment; // TODO Check if needed
    private LoginFragment loginFragment; // TODO Check me too
    private FragmentManager fragmentManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initAnims();
        initButtons();
        initFragments();
    }

    private void initAnims() {
        // Get the animation from resources and initialize it
        onClickScaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_down_animation);
        onClickScaleAnim.setDuration(100);
        onClickScaleAnim.setRepeatCount(1);
        onClickScaleAnim.setRepeatMode(Animation.REVERSE);
    }

    // Initializes all buttons in MainActivity
    private void initButtons() {
        binding.mainActivityExpandBanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainActivityExpandBanksButton.startAnimation(onClickScaleAnim);
                // TODO replace code below
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_fragment_container, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    // Initializes all fragments in MainActivity
    private void initFragments() {
        // Maintain reference to chooseBankFragment and loginFragment
        chooseBankFragment = new ChooseBankFragment();
        loginFragment = new LoginFragment();

        fragmentManager = getSupportFragmentManager();
        // Display chooseBankFragment inside of main_activity_fragment_container
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container,
                chooseBankFragment, "chooseBankFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        /*
        // Create new fragment and transaction
        Fragment newFragment = new ExampleFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
         */
    }

}
