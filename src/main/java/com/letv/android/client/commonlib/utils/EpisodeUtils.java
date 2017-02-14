package com.letv.android.client.commonlib.utils;

import android.text.TextUtils;
import com.letv.android.client.commonlib.R;
import com.letv.core.BaseApplication;
import com.letv.core.utils.LetvUtils;
import com.letv.pp.utils.NetworkUtils;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class EpisodeUtils {
    public static final long OVERDUE_TIME_IN_SECS = 86400000;

    public static String getString(int id) {
        return BaseApplication.getInstance().getString(id);
    }

    public static String getString(int id, Object... objects) {
        return BaseApplication.getInstance().getString(id, objects);
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

    public static String stringForTime(long timeMs) {
        StringBuilder formatBuilder = new StringBuilder();
        Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
        try {
            String formatter2;
            int totalSeconds = (int) (timeMs / 1000);
            int seconds = totalSeconds % 60;
            int minutes = (totalSeconds / 60) % 60;
            int hours = totalSeconds / 3600;
            formatBuilder.setLength(0);
            if (hours > 0) {
                formatter2 = formatter.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
            } else {
                formatter2 = formatter.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
                formatter.close();
            }
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static String getPlayCountsToStrNoZero(long playCount) {
        String str = getPlayCountsToStr(playCount);
        return str.equals("0") ? "" : str;
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
            if (bb.length() < 2) {
                bb = "00" + bb;
            } else if (bb.length() < 3) {
                bb = "0" + bb;
            }
            return aa + "," + bb;
        } else if (playCount < 10000 || playCount >= 100000000) {
            return new DecimalFormat(".#").format((((double) playCount) * 1.0d) / 1.0E8d) + "亿";
        } else {
            return new DecimalFormat(".#").format((((double) playCount) * 1.0d) / 10000.0d) + LetvUtils.getString(R.string.ten_thousands);
        }
    }

    public static int calculateRows(int index, int rowSize) {
        int b = index / rowSize;
        return index % rowSize == 0 ? b : b + 1;
    }

    public static String formatData(String date) {
        if (TextUtils.isEmpty(date) || date.length() != 8) {
            return null;
        }
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        return year + NetworkUtils.DELIMITER_LINE + month + NetworkUtils.DELIMITER_LINE + date.substring(6, 8);
    }

    public static String string2FormatStringTime(String time) {
        String formatTime = "";
        String[] times = time.split(NetworkUtils.DELIMITER_COLON);
        if (times == null || times.length != 3) {
            return formatTime;
        }
        if (times[0].equals("00")) {
            return times[1] + NetworkUtils.DELIMITER_COLON + times[2];
        }
        int add;
        int s1 = Integer.parseInt(times[0].substring(0, 1));
        int s2 = Integer.parseInt(times[0].substring(1));
        if (s1 == 0) {
            add = s2 * 60;
        } else {
            add = (s1 * 10) * 60;
        }
        int s3 = Integer.parseInt(times[1].substring(0, 1));
        return String.valueOf(((s3 * 10) + add) + Integer.parseInt(times[1].substring(1))) + NetworkUtils.DELIMITER_COLON + times[2];
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

    public static int getMinusDaysBetweenTwoDate(long endTime, long startTime) {
        Date endDate = new Date(endTime);
        Date beginDate = new Date(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        long endMilliSec = c.getTimeInMillis();
        c.setTime(beginDate);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return (int) ((endMilliSec - c.getTimeInMillis()) / 86400000);
    }

    public static String toCommentLikeCountText(int playCount) {
        if (playCount <= 0) {
            return "";
        }
        if (playCount < 10000) {
            return new DecimalFormat("#,###").format((long) playCount) + "";
        } else if (playCount <= 100000000) {
            m = new DecimalFormat("#,###.0").format(((double) playCount) / 10000.0d);
            if (m != null && m.endsWith(".0")) {
                m = m.substring(0, m.length() - 2);
            }
            return m + LetvUtils.getString(R.string.ten_thousands);
        } else {
            m = new DecimalFormat("#,###.0").format(((double) playCount) / 1.0E8d);
            if (m != null && m.endsWith(".0")) {
                m = m.substring(0, m.length() - 2);
            }
            return m + LetvUtils.getString(R.string.hundred_million);
        }
    }
}
