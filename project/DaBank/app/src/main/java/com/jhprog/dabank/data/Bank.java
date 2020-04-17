/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
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
    // Used to handle transactions for accounts inside of this application
    public boolean handleTransaction(@NonNull CurrentAccount fromAccount, @NonNull Account toAccount, double amount, @Nullable String date) {
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            Transaction transaction;
            toAccount.acc_balance += amount;
            if (date == null) {
                transaction = new Transaction(
                        Transaction.TYPE_PAYMENT,
                        fromAccount.getAcc_number(),
                        toAccount.getAcc_number(),
                        amount
                );
            } else {
                transaction = new Transaction(
                        Transaction.TYPE_PAYMENT,
                        fromAccount.getAcc_number(),
                        toAccount.getAcc_number(),
                        date,
                        amount
                );
            }
            DataManager dataManager = DataManager.getInstance();
            dataManager.insertTransaction(transaction);
            dataManager.updateAccount(toAccount);
            dataManager.updateAccount(fromAccount);
            return true;
        } else {
            return false;
        }
    }
    // Used to handle transactions for accounts outside of this application
    public boolean handleTransaction(@NonNull CurrentAccount fromAccount, @NonNull String toAccountNumber, double amount, @Nullable String date) {
        DataManager dataManager = DataManager.getInstance();
        Account toAccount = dataManager.getAccountByAccountNumber(toAccountNumber);

        if (toAccount != null) {
            return handleTransaction(fromAccount, toAccount, amount, date);
        }

        if (fromAccount.withdraw(amount)) {
            Transaction transaction;
            if (date == null) {
                transaction = new Transaction(
                        Transaction.TYPE_PAYMENT,
                        fromAccount.getAcc_number(),
                        toAccountNumber,
                        amount
                );
            } else {
                transaction = new Transaction(
                        Transaction.TYPE_PAYMENT,
                        fromAccount.getAcc_number(),
                        toAccountNumber,
                        date,
                        amount
                );
            }
            dataManager.insertTransaction(transaction);
            dataManager.updateAccount(fromAccount);
            return true;
        } else {
            return false;
        }
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