/*
 * Author: Jani Olavi Heinikoski
 * Date: 17.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class PendingTransaction extends Transaction {

    public static final int RECURRENCE_NONE = 0;
    public static final int RECURRENCE_DAILY = 1;
    public static final int RECURRENCE_WEEKLY = 2;
    public static final int RECURRENCE_MONTHLY = 3;

    public static final String NEVER_PAID = "never";

    private int trans_recurrence;
    private String last_paid;
    private String due_date;

    public PendingTransaction(int trans_id, int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount, int trans_recurrence, String last_paid, String due_date) {
        super(trans_id, trans_type, trans_from_acc_id, trans_to_acc_id, trans_amount);
        this.trans_recurrence = trans_recurrence;
        this.last_paid = last_paid;
        this.due_date = due_date;
    }

    public PendingTransaction(int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount, int trans_recurrence, String last_paid, String due_date) {
        super(trans_type, trans_from_acc_id, trans_to_acc_id, trans_amount);
        this.trans_recurrence = trans_recurrence;
        this.last_paid = last_paid;
        this.due_date = due_date;
    }

    public int getTrans_recurrence() {
        return trans_recurrence;
    }

    public String getLast_paid() {
        return last_paid;
    }

    public String getDue_date() {
        return due_date;
    }

}