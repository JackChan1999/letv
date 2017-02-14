package com.letv.component.player.http.parser;

import com.letv.component.player.http.bean.HardSoftDecodeCapability;
import com.sina.weibo.sdk.component.ShareRequestParam;
import org.json.JSONObject;

public class HardSoftDecodeCapabilityPareser extends BaseParser {
    protected String getLocationData() {
        return null;
    }

    public Object parse(Object data) throws Exception {
        JSONObject jsonObject = (JSONObject) data;
        if (jsonObject == null) {
            return null;
        }
        if (!jsonObject.has(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA)) {
            return null;
        }
        JSONObject dataJSONObject = getJSONObject(jsonObject, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        Object capability = new HardSoftDecodeCapability();
        if (dataJSONObject == null) {
            return capability;
        }
        capability.mResult = getString(dataJSONObject, "status");
        return capability;
    }
}
