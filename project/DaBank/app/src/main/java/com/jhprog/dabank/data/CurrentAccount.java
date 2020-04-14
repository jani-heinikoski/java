package com.jhprog.dabank.data;

public final class CurrentAccount extends Account {

    public CurrentAccount(int acc_id, int acc_bank_id, int acc_cust_id, double acc_balance) {
        super(acc_id, Account.TYPE_CURRENT, acc_bank_id, acc_cust_id, acc_balance);
    }

    public CurrentAccount(int acc_bank_id, int acc_cust_id, double acc_balance) {
        super(Account.TYPE_CURRENT, acc_bank_id, acc_cust_id, acc_balance);
    }

    @Override
    public int getType() {
        return Account.TYPE_CURRENT;
    }
}
