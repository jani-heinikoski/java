/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public abstract class Transaction {

    private int trans_id;
    private int trans_type;
    private int trans_ref_number;
    private String trans_from_acc_number;
    private String trans_to_acc_number;
    private String trans_payee_name;
    private String trans_message;
    private String trans_bank_bic;
    private double trans_amount;

    public static final int TYPE_PAYMENT = 1;
    public static final int TYPE_DEPOSIT = 2;
    public static final int TYPE_WITHDRAW = 3;
    public static final int TYPE_TRANSFER = 4;

    public static final int REF_NUM_NULL = -1;
    public static final String MESSAGE_NULL = "none";

    public Transaction(int trans_id, int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount) {
        this.trans_id = trans_id;
        this.trans_type = trans_type;
        this.trans_ref_number = trans_ref_number;
        this.trans_from_acc_number = trans_from_acc_number;
        this.trans_to_acc_number = trans_to_acc_number;
        this.trans_payee_name = trans_payee_name;
        this.trans_message = trans_message;
        this.trans_bank_bic = trans_bank_bic;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, int trans_ref_number, String trans_from_acc_number, String trans_to_acc_number, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_ref_number = trans_ref_number;
        this.trans_from_acc_number = trans_from_acc_number;
        this.trans_to_acc_number = trans_to_acc_number;
        this.trans_payee_name = trans_payee_name;
        this.trans_message = trans_message;
        this.trans_bank_bic = trans_bank_bic;
        this.trans_amount = trans_amount;
    }

    public Transaction(int trans_type, int trans_ref_number, Account trans_from_acc, Account trans_to_acc, String trans_payee_name, String trans_message, String trans_bank_bic, double trans_amount) {
        this.trans_type = trans_type;
        this.trans_ref_number = trans_ref_number;
        this.trans_from_acc_number = trans_from_acc.getAcc_number();
        this.trans_to_acc_number = trans_to_acc.getAcc_number();
        this.trans_payee_name = trans_payee_name;
        this.trans_message = trans_message;
        this.trans_bank_bic = trans_bank_bic;
        this.trans_amount = trans_amount;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public int getTrans_ref_number() {
        return trans_ref_number;
    }

    public String getTrans_from_acc_number() {
        return trans_from_acc_number;
    }

    public String getTrans_to_acc_number() {
        return trans_to_acc_number;
    }

    public String getTrans_payee_name() {
        return trans_payee_name;
    }

    public String getTrans_message() {
        return trans_message;
    }

    public String getTrans_bank_bic() {
        return trans_bank_bic;
    }

    public double getTrans_amount() {
        return trans_amount;
    }
}