package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class OtaUpgradeHttpRequest {
    static final String TAG = "OtaUpgradeHttpRequest";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getUpgradeRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getOtaUpgradeParameter() {
        return new 2();
    }
}
