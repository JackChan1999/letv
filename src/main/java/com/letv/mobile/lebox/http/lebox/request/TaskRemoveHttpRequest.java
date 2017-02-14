package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class TaskRemoveHttpRequest {
    static final String TAG = "TaskRemoveHttpRequest";
    protected static final String VID = "vid";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getRemoveRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskRemoveParameter(String vid) {
        return new 2(vid);
    }
}
