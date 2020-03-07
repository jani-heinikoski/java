package com.jhprog.easykino;

public class Theatre implements Comparable<Theatre> {
    private int ID;
    private String name;
    private String location;

    public Theatre(int id, String n, String l) {
        if (id > 0) {
            ID = id;
        }
        if (!n.trim().isEmpty()) {
            name = n;
        }
        if (!l.trim().isEmpty()) {
            location = l;
        }
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
