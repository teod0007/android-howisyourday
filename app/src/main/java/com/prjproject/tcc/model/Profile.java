package com.prjproject.tcc.model;

/**
 * Created by Emanuel on 2016-03-20.
 */
public class Profile {
    String _id;
    String name;
    public Profile(String name) {
        this.name = name;
    }
    public Profile(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
