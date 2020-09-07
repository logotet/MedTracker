package com.logotet.m.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public final class DateUtils {

    /*
    Navigation method to create drawable containing text
     */
    public static Drawable getDrawableText(Context context, String text, Typeface typeface, int color, int size) {
        Resources resources = context.getResources();
        Bitmap bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTypeface(typeface != null ? typeface : Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(ContextCompat.getColor(context, color));

        float scale = resources.getDisplayMetrics().density;
        paint.setTextSize((int) (size * scale));

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;
        canvas.drawText(text, x, y, paint);

        return new BitmapDrawable(context.getResources(), bitmap);
    }



    /**
     * This method returns a list of calendar objects between two dates
     * @param list List representing a range of dates
     * @param position int representing every active date in a range of dates
     * @return List of only active dates
     */
    public static List<LocalDate> getActiveDates(List<LocalDate> list, int position) {
        List<LocalDate> newList = new ArrayList<>();
        for (int j = 0; j<list.size(); j++) {
            LocalDate item = list.get(j);
            if(j % position == 0){
                newList.add(item);
            }
            j++;
        }
        return  newList;
    }

    public static  String getNamOfMonth(int monthValue){
         return   AppConstants.MONTHS_SHORT.get(monthValue);
    }

    public static List<String> getYear(int yearValue) {
        int finalDay;
        if(Year.isLeap(yearValue)){
            finalDay = 366;
        }else{
            finalDay = 365;
        }
        List<String> year = new ArrayList<>();
        for (int j = 1; j<=finalDay; j++) {
            LocalDate date = LocalDate.ofYearDay(yearValue, j);
            String dateFormat = date.format(DateTimeFormatter.BASIC_ISO_DATE);
            year.add(dateFormat);
        }
        return  year;
    }


    private static LocalDate fromCalendarToLocalDate(Calendar calendar){
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
    }

    public static String getDateAsString(LocalDate date){
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static List<String> getDatesBetweenStartAndEnd(LocalDate startDate, LocalDate endDate) {
        List<String> dates = new ArrayList<>();
        long noOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= noOfDaysBetween; i++) {
            LocalDate newDate = startDate.plusDays(i);
            String data = newDate.format(DateTimeFormatter.BASIC_ISO_DATE);
            dates.add(data);
        }
        return dates;
    }

    private DateUtils() {
    }
}