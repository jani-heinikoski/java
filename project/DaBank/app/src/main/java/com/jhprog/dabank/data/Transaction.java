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
    private int trans_from_acc_id;
    private int trans_to_acc_id;
    private double trans_amount;

    public static final int TYPE_PAYMENT = 1;
    public static final int TYPE_DEPOSIT = 2;
    public static final int TYPE_WITHDRAW = 3;
    public static final int TYPE_TRANSFER = 4;

    public Transaction(int trans_id, int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount) {
        this.trans_id = trans_id;
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_id = trans_from_acc_id;
        this.trans_to_acc_id = trans_to_acc_id;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, Account fromAccount, Account toAccount, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_from_acc_id = fromAccount.getAcc_id();
        this.trans_to_acc_id = toAccount.getAcc_id();
        this.trans_amount = trans_amount;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public int getTrans_from_acc_id() {
        return trans_from_acc_id;
    }

    public int getTrans_to_acc_id() {
        return trans_to_acc_id;
    }

    public double getTrans_amount() {
        return trans_amount;
    }
}
