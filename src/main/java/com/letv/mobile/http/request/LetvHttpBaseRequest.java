package com.letv.mobile.http.request;

import android.content.Context;
import com.letv.mobile.async.LetvTeleHttpAsyncRequest;
import com.letv.mobile.async.TaskCallBack;

public abstract class LetvHttpBaseRequest extends LetvTeleHttpAsyncRequest {
    protected static final String HOST_HEADER_KEY = "Host";

    public LetvHttpBaseRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    protected int getReadTimeOut() {
        return 10000;
    }

    protected int getConnectTimeOut() {
        return 10000;
    }
}
