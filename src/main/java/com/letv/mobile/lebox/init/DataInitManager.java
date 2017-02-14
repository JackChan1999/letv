package com.letv.mobile.lebox.init;

import android.text.TextUtils;
import com.letv.mobile.lebox.config.LeBoxAppConfig;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.utils.Logger;
import java.util.HashSet;
import java.util.Set;

public class DataInitManager {
    private static final String TAG = DataInitManager.class.getSimpleName();
    private static DataInitManager mDataInitManager;
    private final Set<String> mCompletedTaskSet = new HashSet();
    private final InitQueue mInitQueue = new InitQueue();

    public void SetTaskCompleted(Class c) {
        this.mCompletedTaskSet.add(c.getName());
    }

    public boolean isTaskCompleted(Class c) {
        return this.mCompletedTaskSet.contains(c.getName());
    }

    public void clearTaskRecord() {
        this.mCompletedTaskSet.clear();
    }

    private DataInitManager() {
    }

    public static DataInitManager getInstance() {
        if (mDataInitManager != null) {
            return mDataInitManager;
        }
        synchronized (DataInitManager.class) {
            mDataInitManager = new DataInitManager();
        }
        return mDataInitManager;
    }

    public void startInit() {
        Logger.d(TAG, "-----startInit-- InitQueue  isHasWork :" + this.mInitQueue.isHasWork());
        if (!this.mInitQueue.isHasWork()) {
            addTask();
        }
        this.mInitQueue.startInit();
    }

    private synchronized void addTask() {
        if (HttpCacheAssistant.getInstanced().isIdentityDeviceChange()) {
            this.mInitQueue.cleanQueue();
            clearTaskRecord();
        }
        if (TextUtils.isEmpty(LeBoxAppConfig.getDynamicDomain())) {
            Logger.e(TAG, "--init data---addTask---error because domain is empty");
        } else {
            if (!(isTaskCompleted(InitUserPermissionTask.class) || HttpCacheAssistant.getInstanced().isLogin())) {
                Logger.d(TAG, "---------addTask----InitUserPermissionTask----");
                this.mInitQueue.add(new InitUserPermissionTask());
            }
            if (!isTaskCompleted(InitVideoTask.class) && HttpCacheAssistant.getInstanced().isCompleteTaskEmpty() && HttpCacheAssistant.getInstanced().isUnFinishTaskEmpty()) {
                Logger.d(TAG, "---------addTask----InitVideoTask----");
                this.mInitQueue.add(new InitVideoTask());
            }
            if (!isTaskCompleted(InitVideoVersionTask.class) && TextUtils.isEmpty(HttpCacheAssistant.getInstanced().getCompleteVersion())) {
                Logger.d(TAG, "---------addTask----InitVideoVersionTask----");
                this.mInitQueue.add(new InitVideoVersionTask());
            }
            if (!isTaskCompleted(InitFollowTask.class) && HttpCacheAssistant.getInstanced().isFollowEmpty()) {
                Logger.d(TAG, "---------addTask----InitFollowTask----");
                this.mInitQueue.add(new InitFollowTask());
            }
            if (!isTaskCompleted(InitCheckVideoInfoTask.class)) {
                Logger.d(TAG, "---------addTask----InitCheckVideoInfoTask----");
                this.mInitQueue.add(new InitCheckVideoInfoTask());
            }
            if (!isTaskCompleted(InitCheckFollowInfoTask.class)) {
                Logger.d(TAG, "---------addTask----InitCheckFollowInfoTask----");
                this.mInitQueue.add(new InitCheckFollowInfoTask());
            }
        }
    }
}
