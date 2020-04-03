package com.jhprog.sqlscratch;

import android.provider.BaseColumns;

public class DatabaseContract {

    // Estä instansiointi
    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
    }


}
