package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;

public class TaskUpdateHttpRequest {
    public static final String ALBUMCOVER = "albumCover";
    public static final String ALBUMTITLE = "albumTitle";
    public static final String COVER = "cover";
    public static final String ISEND = "isEnd";
    public static final String ISWATCH = "isWatch";
    protected static final String PARAM_TAG = "tag";
    static final String TAG = "TaskUpdateHttpRequest";
    public static final String TITLE = "title";
    public static final String VID = "vid";

    public static LeBoxHttpDynamicRequest<LetvHttpBaseModel> getUpdateRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getTaskUpdateParameter(String vid, String title, String cover, String albumTitle, String albumCover, String isEnd, String isWatch) {
        return new 2(vid, title, cover, albumTitle, albumCover, isEnd, isWatch);
    }
}
