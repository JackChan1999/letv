package com.letv.core.parser;

import com.letv.core.bean.DrmSoUrlBean;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import org.json.JSONObject;

public class DrmSoUrlParser extends LetvMobileParser<DrmSoUrlBean> {
    protected DrmSoUrlBean parse(JSONObject data) throws Exception {
        DrmSoUrlBean result = new DrmSoUrlBean();
        result.version = data.optString("version", "1");
        result.url = data.optString("url");
        result.md5 = data.optString(Field.MD5_ID);
        result.size = data.optInt("size");
        result.pcode = data.optString("pcode");
        return result;
    }
}
