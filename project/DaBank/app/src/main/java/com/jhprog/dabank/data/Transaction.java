/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public abstract class Transaction {

    private int trans_id;
    private int trans_type;
    private String trans_from_acc_number;
    private String trans_to_acc_number;
    private double trans_amount;

    public static final int TYPE_PAYMENT = 1;
    public static final int TYPE_DEPOSIT = 2;
    public static final int TYPE_WITHDRAW = 3;
    public static final int TYPE_TRANSFER = 4;

    public Transaction(int trans_id, int trans_type, String trans_from_acc_number, String trans_to_acc_number, double trans_amount) {
        this.trans_id = trans_id;
        this.trans_type = trans_type;
        this.trans_from_acc_number = trans_from_acc_number;
        this.trans_to_acc_number = trans_to_acc_number;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, String trans_from_acc_number, String trans_to_acc_number, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_number = trans_from_acc_number;
        this.trans_to_acc_number = trans_to_acc_number;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, Account fromAccount, Account toAccount, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_number = fromAccount.getAcc_number();
        this.trans_to_acc_number = toAccount.getAcc_number();
        this.trans_amount = trans_amount;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public String getTrans_from_acc_number() {
        return trans_from_acc_number;
    }

    public String getTrans_to_acc_number() {
        return trans_to_acc_number;
    }

    public double getTrans_amount() {
        return trans_amount;
    }
}
