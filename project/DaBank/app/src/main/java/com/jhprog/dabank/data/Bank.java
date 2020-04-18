/*
 * Author: Jani Olavi Heinikoski
 * Date: 13.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;


import androidx.annotation.NonNull;

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

    public boolean handleTransaction(@NonNull Transaction transaction, @NonNull Account fromAccount, @NonNull Account toAccount) {
        dataManager = DataManager.getInstance();

        switch (transaction.getTrans_type()) {
            case Transaction.TYPE_PAYMENT:
                // Only payments can be pending
                if (transaction instanceof NormalTransaction) {
                    if(normalPayment(transaction, fromAccount, toAccount)) {
                        dataManager.insertTransaction(transaction);
                        updateAccounts(fromAccount, toAccount);
                    } else {
                        return false;
                    }
                } else if (transaction instanceof PendingTransaction) {
                    if(pendingPayment(transaction, fromAccount, toAccount)) {
                        dataManager.insertTransaction(transaction);
                        updateAccounts(fromAccount, toAccount);
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
                } else {
                    return false;
                }
                break;

            case Transaction.TYPE_DEPOSIT:
                deposit(toAccount, transaction.getTrans_amount());
                updateAccounts(toAccount);
                break;

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

    // Use this if to- & fromAccount are unknown (gets both from db with account id's)
    public boolean handleTransaction(@NonNull Transaction transaction) {
        DataManager dataManager = DataManager.getInstance();

        Account fromAcc = dataManager.getAccountByID(transaction.getTrans_from_acc_id());
        Account toAcc = dataManager.getAccountByID(transaction.getTrans_to_acc_id());

        if (fromAcc != null && toAcc != null) {
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
            dataManager.updateAccount(acc);
        }
    }

    private boolean withdraw(Account from, double amount) {
        if (from instanceof CurrentAccount) {
            return ((CurrentAccount) from).withdraw(amount);
        } else if (from instanceof SavingsAccount) {
            // TODO implement savingsaccount withdraw
            return true;
        } else {
            return false;
        }
    }

    private void deposit(@NonNull Account where, double amount) {
        where.deposit(amount);
    }

    private boolean transfer(@NonNull Account fromAccount, @NonNull Account toAccount, double amount) {
        if (fromAccount instanceof CurrentAccount) {
            if (fromAccount.getAcc_number().equals(toAccount.getAcc_number())) {
                return false;
            }
            if (((CurrentAccount) fromAccount).withdraw(amount)) {
                toAccount.deposit(amount);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean normalPayment(@NonNull Transaction transaction, @NonNull Account fromAccount, @NonNull Account toAccount) {
        if (fromAccount instanceof CurrentAccount) {
            if (((CurrentAccount) fromAccount).withdraw(transaction.getTrans_amount())) {
                toAccount.deposit(transaction.getTrans_amount());
            } else {
                return false;
            }
        } else if (fromAccount instanceof SavingsAccount) {
            if (((SavingsAccount) fromAccount).withdraw(transaction.getTrans_amount())) {
                toAccount.deposit(transaction.getTrans_amount());
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean pendingPayment(@NonNull Transaction transaction, @NonNull Account fromAccount, @NonNull Account toAccount) {
        return false;
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