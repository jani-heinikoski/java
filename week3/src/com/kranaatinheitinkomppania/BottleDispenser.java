/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.4.1
 */
package com.kranaatinheitinkomppania;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;



public class BottleDispenser {

    // Amount of bottles
    private int bottles;
    // The array for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private double money;

    public BottleDispenser() {

        this.bottles = 5;
        this.money = 0.0d;

        // Initialize the array
        this.bottleArrayList = new ArrayList(this.bottles);

        // Add Bottle-objects to the array
        for(int i = 0; i < this.bottles; i++) {
            // Use the default constructor to create new Bottles
            this.bottleArrayList.add(new Bottle());
        }

    }

    public void addMoney() {
        this.money += 1.0d;
        System.out.println("Klink! Added more money!");
    }

    private Bottle getLastBottle() {
        // Gets the last bottle in this.bottleArrayList.
        if (this.bottles == 0) {
            return null;
        }

        int count = 0;
        Bottle lastBottle;

        for (Bottle b : this.bottleArrayList) {
            if (b != null) {
                count++;
            }
        }

        lastBottle = this.bottleArrayList.get(count - 1);
        return lastBottle;
    }

    private Bottle popLastBottle() {
        // Gets the last bottle of this.bottleArrayList and removes it.
        if (this.bottles == 0) {
            return null;
        }

        int count = 0;
        Bottle lastBottle;

        for (Bottle b : this.bottleArrayList) {
            if (b != null) {
                count++;
            }
        }

        lastBottle = this.bottleArrayList.get(count - 1);
        this.bottleArrayList.remove(count - 1);

        return lastBottle;
    }

    public void buyBottle() {

        if (this.bottles == 0) {
            System.out.println("No more bottles are left!");
            return;
        }

        Bottle bottleToBuy = getLastBottle();

        if (this.money < bottleToBuy.getBottlePrice()) {
            System.out.println("Add money first!");
            return;
        }

        System.out.println("KACHUNK! " + popLastBottle().getName() + " came out of the dispenser!");
        this.bottles -= 1;
        this.money -= bottleToBuy.getBottlePrice();

    }

    public void returnMoney() {

        if (this.money > 0) {
            System.out.println("Klink klink. Money came out!");
            this.money = 0.0d;
        } else {
            System.out.println("Klink klink!! All money gone!");
        }

    }

    public void listContents() {
        // Prints out the contents of BottleDispenser instance.
        int idx = 1;
        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.US));

        for (Bottle b : this.bottleArrayList) {
            if (b != null) {
                System.out.println(idx + ". Name: " + b.getName());
                System.out.println("\tSize: " + df.format(b.getBottleSize()) + "\tPrice: " + df.format(b.getBottlePrice()));
                idx++;
            }
        }
    }
}
