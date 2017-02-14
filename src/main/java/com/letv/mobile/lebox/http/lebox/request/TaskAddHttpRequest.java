package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;

public class TaskAddHttpRequest {
    protected static final String EXT = "ext";
    protected static final String PARAM_TAG = "tag";
    protected static final String PID = "pid";
    static final String TAG = "TaskAddHttpRequest";
    protected static final String VID = "vid";
    public static final String albumCover = "albumCover";
    public static final String albumTitle = "albumTitle";
    public static final String cover = "cover";
    public static final String isEnd = "isEnd";
    public static final String isWatch = "isWatch";
    public static final String stream = "stream";
    public static final String title = "title";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getAddRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskAddParameter(TaskVideoBean task, String stream) {
        return getTaskAddParameter(task.getVid(), task.getPid(), String.format("{stream:’%s’}", new Object[]{stream}), task.getTag().toString());
    }

    public static LeBoxDynamicHttpBaseParameter getTaskAddParameter(String vid, String pid, String ext, String tag) {
        return new 2(vid, pid, ext, tag);
    }
}
