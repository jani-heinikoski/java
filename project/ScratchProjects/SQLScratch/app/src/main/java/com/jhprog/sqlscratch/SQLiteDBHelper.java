package com.jhprog.sqlscratch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "banv3.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.TransactionTable.table_name +
                " (" + DatabaseContract.TransactionTable.trans_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TransactionTable.trans_type + " INTEGER NOT NULL,"+
                DatabaseContract.TransactionTable.trans_from_acc_id + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.TransactionTable.trans_to_acc_id + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.TransactionTable.trans_date + " DATETIME NOT NULL"+
                ");";

        db.execSQL(SQL_QUERY);
        // todo tätän debug out
        SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.BankTable.table_name +
                " (" + DatabaseContract.BankTable.bank_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.BankTable.bank_bic + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.BankTable.bank_name + " VARCHAR(30) NOT NULL"+
                ");";

        db.execSQL(SQL_QUERY);
// todo tätän debug out

        SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.BankCardTable.table_name +
                " (" + DatabaseContract.BankCardTable.bcard_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.BankCardTable.bcard_acc_id + "  INTEGER NOT NULL,"+
                DatabaseContract.BankCardTable.bcard_type + " INTEGER NOT NULL"+
                ");";

        db.execSQL(SQL_QUERY);
    // todo tätän debug out
        SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.CustomerTable.table_name +
                " (" + DatabaseContract.CustomerTable.cust_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.CustomerTable.cust_user+ " VARCHAR(30) NOT NULL,"+
                DatabaseContract.CustomerTable.cust_passwd + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.CustomerTable.cust_name + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.CustomerTable.cust_address + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.CustomerTable.cust_zipcode + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.CustomerTable.cust_phone + " VARCHAR(30) NOT NULL"+
                ");" +
                "";
        db.execSQL(SQL_QUERY);
        // todo tätän debug out
        SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.AccountTable.table_name +
                " (" + DatabaseContract.AccountTable.acc_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }
}
