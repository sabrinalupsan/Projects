package com.example.revizii;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Date;

enum TYPE {
    sport, normal
}

public class Revision implements Parcelable {

    private int pret;
    private String modelMasina;
    private String producator;
    private Date dataRevizie;
    private TYPE type;

    protected Revision(Parcel in) {
        pret = in.readInt();
        modelMasina = in.readString();
        producator = in.readString();
        dataRevizie = (Date)in.readSerializable();
        String type = in.readString();
        this.type = TYPE.valueOf(type);
    }

    public static final Creator<Revision> CREATOR = new Creator<Revision>() {
        @Override
        public Revision createFromParcel(Parcel in) {
            return new Revision(in);
        }

        @Override
        public Revision[] newArray(int size) {
            return new Revision[size];
        }
    };

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

    public Date getDataRevizie() {
        return dataRevizie;
    }

    public void setDataRevizie(Date dataRevizie) {
        this.dataRevizie = dataRevizie;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = TYPE.valueOf(type);
    }

    public Revision(int pret, String modelMasina, String producator, Date dataRevizie, String type) {
        this.pret = pret;
        this.modelMasina = modelMasina;
        this.producator = producator;
        this.dataRevizie = dataRevizie;
        this.type = TYPE.valueOf(type);
    }

    @NonNull
    @Override
    public String toString() {
        return modelMasina+" "+producator+" "+type.toString()+" "+pret+" "+dataRevizie;
    }

    public String almostToString()
    {
        return modelMasina+" "+producator+" "+type.toString()+" "+dataRevizie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(pret);
        parcel.writeString(modelMasina);
        parcel.writeString(producator);
        parcel.writeSerializable(dataRevizie);
        parcel.writeString(type.toString());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Revision r = (Revision)obj;
        if(r.getPret() == this.pret)
            return true;
        else
            return false;
    }

    public static class NameComparator implements Comparator<Revision>
    {
        public int compare(Revision left, Revision right) {
            if(left.producator.compareTo(right.producator)==0 || left.producator.compareTo(right.producator)==1)
                return 0;
            return -1;
        }
    }
    public static class InverseNameComparator implements Comparator<Revision>
    {
        public int compare(Revision left, Revision right) {
            if(left.producator.compareTo(right.producator)==0 || left.producator.compareTo(right.producator)==-1)
                return 0;
            return -1;
        }
    }
    public static class InverseNumberComparator implements Comparator<Revision>
    {
        public int compare(Revision left, Revision right) {
            if(left.pret==right.pret || left.pret < right.pret)
                return 0;
            return -1;
        }
    }
    public static class NumberComparator implements Comparator<Revision>
    {
        public int compare(Revision left, Revision right) {
            if(left.pret==right.pret || left.pret > right.pret)
                return 0;
            return -1;
        }
    }

    public static class NumberAndNameComparator implements Comparator<Revision>
    {

        public int compare(Revision left, Revision right) {
            if(left.producator.compareTo(right.producator)==0)
                if(left.pret==right.pret || left.pret > right.pret)
                    return 0;
                else
                    return -1;
            if( left.producator.compareTo(right.producator)==1)
                return -1;
            return -1;
        }
    }
    public static class InverseNumberAndNameComparator implements Comparator<Revision>
    {

        public int compare(Revision left, Revision right) {
            if(left.producator.compareTo(right.producator)==0)
                if(left.pret==right.pret || left.pret < right.pret)
                    return 0;
                else
                    return -1;
            if( left.producator.compareTo(right.producator)==1)
                return -1;
            return -1;
        }
    }
}
