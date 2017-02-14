package com.coolcloud.uac.android.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    private static final String TAG = "TimeUtils";

    public static String nowTime() {
        String pattern = "yyyyMMddHHmmss";
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public static String toUTC(long millis) {
        String pattern = "yyyyMMddHHmmss";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            formater.setTimeZone(TimeZone.getTimeZone("GMT"));
            return formater.format(calendar.getTime());
        } catch (Exception e) {
            LOG.i(TAG, "[millis:" + millis + "] local to UTC failed(Exception)", e);
            return "";
        }
    }

    public static String toLocal(String utc) {
        String pattern = "yyyyMMddHHmmss";
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            formater.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = formater.parse(utc);
            formater.setTimeZone(TimeZone.getDefault());
            return formater.format(date);
        } catch (Exception e) {
            LOG.i(TAG, "[utc:" + utc + "] UTC to local failed(Exception)", e);
            return "";
        }
    }
}
