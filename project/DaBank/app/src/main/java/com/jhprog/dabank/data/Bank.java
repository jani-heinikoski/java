package com.jhprog.dabank.data;


import androidx.annotation.NonNull;

import com.jhprog.dabank.login.IAuthentication;


public class Bank implements IAuthentication {

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

    @NonNull
    @Override
    public boolean handleAuthentication(String username, String password) {
        //DataManager dataManager = DataManager.getInstance();

        // TODO Authenticate the user here!

        return true;
    }
}