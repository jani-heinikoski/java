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
import com.jhprog.easykino.databinding.ActivitySearchBinding;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;


import javax.xml.parsers.ParserConfigurationException;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private ArrayAdapter<Theatre> theatreArrayAdapter;
    private ArrayAdapter<String> locationArrayAdapter;
    private ArrayList<Theatre> theatreArrayList;
    private ArrayList<String> locationArrayList;
    private FinnkinoXMLParser parser;
    private TransitionHandler transitionHandler = TransitionHandler.getInstance();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        theatreArrayList = new ArrayList<Theatre>(10);
        locationArrayList = new ArrayList<String>(10);

        initButtons();
        initTheatres();
        initSpinners();
    }

    private void initTimePicker() {
        binding.timePicker.setIs24HourView(true);
        binding.timePicker.setHour(0);
        binding.timePicker.setMinute(0);
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
                    ViewUtil.btnEffect(binding.btnClose);
                    binding.btnClose.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    ViewUtil.remBtnEffect(binding.btnClose);
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
                    ViewUtil.btnEffect(binding.btnApplySearch);
                    binding.btnApplySearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    ViewUtil.remBtnEffect(binding.btnApplySearch);
                    binding.btnApplySearch.setTextColor(getColor(R.color.colorTextSecondary));
                    binding.btnApplySearch.performClick();
                    binding.btnApplySearch.setPressed(false);
                    returnToMainActivity();
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
        transitionHandler.search(theatreArrayList, binding.spinnerTheatres.getSelectedItem().toString(), binding.spinnerLocations.getSelectedItem().toString(), binding.etextDate.toString(), binding.timePicker.getHour(), binding.timePicker.getMinute(), 0, 0);
        finish();
    }

    private Theatre findByNameAndLocation(String n, String l) {
        for (Theatre t : theatreArrayList) {
            if (t.getName().equals(n) && t.getLocation().equals(l)) {
                return t;
            }
        }
        return null;
    }

    private ArrayList<Theatre> findByName(String n) {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>(10);
        for (Theatre t : theatreArrayList) {
            if (t.getName().equals(n)) {
                theatres.add(t);
            }
        }
        return theatres;
    }

    private ArrayList<Theatre> findByLocation(String l) {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>(10);
        for (Theatre t : theatreArrayList) {
            if (t.getLocation().equals(l)) {
                theatres.add(t);
            }
        }
        return theatres;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initSpinners() {
        theatreArrayAdapter = new ArrayAdapter<Theatre>(getApplicationContext(), R.layout.spinner_item, theatreArrayList);
        theatreArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTheatres.setAdapter(theatreArrayAdapter);

        locationArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, locationArrayList);
        locationArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerLocations.setAdapter(locationArrayAdapter);
    }

}
