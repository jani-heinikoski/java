package com.jhprog.dabank.utility;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager {

    private static TimeManager timeManager = null;
    private SimpleDateFormat formatter;

    public static final int BEFORE = -1;
    public static final int AFTER = 1;
    public static final int SAME = 0;

    public static TimeManager getInstance() {
        if (timeManager == null) {
            timeManager = new TimeManager();
        }
        return timeManager;
    }

    @SuppressLint("SimpleDateFormat") // No locale date formatting
    private TimeManager() {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Date stringToDate(String dateAsString) throws ParseException {
        return formatter.parse(dateAsString);
    }

    public String dateToString(@NonNull Date date) {
        return formatter.format(date);
    }

    public int compareDates(Date date1, Date date2) {
        int compareValue = date1.compareTo(date2);

        if (compareValue == 0) {
            return SAME;
        } else if (compareValue < 0) {
            return BEFORE;
        } else {
            return AFTER;
        }
    }

    public int compareDates(String date1, String date2) throws ParseException {
        return compareDates(
                stringToDate(date1),
                stringToDate(date2)
        );
    }

    public String todayString() {
        return dateToString(Calendar.getInstance().getTime());
    }

    public Date todayDate() {
        // This hacky-ish method doesn't account for time (which is the purpose)
        Date today = null;
        try {
            today = stringToDate(todayString());
        } catch (Exception ex) {
            System.err.println("Failed to parse date");
            System.exit(-1);
        }
        return today;
    }

    public void addTimeToDate(@NonNull Date date, int days) { // pass date by ref
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        date.setTime(calendar.getTimeInMillis());
    }

}
