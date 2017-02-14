package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class TaskWaitHttpRequest {
    static final String TAG = "TaskWaitHttpRequest";
    protected static final String VID = "vid";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getWaitRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskWaitParameter(String vid) {
        return new 2(vid);
    }
}
