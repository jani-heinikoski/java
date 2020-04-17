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
    private String trans_date;
    private double trans_amount;

    public static final int TYPE_PAYMENT = 1;
    public static final int TYPE_DEPOSIT = 2;
    public static final int TYPE_WITHDRAW = 3;
    public static final int TYPE_TRANSFER = 4;

    public static final int RECURRENCE_DAILY = 1;
    public static final int RECURRENCE_WEEKLY = 2;
    public static final int RECURRENCE_MONTHLY = 3;


    public Transaction(int trans_id, int trans_type, String trans_from_acc_id, String trans_to_acc_id, String trans_date, double trans_amount) {
        this.trans_id = trans_id;
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_date = trans_date;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, String trans_from_acc_id, String trans_to_acc_id, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_date = "date('now', 'localtime')";
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, String trans_from_acc_id, String trans_to_acc_id, String trans_date, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_date = trans_date;
        this.trans_amount = trans_amount;
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
        return trans_date;
    }

    public double getTrans_amount() {
        return trans_amount;
    }

}
