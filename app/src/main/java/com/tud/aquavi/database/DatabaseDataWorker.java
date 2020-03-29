package com.tud.aquavi.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

class DatabaseDataWorker
{
    private SQLiteDatabase mDb;

    DatabaseDataWorker(SQLiteDatabase db)
    {
        mDb = db;
    }

    void insertUsers()
    {
        insertUser(1, 60, 150, 500);
    }

    void insertDrinks()
    {
        insertDrink("water", "Good for you");
        insertDrink("vodka", "Alcoholic drink");
        insertDrink("fruit juice", "Some vitamins");
        insertDrink("soda", "Bad for teeth");
    }

    void insertDates()
    {
        insertDate("2019-10-07 08:23:41");
        insertDate("2019-10-08 21:15:19");
        insertDate("2019-10-09 17:10:53");
    }

    void insertRecords()
    {
        insertRecord("2019-10-07 08:23:41", "water", 50);
        insertRecord("2019-10-08 21:15:19", "water", 100);
        insertRecord("2019-10-09 17:10:53", "water", 200);
        insertRecord("2019-11-23 13:14:08", "vodka", 100);
        insertRecord("2019-11-23 13:14:08", "fruit juice", 250);
        insertRecord("2019-11-23 13:14:08", "soda", 330);
    }

    private void insertUser(int user_id, int user_weight, int user_height, int user_goal)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserEntry.COLUMN_USER_ID, user_id);
        values.put(DatabaseContract.UserEntry.COLUMN_USER_HEIGHT, user_height);
        values.put(DatabaseContract.UserEntry.COLUMN_USER_WEIGHT, user_weight);
        values.put(DatabaseContract.UserEntry.COLUMN_USER_GOAL, user_goal);

        long newRowId = mDb.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);

    }

    private void insertDrink(String drink_id, String drink_description)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DrinkEntry.COLUMN_DRINK_ID, drink_id);
        values.put(DatabaseContract.DrinkEntry.COLUMN_DRINK_DESCRIPTION, drink_description);

        long newRowId = mDb.insert(DatabaseContract.DrinkEntry.TABLE_NAME, null, values);
    }

    private void insertDate(String date_id)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DateEntry.COLUMN_DATE_ID, date_id);

        long newRowId = mDb.insert(DatabaseContract.DateEntry.TABLE_NAME, null, values);
    }

    private void insertRecord(String date_id, String drink_id, int quantity)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_DATE_ID, date_id);
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_DRINK_ID, drink_id);
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_QUANTITY, quantity);

        long newRowId = mDb.insert(DatabaseContract.RecordEntry.TABLE_NAME, null, values);
    }
}