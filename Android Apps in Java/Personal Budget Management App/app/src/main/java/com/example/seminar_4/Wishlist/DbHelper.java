package com.example.seminar_4.Wishlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.seminar_4.model.Wish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DEBUG_TAG = "Wishes";
    private static final String DATABASE_NAME = "WishList.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {

        WishTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        WishTable.onUpgrade(database, oldVersion, newVersion);
    }

    public Cursor getDataCursor() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WishTable.TABLE_TODO, null, null, null, null, null, null, null);
        // Make sure that potential listeners are getting notified
        return cursor;
    }
    public ArrayList<Wish> selectAll() throws ParseException {
        ArrayList<Wish> wishes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT _id, name, importance,deadline, cost,category,alert FROM  Wish", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String id = c.getString(0);
                String name = c.getString(1);
                String importance = c.getString(2);
                float importanceFloat = Float.parseFloat(importance);
                String deadline= c.getString(3);
                //theDate = .parse(date.getText().toString());
                SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                Date currentdate = sdf.parse(deadline);
                String cost = c.getString(4);
                float costFloat = Float.parseFloat(cost);
                String  category= c.getString(5);
                String  alert= c.getString(6);
//                String savings = c.getString(6);
                Wish wish = new Wish(name,importanceFloat,currentdate,costFloat,alert,category);
                wishes.add(wish);
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return wishes;
    }
    public void insertWish(Wish c) {

        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(WishTable.COLUMN_NAME, c.getName());
            cv.put(WishTable.COLUMN_DEADLINE, c.getDeadline().toString());
            cv.put(WishTable.COLUMN_IMPORTANCE, c.getImportance());
            cv.put(WishTable.COLUMN_CATEGORY, c.getCategory());
            cv.put(WishTable.COLUMN_ALERT, c.getAlert());

            cv.put(WishTable.COLUMN_COST, c.getCost());
            Long value = db.insert(WishTable.TABLE_TODO, null, cv);
            Log.d("DatabaseOperation", value.toString());
        }


    public void deleteItemById(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{value};
        int result = db.delete(WishTable.TABLE_TODO,"_id=?", args);
        Log.d("DatabaseOperation", "Deleted value: " + result);
    }
}