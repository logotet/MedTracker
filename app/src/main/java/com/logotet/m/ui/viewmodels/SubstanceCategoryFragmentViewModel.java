package com.logotet.m.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logotet.m.data.DatabaseClient;
import com.logotet.m.data.entities.Substance;

import java.util.List;

public class SubstanceCategoryFragmentViewModel extends AndroidViewModel {

    private DatabaseClient databaseClient;

    public SubstanceCategoryFragmentViewModel(@NonNull Application application) {
        super(application);
        databaseClient = DatabaseClient.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Substance>> getSubstancesByCategory(String category){
        return databaseClient.getSubstancesByCategory(category);
    }
}
