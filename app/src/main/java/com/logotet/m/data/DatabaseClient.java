package com.logotet.m.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.logotet.m.data.models.ActiveDate;
import com.logotet.m.data.models.Substance;
import com.logotet.m.data.models.SubstanceAndDates;

import java.util.Collections;
import java.util.List;

public class DatabaseClient {

    private static DatabaseClient client;
    private static SubstanceDatabase db;

    private DatabaseClient(Context context) {
        db = Room.databaseBuilder(context, SubstanceDatabase.class, "substance-database.db")
                //TODO: make the methods below asynchornous to remove them from the main thread
                .addMigrations(new Migration(4, 5) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {

                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    public static DatabaseClient getInstance(Context context) {
        if (client == null) {
            client = new DatabaseClient(context);
        }
        return client;
    }

    public void insertSubstance(Substance substance) {
        db.substanceDao().insertSubstance(substance);

    }

    public List<Substance> getSubstancesBuName(String name) {
        return db.substanceDao().getSubstancesByName(name);
    }

    public Substance getSubstanceByName(String name) {
        return db.substanceDao().getSubstanceByName(name);
    }

    public void deleteAll() {
        db.clearAllTables();
    }

    public void deleteSubstance(String name) {
        db.substanceDao().deleteByName(name);
    }


    public List<Substance> getAllSubstances() {
       List<Substance> s = db.substanceDao().getAll();
       Collections.reverse(s);
        return s;
    }

    public void insertActiveDate(ActiveDate activeDate) {
        db.activeDateDao().insertSubstance(activeDate);
    }

    public List<ActiveDate> getSubstancesWithDates(String name){
        return db.substanceAndDatesDao().getSubstanceWithDates(name).getDates();
    }

    public List<ActiveDate> getAllDates(){
            return db.activeDateDao().getAllDates();
    }

    public List<ActiveDate> getDatesByValue(String date){
        return db.activeDateDao().getDatesBySubstanceName(date);
    }

    public SubstanceAndDates getSubstanceDates(String name){
        return db.substanceAndDatesDao().getSubstanceWithDates(name);
    }

    public List<SubstanceAndDates> getAllSubstanceAndDates(){
        return db.substanceAndDatesDao().getAllSubstanceWithDates();
    }

}