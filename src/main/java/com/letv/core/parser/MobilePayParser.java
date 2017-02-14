package com.letv.core.parser;

import com.letv.core.bean.MobilePayBean;
import org.json.JSONObject;

public class MobilePayParser extends LetvMobileParser<MobilePayBean> {
    public MobilePayBean parse(JSONObject data) throws Exception {
        MobilePayBean mobilePayBean = new MobilePayBean();
        JSONObject result = getJSONObject(data, "result");
        mobilePayBean.code = getString(result, "code");
        mobilePayBean.command = getString(result, "command");
        mobilePayBean.corderid = getString(result, "corderid");
        mobilePayBean.msg = getString(result, "msg");
        mobilePayBean.posturl = getString(result, "posturl");
        mobilePayBean.servicecode = getString(result, "servicecode");
        if (result != null && result.has("code") && result.has("msg") && result.has("corderid")) {
            if (result.has("posturl")) {
                mobilePayBean.type = 1;
            } else if (result.has("command") || result.has("servicecode")) {
                mobilePayBean.type = 3;
            } else {
                mobilePayBean.type = 2;
            }
        }
        return mobilePayBean;
    }
}
