package com.jhprog.dabank.data;

public final class CurrentAccount extends Account {

    public CurrentAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance);
    }

    public CurrentAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance);
    }

    @Override
    public int getType() {
        return Account.TYPE_CURRENT;
    }
}
