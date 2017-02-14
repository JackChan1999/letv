package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.WifiNameGetBean;

public class WifiNameGetHttpRequest {
    static final String TAG = "WifiNameGetHttpRequest";

    public static LeBoxHttpDynamicRequest<WifiNameGetBean> getNameGetRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getWifiNameGetParameter() {
        return new 2();
    }
}
