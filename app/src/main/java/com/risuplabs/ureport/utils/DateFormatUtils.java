package com.risuplabs.ureport.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtils {


    public static String getDate(String dateStr) throws ParseException {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

        Date parseDate = dateFormat.parse(dateStr);

        return newFormatDate.format(parseDate);
    }

    public static String getPollDate(String dateStr) throws ParseException {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

        Date parseDate = dateFormat.parse(dateStr);

        return newFormatDate.format(parseDate);
    }

}
