/*
Author: Jani Heinikoski | 0541122
Date: 26.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 5.4.0
 */
package com.kranaatinheitinkomppania;
import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Character character = null;
        int c;
        int w;
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("*** BATTLE SIMULATOR ***\n1) Create a character\n2) Fight with a character\n0) Quit\nYour choice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("Choose your character: \n" +
                        "1) King\n" +
                        "2) Knight\n" +
                        "3) Queen\n" +
                        "4) Troll\n" +
                        "Your choice: ");
                c = sc.nextInt();

                System.out.println("Choose your weapon: \n" +
                        "1) Knife\n" +
                        "2) Axe\n" +
                        "3) Sword\n" +
                        "4) Club\n" +
                        "Your choice: ");
                w = sc.nextInt();

                switch (c) {
                    case 1:
                        character = new King(w);
                        break;
                    case 2:
                        character = new Knight(w);
                        break;
                    case 3:
                        character = new Queen(w);
                        break;
                    case 4:
                        character = new Troll(w);
                        break;
                }

            } else if (choice == 2) {
                if (character != null) character.fight();
            }

        } while (choice != 0);

    }
}
