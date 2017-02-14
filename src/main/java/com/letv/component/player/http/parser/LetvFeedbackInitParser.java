package com.letv.component.player.http.parser;

import android.util.Log;
import com.letv.component.player.http.bean.Cmdinfo;
import com.letv.component.player.http.bean.Order;
import com.letv.component.player.http.bean.Order.Tag;
import com.letv.component.player.http.parser.BaseParser.CODE_VALUES;
import com.sina.weibo.sdk.component.ShareRequestParam;
import org.json.JSONArray;
import org.json.JSONObject;

public class LetvFeedbackInitParser {
    private final String TAG = "LetvFeedbackParser";

    public Object parse(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        JSONObject o = new JSONObject((String) data);
        Log.i("LetvFeedbackParser", o.getString("code"));
        if (!o.getString("code").equals(CODE_VALUES.SUCCESS)) {
            return null;
        }
        Object cmdinfo = new Cmdinfo();
        if (!o.has(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA)) {
            return cmdinfo;
        }
        JSONObject mdata = o.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        Log.i("LetvFeedbackParser", "HttpFeedbackRequest:mdata=" + o);
        cmdinfo.setFbCode(mdata.getString("fbCode"));
        cmdinfo.setFbId(Long.valueOf(mdata.getLong("fbId")));
        cmdinfo.setUpPeriod(mdata.getInt("upPeriod"));
        cmdinfo.setEndTime(Long.valueOf(mdata.getLong("endTime")));
        JSONArray cmdArray = mdata.getJSONArray("cmdAry");
        Log.i("LetvFeedbackParser", "HttpFeedbackRequest:cmdArray=" + cmdArray);
        if (cmdArray != null) {
            Order order = new Order();
            for (int i = 0; i < cmdArray.length(); i++) {
                JSONObject myJson = (JSONObject) cmdArray.get(i);
                Tag tag = new Tag();
                tag.setCmdId(Long.valueOf(myJson.getLong("cmdId")));
                tag.setUpType(myJson.getInt("upType"));
                tag.setCmdType(myJson.getInt("cmdType"));
                tag.setCmdParam(myJson.getString("cmdParam"));
                tag.setCmdUrl(myJson.getString("cmdUrl"));
                order.add(tag);
            }
            Log.i("LetvFeedbackParser", "myorder" + order);
            cmdinfo.setCmdAry(order);
            return cmdinfo;
        }
        cmdinfo.setCmdAry(null);
        return cmdinfo;
    }
}
