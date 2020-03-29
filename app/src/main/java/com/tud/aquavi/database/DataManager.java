package com.tud.aquavi.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.tud.aquavi.tables.DrinkInfo;
import com.tud.aquavi.database.DatabaseContract.DrinkEntry;
import com.tud.aquavi.database.DatabaseContract.RecordEntry;
import com.tud.aquavi.tables.RecordInfo;

import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    private static DataManager ourInstance = null;

    private List<DrinkInfo> mDrinks = new ArrayList<>();
    private List<RecordInfo> mRecords = new ArrayList<>();

    public static DataManager getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public DrinkInfo getDrink(String drink_id)
    {
        for (DrinkInfo drink : mDrinks)
        {
            if (drink_id.equals(drink.getDrink_id()))
            {
                return drink;
            }
        }
        return null;
    }

    public List<DrinkInfo> getmDrinks()
    {
        return mDrinks;
    }

    public List<RecordInfo> getmRecords()
    {
        return mRecords;
    }

    public List<String> getDrinkNames()
    {
        List<String> names = new ArrayList<>();

        for (int x = 0; x < mDrinks.size(); x++)
        {
            names.add(mDrinks.get(x).getDrink_id());
        }
        return names;
    }

    public static void loadFromDatabase(DatabaseOpenHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] drinksColumns = {
                DrinkEntry.COLUMN_DRINK_ID,
                DrinkEntry.COLUMN_DRINK_DESCRIPTION};

        final Cursor drinkCursor = db.query(DrinkEntry.TABLE_NAME, drinksColumns,
                null, null, null, null,
                DrinkEntry.COLUMN_DRINK_ID + " DESC");

        String[] recordColumns = {
                RecordEntry.COLUMN_RECORD_DATE_ID,
                RecordEntry.COLUMN_RECORD_DRINK_ID,
                RecordEntry.COLUMN_RECORD_QUANTITY};

        final Cursor recordCursor = db.query(RecordEntry.TABLE_NAME, recordColumns,
                null, null, null, null,
                RecordEntry.COLUMN_RECORD_DATE_ID);

        loadDrinksFromDatabase(drinkCursor);
        loadRecordsFromDatabase(recordCursor);

        drinkCursor.close();
        recordCursor.close();
    }

    public Cursor getAllDrinks(DatabaseOpenHelper dbHelper)
    {
        String selectQuery = "SELECT * FROM drink_info";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getUser(DatabaseOpenHelper dbHelper)
    {
        String selectQuery = "SELECT * FROM user_info";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAllRecords(DatabaseOpenHelper dbHelper)
    {
        String selectQuery = "SELECT _id, date(record_date_id), SUM(record_quantity) FROM record_info GROUP BY record_date_id";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    // good query select * FROM record_entry WHERE date(record_date_id) = date('now') ;
    // SELECT * FROM record_info A INNER JOIN drink_info B on B.drink_id = A.record_drink_id;

    private static void loadDrinksFromDatabase(Cursor cursor)
    {
        int drinkNamePos = cursor.getColumnIndex(DrinkEntry.COLUMN_DRINK_ID);
        int drinkDescriptionPos = cursor.getColumnIndex(DrinkEntry.COLUMN_DRINK_DESCRIPTION);

        DataManager dm = getInstance();

        // clean the list
        dm.mDrinks.clear();

        while (cursor.moveToNext())
        {
            String drinkId = cursor.getString(drinkNamePos);
            String drinkDescription = cursor.getString(drinkDescriptionPos);

//            List<RecordInfo> record = dm.getRecords(recordID);
            DrinkInfo drink = new DrinkInfo(drinkId, drinkDescription);

            dm.mDrinks.add(drink);
        }

        cursor.close();
    }

    private static void loadRecordsFromDatabase(Cursor cursor)
    {
        int dateIdPos = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_DATE_ID);
        int drinkIdPos = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_DRINK_ID);
        int quantityPos = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_QUANTITY);

        DataManager dm = getInstance();

        // clean the list
        dm.mRecords.clear();

        while (cursor.moveToNext())
        {
            String dateId = cursor.getString(dateIdPos);
            String drinkId = cursor.getString(drinkIdPos);
            String quantity = cursor.getString(quantityPos);

            RecordInfo record = new RecordInfo(dateId, drinkId, quantity);

            dm.mRecords.add(record);
        }

        cursor.close();
    }

    public void insertDate(DatabaseOpenHelper dbHelper, String date_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DateEntry.COLUMN_DATE_ID, date_id);

        long newRowId = db.insert(DatabaseContract.DateEntry.TABLE_NAME, null, values);
    }

    public void insertRecord(DatabaseOpenHelper dbHelper, String date_id, String drink_id, int quantity)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_DATE_ID, date_id);
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_DRINK_ID, drink_id);
        values.put(DatabaseContract.RecordEntry.COLUMN_RECORD_QUANTITY, quantity);

        long newRowId = db.insert(DatabaseContract.RecordEntry.TABLE_NAME, null, values);
    }

    public String getDate(DatabaseOpenHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT datetime('now')", null);
        query.moveToNext();
        String date = query.getString(0);
        query.close();
        return date;
    }

    public void deleteRecord(DatabaseOpenHelper dbHelper, String date_id, String drink_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteStatement sqLiteStatement = db.compileStatement("DELETE FROM record_info WHERE record_date_id = ? AND record_drink_id = ?");
        sqLiteStatement.bindString(1, date_id);
        sqLiteStatement.bindString(2, drink_id);
        sqLiteStatement.execute();
    }

    public void updateUser(DatabaseOpenHelper dbHelper, String user_weight, String user_height, String user_goal)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

//        Another way
//        SQLiteStatement sqLiteStatement = db.compileStatement("UPDATE user_info SET user_height = ?, user_weight = ?, user_goal =? WHERE user_id = 1");
//        sqLiteStatement.bindString(1, String.valueOf(user_height));
//        sqLiteStatement.bindString(2, String.valueOf(user_weight));
//        sqLiteStatement.bindString(3, String.valueOf(user_goal));
//        sqLiteStatement.execute();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserEntry.COLUMN_USER_WEIGHT, user_weight);
        values.put(DatabaseContract.UserEntry.COLUMN_USER_HEIGHT, user_height);
        values.put(DatabaseContract.UserEntry.COLUMN_USER_GOAL, user_goal);
        String where = "user_id = " + 1;

        long newRowId = db.update(DatabaseContract.UserEntry.TABLE_NAME, values, where, null);
    }
}