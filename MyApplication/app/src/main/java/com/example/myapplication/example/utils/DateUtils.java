package com.example.myapplication.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

    public static String getCurrentDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
