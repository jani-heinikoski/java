/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.5.1
 */
package com.kranaatinheitinkomppania;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        try {
            ReadAndWriteIO rwIO = new ReadAndWriteIO("src\\resources\\input.zip");
            ArrayList<String> fileNames = rwIO.searchForFiles(".txt");

            if (fileNames.size() > 0) {
                rwIO.readFileFromZip(fileNames.get(0));
            }

        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
    }
}
