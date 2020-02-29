/*
Author: Jani Heinikoski | 0541122
Date: 27.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 7.5.0
 */
package com.example.bottledispenser;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class FileManager {
    private Context c;
    private BufferedReader br;
    private OutputStreamWriter osw;
    private static FileManager fmgr = null;

    public static FileManager getInstance(Context c) {
        if (fmgr == null) {
            fmgr = new FileManager(c);
        }
        return fmgr;
    }

    private FileManager(Context c) {
        this.c = c;
        br = null;
        osw = null;
    }

    public void writeFile(String fileName, String content) {

        try {
            osw = new OutputStreamWriter(c.openFileOutput(fileName, Context.MODE_PRIVATE));
            osw.write(content + "\n");
            System.out.println("LOGGER: wrote " + content);
            osw.close();
        } catch (IOException e) {
            String errMsg = e.getMessage();
            if (errMsg != null) {
                System.out.println("LOGGER: IOException " + errMsg);
            } else {
                System.out.println("LOGGER: IOException unknown error");
            }
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            if (errMsg != null) {
                System.out.println("LOGGER: Exception " + errMsg);
            } else {
                System.out.println("LOGGER: Exception unknown error");
            }
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }
        osw = null;

    }

    public ArrayList<String> readFile(String fileName) {
        String tempLine;
        ArrayList<String> fileContents = new ArrayList<String>();

        try {
            System.out.println("LOGGER: fname:"+ c.getFilesDir() + fileName);
            br = new BufferedReader(new InputStreamReader(c.openFileInput(fileName)));

            while ((tempLine = br.readLine()) != null) {
                System.out.println("LOGGER: read " + tempLine);
                fileContents.add(tempLine);
            }

        } catch (IOException e) {
            String errMsg = e.getMessage();
            if (errMsg != null) {
                System.out.println("LOGGER: IOException " + errMsg);
            } else {
                System.out.println("LOGGER: IOException unknown error");
            }
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            if (errMsg != null) {
                System.out.println("LOGGER: Exception " + errMsg);
            } else {
                System.out.println("LOGGER: Exception unknown error");
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }
        br = null;
        return fileContents;
    }

}
