package com.letv.mobile.letvhttplib.volley.listener;

import com.letv.mobile.letvhttplib.volley.VolleyRequest;

public interface VolleyCache<T> {
    void add(VolleyRequest<?> volleyRequest, T t);

    T get(VolleyRequest<?> volleyRequest);
}
