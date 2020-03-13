package com.jhprog.easykino;

import java.util.ArrayList;
import java.util.Calendar;

public class Show implements Comparable<Show> {
    private String title;
    private ArrayList<Calendar> startDT;
    private String locationAndName;

    Show(String title, ArrayList<Calendar> startDT, String locationAndName) {
        this.title = title;
        this.startDT = startDT;
        this.locationAndName = locationAndName;
    }

    public ArrayList<Calendar> getStartDT() {
        return startDT;
    }

    public String getTitle() {
        return title;
    }

    public String getLocationAndName() {
        return locationAndName;
    }

    public void addStartDT(Calendar DT) {
        this.startDT.add(DT);
    }

    public void remStartDT(Calendar DT) {
        this.startDT.remove(DT);
    }

    @Override
    public int compareTo(Show o) {
        return this.title.compareTo(o.getTitle());
    }
}
