package com.letv.core.parser;

import android.text.TextUtils;
import com.letv.core.network.volley.exception.DataIsErrException;
import com.letv.core.network.volley.exception.DataIsNullException;
import com.letv.core.network.volley.exception.DataNoUpdateException;
import com.letv.core.network.volley.exception.JsonCanNotParseException;
import com.letv.core.network.volley.exception.ParseException;
import com.letv.core.network.volley.exception.TokenLoseException;
import com.letv.core.utils.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class LetvBaseParser<T, D> extends CommonParser {
    private static final String CHATROOM_TOKEN_INVALID_CODE = "403";
    private static final String CHATROOM_TOKEN_INVALID_RESULT_CODE = "1001";
    private static final String COMMENT_TOKEN_INVALID_RESULT = "notlogged";
    private static final String CREDIT_TOKEN_INVALID_CODE = "201";
    private static final String ERROR_CODE_1002 = "1002";
    private static final String ERROR_CODE_1014 = "1014";
    private static final String ERROR_CODE_1020 = "1020";
    private static final String HOT_POINT_TOKEN_INVALID_CODE = "403";
    private static final String PLAY_RECORD_TOKEN_INVALID_STATUS = "5";
    protected Class<T> mClz;
    private int mErrCode;
    private int mFrom;
    private String mMessage;
    private boolean mShouldCheckToken = false;

    protected abstract boolean canParse(String str);

    protected abstract D getData(String str) throws Exception;

    protected abstract T parse(D d) throws Exception;

    public LetvBaseParser(int from) {
        this.mFrom = from;
    }

    public boolean hasUpdate() {
        return true;
    }

    public void setShouldCheckToken(boolean shouldCheckToken) {
        this.mShouldCheckToken = shouldCheckToken;
    }

    public T doParse(String data) throws DataIsNullException, DataIsErrException, TokenLoseException, ParseException, JsonCanNotParseException, DataNoUpdateException {
        if (TextUtils.isEmpty(data)) {
            throw new DataIsNullException();
        }
        try {
            JSONObject object = new JSONObject(data);
            if (this.mShouldCheckToken && isTokenInvalid(object)) {
                LogInfo.log("ZSM 需要验证token");
                throw new TokenLoseException();
            } else if (canParse(data)) {
                try {
                    D d = getData(data);
                    if (d == null) {
                        throw new ParseException();
                    }
                    try {
                        return parse(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ParseException();
                    }
                } catch (Exception e2) {
                    throw new DataIsErrException();
                }
            } else if (hasUpdate()) {
                throw new JsonCanNotParseException();
            } else {
                throw new DataNoUpdateException();
            }
        } catch (JSONException e3) {
            throw new DataIsErrException();
        } catch (NumberFormatException e4) {
            throw new DataIsErrException();
        }
    }

    public boolean isTokenInvalid(JSONObject object) {
        if (isChatRoomTokenInvalid(object) || isHotPointTokenInvalid(object) || isPlayRecordTokenInvalid(object) || isCommentTokenInvalid(object) || isCreditTokenInvalid(object)) {
            return true;
        }
        String errorCode = object.optString("errorCode");
        if (TextUtils.isEmpty(errorCode)) {
            return false;
        }
        if (TextUtils.equals(errorCode, "1002") && object.has("action") && TextUtils.isEmpty(object.optString("action"))) {
            return true;
        }
        if (TextUtils.equals(errorCode, ERROR_CODE_1014) || TextUtils.equals(errorCode, ERROR_CODE_1020)) {
            return true;
        }
        return false;
    }

    private boolean isCommentTokenInvalid(JSONObject object) {
        boolean isInvalid = false;
        JSONObject body = object.optJSONObject("body");
        if (!isNull(body)) {
            if (TextUtils.equals(COMMENT_TOKEN_INVALID_RESULT, body.optString("result"))) {
                isInvalid = true;
            }
        }
        LogInfo.log("commentTokenInvalid", "isInvalid : " + isInvalid);
        return isInvalid;
    }

    private boolean isPlayRecordTokenInvalid(JSONObject object) {
        boolean isInvalid = false;
        JSONObject header = object.optJSONObject("header");
        if (!isNull(header) && TextUtils.equals(header.optString("status"), "5")) {
            isInvalid = true;
        }
        LogInfo.log("playRecordTokenInvalid", "isInvalid : " + isInvalid);
        return isInvalid;
    }

    private boolean isHotPointTokenInvalid(JSONObject object) {
        boolean isInvalid = false;
        JSONObject body = object.optJSONObject("body");
        if (!isNull(body) && TextUtils.equals(body.optString("code"), "403")) {
            isInvalid = true;
        }
        LogInfo.log("isHotPointTokenInvalid", "isInvalid : " + isInvalid);
        return isInvalid;
    }

    private boolean isCreditTokenInvalid(JSONObject object) {
        JSONObject body = object.optJSONObject("body");
        if (isNull(body) || !TextUtils.equals(body.optString("code"), "201")) {
            return false;
        }
        return true;
    }

    private boolean isChatRoomTokenInvalid(JSONObject object) {
        boolean isInvalid = false;
        JSONObject body = object.optJSONObject("body");
        if (isNull(body)) {
            return false;
        }
        JSONObject result = body.optJSONObject("result");
        if (isNull(result)) {
            return false;
        }
        if (TextUtils.equals(result.optString("code"), "1001")) {
            isInvalid = true;
        }
        if (TextUtils.equals(body.optString("code"), "403")) {
            return true;
        }
        return isInvalid;
    }

    public int getErrCode() {
        return this.mErrCode;
    }

    protected void setErrCode(int errCode) {
        this.mErrCode = errCode;
    }

    protected void setMessage(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getFrom() {
        return this.mFrom;
    }

    public void setClz(Class<T> clz) {
        this.mClz = clz;
    }
}
