package com.logotet.m.utils;

import com.logotet.m.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstants {

    public static final String MEDICATION = "Medication";
    public static final String SUPPLEMENT = "Supplement";
    public static final String FOOD = "Food";
    public static final String OTHER = "Other";

    public static final String PILL = "pill";
    public static final String SPOON = "spoon";
    public static final String DROP = "drop";
    public static final String TABLET = "tablet";
    public static final String GRAM = "g";
    public static final String MILLILITER = "ml";

    public static final List<String> MONTHS_SHORT = new ArrayList<>(
            Arrays.asList(" ", "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Sept", "Oct", "Nov", "Dec"));

    public static final int COLOR_MEDICATION = R.color.red;
    public static final int COLOR_SUPPLEMENT = R.color.blue;
    public static final int COLOR_FOOD = R.color.green;
    public static final int COLOR_OTHER = R.color.yellow;

    public static final int EVERYDAY = 1;
    public static final int EVERY_TWO_DAYS = 2;
    public static final int EVERY_THREE_DAYS = 3;
    public static final int EVERY_WEEK = 7;

    public static final String PROMPT_CATEGORY = "Choose category";
    public static final String PROMPT_INTAKE = "How often will you take it?";
    public static final String PROMPT_INTAKE_DAILY = "How many times per day you will take it?";
    public static final String PROMPT_INTAKE_DOSAGE = "Choose dosage";
    public static final String PROMPT_FORM = "Choose form";

}
