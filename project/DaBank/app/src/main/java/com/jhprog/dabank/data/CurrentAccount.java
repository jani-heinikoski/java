package com.jhprog.dabank.data;

public final class CurrentAccount extends Account {

    public CurrentAccount(int acc_id, int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number) {
        super(acc_id, acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
    }

    public CurrentAccount(int acc_type, int acc_bank_id, int acc_cust_id, double acc_balance, String acc_number) {
        super(acc_type, acc_bank_id, acc_cust_id, acc_balance, acc_number);
    }

    @Override
    public int getType() {
        return Account.TYPE_CURRENT;
    }
}
