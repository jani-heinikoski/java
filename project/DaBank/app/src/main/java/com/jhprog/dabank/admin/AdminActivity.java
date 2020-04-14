/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.jhprog.dabank.R;
import com.jhprog.dabank.databinding.ActivityAdminBinding;
import com.jhprog.dabank.main.IFragmentOwner;

public class AdminActivity extends AppCompatActivity implements IFragmentOwner {

    private ActivityAdminBinding binding;
    // TODO Replace with AdminViewModel
    private static int b_id;
    private static int cust_id;
    private FragmentManager fragmentManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disables screen rotation (locked to portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        b_id = intent.getIntExtra("b_id", 1);
        cust_id = intent.getIntExtra("cust_id", 1);
        initFragments();
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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

    public static int getB_id() {
        return b_id;
    }
    public static int getCust_id() {
        return cust_id;
    }
}
