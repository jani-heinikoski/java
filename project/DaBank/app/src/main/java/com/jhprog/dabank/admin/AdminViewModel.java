package com.jhprog.dabank.admin;

import androidx.lifecycle.ViewModel;

public class AdminViewModel extends ViewModel {

    private int bank_id;

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
}
