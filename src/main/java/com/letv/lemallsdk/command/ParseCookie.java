package com.letv.lemallsdk.command;

import android.text.TextUtils;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.model.BaseBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseCookie extends BaseParse {
    public BaseBean Json2Entity(String content) {
        try {
            JSONObject all = new JSONObject(content);
            String message = all.optString("message");
            String status = all.optString("status");
            JSONArray jsonArray = all.optJSONArray("result");
            if (jsonArray != null && jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String cookieData = "";
                if (TextUtils.isEmpty(jsonObject.optString("COOKIE_USER_ID"))) {
                    cookieData = jsonObject.optString("COOKIE_LINKDATA");
                } else {
                    cookieData = jsonObject.optString("COOKIE_S_LINKDATA");
                }
                LemallPlatform.getInstance().setCookieLinkdata(cookieData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
