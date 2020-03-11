package com.jhprog.easykino;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FinnkinoXMLParser {

    private DocumentBuilder builder;
    private static FinnkinoXMLParser parser = null;


    public static FinnkinoXMLParser getInstance() throws ParserConfigurationException {
        if (parser == null) {
            parser = new FinnkinoXMLParser();
        }
        return parser;
    }

    private FinnkinoXMLParser() throws ParserConfigurationException {
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private ArrayList<Element> readXMLbyTagName(String url, String tagName) throws IOException, SAXException {

        ArrayList<Element> returnData = new ArrayList<Element>(50);

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

    public void printTheatres(ArrayList<Theatre> theatreArrayList) {
        for (Theatre t : theatreArrayList) {
            System.out.println("LOGGER: " + String.format(Locale.getDefault(), "%d: %s", t.getID(), t.getName()));
        }
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
                    ID = Integer.parseInt(e.getElementsByTagName("ID").item(0).getTextContent());
                    theatreStr = e.getElementsByTagName("Name").item(0).getTextContent();
                    if (ID > 0 && !theatreStr.trim().isEmpty()) {
                        name = parseName(theatreStr);
                        location = parseLocation(theatreStr);
                        if (!name.trim().isEmpty() && !location.trim().isEmpty()) {
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

        try {
            Collections.sort(theatres);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return theatres;

    }

    public ArrayList<Show> getShows(ArrayList<Theatre> theatresToSearch, String date) throws SAXException, IOException {
        ArrayList<Show> shows = new ArrayList<>(50);
        String showTitle;
        ArrayList<String> showStartDT;
        String theatreLocAndName;

        for (Theatre t : theatresToSearch) {
            ArrayList<Element> data = readXMLbyTagName(String.format(Locale.getDefault(), "http://www.finnkino.fi/xml/Schedule/?area=%d&dt=%s", t.getID(), date), "Show");
            if (!data.isEmpty()) {
                for (Element e : data) {
                    try {
                        showTitle = e.getElementsByTagName("Title").item(0).getTextContent().trim();
                        showStartDT = parseDateTime(e.getElementsByTagName("dttmShowStart").item(0).getTextContent().trim());
                        theatreLocAndName = e.getElementsByTagName("Theatre").item(0).getTextContent().trim();
                        if (!showTitle.isEmpty() && !theatreLocAndName.isEmpty() && showStartDT != null) {

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("LOGGER: Unknown Exception thrown: " + ex.getMessage());
                    }
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

    private ArrayList<String> parseDateTime(String dttm) {
        String parsedDate = null;
        String parsedTime = null;
        ArrayList<String> retVal = new ArrayList<>(2);

        try {
            if (!dttm.isEmpty() && dttm.contains("T")) {
                String[] splitTime = dttm.split("T");
                parsedDate = splitTime[0];
                parsedTime = splitTime[1];
                retVal.add(parsedDate);
                retVal.add(parsedTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (retVal.size() == 2 && retVal.get(0) != null && retVal.get(1) != null) {
            return retVal;
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
