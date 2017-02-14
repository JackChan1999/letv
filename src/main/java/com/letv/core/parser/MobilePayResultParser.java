package com.letv.core.parser;

import com.letv.core.bean.MobilePayResultBean;
import org.json.JSONObject;

public class MobilePayResultParser extends LetvMobileParser<MobilePayResultBean> {
    public MobilePayResultBean parse(JSONObject data) throws Exception {
        MobilePayResultBean mobilePayResultBean = new MobilePayResultBean();
        JSONObject result = getJSONObject(data, "result");
        mobilePayResultBean.ordernumber = getString(result, "ordernumber");
        mobilePayResultBean.phone = getString(result, "phone");
        mobilePayResultBean.status = getString(result, "status");
        mobilePayResultBean.uid = getString(result, "uid");
        return mobilePayResultBean;
    }
}
