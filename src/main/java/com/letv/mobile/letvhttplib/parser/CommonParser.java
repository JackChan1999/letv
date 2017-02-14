package com.letv.mobile.letvhttplib.parser;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.letv.mobile.letvhttplib.utils.BaseTypeUtils;
import com.letv.mobile.letvhttplib.utils.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommonParser {
    protected boolean isNull(JSONArray array) {
        return array == null || array.length() == 0;
    }

    protected boolean isNull(JSONObject object) {
        return object == null || JSONObject.NULL.equals(object);
    }

    public boolean has(JSONObject jsonObject, String name) {
        return jsonObject.has(name);
    }

    protected int getInt(JSONObject object, String name) {
        return object.optInt(name);
    }

    protected int getInt(JSONArray array, int index) {
        return array.optInt(index);
    }

    protected long getLong(JSONObject object, String name) {
        return object.optLong(name);
    }

    protected long getLong(JSONArray array, int index) {
        return array.optLong(index);
    }

    protected boolean getBoolean(JSONObject object, String name) {
        return object.optBoolean(name);
    }

    protected boolean getBoolean(JSONArray array, int index) {
        return array.optBoolean(index);
    }

    protected float getFloat(JSONObject object, String name) {
        return BaseTypeUtils.stof(getString(object, name));
    }

    protected float getFloat(JSONArray array, int index) {
        return BaseTypeUtils.stof(getString(array, index));
    }

    protected String getString(JSONObject object, String name) {
        return object.optString(name, "");
    }

    protected String getString(JSONArray array, int index) {
        return array.optString(index, "");
    }

    protected JSONArray getJSONArray(JSONObject object, String name) {
        return object.optJSONArray(name);
    }

    protected JSONArray getJSONArray(JSONArray array, int index) {
        return array.optJSONArray(index);
    }

    protected JSONObject getJSONObject(JSONObject object, String name) {
        return object.optJSONObject(name);
    }

    protected JSONObject getJSONObject(JSONArray array, int index) {
        return array.optJSONObject(index);
    }

    public static <T> T getJsonObj(Class<T> c, String content) {
        Logger.d("fornia", "json string content::" + content);
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            return JSON.parseObject(content, c);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getJsonObj(Class<T> c, JSON content) {
        try {
            return JSON.toJavaObject(content, c);
        } catch (Exception e) {
            if (e != null) {
                Logger.d("fornia", "e.getMessage():" + e.getMessage());
            }
            return null;
        }
    }
}
