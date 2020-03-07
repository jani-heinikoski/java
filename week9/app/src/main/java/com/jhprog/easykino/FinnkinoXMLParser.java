package com.jhprog.easykino;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
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
        String name = "";

        if (!data.isEmpty()) {
            for (Element e : data) {
                try {
                    ID = Integer.parseInt(e.getElementsByTagName("ID").item(0).getTextContent());
                    name = parseName(e.getElementsByTagName("Name").item(0).getTextContent());
                    if (ID > 0 && !name.trim().isEmpty()) {
                        Theatre theatre = new Theatre(ID, name);
                        theatres.add(theatre);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("LOGGER: Unknown Exception thrown: " + ex.getMessage());
                }
            }
        }

        try {
            Collections.sort(theatres);
            for (int i = 0; i < (theatres.size() - 1); i++) {
                if (theatres.get(i).getName().equals(theatres.get(i + 1).getName())) {
                    theatres.remove(i);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return theatres;
        }

    }

    private String parseName(String n) {
        String parsedName = "";

        if (!n.trim().isEmpty() && n.contains(":")) {
            parsedName = n.split(":")[1].trim();
        }

        return parsedName;
    }

}
