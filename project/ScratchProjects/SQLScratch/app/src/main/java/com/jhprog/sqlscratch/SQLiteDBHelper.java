package com.jhprog.sqlscratch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ban.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_QUERY = "CREATE TABLE " +
                DatabaseContract.TransactionTable.table_name +
                " (" + DatabaseContract.TransactionTable.trans_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TransactionTable.trans_type + " INTEGER NOT NULL,"+
                DatabaseContract.TransactionTable.trans_from_acc_id + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.TransactionTable.trans_to_acc_id + " VARCHAR(30) NOT NULL,"+
                DatabaseContract.TransactionTable.trans_date + " DATETIME NOT NULL"+
                ");";

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
