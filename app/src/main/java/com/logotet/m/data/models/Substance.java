package com.logotet.m.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.List;

@Entity(tableName = "substance_table")
public class Substance {

    @PrimaryKey(autoGenerate = true)
    int uid;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "type")
    String category;
    @ColumnInfo(name = "intake_days")
    int intakeDays;
    @ColumnInfo(name = "intake_daily")
    int intakeDaily;
    @ColumnInfo(name = "dosage")
    int dosagePerTake;
    @ColumnInfo(name = "form")
    String intakeForm;
    @ColumnInfo(name = "start_date")
    String startDate;
    @ColumnInfo(name = "end_date")
    String endDate;
    @Ignore
    List<Calendar> activeDates;

    public Substance() {
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIntakeForm() {
        return intakeForm;
    }

    public void setIntakeForm(String intakeForm) {
        this.intakeForm = intakeForm;
    }

    public int getDosagePerTake() {
        return dosagePerTake;
    }

    public void setDosagePerTake(int dosagePerTake) {
        this.dosagePerTake = dosagePerTake;
    }


    public void setIntakeDays(int intakeDays) {
        this.intakeDays = intakeDays;
    }

    public int getIntakeDays() {
        return intakeDays;
    }

    public int getIntakeDaily() {
        return intakeDaily;
    }

    public void setIntakeDaily(int intakeDaily) {
        this.intakeDaily = intakeDaily;
    }

    public Substance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Calendar> getActiveDates(Substance s) {
//        // TODO: This method seems too complex. Try to simplify it by redesinging the database and adding some conversion methods.
//        Calendar startDate = Calendar.getInstance();
//        startDate.setTime(new Date(s.getStartDate()));
//        Calendar endDate = Calendar.getInstance();
//        endDate.setTime(new Date(s.getEndDate()));
//        activeDates = DateUtils.getDatesRange(startDate, endDate);
//        activeDates = DateUtils.getActiveDates(activeDates, s.getIntakeDays());
//        return activeDates;
//    }

    public void setActiveDates(List<Calendar> activeDates) {
        this.activeDates = activeDates;
    }


}

