package com.letv.core.utils;

import android.annotation.SuppressLint;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.pp.utils.NetworkUtils;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class StringUtils {
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            return s.replaceAll("0+?$", "").replaceAll("[.]$", "");
        }
        return s;
    }

    public static String timeClockString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String timeString(long time) {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(time));
    }

    public static String timeStringByMinutes(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }

    public static String timeStringBySecond(long time) {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(1000 * time));
    }

    public static String time2StrBySec(long time) {
        return new SimpleDateFormat("HH:mm").format(new Date(1000 * time));
    }

    public static String timeStringAll(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    public static String getNumberTime(long time_second) {
        Formatter formatter = new Formatter(null, Locale.getDefault());
        try {
            long seconds = time_second % 60;
            long minutes = (time_second / 60) % 60;
            String formatter2 = formatter.format("%02d:%02d:%02d", new Object[]{Long.valueOf(time_second / 3600), Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static String getNumberTextTime(long time_second) {
        if (time_second <= 0) {
            return "";
        }
        Formatter formatter = new Formatter(null, Locale.getDefault());
        try {
            long seconds = time_second % 60;
            long minutes = (time_second / 60) % 60;
            String formatter2 = formatter.format("%02d%02d%02d", new Object[]{Long.valueOf(time_second / 3600), Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static String stringForTimeNoHour(long timeMs) {
        String formatter;
        StringBuilder formatBuilder = new StringBuilder();
        Formatter formatter2 = new Formatter(formatBuilder, Locale.getDefault());
        if (timeMs >= 0) {
            try {
                int totalSeconds = (int) (timeMs / 1000);
                int seconds = totalSeconds % 60;
                int minutes = totalSeconds / 60;
                formatBuilder.setLength(0);
                formatter = formatter2.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
            } finally {
                formatter2.close();
            }
        } else {
            formatter = "00:00";
            formatter2.close();
        }
        return formatter;
    }

    public static long stringToLong2(String str) {
        long i = 0;
        try {
            i = Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String stringForTime(long timeMs) {
        StringBuilder formatBuilder = new StringBuilder();
        Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
        try {
            int totalSeconds = (int) (timeMs / 1000);
            int seconds = totalSeconds % 60;
            int minutes = (totalSeconds / 60) % 60;
            int hours = totalSeconds / 3600;
            formatBuilder.setLength(0);
            String formatter2 = formatter.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static String timeFormatter(long timeMs) {
        if (((int) (timeMs / 1000)) >= 3600) {
            return stringForTime(timeMs);
        }
        return stringForTimeNoHour(timeMs);
    }

    public static String getPlayCountsToStr(long playCount) {
        if (playCount < 10000) {
            if (playCount < 1000) {
                return playCount + "";
            }
            if (playCount < 1000 || playCount > 9999) {
                return "";
            }
            String aa = (playCount / 1000) + "";
            String bb = (playCount % 1000) + "";
            if (bb.length() < 3) {
                bb = "0" + bb;
            }
            return aa + "," + bb;
        } else if (playCount < 10000 || playCount >= 100000000) {
            return new DecimalFormat(".#").format((((double) playCount) * 1.0d) / 1.0E8d) + LetvUtils.getString(R.string.hundred_million);
        } else {
            return new DecimalFormat(".#").format((((double) playCount) * 1.0d) / 10000.0d) + LetvUtils.getString(R.string.ten_thousands);
        }
    }

    public static String formatTime(String time, String format) {
        if (!(TextUtils.isEmpty(time) || TextUtils.isEmpty(format))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                time = new SimpleDateFormat(format).format(sdf.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
            }
        }
        return time;
    }

    public static boolean isToday(Date date) {
        return getDateName(0).equals(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public static boolean isYesterday(Date date) {
        return getDateName(-1).equals(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public static boolean isTomorrow(Date date) {
        return getDateName(1).equals(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public static String getWeekName(String dateString) {
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                dateString = getWeekName(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    private static String getWeekName(Calendar calendar) {
        String name = calendar.getDisplayName(7, 1, Locale.CHINESE);
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return name.replace(LetvUtils.getString(R.string.week_xinqi), LetvUtils.getString(R.string.week_zhou));
    }

    public static String getDateName(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return dateString;
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            if (isYesterday(date)) {
                return LetvUtils.getString(R.string.letvutil_week_yesterday);
            }
            if (isToday(date)) {
                return LetvUtils.getString(R.string.letvutil_week_tody);
            }
            if (isTomorrow(date)) {
                return LetvUtils.getString(R.string.letvutil_week_tomorrow);
            }
            return new SimpleDateFormat("MM" + LetvUtils.getString(R.string.month) + "dd" + LetvUtils.getString(R.string.day)).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    private static String getDateName(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(6, calendar.get(6) + i);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String getString(String targetStr, String defaultStr) {
        if (!TextUtils.isEmpty(targetStr)) {
            return targetStr;
        }
        LogInfo.log("getString", "目标String为空， 取默认String : " + defaultStr);
        return defaultStr;
    }

    public static String getString(String targetStr, int defaultStrId) {
        return getString(targetStr, BaseApplication.getInstance().getString(defaultStrId));
    }

    public static long getTimeLong(String date) {
        Date mDate;
        try {
            mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            mDate = new Date();
        }
        return mDate.getTime();
    }

    public static String getStringTwo(String strIn) {
        String strResult = "";
        if (TextUtils.isEmpty(strIn)) {
            return strResult;
        }
        if (strIn.length() >= 2) {
            return strIn;
        }
        return "0".concat(strIn);
    }

    public static String addUnlinkParams(String url, boolean isPay, String liveid, String uuid, String st) {
        if ((TextUtils.isEmpty(liveid) || TextUtils.equals(liveid, NetworkUtils.DELIMITER_LINE)) && (TextUtils.isEmpty(st) || TextUtils.equals(st, NetworkUtils.DELIMITER_LINE))) {
            st = getStreamIdFromUrl(url);
        }
        String str = "&platid=10&playid=1&termid=2&pay=%s&tm=1411974165&splatid=%s&ostype=andriod&hwtype=un&p1=0&p2=00&uuid=%s&liveid=%s&station=%s";
        Object[] objArr = new Object[5];
        objArr[0] = Integer.valueOf(isPay ? 1 : 0);
        objArr[1] = LetvConfig.getHKClientID();
        objArr[2] = uuid;
        objArr[3] = liveid;
        objArr[4] = st;
        return url.concat(String.format(str, objArr));
    }

    private static String getStreamIdFromUrl(String url) {
        String streamId = "";
        if (TextUtils.isEmpty(url) || !url.contains("stream_id=")) {
            return streamId;
        }
        String subStr = url.substring(url.indexOf("stream_id="));
        int start = subStr.indexOf(SearchCriteria.EQ) + 1;
        int end = subStr.indexOf("&");
        return end < 0 ? subStr.substring(start) : subStr.substring(start, end);
    }

    public static String formatPlayTime(String time) {
        try {
            LogInfo.log("time1 = " + time);
            if (!TextUtils.isEmpty(time)) {
                int dotIndex = time.indexOf(NetworkUtils.DELIMITER_COLON);
                if (dotIndex != -1) {
                    LogInfo.log("time2 = " + time.substring(0, dotIndex + 3));
                    time = time.substring(0, dotIndex + 3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String formatBookTime(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 16) {
            return time;
        }
        return time.substring(0, 16);
    }

    public static String getBookTime(String date) {
        Date mDate;
        String bookTime = "";
        try {
            mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            mDate = new Date();
        }
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);
        return new SimpleDateFormat("HH:mm").format(mCalendar.getTime());
    }

    public static String getWeekDayName(String date) {
        Date mDate;
        String weekDayName = "";
        try {
            mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            mDate = new Date();
        }
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);
        int week_day = mCalendar.get(7);
        return new SimpleDateFormat("MM" + LetvUtils.getString(R.string.month) + "dd" + LetvUtils.getString(R.string.day)).format(mCalendar.getTime()) + "(" + getWeek(week_day) + ")";
    }

    public static String getWeek(int week_day) {
        String week = "";
        switch (week_day) {
            case 1:
                return LetvUtils.getString(R.string.letvutil_week07);
            case 2:
                return LetvUtils.getString(R.string.letvutil_week01);
            case 3:
                return LetvUtils.getString(R.string.letvutil_week02);
            case 4:
                return LetvUtils.getString(R.string.letvutil_week03);
            case 5:
                return LetvUtils.getString(R.string.letvutil_week04);
            case 6:
                return LetvUtils.getString(R.string.letvutil_week05);
            case 7:
                return LetvUtils.getString(R.string.letvutil_week06);
            default:
                return week;
        }
    }

    public static String formatLiveTitle(String channel_Num, String channel_Name, String program_Name) {
        if (TextUtils.isEmpty(channel_Name)) {
            return program_Name;
        }
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(channel_Num) && channel_Num.length() == 1) {
            builder.append("0");
            builder.append(channel_Num).append("  ");
        } else if (!TextUtils.isEmpty(channel_Num)) {
            builder.append(channel_Num).append("  ");
        }
        builder.append(channel_Name);
        if (!TextUtils.isEmpty(program_Name)) {
            builder.append("：").append(program_Name);
        }
        return builder.toString();
    }

    public static SpannableString highlightParamText(String inputString, String highlight, int color) {
        inputString = String.format(inputString, new Object[]{highlight});
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(highlight);
            if (beginPos > -1) {
                spanString.setSpan(new ForegroundColorSpan(color), beginPos, highlight.length() + beginPos, 33);
            }
        }
        return spanString;
    }

    public static SpannableString highlightParamText(String inputString, int index, String highlight, int color) {
        inputString = String.format(inputString, new Object[]{highlight});
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = index;
            if (beginPos > -1) {
                spanString.setSpan(new ForegroundColorSpan(color), beginPos, highlight.length() + beginPos, 33);
            }
        }
        return spanString;
    }

    public static SpannableString highlightParamText(String inputString, String highlight, int color, boolean formatParam) {
        if (formatParam) {
            inputString = String.format(inputString, new Object[]{highlight});
        }
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(highlight);
            if (beginPos > -1) {
                spanString.setSpan(new ForegroundColorSpan(color), beginPos, highlight.length() + beginPos, 33);
            }
        }
        return spanString;
    }

    public static SpannableString highlightParamTextAdded(String inputString, String highlight, int color) {
        int beginPos = inputString.length();
        SpannableString spanString = new SpannableString(inputString + highlight);
        spanString.setSpan(new ForegroundColorSpan(color), beginPos, highlight.length() + beginPos, 33);
        return spanString;
    }

    public static SpannableString highlightParamTextOffsetRightOne(String inputString, String highlight, int color) {
        inputString = String.format(inputString, new Object[]{highlight});
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(highlight);
            if (beginPos > -1) {
                int endPos;
                if ((highlight.length() + beginPos) + 1 > inputString.length()) {
                    endPos = inputString.length();
                } else {
                    endPos = (highlight.length() + beginPos) + 1;
                }
                spanString.setSpan(new ForegroundColorSpan(color), beginPos, endPos, 33);
            }
        }
        return spanString;
    }

    public static SpannableString highlightParamTextOffsetRightOne(String inputString, int highlight, int color) {
        inputString = String.format(inputString, new Object[]{Integer.valueOf(highlight)});
        String highlightValue = Integer.toString(highlight);
        SpannableString spanString = new SpannableString(inputString);
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(highlightValue);
            if (beginPos > -1) {
                int endPos;
                if ((highlightValue.length() + beginPos) + 1 > inputString.length()) {
                    endPos = inputString.length();
                } else {
                    endPos = (highlightValue.length() + beginPos) + 1;
                }
                spanString.setSpan(new ForegroundColorSpan(color), beginPos, endPos, 33);
            }
        }
        return spanString;
    }

    public static SpannableString highlightParamTextBetweenTwoString(String inputString, String beforeHighlight, String afterHighlight, int color) {
        return highlightParamTextBetweenTwoString(new SpannableString(inputString), beforeHighlight, afterHighlight, color);
    }

    public static SpannableString highlightParamTextBetweenTwoString(SpannableString spanString, String beforeHighlight, String afterHighlight, int color) {
        String inputString = spanString.toString();
        if (!TextUtils.isEmpty(inputString)) {
            int beginPos = inputString.indexOf(beforeHighlight) + beforeHighlight.length();
            if (beginPos > -1) {
                int endPos = inputString.indexOf(afterHighlight);
                if (endPos > -1 && beginPos < endPos) {
                    spanString.setSpan(new ForegroundColorSpan(color), beginPos, endPos, 33);
                }
            }
        }
        return spanString;
    }

    public static SpannableString highlightSpecifyIndexLengthTextSize(SpannableString spanString, int size, int index, int length) {
        SpannableString spanSizeString = spanString;
        spanSizeString.setSpan(new AbsoluteSizeSpan(size, true), index, length, 33);
        return spanSizeString;
    }

    public static String getString(int id) {
        return BaseApplication.getInstance().getString(id);
    }

    public static String getString(int id, Object... objects) {
        return BaseApplication.getInstance().getString(id, objects);
    }

    public static int getLocalWeekDay() {
        Calendar.getInstance().get(7);
        Date date = new Date(System.currentTimeMillis());
        if (date.getDay() == 0) {
            return 7;
        }
        return date.getDay();
    }

    public static float staticticsLoadTimeInfoFormat(long time) {
        if (time > 575990000) {
            time = System.currentTimeMillis() - time;
        }
        return (((float) (time / 10)) * 1.0f) / 100.0f;
    }

    public static float staticticsLoadTimeInfoFormat(long time1, long time2) {
        long time;
        if (time1 > 575990000) {
            time1 = System.currentTimeMillis() - time1;
        }
        if (time2 > 575990000) {
            time2 = System.currentTimeMillis() - time2;
        }
        if (time1 > time2) {
            time = time1;
        } else {
            time = time2;
        }
        return (((float) (time / 10)) * 1.0f) / 100.0f;
    }

    public static String timeClockString() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static int daysBetween(String t1, String t2) throws ParseException {
        if (t1.length() > 10) {
            t1 = t1.substring(0, 10);
        }
        if (t2.length() > 10) {
            t2 = t2.substring(0, 10);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(t1));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(t2));
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / 86400000;
        LogInfo.log("save_", "t1 = " + t1 + " , t2 = " + t1 + ", time1 = " + time1 + " , time2 = " + time2 + " , time2 - time1 = " + (time2 - time1) + " , betweenDays = " + betweenDays);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    public static int calMinuteBetween(String m1, String m2) {
        return ((Integer.valueOf(m1.substring(0, 2)).intValue() - Integer.valueOf(m2.substring(0, 2)).intValue()) * 60) + (Integer.valueOf(m1.substring(3, 5)).intValue() - Integer.valueOf(m2.substring(3, 5)).intValue());
    }

    public static SpannableString generateTvSpreadSpanText(String text1, String text2, ClickableSpan clickableSpan) {
        if (TextUtils.isEmpty(text1)) {
            text1 = "";
        }
        String str = text1;
        if (!TextUtils.isEmpty(text2)) {
            str = text1 + text2;
        }
        SpannableString spannableString = new SpannableString(str);
        if (clickableSpan != null) {
            spannableString.setSpan(clickableSpan, text1.length(), str.length(), 33);
        }
        return spannableString;
    }

    public static int stringToLong(String time) {
        String[] times = time.split(NetworkUtils.DELIMITER_COLON);
        int minute;
        if (times.length == 3) {
            int hour = Integer.parseInt(times[0]);
            minute = Integer.parseInt(times[1]);
            return (((hour * 60) * 60) + (minute * 60)) + Integer.parseInt(times[2]);
        } else if (times.length == 2) {
            minute = Integer.parseInt(times[0]);
            return (minute * 60) + Integer.parseInt(times[1]);
        } else if (times.length != 1 || TextUtils.isEmpty(times[0])) {
            return 0;
        } else {
            return Integer.parseInt(times[0]);
        }
    }

    public static long stringToMillisecond(String time) {
        return (long) (stringToLong(time) * 1000);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getTimeStamp() {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(System.currentTimeMillis()));
    }

    public static String clipStringWithellipsis(String str, int count) {
        if (str == null || TextUtils.isEmpty(str.trim()) || str.trim().length() <= count) {
            return str;
        }
        return str.trim().substring(0, count) + "...";
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
