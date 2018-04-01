package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

/**
 * Created by Daniel Repko on 4/1/2018.
 */

public class Picture {

    /**
     * PROPERTIES
     */

    private int id;
    private String resource;


    /**
     * CONSTRUCTORS
     */

    public Picture(){

    }

    public Picture(int id, String resource) {
        this.id = id;
        this.resource = resource;
    }

    public Picture(String resource) {
        this.resource = resource;
    }

    /**
     *  GETTERS AND SETTERS
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
