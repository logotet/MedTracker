package com.logotet.m.utils;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;


public class ViewManager {

    public static void setUpBtmNavView(BottomNavigationViewEx bottomNav) {
        bottomNav.enableItemShiftingMode(false);
        bottomNav.enableShiftingMode(false);
        bottomNav.setTextVisibility(false);
        bottomNav.enableAnimation(false);
    }

    public void setSpinner(Context context, int list, Spinner spinner, String prompt) {
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(context, list,
                android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);
        spinner.setPrompt(prompt);
    }

    public boolean isViewEmpty(TextView textView){
       String text = textView.getText().toString();
       return text.length() == 0;
    }

    public List<String> getListOfHours(TextView[] hourViews){
        List<String> hours = new ArrayList<>();
        for (TextView hV :
                hourViews) {
            if(hV.getVisibility()==View.VISIBLE){
                hours.add(hV.getText().toString());
            }
        }
        return hours;
    }
}
