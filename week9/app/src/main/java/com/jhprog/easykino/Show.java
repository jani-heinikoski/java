package com.jhprog.easykino;

public class Show {
    private int showID;
    private int theaterID;
    private String title;
    private String startDate;
    private String startTime;

    public Show(int showID, int theaterID, String title, String startDate, String startTime) {
        this.showID = showID;
        this.theaterID = theaterID;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public int getShowID() {
        return showID;
    }

    public int getTheaterID() {
        return theaterID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }
}
