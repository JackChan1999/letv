package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.HeartBeatBean;

public class HeartBeatHttpRequest {
    static final String TAG = "HeartBeatHttpRequest";

    public static LeBoxHttpDynamicRequest<HeartBeatBean> getSyncStatusRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getWanStatusParameter() {
        return new 2();
    }
}
