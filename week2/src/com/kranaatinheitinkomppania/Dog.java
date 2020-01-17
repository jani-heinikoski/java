// Jani Heinikoski
// 17.1.2019
// 0541122
// Luokka Dog toisen viikon tehtäviin.
package com.kranaatinheitinkomppania;



public class Dog {

    private String name; // Dog -object's name.
    private String says; // Printed to stdout in void speak().

    public void speak(String whatToSay) {
        // Prints user's sentence to stdout.
        // Trim removes L/T whitespace.
        if (whatToSay.trim().isEmpty()) {
            this.says = "Much wow!";
        } else {
            this.says = whatToSay;
        }

        System.out.println(this.name + ": " + this.says);
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