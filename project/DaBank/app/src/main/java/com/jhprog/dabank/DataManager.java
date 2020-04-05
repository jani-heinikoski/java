package com.jhprog.dabank;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

// This class handles all functionality with the SQLite database.
public class DataManager {
    //TODO FIGURE OUT APP CONTEXT
    private static DataManager dataManager = new DataManager();
    private SQLiteDBHelper dbHelper;

    private DataManager() {
        dbHelper = new SQLiteDBHelper(null);
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
    private static final class DatabaseContract {
        // Disables the ability to instantiate this class
        private DatabaseContract() {}

        // Inner class that defines the user table contents
        public static final class UserTable {
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
