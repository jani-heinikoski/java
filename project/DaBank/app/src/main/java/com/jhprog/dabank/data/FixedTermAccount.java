/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;


import com.jhprog.dabank.utility.TimeManager;

import java.text.ParseException;
import java.util.Date;

public final class FixedTermAccount extends Account {

    private String acc_due_date;

    public FixedTermAccount(int acc_id, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, boolean acc_frozen, String acc_due_date) {
        super(acc_id, Account.TYPE_FIXED_TERM, acc_bank_id, acc_cust_id, acc_balance, acc_number, acc_frozen);
        this.acc_due_date = acc_due_date;
    }

    public FixedTermAccount(int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number, boolean acc_frozen, String acc_due_date) {
        super(Account.TYPE_FIXED_TERM, acc_bank_id, acc_cust_id, acc_balance, acc_number, acc_frozen);
        this.acc_due_date = acc_due_date;
    }

    @Override
    public int getType() {
        return Account.TYPE_FIXED_TERM;
    }

    public String getAcc_due_date() {
        return acc_due_date;
    }

    @Override
    public boolean withdraw(double amount) {
        if (!super.withdraw(amount)) {
            return false;
        }

        TimeManager timeManager = TimeManager.getInstance();
        Date today = timeManager.todayDate();
        Date dueDate = null;

        try {
            dueDate = timeManager.stringToDate(acc_due_date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (timeManager.compareDates(today, dueDate) == TimeManager.BEFORE) {
            return false;
        } else {
            if (acc_balance >= amount) {
                acc_balance -= amount;
                return true;
            } else {
                return false;
            }
        }

    }
}
