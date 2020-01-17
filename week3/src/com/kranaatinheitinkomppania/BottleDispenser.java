/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.5.1
 */
package com.kranaatinheitinkomppania;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;



public class BottleDispenser {

    // Amount of bottles
    private int bottles;
    // The array for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private double money;

    public BottleDispenser(int bottles, double money) {
        // Class constructor
        this.bottles = bottles;
        this.money = money;

        // Initialize the array
        this.bottleArrayList = new ArrayList(this.bottles);
    }

    public BottleDispenser() {
        // Class (no-args) constructor
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

    public void addBottle(String name, String manufacturer, double totalEnergy, double bottleSize, double bottlePrice) {
        this.bottleArrayList.add(new Bottle(name, manufacturer, totalEnergy, bottleSize, bottlePrice));
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

    public Bottle popBottleIndex(int idx) {
        Bottle bottle = this.bottleArrayList.get(idx);
        this.bottleArrayList.remove(idx);
        return bottle;
    }

    public void buyBottle() {
        // Allows a user to buy a bottle from the dispenser.
        int choice;
        // Check if bottles left in the dispenser.
        if (this.bottles == 0) {
            System.out.println("No more bottles are left!");
            return;
        }
        // List contents and ask the user what bottle he/she wants.
        Scanner sc = new Scanner(System.in);
        listContents();
        System.out.print("Your choice: ");
        // Catch user input for errors.
        try {
            choice = Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            System.out.println("Choice must be an integer!");
            return;
        }
        // Check if choice is within valid range.
        if (!(choice > 0 && choice <= bottles)) {
            System.out.println("Please choose an appropriate number from the list.");
            return;
        }
        // Get the bottle user wanted.
        Bottle bottleToBuy = this.bottleArrayList.get(choice - 1);
        // Check if user has enough money.
        if (this.money < bottleToBuy.getBottlePrice()) {
            System.out.println("Add money first!");
            return;
        }

        System.out.println("KACHUNK! " + popBottleIndex((choice - 1)).getName() + " came out of the dispenser!");
        this.bottles -= 1;
        this.money -= bottleToBuy.getBottlePrice();
    }

    public void returnMoney() {
        // Refunds money put in to the dispenser (if any).
        if (this.money > 0) {
            DecimalFormat dc = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.GERMANY));
            System.out.println("Klink klink. Money came out! You got " + dc.format(this.money) + "€ back");
            this.money = 0.0d;
        } else {
            System.out.println("Klink klink!! All money gone!");
        }
    }

    public void listContents() {
        // Prints out the contents of BottleDispenser instance.
        int idx = 1;

        for (Bottle b : this.bottleArrayList) {
            if (b != null) {
                System.out.println(idx + ". Name: " + b.getName());
                System.out.println("\tSize: " + b.getBottleSize() + "\tPrice: " + b.getBottlePrice());
                idx++;
            }
        }
    }
}
