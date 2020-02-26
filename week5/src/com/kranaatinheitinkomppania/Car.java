/*
Author: Jani Heinikoski | 0541122
Date: 26.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 5.4.0
 */
package com.kranaatinheitinkomppania;

public class Car {
    private Engine engine;
    private Chassis chassis;
    private Body body;
    private Tyre[] tires = new Tyre[4];

    public Car() {
        body = new Body();
        chassis = new Chassis();
        engine = new Engine();
        for (int i = 0; i < 4; i++) {
            tires[i] = new Tyre();
        }
    }

    public void print() {
        System.out.println("Car parts:");
        int i = 0;
        if (this.body != null) System.out.println("\tBody");
        if (this.chassis != null) System.out.println("\tChassis");
        if (this.engine != null) System.out.println("\tEngine");
        for (int j = 0; j < this.tires.length; j++) {
            if (this.tires[j] != null) i++;
        }
        System.out.println(String.format("\t%d Wheel", i));
    }
}

class Engine {
    public Engine() {
        System.out.println("Manufacturing: Engine");
    }
}
class Chassis {
    public Chassis() {
        System.out.println("Manufacturing: Chassis");
    }
}
class Body {
    public Body() {
        System.out.println("Manufacturing: Body");
    }
}
class Tyre {
    public Tyre() {
        System.out.println("Manufacturing: Wheel");
    }
}
