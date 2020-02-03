package com.example.myruns;

import java.util.Calendar;

public class ExerciseEntry {
    private Long id;
    private int mInputType;  // Manual, GPS or automatic
    private int mActivityType;     // Running, cycling etc.
    private Calendar mDateTime;    // When does this entry happen
    private int mDuration;         // Exercise duration in seconds
    private float mDistance;      // Distance traveled. Either in meters or feet.
    private float mAvgPace;       // Average pace
    private float mAvgSpeed;    // Average speed
    private int mCalorie;        // Calories burnt
    private float mClimb;         // Climb. Either in meters or feet.
    private int mHeartRate;       // Heart rate
    private String mComment;       // Comments
    //private ArrayList<LatLng> mLocationList; // Location list

    public Long getId() {
        return id;
    }

    public int getmInputType() {
        return mInputType;
    }

    public int getmActivityType() {
        return mActivityType;
    }

    public Calendar getmDateTime() {
        return mDateTime;
    }

    public int getmDuration() {
        return mDuration;
    }

    public float getmDistance() {
        return mDistance;
    }

    public float getmAvgPace() {
        return mAvgPace;
    }

    public float getmAvgSpeed() {
        return mAvgSpeed;
    }

    public int getmCalorie() {
        return mCalorie;
    }

    public float getmClimb() {
        return mClimb;
    }

    public int getmHeartRate() {
        return mHeartRate;
    }

    public String getmComment() {
        return mComment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInputType(int mInputType) {
        this.mInputType = mInputType;
    }

    public void setActivityType(int mActivityType) {
        this.mActivityType = mActivityType;
    }

    public void setDateTime(Calendar mDateTime) {
        this.mDateTime = mDateTime;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setDistance(float mDistance) {
        this.mDistance = mDistance;
    }

    public void setAvgPace(float mAvgPace) {
        this.mAvgPace = mAvgPace;
    }

    public void setAvgSpeed(float mAvgSpeed) {
        this.mAvgSpeed = mAvgSpeed;
    }

    public void setCalorie(int mCalorie) {
        this.mCalorie = mCalorie;
    }

    public void setClimb(float mClimb) {
        this.mClimb = mClimb;
    }

    public void setHeartRate(int mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }
}