package com.logotet.m.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.logotet.m.models.ActiveDate;
import com.logotet.m.models.Substance;

import java.util.List;

@Dao
public interface ActiveDateDao {

    @Insert
    void insertSubstance(ActiveDate activeDate);

    @Query("SELECT * FROM substance_dates")
    List<ActiveDate> getAllDates();

    @Query("SELECT * FROM substance_dates WHERE substance_name LIKE :substanceName")
    List<ActiveDate> getDatesBySubstanceName(String substanceName);

    @Query("SELECT * FROM substance_dates WHERE active_dates LIKE :date")
    List<ActiveDate> getDatesByDate(String date);

    @Query("DELETE FROM substance_dates WHERE substance_name = :substanceName")
    void deleteDatesBySubstanceName(String substanceName);

    @Delete
    void deleteAllDates(ActiveDate...activeDates);
}
