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

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> data;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        data = new ArrayList<String>(25);
        data.add("Hello");
        data.add("Pussy");
        initButtons();
        initSpinners();
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

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    ArrayList<String> test = new ArrayList<>(2);
                    intent.putStringArrayListExtra("test", test);
                    setResult(1337, intent);
                    finish();
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

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    ArrayList<String> test = new ArrayList<String>(2);
                    intent.putStringArrayListExtra("test", test);
                    setResult(1337, intent);
                    finish();
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initSpinners() {
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, data);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerTheatres.setAdapter(adapter);
    }

}
