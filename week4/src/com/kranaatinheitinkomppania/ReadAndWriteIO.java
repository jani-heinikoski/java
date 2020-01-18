/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.4.0
 */
package com.kranaatinheitinkomppania;
import java.io.*;



public class ReadAndWriteIO {

    private int lineLen;
    private char c;

    public ReadAndWriteIO(int lineLength, char linesWithChar) {
        // Class constructor
        this.lineLen = lineLength;
        this.c = linesWithChar;
    }

    public ReadAndWriteIO() {
        // Class constructor
        this.lineLen = -1;
    }

    private boolean isAcceptLine (String line) {
        // Checks if line is accepted by given rules.
        // Requires bool containsChar() to be true.
        // Used with void readAndWrite.
        if (containsChar(line)) { // Always true if no char specified in constructor.
            if (this.lineLen <= 0 || (line.length() < this.lineLen && !line.isBlank())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsChar(String line) {
        // Checks if line contains a given character.
        if (this.c == '\u0000') { // If c has not been given, always true.
            return true;
        }
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == this.c) {
                return true;
            }
        }
        return false;
    }

    public void readAndWrite(String inputFilePath, String outputFilePath) {
        // Reads a .txt -file from filePath and prints to stdout.
        // Check if filepaths are empty.
        if (inputFilePath.trim().isEmpty() || outputFilePath.trim().isEmpty()) {
            System.out.println("Filepath can not be empty!");
            return;
        }
        String tempLine;
        // Check for file missing and IO exceptions.
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFilePath));
            // Read while line isn't null.
            while ((tempLine = input.readLine()) != null) {
                if (isAcceptLine(tempLine)) { // Default constructor => always true.
                    output.write(tempLine);
                    output.newLine();
                }
            }
            input.close(); output.close();
        }
        // Raises when file is missing.
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Input: " + inputFilePath + "Output: " + outputFilePath);
        }
        // Raises when error in reading from the file.
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Filepath: " + inputFilePath);
        }
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
            BufferedReader input = new BufferedReader(new FileReader(filePath));
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

    /**
     * Set \u0000 to disable search (default value)
     * @param c
     */
    public void setCharacterToSearch(char c) {
        this.c = c;
    }

    /**
     * Length < 0 disables length limit (default -1)
     * @param lineLen
     */
    public void setLineLen(int lineLen) {
        this.lineLen = lineLen;
    }
}
