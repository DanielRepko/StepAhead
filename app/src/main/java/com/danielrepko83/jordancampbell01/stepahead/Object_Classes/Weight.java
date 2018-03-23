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
}
