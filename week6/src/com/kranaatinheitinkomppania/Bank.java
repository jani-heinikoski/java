/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 6.5.0
 */
package com.kranaatinheitinkomppania;
import java.util.ArrayList;



public class Bank {
    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<Account>();
    }

    private Account search(String accNum) {
        for (Account a : accounts) {
            if (a.getAccNum().equals(accNum)) {
                return a;
            }
        }
        return null;
    }

    public void addDebAcc(String accNum, int balance) {
        accounts.add(new DebitAccount(accNum, balance));
        System.out.println("Account created.");
    }

    public void addCredAcc(String accNum, int balance, int credLim) {
        accounts.add(new CreditAccount(accNum, balance, credLim));
        System.out.println("Account created.");
    }

    public void remAcc(String accNum) {
        Account a = search(accNum);
        if (a != null) {
            accounts.remove(a);
            System.out.println("Account removed.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void deposit(String accNum, int amount) {
        Account a = search(accNum);
        if (a != null) {
            a.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accNum, int amount) {
        Account a = search(accNum);
        if (a != null) {
            a.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void printInfo(String accNum) {
        Account a = search(accNum);
        if (a != null) {
            if (a instanceof DebitAccount) {
                System.out.println("Account number: " + a.getAccNum() + " Amount of money: " + a.getBalance());
            } else if (a instanceof CreditAccount) {
                System.out.println("Account number: " + a.getAccNum() + " Amount of money: " + a.getBalance() + " Credit limit: " + ((CreditAccount)a).getCredLim());
            }

        } else {
            System.out.println("Account not found.");
        }
    }

    public void printAccounts() {
        System.out.println("All accounts:");
        for (Account a : accounts) {
            if (a instanceof DebitAccount) {
                System.out.println("Account number: " + a.getAccNum() + " Amount of money: " + a.getBalance());
            } else if (a instanceof CreditAccount) {
                System.out.println("Account number: " + a.getAccNum() + " Amount of money: " + a.getBalance() + " Credit limit: " + ((CreditAccount)a).getCredLim());
            }
        }
    }

}


