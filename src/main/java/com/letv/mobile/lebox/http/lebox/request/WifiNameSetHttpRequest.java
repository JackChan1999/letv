package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class WifiNameSetHttpRequest {
    protected static final String NAME = "name";
    static final String TAG = "WifiNameSetHttpRequest";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getNameSetRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getWifiNameSetParameter(String name) {
        return new 2(name);
    }
}
