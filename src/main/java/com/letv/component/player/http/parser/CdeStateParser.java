package com.letv.component.player.http.parser;

import com.letv.component.player.http.bean.CdeState;
import com.sina.weibo.sdk.component.ShareRequestParam;
import org.json.JSONObject;

public class CdeStateParser extends BaseParser {
    public Object parse(Object data) throws Exception {
        JSONObject jsonObject = (JSONObject) data;
        if (jsonObject == null) {
            return null;
        }
        if (!jsonObject.has(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA)) {
            return null;
        }
        JSONObject dataJSONObject = getJSONObject(jsonObject, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        Object state = new CdeState();
        if (dataJSONObject == null) {
            return state;
        }
        state.downloadedDuration = getString(dataJSONObject, "downloadedDuration");
        state.downloadedRate = getString(dataJSONObject, "downloadedRate");
        return state;
    }
}
