/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/training/data-storage/sqlite
 * */
package com.jhprog.dabank.data;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jhprog.dabank.utility.DaBank;


// This class handles all functionality with the SQLite database.
public class DataManager {

    private static DataManager dataManager = null;
    private SQLiteDBHelper dbHelper;
    private SQLiteDatabase database;

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

        // Inner class that defines the user table contents
        public static class TransactionTable implements BaseColumns {
            public static final String table_name = "transact";
            public static final String trans_type  = "trans_type";
            public static final String trans_from_acc_id  = "trans_from_acc_id";
            public static final String trans_to_acc_id  = "trans_to_acc_id";
            public static final String trans_date = "trans_date";
            public static final String trans_due_date = "trans_due_date";
            public static final String trans_amount = "trans_amount";
            public static final String trans_recurrence = "trans_recurrence";
        }

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
            public static final String table_name = "customer";
            public static final String cust_user  = "cust_user";
            public static final String cust_passwd  = "cust_passwd";
            public static final String cust_name = "cust_name";
            public static final String cust_address = "cust_address";
            public static final String cust_zipcode = "cust_zipcode";
            public static final String cust_phone  = "cust_phone";
        }
        public static class AccountTable implements BaseColumns {
            public static final String table_name = "account";
            public static final String acc_type  = "acc_type";
            public static final String acc_cust_id  = "acc_cust_id";
            public static final String acc_balance = "acc_balance";
            public static final String acc_creditlimit = "acc_creditlimit";
            public static final String acc_interest = "acc_interest";
            public static final String acc_withdrawlimit  = "acc_withdrawlimit";
            public static final String acc_duedate  = "acc_duedate";
        }

    }

    private static final class SQLiteDBHelper extends SQLiteOpenHelper {

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
                    DatabaseContract.TransactionTable.trans_from_acc_id + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_to_acc_id + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.TransactionTable.trans_due_date + " DATETIME NOT NULL," +
                    DatabaseContract.TransactionTable.trans_amount + " DOUBLE(12,2) NOT NULL," +
                    DatabaseContract.TransactionTable.trans_recurrence + " INTEGER NOT NULL," +
                    DatabaseContract.TransactionTable.trans_date + " DATETIME NOT NULL"+
                    ");";

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
                    DatabaseContract.CustomerTable.cust_user+ " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_passwd + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_name + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_address + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_zipcode + " VARCHAR(30) NOT NULL,"+
                    DatabaseContract.CustomerTable.cust_phone + " VARCHAR(30) NOT NULL"+
                    ");" +
                    "";
            db.execSQL(SQL_QUERY);

            SQL_QUERY = "CREATE TABLE " +
                    DatabaseContract.AccountTable.table_name +
                    " (" + DatabaseContract.AccountTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.AccountTable.acc_type+ "  INTEGER  NOT NULL,"+
                    DatabaseContract.AccountTable.acc_cust_id + " INTEGER   NOT NULL,"+
                    DatabaseContract.AccountTable.acc_balance+ " DOUBLE(12,2) NOT NULL,"+
                    DatabaseContract.AccountTable.acc_creditlimit + " DOUBLE(12,2),"+
                    DatabaseContract.AccountTable.acc_interest + " DOUBLE(6,2),"+
                    DatabaseContract.AccountTable.acc_withdrawlimit + " DOUBLE(12,2),"+
                    DatabaseContract.AccountTable.acc_duedate + "  DATETIME"+
                    ");" +
                    "";
            db.execSQL(SQL_QUERY);

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

    public void insertTransaction(Transaction transaction) {
        String INSERT_TRANSACT = "INSERT INTO " + DatabaseContract.TransactionTable.table_name + "(" +
                DatabaseContract.TransactionTable.trans_type + "," +
                DatabaseContract.TransactionTable.trans_from_acc_id + "," +
                DatabaseContract.TransactionTable.trans_to_acc_id + "," +
                DatabaseContract.TransactionTable.trans_due_date + "," +
                DatabaseContract.TransactionTable.trans_amount + "," +
                DatabaseContract.TransactionTable.trans_recurrence + "," +
                DatabaseContract.TransactionTable.trans_date + ")" + " VALUES (" +
                transaction.getTrans_type() + "," +
                transaction.getTrans_from_acc_id() + "," +
                transaction.getTrans_to_acc_id() + "," +
                transaction.getTrans_due_date() + "," +
                transaction.getTrans_amount() + "," +
                transaction.getTrans_recurrence() + "," +
                "datetime('now', 'localtime')" +
                ");";
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        database.execSQL(INSERT_TRANSACT);
    }

    public Customer getCustomerByID(int id) {
        return new Customer(
                id,
                "574783",
                "1999",
                "Jani",
                "Mökkitie 20",
                "48220",
                "0400440040"
        );
    }

    public Customer getCustomerByID(String id) {
        return getCustomerByID(Integer.parseInt(id));
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

        if (cursor.moveToFirst()) {
            bank_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.BankTable._ID));
            bank_bic = cursor.getString(cursor.getColumnIndex(DatabaseContract.BankTable.bank_bic));
            System.out.println("LOGGER: " + bank_id + ", " + bank_bic);
            bank = new Bank(bank_id, bankName, bank_bic);
        }

        cursor.close();

        if (bank == null) {
            System.out.println("LOGGER: BANK WAS NULL! got param: " + bankName);
            System.exit(-1);
        }

        return bank;
    }

}
