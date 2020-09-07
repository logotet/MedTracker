package com.logotet.m.data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.logotet.m.models.Substance;
import com.logotet.m.models.SubstanceAndDates;

import java.util.List;

@Dao
public interface SubstanceAndDatesDao {
    @Transaction
    @Query("SELECT * FROM substance_table WHERE name = :name")
    SubstanceAndDates getSubstanceWithDates(String name);

    @Transaction
    @Query("SELECT * FROM substance_table")
    List<SubstanceAndDates> getAllSubstanceWithDates();
}
