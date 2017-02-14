package com.letv.android.client.album.half.parser;

import com.facebook.internal.NativeProtocol;
import com.letv.core.bean.CommentAddBean;
import com.letv.core.parser.LetvMasterParser;
import org.json.JSONException;
import org.json.JSONObject;

public class ReplyAddParser extends LetvMasterParser<CommentAddBean> {
    protected final String BODY = "body";
    protected final String HEADER = "header";
    private final String RESULT = "result";
    private final String RULE = "rule";
    private final String STATUS = "status";
    private int status;

    public CommentAddBean parse(JSONObject data) throws JSONException {
        CommentAddBean add = new CommentAddBean();
        if (this.status == 3) {
            add.result = NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE;
        } else if (this.status == 1 && has(data, "result")) {
            add.result = getString(data, "result");
            if ("200".equals(add.result)) {
                add.rule = getInt(data, "rule");
            }
        }
        add.status = String.valueOf(this.status);
        return add;
    }

    protected boolean canParse(String data) {
        try {
            JSONObject object = new JSONObject(data);
            if (!object.has("header")) {
                return false;
            }
            this.status = getInt(object.getJSONObject("header"), "status");
            return true;
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
