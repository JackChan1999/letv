package com.letv.core.utils;

import android.text.TextUtils;
import com.letv.datastatistics.util.DataConstant.PAGE;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.pp.utils.NetworkUtils;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class LetvDateUtils {
    public static Calendar getCalender(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static long getTimeInMillis(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(formatter.format(currentTime), new ParsePosition(8));
    }

    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(formatter.format(currentTime), new ParsePosition(8));
    }

    public static String getDateAsString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getStringDateShort() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getTimeShort() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static Date strToDateLong(String strDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate, new ParsePosition(0));
    }

    public static String dateToStrLong(Date dateDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateDate);
    }

    public static String dateToStr(Date dateDate, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(format).format(dateDate);
    }

    public static Date strToDate(String strDate) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(strDate, new ParsePosition(0));
    }

    public static Date getNow() {
        return new Date();
    }

    public static Date getLastDate(long day) {
        return new Date(new Date().getTime() - (122400000 * day));
    }

    public static String getStringToday() {
        return new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
    }

    public static String getHour() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).substring(11, 13);
    }

    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).substring(14, 16);
    }

    public static String getUserDate(String sformat) {
        return new SimpleDateFormat(sformat).format(new Date());
    }

    public static String getTwoHour(String st1, String st2) {
        String[] kk = st1.split(NetworkUtils.DELIMITER_COLON);
        String[] jj = st2.split(NetworkUtils.DELIMITER_COLON);
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
            return "0";
        }
        double y = Double.parseDouble(kk[0]) + (Double.parseDouble(kk[1]) / 60.0d);
        double u = Double.parseDouble(jj[0]) + (Double.parseDouble(jj[1]) / 60.0d);
        if (y - u > 0.0d) {
            return (y - u) + "";
        }
        return "0";
    }

    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return ((myFormatter.parse(sj1).getTime() - myFormatter.parse(sj2).getTime()) / 86400000) + "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            date1.setTime(((date1.getTime() / 1000) + ((long) (Integer.parseInt(jj) * 60))) * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            d.setTime(((d.getTime() / 1000) + ((long) (((Integer.parseInt(delay) * 24) * 60) * 60))) * 1000);
            return format.format(d);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isLeapYear(String ddate) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(strToDate(ddate));
        int year = gc.get(1);
        if (year % 400 == 0) {
            return true;
        }
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0) {
            return false;
        }
        return true;
    }

    public static String getEDate(String str) {
        String[] k = new SimpleDateFormat("yyyy-MM-dd").parse(str, new ParsePosition(0)).toString().split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    public static String getEndDateOfMonth(String dat) {
        String str = dat.substring(0, 8);
        int mon = Integer.parseInt(dat.substring(5, 7));
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            return str + VType.FLV_1080P6M_3D;
        }
        if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            return str + VType.MP4_720P_3D;
        }
        if (isLeapYear(dat)) {
            return str + VType.FLV_720P_3D;
        }
        return str + "28";
    }

    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(1) - cal2.get(1);
        if (subYear == 0) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (1 == subYear && 11 == cal2.get(2)) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(2) && cal1.get(3) == cal2.get(3)) {
            return true;
        }
        return false;
    }

    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(3));
        if (week.length() == 1) {
            week = "0" + week;
        }
        return Integer.toString(c.get(1)) + week;
    }

    public static String getWeek(String sdate, String num) {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) {
            c.set(7, 2);
        } else if (num.equals("2")) {
            c.set(7, 3);
        } else if (num.equals("3")) {
            c.set(7, 4);
        } else if (num.equals("4")) {
            c.set(7, 5);
        } else if (num.equals("5")) {
            c.set(7, 6);
        } else if (num.equals("6")) {
            c.set(7, 7);
        } else if (num.equals("0")) {
            c.set(7, 1);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String getWeek(String sdate) {
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals("") || date2 == null || date2.equals("")) {
            return 0;
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        return (date.getTime() - mydate.getTime()) / 86400000;
    }

    public static String getNowMonth(String sdate) {
        sdate = sdate.substring(0, 8) + "01";
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getNextDay(sdate, (1 - c.get(7)) + "");
    }

    public static String getNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    public static String getRandom(int i) {
        Random jjj = new Random();
        if (i == 0) {
            return "";
        }
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    public static boolean RightDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (date == null) {
            return false;
        }
        if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String timeClockString() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static String timeClockString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static long string2Timestamp(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString).getTime();
    }

    public static String getFormatPhotoName(String time) {
        return time.replaceAll(" ", PAGE.MYSHARE).replaceAll(NetworkUtils.DELIMITER_COLON, "f").replaceAll(NetworkUtils.DELIMITER_LINE, "c");
    }
}
