package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

import java.util.ArrayList;

/**
 * Created by Daniel Repko on 2018-03-29.
 */

public class RunJournal {

    private int id;
    private double distance;
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
    private int measurement;


    public RunJournal(){

    }

    public RunJournal(int id, double distance, String startTime, int calories, String feeling, String area, int heartRate,
                      String note, double avgPace, double avgSpeed, String weather, int measurement) {
        this.id = id;
        this.distance = distance;
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
    public RunJournal(double distance, String startTime, int calories, String feeling, String area, int heartRate,
                      String note, double avgPace, double avgSpeed, String weather, int measurement) {
        this.distance = distance;
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
}
