package com.jhprog.easykino;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Bank { // Follows Singleton design principle

    private ArrayList<Account> accounts;
    private static Bank bank = new Bank();

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    private Bank() {
        accounts = new ArrayList<>();
    }

    public void newCurrentAccount(double balance, String accID) {
        accounts.add(new CurrentAccount(balance, accID));
    }

    public void newCreditAccount(double balance, String accID, double credLimit) {
        accounts.add(new CreditAccount(balance, accID, credLimit));
    }

    public void remAccount(String accID) {
        for (Account account : accounts) {
            if (account.getAccID().equals(accID)) {
                accounts.remove(account);
            }
        }
    }

    private Account findAccount(String accID) {
        for (Account acc : accounts) {
            if (acc.getAccID().equals(accID)) {
                return acc;
            }
        }
        return null;
    }
}

abstract class Account {
    private String accID;
    private double balance;

    Account(String accID, double balance) {
        this.accID = accID;
        this.balance = balance;
    }

    public String getAccID() {
        return accID;
    }

    public double getBalance() {
        return balance;
    }
}

class CurrentAccount extends Account {
    public CurrentAccount(double balance, String accID) {
        super(accID, balance);
    }
}

class CreditAccount extends Account {
    private double credLimit;

    public CreditAccount(double balance, String accID, double credLimit) {
        super(accID, balance);
        this.credLimit = credLimit;
    }

    public double getCredLimit() {
        return credLimit;
    }
}

