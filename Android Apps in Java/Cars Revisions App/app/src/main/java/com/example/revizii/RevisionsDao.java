package com.example.revizii;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RevisionsDao {
    @Query("SELECT * FROM Revisions WHERE userPrice=:price")
    RevisionDAO getRevision(int price);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRevision(RevisionDAO revision);

    @Query("DELETE FROM Revisions")
    void deleteAllRevisions();
}
