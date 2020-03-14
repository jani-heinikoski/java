package com.jhprog.easykino;

public class SearchFormData {
    private String selectedTheatre;
    private String selectedLocation;
    private String dateString;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public SearchFormData(String selectedTheatre, String selectedLocation, String dateString, int startHour, int startMinute, int endHour, int endMinute) {
        this.selectedTheatre = selectedTheatre;
        this.selectedLocation = selectedLocation;
        this.dateString = dateString;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public String getSelectedTheatre() {
        return selectedTheatre;
    }

    public String getSelectedLocation() {
        return selectedLocation;
    }

    public String getDateString() {
        return dateString;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }
}
