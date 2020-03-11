package com.jhprog.easykino;

public class Theatre implements Comparable<Theatre> {
    private int ID;
    private String name;
    private String location;

    public Theatre(int id, String n, String l) {
        ID = id;
        name = n;
        location = l;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int compareTo(Theatre theatre) {
        return this.name.compareTo(theatre.getName());
    }

    @Override
    public String toString() {
        return getName();
    }
}
