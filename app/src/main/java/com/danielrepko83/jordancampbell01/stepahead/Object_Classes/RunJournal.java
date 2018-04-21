package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Daniel Repko on 2018-03-29.
 */

public class RunJournal implements Parcelable{

    /**
     * PROPERTIES
     */

    private int id;
    private double distanceKM;
    private double distanceMI;
    private String duration;
    private String startTime;
    private int calories;
    private String feeling;
    private String area;
    private int heartRate;
    private String note;
    private String weather;


    /**
     * CONTRUCTORS
     */

    public RunJournal(){

    }

    public RunJournal(int id, double distanceKM, double distanceMI, String duration, String startTime, int calories, String feeling, String area, int heartRate, String note, String weather) {
        this.id = id;
        this.distanceKM = distanceKM;
        this.distanceMI = distanceMI;
        this.duration = duration;
        this.startTime = startTime;
        this.calories = calories;
        this.feeling = feeling;
        this.area = area;
        this.heartRate = heartRate;
        this.note = note;
        this.weather = weather;
    }

    /**
     * The RunJournal Class is used to store information about a user's run
     * @param distanceKM the distance that was run in km
     * @param distanceMI the distance that was run in miles
     * @param startTime the specific time of day when the run began
     * @param calories the amount of calories that were burned
     * @param feeling how the user felt during the run
     * @param area the type of area that the user ran in
     * @param heartRate the user's heart rate during the run
     * @param note a random note that the user can write describing their run
     * @param weather the weather during the run
     */
    public RunJournal(double distanceKM, double distanceMI, String duration, String startTime, int calories, String feeling, String area, int heartRate, String note, String weather) {
        this.distanceKM = distanceKM;
        this.distanceMI = distanceMI;
        this.duration = duration;
        this.startTime = startTime;
        this.calories = calories;
        this.feeling = feeling;
        this.area = area;
        this.heartRate = heartRate;
        this.note = note;
        this.weather = weather;
    }

    /**
     * METHODS
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(double distanceKM) {
        this.distanceKM = distanceKM;
        this.distanceMI = Double.parseDouble(String.format("%.2f",this.distanceKM * 0.62137));
    }

    public double getDistanceMI() {
        return distanceMI;
    }

    public void setDistanceMI(double distanceMI) {
        this.distanceMI = distanceMI;
        this.distanceKM = Double.parseDouble(String.format("%.2f",this.distanceMI / 0.62137));
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.distanceKM);
        dest.writeDouble(this.distanceMI);
        dest.writeString(this.duration);
        dest.writeString(this.startTime);
        dest.writeInt(this.calories);
        dest.writeString(this.feeling);
        dest.writeString(this.area);
        dest.writeInt(this.heartRate);
        dest.writeString(this.note);
        dest.writeString(this.weather);
    }

    protected RunJournal(Parcel in){
        this.id = in.readInt();
        this.distanceKM = in.readDouble();
        this.distanceMI = in.readDouble();
        this.duration = in.readString();
        this.startTime = in.readString();
        this.calories = in.readInt();
        this.feeling = in.readString();
        this.area = in.readString();
        this.heartRate = in.readInt();
        this.note = in.readString();
        this.weather = in.readString();
    }

    public static final Creator<RunJournal> CREATOR = new Creator<RunJournal>() {
        @Override
        public RunJournal createFromParcel(Parcel source) {
            return new RunJournal(source);
        }

        @Override
        public RunJournal[] newArray(int size) {
            return new RunJournal[size];
        }
    };


}
