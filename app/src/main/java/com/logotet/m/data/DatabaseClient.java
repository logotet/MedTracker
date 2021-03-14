package com.logotet.m.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.logotet.m.data.entities.ActiveDate;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.data.entities.SubstanceWithDates;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseClient {

    private static DatabaseClient client;
    private static SubstanceDatabase db;

    private Executor executor = Executors.newSingleThreadExecutor();

    public DatabaseClient(Context context) {
        db = Room.databaseBuilder(context, SubstanceDatabase.class, "substance-database.db")
                .addMigrations(new Migration(6, 7) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {

                    }
                })
                .build();
    }

    public static DatabaseClient getInstance(Context context) {
        if (client == null) {
            client = new DatabaseClient(context);
        }
        return client;
    }

    //    Substance
    public void insertSubstance(Substance substance) {
        executor.execute(() -> db.substanceDao().insertSubstance(substance));
    }

    public LiveData<List<Substance>> getSubstancesByCategory(String name) {
        return db.substanceDao().getSubstancesByCategory(name);
    }

    public LiveData<Substance> getSubstanceByName(String name) {
        return db.substanceDao().getSubstanceByName(name);
    }

    public LiveData<List<Substance>> getAllSubstances() {
        return db.substanceDao().getAll();
    }

    public void deleteAll() {
        executor.execute(() -> db.clearAllTables());
    }

    public void deleteSubstance(String name) {
        executor.execute(() -> db.substanceDao().deleteByName(name));
    }

    //    ActiveDate
    public void insertActiveDate(ActiveDate activeDate) {
        executor.execute(() -> db.activeDateDao().insertActiveDate(activeDate));
    }

    public void insertActiveDates(ActiveDate... activeDates) {
        executor.execute(() ->
                db.activeDateDao().insertListOfActiveDate(activeDates)
        );
    }

    public LiveData<List<ActiveDate>> getAllDates() {
        return db.activeDateDao().getAllDatesLive();
    }

}