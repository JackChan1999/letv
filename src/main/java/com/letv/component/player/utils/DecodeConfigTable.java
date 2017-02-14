package com.letv.component.player.utils;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DecodeConfigTable {
    String sConfig = new String("[{\"model\":\"XIAOMI\",\"sysVer\":\"ANDROID OS 4.2\",\"status\":\"256,23\"}]");

    public String getStatus(Context context) {
        String status = "";
        try {
            JSONArray jsonArray = new JSONArray(this.sConfig);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String model = jsonObject.getString("model");
                String sysVer = jsonObject.getString("sysVer");
                if (model.trim().equalsIgnoreCase(Tools.getDeviceName()) && sysVer.trim().equalsIgnoreCase(Tools.getOSVersionName())) {
                    status = jsonObject.getString("status");
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
}
