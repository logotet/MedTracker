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
}
