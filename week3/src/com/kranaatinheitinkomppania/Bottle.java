/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.3.0
 */
package com.kranaatinheitinkomppania;
import java.util.ArrayList;



public class Bottle {

    private String name;
    private String manufacturer;
    private double bottleSize;
    private double bottlePrice;
    private double totalEnergy;

    public Bottle() {
        this.name = "Pepsi Max";
        this.manufacturer = "Pepsi";
        this.totalEnergy = 0.3f;
        this.bottlePrice = 1.80f;
        this.bottleSize = 0.5f;
    }

    public double getBottlePrice() {
        return this.bottlePrice;
    }

    public double getBottleSize() {
        return this.bottleSize;
    }

    public void setBottlePrice(double bottlePrice) {
        // Bottle price has to be over 0.
        if (bottlePrice > 0.0f) {
            this.bottlePrice = bottlePrice;
        } else {
            System.out.println("Bottle price must be positive!");
        }

    }

    public void setBottleSize(double bottleSize) {

        if (bottleSize > 0.0) {
            this.bottleSize = bottleSize;
        } else {
            System.out.println("Bottle size not accepted, must be positive of type double.");
        }

    }

    public Bottle(String name, String manuf, float totE) {
        this.name = name;
        this.manufacturer = manuf;
        this.totalEnergy = totE;
    }

    public double getTotalEnergy() {
        return this.totalEnergy;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

}

