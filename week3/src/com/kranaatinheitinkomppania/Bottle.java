/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.2.0
 */
package com.kranaatinheitinkomppania;



public class Bottle {

    private String name;
    private String manufacturer;
    private double total_energy;

    public Bottle() {
        this.name = "Pepsi Max";
        this.manufacturer = "Pepsi";
        this.total_energy = 0.3f;
    }

    public Bottle(String name, String manuf, float totE) {
        this.name = name;
        this.manufacturer = manuf;
        this.total_energy = totE;
    }

    public double getTotal_energy() {
        return this.total_energy;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

}

