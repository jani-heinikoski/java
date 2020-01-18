/*
Author: Jani Heinikoski | 0541122
Date: 18.1.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 3
Version: 4.2.3
 */
package com.kranaatinheitinkomppania;

public class Main {

    public static void main(String[] args) {
	    ReadAndWriteIO rwIO = new ReadAndWriteIO();
	    //rwIO.readAndWrite("input.txt", "output.txt");
        rwIO.readAndWrite("src\\resources\\input.txt", "src\\resources\\output.txt");
    }
}
