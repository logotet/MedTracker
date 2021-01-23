package com.logotet.m.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logotet.m.data.DatabaseClient;
import com.logotet.m.data.models.Substance;

public class SubstanceDetailsFragmentViewModel extends AndroidViewModel {
    private DatabaseClient databaseClient;

    public SubstanceDetailsFragmentViewModel(@NonNull Application application) {
        super(application);
        databaseClient = DatabaseClient.getInstance(application.getApplicationContext());
    }

    public LiveData<Substance> getSubstance(String name){
        return databaseClient.getSubstanceByName(name);
    }
}
