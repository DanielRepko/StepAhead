package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

import java.util.ArrayList;

/**
 * Created by Daniel Repko on 2018-03-29.
 */

public class RunJournal {

    /**
     * PROPERTIES
     */

    private int id;
    private double distanceKM;
    private double distanceMI;
    private double duration;
    private String startTime;
    private int calories;
    private String feeling;
    private String area;
    private int heartRate;
    private String note;
    private double avgPace;
    private double avgSpeed;
    private String weather;


    /**
     * CONTRUCTORS
     */

    public RunJournal(){

    }

    public RunJournal(int id, double distanceKM, double distanceMI, double duration, String startTime, int calories, String feeling, String area, int heartRate, String note, double avgPace, double avgSpeed, String weather) {
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
        this.avgPace = avgPace;
        this.avgSpeed = avgSpeed;
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
     * @param avgPace the average pace that the user ran at
     * @param avgSpeed the average speed that the user ran
     * @param weather the weather during the run
     */
    public RunJournal(double distanceKM, double distanceMI, double duration, String startTime, int calories, String feeling, String area, int heartRate, String note, double avgPace, double avgSpeed, String weather) {
        this.distanceKM = distanceKM;
        this.distanceMI = distanceMI;
        this.duration = duration;
        this.startTime = startTime;
        this.calories = calories;
        this.feeling = feeling;
        this.area = area;
        this.heartRate = heartRate;
        this.note = note;
        this.avgPace = avgPace;
        this.avgSpeed = avgSpeed;
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
        this.distanceMI = this.distanceKM * 0.62137;
    }

    public double getDistanceMI() {
        return distanceMI;
    }

    public void setDistanceMI(double distanceMI) {
        this.distanceMI = distanceMI;
        this.distanceKM = this.distanceMI / 0.62137;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
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

    public double getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(double avgPace) {
        this.avgPace = avgPace;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
