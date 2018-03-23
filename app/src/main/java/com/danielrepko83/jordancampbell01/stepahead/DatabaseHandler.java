package com.danielrepko83.jordancampbell01.stepahead;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

/**
 * Created by web on 2018-03-23.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stepahead";

    /* Table Names */
    public static final String TABLE_WEIGHT = "weight";

    /* Column Names - Shared Columns */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";

    /* Column Names - Weight Table */
    public static final String COLUMN_POUNDS = "lbs";
    public static final String COLUMN_KILOGRAMS = "kg";

    /* Create statements for Tables */
    public static final String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_POUNDS + " REAL,"
            + COLUMN_KILOGRAMS + " REAL,"
            + COLUMN_DATE + " TEXT)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEIGHT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
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

}
