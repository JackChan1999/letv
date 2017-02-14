package com.letv.component.player.http.parser;

import com.letv.component.core.http.parse.LetvMainParser;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseParser extends LetvMainParser {
    protected static final String CODE = "code";
    protected static final String DATA = "data";
    protected static final String TIMESTAMP = "timestamp";
    private String code;
    private String timestamp;

    public interface CODE_VALUES {
        public static final String FAILURE = "A000014";
        public static final String NODATA = "A000004";
        public static final String PARAMETER_INVALID = "A000001";
        public static final String SERVER_ERR = "E000000";
        public static final String SUCCESS = "A000000";
    }

    public BaseParser(int from) {
        super(from);
    }

    protected boolean canParse(String data) {
        try {
            JSONObject object = new JSONObject(data);
            if (!object.has("code")) {
                return false;
            }
            this.code = getString(object, "code");
            this.timestamp = getString(object, "timestamp");
            if (CODE_VALUES.SUCCESS.equals(this.code)) {
                return true;
            }
            setMessage(this.code);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected JSONObject getData(String data) throws JSONException {
        if (CODE_VALUES.SUCCESS.equals(this.code)) {
            return new JSONObject(data);
        }
        if (CODE_VALUES.FAILURE.equals(this.code)) {
            return new JSONObject(getLocationData());
        }
        return null;
    }

    protected String getTimeStamp() {
        return this.timestamp;
    }

    protected String getLocationData() {
        return null;
    }
}
