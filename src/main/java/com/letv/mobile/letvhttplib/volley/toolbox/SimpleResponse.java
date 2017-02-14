package com.letv.mobile.letvhttplib.volley.toolbox;

import com.letv.mobile.letvhttplib.bean.DataHull;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import com.letv.mobile.letvhttplib.volley.VolleyRequest;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.CacheResponseState;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.NetworkResponseState;
import com.letv.mobile.letvhttplib.volley.listener.OnEntryResponse;

public class SimpleResponse<T extends LetvBaseBean> implements OnEntryResponse<T> {
    public void onNetworkResponse(VolleyRequest<T> volleyRequest, T t, DataHull hull, NetworkResponseState state) {
    }

    public void onCacheResponse(VolleyRequest<T> volleyRequest, T t, DataHull hull, CacheResponseState state) {
    }

    public void onErrorReport(VolleyRequest<T> volleyRequest, String errorInfo) {
    }
}
