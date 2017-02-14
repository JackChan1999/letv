package com.letv.core.parser;

import com.letv.core.bean.MobileCheckBean;
import org.json.JSONObject;

public class MobileCheckParser extends LetvMobileParser<MobileCheckBean> {
    public MobileCheckBean parse(JSONObject data) throws Exception {
        MobileCheckBean phoneNumCheckBean = new MobileCheckBean();
        JSONObject result = getJSONObject(data, "result");
        phoneNumCheckBean.code = getString(result, "code");
        phoneNumCheckBean.gjprice = getString(result, "gjprice");
        phoneNumCheckBean.msg = getString(result, "msg");
        phoneNumCheckBean.payType = getString(result, "payType");
        phoneNumCheckBean.price = getString(result, "price");
        phoneNumCheckBean.provider = getString(result, "provider");
        return phoneNumCheckBean;
    }
}
