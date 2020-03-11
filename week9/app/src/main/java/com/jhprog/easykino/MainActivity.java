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
import android.widget.Toast;


import com.jhprog.easykino.databinding.ActivityMainBinding;
import java.util.ArrayList;


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
                    TransitionHandler.btnEffect(binding.btnSearch);
                    binding.btnSearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
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
        this.shows.clear();
        this.shows.addAll(transitionHandler.getShows());
        recyclerAdapter.notifyDataSetChanged();
        binding.recyclerView.smoothScrollToPosition(0);
    }


}
