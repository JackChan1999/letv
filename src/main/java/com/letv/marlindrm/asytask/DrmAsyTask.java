package com.letv.marlindrm.asytask;

import android.os.Handler;
import android.os.Message;
import com.letv.core.utils.LogInfo;
import com.letv.marlindrm.bean.DrmResultBean;
import com.letv.marlindrm.intf.DrmDealCallBackInf;

public abstract class DrmAsyTask {
    protected String DRM_DRI_NAME = "wasabi";
    private final int MSG_CALL_POST_EXECUTE = 1;
    private String TAG = "drmTest";
    public DrmDealCallBackInf mCallBack;
    private Thread mDrmThread;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (DrmAsyTask.this.mIsCanRun) {
                        DrmResultBean bean = msg.obj;
                        DrmAsyTask.this.onPostExecute(bean.getResultCode(), bean);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    public boolean mIsCanRun = true;

    public abstract DrmResultBean doInBackground();

    public void stop() {
        LogInfo.log(this.TAG, "drmAsyTask stop");
        this.mIsCanRun = false;
        if (this.mDrmThread != null) {
            try {
                LogInfo.log(this.TAG, "DrmManager_stopDrmThread_stopThread_hashCode:" + this.mDrmThread.hashCode());
                this.mDrmThread.interrupt();
                this.mDrmThread.stop();
            } catch (Exception e) {
                e.printStackTrace();
                LogInfo.log(this.TAG, "stop exception....");
            }
        }
    }

    public DrmAsyTask(DrmDealCallBackInf callBack) {
        this.mCallBack = callBack;
    }

    public void onPostExecute(int status, DrmResultBean bean) {
        if (this.mCallBack != null && this.mIsCanRun) {
            this.mCallBack.onDrmCallBack(status, bean);
        }
    }

    public void execute() {
        if (this.mIsCanRun) {
            this.mDrmThread = new Thread() {
                public void run() {
                    super.run();
                    if (DrmAsyTask.this.mIsCanRun) {
                        DrmResultBean result = DrmAsyTask.this.doInBackground();
                        if (result != null) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = result;
                            DrmAsyTask.this.mHandler.sendMessage(msg);
                        }
                    }
                }
            };
            LogInfo.log(this.TAG, "DrmManager_initTaskByVideoType_newThread_hashcode:" + this.mDrmThread.hashCode());
            this.mDrmThread.start();
        }
    }
}
