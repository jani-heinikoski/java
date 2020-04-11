package com.jhprog.dabank.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeManager {

    private static TimeManager timeManager = null;
    private SimpleDateFormat formatter;

    public static TimeManager getInstance() {
        if (timeManager == null) {
            timeManager = new TimeManager();
        }
        return timeManager;
    }

    private TimeManager() {
        formatter= new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    }

    public long createTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }



}
