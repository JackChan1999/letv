package com.letv.mobile.letvhttplib.parser;

import com.alibaba.fastjson.JSON;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import org.json.JSONException;
import org.json.JSONObject;

public class LetvMobileParser<T extends LetvBaseBean> extends LetvBaseParser<T, JSONObject> {
    public static final String BODY = "body";
    protected static final String HEADER = "header";
    protected static final String MARKID = "markid";
    protected static final String STATUS = "status";
    public String markid;
    public int status;

    public LetvMobileParser() {
        this(0);
    }

    public LetvMobileParser(int from) {
        super(from);
    }

    protected T parse(JSONObject data) throws Exception {
        if (this.mClz == null || data == null) {
            return null;
        }
        return (LetvBaseBean) JSON.parseObject(data.toString(), this.mClz);
    }

    protected boolean canParse(String data) {
        boolean canParse = false;
        try {
            JSONObject headObj = new JSONObject(data).optJSONObject(HEADER);
            if (isNull(headObj)) {
                setErrCode(0);
                return false;
            }
            this.status = headObj.optInt("status");
            setErrCode(this.status);
            switch (this.status) {
                case 1:
                    this.markid = headObj.optString(MARKID);
                    canParse = true;
                    break;
                case 2:
                    canParse = false;
                    break;
                case 4:
                    this.markid = headObj.optString(MARKID);
                    canParse = false;
                    break;
                case 6:
                    canParse = true;
                    break;
            }
            return canParse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected JSONObject getData(String data) throws Exception {
        if (this.status != 1 && this.status != 6) {
            return null;
        }
        JSONObject object = new JSONObject(data);
        if (isNull(object)) {
            return null;
        }
        JSONObject bodyJson = object.optJSONObject("body");
        if (isNull(bodyJson)) {
            return null;
        }
        return bodyJson;
    }

    public boolean hasUpdate() {
        return this.status != 4;
    }

    public String getMarkId() {
        return this.markid;
    }

    public boolean isNewData() {
        return this.status == 1;
    }

    public boolean isNoUpdate() {
        return this.status == 4;
    }
}
