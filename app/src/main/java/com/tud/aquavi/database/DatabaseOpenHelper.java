package com.tud.aquavi.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public final class DatabaseOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "AquaVi.db";
    private static final int DATABASE_VERSION = 1;

    // Factory is used to customize the database interaction.
    public DatabaseOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Get called when a getRead or getWrite is invoked
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //CREATE TABLES HERE!
        try
        {
            db.execSQL(DatabaseContract.DrinkEntry.SQL_CREATE_TABLE);
            db.execSQL(DatabaseContract.RecordEntry.SQL_CREATE_TABLE);
            db.execSQL(DatabaseContract.DateEntry.SQL_CREATE_TABLE);
            db.execSQL(DatabaseContract.UserEntry.SQL_CREATE_TABLE);
        } catch (SQLException e)
        {
            Log.e("Error executing SQL", e.toString());
        }

        // Seed for database
        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertDrinks();
        worker.insertRecords();
        worker.insertDates();
        worker.insertUsers();
    }

    // update database structure
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if it exists
        try
        {
            db.execSQL("DROP TABLE IF EXISTS " + "drink_info");
            db.execSQL("DROP TABLE IF EXISTS " + "date_info");
            db.execSQL("DROP TABLE IF EXISTS " + "user_info");
            db.execSQL("DROP TABLE IF EXISTS " + "record_info");
        } catch (SQLException e)
        {
            Log.e("Error executing SQL", e.toString());
        }

        // Create tables again
        onCreate(db);
    }
}