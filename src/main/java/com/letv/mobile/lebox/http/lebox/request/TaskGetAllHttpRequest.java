package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.TaskAllListBean;

public class TaskGetAllHttpRequest {
    protected static final String NEEDINFO = "needInfo";
    public static final String NEED_RETURN_INFO_FALSE = "0";
    public static final String NEED_RETURN_INFO_TRUE = "1";
    protected static final String PID = "pid";
    static final String TAG = "TaskGetAllHttpRequest";
    protected static final String TYPE = "type";
    public static final String TYPE_ALL = "0";
    public static final String TYPE_COMPLETED = "1";
    public static final String TYPE_NOT_FINISHED = "2";

    public static LeBoxHttpDynamicRequest<TaskAllListBean> getGetAllRequest(Context context, TaskCallBack callback) {
        return getGetAllRequest(context, callback, false);
    }

    public static LeBoxHttpDynamicRequest<TaskAllListBean> getGetAllRequest(Context context, TaskCallBack callback, boolean isSync) {
        return new 1(context, callback, isSync);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskGetAllParameter(String type, String pid, String needInfo) {
        return new 2(type, pid, needInfo);
    }
}
