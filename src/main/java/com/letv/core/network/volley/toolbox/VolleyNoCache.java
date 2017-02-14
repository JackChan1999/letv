package com.letv.core.network.volley.toolbox;

import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.listener.VolleyCache;

public class VolleyNoCache implements VolleyCache<String> {
    public synchronized String get(VolleyRequest<?> volleyRequest) {
        return null;
    }

    public synchronized void add(VolleyRequest<?> volleyRequest, String data) {
    }
}
