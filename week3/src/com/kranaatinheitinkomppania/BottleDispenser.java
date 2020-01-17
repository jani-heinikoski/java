/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.3.3
 */
package com.kranaatinheitinkomppania;



public class BottleDispenser {

    // Amount of bottles
    private int bottles;
    // The array for the Bottle-objects
    private Bottle[] bottle_array;
    private double money;

    public BottleDispenser() {

        this.bottles = 50;
        this.money = 0.0d;

        // Initialize the array
        this.bottle_array = new Bottle[this.bottles];

        // Add Bottle-objects to the array
        for(int i = 0; i < this.bottles; i++) {
            // Use the default constructor to create new Bottles
            bottle_array[i] = new Bottle();
        }

    }

    public void addMoney() {
        this.money += 1.0d;
        System.out.println("Klink! Added more money!");
    }

    private Bottle getLastBottle() {
        // Gets the price of last bottle in this.bottle_array.
        if (this.bottles == 0) {
            return null;
        }

        int count = 0;
        Bottle lastBottle;

        for (Bottle b : this.bottle_array) {
            if (b != null) {
                count++;
            }
        }

        lastBottle = this.bottle_array[count - 1];
        return lastBottle;
    }

    private Bottle popLastBottle() {
        // Gets the last bottle of this.bottle_array and removes it.
        if (this.bottles == 0) {
            return null;
        }

        int count = 0;
        Bottle lastBottle;

        for (Bottle b : this.bottle_array) {
            if (b != null) {
                count++;
            }
        }

        lastBottle = this.bottle_array[count - 1];
        this.bottle_array[count - 1] = null;

        return lastBottle;
    }

    public void buyBottle() {

        if (this.bottles == 0) {
            System.out.println("No more bottles left!");
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

}
