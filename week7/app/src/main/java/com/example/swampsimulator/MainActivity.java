/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 7.4.0
 */
package com.example.swampsimulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView txtView;
    private EditText eText;
    private int w;
    private int h;

    private TextWatcher tw;

    private void initScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
    }

    private void initElements() {
        btn = (Button) findViewById(R.id.btnApply);
        btn.setWidth(this.w-32);
        btn.setHeight(this.h/10);

        txtView = (TextView) findViewById(R.id.textView);
        eText = (EditText) findViewById(R.id.inputText);

        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextToDisplay();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        eText.addTextChangedListener(tw);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreenSize();
        initElements();
    }

    private void setTextToDisplay() {
        txtView.setText(eText.getText());
    }

    public void clickButton(View v) {
        setTextToDisplay();
    }
}
