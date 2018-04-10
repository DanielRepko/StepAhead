package com.danielrepko83.jordancampbell01.stepahead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Picture;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.RunJournal;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.ArrayList;

/**
 * Created by web on 2018-03-23.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stepahead";

    /* Table Names */
    public static final String TABLE_WEIGHT = "weight";
    public static final String TABLE_RUN = "run";
    public static final String TABLE_PICTURE = "picture";
    public static final String TABLE_RUN_PICTURE = "run_picture";

    /* Column Names - Shared Columns */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";

    /* Column Names - Weight Table */
    public static final String COLUMN_POUNDS = "lbs";

    /* Column Names - Run Table */
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_FEELING = "feeling";
    public static final String COLUMN_AREA = "area";
    public static final String COLUMN_HEART_RATE = "heartRate";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_AVERAGE_PACE = "averagePace";
    public static final String COLUMN_AVERAGE_SPEED = "averageSpeed";
    public static final String COLUMN_WEATHER = "weather";
    public static final String COLUMN_MEASUREMENT = "measurement";

    /* Column Names - Picture Table */
    public static final String COLUMN_RESOURCE = "resource";

    /* Column Names - RunPicture Table */
    public static final String COLUMN_RUN_ID = "runId";
    public static final String COLUMN_PICTURE_ID = "pictureId";

    /* Create statement for Weight Table */
    public static final String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + COLUMN_POUNDS + " REAL,"
            + COLUMN_DATE + " TEXT)";

    /* Create statement for Run Table */
    public static final String CREATE_RUN_TABLE = "CREATE TABLE "+TABLE_RUN+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY NOT NULL,"
            +COLUMN_DISTANCE+" REAL,"
            +COLUMN_DURATION+" TEXT NOT NULL,"
            +COLUMN_START_TIME+" TEXT NOT NULL,"
            +COLUMN_CALORIES+" INTEGER,"
            +COLUMN_FEELING+" TEXT,"
            +COLUMN_AREA+" TEXT,"
            +COLUMN_HEART_RATE+" INTEGER,"
            +COLUMN_NOTE+" TEXT,"
            +COLUMN_AVERAGE_PACE+" REAL,"
            +COLUMN_AVERAGE_SPEED+" REAL,"
            +COLUMN_WEATHER+" TEXT,"
            +COLUMN_MEASUREMENT+" INTEGER NOT NULL)";

    /* Create statement for Picture table */
    public static final String CREATE_PICTURE_TABLE = "CREATE TABLE "+TABLE_PICTURE+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY NOT NULL,"
            +COLUMN_RESOURCE+" TEXT)";

    /* Create statement for RunPicture Table */
    public static final String CREATE_RUN_PICTURE_TABLE = "CREATE TABLE "+TABLE_RUN_PICTURE+"("
            +COLUMN_RUN_ID+" INTEGER REFERENCES "+TABLE_RUN+"("+COLUMN_ID+"),"
            +COLUMN_PICTURE_ID+" INTEGER REFERENCES "+TABLE_PICTURE+"("+COLUMN_ID+"))";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_RUN_TABLE);
        db.execSQL(CREATE_PICTURE_TABLE);
        db.execSQL(CREATE_RUN_PICTURE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUN_PICTURE);
    }

    /* CRUD Operations - Picture Table */

    //Add method
    public int addPicture(Picture picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, picture.getResource());
        db.insert(TABLE_PICTURE,null, values);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int picId = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + picId);
            db.close();
            return picId;
        }
        return -1;
    }

    //Get methods
    public Picture getPicture(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Picture picture = null;
        Cursor cursor = db.query(TABLE_PICTURE,
                new String[]{COLUMN_ID, COLUMN_RESOURCE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            picture = new Picture(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1));
        }
        db.close();
        return picture;
    }

    public ArrayList<Picture> getAllPictures() {
        ArrayList<Picture> picList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PICTURE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                picList.add(new Picture(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1)));
            } while(cursor.moveToNext());
        }
        return picList;
    }

    public ArrayList<Picture> getRunPictures(int runId) {
        ArrayList<Picture> picList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_RUN_PICTURE+" WHERE "+COLUMN_RUN_ID+" = "+runId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                String innerQuery = "SELECT * FROM "+TABLE_PICTURE+" WHERE "+COLUMN_ID+" = "+cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if(innerCursor.moveToFirst()){
                    do{
                        Picture picture = new Picture(Integer.parseInt(innerCursor.getString(0)), innerCursor.getString(1));
                        picList.add(picture);
                    }while(innerCursor.moveToNext());
                }
            }while(cursor.moveToNext());
        }
        return picList;
    }

    //Delete method
    public void deletePicture(int picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PICTURE,
                COLUMN_ID + "= ?",
                new String[]{String.valueOf(picture)});
        db.close();
    }

    /* CRUD Operations - Run Table */

    //Add method
    public void addRun(RunJournal run){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTANCE, run.getDistance());
        values.put(COLUMN_DURATION, run.getDuration());
        values.put(COLUMN_START_TIME, run.getStartTime());
        values.put(COLUMN_CALORIES, run.getCalories());
        values.put(COLUMN_FEELING, run.getFeeling());
        values.put(COLUMN_AREA, run.getArea());
        values.put(COLUMN_HEART_RATE, run.getHeartRate());
        values.put(COLUMN_NOTE, run.getNote());
        values.put(COLUMN_AVERAGE_PACE, run.getAvgPace());
        values.put(COLUMN_AVERAGE_SPEED, run.getAvgSpeed());
        values.put(COLUMN_WEATHER, run.getWeather());
        values.put(COLUMN_MEASUREMENT, run.getMeasurement());
        db.insert(TABLE_RUN, null, values);
        db.close();
    }

    //Get methods
    public RunJournal getRun(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        RunJournal run = null;
        //table name, string array of column names, query, String array of values that will be inserted into the query
        Cursor cursor = db.query(TABLE_RUN,
                new String[]{COLUMN_ID, COLUMN_DISTANCE, COLUMN_DURATION, COLUMN_START_TIME,
                            COLUMN_CALORIES, COLUMN_FEELING, COLUMN_AREA, COLUMN_HEART_RATE,
                            COLUMN_NOTE, COLUMN_AVERAGE_PACE, COLUMN_AVERAGE_SPEED, COLUMN_WEATHER, COLUMN_MEASUREMENT},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            run = new RunJournal(Integer.parseInt(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    Double.parseDouble(cursor.getString(2)),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    Integer.parseInt(cursor.getString(7)),
                    cursor.getString(8),
                    Double.parseDouble(cursor.getString(9)),
                    Double.parseDouble(cursor.getString(10)),
                    cursor.getString(11),
                    Integer.parseInt(cursor.getString(12)));
        }
        db.close();
        return run;
    }

    public ArrayList<RunJournal> getAllRuns(){
        ArrayList<RunJournal> runList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_RUN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                RunJournal run = new RunJournal(Integer.parseInt(cursor.getString(0)),
                        Double.parseDouble(cursor.getString(1)),
                        Double.parseDouble(cursor.getString(2)),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7)),
                        cursor.getString(8),
                        Double.parseDouble(cursor.getString(9)),
                        Double.parseDouble(cursor.getString(10)),
                        cursor.getString(11),
                        Integer.parseInt(cursor.getString(12)));
                runList.add(run);
            }while(cursor.moveToNext());
        }
        db.close();
        return runList;
    }

    //Update method
    public int updateRun(RunJournal run){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTANCE, run.getDistance());
        values.put(COLUMN_DURATION, run.getDuration());
        values.put(COLUMN_START_TIME, run.getStartTime());
        values.put(COLUMN_CALORIES, run.getCalories());
        values.put(COLUMN_FEELING, run.getFeeling());
        values.put(COLUMN_AREA, run.getArea());
        values.put(COLUMN_HEART_RATE, run.getHeartRate());
        values.put(COLUMN_NOTE, run.getNote());
        values.put(COLUMN_AVERAGE_PACE, run.getAvgPace());
        values.put(COLUMN_AVERAGE_SPEED, run.getAvgSpeed());
        values.put(COLUMN_WEATHER, run.getWeather());
        values.put(COLUMN_MEASUREMENT, run.getMeasurement());
        return db.update(TABLE_RUN, values, COLUMN_ID + "= ?",
                new String[]{String.valueOf(run.getId())});
    }

    //Delete method
    public void deleteRun(int run){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RUN, COLUMN_ID + " = ?",
                new String[]{String.valueOf(run)});
        db.close();
    }


    /* CRUD Operations - Weight Table */
    public void addWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POUNDS, weight.getPounds());
        values.put(COLUMN_DATE, weight.getDate());
        db.insert(TABLE_WEIGHT,null, values);
        db.close();
    }

    public Weight getWeight(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Weight weight = null;
        Cursor cursor = db.query(TABLE_WEIGHT,
                new String[]{COLUMN_ID, COLUMN_POUNDS, COLUMN_DATE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(cursor != null) {
            cursor.moveToFirst();
            weight = new Weight(Integer.parseInt(cursor.getString(0)),
                    cursor.getDouble(1),
                    cursor.getString(2));
        }
        db.close();
        return weight;
    }

    public ArrayList<Weight> getAllWeights() {
        ArrayList<Weight> weightList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_WEIGHT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                weightList.add(new Weight(Integer.parseInt(cursor.getString(0)),
                        cursor.getDouble(1),
                        cursor.getString(2)));
            } while(cursor.moveToNext());
        }
        return weightList;
    }

    public int updateWeight(Weight weight) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POUNDS, weight.getPounds());
        values.put(COLUMN_DATE, weight.getDate());
        return db.update(TABLE_WEIGHT, values, COLUMN_ID + "= ?", new String[]{String.valueOf(weight.getId())});
    }

    public void deleteWeight(int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEIGHT,
                COLUMN_ID + "= ?",
                new String[]{String.valueOf(weight)});
        db.close();
    }

    /* Add method for the RunPictureTable */
    public void addRunPicture(int runId, int picId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RUN_ID, runId);
        values.put(COLUMN_PICTURE_ID, picId);
        db.insert(TABLE_RUN_PICTURE, null, values);
        db.close();
    }

}
