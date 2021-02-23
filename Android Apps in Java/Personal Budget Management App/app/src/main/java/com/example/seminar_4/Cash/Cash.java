package com.example.seminar_4.Cash;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Cash implements Serializable, Parcelable {
    private String type;
    private float cashAmount;
    private Date date;
    private float rating;
    private String planned;
    private String fromSavings;
    private String url;

    protected Cash(Parcel in) {
        type = in.readString();
        cashAmount = in.readFloat();
        rating = in.readFloat();
        planned = in.readString();
        fromSavings = in.readString();
        date = (Date)in.readSerializable();
        url = in.readString();
    }

    public static final Creator<Cash> CREATOR = new Creator<Cash>() {
        @Override
        public Cash createFromParcel(Parcel in) {
            return new Cash(in);
        }

        @Override
        public Cash[] newArray(int size) {
            return new Cash[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        String s = "Type of transaction: "+type+"\nAmount of cash: "+cashAmount+"\nDate of transaction: "+
                date+"\nRating: "+rating+"\nWas the transaction planned? " + planned;
        s+="\nDid the transaction interfere with the savings? " + fromSavings;
        return s;
    }

    public Cash() {
    }

    public Cash(String type, float cashAmount, Date date, float rating, String planned, String fromSavings) {
        this.type = type;
        this.cashAmount = cashAmount;
        this.date = date;
        this.rating = rating;
        this.planned = planned;
        this.fromSavings = fromSavings;
    }

    public Cash(String type, float cashAmount, Date date, float rating, String planned, String fromSavings, String url) {
        this.type = type;
        this.cashAmount = cashAmount;
        this.date = date;
        this.rating = rating;
        this.planned = planned;
        this.fromSavings = fromSavings;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getURL() {
        return url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(float cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String isPlanned() {
        return planned;
    }

    public void setPlanned(String planned) {
        this.planned = planned;
    }

    public String isFromSavings() {
        return fromSavings;
    }

    public void setFromSavings(String fromSavings) {
        this.fromSavings = fromSavings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeFloat(cashAmount);
        parcel.writeFloat(rating);
        parcel.writeString(planned);
        parcel.writeString(fromSavings);
        //parcel.writeBoolean(fromSavings);
        //parcel.writeBoolean(planned);
        parcel.writeSerializable(date);
        parcel.writeString(url);
    }
}
