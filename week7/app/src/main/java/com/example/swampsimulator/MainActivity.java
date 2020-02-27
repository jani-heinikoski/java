package com.example.swampsimulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreenSize();
        initElements();
    }

    public void clickButton(View v) {
        txtView.setText(eText.getText());
    }
}
