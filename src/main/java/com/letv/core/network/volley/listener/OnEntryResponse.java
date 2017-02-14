package com.letv.core.network.volley.listener;

import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;

public interface OnEntryResponse<T> {
    void onCacheResponse(VolleyRequest<T> volleyRequest, T t, DataHull dataHull, CacheResponseState cacheResponseState);

    void onErrorReport(VolleyRequest<T> volleyRequest, String str);

    void onNetworkResponse(VolleyRequest<T> volleyRequest, T t, DataHull dataHull, NetworkResponseState networkResponseState);
}
