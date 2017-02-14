package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.OtaVersionBean;

public class OtaVersionHttpRequest {
    public static final String FALSE = "false";
    static final String TAG = "OtaVersionHttpRequest";
    public static final String TRUE = "true";

    public static LeBoxHttpDynamicRequest<OtaVersionBean> getVersionRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getOtaVersionParameter() {
        return new 2();
    }
}
