package com.jhprog.easykino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jhprog.easykino.databinding.ActivityMainBinding;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding binding;
    protected Context context;
    private TransitionHandler transitionHandler = TransitionHandler.getInstance();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Show> shows;

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = getApplicationContext();

        initButtons();
        initRecycler();

    }

    private void initRecycler() {
        shows = new ArrayList<>(1);
        shows.add(new Show(1337, 6969, "Transformers 3", "2.3.2020", "10:20"));

        // Increases performance (use when recyclerview has fixed size)
        binding.recyclerView.setHasFixedSize(true);

        // specify a layout manager
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        recyclerAdapter = new ShowAdapter(shows);
        binding.recyclerView.setAdapter(recyclerAdapter);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        binding.btnSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    TransitionHandler.btnEffect(binding.btnSearch);
                    binding.btnSearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    TransitionHandler.remBtnEffect(binding.btnSearch);
                    binding.btnSearch.setTextColor(getColor(R.color.colorTextPrimary));
                    binding.btnSearch.performClick();
                    binding.btnSearch.setPressed(false);
                    searchIntent();
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void searchIntent() {
        startActivityForResult(new Intent(this, SearchActivity.class), TransitionHandler.getResultCode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
