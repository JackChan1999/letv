package com.letv.mobile.letvhttplib.volley.listener;

import com.letv.mobile.letvhttplib.bean.DataHull;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import com.letv.mobile.letvhttplib.volley.VolleyRequest;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.CacheResponseState;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.NetworkResponseState;

public interface OnEntryResponse<T extends LetvBaseBean> {
    void onCacheResponse(VolleyRequest<T> volleyRequest, T t, DataHull dataHull, CacheResponseState cacheResponseState);

    void onErrorReport(VolleyRequest<T> volleyRequest, String str);

    void onNetworkResponse(VolleyRequest<T> volleyRequest, T t, DataHull dataHull, NetworkResponseState networkResponseState);
}
