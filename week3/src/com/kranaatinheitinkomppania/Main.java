/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.3.3
 */
package com.kranaatinheitinkomppania;



public class Main {

    public static void main(String[] args) {
        BottleDispenser BD = new BottleDispenser();

        BD.addMoney();
        BD.buyBottle();
        BD.buyBottle();
        BD.addMoney();
        BD.addMoney();
        BD.buyBottle();
        BD.returnMoney();
    }
}
