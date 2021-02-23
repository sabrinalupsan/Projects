package com.example.seminar_4.CreditCards;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AccountTable {


    // Database table
    public static final String TABLE_TODO = "Account";
    public static final String COLUMN_IBAN = "_id";
    public static final String COLUMN_BANK = "Bank";
    public static final String COLUMN_LIMIT = "AmountLimit";


    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + " ("
            + COLUMN_IBAN + " text primary key, "
            + COLUMN_BANK + " text not null, "
            + COLUMN_LIMIT + " text not null); ";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(AccountTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }
}