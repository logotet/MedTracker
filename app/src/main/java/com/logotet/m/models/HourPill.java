package com.logotet.m.models;

import java.util.Calendar;
import java.util.List;

public class HourPill {
    String name;
    String time;
    int color;
    int date;

    public HourPill() {
    }

    public HourPill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }


}

