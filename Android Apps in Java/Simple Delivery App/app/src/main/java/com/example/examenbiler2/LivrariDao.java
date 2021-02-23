package com.example.examenbiler2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LivrariDao {

    @Query("SELECT * FROM Livrari WHERE destinatar=:destinatar2 AND adresa=:adresa2")
    LivrareDao getUser(String destinatar2, String adresa2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(LivrareDao livrareDao);

    @Query("DELETE FROM Livrari")
    void deleteAllLivrari();

}
