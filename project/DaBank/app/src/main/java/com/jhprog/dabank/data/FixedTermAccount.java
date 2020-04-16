/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;


public final class FixedTermAccount extends Account {

    private String acc_due_date;

    public FixedTermAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, String acc_due_date) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_due_date = acc_due_date;
    }

    public FixedTermAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, String acc_due_date) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_due_date = acc_due_date;
    }

    @Override
    public int getType() {
        return Account.TYPE_FIXED_TERM;
    }

    public String getAcc_due_date() {
        return acc_due_date;
    }
}
