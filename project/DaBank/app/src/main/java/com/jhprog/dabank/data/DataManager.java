/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/training/data-storage/sqlite
 * */
package com.jhprog.dabank.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.jhprog.dabank.utility.DaBank;

import java.util.Locale;

// This class handles all functionality with the SQLite database.
public class DataManager {

    private static DataManager dataManager = new DataManager();
    private SQLiteDBHelper dbHelper;

    private DataManager() {
        dbHelper = new SQLiteDBHelper(DaBank.getAppContext());
        try {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }
    // Authentication class in order to validate user credentials
    private static final class UserAuthenticator {

        private String username;
        private String password;
        private Bank bank;

        public UserAuthenticator(String username, String password, Bank bank) {
            this.username = username;
            this.password = password;
            this.bank = bank;
        }

    }
    // Contract class for the SQLite database
    private static final class Contract {
        // Disables the ability to instantiate this class
        private Contract() {}

        // Inner class that defines the user table contents
        public static final class UserTable implements BaseColumns {
            public static final String TABLE_NAME = "user";
            public static final String COLUMN_USERNAME = "username";
            public static final String COLUMN_PASSWORD = "password";
            public static final String COLUMN_BANK_ID = "b_id";
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
            // TODO create the database here
            final String SQL_QUERY = String.format(Locale.getDefault(),
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);",
                    Contract.UserTable.TABLE_NAME, Contract.UserTable._ID, Contract.UserTable.COLUMN_USERNAME, Contract.UserTable.COLUMN_PASSWORD
            );
            db.execSQL(SQL_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
