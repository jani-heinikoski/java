/*
 * Author: Jani Olavi Heinikoski
 * Date: 06.04.2020
 * Version: alpha
 * Sources:
 * https://stackoverflow.com/Questions/987072/using-Application-Context-Everywhere
 * */
package com.jhprog.dabank.utility;

import android.app.Application;
import android.content.Context;


// This class is purely for delivering app-wide Context to classes who need it
// such as SQLiteOpenHelper
public class DaBank extends Application {

    private static DaBank instance;

    public DaBank() {
        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }

}
