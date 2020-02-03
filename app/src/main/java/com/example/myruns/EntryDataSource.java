package com.example.myruns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/*
public static final String PRIMARY_KEY = "_id";
    public static final String INPUT_TYPE = "input_type";
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final String DATE_TIME = "date_time";
    public static final String DURATION = "duration";
    public static final String DISTANCE = "distance";
    public static final String AVG_PACE = "avg_pace";
    public static final String AVG_SPEED = "avg_speed";
    public static final String CALORIES = "calories";
    public static final String CLIMB = "climb";
    public static final String HEARTRATE = "heartrate";
    public static final String COMMENT = "comment";
    public static final String PRIVACY = "privacy";
    public static final String GPS_DATA = "gps_data";
 */

public class EntryDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper helper;
    private String[] columns = {SQLiteHelper.PRIMARY_KEY,
                                SQLiteHelper.INPUT_TYPE,
                                SQLiteHelper.ACTIVITY_TYPE,
                                SQLiteHelper.DATE_TIME,
                                SQLiteHelper.DURATION,
                                SQLiteHelper.DISTANCE,
                                SQLiteHelper.AVG_PACE,
                                SQLiteHelper.AVG_SPEED,
                                SQLiteHelper.CALORIES,
                                SQLiteHelper.CLIMB,
                                SQLiteHelper.HEARTRATE,
                                SQLiteHelper.COMMENT,
                                SQLiteHelper.PRIVACY,
                                SQLiteHelper.GPS_DATA};

    public EntryDataSource(Context c) {
        helper = new SQLiteHelper(c);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public ExerciseEntry createEntry(ExerciseEntry entry) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.INPUT_TYPE, entry.getmInputType());
        values.put(SQLiteHelper.ACTIVITY_TYPE, entry.getmActivityType());
        values.put(SQLiteHelper.DATE_TIME, entry.getmDateTime().toString());
        values.put(SQLiteHelper.DURATION, entry.getmDuration());
        values.put(SQLiteHelper.DISTANCE, entry.getmDistance());
        values.put(SQLiteHelper.AVG_PACE, entry.getmAvgPace());
        values.put(SQLiteHelper.AVG_SPEED, entry.getmAvgSpeed());
        values.put(SQLiteHelper.CALORIES, entry.getmCalorie());
        values.put(SQLiteHelper.CLIMB, entry.getmClimb());
        values.put(SQLiteHelper.HEARTRATE, entry.getmHeartRate());
        values.put(SQLiteHelper.COMMENT, entry.getmComment());
        values.put(SQLiteHelper.PRIVACY, 1);
        values.put(SQLiteHelper.GPS_DATA, "this is a test");

        long recordID = database.insert(SQLiteHelper.ENTRIES_TABLE, null, values);
        Cursor cursor = database.query(SQLiteHelper.ENTRIES_TABLE,
                columns,
                SQLiteHelper.PRIMARY_KEY + " = " + recordID, null, null, null, null);

        cursor.moveToFirst();

        ExerciseEntry obtainedEntry = convertCursorToEntry(cursor);
    }

    public ExerciseEntry convertCursorToEntry(Cursor c) {
        ExerciseEntry e = new ExerciseEntry();
        e.setId(c.getLong(0));
        e.setActivityType(c.getInt(1));
        e.setDateTime(c.getString(2));
        e.setDuration(c.getInt(3));
        e.setDistance(c.getInt(4));
        e.setAvgPace(c.getInt(5));
        e.setAvgSpeed(c.getInt(6));
        e.setCalorie(c.getInt(7));
        e.setClimb(c.getFloat(8));
        e.setHeartRate(c.getInt(9));
        e.setComment(c.getString(10));
    }


}
