// Jani Heinikoski
// 14.1.2019
// 0541122
// Luokka Dog toisen viikon tehtäviin.
package com.kranaatinheitinkomppania;

public class Dog {

    private String name;

    public void speak(String sentence) {
        System.out.println(this.name + ": " + sentence);
    }

    public Dog(String name) {

        this.name = name;

        if (!this.name.isEmpty()) {
            System.out.println("Hey, my name is " + this.name + "!");
        }

    }
}