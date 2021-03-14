package com.logotet.m;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.logotet.m.data.DatabaseClient;
import com.logotet.m.data.SubstanceDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public DatabaseClient providesDatabaseClient(Application application){
       return new DatabaseClient(application);
    }

    @Singleton
    @Provides
    public SubstanceDatabase providesDatabase(Application application){
        return Room.databaseBuilder(application, SubstanceDatabase.class, "substance-database.db")
                //TODO: make the methods below asynchronous to remove them from the main thread
                .addMigrations(new Migration(6, 7) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                    }
                })
                .allowMainThreadQueries()
                .build();
    }
}
