package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class FollowAddHttpRequest {
    protected static final String EXT = "ext";
    protected static final String FOLLOW_TAG = "tag";
    protected static final String PID = "pid";
    static final String TAG = "FollowAddHttpRequest";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getAddRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getFollowAddParameter(String pid, String ext, String tag) {
        return new 2(pid, ext, tag);
    }
}
