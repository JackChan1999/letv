package com.letv.mobile.letvhttplib.volley.toolbox;

import com.letv.mobile.letvhttplib.volley.VolleyRequest;
import com.letv.mobile.letvhttplib.volley.listener.VolleyCache;

public class VolleyNoCache implements VolleyCache<String> {
    public synchronized String get(VolleyRequest<?> volleyRequest) {
        return null;
    }

    public synchronized void add(VolleyRequest<?> volleyRequest, String data) {
    }
}
