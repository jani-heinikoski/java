package com.jhprog.easykino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    private ArrayAdapter<Theatre> adapter;
    private ArrayList<Theatre> data;
    private ArrayList<String> params;
    private FinnkinoXMLParser parser;
    private String intentArrayName;
    private int requestCode;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        intentArrayName = "test";
        requestCode = 1337;
        data = new ArrayList<Theatre>(10);
        //TODO adapter.notify();
        initButtons();
        initTheatres();
        initSpinners();
    }

    private void initTheatres() {
        try {
            parser = FinnkinoXMLParser.getInstance();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            data = parser.getTheatres();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        binding.btnClose.setOnTouchListener(new View.OnTouchListener() {
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
        params = new ArrayList<String>(5);
        params.add(binding.spinnerTheatres.getSelectedItem().toString());
        intent.putStringArrayListExtra(intentArrayName, params);
        setResult(requestCode, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initSpinners() {
        adapter = new ArrayAdapter<Theatre>(getApplicationContext(), R.layout.spinner_item, data);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTheatres.setAdapter(adapter);
    }

}
