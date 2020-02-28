/*
Author: Jani Heinikoski | 0541122
Date: 28.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 8.5.0
 */
package com.example.bottledispenser;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;


public class BottleDispenser {
    // Amount of bottles
    private int bottles;
    // The ArrayList for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private ArrayAdapter<Bottle> adapter;
    private Spinner spinner;
    private Context context;
    private TextView textViewInserted;
    private TextView textViewLogger;
    private double money;
    private static BottleDispenser bd = null;

    public static BottleDispenser getInstance(int bottles, double money, Spinner sp, Context c, TextView t, TextView l) {
        if (bd == null) {
            bd = new BottleDispenser(bottles, money, sp, c, t, l);
        }
        return bd;
    }

    private BottleDispenser(int bottles, double money, Spinner sp, Context c, TextView t, TextView l) {
        this.bottles = bottles;
        this.money = money;
        this.spinner = sp;
        this.context = c;
        this.textViewInserted = t;
        this.textViewLogger = l;
        // Initialize the array
        this.bottleArrayList = new ArrayList<Bottle>(this.bottles);
        // Populate ArrayList
        for (int i = 0; i < this.bottles; i++) {
            this.bottleArrayList.add(new Bottle("Pepsi Max", "Pepsi", 0.3, 0.5, (i+1)));
        }
        // Initialize ArrayAdapter for the Spinner
        this.adapter = new ArrayAdapter<Bottle>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, this.bottleArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
    }

    public void addBottle(String name, String manufacturer, double totalEnergy, double bottleSize, double bottlePrice) {
        // Add bottles to the Dispenser
        this.bottleArrayList.add(new Bottle(name, manufacturer, totalEnergy, bottleSize, bottlePrice));
        adapter.notifyDataSetChanged();
    }

    private void log(String s) {
        textViewLogger.setText(String.format(Locale.GERMANY, "%s %s", context.getResources().getString(R.string.txtv_logger_text), s));
    }

    public void buySelectedBottle() {
        Bottle b = adapter.getItem(spinner.getSelectedItemPosition());

        if (money >= b.getBottlePrice()) {
            money -= b.getBottlePrice();
            log("Boink! Bottle appears!");
            refreshInsertedCoinsTextView();
            this.bottleArrayList.remove(b);
            adapter.notifyDataSetChanged();
        } else {
            log("Not enough money!");
        }

    }

    public void addMoney() {
        // Add money to the Dispenser
        this.money += 1.0d;
        refreshInsertedCoinsTextView();
        log("Insterted a coin!");
    }


    public double returnMoney() {
        double d = this.money;
        this.money = 0;
        refreshInsertedCoinsTextView();
        return d;
    }

    private void refreshInsertedCoinsTextView() {
        textViewInserted.setText(String.format(Locale.GERMANY, "%s %.2f", context.getResources().getString(R.string.txtv_coins_machine), this.money));
    }

}
