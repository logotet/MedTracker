package com.logotet.m.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "substance_dates")
public class ActiveDate {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "substance_name")
    private String substanceName;
    @ColumnInfo(name = "active_date")
    private String date;
    @ColumnInfo(name = "active_hour")
    private String hour;
    @ColumnInfo(name = "color")
    private int color;
    @Ignore
    private boolean taken;

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
