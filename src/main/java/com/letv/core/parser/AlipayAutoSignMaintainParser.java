package com.letv.core.parser;

import com.alibaba.fastjson.JSON;
import com.letv.core.bean.AlipayAutoPayMaintainBean;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayAutoSignMaintainParser extends LetvMobileParser<AlipayAutoPayMaintainBean> {
    public AlipayAutoPayMaintainBean parse(JSONObject data) throws JSONException {
        AlipayAutoPayMaintainBean alipayAutoPayMaintainBean = new AlipayAutoPayMaintainBean();
        return (AlipayAutoPayMaintainBean) JSON.parseObject(data.getString("result"), AlipayAutoPayMaintainBean.class);
    }
}
