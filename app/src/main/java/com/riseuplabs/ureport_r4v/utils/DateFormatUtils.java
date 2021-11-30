package com.riseuplabs.ureport_r4v.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtils {


    public static String getDate(String dateStr) throws ParseException {

        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'-05:00'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);

        Date parseDate = dateFormat.parse(dateStr);

        return newFormatDate.format(parseDate);
    }

    public static String getPollDate(String dateStr) throws ParseException {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSX");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);

        Date parseDate = dateFormat.parse(dateStr);

        return newFormatDate.format(parseDate);
    }

}
