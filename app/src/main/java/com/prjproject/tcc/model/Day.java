package com.prjproject.tcc.model;

import java.sql.Date;

/**
 * Created by Emanuel on 2016-03-20.
 */
public class Day {
    private int _id;
    private int profile_id;
    private Date day_date;
    private boolean isFuture;

    public Day(int profile_id, Date day_date, boolean isFuture) {
        this.profile_id = profile_id;
        this.day_date = day_date;
        this.isFuture = isFuture;
    }

    public Day(int _id, int profile_id, Date day_date, boolean isFuture) {
        this._id = _id;
        this.profile_id = profile_id;
        this.day_date = day_date;
        this.isFuture = isFuture;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public Date getDay_date() {
        return day_date;
    }

    public void setDay_date(Date day_date) {
        this.day_date = day_date;
    }

    public boolean isFuture() {
        return isFuture;
    }

    public void setIsFuture(boolean isFuture) {
        this.isFuture = isFuture;
    }
}
