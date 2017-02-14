package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.StorageGetInfoBean;

public class StorageGetInfoHttpRequest {
    static final String TAG = "StorageGetInfoHttpRequest";

    public static LeBoxHttpDynamicRequest<StorageGetInfoBean> getGetInfoRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getStorageGetInfoParameter() {
        return new 2();
    }
}
