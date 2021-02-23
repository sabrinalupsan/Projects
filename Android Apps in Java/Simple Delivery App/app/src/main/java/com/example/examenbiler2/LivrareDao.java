package com.example.examenbiler2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "livrari")
public class LivrareDao {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "livrareid")
    protected String mId;

    @ColumnInfo(name = "destinatar")
    protected String mDestinatar;

    @ColumnInfo(name = "adresa")
    protected String mAdresa;

    @ColumnInfo(name = "data")
    protected String mData;

    @ColumnInfo(name = "valoare")
    protected float mValoare;

    @ColumnInfo(name = "tip")
    protected String mTip;

    public LivrareDao(Livrare l) {
        mId = UUID.randomUUID().toString();
        mDestinatar = l.getDestinatar();
        mAdresa = l.getAdresa();
        mData = l.getData().toString();
        mValoare = l.getValoare();
        mTip = l.getTip();
    }

    @Ignore
    public LivrareDao(String id, String mDestinatar) {
        this.mId = id;
        this.mDestinatar = mDestinatar;
    }

    public LivrareDao()
    {

    }

    public String getId() {
        return mId;
    }

    public String getmDestinatar() {
        return mDestinatar;
    }

    public String getmAdresa() {
        return mAdresa;
    }

    public String getmData() {
        return mData;
    }

    public float getmValoare() {
        return mValoare;
    }

    public String getmTip() {
        return mTip;
    }
}
