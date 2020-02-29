/*
Author: Jani Heinikoski | 0541122
Date: 28.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 8.5.0
 */
package com.example.bottledispenser;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BottleDispenser {
    // The ArrayList for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private ArrayAdapter<Bottle> adapter;
    private Spinner spinner;
    private Context context;
    private TextView textViewInserted;
    private TextView textViewLogger;
    private FileManager fmgr;
    private SimpleDateFormat df;
    private double money;
    private static BottleDispenser bd = null;

    public static BottleDispenser getInstance(double money, Spinner sp, Context c, TextView t, TextView l) {
        if (bd == null) {
            bd = new BottleDispenser(money, sp, c, t, l);
        }
        return bd;
    }

    private BottleDispenser(double money, Spinner sp, Context c, TextView t, TextView l) {
        this.money = money;
        this.spinner = sp;
        this.context = c;
        this.fmgr = FileManager.getInstance(this.context);
        this.df = new SimpleDateFormat("dd.MMM.yyyy");
        this.textViewInserted = t;
        this.textViewLogger = l;
        // Initialize the array
        this.bottleArrayList = new ArrayList<Bottle>(7);
        fillMachine();
        // Initialize ArrayAdapter for the Spinner
        this.adapter = new ArrayAdapter<Bottle>(context.getApplicationContext(), R.layout.spinner_item, this.bottleArrayList);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        this.spinner.setAdapter(adapter);
    }

    private void fillMachine() {
        // Populate ArrayList
        this.bottleArrayList.add(new Bottle("Pepsi Max", "Pepsi", 0.3, 0.5, 1.8));
        this.bottleArrayList.add(new Bottle("Pepsi Max", "Pepsi", 0.3, 0.33, 1.1));
        this.bottleArrayList.add(new Bottle("7 up", "Keurig Dr. Pepper", 0.3, 0.5, 1.8));
        this.bottleArrayList.add(new Bottle("7 up", "Keurig Dr. Pepper", 0.3, 0.5, 1.8));
        this.bottleArrayList.add(new Bottle("7 up", "Keurig Dr. Pepper", 0.3, 0.5, 1.8));
        this.bottleArrayList.add(new Bottle("Coca Cola", "Coca Cola", 0.3, 0.5, 2.0));
        this.bottleArrayList.add(new Bottle("Coca Cola", "Coca Cola", 0.3, 1.5, 3.2));
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
        if (adapter.getCount() > 0) {
            Bottle b = adapter.getItem(spinner.getSelectedItemPosition());

            if (money >= b.getBottlePrice()) {
                Date dt = Calendar.getInstance().getTime();
                String formattedDate = df.format(dt);
                money -= b.getBottlePrice();
                writeReceipt(new Receipt(b.getName(), b.getBottlePrice(), formattedDate));
                log("Boink! Bottle appears!");
                refreshInsertedCoinsTextView();
                this.bottleArrayList.remove(b);
                adapter.notifyDataSetChanged();
            } else {
                log("Not enough money!");
            }
        } else {
            log("No bottles left!");
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

    public void showReceipt() {
        ArrayList<String> fileContent = fmgr.readFile("receipt.txt");

        if (fileContent != null && !fileContent.isEmpty()) {
            Toast toast = Toast.makeText(context, fileContent.get(0), Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "No receipt found!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void writeReceipt(Receipt receipt) {
        fmgr.writeFile("receipt.txt", receipt.toString());
    }

}
