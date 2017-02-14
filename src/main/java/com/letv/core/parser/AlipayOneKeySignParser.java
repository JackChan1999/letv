package com.letv.core.parser;

import com.alibaba.fastjson.JSON;
import com.letv.core.bean.AlipayOneKeySignBean;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayOneKeySignParser extends LetvMobileParser<AlipayOneKeySignBean> {
    public AlipayOneKeySignBean parse(JSONObject data) throws JSONException {
        AlipayOneKeySignBean alipayOneKeySignBean = new AlipayOneKeySignBean();
        return (AlipayOneKeySignBean) JSON.parseObject(data.getString("result"), AlipayOneKeySignBean.class);
    }
}
