package com.danielrepko83.jordancampbell01.stepahead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    /* Column Names - Shared Columns */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";

    /* Column Names - Weight Table */
    public static final String COLUMN_POUNDS = "lbs";
    public static final String COLUMN_KILOGRAMS = "kg";

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

    /* Create statement for Weight Table */
    public static final String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + COLUMN_POUNDS + " REAL,"
            + COLUMN_KILOGRAMS + " REAL,"
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

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_RUN_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUN);
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


    /* CRUD Operations - Weight Table */
    public void addWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POUNDS, weight.getPounds());
        values.put(COLUMN_KILOGRAMS, weight.getKilograms());
        values.put(COLUMN_DATE, weight.getDate());
        db.insert(TABLE_WEIGHT,null, values);
        db.close();
    }

    public Weight getWeight(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Weight weight = null;
        Cursor cursor = db.query(TABLE_WEIGHT,
                new String[]{COLUMN_ID, COLUMN_POUNDS, COLUMN_KILOGRAMS, COLUMN_DATE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(cursor != null) {
            cursor.moveToFirst();
            weight = new Weight(Integer.parseInt(cursor.getString(0)),
                    cursor.getDouble(1),
                    cursor.getDouble(2),
                    cursor.getString(3));
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
                        cursor.getDouble(2),
                        cursor.getString(3)));
            } while(cursor.moveToNext());
        }
        return weightList;
    }

    public int updateWeight(Weight weight) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POUNDS, weight.getPounds());
        values.put(COLUMN_KILOGRAMS, weight.getKilograms());
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

}
