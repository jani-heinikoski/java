/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

import java.util.Date;

public final class FixedTermAccount extends Account {

    private Date acc_due_date;
    private double interest;

    public FixedTermAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, Date acc_due_date, double interest) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance);
        this.acc_due_date = acc_due_date;
        this.interest = interest;
    }

    public FixedTermAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, Date acc_due_date, double interest) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance);
        this.acc_due_date = acc_due_date;
        this.interest = interest;
    }

    @Override
    public int getType() {
        return Account.TYPE_FIXED_TERM;
    }
}
