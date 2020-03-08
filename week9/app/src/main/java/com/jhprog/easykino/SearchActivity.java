package com.jhprog.easykino;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.jhprog.easykino.databinding.ActivitySearchBinding;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import javax.xml.parsers.ParserConfigurationException;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private ArrayAdapter<String> theatreNameArrayAdapter;
    private ArrayAdapter<String> locationArrayAdapter;
    private ArrayList<Theatre> theatreArrayList;
    private ArrayList<String> theatreNameArrayList;
    private ArrayList<String> locationArrayList;
    private FinnkinoXMLParser parser;
    private TransitionHandler transitionHandler = TransitionHandler.getInstance();
    private String date;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        theatreArrayList = new ArrayList<Theatre>(10);
        locationArrayList = new ArrayList<String>(10);
        theatreNameArrayList = new ArrayList<String>(10);

        initTimePicker();
        initDatePicker();
        initButtons();
        initTheatres();
        initSpinners();
    }

    private void initDatePicker() {
        int year = 0;
        int month = 0;
        int day = 0;

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        date = String.format(Locale.getDefault(), "%d.%d.%d", day, (month + 1), year);

        binding.datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = String.format(Locale.getDefault(), "%d.%d.%d", dayOfMonth, monthOfYear, year);
            }
        });
    }

    private void initTimePicker() {
        binding.startTimePicker.setBackgroundColor(getColor(R.color.colorPrimary));
        binding.startTimePicker.setIs24HourView(true);
        binding.startTimePicker.setHour(0);
        binding.startTimePicker.setMinute(0);

        binding.endTimePicker.setBackgroundColor(getColor(R.color.colorPrimary));
        binding.endTimePicker.setIs24HourView(true);
        binding.endTimePicker.setHour(0);
        binding.endTimePicker.setMinute(0);
    }

    private void initTheatres() {
        try {
            parser = FinnkinoXMLParser.getInstance();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            theatreArrayList = parser.getTheatres();
            theatreArrayList.add(0, new Theatre(1, "All", "All"));

            for (Theatre t : theatreArrayList) {
                if (!locationArrayList.contains(t.getLocation())) {
                    locationArrayList.add(t.getLocation());
                }
                if (!theatreNameArrayList.contains(t.getName())) {
                    theatreNameArrayList.add(t.getName());
                }
            }

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        binding.btnClose.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    TransitionHandler.btnEffect(binding.btnClose);
                    binding.btnClose.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    TransitionHandler.remBtnEffect(binding.btnClose);
                    binding.btnClose.setTextColor(getColor(R.color.colorTextPrimary));
                    binding.btnClose.performClick();
                    binding.btnClose.setPressed(false);
                    returnToMainActivity();
                    return false;
                } else {
                    return false;
                }
            }
        });

        binding.btnApplySearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    TransitionHandler.btnEffect(binding.btnApplySearch);
                    binding.btnApplySearch.setTextColor(getColor(R.color.colorTextSecondary));
                    binding.btnApplySearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    TransitionHandler.remBtnEffect(binding.btnApplySearch);
                    binding.btnApplySearch.setTextColor(getColor(R.color.colorTextPrimary));
                    binding.btnApplySearch.performClick();
                    binding.btnApplySearch.setPressed(false);
                    transitionHandler.search(theatreArrayList, binding.spinnerTheatres.getSelectedItem().toString(), binding.spinnerLocations.getSelectedItem().toString(), date, binding.startTimePicker.getHour(), binding.startTimePicker.getMinute(), binding.endTimePicker.getHour(), binding.endTimePicker.getMinute());
                    //returnToMainActivity();
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(TransitionHandler.getResultCode(), intent);
        transitionHandler.search(theatreArrayList, binding.spinnerTheatres.getSelectedItem().toString(), binding.spinnerLocations.getSelectedItem().toString(), date, binding.startTimePicker.getHour(), binding.startTimePicker.getMinute(), binding.endTimePicker.getHour(), binding.endTimePicker.getMinute());
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initSpinners() {
        theatreNameArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, theatreNameArrayList);
        theatreNameArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTheatres.setAdapter(theatreNameArrayAdapter);

        locationArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, locationArrayList);
        locationArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerLocations.setAdapter(locationArrayAdapter);
    }

}
