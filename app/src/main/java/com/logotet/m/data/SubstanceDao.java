package com.logotet.m.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.logotet.m.models.Substance;

import java.util.Calendar;
import java.util.List;

@Dao
public interface SubstanceDao {
    @Insert
    void insertSubstance(Substance substance);

    @Query("SELECT * FROM substance_table")
    List<Substance> getAll();

    @Query("SELECT * FROM substance_table WHERE name LIKE :substanceName")
    List<Substance> getSubstancesByName(String substanceName);

    @Query("SELECT * FROM substance_table WHERE name LIKE :substanceName")
    Substance getSubstanceByName(String substanceName);

//    @Query("SELECT * FROM substance_table WHERE start_date LIKE :startDate")
//    List<Substance> getSubstanceByStartDate(Calendar startDate);

    @Query("DELETE FROM substance_table WHERE name = :substanceName")
    void deleteByName(String substanceName);

    @Delete
    void deleteAll(Substance...substances);
}
