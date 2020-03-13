package com.jhprog.easykino;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

public class BackgroundWorker {

    private static BackgroundWorker worker = new BackgroundWorker();
    private volatile boolean finished;
    private ArrayList<Theatre> theatreArrayList;
    private ArrayList<String> locationArrayList;
    private ArrayList<String> theatreNameArrayList;
    private ArrayList<Show> shows;
    private ShowFormData showFormData;

    private class TheatreGetter extends Thread {
        @Override
        public void run() {
            finished = false;
            String location, name;
            FinnkinoXMLParser parser = FinnkinoXMLParser.getInstance();

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            try {
                theatreArrayList = parser.getTheatres();
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }

            theatreArrayList.add(0, new Theatre(1, "All", "All"));

            for (Theatre t : theatreArrayList) {
                location = t.getLocation();
                name = t.getName();

                if (!locationArrayList.contains(location)) {
                    locationArrayList.add(location);
                }
                if (!theatreNameArrayList.contains(name)) {
                    theatreNameArrayList.add(name);
                }
            }

            finished = true;
        }
    }

    private class ShowGetter extends Thread {

        @Override
        public void run() {
            finished = false;
            ArrayList<Theatre> theatresToSearch = new ArrayList<>();
            FinnkinoXMLParser parser = FinnkinoXMLParser.getInstance();
            shows.clear();

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            // Theatre nor location selected
            if (showFormData.getSelectedTheatre().equals("All") && showFormData.getSelectedLocation().equals("All")) {
                theatresToSearch = theatreArrayList;
            }
            // Location selected, theatre not
            if (showFormData.getSelectedTheatre().equals("All") && !showFormData.getSelectedLocation().equals("All")) {
                theatresToSearch = findByLocation(showFormData.getSelectedLocation());
            }
            // Theatre selected, location not
            if (!showFormData.getSelectedTheatre().equals("All") && showFormData.getSelectedLocation().equals("All")) {
                theatresToSearch = findByName(showFormData.getSelectedTheatre());
            }
            // Theare and location selected
            if (!showFormData.getSelectedTheatre().equals("All") && !showFormData.getSelectedLocation().equals("All")) {
                theatresToSearch.add(findByNameAndLocation(showFormData.getSelectedTheatre(), showFormData.getSelectedLocation()));
            }

            ArrayList<Show> gottenShows;
            Handler handler = new Handler(Looper.getMainLooper());

            if (theatresToSearch != null) {
                for (Theatre t : theatresToSearch) {
                    if (t == null) {
                        continue;
                    }
                    try {
                        gottenShows = parser.getShows(t, showFormData.getDateString());
                        if (gottenShows != null) {
                            shows.addAll(gottenShows);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ShowChangedObservable sco = ShowChangedObservable.getInstance();
                                    sco.iChanged();
                                }
                            });

                        } else {
                            continue;
                        }
                    } catch (SAXException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            finished = true;
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
    }

    // Returns instance (Singleton design pattern)
    public static BackgroundWorker getInstance() {
        if (worker == null) {
            worker = new BackgroundWorker();
        }
        return worker;
    }

    private BackgroundWorker() {
        finished = false;
        locationArrayList = new ArrayList<>();
        theatreNameArrayList = new ArrayList<>();
        shows = new ArrayList<>();
        new TheatreGetter().start();
    }


    public void searchShows(ShowFormData showFormData) {
        this.showFormData = showFormData;
        new ShowGetter().start();
    }

    public boolean isFinished() {
        return finished;
    }

    public ArrayList<Theatre> getTheatreArrayList() {
        return theatreArrayList;
    }

    public ArrayList<String> getLocationArrayList() {
        return locationArrayList;
    }

    public ArrayList<String> getTheatreNameArrayList() {
        return theatreNameArrayList;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }
}
