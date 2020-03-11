package com.jhprog.easykino;

import java.util.Calendar;

public class Show implements Comparable<Show> {
    private String title;
    private Calendar startDT;
    private String locationAndName;

    Show(String title, Calendar startDT, String locationAndName) {
        this.title = title;
        this.startDT = startDT;
        this.locationAndName = locationAndName;
    }

    Calendar getStartDT() {
        return startDT;
    }

    String getTitle() {
        return title;
    }

    public String getLocationAndName() {
        return locationAndName;
    }

    @Override
    public int compareTo(Show o) {
        return this.title.compareTo(o.getTitle());
    }
}
