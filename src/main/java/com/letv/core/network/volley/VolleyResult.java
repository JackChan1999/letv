package com.letv.core.network.volley;

import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;

public class VolleyResult<T> {
    public final CacheResponseState cacheState;
    public final DataHull dataHull;
    public final String errorInfo;
    public final NetworkResponseState networkState;
    public final T result;

    public VolleyResult(T result, DataHull dataHull, NetworkResponseState networkState, CacheResponseState cacheState, String errorInfo) {
        this.result = result;
        this.dataHull = dataHull;
        this.networkState = networkState;
        this.cacheState = cacheState;
        this.errorInfo = errorInfo;
    }
}
