package com.jhprog.sqlscratch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "testi";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_QUERY = "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME +
                " (" + DatabaseContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.FeedEntry.COLUMN_NAME_NAME + " TEXT NOT NULL);";
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
