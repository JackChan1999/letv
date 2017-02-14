package com.letv.core.parser;

import com.letv.core.bean.RedPacketBean;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import org.json.JSONObject;

public class RedPacketParser extends LetvMobileParser<RedPacketBean> {
    public RedPacketBean parse(JSONObject data) throws Exception {
        RedPacketBean bean = new RedPacketBean();
        bean.code = data.getString("code");
        if (data.has("package")) {
            JSONObject object = data.getJSONObject("package");
            if (object.has("mobilePic")) {
                bean.mobilePic = object.getString("mobilePic");
            }
            if (object.has("title")) {
                bean.title = object.getString("title");
            }
            if (object.has("shorDesc")) {
                bean.shorDesc = object.getString("shorDesc");
            }
            if (object.has("limitNum")) {
                bean.limitNum = object.getString("limitNum");
            }
            if (object.has("url")) {
                bean.url = object.getString("url");
            }
            if (object.has("skipUrl")) {
                bean.skipUrl = object.getString("skipUrl");
            }
            if (object.has("activeID")) {
                bean.activeID = object.getString("activeID");
            }
            if (object.has("leftButton")) {
                bean.leftButton = object.getString("leftButton");
            }
            if (object.has("rightButton")) {
                bean.rightButton = object.getString("rightButton");
            }
            if (object.has("shareTitle")) {
                bean.shareTitle = object.getString("shareTitle");
            }
            if (object.has("shareDesc")) {
                bean.shareDesc = object.getString("shareDesc");
            }
            if (object.has("sharePic")) {
                bean.sharePic = object.getString("sharePic");
            }
            if (object.has(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
                bean.shareDesc = object.getString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
            }
            if (object.has("imgUrl")) {
                bean.mobilePic = object.getString("imgUrl");
            }
            if (object.has("shareUrl")) {
                bean.url = object.getString("shareUrl");
            }
            if (object.has("channelId")) {
                bean.channelId = object.getInt("channelId");
            }
        }
        return bean;
    }
}
