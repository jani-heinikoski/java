package com.jhprog.dabank.data;

public class BankCard {
    private int id, type, ownerAccountId;
    private double withdrawLimit, paymentLimit;
    private String lastWithdrawDate, lastPaymentDate;
    private boolean frozen;

    public static final int TYPE_DEBIT = 1;
    public static final int TYPE_CREDIT = 2;

    public static final int LIMIT_FINLAND = 1;
    public static final int LIMIT_NORDIC_COUNTRIES = 2;
    public static final int LIMIT_EUROPE = 3;
    public static final int LIMIT_WHOLE_WORLD = 4;

    public BankCard(int id, int type, int ownerAccountId, double withdrawLimit, double paymentLimit, String lastWithdrawDate, String lastPaymentDate, boolean frozen) {
        this.id = id;
        this.type = type;
        this.ownerAccountId = ownerAccountId;
        this.withdrawLimit = withdrawLimit;
        this.paymentLimit = paymentLimit;
        this.lastWithdrawDate = lastWithdrawDate;
        this.lastPaymentDate = lastPaymentDate;
        this.frozen = frozen;
    }

    public BankCard(int type, int ownerAccountId, double withdrawLimit, double paymentLimit, String lastWithdrawDate, String lastPaymentDate, boolean frozen) {
        this.type = type;
        this.ownerAccountId = ownerAccountId;
        this.withdrawLimit = withdrawLimit;
        this.paymentLimit = paymentLimit;
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
}
