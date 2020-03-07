package com.jhprog.easykino;
import java.util.ArrayList;
import java.util.Locale;

public class IntentParameter {

    private final static IntentParameter parameter = new IntentParameter();
    private final int requestCode = 1337;
    private Date date;
    private ArrayList<Integer> theatreIDs;
    private int hour;
    private int minute;

    public static IntentParameter getInstance() {
        return parameter;
    }

    private IntentParameter() {
        hour = 0;
        minute = 0;
        theatreIDs = new ArrayList<Integer>(10);
        date = new Date(0, 0, 0);
    }

    public void setDate(String s) {
        date = Date.fromString(s);
    }

    public void setDate(Date d) {
        this.date = d;
    }

    public void setDate(int day, int month, int year) {
        this.date = new Date(day, month, year);
    }

    public void addTheatreID(int ID) {
        if (ID >= 0) {
            this.theatreIDs.add(ID);
        }
    }

    public ArrayList<Integer> getTheatreIDs() {
        return theatreIDs;
    }

    public Date getDate() {
        return date;
    }

    public int getTheatreID(int idx) {
        return theatreIDs.get(idx);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getRequestCode() {
        return requestCode;
    }

}

class Date {
    private int day;
    private int month;
    private int year;

    private Date() {
        day = 0;
        month = 0;
        year = 0;
    }

    public Date(int day, int month, int year) {
        this();
        if (day > 0 && month > 0 && year > 0) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public static Date fromString(String s) {
        int dotCounter = 0;
        int lenCounter = 0;
        int year, month, day;
        Date date;

        if (s != null && !s.trim().isEmpty()) {
            for (char c : s.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    lenCounter++;
                } else if (c == '.') {
                    lenCounter++;
                    dotCounter++;
                } else if (dotCounter > 2) {
                    return new Date();
                } else {
                    return new Date();
                }
            }

            if (!(dotCounter == 2 && lenCounter >= 8 && lenCounter <= 10)) {
                return new Date();
            }

            String[] splitDate = s.split(".");
            day = Integer.parseInt(splitDate[0]);
            month = Integer.parseInt(splitDate[1]);
            year = Integer.parseInt(splitDate[2]);

            if (day > 0 && month > 0 && year > 0) {
                date = new Date(day, month, year);
            } else {
                return new Date();
            }
        } else {
            return new Date();
        }

        return date;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d.%d.%d", day, month, year);
    }

}