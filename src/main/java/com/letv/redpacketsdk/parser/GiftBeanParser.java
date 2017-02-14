package com.letv.redpacketsdk.parser;

import android.text.TextUtils;
import com.letv.redpacketsdk.bean.GiftBean;
import com.letv.redpacketsdk.utils.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class GiftBeanParser extends CommonParser {
    private final String BIG_TYPE = "big_type";
    private final String COMPANY_NAME = "company_name";
    private final String IS_FIRST = "is_first";
    private final String PRICE_IMAGE = "price_image";
    private final String TITLE = "title";

    public GiftBean parser(String jsonString) {
        LogInfo.log("GiftBeanParser", "GiftBeanParser + GiftBeanString =" + jsonString);
        GiftBean giftBean = new GiftBean();
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONObject json = new JSONObject(jsonString);
                giftBean.is_first = json.optBoolean("is_first", true);
                giftBean.big_type = json.optInt("big_type");
                giftBean.title = json.optString("title");
                giftBean.price_image = json.optString("price_image");
                giftBean.company_name = json.optString("company_name");
            } catch (JSONException e) {
                LogInfo.log("GiftBeanParser", "jsonString error");
                e.printStackTrace();
            }
        }
        LogInfo.log("GiftBeanParser", "parser + GiftBean =" + giftBean.toString());
        return giftBean;
    }
}
