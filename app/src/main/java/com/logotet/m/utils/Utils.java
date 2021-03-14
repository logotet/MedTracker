package com.logotet.m.utils;

import com.logotet.m.R;

public class Utils {
    public static int getColor(String type){
        int color = 0;
        switch (type){
            case AppConstants.MEDICATION:
                color = AppConstants.COLOR_MEDICATION;
                break;
            case AppConstants.SUPPLEMENT:
                color = AppConstants.COLOR_SUPPLEMENT;
                break;
            case AppConstants.FOOD:
                color = AppConstants.COLOR_FOOD;
                break;
            case AppConstants.OTHER:
                color = AppConstants.COLOR_OTHER;
                break;
        }
        return color;
    }

    public static String getCategory(int value){
        String category = "";
        switch (value){
            case 1:
                category = AppConstants.EVERYDAY;
                break;
            case 2:
                category = AppConstants.EVERY_TWO_DAYS;
                break;
            case 3:
                category = AppConstants.EVERY_THREE_DAYS;
                break;
            case 7:
                category = AppConstants.EVERY_WEEK;
                break;
        }
        return category;
    }
}
