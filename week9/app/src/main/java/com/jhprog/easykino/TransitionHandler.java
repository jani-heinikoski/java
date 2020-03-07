package com.jhprog.easykino;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransitionHandler {
    private final static TransitionHandler transitionHandler = new TransitionHandler();
    private final int requestCode = 1337;
    private boolean timeMatters;
    private boolean dateMatters;
    private String dateString;
    private ArrayList<Integer> theatreIDs;
    private ArrayList<Theatre> theatreArrayList;
    private ArrayList<Theatre> theatresToSearch;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    private static final int resultCode = 1337;

    public static TransitionHandler getInstance() {
        return transitionHandler;
    }

    private TransitionHandler() {
        timeMatters = true;
        dateMatters = true;
        theatreIDs = new ArrayList<>(10);
    }

    public void search(ArrayList<Theatre> theatreArrayList, String selectedTheatre, String selectedLocation, String dateString, int startHour, int startMinute, int endHour, int endMinute) {
        // Time not selected
        if (startHour == 0 && startMinute == 0 && endHour == 0 && endMinute == 0) {
            timeMatters = false;
        } else if (startHour == endHour && startMinute == endMinute) {
            timeMatters = false;
        } else {
            // Time selected
            timeMatters = true;
            this.theatreArrayList = theatreArrayList;
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }
        // Date not selected
        if (dateString.equals("dd.mm.yyyy")) {
            try {
                // Try to get current date from system.
                Date date = Calendar.getInstance().getTime();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
                this.dateString = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("LOGGER: Failed to parse time: " + ex.getMessage());
                // Required attribute, must exit if fails.
                System.exit(-1);
            }
        } else {
            // Date selected
            this.dateString = dateString;
        }

        // Theatre nor location selected
        if (selectedTheatre.equals("All") && selectedLocation.equals("All")) {
            theatresToSearch = theatreArrayList;
        }
        // Location selected, theatre not
        if (selectedTheatre.equals("All") && !selectedLocation.equals("All")) {
            theatresToSearch = findByLocation(selectedLocation);
        }
        // Theatre selected, location not
        if (!selectedTheatre.equals("All") && selectedLocation.equals("All")) {
            theatresToSearch = findByName(selectedTheatre);
        }
        // Theare and location selected
        if (!selectedTheatre.equals("All") && !selectedLocation.equals("All")) {
            theatresToSearch.add(findByNameAndLocation(selectedTheatre, selectedLocation));
        }

    }
    // Find the theatre whose name and location match selected ones
    private Theatre findByNameAndLocation(String n, String l) {
        for (Theatre t : theatreArrayList) {
            if (t.getName().equals(n) && t.getLocation().equals(l)) {
                return t;
            }
        }
        return null;
    }
    // Find theatres which match by name
    private ArrayList<Theatre> findByName(String n) {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>(10);
        for (Theatre t : theatreArrayList) {
            if (t.getName().equals(n)) {
                theatres.add(t);
            }
        }
        return theatres;
    }
    // Find theatres which match by location
    private ArrayList<Theatre> findByLocation(String l) {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>(10);
        for (Theatre t : theatreArrayList) {
            if (t.getLocation().equals(l)) {
                theatres.add(t);
            }
        }
        return theatres;
    }
    // Parse search results by time
    private void parseResultsByTime() {
        if (startHour == 0 && startMinute == 0 && endHour == 0 && endMinute == 0) {
            timeMatters = false;
        } else if (startHour == endHour && startMinute == endMinute) {
            timeMatters = false;
        }
    }

    public static final int getResultCode() {
        return resultCode;
    }
}
