package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_add_coin;
    private Button btn_return_coins;
    private ImageView imgv_logo;
    private TextView txtv_coinsleft;
    private double coins;
    private BottleDispenser bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coins = 50;
        declareButtons();
        declareImageViews();
        declareTextViews();
    }

    @SuppressLint("DefaultLocale")
    private void declareTextViews() {
        txtv_coinsleft = findViewById(R.id.txtv_coins_left);
        txtv_coinsleft.setText(String.format("%s %,.2f", getResources().getString(R.string.txtv_bank), coins));

    }

    private void declareImageViews() {
        imgv_logo = findViewById(R.id.logo_imageview);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.logo_rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        imgv_logo.startAnimation(rotation);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void declareButtons() {
        btn_add_coin = findViewById(R.id.add_coin_button);
        btn_return_coins = findViewById(R.id.return_coins_button);

        btn_return_coins.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    btnEffect(btn_return_coins);
                    btn_return_coins.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    btn_return_coins.setTextColor(Color.BLACK);
                    btn_return_coins.performClick();
                    btn_return_coins.setPressed(false);
                    return false;
                } else {
                    return false;
                }
            }
        });

        btn_add_coin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    btnEffect(btn_add_coin);
                    btn_add_coin.setPressed(true);
                    return true;

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    btn_add_coin.setTextColor(Color.BLACK);
                    btn_add_coin.performClick();
                    btn_add_coin.setPressed(false);
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void btnEffect(Button b) {
        b.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void addCoin(View v) {

    }
}
