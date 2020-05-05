package com.jhprog.dabank.data;

import com.jhprog.dabank.utility.TimeManager;

import java.util.Date;

public class BankCard {
    private int id, type, ownerAccountId, countryLimit;
    private double withdrawLimit, paymentLimit, withdrawn, paid;
    private String lastWithdrawDate, lastPaymentDate;
    private boolean frozen;

    public static final int TYPE_DEBIT = 1;
    public static final int TYPE_CREDIT = 2;

    public static final int LIMIT_FINLAND = 1;
    public static final int LIMIT_NORDIC_COUNTRIES = 2;
    public static final int LIMIT_EUROPE = 3;
    public static final int LIMIT_WHOLE_WORLD = 4;

    public BankCard(int id, int type, int ownerAccountId, int countryLimit, double withdrawLimit, double paymentLimit, double withdrawn, double paid, String lastWithdrawDate, String lastPaymentDate, boolean frozen) {
        this.id = id;
        this.type = type;
        this.ownerAccountId = ownerAccountId;
        this.countryLimit = countryLimit;
        this.withdrawLimit = withdrawLimit;
        this.paymentLimit = paymentLimit;
        this.withdrawn = withdrawn;
        this.paid = paid;
        this.lastWithdrawDate = lastWithdrawDate;
        this.lastPaymentDate = lastPaymentDate;
        this.frozen = frozen;
    }

    public BankCard(int type, int ownerAccountId, int countryLimit, double withdrawLimit, double paymentLimit, double withdrawn, double paid, String lastWithdrawDate, String lastPaymentDate, boolean frozen) {
        this.type = type;
        this.ownerAccountId = ownerAccountId;
        this.countryLimit = countryLimit;
        this.withdrawLimit = withdrawLimit;
        this.paymentLimit = paymentLimit;
        this.withdrawn = withdrawn;
        this.paid = paid;
        this.lastWithdrawDate = lastWithdrawDate;
        this.lastPaymentDate = lastPaymentDate;
        this.frozen = frozen;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getPaymentLimit() {
        return paymentLimit;
    }

    public String getLastWithdrawDate() {
        return lastWithdrawDate;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public int getOwnerAccountId() {
        return ownerAccountId;
    }

    public int getCountryLimit() {
        return countryLimit;
    }

    public double getWithdrawn() {
        return withdrawn;
    }

    public double getPaid() {
        return paid;
    }

    public boolean withdraw(double amount, int countryLimit) {
        TimeManager timeManager = TimeManager.getInstance();
        DataManager dataManager = DataManager.getInstance();
        Date today = timeManager.todayDate();
        Date lastWithdrawn;
        Account ownerAccount = null;
        double limit;

        try {
            lastWithdrawn = timeManager.stringToDate(lastWithdrawDate);
        } catch (Exception e) {
            return false;
        }

        if (countryLimit > this.countryLimit) {
            return false;
        }

        if (timeManager.compareDates(lastWithdrawn, today) == TimeManager.SAME) {
            limit = withdrawn + amount;
        } else {
            limit = amount;
        }

        if (limit > withdrawLimit) {
            return false;
        } else {
            ownerAccount = dataManager.getAccountByID(ownerAccountId);
            if (ownerAccount != null) {
                Bank bank = dataManager.getBankByID(ownerAccount.getAcc_bank_id());
                Customer customer = dataManager.getCustomerByID(ownerAccount.getAcc_cust_id());

                if (bank == null || customer == null) {
                    return false;
                }

                if (bank.handleTransaction(
                        new NormalTransaction(
                                Transaction.TYPE_WITHDRAW,
                                Transaction.REF_NUM_NULL,
                                ownerAccount.getAcc_number(),
                                ownerAccount.getAcc_number(),
                                customer.getCust_name(),
                                Transaction.MESSAGE_NULL,
                                bank.getBank_bic(),
                                amount,
                                timeManager.todayString()
                        ),
                        ownerAccount,
                        null
                )) {
                    withdrawn += amount;
                    this.lastWithdrawDate = timeManager.todayString();
                    dataManager.updateBankCard(this);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
