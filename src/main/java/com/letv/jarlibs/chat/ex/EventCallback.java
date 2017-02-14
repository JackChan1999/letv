package com.letv.jarlibs.chat.ex;

import org.json.JSONArray;

public interface EventCallback {
    void onEvent(JSONArray jSONArray, Acknowledge acknowledge);
}
