package com.letv.core.network.volley;

import android.text.TextUtils;

public class VolleyResponse {
    public final String data;
    public final ResponseSupplier dispatcherType;
    public final Object entry;
    public final boolean success;

    public enum CacheResponseState {
        SUCCESS,
        ERROR,
        IGNORE
    }

    public enum NetworkResponseState {
        SUCCESS,
        PRE_FAIL,
        NETWORK_NOT_AVAILABLE,
        NETWORK_ERROR,
        RESULT_ERROR,
        RESULT_NOT_UPDATE,
        IGNORE,
        UNKONW
    }

    public VolleyResponse(ResponseSupplier responseSupplier) {
        this(null, null, false, responseSupplier);
    }

    public VolleyResponse(String data, ResponseSupplier responseSupplier) {
        this(data, null, !TextUtils.isEmpty(data), responseSupplier);
    }

    public VolleyResponse(Object entry, ResponseSupplier responseSupplier) {
        this(null, entry, entry != null, responseSupplier);
    }

    public VolleyResponse(String data, Object entry, boolean success, ResponseSupplier responseSupplier) {
        this.data = data;
        this.entry = entry;
        this.success = success;
        this.dispatcherType = responseSupplier;
    }
}
