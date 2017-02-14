package com.letv.component.upgrade.core.service;

import android.content.Context;
import com.letv.component.upgrade.bean.DownloadInfo;
import com.letv.component.upgrade.bean.DownloadInfo.DownloadState;
import com.letv.component.upgrade.core.AppDownloadConfiguration;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DBSaveManage;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DataCallbackCategory;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DownloadServiceManage;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DownloadStateManage;
import com.letv.component.utils.DebugLog;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DownLoadFunction {
    private static final String TAG = "DownLoadFunction";
    private static DownLoadFunction mInstance;
    public DataCallbackCategory callbackCategory;
    private AppDownloadConfiguration config;
    public int downLoadTaskCount;
    public int downLoadThreadCount;
    private DownloadServiceManage downloadServiceType;
    private DownloadStateManage downloadState;
    public String fileDir;
    public DBSaveManage finishAddTaskToDB;
    public boolean isBindSuccess;
    private Context mContext;
    public DBSaveManage startAddTaskToDB;

    private DownLoadFunction(Context context) {
        this.mContext = context;
    }

    public static synchronized DownLoadFunction getInstance(Context context) {
        DownLoadFunction downLoadFunction;
        synchronized (DownLoadFunction.class) {
            if (mInstance == null) {
                mInstance = new DownLoadFunction(context);
            }
            downLoadFunction = mInstance;
        }
        return downLoadFunction;
    }

    public void initDownLoadConfig(AppDownloadConfiguration configuration) {
        this.config = configuration;
        initParams();
    }

    public AppDownloadConfiguration getDownLoadConfig() {
        return this.config;
    }

    public void initParams() {
        this.downLoadTaskCount = this.config.downloadTaskNum;
        this.downLoadThreadCount = this.config.downloadTaskThreadNum;
        this.fileDir = this.config.downloadLocation;
        this.downloadServiceType = this.config.downloadServiceType;
        this.downloadState = this.config.downloadState;
        this.callbackCategory = this.config.callbackCategory;
        this.finishAddTaskToDB = this.config.addFinishTaskToDB;
        this.startAddTaskToDB = this.config.addStartTaskToDB;
    }

    public void pauseAll(boolean isError, boolean isManualOrNetNull) {
        DebugLog.log("downloadFunction", toString());
        DebugLog.log("downloadFunction", this.downloadServiceType.toString());
        System.out.println("downloadFunction" + toString());
        if (this.downloadServiceType != DownloadServiceManage.LOCALSERVICE) {
            return;
        }
        if (isError) {
            DownloadManager.getInstance(this.mContext).errorPauseAll();
        } else {
            DownloadManager.getInstance(this.mContext).pauseAll();
        }
    }

    public void startAll() {
        DownloadService.startAllDownload(this.mContext);
    }

    public void startDownload(Context mContext, String url, String name, boolean isCurrentApp, int installType) {
        if (url != null && !"".equalsIgnoreCase(url.trim()) && name != null && !"".equalsIgnoreCase(name.trim())) {
            DownloadService.addDownload(mContext, url, url, this.fileDir, name, this.downLoadThreadCount, isCurrentApp, installType);
        }
    }

    public void remove(String id) {
        DownloadService.removeDownload(this.mContext, id);
    }

    public void startPending() {
        DownloadService.pendingDownload(this.mContext);
    }

    public void removeAll() {
        DownloadService.removeAllDownload(this.mContext);
    }

    public void cancelDownload(String url) {
        DownloadService.pauseDowload(this.mContext, url);
    }

    public void refreshDownloads() {
        DownloadService.refreshDownloads(this.mContext);
    }

    public void resumeDownload(String url) {
        DownloadService.resumeDownload(this.mContext, url);
    }

    public int getDownloadStatus(String url) {
        DownloadInfo mDownloadInfo = DownloadManager.getInstance(this.mContext).getDownloadInfo(url);
        if (mDownloadInfo != null) {
            return mDownloadInfo.state.toInt();
        }
        return DownloadState.STOPPED.toInt();
    }

    public int getDownloadNumber() {
        return DownloadManager.getInstance(this.mContext).getDownloadingNum();
    }

    public DownloadInfo getDownloadInfoById(String url) {
        return (DownloadInfo) DownloadManager.getInstance(this.mContext).downloadMaps.get(url);
    }

    public Map<String, DownloadInfo> getDownloadTask() {
        Map<String, DownloadInfo> downloadMaps = new LinkedHashMap();
        DebugLog.log(TAG, "localservice");
        return DownloadManager.getInstance(this.mContext).downloadMaps;
    }

    public Map<String, DownloadInfo> initDownloadTask() {
        return DownloadManager.getInstance(this.mContext).downloadMaps;
    }

    public List<DownloadInfo> getFinishedDownloadTask() {
        return DownloadManager.getInstance(this.mContext).getAllFinishedDownloadInfo();
    }

    public List<DownloadInfo> getUnFinishedDownloadTask() {
        return DownloadManager.getInstance(this.mContext).getAllUnFinishedDownloadInfo();
    }

    public void registerListener(DownloadListener listener) {
        NotifyManage.registerLocalListener(listener);
    }

    public void unRegistCallback(DownloadListener listener) {
        NotifyManage.unRegisterLocalListener(listener);
    }
}
