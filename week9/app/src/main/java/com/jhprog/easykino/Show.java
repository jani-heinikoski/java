package com.jhprog.easykino;

import java.util.Calendar;

public class Show implements Comparable<Show> {
    private String title;
    private Calendar startDT;

    public Show(String title, Calendar startDT) {
        this.title = title;
        this.startDT = startDT;
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
