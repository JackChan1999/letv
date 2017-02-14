package com.letv.android.client.album.half.parser;

import com.letv.core.bean.ReplyListBean;
import com.letv.core.constant.DownloadConstant.BroadcaseIntentParams;
import com.letv.core.parser.LetvMasterParser;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReplyListParser extends LetvMasterParser<ReplyListBean> {
    protected final String BODY = "body";
    protected final String DATA = ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA;
    protected final String HEADER = "header";
    protected final String RESULT = "result";
    private final String RULE = "rule";
    protected final String STATUS = "status";
    protected final String TOTAL = BroadcaseIntentParams.KEY_TOTAL;
    private int status;

    public ReplyListBean parse(JSONObject data) throws JSONException {
        ReplyListBean replyList = new ReplyListBean();
        if (this.status == 1 && has(data, "result")) {
            String result = getString(data, "result");
            if ("200".equals(result)) {
                replyList.rule = Integer.valueOf(getString(data, "rule")).intValue();
                replyList.total = getInt(data, BroadcaseIntentParams.KEY_TOTAL);
                if (has(data, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA) && replyList.total > 0) {
                    JSONArray array = getJSONArray(data, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                    if (array != null && array.length() > 0) {
                        replyList.data = new LinkedList();
                        for (int i = 0; i < array.length(); i++) {
                            replyList.data.addLast(new ReplyParser().parse(array.getJSONObject(i)));
                        }
                    }
                }
            } else {
                replyList.errorMsg = result;
            }
        }
        return replyList;
    }

    protected boolean canParse(String data) {
        boolean z = true;
        try {
            JSONObject object = new JSONObject(data);
            if (!object.has("header")) {
                return false;
            }
            this.status = getInt(object.getJSONObject("header"), "status");
            if (this.status == 3) {
                return false;
            }
            if (this.status != 1) {
                z = false;
            }
            return z;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected JSONObject getData(String data) throws Exception {
        if (this.status == 1) {
            return getJSONObject(new JSONObject(data), "body");
        }
        return null;
    }
}
