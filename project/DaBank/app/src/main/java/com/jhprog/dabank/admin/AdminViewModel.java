/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.admin;

import androidx.lifecycle.ViewModel;

public class AdminViewModel extends ViewModel {

    private int bank_id;
    private int customer_id;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
}
