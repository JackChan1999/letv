package com.letv.component.upgrade.core.service;

import android.content.Context;
import com.letv.component.upgrade.bean.DownloadInfo;
import com.letv.component.upgrade.bean.DownloadInfo.DownloadState;
import com.letv.component.upgrade.bean.PartInfo;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DBSaveManage;
import com.letv.component.upgrade.core.db.DownloadDBUtil;
import com.letv.component.upgrade.core.service.task.FileDownloader;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.component.utils.DebugLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DownloadManager {
    public static final int SILENT_INSTALL = 1;
    private static final String TAG = "DownloadManager";
    private static DownloadManager mInstance = null;
    public int downTaskNum;
    public Map<String, DownloadInfo> downloadMaps = new LinkedHashMap();
    public DBSaveManage finishAddToDb;
    private Context mContext;
    private DownloadDBUtil mDbUtil;
    public DBSaveManage startAddToDb;

    private DownloadManager(Context context) {
        this.mContext = context;
        this.mDbUtil = new DownloadDBUtil(context);
        initDownloadMap();
    }

    public static synchronized DownloadManager getInstance(Context context) {
        DownloadManager downloadManager;
        synchronized (DownloadManager.class) {
            if (mInstance == null) {
                mInstance = new DownloadManager(context);
            }
            downloadManager = mInstance;
        }
        return downloadManager;
    }

    public List<DownloadInfo> getAllDownloadInfo() {
        return this.mDbUtil.getAllDownloadInfo();
    }

    public List<DownloadInfo> getAllFinishedDownloadInfo() {
        return this.mDbUtil.getAllFinishedDownloadInfo();
    }

    public List<DownloadInfo> getAllUnFinishedDownloadInfo() {
        return this.mDbUtil.getAllUnFinishedDownloadInfo();
    }

    private synchronized void initDownloadMap() {
        List<DownloadInfo> list = getAllDownloadInfo();
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (DownloadInfo info : list) {
                DebugLog.log(TAG, "initDownloadMap:" + info.fileName + "--" + info.state.toString());
                sb.append("initDownloadMap:" + info.fileName + "--" + info.state.toString()).append("\n");
                info.listener = newListener();
                this.downloadMaps.put(info.id, info);
            }
        }
    }

    public synchronized void refresh() {
        if (getDownloadingNum() == 0) {
            for (DownloadInfo info : this.downloadMaps.values()) {
                if (info.state == DownloadState.ERROR || info.state == DownloadState.STOPPED) {
                    resumeDownload(info.id);
                }
            }
        }
    }

    public void addDownload(String id, String url, String dir, String name, int threads, boolean isCurrentApp, int installType) {
        DownloadInfo info = new DownloadInfo(id, url, name, dir, Integer.valueOf(threads), installType);
        if (this.downloadMaps.containsKey(id)) {
            DebugLog.log(TAG, "has in dl queue");
            return;
        }
        DebugLog.log(TAG, "put  in dl queue");
        synchronized (this.downloadMaps) {
            if (this.startAddToDb == DBSaveManage.START_ADD_TO_DB) {
                this.mDbUtil.insertDownload(info);
            }
            this.downloadMaps.put(id, info);
        }
        ArrayList<DownloadInfo> arrayList = getDownloadingTask();
        int downloadingNum = arrayList.size();
        DebugLog.log(TAG, new StringBuilder(String.valueOf(Thread.currentThread().getName())).append("线程").toString());
        DebugLog.log(TAG, "downloadingNum: " + downloadingNum);
        if (downloadingNum < (this.downTaskNum == 0 ? 1 : this.downTaskNum)) {
            info.listener = newListener();
            info.downloader = new FileDownloader(info, this.mContext);
            info.downloader.execute(new Void[0]);
            info.state = DownloadState.STARTED;
        } else if (isCurrentApp) {
            info.listener = newListener();
            info.downloader = new FileDownloader(info, this.mContext);
            info.downloader.execute(new Void[0]);
            info.state = DownloadState.STARTED;
            DownloadInfo downloadInfo = (DownloadInfo) arrayList.get(0);
            pauseDownload(downloadInfo.url);
            resumeDownload(downloadInfo.url);
        } else {
            info.state = DownloadState.TOSTART;
            NotifyManage.notifyAdd(this.mContext, info);
        }
    }

    public synchronized void pauseDownload(String id) {
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info != null) {
            info.state = DownloadState.STOPPED;
            NotifyManage.notifyPause(this.mContext, info);
            if (info.downloader != null) {
                info.downloader.doCancelled(true);
            } else if (!this.mDbUtil.updateDownload(info)) {
                this.mDbUtil.insertDownload(info);
            }
            DebugLog.log(TAG, "pauseDownload in manager");
        }
    }

    public void pauseAll(boolean isError) {
        if (isError) {
            errorPauseAll();
        } else {
            pauseAll();
        }
    }

    public synchronized void errorPauseAll() {
        StringBuilder sb = new StringBuilder();
        for (Object obj : this.downloadMaps.keySet()) {
            DownloadInfo info = (DownloadInfo) this.downloadMaps.get(obj);
            if (!(info == null || info.state == DownloadState.FINISHED)) {
                DebugLog.log(TAG, "errorPauseAll:" + info.id + "--" + info.fileName + "--" + info.state.toString());
                sb.append("errorPauseAll:" + info.id + "--" + info.fileName + "--" + info.state.toString()).append("\n");
                if (info.downloader == null || info.state != DownloadState.STARTED) {
                    info.state = DownloadState.ERROR;
                    if (!this.mDbUtil.updateDownload(info)) {
                        this.mDbUtil.insertDownload(info);
                    }
                    NotifyManage.notifyPause(this.mContext, info);
                } else {
                    info.downloader.errorCancel();
                }
            }
        }
        DebugLog.log(TAG, sb.toString() + "ErrorPauseAll--" + System.currentTimeMillis());
    }

    public synchronized void pauseAll() {
        for (Object obj : this.downloadMaps.keySet()) {
            DownloadInfo info = (DownloadInfo) this.downloadMaps.get(obj);
            if (!(info == null || info.state == DownloadState.FINISHED)) {
                if (info.downloader != null) {
                    info.state = DownloadState.STOPPED;
                    info.downloader.doCancelled(false);
                } else {
                    info.state = DownloadState.STOPPED;
                    if (!this.mDbUtil.updateDownload(info)) {
                        this.mDbUtil.insertDownload(info);
                    }
                }
                NotifyManage.notifyPause(this.mContext, info);
            }
        }
    }

    public synchronized void startAll() {
        Iterator<String> i = this.downloadMaps.keySet().iterator();
        while (i.hasNext()) {
            DownloadInfo downloadInfo = (DownloadInfo) this.downloadMaps.get(i.next());
            if (!(downloadInfo == null || downloadInfo.state == DownloadState.FINISHED)) {
                resumeDownload((String) i.next());
            }
        }
    }

    public synchronized void resumeDownload(String id) {
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        DebugLog.log(TAG, "---------------------->resumeDownload------------");
        if (info == null) {
            DebugLog.log(TAG, "---------------------->info == null-------------");
        } else if (info.state != DownloadState.STARTED) {
            int downloadingNum = getDownloadingNum();
            DebugLog.log(TAG, "---------------------->downloadingNum =" + downloadingNum);
            if (downloadingNum < this.downTaskNum) {
                if (!(info.downloader == null || info.downloader.isCancelled())) {
                    info.downloader.doCancelled(false);
                }
                info.state = DownloadState.STARTED;
                info.downloader = new FileDownloader(info, this.mContext);
                info.downloader.execute(new Void[0]);
                NotifyManage.notifyStart(this.mContext, info);
            } else {
                info.state = DownloadState.TOSTART;
                NotifyManage.notifyPending(this.mContext, info);
            }
        } else {
            DebugLog.log(TAG, "--------DownloadState.STARTED--------");
            NotifyManage.notifyProgress(this.mContext, info);
        }
    }

    public synchronized PartInfo newDownloadPart(DownloadInfo info, long firstByte, long lastByte) {
        PartInfo part;
        part = new PartInfo(firstByte, lastByte);
        synchronized (this.downloadMaps) {
            part.info = info;
            if (this.startAddToDb == DBSaveManage.START_ADD_TO_DB) {
                this.mDbUtil.insertPart(info, part);
            }
        }
        return part;
    }

    public synchronized void startPendingDownload() {
        DebugLog.log(TAG, "startPendingDownload");
        DownloadInfo pending = getaPendingDownload();
        if (pending != null) {
            pending.listener = newListener();
            if (pending.downloader != null) {
                pending.downloader.doCancelled(false);
            }
            pending.downloader = new FileDownloader(pending, this.mContext);
            pending.downloader.execute(new Void[0]);
            pending.state = DownloadState.STARTED;
            NotifyManage.notifyStart(this.mContext, pending);
        }
    }

    public synchronized void saveDownloads() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (!this.mDbUtil.updateDownload(info)) {
                this.mDbUtil.insertDownload(info);
            } else if (info.parts != null) {
                for (PartInfo part : info.parts) {
                    if (!this.mDbUtil.updatePart(part, info)) {
                        this.mDbUtil.insertPart(info, part);
                    }
                }
            }
        }
    }

    public synchronized boolean isDownloading(String id) {
        boolean z;
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info == null || info.state != DownloadState.STARTED) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public synchronized void saveDownloadInfo(DownloadInfo info) {
        DebugLog.log(TAG, "保存下载任务");
        if (!this.mDbUtil.updateDownload(info)) {
            DebugLog.log(TAG, "insertDownload");
            this.mDbUtil.insertDownload(info);
            DebugLog.log(TAG, "<saveDownloadInfo> updateDownload fail:" + info.fileName);
        } else if (info.parts != null) {
            for (PartInfo part : info.parts) {
                if (!(part == null || this.mDbUtil.updatePart(part, info))) {
                    this.mDbUtil.insertPart(info, part);
                }
            }
        } else {
            DebugLog.log(TAG, "<saveDownloadInfo> updatePart fail:info.parts==null " + info.fileName);
        }
    }

    public void completeDownloadInfo(String id) {
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        synchronized (this.downloadMaps) {
            this.downloadMaps.remove(id);
        }
        NotifyManage.notifyFinish(this.mContext, info);
        startPendingDownload();
    }

    public DownloadListener newListener() {
        return new 1(this);
    }

    public synchronized int getDownloadingNum() {
        int num;
        num = 0;
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadState.STARTED) {
                num++;
            }
        }
        return num;
    }

    public synchronized ArrayList<DownloadInfo> getDownloadingTask() {
        ArrayList<DownloadInfo> list;
        list = new ArrayList();
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadState.STARTED) {
                list.add(info);
            }
        }
        return list;
    }

    public synchronized int getErrorDownloadNum() {
        int num;
        num = 0;
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadState.ERROR || info.state == DownloadState.STARTED) {
                num++;
            }
        }
        return num;
    }

    private DownloadInfo getErrorDownloadInfo() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadState.ERROR) {
                return info;
            }
        }
        return null;
    }

    private synchronized DownloadInfo getaPendingDownload() {
        DownloadInfo downloadInfo;
        if (getDownloadingNum() >= this.downTaskNum) {
            downloadInfo = null;
        } else {
            for (DownloadInfo downloadInfo2 : this.downloadMaps.values()) {
                if (downloadInfo2.state == DownloadState.TOSTART) {
                    break;
                }
            }
            downloadInfo2 = null;
        }
        return downloadInfo2;
    }

    public DownloadInfo getDownloadInfo(String id) {
        return (DownloadInfo) this.downloadMaps.get(id);
    }

    public boolean isDownloadExist(String id) {
        return this.mDbUtil.isExistById(id);
    }

    public int getUnFinishNum() {
        return this.mDbUtil.fetchAllUnFinishDownloadNum();
    }

    public void removeDownload(String id) {
        DebugLog.log(TAG, "removeDonwload");
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info != null) {
            if (info.downloader != null) {
                if (info.state == DownloadState.STARTED) {
                    info.downloader.doCancelled(true);
                } else {
                    info.downloader.doCancelled(false);
                }
            }
            LetvUtil.deleteFile(new File(info.fileDir, info.fileName));
        }
        this.mDbUtil.deleteDownloadById(id);
        this.downloadMaps.remove(id);
    }

    public synchronized void removeAllDownload() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.downloader != null) {
                info.downloader.doCancelled(false);
            }
            LetvUtil.deleteFile(new File(info.fileDir, info.fileName));
        }
        this.downloadMaps.clear();
        this.mDbUtil.deleteAll();
    }

    public DownloadState getCurrentState(String id) {
        return ((DownloadInfo) this.downloadMaps.get(id)).state;
    }
}
