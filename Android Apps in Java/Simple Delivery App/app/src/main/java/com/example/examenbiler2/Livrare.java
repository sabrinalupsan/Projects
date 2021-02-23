package com.example.examenbiler2;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
enum ENUM {FRAGIL, NORMAL, DUR};
public class Livrare implements Serializable, Parcelable  {

    private String destinatar;
    private String adresa;
    private Date data;
    private float valoare;

    private ENUM tip;

    protected Livrare(Parcel in)  {
        destinatar = in.readString();
        adresa = in.readString();
        data = (Date)in.readSerializable();
        valoare = in.readFloat();
        String tip2 = in.readString();
        tip = ENUM.valueOf(tip2);
    }


    public static final Creator<Livrare> CREATOR = new Creator<Livrare>(){
        @Override
        public Livrare createFromParcel(Parcel in) {
                return new Livrare(in);
        }

        @Override
        public Livrare[] newArray(int size) {
            return new Livrare[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(destinatar);
        parcel.writeString(adresa);
        parcel.writeSerializable(data);
        parcel.writeFloat(valoare);
        parcel.writeString(tip.toString());

    }

    public void readFromParcel(Parcel in) throws ParseException {
        destinatar = in.readString();
        adresa = in.readString();
        data = (Date)in.readSerializable();
        valoare = in.readFloat();
        String tip2 = in.readString();
        tip = ENUM.valueOf(tip2);
    }


    public Livrare(String destinatar, String adresa, Date data, float valoare, String tip) {
        this.destinatar = destinatar;
        this.adresa = adresa;
        this.data = data;
        this.valoare = valoare;
        this.tip = ENUM.valueOf(tip);
    }

    @NonNull
    @Override
    public String toString() {
        return destinatar.toString() + " "+adresa.toString()+" "+data.toString() + " "+valoare+" "+tip;
    }

    public Date getData() {
        return data;
    }

    public String getDestinatar() {
        return destinatar;
    }

    public String getAdresa() {
        return adresa;
    }

    public float getValoare() {
        return valoare;
    }

    public String getTip() {
        return tip.toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Livrare liv = (Livrare) obj;
        if(this.destinatar.compareTo(((Livrare) obj).destinatar)==0 && this.adresa.compareTo(((Livrare) obj).adresa)==0)
            return true;
        return false;
    }
}
