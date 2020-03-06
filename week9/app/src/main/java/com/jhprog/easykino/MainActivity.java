package com.jhprog.easykino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jhprog.easykino.databinding.ActivityMainBinding;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding binding;
    protected Context context;
    private int firstColIdCounter;
    private int secondColIdCounter;

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        firstColIdCounter = 1;
        secondColIdCounter = 2;

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = getApplicationContext();

        initButtons();

        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));
        binding.relayFirstColumn.addView(newCard(1));
        binding.relaySecondColumn.addView(newCard(2));


        binding.relayFirstColumn.setPadding(25,5,25,0);
        binding.relaySecondColumn.setPadding(25, 5, 25, 0);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        binding.btnSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println("LOGGER: ACTION_DOWN");
                    ViewUtil.btnEffect(binding.btnSearch);
                    binding.btnSearch.setPressed(true);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("LOGGER: ACTION_UP");
                    ViewUtil.remBtnEffect(binding.btnSearch);
                    binding.btnSearch.setTextColor(getColor(R.color.colorTextPrimary));
                    binding.btnSearch.performClick();
                    binding.btnSearch.setPressed(false);
                    searchIntent();
                    /*
                    try {
                        FinnkinoXMLParser.readXMLbyTagNames("https://www.finnkino.fi/xml/TheatreAreas/", "TheatreArea");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    }
                    */
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void searchIntent() {
        startActivityForResult(new Intent(this, SearchActivity.class), 1337);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getStringArrayListExtra("test").isEmpty()) {
            binding.btnSearch.setText("HASKLJAKDLAJS");
        }
    }


    private CardView newCard(int column) {
        CardView cv = new CardView(context);
        setCardViewParams(cv, column);
        TextView tv = newTextViewForCardView(String.format(Locale.getDefault(),"I'm %d years old.", new Random().nextInt(10)));
        cv.addView(tv);
        return cv;
    }

    private void setCardViewParams(CardView cardView, int column) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(4, 15, 4 ,15);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        cardView.setMaxCardElevation(15);
        cardView.setElevation(10);
        cardView.setTranslationZ(10);
        cardView.setCardBackgroundColor(getColor(R.color.colorPrimary));
        cardView.setRadius(15);
        cardView.setMinimumHeight(400);

        if (firstColIdCounter > 1 && column == 1) {
            lp.addRule(RelativeLayout.BELOW, firstColIdCounter);
            firstColIdCounter += 2;
            cardView.setId(firstColIdCounter);
        } else if (firstColIdCounter == 1 && column == 1){
            firstColIdCounter += 2;
            cardView.setId(firstColIdCounter);
        }

        if (secondColIdCounter > 2 && column == 2) {
            lp.addRule(RelativeLayout.BELOW, secondColIdCounter);
            secondColIdCounter += 2;
            cardView.setId(secondColIdCounter);
        } else if (secondColIdCounter == 2 && column == 2){
            secondColIdCounter += 2;
            cardView.setId(secondColIdCounter);
        }

        cardView.setLayoutParams(lp);
    }

    private TextView newTextViewForCardView(CharSequence text) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setTextColor(getColor(R.color.colorTextSecondary));
        return tv;
    }

}
