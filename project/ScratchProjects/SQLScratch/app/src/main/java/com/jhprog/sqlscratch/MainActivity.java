package com.jhprog.sqlscratch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jhprog.sqlscratch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initButtons();

    }

    private void initButtons() {

        binding.executeQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });

    }

    private void setResult() {
        String queryString = binding.editQuery.getText().toString();
        binding.resultTextview.setText("RESULT: " + queryString);
    }
}
