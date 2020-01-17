/*
Author: Jani Heinikoski | 0541122
Date: 17.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 3.4.0
 */
package com.kranaatinheitinkomppania;
import java.util.Scanner;



public class Main {

    private static int ui() {
        // Prints the user interface and returns user's choice.
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.print("\n*** BOTTLE DISPENSER ***\n" +
                "1) Add money to the machine\n" +
                "2) Buy a bottle\n" +
                "3) Take money out\n" +
                "4) List bottles in the dispenser\n" +
                "0) End\n" +
                "Your choice: ");

        try {
            choice = Integer.parseInt(sc.next());
        } catch (NumberFormatException e) {
            System.out.println("Choice must be an integer!");
            return 0;
        }

        return choice;
    }

    public static void main(String[] args) {
        BottleDispenser BD = new BottleDispenser();
        int choice;

        while (true) {
            choice = ui();

            switch (choice) {
                case 0: // End the program
                    return;
                case 1: // Add money
                    BD.addMoney();
                    break;
                case 2: // Buy a bottle
                    BD.buyBottle();
                    break;
                case 3: // Take money out
                    BD.returnMoney();
                    break;
                case 4: // List contents of BD
                    BD.listContents();
                    break;
                default:
                    System.out.println("Unknown choice");
                    break;
            }
        }
    }
}
