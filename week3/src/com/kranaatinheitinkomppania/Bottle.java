/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.4.0
 */
package com.kranaatinheitinkomppania;



public class Bottle {

    private String name;
    private String manufacturer;
    private double bottleSize;
    private double bottlePrice;
    private double totalEnergy;

    public Bottle() {
        this.name = "Pepsi Max";
        this.manufacturer = "Pepsi";
        this.totalEnergy = 0.3d;
        setBottlePrice(1.80d);
        setBottleSize(0.5d);
    }

    public Bottle(String name, String manuf, float totE) {
        this.name = name;
        this.manufacturer = manuf;
        this.totalEnergy = totE;
    }

    public double getBottlePrice() {
        return this.bottlePrice;
    }

    public void setBottlePrice(double bottlePrice) {
        // Bottle price has to be over 0.
        if (bottlePrice > 0.0d) {
            this.bottlePrice = bottlePrice;
        } else {
            System.out.println("Bottle price must be positive!");
        }
    }

    public double getBottleSize() {
        return this.bottleSize;
    }

    public void setBottleSize(double bottleSize) {
        // Bottle size has to be over 0.
        if (bottleSize > 0.0d) {
            this.bottleSize = bottleSize;
        } else {
            System.out.println("Bottle size must be positive!");
        }
    }

    public String getName() {
        return this.name;
    }

    public double getTotalEnergy() {
        return this.totalEnergy;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

}

