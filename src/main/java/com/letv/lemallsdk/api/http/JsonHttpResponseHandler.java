package com.letv.lemallsdk.api.http;

import android.os.Message;
import com.letv.core.messagebus.config.LeMessageIds;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHttpResponseHandler extends AsyncHttpResponseHandler {
    protected static final int SUCCESS_JSON_MESSAGE = 100;

    public void onSuccess(JSONObject response) {
    }

    public void onSuccess(JSONArray response) {
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        onSuccess(statusCode, response);
    }

    public void onSuccess(int statusCode, JSONObject response) {
        onSuccess(response);
    }

    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        onSuccess(statusCode, response);
    }

    public void onSuccess(int statusCode, JSONArray response) {
        onSuccess(response);
    }

    public void onFailure(Throwable e, JSONObject errorResponse) {
    }

    public void onFailure(Throwable e, JSONArray errorResponse) {
    }

    protected void sendSuccessMessage(int statusCode, Header[] headers, String responseBody) {
        if (statusCode != LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM) {
            try {
                Object jsonResponse = parseResponse(responseBody);
                sendMessage(obtainMessage(100, new Object[]{Integer.valueOf(statusCode), headers, jsonResponse}));
                return;
            } catch (JSONException e) {
                sendFailureMessage((Throwable) e, responseBody);
                return;
            }
        }
        sendMessage(obtainMessage(100, new Object[]{Integer.valueOf(statusCode), new JSONObject()}));
    }

    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case 100:
                Object[] response = msg.obj;
                handleSuccessJsonMessage(((Integer) response[0]).intValue(), (Header[]) response[1], response[2]);
                return;
            default:
                super.handleMessage(msg);
                return;
        }
    }

    protected void handleSuccessJsonMessage(int statusCode, Header[] headers, Object jsonResponse) {
        if (jsonResponse instanceof JSONObject) {
            onSuccess(statusCode, headers, (JSONObject) jsonResponse);
        } else if (jsonResponse instanceof JSONArray) {
            onSuccess(statusCode, headers, (JSONArray) jsonResponse);
        } else {
            onFailure(new JSONException("Unexpected type " + jsonResponse.getClass().getName()), null);
        }
    }

    protected Object parseResponse(String responseBody) throws JSONException {
        Object result = null;
        responseBody = responseBody.trim();
        if (responseBody.startsWith("{") || responseBody.startsWith("[")) {
            result = new JSONTokener(responseBody).nextValue();
        }
        if (result == null) {
            return responseBody;
        }
        return result;
    }

    protected void handleFailureMessage(Throwable e, String responseBody) {
        if (responseBody != null) {
            try {
                Object jsonResponse = parseResponse(responseBody);
                if (jsonResponse instanceof JSONObject) {
                    onFailure(e, (JSONObject) jsonResponse);
                    return;
                } else if (jsonResponse instanceof JSONArray) {
                    onFailure(e, (JSONArray) jsonResponse);
                    return;
                } else {
                    onFailure(e, responseBody);
                    return;
                }
            } catch (JSONException e2) {
                onFailure(e, responseBody);
                return;
            }
        }
        onFailure(e, "");
    }
}
