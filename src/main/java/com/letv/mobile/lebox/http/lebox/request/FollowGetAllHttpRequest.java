package com.letv.mobile.lebox.http.lebox.request;

import android.content.Context;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpDynamicRequest;
import com.letv.mobile.lebox.http.lebox.bean.FollowGetAllListBean;

public class FollowGetAllHttpRequest {
    protected static final String NEEDALBUMINFO = "needAlbumInfo";
    protected static final String NEEDEXT = "needExt";
    protected static final String NEEDINFO = "needInfo";
    protected static final String NEEDTAG = "needTag";
    public static final String NEED_ALBUMINFO = "1";
    public static final String NEED_EXT = "1";
    public static final String NEED_INFO = "1";
    public static final String NEED_TAG = "1";
    public static final String NO_ALBUMINFO = "0";
    public static final String NO_EXT = "0";
    public static final String NO_INFO = "0";
    public static final String NO_TAG = "0";
    protected static final String PID = "pid";
    static final String TAG = "FollowGetAllHttpRequest";
    protected static final String TYPE = "type";
    public static final String VIDEO_ALL = "0";
    public static final String VIDEO_COMPLETE = "1";
    public static final String VIDEO_UNFINISH = "2";

    public static LeBoxHttpDynamicRequest<FollowGetAllListBean> getGetAllRequest(Context context, TaskCallBack callback) {
        return new 1(context, callback);
    }

    public static LeBoxDynamicHttpBaseParameter getFollowGetAllParameter(String pid, String type, String needTag, String needInfo, String needAlbumInfo, String needExt) {
        return new 2(pid, type, needTag, needInfo, needAlbumInfo, needExt);
    }
}
