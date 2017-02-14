package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class FollowDelHttpRequest {
    protected static final String PID = "pid";
    static final String TAG = "FollowDelHttpRequest";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getDelRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getFollowDelParameter(String pid) {
        return new 2(pid);
    }
}
