/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.5.3
 */
package com.kranaatinheitinkomppania;
import java.util.ArrayList;
import java.util.Scanner;



public class BottleDispenser {

    // Amount of bottles
    private int bottles;
    // The ArrayList for the Bottle-objects
    private ArrayList<Bottle> bottleArrayList;
    private double money;

    public BottleDispenser(int bottles, double money) {
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
        System.out.println("Klink! Added more money!");
    }

    private Bottle popBottleIndex(int idx) {
        // Get bottle from this.bottleArrayList at index idx and remove it.
        Bottle bottle = this.bottleArrayList.get(idx);
        this.bottleArrayList.remove(idx);
        return bottle;
    }

    private Bottle chooseBottle() {
        // Allows user to choose a bottle from the dispenser.
        // Used for void buyBottle() (for clarity's sake).
        int choice;
        Scanner sc = new Scanner(System.in);
        // List contents and ask the user what bottle he/she wants.
        listContents();
        System.out.print("Your choice: ");
        // Catch user input for errors.
        try {
            choice = Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            System.out.println("Choice must be an integer!");
            return null;
        }
        // Check if choice is within valid range.
        if (!(choice > 0 && choice <= bottles)) {
            System.out.println("Please choose an appropriate number from the list.");
            return null;
        }
        // Get the bottle user wanted.
        Bottle bottleToBuy = this.bottleArrayList.get(choice - 1);
        // Returns the bottle of choice.
        return bottleToBuy;
    }

    public void buyBottle() {
        // Allows a user to buy a bottle from the dispenser.
        // Check if bottles left in the dispenser.
        if (this.bottles == 0) {
            System.out.println("No more bottles are left!");
            return;
        }
        // Let the user choose the bottle.
        Bottle bottleToBuy = chooseBottle();
        // chooseBottle returns null in case of error.
        if (bottleToBuy != null) {
            // Check if user has enough money.
            if (this.money < bottleToBuy.getBottlePrice()) {
                System.out.println("Add money first!");
                return;
            }

            System.out.println("KACHUNK! " + popBottleIndex(this.bottleArrayList.indexOf(bottleToBuy)).getName() + " came out of the dispenser!");
            this.bottles -= 1;
            this.money -= bottleToBuy.getBottlePrice();
        }
    }

    public void returnMoney() {
        // Refunds money put in to the dispenser (if any).
        if (this.money > 0) {
            System.out.println(String.format("Klink klink. Money came out! You got %,.2f€ back", this.money));
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
