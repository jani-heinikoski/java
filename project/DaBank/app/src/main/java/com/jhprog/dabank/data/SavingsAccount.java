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

    public SavingsAccount(int acc_id, int acc_bank_id, int acc_cust_id, double acc_balance, int acc_withdrawlimit, String acc_number) {
        super(acc_id, Account.TYPE_SAVING, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_withdrawlimit = acc_withdrawlimit;
    }

    public SavingsAccount(int acc_bank_id, int acc_cust_id, double acc_balance, int acc_withdrawlimit, String acc_number) {
        super(Account.TYPE_SAVING, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_withdrawlimit = acc_withdrawlimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (this.acc_withdrawlimit > 0 && (this.acc_balance - amount) >= 0) {
            this.acc_balance -= amount;
            this.acc_withdrawlimit--;
            return true;
        } else {
            return false;
        }
    }

    public int getAcc_withdrawlimit() {
        return acc_withdrawlimit;
    }

    @Override
    public int getType() {
        return Account.TYPE_SAVING;
    }
}
