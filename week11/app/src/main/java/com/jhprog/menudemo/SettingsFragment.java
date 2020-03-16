/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.4
 */
package com.jhprog.menudemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.menudemo.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private ArrayList<String> fonts;
    private ArrayAdapter<String> fontAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initFonts();
        initSpinners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO RESUMED
    }

    private void initFonts() {
        fonts = new ArrayList<>();
        for (int i = 24; i <= 32; i++) {
            fonts.add(String.format(Locale.getDefault(), "%d", i));
        }
    }

    private void initSpinners() {
        fontAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                                android.R.layout.simple_spinner_dropdown_item, fonts);
        fontAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        binding.fontSizeSpinner.setAdapter(fontAdapter);
    }

    public int getFontSize() {
        int fontSize;
        String fontSizeAsString = binding.fontSizeSpinner.getSelectedItem().toString();
        fontSize = Integer.parseInt(fontSizeAsString);
        return fontSize;
    }

}
