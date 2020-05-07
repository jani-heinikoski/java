/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

import java.util.Calendar;

public final class SavingsAccount extends Account {

    private int acc_withdrawlimit;
    private int acc_last_withdraw_year;

    public static final int NEVER_USED = 1;

    public SavingsAccount(int acc_id, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, boolean acc_frozen, int acc_withdrawlimit, int acc_last_withdraw_year) {
        super(acc_id, Account.TYPE_SAVING, acc_bank_id, acc_cust_id, acc_balance, acc_number, acc_frozen);
        this.acc_withdrawlimit = acc_withdrawlimit;
        this.acc_last_withdraw_year = acc_last_withdraw_year;
    }

    public SavingsAccount(int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, boolean acc_frozen, int acc_withdrawlimit, int acc_last_withdraw_year) {
        super(Account.TYPE_SAVING, acc_bank_id, acc_cust_id, acc_balance, acc_number, acc_frozen);
        this.acc_withdrawlimit = acc_withdrawlimit;
        this.acc_last_withdraw_year = acc_last_withdraw_year;
    }

    @Override
    public boolean withdraw(double amount) {
        if (!super.withdraw(amount)) {
            return false;
        }

        if (acc_last_withdraw_year != NEVER_USED) {
            if (Calendar.getInstance().get(Calendar.YEAR) > acc_last_withdraw_year) {
                acc_withdrawlimit = 10;
            }
        }

        if (this.acc_withdrawlimit > 0 && (this.acc_balance - amount) >= 0) {
            this.acc_balance -= amount;
            this.acc_withdrawlimit--;
            this.acc_last_withdraw_year = Calendar.getInstance().get(Calendar.YEAR);
            return true;
        } else {
            return false;
        }
    }

    public int getAcc_last_withdraw_year() {
        return acc_last_withdraw_year;
    }

    public int getAcc_withdrawlimit() {
        return acc_withdrawlimit;
    }

    @Override
    public int getType() {
        return Account.TYPE_SAVING;
    }
}
