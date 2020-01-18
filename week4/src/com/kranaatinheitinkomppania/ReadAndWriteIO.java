/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.5.0
 */
package com.kranaatinheitinkomppania;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class ReadAndWriteIO {

    private ZipFile zipFile;
    private String zipFilePath;

    public ReadAndWriteIO(String zipFilePath) throws NoSuchFileException {
        // Class constructor
        setZipFile(zipFilePath);
    }

    private void setZipFile(String zipFilePath) throws NoSuchFileException {
        // Check if filepath to the .zip file is empty.
        if (!zipFilePath.trim().isEmpty()) {
            this.zipFilePath = zipFilePath;
        } else {
            throw new NoSuchFileException("Filepath can't be empty!");
        }
        // Try to instantiate ZipFile based on given path.
        try {
            this.zipFile = new ZipFile(this.zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFileFromZip(String fileName) {
        // Reads file to stdout from
        ZipEntry file;
        // Try to get the wanted file from the zip.
        try {
            file = this.zipFile.getEntry(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String tempLine;
        // Check for file missing and IO exceptions.
        try {
            Scanner input = new Scanner(this.zipFile.getInputStream(file));
            // Read while line isn't null.
            while (input.hasNextLine()) {
                tempLine = input.nextLine();
                System.out.println(tempLine);
            }
        }
        // Raises when file is missing.
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("File not found, path: " + this.zipFilePath);
        }
        // Raises when error in reading from the file.
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Filepath: " + this.zipFilePath);
        }
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }
}