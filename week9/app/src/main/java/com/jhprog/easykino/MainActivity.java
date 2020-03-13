package com.jhprog.easykino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import com.jhprog.easykino.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    protected ActivityMainBinding binding;
    protected Context context;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Show> shows;
    private BackgroundWorker backgroundWorker;
    private ShowChangedObservable sco;

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        backgroundWorker = BackgroundWorker.getInstance();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = getApplicationContext();

        initButtons();
        initRecycler();

        sco = ShowChangedObservable.getInstance();
        sco.addObserver(this);

    }

    private void initRecycler() {
        shows = new ArrayList<>();

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
                    btnEffect(binding.btnSearch);
                    binding.btnSearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    remBtnEffect(binding.btnSearch);
                    binding.btnSearch.setTextColor(getColor(R.color.colorTextPrimary));
                    binding.btnSearch.performClick();
                    binding.btnSearch.setPressed(false);
                    if (backgroundWorker.isFinished()) {
                        searchIntent();
                    } else {
                        Toast.makeText(context, "Wait for background worker to finish...", Toast.LENGTH_SHORT).show();
                    }

                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void searchIntent() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public static void remBtnEffect(Button b) {
        b.setHeight(b.getHeight() - 25);
    }

    public static void btnEffect(Button b) {
        b.setHeight(b.getHeight() + 25);
    }

    @Override
    public void update(Observable o, Object arg) {
        shows.clear();
        shows.addAll(backgroundWorker.getShows());
        recyclerAdapter.notifyDataSetChanged();
    }
}
