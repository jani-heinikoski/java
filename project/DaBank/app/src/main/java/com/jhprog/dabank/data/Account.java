/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

import androidx.annotation.NonNull;

import java.util.Locale;

public abstract class Account {

    private int acc_id;
    private int acc_type;
    private int acc_bank_id;
    private int acc_cust_id;
    double acc_balance;
    private String acc_number;

    public static final int TYPE_CURRENT = 1;
    public static final int TYPE_SAVING = 2;
    public static final int TYPE_FIXED_TERM = 3;

    public Account(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number) {
        this.acc_id = acc_id;
        this.acc_type = acc_type;
        this.acc_bank_id = acc_bank_id;
        this.acc_cust_id = acc_cust_id;
        this.acc_balance = acc_balance;
        this.acc_number = acc_number;
    }

    public Account(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number) {
        this.acc_type = acc_type;
        this.acc_bank_id = acc_bank_id;
        this.acc_cust_id = acc_cust_id;
        this.acc_balance = acc_balance;
        this.acc_number = acc_number;
    }

    public void deposit(double amount) {
        this.acc_balance += amount;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public int getAcc_bank_id() {
        return acc_bank_id;
    }

    public int getAcc_cust_id() {
        return acc_cust_id;
    }

    public String getAcc_number() {
        return acc_number;
    }

    public double getAcc_balance() {
        return acc_balance;
    }

    public abstract int getType();

    @NonNull
    @Override
    public String toString() {
        String retVal = "";
        switch (acc_type) {
            case TYPE_CURRENT:
                retVal = String.format(Locale.getDefault(),
                        "%s %.2f",
                        "Current account", acc_balance);
                break;
            case TYPE_SAVING:
                retVal = String.format(Locale.getDefault(),
                        "%s %.2f",
                        "Saving account", acc_balance);
                break;
            case TYPE_FIXED_TERM:
                retVal = String.format(Locale.getDefault(),
                        "%s %.2f",
                        "Fixed-term account", acc_balance);
                break;
        }
        return retVal;
    }
}