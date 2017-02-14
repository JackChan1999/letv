package com.letv.core.network.volley.toolbox;

import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.listener.OnEntryResponse;

public class SimpleResponse<T> implements OnEntryResponse<T> {
    public void onNetworkResponse(VolleyRequest<T> volleyRequest, T t, DataHull hull, NetworkResponseState state) {
    }

    public void onCacheResponse(VolleyRequest<T> volleyRequest, T t, DataHull hull, CacheResponseState state) {
    }

    public void onErrorReport(VolleyRequest<T> volleyRequest, String errorInfo) {
    }
}
