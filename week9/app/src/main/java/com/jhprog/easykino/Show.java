package com.jhprog.easykino;

import java.util.Calendar;

public class Show implements Comparable<Show> {
    private String title;
    private Calendar startDT;
    private String locationAndName;

    public Show(String title, Calendar startDT, String locationAndName) {
        this.title = title;
        this.startDT = startDT;
        this.locationAndName = locationAndName;
    }

    public Calendar getStartDT() {
        return startDT;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Show o) {
        return this.title.compareTo(o.getTitle());
    }
}
