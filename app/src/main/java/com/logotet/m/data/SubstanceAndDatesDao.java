package com.logotet.m.data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.logotet.m.data.entities.SubstanceWithDates;

import java.util.List;

@Dao
public interface SubstanceAndDatesDao {
    @Transaction
    @Query("SELECT * FROM substance_table WHERE name = :name")
    SubstanceWithDates getSubstanceWithDates(String name);

    @Transaction
    @Query("SELECT * FROM substance_table")
    List<SubstanceWithDates> getAllSubstanceWithDates();
}
