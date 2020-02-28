/*
Author: Jani Heinikoski | 0541122
Date: 28.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 8.5.0
 */
package com.example.bottledispenser;


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

    public Bottle(String name, String manufacturer, double totalEnergy, double bottleSize, double bottlePrice) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.totalEnergy = totalEnergy;
        setBottlePrice(bottlePrice);
        setBottleSize(bottleSize);
    }

    public double getBottlePrice() {
        return this.bottlePrice;
    }

    public void setBottlePrice(double bottlePrice) {
        // Bottle price has to be over 0.
        if (bottlePrice > 0.0d) {
            this.bottlePrice = bottlePrice;
        } else {
            System.out.println("LOGGER: Bottle price must be positive!");
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
            System.out.println("LOGGER: Bottle size must be positive!");
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
