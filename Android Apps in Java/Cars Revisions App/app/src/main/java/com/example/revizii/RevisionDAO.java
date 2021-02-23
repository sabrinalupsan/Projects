package com.example.revizii;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.Date;

@Entity(tableName = "Revisions")
public class RevisionDAO {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userPrice")
    private int pret;
    @ColumnInfo(name = "canName")
    private String modelMasina;
    @ColumnInfo(name = "prodName")
    private String producator;
    @ColumnInfo(name = "revisionDate")
    private String dataRevizie;
    @ColumnInfo(name = "type")
    private String type;

    public RevisionDAO() {
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getModelMasina() {
        return modelMasina;
    }

    public void setModelMasina(String modelMasina) {
        this.modelMasina = modelMasina;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public String getDataRevizie() {
        return dataRevizie;
    }

    public void setDataRevizie(String dataRevizie) {
        this.dataRevizie = dataRevizie;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = type;
    }

    public RevisionDAO(Revision r) {
        this.pret = r.getPret();
        this.modelMasina = r.getModelMasina();
        this.producator = r.getProducator();
        this.dataRevizie = r.getDataRevizie().toString();
        this.type = r.getType();
    }



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRevision(Revision revision) {

    }

    @Query("DELETE FROM Revisions")
    void deleteAllUsers() {

    }
}
