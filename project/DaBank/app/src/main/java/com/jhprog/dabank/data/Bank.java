/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Bank {

    private int bank_id;
    private String bank_name;
    private String bank_bic;
    private DataManager dataManager;

    public Bank(int bank_id, String bank_name, String bank_bic) {
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.bank_bic = bank_bic;
    }

    public boolean handleTransaction(@NonNull Transaction transaction, @NonNull Account fromAccount, Account toAccount) {
        dataManager = DataManager.getInstance();

        switch (transaction.getTrans_type()) {
            case Transaction.TYPE_PAYMENT:
                // Only payments can be pending
                if (transaction instanceof NormalTransaction) {
                    if(normalPayment((NormalTransaction) transaction, fromAccount, toAccount)) {
                        dataManager.insertTransaction(transaction);
                        updateAccounts(fromAccount, toAccount);
                        return true;
                    } else {
                        return false;
                    }
                } else if (transaction instanceof PendingTransaction) {
                    if(pendingPayment((PendingTransaction) transaction, fromAccount, toAccount)) {
                        dataManager.insertTransaction(transaction);
                        updateAccounts(fromAccount, toAccount);
                        return true;
                    } else {
                        return false;
                    }
                }
                break;

            case Transaction.TYPE_TRANSFER:
                // Money transfer between customer's own accounts
                if (transfer(fromAccount, toAccount, transaction.getTrans_amount())) {
                    dataManager.insertTransaction(transaction);
                    updateAccounts(fromAccount, toAccount);
                    return true;
                } else {
                    return false;
                }

            case Transaction.TYPE_DEPOSIT:
                deposit(toAccount, transaction.getTrans_amount());
                updateAccounts(toAccount);
                return true;

            case Transaction.TYPE_WITHDRAW:
                if (withdraw(fromAccount, transaction.getTrans_amount())) {
                    updateAccounts(fromAccount);
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }

    // Use this if to- & fromAccount are unknown (gets both from db with account numbers)
    public boolean handleTransaction(@NonNull Transaction transaction) {
        DataManager dataManager = DataManager.getInstance();

        Account fromAcc = dataManager.getAccountByAccountNumber(transaction.getTrans_from_acc_number());
        Account toAcc = dataManager.getAccountByAccountNumber(transaction.getTrans_to_acc_number());

        if (fromAcc != null) {
            return handleTransaction(transaction, fromAcc, toAcc);
        } else {
            return false;
        }

    }

    private void updateAccounts(@NonNull Account ...accounts) {
        if (dataManager == null) {
            dataManager = DataManager.getInstance();
        }
        for (Account acc : accounts) {
            if (acc != null) {
                dataManager.updateAccount(acc);
            }
        }
    }

    private boolean withdraw(Account from, double amount) {// TODO implement withdrawing from FixedTermAccount
        if (from instanceof CurrentAccount) {
            return ((CurrentAccount) from).withdraw(amount);
        } else if (from instanceof SavingsAccount) {
            return ((SavingsAccount) from).withdraw(amount);
        } else {
            return false;
        }
    }

    private void deposit(Account where, double amount) {
        if (where != null) {
            where.deposit(amount);
        }
    }

    private boolean transfer(@NonNull Account fromAccount, @NonNull Account toAccount, double amount) {
        if (withdraw(fromAccount, amount)) {
            deposit(toAccount, amount);
            return true;
        } else {
            return false;
        }
    }

    private boolean normalPayment(@NonNull NormalTransaction transaction, @NonNull Account fromAccount, Account toAccount) {
        if (withdraw(fromAccount, transaction.getTrans_amount())) {
            deposit(toAccount, transaction.getTrans_amount());
            return true;
        } else {
            return false;
        }
    }

    private boolean pendingPayment(@NonNull PendingTransaction transaction, @NonNull Account fromAccount, Account toAccount) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(Calendar.getInstance().getTime());

        if (transaction.getLast_paid().equals(PendingTransaction.NEVER_PAID) && transaction.getDue_date().equals(today)) {
            if (withdraw(fromAccount, transaction.getTrans_amount())) {
                deposit(toAccount, transaction.getTrans_amount());
                transaction.setLast_paid(today);
                // TODO insert regular transaction here
            } else {
                return false;
            }
        }



        return true;
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