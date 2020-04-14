package com.jhprog.dabank.data;

import androidx.annotation.NonNull;

import java.util.Locale;

public final class Customer {

    private int cust_id;
    private int cust_bank_id;
    private String cust_user;
    private String cust_passwd;
    private String cust_name;
    private String cust_address;
    private String cust_zipcode;
    private String cust_phone;

    public Customer(int cust_id, int cust_bank_id, String cust_user, String cust_passwd, String cust_name, String cust_address, String cust_zipcode, String cust_phone) {
        this.cust_id = cust_id;
        this.cust_bank_id = cust_bank_id;
        this.cust_user = cust_user;
        this.cust_passwd = cust_passwd;
        this.cust_name = cust_name;
        this.cust_address = cust_address;
        this.cust_zipcode = cust_zipcode;
        this.cust_phone = cust_phone;
    }

    public Customer(int cust_bank_id, String cust_user, String cust_passwd, String cust_name, String cust_address, String cust_zipcode, String cust_phone) {
        this.cust_bank_id = cust_bank_id;
        this.cust_user = cust_user;
        this.cust_passwd = cust_passwd;
        this.cust_name = cust_name;
        this.cust_address = cust_address;
        this.cust_zipcode = cust_zipcode;
        this.cust_phone = cust_phone;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getCust_user() {
        return cust_user;
    }

    public String getCust_passwd() {
        return cust_passwd;
    }

    public String getCust_name() {
        return cust_name;
    }

    public String getCust_address() {
        return cust_address;
    }

    public String getCust_zipcode() {
        return cust_zipcode;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public int getCust_bank_id() {
        return cust_bank_id;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "%d | %s | %s",
                this.cust_id, this.cust_name, this.cust_address);
    }
}
