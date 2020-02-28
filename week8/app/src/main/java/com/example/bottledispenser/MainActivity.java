package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btn_add_coin;
    private Button btn_return_coins;
    private Button btn_buy_bottle;
    private ImageView imgv_logo;
    private TextView txtv_coinsleft;
    private double coins;
    private BottleDispenser bd;
    private MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coins = 20;
        bd = BottleDispenser.getInstance(5, 0, (Spinner)findViewById(R.id.spinner_choose_bottle), getApplicationContext(), (TextView)findViewById(R.id.txtv_coins_inserted));
        declareButtons();
        declareImageViews();
        declareTextViews();
        int resID=getResources().getIdentifier("jugnog", "raw", getPackageName());
        song = MediaPlayer.create(getApplicationContext(), resID);
        song.setLooping(true);
        song.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        song.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        song.start();
    }

    private void addCoinToDispenser() {
        if (coins > 0) {
            coins--;
            bd.addMoney();
            refresh();
        } else {
            System.out.println("LOGGER: Not enough money!");
        }

    }

    private void getMoneyBack() {
        coins += bd.returnMoney();
        refresh();
    }

    private void refresh() {
        txtv_coinsleft.setText(String.format(Locale.GERMANY, "%s %,.2f", getResources().getString(R.string.txtv_bank), coins));
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
        btn_buy_bottle = findViewById(R.id.buy_bottle_button);

        btn_buy_bottle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    btnEffect(btn_buy_bottle);
                    btn_buy_bottle.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    btn_buy_bottle.setTextColor(Color.BLACK);
                    btn_buy_bottle.performClick();
                    btn_buy_bottle.setPressed(false);
                    bd.buySelectedBottle();
                    return false;
                } else {
                    return false;
                }
            }
        });

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
                    getMoneyBack();
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
                    addCoinToDispenser();
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

}
