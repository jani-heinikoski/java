/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.4
 */

package com.jhprog.menudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.navigation.NavigationView;
import com.jhprog.menudemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainFragment mainFragment;
    private SettingsFragment settingsFragment;
    private SharedViewModel viewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.mainDrawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        setTitle("Main View");

        initFragments();
        initNavButtons();
    }

    private void initFragments() {
        mainFragment = new MainFragment();
        settingsFragment = new SettingsFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mainFragment, "main").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                settingsFragment, "settings").commit();

        getSupportFragmentManager().beginTransaction().hide(settingsFragment).commit();
    }

    private void initNavButtons() {
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_main:
                        if (getSupportFragmentManager().findFragmentByTag("settings") != null) {
                            getSupportFragmentManager().beginTransaction().hide(settingsFragment).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("main") != null) {
                            getSupportFragmentManager().beginTransaction().show(mainFragment).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment, "main");
                        }
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                        setTitle("Main View");
                        break;
                    case R.id.nav_settings:
                        if (getSupportFragmentManager().findFragmentByTag("main") != null) {
                            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
                        }
                        if (getSupportFragmentManager().findFragmentByTag("settings") != null) {
                            getSupportFragmentManager().beginTransaction().show(settingsFragment).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, settingsFragment, "settings");
                        }
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                        setTitle("Settings");
                        break;
                    case R.id.nav_close:
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        binding.navigation.setCheckedItem(R.id.nav_main);
    }

    @Override
    public void onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
