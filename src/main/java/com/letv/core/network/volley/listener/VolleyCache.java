package com.letv.core.network.volley.listener;

import com.letv.core.network.volley.VolleyRequest;

public interface VolleyCache<T> {
    void add(VolleyRequest<?> volleyRequest, T t);

    T get(VolleyRequest<?> volleyRequest);
}
