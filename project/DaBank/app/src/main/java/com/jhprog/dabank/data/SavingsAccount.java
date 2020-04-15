/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class SavingsAccount extends Account {

    private double interest;
    private double withdrawLimit;

    public SavingsAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, double interest, double withdrawLimit) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.interest = interest;
        this.withdrawLimit = withdrawLimit;
    }

    public SavingsAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, double interest, double withdrawLimit) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.interest = interest;
        this.withdrawLimit = withdrawLimit;
    }

    public double getInterest() {
        return interest;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    @Override
    public int getType() {
        return Account.TYPE_SAVING;
    }
}
