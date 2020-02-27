/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 7.5.0
 */
package com.example.swampsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnRead;
    private Button btnWrite;
    private TextView txtView;
    private EditText etFileName;
    private EditText etTextToWrite;
    private TextWatcher tw;
    private Context c;
    private FileManager fm;

    private void initElements() {

        btnRead = (Button) findViewById(R.id.btnReadFile);
        btnWrite = (Button) findViewById(R.id.btnWriteFile);

        c = getApplicationContext();

        txtView = (TextView) findViewById(R.id.textView);
        etFileName = (EditText) findViewById(R.id.et_filename);
        etTextToWrite = (EditText) findViewById(R.id.et_text_to_write);

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

        etFileName.addTextChangedListener(tw);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        fm = new FileManager(c);
    }

    private void setTextToDisplay() {
        txtView.setText(etFileName.getText());
    }

    public void readFile(View v) {
        ArrayList<String> fileContent = fm.readFile(etFileName.getText().toString());
        String output = "";

        for (String s : fileContent) {
            System.out.println("LOGGER: " + s);
            output += s;
        }

        txtView.setText(output);
    }

    public void writeFile(View v) {
        fm.writeFile(etFileName.getText().toString(), etTextToWrite.getText().toString());
    }

}
