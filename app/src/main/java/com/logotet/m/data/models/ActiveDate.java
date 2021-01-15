package com.logotet.m.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "substance_dates")
public class ActiveDate {
    @PrimaryKey(autoGenerate = true)
    int uid;
    @ColumnInfo(name = "substance_name")
    String substanceName;
    @ColumnInfo(name = "active_dates")
    String date;

    public ActiveDate() {

    }

    public String getSubstanceName() {
        return substanceName;
    }

    public void setSubstanceName(String substanceName) {
        this.substanceName = substanceName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
