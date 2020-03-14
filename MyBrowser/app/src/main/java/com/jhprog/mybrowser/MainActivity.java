package com.jhprog.mybrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jhprog.mybrowser.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String startURL = "file:///android_asset/index.html";
    private String currentURL;
    private ListIterator iterator;
    private ActivityMainBinding binding;
    private ArrayList<String> searchHistory;
    Animation anim;

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        searchHistory = new ArrayList<>();
        searchHistory.add(startURL);
        iterator = null;
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_down);
        currentURL = startURL;


        initWebView();
        initButtons();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(startURL);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchButton.startAnimation(anim);
                String url = parseURL();
                int i;
                if (!url.isEmpty()) {
                    currentURL = url;
                    if (iterator != null) {
                        if (iterator.hasNext()) {
                            i = iterator.nextIndex();
                            for (int j = i; j < searchHistory.size(); j++) {
                                searchHistory.remove(j);
                            }
                        }
                        iterator = null;
                        searchHistory.add(url);
                    } else {
                        searchHistory.add(url);
                    }
                    binding.webView.loadUrl(url);
                    currentURL = url;
                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.backButton.startAnimation(anim);
                back();
            }
        });

        binding.forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.forwardButton.startAnimation(anim);
                forward();
            }
        });
    }

    private String parseURL() {
        String url = binding.mySearchBar.getText().toString().trim();

        if (url.isEmpty()) {
            return "";
        } else {
            if (url.contains("http://")) {
                return url;
            } else {
                return String.format(Locale.getDefault(),"http://%s", url);
            }
        }

    }

    private void back() {

        if (iterator == null) {
            iterator = searchHistory.listIterator(searchHistory.size());
        }

        if (iterator.hasPrevious()) {
            String url = iterator.previous().toString();
            if (url.equals(currentURL) && iterator.hasPrevious()) {
                url = iterator.previous().toString();
            }
            System.out.println("LOGGER: Pressed back: " + url);
            binding.webView.loadUrl(url);
            currentURL = url;
        }

        for (String s : searchHistory) {
            System.out.println(s);
        }
    }

    private void forward() {

        if (iterator == null) {
            iterator = searchHistory.listIterator(0);
        }

        if (iterator.hasNext()) {
            String url = iterator.next().toString();
            if (currentURL.equals(url) && iterator.hasNext()) {
                url = iterator.next().toString();
            }
            System.out.println("LOGGER: Pressed forward: " + url);
            binding.webView.loadUrl(url);
            currentURL = url;
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
