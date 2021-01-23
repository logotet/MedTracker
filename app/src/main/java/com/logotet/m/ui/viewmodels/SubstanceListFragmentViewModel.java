package com.logotet.m.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logotet.m.data.DatabaseClient;
import com.logotet.m.data.models.Substance;

import java.util.List;

public class SubstanceListFragmentViewModel extends AndroidViewModel {

    private DatabaseClient databaseClient;

    public SubstanceListFragmentViewModel(@NonNull Application application) {
        super(application);
        databaseClient = DatabaseClient.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Substance>> getAllSubstances() {
        return  databaseClient.getAllSubstances();
    }

    public void deleteSubstance(String name) {
         databaseClient.deleteSubstance(name);
    }
}
