package com.letv.core.parser;

import com.letv.core.bean.LiveDateInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveDateInfoParser extends LetvMobileParser<LiveDateInfo> {
    public LiveDateInfo parse(JSONObject data) throws JSONException {
        if (data == null || !has(data, "date") || !has(data, "week_day")) {
            return null;
        }
        LiveDateInfo mLiveDateInfo = new LiveDateInfo();
        mLiveDateInfo.date = getString(data, "date");
        mLiveDateInfo.week_day = getString(data, "week_day");
        return mLiveDateInfo;
    }
}
