/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 7.5.0
 */
package com.example.swampsimulator;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class FileManager {
    Context c;
    BufferedReader br;
    OutputStreamWriter osw;


    public FileManager(Context c) {
        this.c = c;
        br = null;
        osw = null;
    }

    public void writeFile(String fileName, String content) {
        try {
            osw = new OutputStreamWriter(c.openFileOutput(fileName, c.MODE_PRIVATE));
            osw.write(content + "\n");
            System.out.println("LOGGER: wrote " + content);
        } catch (IOException e) {
            Log.e("IOExc", e.getMessage());
        } finally {
            try {
                osw.close();
            } catch (Exception ex) {
                Log.e("Unknown error", ex.getMessage());
            }
        }
    }

    public ArrayList<String> readFile(String fileName) {
        String tempLine;
        ArrayList<String> fileContents = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(c.openFileInput(fileName)));

            while ((tempLine = br.readLine()) != null) {
                System.out.println("LOGGER: read " + tempLine);
                fileContents.add(tempLine);
            }

        } catch (IOException e) {
            Log.e("IOEx", "Error while reading file");
        } catch (Exception exx) {
            System.out.println("LOGGER: " + exx.getMessage());
        } finally {
            try {
                br.close();
            } catch (Exception ex) {
                Log.e(ex.getMessage(), "Failed to close BufferedReader");
                System.exit(-1);
            }
        }

        return fileContents;
    }

}
