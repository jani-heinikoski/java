/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 6.5.0
 */
package com.kranaatinheitinkomppania;

public abstract class Account {
    protected int balance;
    protected String accNum;

    public Account(String accNum, int balance) {
        this.accNum = accNum;
        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }

    }

    public void deposit(int amount) {
        if (amount >= 0) {
            balance += amount;
        } else {
            System.out.println("Can't deposit negative amount.");
        }
    }

    public void withdraw(int amount) {

        if (amount < 0) {
            System.out.println("Can't withdraw negative amount.");
            return;
        }

        if (amount < balance) {
            balance -= amount;
        } else {
            System.out.println("Not enough money.");
        }
    }

    public String getAccNum() {
        return accNum;
    }

    public int getBalance() {
        return balance;
    }
}

class DebitAccount extends Account {
    public DebitAccount(String accNum, int balance) {
        super(accNum, balance);
    }
}

class CreditAccount extends Account {
    private int credLim;

    public CreditAccount(String accNum, int balance, int credLim) {
        super(accNum, balance);
        if (credLim > 0) {
            this.credLim = credLim;
        } else {
            this.credLim = 0;
        }
    }

    @Override
    public void withdraw(int amount) {
        if (amount < (credLim + balance)) {
            balance -= amount;
        } else {
            System.out.println("Not enough credibility.");
        }
    }

    public int getCredLim() {
        return credLim;
    }
}
