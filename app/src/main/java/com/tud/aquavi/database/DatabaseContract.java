package com.tud.aquavi.database;

import android.provider.BaseColumns;


// Database class
final class DatabaseContract
{
    private DatabaseContract()
    {
    }

    static final class DrinkEntry implements BaseColumns
    {
        static final String TABLE_NAME = "drink_info";
        static final String COLUMN_DRINK_ID = "drink_id";
        static final String COLUMN_DRINK_DESCRIPTION = "drink_description";

        static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_DRINK_ID + " TEXT UNIQUE, "
                + COLUMN_DRINK_DESCRIPTION + " TEXT)";
    }

    static final class DateEntry implements BaseColumns
    {
        static final String TABLE_NAME = "date_info";
        static final String COLUMN_DATE_ID = "drink_id";

        static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_DATE_ID + " TEXT UNIQUE)";
    }

    static final class UserEntry implements BaseColumns
    {
        static final String TABLE_NAME = "user_info";
        static final String COLUMN_USER_ID = "user_id";
        static final String COLUMN_USER_WEIGHT = "user_weight";
        static final String COLUMN_USER_HEIGHT = "user_height";
        static final String COLUMN_USER_GOAL = "user_goal";

        static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_USER_ID + " INT UNIQUE, "
                + COLUMN_USER_WEIGHT + " INT, "
                + COLUMN_USER_HEIGHT + " INT, "
                + COLUMN_USER_GOAL + " INT)";
    }

    static final class RecordEntry implements BaseColumns
    {
        static final String TABLE_NAME = "record_info";
        static final String COLUMN_RECORD_DATE_ID = "record_date_id";
        static final String COLUMN_RECORD_DRINK_ID = "record_drink_id";
        static final String COLUMN_RECORD_QUANTITY = "record_quantity";

        static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_RECORD_DATE_ID + " TEXT, "
                + COLUMN_RECORD_DRINK_ID + " TEXT, "
                + COLUMN_RECORD_QUANTITY + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_RECORD_DATE_ID + ") REFERENCES " + DrinkEntry.TABLE_NAME + "(" + DrinkEntry.COLUMN_DRINK_ID + "), "
                + "FOREIGN KEY(" + COLUMN_RECORD_DRINK_ID + ") REFERENCES " + DrinkEntry.TABLE_NAME + "(" + DrinkEntry.COLUMN_DRINK_ID + "))";
    }
}
