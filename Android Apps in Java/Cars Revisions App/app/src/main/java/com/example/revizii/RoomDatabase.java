package com.example.revizii;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {RevisionDAO.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static volatile RoomDatabase INSTANCE;

    public abstract RevisionsDao revisionsDAO();

    public static RoomDatabase getInstance(Context context) {
        if (INSTANCE == null)
        {
            synchronized (RoomDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), RoomDatabase.class, "Sample.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
