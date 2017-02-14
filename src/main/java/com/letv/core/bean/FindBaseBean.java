package com.letv.core.bean;

import com.letv.core.db.PreferencesManager;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class FindBaseBean {
    public abstract String getTimeStamp();

    public abstract String getTimeStampKey();

    public boolean isNewData() {
        boolean b = true;
        JSONObject timeStampJson = PreferencesManager.getInstance().getFindTimeStamp();
        if (timeStampJson != null) {
            String prefTimes = timeStampJson.optString(getTimeStampKey());
            if (prefTimes != null) {
                if (prefTimes.equals(getTimeStamp())) {
                    b = false;
                }
            }
        }
        return b;
    }

    public void saveTimeStamp() {
        JSONObject jsonObject = PreferencesManager.getInstance().getFindTimeStamp();
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        try {
            jsonObject.put(getTimeStampKey(), getTimeStamp());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PreferencesManager.getInstance().saveFindTimeStamp(jsonObject);
    }
}
