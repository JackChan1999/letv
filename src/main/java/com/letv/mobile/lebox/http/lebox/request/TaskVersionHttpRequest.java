package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.TaskVersionBean;

public class TaskVersionHttpRequest {
    static final String TAG = "TaskVersionHttpRequest";

    public static LeBoxHttpDynamicRequest<TaskVersionBean> getVersionRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskVersionParameter() {
        return new 2();
    }
}
