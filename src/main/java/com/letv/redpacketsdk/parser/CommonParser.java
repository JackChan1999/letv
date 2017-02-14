package com.letv.redpacketsdk.parser;

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
}
