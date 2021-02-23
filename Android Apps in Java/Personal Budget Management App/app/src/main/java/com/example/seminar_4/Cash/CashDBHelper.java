package com.example.seminar_4.Cash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.seminar_4.Cash.Cash;
import com.example.seminar_4.Cash.CashTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CashDBHelper extends SQLiteOpenHelper {

    public static final String DEBUG_TAG = "CashDatabase";
    private static final String DATABASE_NAME = "cashDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public CashDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CashTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,
                          int newVersion) {
        CashTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public Cursor getDataCursor() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CashTable.TABLE_TODO, null, null, null, null, null, null, null);
        // Make sure that potential listeners are getting notified
        return cursor;
    }

    public ArrayList<Cash> selectAll() throws ParseException {
        ArrayList<Cash> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT _id, type, amount, time, rating, planned, savings FROM cash ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String id = c.getString(0);
                String type = c.getString(1);
                String amount = c.getString(2);
                float amountOfCash = Float.parseFloat(amount);
                String time = c.getString(3);
                //theDate = .parse(date.getText().toString());
                SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                Date currentdate = sdf.parse(time);
                String rating = c.getString(4);
                float ratingFloat = Float.parseFloat(rating);
                String planned = c.getString(5);
                String savings = c.getString(6);
                Cash cash = new Cash(type, amountOfCash, currentdate, ratingFloat, planned, savings);
                transactions.add(cash);
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return transactions;
    }

    public void insertSample() {

        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0; i<10; i++) {
            Calendar c = Calendar.getInstance();
            ContentValues cv = new ContentValues();
            cv.put(CashTable.COLUMN_ID, i+1);
            cv.put(CashTable.COLUMN_TYPE, "Income");
            cv.put(CashTable.COLUMN_TIME, c.getTime().toString());
            cv.put(CashTable.COLUMN_AMOUNT, 12.3);
            cv.put(CashTable.COLUMN_PLANNED, "false");
            cv.put(CashTable.COLUMN_SAVINGS, "true");
            Long value = db.insert(CashTable.TABLE_TODO, null, cv);
            Log.d("DatabaseOperation", value.toString());
        }
    }

    public void insertCash(Cash c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CashTable.COLUMN_TYPE, c.getType());
        cv.put(CashTable.COLUMN_TIME, c.getDate().toString());
        cv.put(CashTable.COLUMN_AMOUNT, c.getCashAmount());
        cv.put(CashTable.COLUMN_RATING, c.getRating());
        cv.put(CashTable.COLUMN_PLANNED, c.isPlanned());
        cv.put(CashTable.COLUMN_SAVINGS, c.isFromSavings());
        Long value = db.insert(CashTable.TABLE_TODO, null, cv);
    }

    public void deleteItemById(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{value};
        int result = db.delete(CashTable.TABLE_TODO,"_id=?", args);
        Log.d("DatabaseOperation", "Deleted value: " + result);
    }
}
