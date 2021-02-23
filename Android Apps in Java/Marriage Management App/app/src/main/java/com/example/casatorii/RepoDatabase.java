package com.example.casatorii;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MarriageDao.class}, version = 1)
public abstract class RepoDatabase extends RoomDatabase {

    private static volatile RepoDatabase INSTANCE;

    public abstract MarriagesDao marriagesDao();

    public static RepoDatabase getInstance(Context context) {
        if (INSTANCE == null)
        {
            synchronized (RepoDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), RepoDatabase.class, "Sample.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
