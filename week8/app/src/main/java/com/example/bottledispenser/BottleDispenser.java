/*
Author: Jani Heinikoski | 0541122
Date: 28.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 8.5.0
 */
package com.example.bottledispenser;
import java.util.ArrayList;



public class BottleDispenser {
    // Amount of bottles
    private int bottles;
    // The ArrayList for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private double money;
    private static BottleDispenser bd = null;

    public static BottleDispenser getInstance(int bottles, double money) {
        if (bd == null) {
            bd = new BottleDispenser(bottles, money);
        }
        return bd;
    }

    private BottleDispenser(int bottles, double money) {
        // Class constructor
        this.bottles = bottles;
        this.money = money;
        // Initialize the array
        this.bottleArrayList = new ArrayList(this.bottles);
    }

    public void addBottle(String name, String manufacturer, double totalEnergy, double bottleSize, double bottlePrice) {
        // Add bottles to the Dispenser
        this.bottleArrayList.add(new Bottle(name, manufacturer, totalEnergy, bottleSize, bottlePrice));
    }

    public void addMoney() {
        // Add money to the Dispenser
        this.money += 1.0d;
        System.out.println("LOGGER: Added more money!");
    }

    private Bottle popBottleIndex(int idx) {
        // Get bottle from this.bottleArrayList at index idx and remove it.
        Bottle bottle = this.bottleArrayList.get(idx);
        this.bottleArrayList.remove(idx);
        return bottle;
    }

}
