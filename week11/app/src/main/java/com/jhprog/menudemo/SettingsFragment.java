/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.7
 */
package com.jhprog.menudemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jhprog.menudemo.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SharedViewModel viewModel;

    private ArrayList<String> fonts;
    private ArrayAdapter<String> fontAdapter;

    private ArrayList<String> colors;
    private ArrayAdapter<String> colorAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFonts();
        initColors();
        initSpinners();
        initSwitches();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    private void initSwitches() {
        binding.visibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setTextVisible(isChecked);
            }
        });

        binding.allCapsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setTextAllCaps(isChecked);
            }
        });

        binding.editableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setTextEditable(isChecked);
            }
        });
    }

    private void initFonts() {
        fonts = new ArrayList<>();
        for (int i = 24; i <= 32; i++) {
            fonts.add(String.format(Locale.getDefault(), "%d", i));
        }
    }

    private void initColors() {
        colors = new ArrayList<>();
        colors.add("Blue");
        colors.add("Purple");
        colors.add("Black");
        colors.add("Red");
    }

    private void initSpinners() {
        // Adapter for font size spinner
        fontAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                                android.R.layout.simple_spinner_dropdown_item, fonts);
        fontAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        binding.fontSizeSpinner.setAdapter(fontAdapter);
        // Adapter for font color spinner
        colorAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, colors);
        colorAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        binding.fontColorSpinner.setAdapter(colorAdapter);
        // Item selected listener for color spinner
        binding.fontColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setFontColor(getColour());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                viewModel.setFontColor(R.color.navy);
            }
        });
        // Item selected listener for font size spinner
        binding.fontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setFontSize(getFontSize());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                viewModel.setFontSize(24);
            }
        });
    }

    private int getFontSize() {
        int fontSize;
        String chosenElement;
        Object element = binding.fontSizeSpinner.getSelectedItem();
        if (element != null) {
            chosenElement = element.toString();
        } else {
            return 24;
        }
        fontSize = Integer.parseInt(chosenElement);
        return fontSize;
    }

    private int getColour() {
        int color;
        String chosenElement;
        Object element = binding.fontColorSpinner.getSelectedItem();
        if (element != null) {
            chosenElement = element.toString().toLowerCase();
        } else {
            return getActivity().getColor(R.color.navy);
        }

        if (chosenElement.equals("blue")) {
            color = R.color.blue;
        } else if (chosenElement.equals("red")) {
            color = R.color.red;
        } else if (chosenElement.equals("black")) {
            color = R.color.black;
        } else if (chosenElement.equals("purple")) {
            color = R.color.purple;
        } else {
            color = R.color.navy;
        }

        return getActivity().getColor(color);
    }

}
