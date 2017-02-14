package com.letv.pp.utils;

import com.letv.core.utils.LetvUtils;
import com.letv.datastatistics.util.DataConstant.PAGE;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class StringUtils {
    private static final String FORMAT_NUMBER_UNIT = "%s %s";
    private static final String[] SIZE_UNITS = new String[]{"B", "KB", "MB", "GB", "TB", "EB", "ZB", "YB"};
    private static final String TAG = "StringUtils";
    private static final String[] TIME2_UNITS = new String[]{"ms", "s", PAGE.MYLETV, "h"};
    private static final String[] TIME_UNITS = new String[]{"ps", "ns", "μs", "ms", "s"};

    public static HashMap<String, String> parseParams(String params) {
        if (isEmpty(params)) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap();
        for (String param : params.split("&")) {
            String[] array = param.split(SearchCriteria.EQ);
            if (array != null && array.length == 2) {
                hashMap.put(array[0].trim(), array[1].trim());
            }
        }
        return hashMap;
    }

    public static String formatTime2(long time) {
        if (time < 1000) {
            return String.format(FORMAT_NUMBER_UNIT, new Object[]{Long.valueOf(time), TIME2_UNITS[0]});
        }
        time /= 1000;
        int level = 0 + 1;
        while (time > 60) {
            time /= 60;
            level++;
            if (level >= TIME2_UNITS.length - 1) {
                break;
            }
        }
        return String.format(FORMAT_NUMBER_UNIT, new Object[]{Long.valueOf(time), TIME2_UNITS[level]});
    }

    public static String formatTime(double time) {
        int level = 1;
        while (time > 1000.0d) {
            time /= 1000.0d;
            level++;
            if (level >= TIME_UNITS.length - 1) {
                break;
            }
        }
        if (((int) (time * 10.0d)) % 10 == 0) {
            return String.format(FORMAT_NUMBER_UNIT, new Object[]{Integer.valueOf(((int) (time * 10.0d)) / 10), TIME_UNITS[level]});
        }
        return String.format(FORMAT_NUMBER_UNIT, new Object[]{Double.valueOf(((double) ((int) (time * 10.0d))) / 10.0d), TIME_UNITS[level]});
    }

    public static String formatSpeed(DecimalFormat decimalFormater, double speed) {
        return formatSize(decimalFormater, speed) + "/s";
    }

    public static String formatSize(DecimalFormat decimalFormater, double size) {
        int level = 0;
        while (size > 1024.0d) {
            size /= 1024.0d;
            level++;
        }
        return String.format(FORMAT_NUMBER_UNIT, new Object[]{decimalFormater.format(size), SIZE_UNITS[level]});
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }

    public static String getRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0.");
        Random random = new Random();
        int count = random.nextInt(11) + 10;
        for (int i = 0; i <= count; i++) {
            if (i == count) {
                stringBuilder.append(random.nextInt(9) + 1);
                break;
            }
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public static long getVersionNum(String versionString) {
        if (isEmpty(versionString)) {
            return 0;
        }
        long versionNum = 0;
        try {
            String[] versionArray = versionString.split("\\.");
            for (int i = 0; i < versionArray.length; i++) {
                if (i == versionArray.length - 1) {
                    versionNum += Long.valueOf(versionArray[i]).longValue();
                } else {
                    versionNum = (long) (((double) versionNum) + (((double) Long.valueOf(versionArray[i]).longValue()) * Math.pow(10.0d, (double) (versionArray.length - i))));
                }
            }
            return versionNum;
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
            return 0;
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String join(Object[] elements, CharSequence separator) {
        return join(Arrays.asList(elements), separator);
    }

    public static String join(Iterable<? extends Object> elements, CharSequence separator) {
        StringBuilder builder = new StringBuilder();
        if (elements != null) {
            Iterator<? extends Object> iter = elements.iterator();
            if (iter.hasNext()) {
                builder.append(String.valueOf(iter.next()));
                while (iter.hasNext()) {
                    builder.append(separator).append(String.valueOf(iter.next()));
                }
            }
        }
        return builder.toString();
    }

    public static String fixLastSlash(String str) {
        String res = str == null ? "/" : str.trim() + "/";
        if (res.length() <= 2 || res.charAt(res.length() - 2) != LetvUtils.CHARACTER_BACKSLASH) {
            return res;
        }
        return res.substring(0, res.length() - 1);
    }

    public static int convertToInt(String str) throws NumberFormatException {
        int s = 0;
        while (s < str.length() && !Character.isDigit(str.charAt(s))) {
            s++;
        }
        int e = str.length();
        while (e > 0 && !Character.isDigit(str.charAt(e - 1))) {
            e--;
        }
        if (e > s) {
            try {
                return Integer.parseInt(str.substring(s, e));
            } catch (NumberFormatException ex) {
                LogTool.e(TAG, "convertToInt failed. " + ex.toString());
                throw new NumberFormatException();
            }
        }
        throw new NumberFormatException();
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        if (totalSeconds / 3600 > 0) {
            return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(totalSeconds / 3600), Integer.valueOf(minutes), Integer.valueOf(seconds)});
        }
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)});
    }

    private StringUtils() {
    }
}
