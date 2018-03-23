package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

/**
 * Created by web on 2018-03-23.
 */

public class Weight {
    private int id;
    private Double pounds;
    private Double kilograms;
    private String date;

    public Weight() {

    }

    public Weight(Double pounds, Double kilograms, String date) {
        this.pounds = pounds;
        this.kilograms = kilograms;
        this.date = date;
    }

    public Weight(int id, Double pounds, Double kilograms, String date) {
        this.id = id;
        this.pounds = pounds;
        this.kilograms = kilograms;
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

    public Double getKilograms() {
        return kilograms;
    }
    public void setKilograms(Double kilograms) {
        this.kilograms = kilograms;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
