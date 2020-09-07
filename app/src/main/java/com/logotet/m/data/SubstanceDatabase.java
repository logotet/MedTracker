package com.logotet.m.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.logotet.m.models.ActiveDate;
import com.logotet.m.models.Substance;
import com.logotet.m.models.SubstanceAndDates;

@Database(entities = {Substance.class, ActiveDate.class, }, version = 5, exportSchema = false )
public abstract class SubstanceDatabase extends RoomDatabase {

        public abstract SubstanceDao substanceDao();
        public abstract ActiveDateDao activeDateDao();
        public abstract SubstanceAndDatesDao substanceAndDatesDao();
}
