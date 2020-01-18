/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.5.0
 */
package com.kranaatinheitinkomppania;
import java.nio.file.NoSuchFileException;



public class Main {

    public static void main(String[] args) {
        try {
            ReadAndWriteIO rwIO = new ReadAndWriteIO("src\\resources\\input.zip");
            rwIO.readFileFromZip("input.txt");
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
    }
}
