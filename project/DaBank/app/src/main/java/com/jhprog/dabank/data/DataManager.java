/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/training/data-storage/sqlite
 * https://www.sqlitetutorial.net/sqlite-like/
 * */
package com.jhprog.dabank.data;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jhprog.dabank.utility.DaBank;

import java.util.ArrayList;


// This class handles all functionality with the SQLite database.
public class DataManager {

    private static DataManager dataManager = null;
    private SQLiteDBHelper dbHelper;
    private SQLiteDatabase database;

    private final String ADMIN_USERNAME = "1337";

    private DataManager() {
        dbHelper = new SQLiteDBHelper(DaBank.getAppContext());
        database = dbHelper.getWritableDatabase();
    }

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    // Contract class for the SQLite database
    private static final class DatabaseContract {
        // Disables the ability to instantiate this class
        private DatabaseContract() {}

        public static class BankTable implements BaseColumns {
            public static final String table_name = "bank";
            public static final String bank_bic  = "bank_bic";
            public static final String bank_name  = "bank_name";
        }
        public static class BankCardTable implements BaseColumns {
            public static final String table_name = "bankcard";
            public static final String bcard_acc_id  = "bcard_acc_id";
            public static final String bcard_type  = "bcard_type";
        }
        public static class CustomerTable implements BaseColumns {
            public static final String cust_bank_id  = "cust_bank_id";
            public static final String table_name = "customer";
            public static final String cust_user  = "cust_user";
            public static final String cust_passwd  = "cust_passwd";
            public static final String cust_salt = "cust_salt";
            public static final String cust_name = "cust_name";
            public static final String cust_address = "cust_address";
            public static final String cust_zipcode = "cust_zipcode";
            public static final String cust_phone  = "cust_phone";
        }
        public static class AccountTable implements BaseColumns {
            public static final String table_name = "account";
            public static final String acc_number  = "acc_number";
            public static final String acc_type  = "acc_type";
            public static final String acc_bank_id = "acc_bank_id";
            public static final String acc_cust_id  = "acc_cust_id";
            public static final String acc_balance = "acc_balance";
            public static final String acc_creditlimit = "acc_creditlimit";
            public static final String acc_withdrawlimit  = "acc_withdrawlimit";
            public static final String acc_duedate  = "acc_duedate";
        }

        public static class PendingTransactionTable implements BaseColumns {
            public static final String table_name = "pending_transaction";
            public static final String pending_transaction_payee_name = "pending_transaction_payee_name";
            public static final String pending_transaction_ref_number = "pending_transaction_ref_number";
            public static final String pending_transaction_message = "pending_transaction_message";
            public static final String pending_transaction_bank_bic = "pending_transaction_bank_bic";
            public static final String pending_transaction_from_acc_number = "pending_transaction_from_acc_number";
            public static final String pending_transaction_to_acc_number = "pending_transaction_to_acc_number";
            public static final String pending_transaction_recurrence = "pending_transaction_recurrence";
            public static final String pending_transaction_amount = "pending_transaction_amount";
            public static final String pending_transaction_last_paid = "pending_transaction_last_paid";
            public static final String pending_transaction_due_date = "pending_transaction_due_date";
        }

        public static class TransactionTable implements BaseColumns {
            public static final String table_name = "transact";
            public static final String trans_payee_name = "trans_payee_name";
            public static final String trans_ref_number = "trans_ref_number";
            public static final String trans_message = "trans_message";
            public static final String trans_bank_bic = "trans_bank_bic";
            public static final String trans_type  = "trans_type";
            public static final String trans_from_acc_number  = "trans_from_acc_number";
            public static final String trans_to_acc_number  = "trans_to_acc_number";
            public static final String trans_date = "trans_date";
            public static final String trans_amount = "trans_amount";
        }

    }

    private final class SQLiteDBHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "data.db";
        public static final int DATABASE_VERSION = 1;

