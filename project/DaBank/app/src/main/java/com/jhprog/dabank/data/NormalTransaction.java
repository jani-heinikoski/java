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

    public NormalTransaction(int trans_id, int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, String trans_date) {
        super(trans_id, trans_type, trans_ref_number, trans_from_acc_number, trans_to_acc_number, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
        this.trans_date = trans_date;
    }

    public NormalTransaction(int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, String trans_date) {
        super(trans_type, trans_ref_number, trans_from_acc_number, trans_to_acc_number, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
        this.trans_date = trans_date;
    }

    public NormalTransaction(int trans_type, int trans_ref_number, Account trans_from_acc, Account trans_to_acc, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, String trans_date) {
        super(trans_type, trans_ref_number, trans_from_acc, trans_to_acc, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
        this.trans_date = trans_date;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
}
