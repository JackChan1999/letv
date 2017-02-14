package com.letv.core.utils;

import android.text.TextUtils;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BaseTypeUtils {
    public static int stoi(String str) {
        int value = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static int stoi(String str, int defaultValue) {
        int value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static float stof(String str) {
        float value = 0.0f;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static float stof(String str, float defaultValue) {
        float value = 0.0f;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static double stod(String str) {
        double value = 0.0d;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static double stod(String str, double defaultValue) {
        double value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static long stol(String str) {
        long value = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static long stol(String str, long defaultValue) {
        long value = defaultValue;
        if (TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException e) {
            }
        }
        return value;
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isQueueEmpty(Queue<T> queue) {
        return queue == null || queue.isEmpty();
    }

    public static <T> T getElementFromList(List<T> list, int index) {
        if (!isListEmpty(list) && index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public static <T> boolean isArrayEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> T getElementFromArray(T[] array, int index) {
        if (!isArrayEmpty(array) && index >= 0 && index < array.length) {
            return array[index];
        }
        return null;
    }

    public static int getElementFromIntArray(int[] array, int index) {
        if (array == null || array.length == 0 || index < 0 || index >= array.length) {
            return 0;
        }
        return array[index];
    }

    public static float getElementFromFloatArray(float[] array, int index) {
        if (array == null || array.length == 0 || index < 0 || index >= array.length) {
            return 0.0f;
        }
        return array[index];
    }

    public static boolean isMapEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isMapContainsKey(Map<? extends Object, ? extends Object> map, Object key) {
        return (key == null || isMapEmpty(map) || !map.containsKey(key)) ? false : true;
    }

    public static <T> T getElementFromMap(Map<? extends Object, T> map, Object key) {
        if (isMapContainsKey(map, key)) {
            return map.get(key);
        }
        return null;
    }

    public static String ensureStringValidate(String str) {
        return str == null ? "" : str;
    }

    public static String ensureStringValidateNumber(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }

    public static String checkUrl(String url) {
        if (url == null) {
            return url;
        }
        url.replaceAll(" ", "");
        if (url.startsWith("http://")) {
            return url;
        }
        return "http://" + url;
    }
}
