package com.tempo.sample.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {

    private DateUtil() {
    }

    public static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_MATCH = "d MMM yyyy hh:mm aa";
    public static final String DATE_DD_MM_YYYY_FORMAT = "dd/MM/yyyy hh:mm aa";
    public static final String DATE_YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String DATE_HH_MM_FORMAT = "hh:mm aa";
    public static final String DATE_EEE_HH_MM_FORMAT = "EEE, hh:mm aa";

    public static String getUTCDatetime(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_UTC, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static Date parseUTCDatetime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_UTC, Locale.US);
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String format, String date) {
        if (date == null) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(parseUTCDatetime(date));
    }

    public static String formatDate(String format, Date date) {
        return new SimpleDateFormat(format, Locale.US).format(date);
    }

    public static String formatDate(String format, String date, Locale locale) {
        if (date == null) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(parseUTCDatetime(date));
    }

    public static String getNowUTC() {
        return getUTCDatetime(Calendar.getInstance().getTime());
    }

    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    public static Calendar getNowCalender() {
        return Calendar.getInstance();
    }
}