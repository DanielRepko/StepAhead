package com.danielrepko83.jordancampbell01.stepahead.Object_Classes;

/**
 * Created by Daniel Repko on 4/1/2018.
 */

public class Picture {

    /**
     * PROPERTIES
     */

    private int id;
    private int runId;
    private String resource;


    /**
     * CONSTRUCTORS
     */

    public Picture(){

    }

    /**
     * The Picture class is used to hold image resources to be used with Run Journals
     * @param id the id of the picture in the database
     * @param runId the id of the run journal the Picture is associated with
     * @param resource the resource code of the image
     */
    public Picture(int id, int runId, String resource) {
        this.id = id;
        this.runId = runId;
        this.resource = resource;
    }

    public Picture(int runId, String resource) {
        this.runId = runId;
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

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
