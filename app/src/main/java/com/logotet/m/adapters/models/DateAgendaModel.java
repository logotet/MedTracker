package com.logotet.m.adapters.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DateAgendaModel {
    int date;
    String weekDay;
    int month;
    int year;
    List<HourPill> hourPills = new ArrayList<>();

    public DateAgendaModel(int date) {
        this.date = date;
    }

    public DateAgendaModel() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<HourPill> getHourPills() {
        return hourPills;
    }

    public void setHourPills(List<HourPill> hourPills) { this.hourPills = hourPills; }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    @NonNull
    @Override
    public String toString() {
        String month = String.valueOf(getMonth());
        if(getMonth() < 10){
            month = "0" + getMonth();
        }
        String date = String.valueOf(getDate());
        if(getDate() < 10){
            date = "0" + getDate();
        }

        return getYear()+month+date+"";
    }
}
