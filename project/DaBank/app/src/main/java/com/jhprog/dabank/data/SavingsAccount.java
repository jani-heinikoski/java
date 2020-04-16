/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class SavingsAccount extends Account {

    private int acc_withdrawlimit;

    public SavingsAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, int acc_withdrawlimit) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_withdrawlimit = acc_withdrawlimit;
    }

    public SavingsAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, int acc_withdrawlimit) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_withdrawlimit = acc_withdrawlimit;
    }

    public int getAcc_withdrawlimit() {
        return acc_withdrawlimit;
    }

    @Override
    public int getType() {
        return Account.TYPE_SAVING;
    }
}
