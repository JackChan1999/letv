package com.coolcloud.uac.android.common.util;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KVUtils {
    public static String bundle2JSON(Bundle bundle) throws JSONException {
        JSONObject jo = new JSONObject();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (value instanceof Number) {
                put(jo, key, String.valueOf(value));
            } else {
                put(jo, key, bundle.getString(key));
            }
        }
        return jo.toString();
    }

    public static Bundle JSON2Bundle(String json) throws JSONException {
        Bundle bundle = new Bundle();
        JSONObject jo = new JSONObject(json);
        JSONArray ja = jo.names();
        for (int i = 0; i < ja.length(); i++) {
            String key = ja.getString(i);
            put(bundle, key, (String) jo.get(key));
        }
        return bundle;
    }

    public static JSONObject put(JSONObject jo, Bundle bundle, String key) throws JSONException {
        if (!(jo == null || bundle == null)) {
            put(jo, key, get(bundle, key));
        }
        return jo;
    }

    public static JSONObject put(JSONObject jo, String key, String value) throws JSONException {
        if (!(jo == null || TextUtils.empty(key) || TextUtils.empty(value))) {
            jo.put(key, value);
        }
        return jo;
    }

    public static String get(JSONObject jo, String key) {
        if (jo != null) {
            try {
                if (!TextUtils.empty(key) && jo.has(key)) {
                    return jo.getString(key);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String get(Bundle bundle, String key) {
        if (bundle == null || TextUtils.empty(key)) {
            return null;
        }
        return bundle.getString(key);
    }

    public static String get(Bundle bundle, String key, String defaultValue) {
        if (bundle == null || TextUtils.empty(key) || !bundle.containsKey(key)) {
            return defaultValue;
        }
        return bundle.getString(key);
    }

    public static boolean get(Bundle bundle, String key, boolean defaultValue) {
        if (bundle == null || TextUtils.empty(key) || !bundle.containsKey(key)) {
            return defaultValue;
        }
        return bundle.getBoolean(key);
    }

    public static int getInt(Bundle bundle, String key, int defaultValue) {
        if (bundle == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return bundle.getInt(key, defaultValue);
    }

    public static long getLong(Bundle bundle, String key, long defaultValue) {
        if (bundle == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return bundle.getLong(key, defaultValue);
    }

    public static boolean getBoolean(Bundle bundle, String key, boolean defaultValue) {
        if (bundle == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return bundle.getBoolean(key, defaultValue);
    }

    public static Bundle put(Bundle bundle, String key, String value) {
        if (!(bundle == null || TextUtils.empty(key) || TextUtils.empty(value))) {
            bundle.putString(key, value);
        }
        return bundle;
    }

    public static Bundle put(Bundle bundle, String key, int value) {
        if (!(bundle == null || TextUtils.empty(key))) {
            bundle.putInt(key, value);
        }
        return bundle;
    }

    public static Bundle put(Bundle bundle, String key, long value) {
        if (!(bundle == null || TextUtils.empty(key))) {
            bundle.putLong(key, value);
        }
        return bundle;
    }

    public static Bundle put(Bundle bundle, String key, boolean value) {
        if (!(bundle == null || TextUtils.empty(key))) {
            bundle.putBoolean(key, value);
        }
        return bundle;
    }

    public static Bundle put(Bundle to, Bundle from, String key) {
        if (!(to == null || from == null || TextUtils.empty(key) || !from.containsKey(key))) {
            Object value = from.get(key);
            if (value instanceof Boolean) {
                to.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (value instanceof Byte) {
                to.putByte(key, ((Byte) value).byteValue());
            } else if (value instanceof Character) {
                to.putChar(key, ((Character) value).charValue());
            } else if (value instanceof Short) {
                to.putShort(key, ((Short) value).shortValue());
            } else if (value instanceof Integer) {
                to.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                to.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                to.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Double) {
                to.putDouble(key, ((Double) value).doubleValue());
            } else if (value instanceof String) {
                to.putString(key, (String) value);
            }
        }
        return to;
    }

    public static String get(Intent i, String key) {
        if (i == null || TextUtils.empty(key)) {
            return null;
        }
        return i.getStringExtra(key);
    }

    public static ArrayList<Bundle> getList(Intent i, String key) {
        if (i == null || TextUtils.empty(key)) {
            return null;
        }
        return i.getParcelableArrayListExtra(key);
    }

    public static void putArrayList(Bundle i, String key, ArrayList<Bundle> mArrayList) {
        if (i != null && !TextUtils.empty(key)) {
            i.putParcelableArrayList(key, mArrayList);
        }
    }

    public static String get(Intent i, String key, String defaultValue) {
        if (i == null || TextUtils.empty(key) || !i.hasExtra(key)) {
            return defaultValue;
        }
        return i.getStringExtra(key);
    }

    public static boolean get(Intent i, String key, boolean defaultValue) {
        if (i == null || TextUtils.empty(key) || !i.hasExtra(key)) {
            return defaultValue;
        }
        return i.getBooleanExtra(key, defaultValue);
    }

    public static int getInt(Intent i, String key, int defaultValue) {
        if (i == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return i.getIntExtra(key, defaultValue);
    }

    public static long getLong(Intent i, String key, long defaultValue) {
        if (i == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return i.getLongExtra(key, defaultValue);
    }

    public static boolean getBoolean(Intent i, String key, boolean defaultValue) {
        if (i == null || TextUtils.empty(key)) {
            return defaultValue;
        }
        return i.getBooleanExtra(key, defaultValue);
    }

    public static Intent put(Intent i, String key, String value) {
        if (!(i == null || TextUtils.empty(key) || TextUtils.empty(value))) {
            i.putExtra(key, value);
        }
        return i;
    }

    public static Intent put(Intent i, String key, int value) {
        if (!(i == null || TextUtils.empty(key))) {
            i.putExtra(key, value);
        }
        return i;
    }

    public static Intent put(Intent i, String key, long value) {
        if (!(i == null || TextUtils.empty(key))) {
            i.putExtra(key, value);
        }
        return i;
    }

    public static Intent put(Intent i, String key, boolean value) {
        if (!(i == null || TextUtils.empty(key))) {
            i.putExtra(key, value);
        }
        return i;
    }

    public static Intent put(Intent i, Bundle bundle) {
        if (!(i == null || bundle == null)) {
            i.putExtras(bundle);
        }
        return i;
    }

    public static Intent put(Intent to, Bundle from, String key) {
        if (!(to == null || from == null || TextUtils.empty(key) || !from.containsKey(key))) {
            Object value = from.get(key);
            if (value instanceof Boolean) {
                to.putExtra(key, (Boolean) value);
            } else if (value instanceof Byte) {
                to.putExtra(key, (Byte) value);
            } else if (value instanceof Character) {
                to.putExtra(key, (Character) value);
            } else if (value instanceof Short) {
                to.putExtra(key, (Short) value);
            } else if (value instanceof Integer) {
                to.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                to.putExtra(key, (Long) value);
            } else if (value instanceof Float) {
                to.putExtra(key, (Float) value);
            } else if (value instanceof Double) {
                to.putExtra(key, (Double) value);
            } else if (value instanceof String) {
                to.putExtra(key, (String) value);
            } else if (value instanceof List) {
                to.putParcelableArrayListExtra(key, (ArrayList) value);
            }
        }
        return to;
    }

    public static Intent put(Intent to, Intent from, String key) {
        if (!(to == null || from == null || TextUtils.empty(key) || !from.hasExtra(key))) {
            Object value = from.getExtras().get(key);
            if (value instanceof Boolean) {
                to.putExtra(key, (Boolean) value);
            } else if (value instanceof Byte) {
                to.putExtra(key, (Byte) value);
            } else if (value instanceof Character) {
                to.putExtra(key, (Character) value);
            } else if (value instanceof Short) {
                to.putExtra(key, (Short) value);
            } else if (value instanceof Integer) {
                to.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                to.putExtra(key, (Long) value);
            } else if (value instanceof Float) {
                to.putExtra(key, (Float) value);
            } else if (value instanceof Double) {
                to.putExtra(key, (Double) value);
            } else if (value instanceof String) {
                to.putExtra(key, (String) value);
            }
        }
        return to;
    }

    public static Bundle put(Bundle to, Intent from, String key) {
        if (!(to == null || from == null || TextUtils.empty(key) || !from.hasExtra(key))) {
            Object value = from.getExtras().get(key);
            if (value instanceof Boolean) {
                to.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (value instanceof Byte) {
                to.putByte(key, ((Byte) value).byteValue());
            } else if (value instanceof Character) {
                to.putChar(key, ((Character) value).charValue());
            } else if (value instanceof Short) {
                to.putShort(key, ((Short) value).shortValue());
            } else if (value instanceof Integer) {
                to.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                to.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                to.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Double) {
                to.putDouble(key, ((Double) value).doubleValue());
            } else if (value instanceof String) {
                to.putString(key, (String) value);
            }
        }
        return to;
    }

    public static String toString(Bundle b) {
        if (b == null) {
            return "";
        }
        Bundle bundle = new Bundle();
        bundle.putAll(b);
        return bundle.toString();
    }

    public static void putArrayList(Intent i, String key, ArrayList<Bundle> mArrayList) {
        if (i != null && !TextUtils.empty(key)) {
            i.putParcelableArrayListExtra(key, mArrayList);
        }
    }

    public static JSONArray bundleList2JSONArray(List<Bundle> mList) throws JSONException {
        if (mList == null) {
            return null;
        }
        JSONArray mJSONArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            Bundle mBundle = (Bundle) mList.get(i);
            if (mBundle != null) {
                JSONObject mJSONObject = new JSONObject();
                for (String key : mBundle.keySet()) {
                    mJSONObject.put(key, get(mBundle, key));
                }
                mJSONArray.put(mJSONObject);
            }
        }
        return mJSONArray;
    }
}
