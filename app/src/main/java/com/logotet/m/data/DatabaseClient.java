package com.logotet.m.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.logotet.m.data.models.ActiveDate;
import com.logotet.m.data.models.Substance;
import com.logotet.m.data.models.SubstanceAndDates;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseClient {

    private static DatabaseClient client;
    private static SubstanceDatabase db;

    private Executor executor;

    private DatabaseClient(Context context) {
        db = Room.databaseBuilder(context, SubstanceDatabase.class, "substance-database.db")
                //TODO: make the methods below asynchronous to remove them from the main thread
                .addMigrations(new Migration(5, 6) {
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
//        executor = Executors.newSingleThreadExecutor();
//        executor.execute(() -> db.substanceDao().insertSubstance(substance));
        db.substanceDao().insertSubstance(substance);
    }

    public LiveData<List<Substance>> getSubstancesByCategory(String name) {
        return db.substanceDao().getSubstancesByCategory(name);
    }

    public LiveData<Substance> getSubstanceByName(String name) {
        return db.substanceDao().getSubstanceByName(name);
    }

    public LiveData<List<Substance>> getAllSubstances() {
        return  db.substanceDao().getAll();

    }

    public void deleteAll() {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> db.clearAllTables());
    }

    public void deleteSubstance(String name) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> db.substanceDao().deleteByName(name));
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

//    LiveData

}