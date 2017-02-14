package com.letv.core.parser;

import android.text.TextUtils;
import com.letv.core.bean.LetvOrderInfo;
import com.letv.core.bean.LetvOrderInfo.Data;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.tencent.open.SocialConstants;
import org.json.JSONObject;

public class LetvOrderInfoParse extends LetvBaseParser<LetvOrderInfo, JSONObject> {
    public LetvOrderInfoParse() {
        super(0);
    }

    public LetvOrderInfo parse(JSONObject object) throws Exception {
        LetvOrderInfo entity = new LetvOrderInfo();
        try {
            entity.code = object.getString("code");
            entity.message = object.getString("message");
            Data info = new Data();
            JSONObject object1 = object.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
            info.canorder = object1.getString("canorder");
            info.desc = object1.getString(SocialConstants.PARAM_APP_DESC);
            if (object1.has("username")) {
                info.username = object1.getString("username");
            }
            entity.data = info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    protected boolean canParse(String data) {
        return !TextUtils.isEmpty(data);
    }

    protected JSONObject getData(String data) throws Exception {
        return new JSONObject(data);
    }
}
