package com.jhprog.easykino;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FinnkinoXMLParser {

    public static void readXMLbyTagNames(String url, String tagName) throws IOException, SAXException, ParserConfigurationException {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList dataList = doc.getElementsByTagName(tagName);

            for (int i = 0; i < dataList.getLength(); i++) {
                Node n = dataList.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    System.out.println("LOGGER: " + e.getElementsByTagName("Name").item(0).getTextContent());
                }

            }

        } catch (SAXException sax_e) {
            sax_e.printStackTrace();
        } catch (ParserConfigurationException io_e) {
            io_e.printStackTrace();
        } catch (IOException io_e) {
            io_e.printStackTrace();
        }


    }





}
