// Jani Heinikoski
// 17.1.2019
// 0541122
// Luokka Dog toisen viikon tehtäviin.
package com.kranaatinheitinkomppania;
import java.util.Scanner;



public class Dog {

    private String name; // Dog -object's name.
    private String says; // Printed to stdout in void speak().
    private Scanner sc;

    public void speak(String whatToSay) {
        // Prints user's sentence to stdout.
        // Trim removes L/T whitespace.
        whatToSay = whatToSay.trim();
        this.sc = new Scanner(whatToSay);
        String tempWord;

        if (whatToSay.isEmpty()) {
            this.says = "Much wow!";
        } else {
            this.says = whatToSay;
        }

        while (this.sc.hasNext()) {

            if (sc.hasNextBoolean()) {
                tempWord = sc.next();
                System.out.println("Such boolean: " + tempWord);
            } else if (sc.hasNextInt()) {
                tempWord = sc.next();
                System.out.println("Such integer: " + tempWord);
            } else {
                tempWord = sc.next();
                System.out.println(tempWord);
            }

        }

    }

    public Dog(String name) {
        // Class constructor, sets this.name to given (arg) name if not empty.
        // Trim removes L/T whitespace.
        if (name.trim().isEmpty()) {
            this.name = "Doge";
        } else {
            this.name = name;
        }

        System.out.println("Hey, my name is " + this.name + "!");
    }
}