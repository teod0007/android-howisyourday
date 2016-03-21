package com.prjproject.tcc.model;

import android.graphics.Bitmap;

/**
 * Created by Emanuel on 2016-03-20.
 */
public class Activity {
    private int _id;
    private int category_id;
    private int day_id;
    private String name;
    private String dayPeriod;
    private Bitmap activityImage;

    public Activity(int _id, int category_id, int day_id, String name, String dayPeriod, Bitmap activityImage) {
        this._id = _id;
        this.category_id = category_id;
        this.day_id = day_id;
        this.name = name;
        this.dayPeriod = dayPeriod;
        this.activityImage = activityImage;
    }

    public Activity(int category_id, int day_id, String name, String dayPeriod, Bitmap activityImage) {
        this.category_id = category_id;
        this.day_id = day_id;
        this.name = name;
        this.dayPeriod = dayPeriod;
        this.activityImage = activityImage;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayPeriod() {
        return dayPeriod;
    }

    public void setDayPeriod(String dayPeriod) {
        this.dayPeriod = dayPeriod;
    }

    public Bitmap getActivityImage() {
        return activityImage;
    }

    public void setActivityImage(Bitmap activityImage) {
        this.activityImage = activityImage;
    }
}
