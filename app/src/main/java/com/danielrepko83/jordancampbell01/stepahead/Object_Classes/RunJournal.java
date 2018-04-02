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
    private double distance;
    private double duration;
    private String startTime;
    private int calories;
    //private ArrayList<Picture> pictures;
    private String feeling;
    private String area;
    private int heartRate;
    private String note;
    private double avgPace;
    private double avgSpeed;
    private String weather;
    private int measurement = 0;


    /**
     * CONTRUCTORS
     */

    public RunJournal(){

    }

    public RunJournal(int id, double distance, double duration, String startTime, int calories, String feeling, String area, int heartRate,
                      String note, double avgPace, double avgSpeed, String weather, int measurement) {
        this.id = id;
        this.distance = distance;
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
        this.measurement = measurement;
    }

    /**
     * The RunJournal Class is used to store information about a user's run
     * @param distance the distance that was run (measured in either km or miles based on the measurement property)
     * @param startTime the specific time of day when the run began
     * @param calories the amount of calories that were burned
     * @param feeling how the user felt during the run
     * @param area the type of area that the user ran in
     * @param heartRate the user's heart rate during the run
     * @param note a random note that the user can write describing their run
     * @param avgPace the average pace that the user ran at
     * @param avgSpeed the average speed that the user ran
     * @param weather the weather during the run
     * @param measurement a binary value indicating whether the distance is in km(0) or miles(1)
     */
    public RunJournal(double distance, double duration, String startTime, int calories, String feeling, String area, int heartRate,
                      String note, double avgPace, double avgSpeed, String weather, int measurement) {
        this.distance = distance;
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
        this.measurement = measurement;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    //allows the distance and measurement to be set at the same time
    public void setDistance(double distance, int measurement) {
        this.distance = distance;
        this.setMeasurement(measurement);
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

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        if(this.measurement == measurement) {
            this.measurement = measurement;
        } else {
            this.measurement = measurement;

            if(this.measurement == 0){
                double distance = this.getDistance();
                distance = distance / 0.62137;
                this.setDistance(distance);
            } else if(this.measurement == 1){
                double distance = this.getDistance();
                distance = distance * 0.62137;
                this.setDistance(distance);
            }
        }
    }
}
