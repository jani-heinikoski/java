package com.jhprog.dabank.data;


import androidx.annotation.NonNull;

import com.jhprog.dabank.login.IAuthentication;


public class Bank implements IAuthentication {

    private int id;
    private String name;

    public Bank(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public boolean handleAuthentication(String username, String password) {
        //DataManager dataManager = DataManager.getInstance();

        // TODO Authenticate the user here!

        return false;
    }
}