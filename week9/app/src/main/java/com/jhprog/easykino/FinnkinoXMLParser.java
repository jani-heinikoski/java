package com.jhprog.easykino;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FinnkinoXMLParser {

    private DocumentBuilder builder;
    private static FinnkinoXMLParser parser = null;


    static FinnkinoXMLParser getInstance() {
        if (parser == null) {
            try {
                parser = new FinnkinoXMLParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                System.exit(-2);
            }
        }
        return parser;
    }

    private FinnkinoXMLParser() throws ParserConfigurationException {
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private ArrayList<Element> readXMLbyTagName(String url, String tagName) throws IOException, SAXException {

        builder.reset();
        ArrayList<Element> returnData = new ArrayList<>();
        Document doc = builder.parse(url);
        doc.getDocumentElement().normalize();

        NodeList rawDataList = doc.getElementsByTagName(tagName);

        for (int i = 0; i < rawDataList.getLength(); i++) {
            Node n = rawDataList.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                returnData.add(e);
            }

        }

        return returnData;
    }

    public ArrayList<Theatre> getTheatres() throws SAXException, IOException {

        ArrayList<Theatre> theatres = new ArrayList<>(10);
        ArrayList<Element> data = readXMLbyTagName("https://www.finnkino.fi/xml/TheatreAreas/", "TheatreArea");
        int ID = 0;
        String theatreStr = "";
        String name = "";
        String location = "";

        if (!data.isEmpty()) {
            for (Element e : data) {
                try {
                    // Parses ID and theatre's name+location from the xml
                    ID = Integer.parseInt(e.getElementsByTagName("ID").item(0).getTextContent());
                    theatreStr = e.getElementsByTagName("Name").item(0).getTextContent();
                    // Validating values
                    if (ID > 0 && !theatreStr.trim().isEmpty()) {
                        // Separating name and location from the theatreStr
                        name = parseName(theatreStr);
                        location = parseLocation(theatreStr);
                        // Validating values
                        if (!name.trim().isEmpty() && !location.trim().isEmpty()) {
                            // Add new theatre based on valid values
                            Theatre theatre = new Theatre(ID, name, location);
                            theatres.add(theatre);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("LOGGER: Unknown Exception thrown: " + ex.getMessage());
                }
            }
        }

        // Try to sort the theatres in ascending alphabetic order
        try {
            Collections.sort(theatres);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return theatres;

    }

    public ArrayList<Show> getShows(Theatre theatreToSearch, SearchFormData searchFormData, boolean timeMatters, boolean movieNameMatters) throws SAXException, IOException {

        String date = searchFormData.getDateString();
        ArrayList<Show> shows = new ArrayList<>();
        ArrayList<Element> data;
        String url;
        String showTitle;
        Calendar showStartDT;
        String theatreLocAndName;
        boolean found = false;

        if (theatreToSearch == null || theatreToSearch.getName().equals("All")) {
            return null;
        }
        url = String.format(Locale.getDefault(), "https://www.finnkino.fi/xml/Schedule/?area=%d&dt=%s", theatreToSearch.getID(), date);
        System.out.println("LOGGER: " + url);
        data = readXMLbyTagName(url, "Show");
        if (!data.isEmpty()) {
            for (Element e : data) {
                try {
                    // Parsing Show's title, start datetime, theatre location and name.
                    showStartDT = parseDateTime(e.getElementsByTagName("dttmShowStart").item(0).getTextContent().trim());
                    showTitle = e.getElementsByTagName("Title").item(0).getTextContent().trim();
                    theatreLocAndName = e.getElementsByTagName("Theatre").item(0).getTextContent().trim();
                    // Validating values
                    if (!showTitle.isEmpty() && !theatreLocAndName.isEmpty() && showStartDT != null) {
                        // If time matters and time doesn't match criteria we want to skip the whole element
                        if (timeMatters && !timeMatches(showStartDT, searchFormData)) {
                            continue;
                        }

                        if (movieNameMatters && !movieMatches(showTitle, searchFormData.getMovieName())) {
                            continue;
                        }

                        if (shows.size() > 0) {
                            found = false;
                            // Just add a new starting time if show is already listed
                            for (Show s : shows) {
                                if (s.getTitle().equals(showTitle) && s.getLocationAndName().equals(theatreLocAndName)) {
                                    s.addStartDT(showStartDT);
                                    found = true;
                                }
                            }
                            // If not listed, create a new one
                            if (!found) {
                                ArrayList<Calendar> calendars = new ArrayList<>();
                                calendars.add(showStartDT);
                                shows.add(new Show(showTitle, calendars, theatreLocAndName));
                            }
                        // Create a new show if none have been found yet.
                        } else {
                            ArrayList<Calendar> calendars = new ArrayList<>();
                            calendars.add(showStartDT);
                            shows.add(new Show(showTitle, calendars, theatreLocAndName));
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("LOGGER: Unknown Exception thrown: " + ex.getMessage());
                }
            }
        }

        try {
            Collections.sort(shows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return shows;
        
    }

    private boolean movieMatches(String showTitle, String searchTitle) {

        if (showTitle == null || searchTitle == null || showTitle.trim().isEmpty() || searchTitle.trim().isEmpty()) {
            return false;
        }

        String first = showTitle.toLowerCase().trim();
        String second = searchTitle.toLowerCase().trim();

        if (first.contains(second) || first.equals(second)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean timeMatches(Calendar c, SearchFormData searchFormData) {
        Calendar startCal, endCal;
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();

        startCal.clear();
        endCal.clear();

        startCal.set(
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DATE),
                searchFormData.getStartHour(),
                searchFormData.getStartMinute()
        );

        endCal.set(
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DATE),
                searchFormData.getEndHour(),
                searchFormData.getEndMinute()
        );

        if (c.compareTo(startCal) >= 0 && c.compareTo(endCal) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private Calendar parseDateTime(@NonNull String dttm) { // Parses datetime from string yyyy-MM-ddTHH:mm:00
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        String[] splitDT, splitDate, splitTime;
        int year, month, day, hour, minute;

        if (!dttm.trim().isEmpty() && dttm.contains("T")) {
            splitDT = dttm.split("T");
            if (splitDT[0].contains("-") && splitDT[1].contains(":")) {
                splitDate = splitDT[0].split("-");
                splitTime = splitDT[1].split(":");

                try {
                    year = Integer.parseInt(splitDate[0]);
                    month = Integer.parseInt(splitDate[1]);
                    day = Integer.parseInt(splitDate[2]);

                    hour = Integer.parseInt(splitTime[0]);
                    minute = Integer.parseInt(splitTime[1]);

                    calendar.set(year, month, day, hour, minute);
                } catch (Exception e) {
                    calendar = null;
                    e.printStackTrace();
                }
            }
        }

        if (calendar != null) {
            return calendar;
        } else {
            return null;
        }
    }

    private String parseName(String n) {

        String parsedName = "";

        try {
            if (!n.trim().isEmpty() && n.contains(":")) {
                parsedName = n.split(":")[1].trim().toLowerCase();
                if (parsedName.contains(" ")) {
                    String[] splitString = parsedName.split(" ");
                    for (int i = 0; i < splitString.length; i++) {
                        splitString[i] = splitString[i].substring(0, 1).toUpperCase() + splitString[i].substring(1);
                    }
                    parsedName = "";
                    for (int i = 0; i < splitString.length; i++) {
                        parsedName += splitString[i] + " ";
                    }

                } else {
                    parsedName = parsedName.substring(0, 1).toUpperCase() + parsedName.substring(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            parsedName = "";
        }
        return parsedName;
    }

    private String parseLocation(String n) {
        String parsedLocation = "";

        try {
            if (!n.trim().isEmpty() && n.contains(":")) {
                parsedLocation = n.split(":")[0].trim();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            parsedLocation = "";
        }

        return parsedLocation;
    }

}
