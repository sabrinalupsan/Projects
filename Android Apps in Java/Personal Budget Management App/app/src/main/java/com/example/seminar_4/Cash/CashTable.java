package com.example.seminar_4.Cash;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CashTable {
    // Database table
    public static final String TABLE_TODO = "cash";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_PLANNED = "planned";
    public static final String COLUMN_SAVINGS = "savings";


    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TYPE + " text not null, "
            + COLUMN_TIME + " text, "
            + COLUMN_AMOUNT + " integer not null, "
            + COLUMN_RATING + " integer not null, "
            + COLUMN_PLANNED + " text not null,"
            + COLUMN_SAVINGS + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(CashTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }
}
