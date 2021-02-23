package com.example.casatorii;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import java.util.ArrayList;

@Dao
public interface MarriagesDao {

    @Query("SELECT * FROM marriages WHERE name=:nume")
    MarriageDao getMarriage(String nume);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMarriage(MarriageDao marriageDao);

    @Query("DELETE FROM marriages")
    void deleteAllMarriages();


}
