package com.logotet.m.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.logotet.m.data.models.ActiveDate;
import com.logotet.m.data.models.Substance;

@Database(entities = {Substance.class, ActiveDate.class, }, version = 6, exportSchema = false )
@TypeConverters(Converter.class)
public abstract class SubstanceDatabase extends RoomDatabase {

        public abstract SubstanceDao substanceDao();
        public abstract ActiveDateDao activeDateDao();
        public abstract SubstanceAndDatesDao substanceAndDatesDao();
}
