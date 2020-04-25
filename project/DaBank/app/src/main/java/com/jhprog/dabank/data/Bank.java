/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;


import androidx.annotation.NonNull;

import com.jhprog.dabank.utility.TimeManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Bank {

    private int bank_id;
    private String bank_name;
    private String bank_bic;
    private DataManager dataManager;

    public Bank(int bank_id, String bank_name, String bank_bic) {
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.bank_bic = bank_bic;
        dataManager = DataManager.getInstance();
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
        TimeManager timeManager = TimeManager.getInstance();
        Date dueDate;
        Date today = timeManager.todayDate();

        try {
            dueDate = timeManager.stringToDate(transaction.getDue_date());
        } catch (ParseException e) {
            return false;
        }

        NormalTransaction normalTransaction = new NormalTransaction(
                Transaction.TYPE_PAYMENT,
                transaction.getTrans_from_acc_number(),
                transaction.getTrans_to_acc_number(),
                transaction.getTrans_amount(),
                transaction.getDue_date()
        );
        // Check project documentation in-dev-plans pending_payments.pdf for clarity
        if (transaction.getTrans_recurrence() == PendingTransaction.RECURRENCE_NONE) {

            if (dueDate.compareTo(today) <= 0 && transaction.getLast_paid().equals(PendingTransaction.NEVER_PAID)) {
                if (withdraw(fromAccount, transaction.getTrans_amount())) {
                    deposit(toAccount, transaction.getTrans_amount());
                    transaction.setLast_paid(timeManager.dateToString(dueDate));
                    dataManager.insertTransaction(normalTransaction);
                    dataManager.deletePendingTransaction(transaction);
                } else {
                    return false;
                }
            }

        } else if ( // if the transaction recurrence is weekly/monthly
                transaction.getTrans_recurrence() == PendingTransaction.RECURRENCE_WEEKLY ||
                transaction.getTrans_recurrence() == PendingTransaction.RECURRENCE_MONTHLY
        ) {

            final int noOfDays =
                    transaction.getTrans_recurrence() == PendingTransaction.RECURRENCE_WEEKLY ? 7 : 30;

            if (dueDate.compareTo(today) <= 0) {
                Date date = (Date) dueDate.clone();
                if (transaction.getLast_paid().equals(PendingTransaction.NEVER_PAID)) {
                    do {
                        if (withdraw(fromAccount, transaction.getTrans_amount())) {
                            deposit(toAccount, transaction.getTrans_amount());
                            transaction.setLast_paid(timeManager.dateToString(date));
                            normalTransaction.setTrans_date(timeManager.dateToString(date));
                            timeManager.addTimeToDate(date, noOfDays);
                            dataManager.insertTransaction(normalTransaction);
                        } else {
                            return false;
                        }
                    } while (date.compareTo(today) <= 0);

                } else {
                    timeManager.addTimeToDate(date, noOfDays);
                    while (date.compareTo(today) <= 0) {

                        if (withdraw(fromAccount, transaction.getTrans_amount())) {
                            deposit(toAccount, transaction.getTrans_amount());
                            transaction.setLast_paid(timeManager.dateToString(date));
                            normalTransaction.setTrans_date(timeManager.dateToString(date));
                            timeManager.addTimeToDate(date, noOfDays);
                            dataManager.insertTransaction(normalTransaction);
                        } else {
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    public void checkPendingTransactions() { // Handles pending transactions when user logs in (successfully) to a bank
        ArrayList<PendingTransaction> pendingTransactions = dataManager.getPendingTransactions();

        if (pendingTransactions != null && pendingTransactions.size() > 0) {
            for (PendingTransaction pendingTransaction : pendingTransactions) {
                Account fromAccount = dataManager.getAccountByAccountNumber(pendingTransaction.getTrans_from_acc_number());
                Account toAccount = dataManager.getAccountByAccountNumber(pendingTransaction.getTrans_to_acc_number());
                pendingPayment(pendingTransaction, fromAccount, toAccount);
                updateAccounts(fromAccount, toAccount);
            }
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