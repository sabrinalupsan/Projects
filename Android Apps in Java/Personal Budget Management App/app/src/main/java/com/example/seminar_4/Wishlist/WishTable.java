package com.example.seminar_4.Wishlist;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WishTable {


    // Database table
    public static final String TABLE_TODO = "Wish";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_IMPORTANCE = "importance";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_ALERT= "alert";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DEADLINE + " text not null, "
            + COLUMN_IMPORTANCE + " text not null, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_ALERT + " text not null, "
            + COLUMN_COST + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(WishTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }
}