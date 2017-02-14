package com.letv.lemallsdk.command;

import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.MenuEntity;
import com.letv.lemallsdk.util.Constants;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseMenuEntity extends BaseParse {
    public BaseBean Json2Entity(String content) {
        BaseBean oneBean = new BaseBean();
        try {
            JSONObject all = new JSONObject(content);
            oneBean.setMessage(all.optString("message"));
            oneBean.setStatus(all.optString("status"));
            JSONObject resultObj = all.optJSONObject("result");
            if (resultObj != null) {
                JSONArray jsonArray = resultObj.optJSONArray("menuList");
                if (jsonArray != null && jsonArray.length() > 0) {
                    oneBean.setBeanList(new ArrayList());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MenuEntity menuObj = new MenuEntity();
                        JSONObject object = jsonArray.optJSONObject(i);
                        menuObj.setUrl(object.optString("url"));
                        menuObj.setTitle(object.optString("title"));
                        menuObj.setIcon(object.optString(SettingsJsonConstants.APP_ICON_KEY));
                        menuObj.setPageFlag(object.optString(Constants.PAGE_FLAG));
                        oneBean.getBeanList().add(menuObj);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oneBean;
    }
}
