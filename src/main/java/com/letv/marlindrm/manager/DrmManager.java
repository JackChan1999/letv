package com.letv.marlindrm.manager;

import android.content.Context;
import android.util.Log;
import com.letv.core.BaseApplication;
import com.letv.marlindrm.asytask.DrmAsyTask;
import com.letv.marlindrm.asytask.DrmSoftAsyTask;
import com.letv.marlindrm.constants.ContentTypeEnum;
import com.letv.marlindrm.constants.DrmVideoTypeContants;
import com.letv.marlindrm.http.DrmCommenUtils;
import com.letv.marlindrm.intf.DrmDealCallBackInf;

public class DrmManager {
    private static DrmManager sDrmManager;
    private String TAG = "drmTest";
    private DrmDealCallBackInf mCallBack;
    private ContentTypeEnum mContentType = ContentTypeEnum.DASH;
    private Context mContext;
    private DrmAsyTask mDrmAsyTask;
    private String mDrmXmlUrl;
    private String mOriginalPlayUrl;
    private String mVideoType = DrmVideoTypeContants.VIDEO_TYPE_DASH;

    public static DrmManager getInstance() {
        Log.i("DRMTest", "getInstance()");
        if (sDrmManager == null) {
            sDrmManager = new DrmManager(BaseApplication.getInstance());
        }
        return sDrmManager;
    }

    private DrmManager(Context context) {
        this.mContext = context;
    }

    public void startDrm(String originalPlayUrl, String drmXmlUrl, String videoType, DrmDealCallBackInf callBack) {
        Log.i("DRMTest", "startDrm: mailUrl: " + originalPlayUrl + " drmXmlUrl: " + drmXmlUrl);
        Log.i("DRMTest", "startDrm: videoType: " + videoType);
        Log.i("DRMTest", "startDrm: playUrl: " + this.mOriginalPlayUrl);
        this.mOriginalPlayUrl = originalPlayUrl;
        this.mVideoType = videoType;
        this.mDrmXmlUrl = drmXmlUrl;
        this.mCallBack = callBack;
        this.mContentType = DrmCommenUtils.getContentTypeByVideoType(this.mVideoType);
        Log.i("DRMTest", "startDrm: mContentType: " + this.mContentType);
        initTaskByVideoType();
        this.mDrmAsyTask.execute();
    }

    public void stopDrmThread() {
        Log.i("DRMTest", "stopDrmThread()");
        if (this.mDrmAsyTask != null) {
            this.mDrmAsyTask.stop();
        }
    }

    private void initTaskByVideoType() {
        this.mDrmAsyTask = new DrmSoftAsyTask(this.mContext, this.mOriginalPlayUrl, this.mDrmXmlUrl, this.mContentType, this.mCallBack);
    }
}
