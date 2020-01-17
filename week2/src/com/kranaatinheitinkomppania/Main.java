// Jani Heinikoski
// 17.1.2019
// 0541122
// Luokka Dog toisen viikon tehtäviin.
package com.kranaatinheitinkomppania;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String dogsName;
        String dogsSentence;

        System.out.print("Give a name to the dog: ");
        dogsName = sc.nextLine();
        Dog dog = new Dog(dogsName);

        System.out.print("What does a dog say: ");
        dogsSentence = sc.nextLine();
        dog.speak(dogsSentence);

        sc.close();
        /*
        Dog firstDog = new Dog("Rekku");
        Dog secondDog = new Dog("Musti");

        firstDog.speak("Hau!");
        secondDog.speak("Vuh!");
         */
    }


}