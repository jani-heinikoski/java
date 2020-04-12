package com.jhprog.dabank.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Bank {

    private int bank_id;
    private String bank_name;
    private String bank_bic;

    public Bank(int bank_id, String bank_name, String bank_bic) {
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.bank_bic = bank_bic;
    }

    public int getBank_id() {
        return bank_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_bic() {
        return bank_bic;
    }

}