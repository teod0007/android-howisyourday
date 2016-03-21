package com.prjproject.tcc.model;

/**
 * Created by Emanuel on 2016-03-20.
 */
public class Profile {
    int _id;
    String name;
    public Profile(String name) {
        this.name = name;
    }
    public Profile(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
