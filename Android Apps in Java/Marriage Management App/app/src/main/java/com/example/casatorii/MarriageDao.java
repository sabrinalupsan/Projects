package com.example.casatorii;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "marriages")
public class MarriageDao {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    protected String name;

    @ColumnInfo(name = "buildingName")
    protected String buildingName;

    @ColumnInfo(name = "data")
    protected String date;

    @ColumnInfo(name = "price")
    protected float price;

    @ColumnInfo(name = "noOfPeople")
    protected int noOfPeople;

    @ColumnInfo(name = "url")
    protected String url;

    public MarriageDao(Marriage l) {
        name = l.getNames().get(0);
        buildingName = l.getBuildingName();
        date = l.getDate().toString();
        price = l.getPrice();
        noOfPeople = l.getNoOfPeople();
        url = l.getUrl();
    }

    public MarriageDao()
    {

    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
