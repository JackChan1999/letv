package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.KeyLoginBean;

public class KeyLoginHttpRequest {
    public static final String PERMISSION_IS_ADMINI = "1";
    public static final String PERMISSION_IS_GUEST = "0";
    static final String TAG = "KeyLoginHttpRequest";

    public static LeBoxHttpDynamicRequest<KeyLoginBean> getLoginRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getKeyLoginParameter() {
        return new 2();
    }
}
