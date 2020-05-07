/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

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

import com.jhprog.dabank.R;
import com.jhprog.dabank.databinding.ActivityAdminBinding;
import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.utility.AnimationProvider;

public class AdminActivity extends AppCompatActivity implements IFragmentOwner {

    private ActivityAdminBinding binding;
    private FragmentManager fragmentManager;
    private AdminViewModel viewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        initViewModel(
                intent.getIntExtra("b_id", 1),
                intent.getIntExtra("cust_id", 1)
        );
        initFragments();
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        initButtons();
        setContentView(binding.getRoot());
    }

    private void initButtons() {
        binding.adminActivityButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.adminActivityButtonExit.startAnimation(AnimationProvider.getOnClickAnimation());
                onBackPressed();
            }
        });
    }

    private void initViewModel(int b_id, int customer_id) {
        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        viewModel.setBank_id(b_id);
        viewModel.setCustomer_id(customer_id);
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        // Display MainFragment inside of main_activity_fragment_container
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_activity_fragment_container,
                new NavigationFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment(Fragment newFragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_activity_fragment_container, newFragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

}
