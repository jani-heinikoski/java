/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.1.0
 */
package com.kranaatinheitinkomppania;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class ReadAndWriteIO {

    public ReadAndWriteIO() {
        // Class constructor
    }

    public void readFile(String filePath) {
        // Reads a .txt -file from filePath and prints to stdout.
        if (filePath.trim().isEmpty()) {
            System.out.println("Filepath can not be empty!");
            return;
        }
        String tempLine;
        // Check for file missing and IO exceptions.
        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
            // Read while line isn't null.
            while ((tempLine = input.readLine()) != null) {
                System.out.println(tempLine);
            }
        }
        // Raises when file is missing.
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("File not found, path: " + filePath);
        }
        // Raises when error in reading from the file.
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Filepath: " + filePath);
        }
    }
}
