package com.letv.core.parser;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

public class LetvMasterParser<T> extends LetvBaseParser<T, JSONObject> {
    public static final String ACTION = "action";
    public static final String BEAN = "bean";
    public static final String ERRORCODE = "errorCode";
    public static final String MESSAGE = "message";
    public static final String RESPONSETYPE = "responseType";
    public static final String STATUS = "status";

    public LetvMasterParser() {
        this(0);
    }

    public LetvMasterParser(int from) {
        super(from);
    }

    protected T parse(JSONObject data) throws Exception {
        if (this.mClz == null || data == null) {
            return null;
        }
        return JSON.parseObject(data.toString(), this.mClz);
    }

    protected boolean canParse(String data) {
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("status")) {
                int status = object.getInt("status");
                int errorCode = object.optInt("errorCode");
                setMessage(object.optString("message"));
                setErrCode(errorCode);
                if (status == 1 && errorCode == 0) {
                    return true;
                }
                return false;
            }
            setErrCode(0);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected JSONObject getData(String data) throws Exception {
        return new JSONObject(data).optJSONObject(BEAN);
    }
}
