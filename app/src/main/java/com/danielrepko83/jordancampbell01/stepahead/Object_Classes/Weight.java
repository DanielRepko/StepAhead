package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by web on 2018-03-23.
 */

public class Weight implements Parcelable {
    private int id;
    private Double pounds;
    private String date;

    public Weight() {

    }

    public Weight(Double pounds, String date) {
        this.pounds = pounds;
        this.date = date;
    }

    public Weight(int id, Double pounds, String date) {
        this.id = id;
        this.pounds = pounds;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Double getPounds() {
        return pounds;
    }
    public void setPounds(Double pounds) {
        this.pounds = pounds;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.pounds);
        dest.writeString(this.date);
    }

    protected Weight(Parcel in) {
        this.id = in.readInt();
        this.pounds = in.readDouble();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Weight> CREATOR = new Parcelable.Creator<Weight>() {
        public Weight createFromParcel(Parcel source) { return new Weight(source); }

        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };
}
