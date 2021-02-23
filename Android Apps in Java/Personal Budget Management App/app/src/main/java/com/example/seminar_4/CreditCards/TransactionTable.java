package com.example.seminar_4.CreditCards;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TransactionTable {


    // Database table
    public static final String TABLE_TODO = "'Transaction'";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IBAN = "IBAN";
    public static final String COLUMN_AMOUNT = "ActualAmount";
    public static final String COLUMN_SUMTYPE = "SumType";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_DATE = "Date";



    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + " ("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_IBAN + " text not null, "
            + COLUMN_AMOUNT + " text not null, "
            + COLUMN_SUMTYPE + " text not null, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_DATE + " text not null); ";

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