package com.logotet.m.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.logotet.m.data.entities.Substance;

import java.util.List;

@Dao
public interface SubstanceDao {

    @Insert
    void insertSubstance(Substance substance);

    @Query("SELECT * FROM substance_table")
    LiveData<List<Substance>> getAll();

    @Query("SELECT * FROM substance_table WHERE type LIKE :category")
    LiveData<List<Substance>> getSubstancesByCategory(String category);

    @Query("SELECT * FROM substance_table WHERE name LIKE :substanceName")
    LiveData<Substance> getSubstanceByName(String substanceName);

    @Query("DELETE FROM substance_table WHERE name = :substanceName")
    void deleteByName(String substanceName);

    @Delete
    void deleteAll(Substance...substances);


}
