// Jani Heinikoski
// 17.1.2019
// 0541122
// Luokka Main toisen viikon tehtäviin.
package com.kranaatinheitinkomppania;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        // Ask dog's name and a sentence to say.
        Scanner sc = new Scanner(System.in);
        String dogsName;
        String whatDoesTheDogSay;

        System.out.print("Give a name to the dog: ");
        dogsName = sc.nextLine();
        Dog dog = new Dog(dogsName);

        System.out.print("What does a dog say: ");
        whatDoesTheDogSay = sc.nextLine();
        dog.speak(whatDoesTheDogSay);
    }

}