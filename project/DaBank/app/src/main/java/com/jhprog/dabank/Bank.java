package com.jhprog.dabank;

public class Bank {

    private int b_id;
    private String b_name;

    public Bank(int b_id, String b_name) {
        this.b_id = b_id;
        this.b_name = b_name;
    }

    public int getB_id() {
        return b_id;
    }

    public String getB_name() {
        return b_name;
    }

}
