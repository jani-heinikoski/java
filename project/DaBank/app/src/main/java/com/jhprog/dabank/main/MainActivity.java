/*
 * Author: Jani Olavi Heinikoski
 * Date: 07.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/training/basics/firstapp/starting-activity
 * */
package com.jhprog.dabank.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.ActivityMainBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public class MainActivity extends AppCompatActivity implements IFragmentOwner {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private FragmentManager fragmentManager;
    private Animation onClickAnimation;
    private DataManager dataManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getInstance();
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize mainViewModel to LoginActivity's scope
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // Get the intent used to start this activity and construct the passed bank
        Intent intent = getIntent();
        mainViewModel.setBank(
                new Bank(intent.getIntExtra("b_id", 0), intent.getStringExtra("b_name"))
        );
        mainViewModel.setCustomer(dataManager.getCustomerByID(
                intent.getIntExtra("cust_id", 0)
        ));
        binding.mainActivityHeaderText.setText(mainViewModel.getBank().getValue().getName());
        // Initialize UI components
        onClickAnimation = AnimationProvider.getOnClickAnimation();
        initButtons();
        initFragments();
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        // Display MainFragment inside of main_activity_fragment_container
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container,
                new MainFragment());
        fragmentTransaction.commit();
    }

    private void initButtons() {
        binding.mainActivityExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainActivityExitButton.startAnimation(onClickAnimation);
            }
        });
    }

    @Override
    public void changeFragment(Fragment newFragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, newFragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
