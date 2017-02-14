package com.letv.lemallsdk.command;

import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.MenuEntity;
import com.letv.lemallsdk.model.TitleStatus;
import com.letv.lemallsdk.util.Constants;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseTitleStatus extends BaseParse {
    public BaseBean Json2Entity(String content) {
        BaseBean oneBean = new BaseBean();
        try {
            JSONObject all = new JSONObject(content);
            oneBean.setMessage(all.optString("message"));
            oneBean.setStatus(all.optString("status"));
            JSONObject resultObj = all.optJSONObject("result");
            if (resultObj != null) {
                JSONArray jsonArray = resultObj.optJSONArray("toolList");
                if (jsonArray != null && jsonArray.length() > 0) {
                    oneBean.setBeanList(new ArrayList());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        TitleStatus titleObj = new TitleStatus();
                        if (object != null) {
                            JSONArray menuArray = object.optJSONArray("menuList");
                            if (menuArray != null && menuArray.length() > 0) {
                                titleObj.setMenuList(new ArrayList());
                                for (int j = 0; j < menuArray.length(); j++) {
                                    MenuEntity menuObj = new MenuEntity();
                                    JSONObject menuJson = menuArray.optJSONObject(j);
                                    menuObj.setUrl(menuJson.optString("url"));
                                    menuObj.setTitle(menuJson.optString("title"));
                                    menuObj.setIcon(menuJson.optString(SettingsJsonConstants.APP_ICON_KEY));
                                    menuObj.setPageFlag(menuJson.optString(Constants.PAGE_FLAG));
                                    titleObj.getMenuList().add(menuObj);
                                }
                            }
                            titleObj.setPageFlag(object.optString(Constants.PAGE_FLAG));
                            titleObj.setTitle(object.optString("title"));
                            titleObj.setHasShare(object.optString("hasShare"));
                            titleObj.setHasMore(object.optString("hasMore"));
                        }
                        oneBean.getBeanList().add(titleObj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oneBean;
    }
}
