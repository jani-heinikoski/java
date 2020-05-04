package com.jhprog.dabank.data;


public final class CurrentAccount extends Account {

    private double acc_creditlimit;

    public CurrentAccount(int acc_id, int acc_bank_id, int acc_cust_id, double acc_balance, double acc_creditlimit, String acc_number) {
        super(acc_id, Account.TYPE_CURRENT, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_creditlimit = acc_creditlimit;
    }

    public CurrentAccount(int acc_bank_id, int acc_cust_id, double acc_balance, double acc_creditlimit, String acc_number) {
        super(Account.TYPE_CURRENT, acc_bank_id, acc_cust_id, acc_balance, acc_number);
        this.acc_creditlimit = acc_creditlimit;
    }

    @Override
    public int getType() {
        return Account.TYPE_CURRENT;
    }

    public double getAcc_creditlimit() {
        return acc_creditlimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if ((acc_creditlimit + acc_balance) - amount >= 0) {
            this.acc_balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}
