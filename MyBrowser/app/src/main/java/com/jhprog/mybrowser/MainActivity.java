/*
Author: Jani Heinikoski
Date: 14.3.2020
Version: 1.3 [first working]
 */
package com.jhprog.mybrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebViewClient;
import com.jhprog.mybrowser.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String startURL = "file:///android_asset/index.html";
    private ActivityMainBinding binding;
    private SearchHistory searchHistory;
    private boolean clicked;
    private Animation anim;

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

        searchHistory = new SearchHistory();
        searchHistory.add(startURL);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_down);

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
                if (!url.isEmpty()) {
                    searchHistory.add(url);
                    binding.webView.loadUrl(url);
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

        binding.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.refreshButton.startAnimation(anim);
                binding.webView.loadUrl(searchHistory.getCurrentURL());
            }
        });

        binding.invokeJavascriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.invokeJavascriptButton.startAnimation(anim);
                if (searchHistory.getCurrentURL().equals(startURL)) {
                    if (clicked) {
                        binding.webView.evaluateJavascript("javascript:initialize()", null);
                        clicked = false;
                    } else {
                        binding.webView.evaluateJavascript("javascript:shoutOut()", null);
                        clicked = true;
                    }

                }
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
                if (url.equals("index.html")) {
                    return startURL;
                }
                return String.format(Locale.getDefault(),"http://%s", url);
            }
        }

    }

    private void back() {
        if (searchHistory.hasPrevious()) {
            binding.webView.loadUrl(searchHistory.previous());
        }
    }

    private void forward() {
        if (searchHistory.hasNext()) {
            binding.webView.loadUrl(searchHistory.next());
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

}
