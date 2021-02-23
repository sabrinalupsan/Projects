package com.example.casatorii;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class Marriage implements Parcelable {
    private Date date;
    private ArrayList<String> names;
    private float price;
    private String buildingName;
    private int noOfPeople;
    private String url;

    protected Marriage(Parcel in) {
        date = (Date) in.readSerializable();
        names = in.createStringArrayList();
        price = in.readFloat();
        buildingName = in.readString();
        noOfPeople = in.readInt();
        url = in.readString();
    }

    public static final Creator<Marriage> CREATOR = new Creator<Marriage>() {
        @Override
        public Marriage createFromParcel(Parcel in) {
            return new Marriage(in);
        }

        @Override
        public Marriage[] newArray(int size) {
            return new Marriage[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return date+" "+names.get(0)+" "+names.get(1)+" "+price+" "+buildingName+" "+noOfPeople;
    }

    public Marriage(Date date, ArrayList<String> names, float price, String buildingName, int noOfPeople, String url) {
        this.date = date;
        this.names = names;
        this.price = price;
        this.buildingName = buildingName;
        this.noOfPeople = noOfPeople;
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(date);
        parcel.writeStringList(names);
        parcel.writeFloat(price);
        parcel.writeString(buildingName);
        parcel.writeInt(noOfPeople);
        parcel.writeString(url);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Marriage m = (Marriage)obj;
        if(this.toString().compareTo(m.toString())==0)
            return true;
        return false;
    }
}
