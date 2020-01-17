/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.1.0
 */
package com.kranaatinheitinkomppania;



public class BottleDispenser {

    private int bottles;
    private int money;

    public BottleDispenser() { // Class Constructor
        this.bottles = 5;
        this.money = 0;
    }

    public void addMoney() {
        this.money += 1;
        System.out.println("Klink! Added more money!");
    }

    public void buyBottle() {

        if (this.bottles == 0) {
            System.out.println("No more bottles left!");
            return;
        }

        if (this.money == 0) {
            System.out.println("Add money first!");
            return;
        }

        if (this.bottles > 0 && this.money > 0) {
            this.bottles -= 1;
            this.money -= 1;
            System.out.println("KACHUNK! A bottle came out of the dispenser!");
        }

    }

    public void returnMoney() {

        if (this.money > 0) {
            System.out.println("Klink klink. Money came out!");
            this.money = 0;
        } else {
            System.out.println("Klink klink!! All money gone!");
        }

    }

}
