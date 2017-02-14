package com.letv.core.parser;

import com.alibaba.fastjson.JSON;
import com.letv.core.bean.AlipayOneKeyPayBean;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayOneKeyPayParser extends LetvMobileParser<AlipayOneKeyPayBean> {
    public AlipayOneKeyPayBean parse(JSONObject data) throws JSONException {
        AlipayOneKeyPayBean alipayAutoSignPayBean = new AlipayOneKeyPayBean();
        return (AlipayOneKeyPayBean) JSON.parseObject(data.getString("result"), AlipayOneKeyPayBean.class);
    }
}
