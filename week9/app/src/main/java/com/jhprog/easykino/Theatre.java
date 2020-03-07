package com.jhprog.easykino;

public class Theatre implements Comparable<Theatre> {
    private int ID;
    private String name;

    public Theatre(int id, String n) {
        if (id > 0) {
            ID = id;
        }
        if (!n.trim().isEmpty()) {
            name = n;
        }
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    @Override
    public int compareTo(Theatre theatre) {
        return this.name.compareTo(theatre.getName());
    }
}
