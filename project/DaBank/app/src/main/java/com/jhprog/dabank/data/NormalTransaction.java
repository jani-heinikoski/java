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

    public NormalTransaction(int trans_id, int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount, String trans_date) {
        super(trans_id, trans_type, trans_from_acc_id, trans_to_acc_id, trans_amount);
        this.trans_date = trans_date;
    }

    public NormalTransaction(int trans_type, int trans_from_acc_id, int trans_to_acc_id, double trans_amount, String trans_date) {
        super(trans_type, trans_from_acc_id, trans_to_acc_id, trans_amount);
        this.trans_date = trans_date;
    }

    public String getTrans_date() {
        return trans_date;
    }

}