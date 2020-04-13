/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class Transaction {

    private int trans_id;
    private int trans_type;
    private String trans_from_acc_id;
    private String trans_to_acc_id;
    private String trans_date_time;
    private String trans_due_date;
    private double trans_amount;
    private int trans_recurrence;

    public static final int TYPE_PAYMENT = 1;
    public static final int TYPE_DEPOSIT = 2;
    public static final int TYPE_WITHDRAW = 3;
    public static final int TYPE_TRANSFER = 4;

    public static final int RECURRENCE_DAILY = 1;
    public static final int RECURRENCE_WEEKLY = 2;
    public static final int RECURRENCE_MONTHLY = 3;


    public Transaction(int trans_id, int trans_type, String trans_from_acc_id, String trans_to_acc_id, String trans_date_time, String trans_due_date, double trans_amount, int trans_recurrence) {
        this.trans_id = trans_id;
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_date_time = trans_date_time;
        this.trans_due_date = trans_due_date;
        this.trans_amount = trans_amount;
        this.trans_recurrence = trans_recurrence;
    }

    public Transaction(int trans_type, String trans_from_acc_id, String trans_to_acc_id, String trans_due_date, double trans_amount, int trans_recurrence) {
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_due_date = trans_due_date;
        this.trans_amount = trans_amount;
        this.trans_recurrence = trans_recurrence;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public String getTrans_from_acc_id() {
        return trans_from_acc_id;
    }

    public String getTrans_to_acc_id() {
        return trans_to_acc_id;
    }

    public String getTrans_date_time() {
        return trans_date_time;
    }

    public String getTrans_due_date() {
        return trans_due_date;
    }

    public double getTrans_amount() {
        return trans_amount;
    }

    public int getTrans_recurrence() {
        return trans_recurrence;
    }
}
