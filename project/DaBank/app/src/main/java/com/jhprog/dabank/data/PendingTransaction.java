/*
 * Author: Jani Olavi Heinikoski
 * Date: 17.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

import androidx.annotation.NonNull;

public final class PendingTransaction extends Transaction {

    public static final int RECURRENCE_NONE = 0;
    public static final int RECURRENCE_DAILY = 1;
    public static final int RECURRENCE_WEEKLY = 2;
    public static final int RECURRENCE_MONTHLY = 3;

    public static final String NEVER_PAID = "never";

    private int trans_recurrence;
    private String last_paid;
    private String due_date;

    public PendingTransaction(int trans_id, int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, int trans_recurrence, String last_paid, String due_date) {
        super(trans_id, trans_type, trans_ref_number, trans_from_acc_number, trans_to_acc_number, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
        this.trans_recurrence = trans_recurrence;
        this.last_paid = last_paid;
        this.due_date = due_date;
    }

    public PendingTransaction(int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, int trans_recurrence, String last_paid, String due_date) {
        super(trans_type, trans_ref_number, trans_from_acc_number, trans_to_acc_number, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
        this.trans_recurrence = trans_recurrence;
        this.last_paid = last_paid;
        this.due_date = due_date;
    }

    public PendingTransaction(int trans_type, int trans_ref_number, Account trans_from_acc, Account trans_to_acc, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount, int trans_recurrence, String last_paid, String due_date) {
        super(trans_type, trans_ref_number, trans_from_acc, trans_to_acc, trans_payee_name, trans_message, trans_bank_bic, trans_amount);
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

    public void setLast_paid(@NonNull String last_paid) {
        this.last_paid = last_paid;
    }

    public String getDue_date() {
        return due_date;
    }

}
