/*
 * Author: Jani Olavi Heinikoski
 * Date: 17.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class NormalTransaction extends Transaction {
    private String trans_date;

    public NormalTransaction(int trans_id, int trans_type, String trans_from_acc_number, String trans_to_acc_number, double trans_amount, String trans_date) {
        super(trans_id, trans_type, trans_from_acc_number, trans_to_acc_number, trans_amount);
        this.trans_date = trans_date;
    }

    public NormalTransaction(int trans_type, String trans_from_acc_number, String trans_to_acc_number, double trans_amount, String trans_date) {
        super(trans_type, trans_from_acc_number, trans_to_acc_number, trans_amount);
        this.trans_date = trans_date;
    }

    public NormalTransaction(int trans_type, Account fromAccount, Account toAccount, double trans_amount, String trans_date) {
        super(trans_type, fromAccount, toAccount, trans_amount);
        this.trans_date = trans_date;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
}