        public SQLiteDBHelper(@Nullable Context context) {
            // Give the application context here, needs to be tied to the whole app's lifecycle!
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            // TODO validate the database here
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.TransactionTable.table_name +
                    " (" + DatabaseContract.TransactionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.TransactionTable.trans_type + " INTEGER NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_payee_name + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_ref_number + " INTEGER,"+
                    DatabaseContract.TransactionTable.trans_message + " VARCHAR(200),"+
                    DatabaseContract.TransactionTable.trans_bank_bic + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_from_acc_number + " VARCHAR(20) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_to_acc_number + " VARCHAR(20) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_amount + " DOUBLE(12,2) NOT NULL," +
                    DatabaseContract.TransactionTable.trans_date + " DATE NOT NULL);";

            db.execSQL(SQL_QUERY);

            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.BankTable.table_name +
                    " (" + DatabaseContract.BankTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.BankTable.bank_bic + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.BankTable.bank_name + " VARCHAR(30) NOT NULL"+
                    ");";

            db.execSQL(SQL_QUERY);

            SQL_QUERY = "INSERT INTO " +
                    DatabaseContract.BankTable.table_name +
                    " ("  +
                    DatabaseContract.BankTable.bank_bic + "," +
                    DatabaseContract.BankTable.bank_name +
                    ") values('DABAFI', 'DaBank'),('STABAFI', 'Star Bank')," +
                    "('FLABAFI', 'Flash Bank'),('SUBAFI', 'Sun Bank');";

            db.execSQL(SQL_QUERY);


            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.BankCardTable.table_name +
                    " (" + DatabaseContract.BankCardTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.BankCardTable.bcard_acc_id + "  INTEGER NOT NULL,"+
                    DatabaseContract.BankCardTable.bcard_type + " INTEGER NOT NULL"+
                    ");";

            db.execSQL(SQL_QUERY);

            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.CustomerTable.table_name +
                    " (" + DatabaseContract.CustomerTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.CustomerTable.cust_bank_id + " INTEGER NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_user + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_passwd + " VARCHAR(64) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_salt + " VARCHAR(64) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_name + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_address + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_zipcode + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_phone + " VARCHAR(30) NOT NULL"+
                    ");";

            db.execSQL(SQL_QUERY);

            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.AccountTable.table_name +
                    " (" + DatabaseContract.AccountTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.AccountTable.acc_type+ "  INTEGER NOT NULL,"+
                    DatabaseContract.AccountTable.acc_cust_id + " INTEGER NOT NULL,"+
                    DatabaseContract.AccountTable.acc_bank_id + " INTEGER NOT NULL," +
                    DatabaseContract.AccountTable.acc_number + " VARCHAR(18) NOT NULL,"+
                    DatabaseContract.AccountTable.acc_balance+ " DOUBLE(12,2) NOT NULL,"+
                    DatabaseContract.AccountTable.acc_creditlimit + " DOUBLE(12,2),"+
                    DatabaseContract.AccountTable.acc_withdrawlimit + " INTEGER,"+
                    DatabaseContract.AccountTable.acc_duedate + "  DATETIME"+
                    ");";

            db.execSQL(SQL_QUERY);

            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.PendingTransactionTable.table_name + " (" +
                    DatabaseContract.PendingTransactionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_from_acc_number + " VARCHAR(20) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_to_acc_number + " VARCHAR(20) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_recurrence + " INTEGER NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_payee_name + " VARCHAR(30) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_ref_number + " INTEGER, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_message + " VARCHAR(200) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_bank_bic + " VARCHAR(30) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_amount + " DOUBLE(12,2) NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_due_date + " DATE NOT NULL, " +
                    DatabaseContract.PendingTransactionTable.pending_transaction_last_paid + " DATE NOT NULL);";

            db.execSQL(SQL_QUERY);

            insertAdmins(db);
            insertTestUsers(db);
            insertAccountsForTestUsers(db);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    // Closes the database connection
    // TODO tie this to Activities lifecycles
    public void closeDatabaseConnection() {
        if (database.isOpen()) {
            database.close();
        }
    }

    private void insertAdmins(SQLiteDatabase db) {
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "SELECT " + DatabaseContract.BankTable._ID + " FROM " +
                    DatabaseContract.BankTable.table_name + ";",
                    null
            );
            if (cursor != null) {
                PasswordHandler passwordHandler = PasswordHandler.getInstance();
                while (cursor.moveToNext()) {
                    Password password = passwordHandler.newPassword("Administrator123!");
                    String INSERT_TRANSACT = "INSERT INTO " + DatabaseContract.CustomerTable.table_name + "(" +
                            DatabaseContract.CustomerTable.cust_bank_id + "," +
                            DatabaseContract.CustomerTable.cust_address + "," +
                            DatabaseContract.CustomerTable.cust_name + "," +
                            DatabaseContract.CustomerTable.cust_passwd + "," +
                            DatabaseContract.CustomerTable.cust_salt + "," +
                            DatabaseContract.CustomerTable.cust_phone + "," +
                            DatabaseContract.CustomerTable.cust_user + "," +
                            DatabaseContract.CustomerTable.cust_zipcode + ") VALUES (" +
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BankTable._ID)) + "," +
                            "'none'," +
                            "'none'," +
                            "'" + password.getHash() + "'," +
                            "'" + password.getSalt() + "'," +
                            "'none'," +
                            "'" + ADMIN_USERNAME + "'," +
                            "'none');";
                    db.execSQL(INSERT_TRANSACT);
                }
                cursor.close();
            }
        }
    }

    private void insertTestUsers(SQLiteDatabase db) {
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "SELECT " + DatabaseContract.BankTable._ID + " FROM " +
                            DatabaseContract.BankTable.table_name + ";",
                    null
            );
            if (cursor != null) {
                PasswordHandler passwordHandler = PasswordHandler.getInstance();
                String INSERT_USER;
                while (cursor.moveToNext()) {
                    Password password = passwordHandler.newPassword("password");
                    INSERT_USER = "INSERT INTO " + DatabaseContract.CustomerTable.table_name + "(" +
                            DatabaseContract.CustomerTable.cust_bank_id + "," +
                            DatabaseContract.CustomerTable.cust_address + "," +
                            DatabaseContract.CustomerTable.cust_name + "," +
                            DatabaseContract.CustomerTable.cust_passwd + "," +
                            DatabaseContract.CustomerTable.cust_salt + "," +
                            DatabaseContract.CustomerTable.cust_phone + "," +
                            DatabaseContract.CustomerTable.cust_user + "," +
                            DatabaseContract.CustomerTable.cust_zipcode + ") VALUES (" +
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BankTable._ID)) + "," +
                            "'none'," +
                            "'Test User'," +
                            "'" + password.getHash() + "'," +
                            "'" + password.getSalt() + "'," +
                            "'none'," +
                            "'username'," +
                            "'none');";
                    db.execSQL(INSERT_USER);
                }
                cursor.close();
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void insertAccountsForTestUsers(SQLiteDatabase db) {
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseContract.CustomerTable._ID + "," +
                        DatabaseContract.CustomerTable.cust_user + "," +
                        DatabaseContract.CustomerTable.cust_bank_id + " FROM " +
                        DatabaseContract.CustomerTable.table_name + ";",
        null
            );

            if (cursor != null) {
                String INSERT_ACCOUNT;
                int i = 0;
                while (cursor.moveToNext()) {
                    // Don't add accounts to admins
                    if (cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_user)).equals(ADMIN_USERNAME)) {
                        continue;
                    }
                    // Generate unique account numbers for test users
                    String ACCOUNT_NUMBER =
                        String.format("%s%04d", "FI001111000011", ++i);

                    CurrentAccount account = new CurrentAccount(
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_bank_id)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable._ID)),
                            5000,
                            500,
                            ACCOUNT_NUMBER
                    );

                    INSERT_ACCOUNT = "INSERT INTO " + DatabaseContract.AccountTable.table_name + "(" +
                            DatabaseContract.AccountTable.acc_type + "," +
                            DatabaseContract.AccountTable.acc_cust_id + "," +
                            DatabaseContract.AccountTable.acc_bank_id + "," +
                            DatabaseContract.AccountTable.acc_number + "," +
                            DatabaseContract.AccountTable.acc_balance + "," +
                            DatabaseContract.AccountTable.acc_creditlimit +
                            ") VALUES (" +
                            Account.TYPE_CURRENT + "," +
                            account.getAcc_cust_id() + "," +
                            account.getAcc_bank_id() + "," +
                            "'" + account.getAcc_number() + "'," +
                            account.getAcc_balance() + "," +
                            account.getAcc_creditlimit() + ");";;

                    db.execSQL(INSERT_ACCOUNT);
                }
                cursor.close();
            }

        }
    }

    public void insertCustomer(@NonNull Customer customer) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        String INSERT_TRANSACT = "INSERT INTO " + DatabaseContract.CustomerTable.table_name + "(" +
                DatabaseContract.CustomerTable.cust_bank_id + "," +
                DatabaseContract.CustomerTable.cust_address + "," +
                DatabaseContract.CustomerTable.cust_name + "," +
                DatabaseContract.CustomerTable.cust_passwd + "," +
                DatabaseContract.CustomerTable.cust_salt + "," +
                DatabaseContract.CustomerTable.cust_phone + "," +
                DatabaseContract.CustomerTable.cust_user + "," +
                DatabaseContract.CustomerTable.cust_zipcode + ") VALUES (" +
                customer.getCust_bank_id() + "," +
                "'" + customer.getCust_address() + "'," +
                "'" + customer.getCust_name() + "'," +
                "'" + customer.getCust_passwd().getHash() + "'," +
                "'" + customer.getCust_passwd().getSalt() + "'," +
                "'" + customer.getCust_phone() + "'," +
                "'" + customer.getCust_user() + "'," +
                "'" + customer.getCust_zipcode() + "');";
        database.execSQL(INSERT_TRANSACT);
    }

    public void insertTransaction(Transaction transaction) {

        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String INSERT_TRANSACTION = "";

        if (transaction instanceof NormalTransaction) {
            INSERT_TRANSACTION = "INSERT INTO " + DatabaseContract.TransactionTable.table_name + "(" +
                DatabaseContract.TransactionTable.trans_from_acc_number + "," +
                DatabaseContract.TransactionTable.trans_to_acc_number + "," +
                DatabaseContract.TransactionTable.trans_ref_number + "," +
                DatabaseContract.TransactionTable.trans_message + "," +
                DatabaseContract.TransactionTable.trans_payee_name + "," +
                DatabaseContract.TransactionTable.trans_bank_bic + "," +
                DatabaseContract.TransactionTable.trans_type + "," +
                DatabaseContract.TransactionTable.trans_amount + "," +
                DatabaseContract.TransactionTable.trans_date + ") VALUES (" +
                "'" + transaction.getTrans_from_acc_number() + "'," +
                "'" + transaction.getTrans_to_acc_number() + "'," +
                transaction.getTrans_ref_number() + "," +
                "'" + transaction.getTrans_message() + "'," +
                "'" + transaction.getTrans_payee_name() + "'," +
                "'" + transaction.getTrans_bank_bic() + "'," +
                transaction.getTrans_type() + "," +
                transaction.getTrans_amount() + "," +
                "'" + ((NormalTransaction) transaction).getTrans_date() + "');";
        } else if (transaction instanceof PendingTransaction) { // Pending can only be of type PAYMENT
            INSERT_TRANSACTION = "INSERT INTO " + DatabaseContract.PendingTransactionTable.table_name + "(" +
                DatabaseContract.PendingTransactionTable.pending_transaction_from_acc_number + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_to_acc_number + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_ref_number + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_message + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_payee_name + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_bank_bic + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_amount + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_recurrence + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_last_paid + "," +
                DatabaseContract.PendingTransactionTable.pending_transaction_due_date + ") VALUES (" +
                "'" + transaction.getTrans_from_acc_number() + "'," +
                "'" + transaction.getTrans_to_acc_number() + "'," +
                transaction.getTrans_ref_number() + "," +
                "'" + transaction.getTrans_message() + "'," +
                "'" + transaction.getTrans_payee_name() + "'," +
                "'" + transaction.getTrans_bank_bic() + "'," +
                transaction.getTrans_amount() + "," +
                ((PendingTransaction) transaction).getTrans_recurrence() + "," +
                "'" + ((PendingTransaction) transaction).getLast_paid() + "'," +
                "'" + ((PendingTransaction) transaction).getDue_date() + "');";
        }

        database.execSQL(INSERT_TRANSACTION);
    }

    public Customer getCustomerByID(int id) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        Customer retval = null;

        Cursor cursor = database.rawQuery(
                "SELECT *" +
                        " FROM " + DatabaseContract.CustomerTable.table_name +
                        " WHERE " + DatabaseContract.CustomerTable._ID + "=" + id + ";",
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                retval = new Customer(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable._ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_bank_id)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_user)),
                        new Password(cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_passwd)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_salt))),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_name)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_address)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_zipcode)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_phone))
                );
            }
            cursor.close();
        }

        return retval;
    }

    public Bank getBankByName(@NonNull String bankName) {
        int bank_id;
        String bank_bic;
        Bank bank = null;

        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        Cursor cursor = database.rawQuery(
                "SELECT *" +
                " FROM " + DatabaseContract.BankTable.table_name +
                " WHERE " + DatabaseContract.BankTable.bank_name + "='" + bankName + "';",
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bank_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.BankTable._ID));
                bank_bic = cursor.getString(cursor.getColumnIndex(DatabaseContract.BankTable.bank_bic));
                bank = new Bank(bank_id, bankName, bank_bic);
            }
            cursor.close();
        }

        return bank;
    }

    public ArrayList<Customer> getCustomersByName(int bank_id, @NonNull String cust_name) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        ArrayList<Customer> customers = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.CustomerTable.table_name + " WHERE " +
                DatabaseContract.CustomerTable.cust_name + " LIKE '%" + cust_name + "%' AND " +
                DatabaseContract.CustomerTable.cust_bank_id + "=" + bank_id + ";",
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                customers.add(new Customer(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable._ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_bank_id)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_user)),
                        new Password(cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_passwd)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_salt))),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_name)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_address)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_zipcode)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_phone))
                ));
            }
            cursor.close();
        } else {
            customers = null;
        }

        return customers;
    }

    public boolean accountExists(@NonNull String accountNumber) {
        return getAccountByAccountNumber(accountNumber) != null;
    }

    public Account getAccountByAccountNumber(@NonNull String accountNumber) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        Account account = null;

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.AccountTable.table_name +
                        " WHERE " + DatabaseContract.AccountTable.acc_number + "='" + accountNumber + "';",
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                switch (cursor.getInt(cursor.getColumnIndex(
                        DatabaseContract.AccountTable.acc_type
                ))) {
                    case Account.TYPE_CURRENT:
                        account = new CurrentAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_creditlimit)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number))
                        );
                        break;
                    case Account.TYPE_SAVING:
                        account = new SavingsAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_withdrawlimit)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number))
                        );
                        break;
                    case Account.TYPE_FIXED_TERM:
                        account = new FixedTermAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                Account.TYPE_FIXED_TERM,
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_duedate))
                        );
                        break;
                }
            }
            cursor.close();
        }
        return account;
    }

    public Customer getCustomerByUsername(@NonNull Bank bank, @NonNull String username) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        Customer customer = null;

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.CustomerTable.table_name + " WHERE " + DatabaseContract.CustomerTable.cust_user +
                "='" + username + "' AND " + DatabaseContract.CustomerTable.cust_bank_id + "=" + bank.getBank_id() + ";",
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                customer = new Customer(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable._ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_bank_id)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_user)),
                        new Password(cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_passwd)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_salt))),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_name)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_address)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_zipcode)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerTable.cust_phone))
                );
            }
            cursor.close();
        }

        return customer;
    }

    public void insertAccount(@NonNull Account account) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String INSERT_QUERY = "";

        if (account instanceof CurrentAccount) {
            INSERT_QUERY = "INSERT INTO " + DatabaseContract.AccountTable.table_name + "(" +
                    DatabaseContract.AccountTable.acc_type + "," +
                    DatabaseContract.AccountTable.acc_cust_id + "," +
                    DatabaseContract.AccountTable.acc_bank_id + "," +
                    DatabaseContract.AccountTable.acc_number + "," +
                    DatabaseContract.AccountTable.acc_balance + "," +
                    DatabaseContract.AccountTable.acc_creditlimit +
                    ") VALUES (" +
                    Account.TYPE_CURRENT + "," +
                    account.getAcc_cust_id() + "," +
                    account.getAcc_bank_id() + "," +
                    "'" + account.getAcc_number() + "'," +
                    account.getAcc_balance() + "," +
                    ((CurrentAccount) account).getAcc_creditlimit() + ");";
        } else if (account instanceof SavingsAccount) {
            INSERT_QUERY = "INSERT INTO " + DatabaseContract.AccountTable.table_name + "(" +
                    DatabaseContract.AccountTable.acc_type + "," +
                    DatabaseContract.AccountTable.acc_cust_id + "," +
                    DatabaseContract.AccountTable.acc_bank_id + "," +
                    DatabaseContract.AccountTable.acc_number + "," +
                    DatabaseContract.AccountTable.acc_balance + "," +
                    DatabaseContract.AccountTable.acc_withdrawlimit +
                    ") VALUES (" +
                    Account.TYPE_SAVING + "," +
                    account.getAcc_cust_id() + "," +
                    account.getAcc_bank_id() + "," +
                    "'" + account.getAcc_number() + "'," +
                    account.getAcc_balance() + "," +
                    ((SavingsAccount) account).getAcc_withdrawlimit() + ");";
        } else if (account instanceof FixedTermAccount) {
            INSERT_QUERY = "INSERT INTO " + DatabaseContract.AccountTable.table_name + "(" +
                    DatabaseContract.AccountTable.acc_type + "," +
                    DatabaseContract.AccountTable.acc_cust_id + "," +
                    DatabaseContract.AccountTable.acc_bank_id + "," +
                    DatabaseContract.AccountTable.acc_number + "," +
                    DatabaseContract.AccountTable.acc_balance + "," +
                    DatabaseContract.AccountTable.acc_duedate +
                    ") VALUES (" +
                    Account.TYPE_FIXED_TERM + "," +
                    account.getAcc_cust_id() + "," +
                    account.getAcc_bank_id() + "," +
                    "'" + account.getAcc_number() + "'," +
                    account.getAcc_balance() + "," +
                    "'" + ((FixedTermAccount) account).getAcc_due_date() + "');";
        }


        if (!INSERT_QUERY.equals("")) {
            database.execSQL(INSERT_QUERY);
        }

    }

    public ArrayList<Account> getCustomerAccounts(int bank_id, int cust_id) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.AccountTable.table_name + " WHERE " +
                DatabaseContract.AccountTable.acc_cust_id + "=" + cust_id + " AND " +
                DatabaseContract.AccountTable.acc_bank_id + "=" + bank_id + ";",
                null
        );

        ArrayList<Account> accounts = null;

        if (cursor != null) {

            accounts = new ArrayList<>();

            while (cursor.moveToNext()) {
                switch (cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_type))) {
                    case Account.TYPE_CURRENT:
                        accounts.add(new CurrentAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_creditlimit)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number))
                        ));
                        break;
                    case Account.TYPE_SAVING:
                        accounts.add(new SavingsAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_withdrawlimit)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number))
                        ));
                        break;
                    case Account.TYPE_FIXED_TERM:
                        accounts.add(new FixedTermAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable._ID)),
                                Account.TYPE_FIXED_TERM,
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_bank_id)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_cust_id)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_balance)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_number)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.AccountTable.acc_duedate))
                        ));
                        break;
                }
            }
            cursor.close();
        }

        return accounts;
    }

    public void updateAccount(@NonNull Account account) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String UPDATE_ACCOUNT;

        if (account instanceof CurrentAccount) {
            UPDATE_ACCOUNT = "UPDATE " + DatabaseContract.AccountTable.table_name +
                " SET " + DatabaseContract.AccountTable.acc_cust_id + "=" + account.getAcc_cust_id() + ", " +
                DatabaseContract.AccountTable.acc_balance + "=" + account.getAcc_balance() + " WHERE " +
                DatabaseContract.AccountTable._ID + "=" + account.getAcc_id() + ";";
        } else if (account instanceof SavingsAccount) {
            UPDATE_ACCOUNT = "UPDATE " + DatabaseContract.AccountTable.table_name +
                " SET " + DatabaseContract.AccountTable.acc_cust_id + "=" + account.getAcc_cust_id() + ", " +
                DatabaseContract.AccountTable.acc_balance + "=" + account.getAcc_balance() + ", " +
                DatabaseContract.AccountTable.acc_withdrawlimit + "=" + ((SavingsAccount) account).getAcc_withdrawlimit() +
                " WHERE " + DatabaseContract.AccountTable._ID + "=" + account.getAcc_id() + ";";
        } else if (account instanceof FixedTermAccount) {
            UPDATE_ACCOUNT = "UPDATE " + DatabaseContract.AccountTable.table_name +
                " SET " + DatabaseContract.AccountTable.acc_cust_id + "=" + account.getAcc_cust_id() + ", " +
                DatabaseContract.AccountTable.acc_balance + "=" + account.getAcc_balance() +
                " WHERE " + DatabaseContract.AccountTable._ID + "=" + account.getAcc_id() + ";";
        } else {
            return;
        }

        database.execSQL(UPDATE_ACCOUNT);

    }

    public void deletePendingTransaction(@NonNull PendingTransaction transaction) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        if (transaction.getTrans_id() <= 0) {
            return;
        }

        String DELETE_PENDING_TR =
                "DELETE FROM " + DatabaseContract.PendingTransactionTable.table_name +
                        " WHERE " + DatabaseContract.PendingTransactionTable._ID + "=" + transaction.getTrans_id();

        database.execSQL(DELETE_PENDING_TR);
    }

    public ArrayList<PendingTransaction> getPendingTransactions() {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        ArrayList<PendingTransaction> pendingTransactions = null;

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.PendingTransactionTable.table_name + ";",
                null
        );

        if (cursor != null) {
            pendingTransactions = new ArrayList<>();
            while (cursor.moveToNext()) {
                pendingTransactions.add(new PendingTransaction(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable._ID)),
                        Transaction.TYPE_PAYMENT,
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_ref_number)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_from_acc_number)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_to_acc_number)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_payee_name)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_message)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_bank_bic)),
                        cursor.getDouble(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_amount)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_recurrence)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_last_paid)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.PendingTransactionTable.pending_transaction_due_date))
                ));
            }
            cursor.close();
        }

        return pendingTransactions;
    }

    public void updatePendingTransaction(@NonNull PendingTransaction transaction) { // Updates the last paid field from pending tr
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String UPDATE_PENDING = "UPDATE " + DatabaseContract.PendingTransactionTable.table_name +
                " SET " + DatabaseContract.PendingTransactionTable.pending_transaction_last_paid +
                "='" + transaction.getLast_paid() + "';";

        database.execSQL(UPDATE_PENDING);
    }

    public ArrayList<NormalTransaction> getNormalTransactionsByAccNumber(String accountNumber) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        ArrayList<NormalTransaction> transactions = null;
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.TransactionTable.table_name + " WHERE " +
                DatabaseContract.TransactionTable.trans_to_acc_number + "='" + accountNumber + "' OR " +
                DatabaseContract.TransactionTable.trans_from_acc_number + "='" + accountNumber + "' ORDER BY " +
                DatabaseContract.TransactionTable.trans_date + " DESC;",
                null
        );

        if (cursor != null)  {
            transactions = new ArrayList<>();
            while (cursor.moveToNext()) {
                transactions.add(new NormalTransaction(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable._ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_type)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_ref_number)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_from_acc_number)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_to_acc_number)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_payee_name)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_message)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_bank_bic)),
                    cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_amount)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.trans_date))
                ));

            }
            cursor.close();
        }

        return transactions;
    }

    public Bank getBankByID(int id) {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseContract.BankTable.table_name +
                " WHERE " + DatabaseContract.BankTable._ID + "=" + id,
                null
        );

        Bank bank = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bank = new Bank(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.BankTable._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.BankTable.bank_name)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.BankTable.bank_bic))
                );
            }
            cursor.close();
        }

        return bank;
    }

}
