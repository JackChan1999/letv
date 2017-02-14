package com.letv.core.parser;

import com.alibaba.fastjson.JSON;
import com.letv.core.bean.AlipayUserSignStatusBean;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayUserSignStatusParser extends LetvMobileParser<AlipayUserSignStatusBean> {
    public AlipayUserSignStatusBean parse(JSONObject data) throws JSONException {
        AlipayUserSignStatusBean alipayUserSignStatusBean = new AlipayUserSignStatusBean();
        return (AlipayUserSignStatusBean) JSON.parseObject(data.getString("result"), AlipayUserSignStatusBean.class);
    }
}
