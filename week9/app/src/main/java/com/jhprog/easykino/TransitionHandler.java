package com.jhprog.easykino;
import android.widget.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class TransitionHandler {
    private final static TransitionHandler transitionHandler = new TransitionHandler();
    private static final int requestCode = 1337;
    private boolean timeMatters;
    private String dateString;
    private ArrayList<Show> shows;
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
        shows = new ArrayList<>(50);
    }

    public void search(ArrayList<Theatre> theatreArrayList, String selectedTheatre, String selectedLocation, String dateString, int startHour, int startMinute, int endHour, int endMinute) {
        this.theatreArrayList = theatreArrayList;
        theatresToSearch = new ArrayList<Theatre>(10);
        // Time not selected
        if (startHour == 0 && startMinute == 0 && endHour == 0 && endMinute == 0) {
            timeMatters = false;
        } else if (startHour == endHour && startMinute == endMinute) {
            timeMatters = false;
        } else {
            // Time selected
            timeMatters = true;
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }

        this.dateString = dateString;


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
        if (theatresToSearch != null) {
            for (int i = 0; i < theatresToSearch.size(); i++) {
                Theatre t = theatresToSearch.get(i);
                if (t != null) {
                    System.out.println("LOGGER: THEATRES: " + t.getName() + " | " + t.getLocation() + " | " + t.getID());
                } else {
                    theatresToSearch.remove(i);
                }
            }
        } else {

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
            if (t != null && t.getName().equals(n)) {
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
        if (timeMatters) {

        }
    }

    static int getResultCode() {
        return resultCode;
    }

    static void remBtnEffect(Button b) {
        b.setHeight(b.getHeight() - 25);
    }

    static void btnEffect(Button b) {
        b.setHeight(b.getHeight() + 25);
    }
}
