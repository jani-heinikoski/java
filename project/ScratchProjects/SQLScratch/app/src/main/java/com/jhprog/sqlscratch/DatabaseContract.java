package com.jhprog.sqlscratch;

import android.provider.BaseColumns;

public class DatabaseContract {

    // Estä instansiointi
    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class TransactionTable {
        public static final String table_name = "transact";
        public static final String trans_id = "trans_id";
        public static final String trans_type  = "trans_type";
        public static final String trans_from_acc_id  = "trans_from_acc_id";
        public static final String trans_to_acc_id  = "trans_to_acc_id";
        public static final String trans_date = "trans_date";
    }

    public static class BankTable {
        public static final String table_name = "bank";
        public static final String bank_id = "bank_id";
        public static final String bank_bic  = "bank_bic";
        public static final String bank_name  = "bank_name";
    }
    public static class BankCardTable {
        public static final String table_name = "bankcard";
        public static final String bcard_id = "bcard_id";
        public static final String bcard_acc_id  = "bcard_acc_id";
        public static final String bcard_type  = "bcard_type int";
    }
    public static class CustomerTable {
        public static final String table_name = "customer";
        public static final String cust_id = "cust_id";
        public static final String cust_user  = "cust_user";
        public static final String cust_passwd  = "cust_passwd";
        public static final String cust_name = "cust_name";
        public static final String cust_address = "cust_address";
        public static final String cust_zipcode = "cust_zipcode";
        public static final String cust_phone  = "cust_phone";
    }
    public static class AccountTable {
        public static final String table_name = "account";
        public static final String acc_id = "acc_id";
        public static final String acc_type  = "acc_type";
        public static final String acc_cust_id  = "acc_cust_id";
        public static final String acc_balance = "acc_balance";
        public static final String acc_creditlimit = "acc_creditlimit";
        public static final String acc_interest = "acc_interest";
        public static final String acc_withdrawlimit  = "acc_withdrawlimit";
        public static final String acc_duedate  = "acc_duedate";
    }
}
