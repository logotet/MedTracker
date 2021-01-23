package com.logotet.m.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.logotet.m.data.models.ActiveDate;

import java.util.List;

@Dao
public interface ActiveDateDao {

    @Insert
    void insertSubstance(ActiveDate activeDate);

    @Query("SELECT * FROM substance_dates")
    List<ActiveDate> getAllDates();

    @Query("SELECT * FROM substance_dates WHERE substance_name LIKE :substanceName")
    List<ActiveDate> getDatesBySubstanceName(String substanceName);

    @Query("SELECT * FROM substance_dates WHERE active_date LIKE :date")
    List<ActiveDate> getDatesByDate(String date);

    @Query("DELETE FROM substance_dates WHERE substance_name = :substanceName")
    void deleteDatesBySubstanceName(String substanceName);

    @Delete
    void deleteAllDates(ActiveDate...activeDates);



//    LiveData
    @Query("SELECT * FROM substance_dates")
    LiveData<List<ActiveDate>> getAllDatesLive();

    @Query("SELECT * FROM substance_dates WHERE substance_name LIKE :substanceName")
    LiveData<List<ActiveDate>> getDatesBySubstanceNameLive(String substanceName);

    @Query("SELECT * FROM substance_dates WHERE active_date LIKE :date")
    LiveData<List<ActiveDate>> getDatesByDateLive(String date);
}
