package com.letv.android.client.worldcup.util;

import android.annotation.SuppressLint;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint({"NewApi", "DefaultLocale"})
public class LetvUtil {
    public static long currentClickPlayTime = 0;
    public static long currentClickTime = 0;
    public static long lastClickPlayTime = 0;
    public static long lastClickTime = 0;

    public static String timeString(long time) {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(time));
    }

    public static String timeString(long time, String format) {
        SimpleDateFormat formatTime = new SimpleDateFormat(format);
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(time));
    }

    public static String timeStringByMinutes(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }

    public static String timeStringAll(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    public static String timeString(String time) {
        if (!(time == null || "".equals(time.trim()))) {
            try {
                return timeString(Long.parseLong(time));
            } catch (Exception e) {
                Log.e("LHY", "Utils - timeString - error = " + e.toString());
            }
        }
        return "";
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        int i = 0;
        while (i < c.length) {
            if (c[i] == '　') {
                c[i] = ' ';
            } else if (c[i] > '＀' && c[i] < '｟') {
                c[i] = (char) (c[i] - 65248);
            }
            i++;
        }
        return new String(c);
    }

    public static long getTimeInMillis(String time) {
        Calendar c = Calendar.getInstance();
        Constants.debug("=========getTimeInMillis:" + time);
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
