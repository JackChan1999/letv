package com.letv.android.client.worldcup.download;

import android.content.Context;
import android.text.format.Formatter;
import com.letv.android.client.worldcup.bean.DownloadStatus;
import com.letv.android.client.worldcup.db.DownloadTraceHandler;
import com.letv.android.client.worldcup.util.Constants;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class WorldCupDownloadManager {
    private static WorldCupDownloadManager mInstance = null;
    public Map<String, DownloadInfo> downloadMaps = new LinkedHashMap();
    private DownloadTraceHandler downloadTraceHandler;
    private Context mContext;
    private String speed = "";

    private WorldCupDownloadManager(Context context) {
        this.mContext = context;
        this.downloadTraceHandler = new DownloadTraceHandler(context.getContentResolver());
        initDownloadMap();
    }

    public static synchronized WorldCupDownloadManager getInstance(Context context) {
        WorldCupDownloadManager worldCupDownloadManager;
        synchronized (WorldCupDownloadManager.class) {
            if (mInstance == null) {
                mInstance = new WorldCupDownloadManager(context);
            }
            worldCupDownloadManager = mInstance;
        }
        return worldCupDownloadManager;
    }

    private synchronized void initDownloadMap() {
        List<DownloadInfo> list = this.downloadTraceHandler.getAllDownloadInfo();
        Constants.debug("initDownloadMap:" + list.size());
        boolean hasStart = false;
        StringBuilder sb = new StringBuilder();
        for (DownloadInfo info : list) {
            Constants.debug("initDownloadMap:" + info.fileName + "--" + info.state.toString());
            sb.append("initDownloadMap:" + info.fileName + "--" + info.state.toString()).append("\n");
            if (info.state != DownloadStatus.FINISHED) {
                info.listener = newListener();
                this.downloadMaps.put(info.id, info);
                if (info.state == DownloadStatus.STARTED || info.state == DownloadStatus.ERROR) {
                    if (!hasStart) {
                        hasStart = true;
                        if (info.downloader == null) {
                            info.downloader = new FileDownloader(info, this.mContext);
                        }
                        info.downloader.execute(new Void[0]);
                    } else if (info.downloader != null) {
                        Constants.debug("cancel another started download");
                        info.downloader.doCancelled(false);
                    }
                }
            }
        }
        Constants.debug(sb.toString(), "InitDownloadMap--" + System.currentTimeMillis());
        if (!hasStart) {
            for (DownloadInfo info2 : list) {
                if (info2 != null && info2.state == DownloadStatus.TOSTART) {
                    info2.downloader = new FileDownloader(info2, this.mContext);
                    info2.downloader.execute(new Void[0]);
                    break;
                }
            }
        }
    }

    public synchronized void refresh() {
        if (getDownloadingNum() == 0) {
            for (DownloadInfo info : this.downloadMaps.values()) {
                if (info.state == DownloadStatus.ERROR || info.state == DownloadStatus.STOPPED) {
                    resumeDownload(info.id);
                }
            }
        }
    }

    public void addDownloadLetv(String episodeId, String dir, String name, String mmsid, String isHd, String pcode, String version) {
        Constants.debug("=============addDownloadLetv---episodeId:" + episodeId + "---dir:" + dir + "---name:" + name + "---mmsid:" + mmsid + "---isHd:" + isHd + "----pcode:" + pcode + "---version:" + version);
        DownloadInfo info = new DownloadInfo(episodeId, episodeId, name, dir, Integer.valueOf(Constants.DOWNLOAD_JOB_THREAD_LIMIT), mmsid, isHd, pcode, version);
        synchronized (this.downloadMaps) {
            this.downloadTraceHandler.insertDownload(info);
            this.downloadMaps.put(episodeId, info);
        }
        Constants.debug(info.id + "--" + info.isHd);
        info.state = DownloadStatus.TOSTART;
        NotifyManage.notifyAdd(this.mContext, info);
        int downloadingNum = getDownloadingNum();
        Constants.debug("downloadingNum: " + downloadingNum);
        if (downloadingNum < Constants.DOWNLOAD_JOB_NUM_LIMIT) {
            info.listener = newListener();
            info.downloader = new FileDownloader(info, this.mContext);
            info.downloader.execute(new Void[0]);
            info.state = DownloadStatus.STARTED;
        }
    }

    public synchronized void pauseDownload(String id) {
        Constants.debug("pauseDownload in manager");
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info != null) {
            info.state = DownloadStatus.STOPPED;
            calculateDownloadSpeed(info.lastStarted, System.currentTimeMillis(), info.downloaded - info.lastDownloaded);
            NotifyManage.notifyPauseByUser(this.mContext, info);
            if (info.downloader != null) {
                info.downloader.doCancelled(true);
                startPendingDownload();
            } else {
                this.downloadTraceHandler.updateDownload(info);
            }
        }
    }

    public synchronized void errorPauseAll() {
        StringBuilder sb = new StringBuilder();
        for (Object obj : this.downloadMaps.keySet()) {
            DownloadInfo info = (DownloadInfo) this.downloadMaps.get(obj);
            if (info != null) {
                Constants.debug("errorPauseAll:" + info.id + "--" + info.fileName + "--" + info.state.toString());
                sb.append("errorPauseAll:" + info.id + "--" + info.fileName + "--" + info.state.toString()).append("\n");
                if (info.downloader != null && info.state == DownloadStatus.STARTED) {
                    info.downloader.errorCancel();
                }
            }
        }
        Constants.debug(sb.toString(), "ErrorPauseAll--" + System.currentTimeMillis());
    }

    public synchronized void pauseAll() {
        for (Object obj : this.downloadMaps.keySet()) {
            DownloadInfo info = (DownloadInfo) this.downloadMaps.get(obj);
            if (info != null) {
                if (info.downloader != null) {
                    info.state = DownloadStatus.STOPPED;
                    info.downloader.doCancelled(false);
                } else {
                    info.state = DownloadStatus.STOPPED;
                    this.downloadTraceHandler.updateDownload(info);
                }
                NotifyManage.notifyPause(this.mContext, info);
            }
        }
    }

    public synchronized void startAll() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadStatus.ERROR || info.state == DownloadStatus.STOPPED) {
                resumeDownload(info.id);
            }
        }
    }

    public synchronized void resumeDownload(String id) {
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info != null) {
            if (info.state != DownloadStatus.STARTED) {
                int downloadingNum = getDownloadingNum();
                Constants.debug("---------------------->downloadingNum =" + downloadingNum);
                if (downloadingNum < Constants.DOWNLOAD_JOB_NUM_LIMIT) {
                    if (!(info.downloader == null || info.downloader.isCancelled())) {
                        info.downloader.doCancelled(false);
                    }
                    info.state = DownloadStatus.STARTED;
                    info.downloader = new FileDownloader(info, this.mContext);
                    info.downloader.execute(new Void[0]);
                    NotifyManage.notifyStart(this.mContext, info);
                } else {
                    info.state = DownloadStatus.TOSTART;
                    this.downloadTraceHandler.updateDownload(info);
                    NotifyManage.notifyPending(this.mContext, info);
                }
            } else {
                this.downloadTraceHandler.updateDownload(info);
                NotifyManage.notifyProgress(this.mContext, info);
            }
        }
    }

    public synchronized PartInfo newDownloadPart(DownloadInfo info, long firstByte, long lastByte) {
        PartInfo part;
        part = new PartInfo(firstByte, lastByte);
        synchronized (this.downloadMaps) {
            part.info = info;
            this.downloadTraceHandler.insertPart(info, part);
        }
        return part;
    }

    public synchronized void startPendingDownload() {
        Constants.debug("startPendingDownload");
        DownloadInfo pending = getaPendingDownload();
        if (pending != null) {
            pending.listener = newListener();
            if (pending.downloader != null) {
                pending.downloader.doCancelled(false);
            }
            pending.downloader = new FileDownloader(pending, this.mContext);
            pending.downloader.execute(new Void[0]);
            pending.state = DownloadStatus.STARTED;
        }
    }

    public synchronized void saveDownloads() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (!this.downloadTraceHandler.updateDownload(info)) {
                this.downloadTraceHandler.insertDownload(info);
            } else if (info.parts != null) {
                for (PartInfo part : info.parts) {
                    this.downloadTraceHandler.updatePart(part, info);
                }
            }
        }
    }

    public synchronized boolean isDownloading(String id) {
        boolean z;
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info == null || info.state != DownloadStatus.STARTED) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public void saveDownloadInfo(DownloadInfo info) {
        if (!this.downloadTraceHandler.updateDownload(info)) {
            Constants.debug("<saveDownloadInfo> updateDownload fail:" + info.fileName);
        } else if (info.parts != null) {
            for (PartInfo part : info.parts) {
                if (part != null) {
                    this.downloadTraceHandler.updatePart(part, info);
                }
            }
        } else {
            Constants.debug("<saveDownloadInfo> updatePart fail:info.parts==null " + info.fileName);
        }
    }

    public void completeDownloadInfo(String id) {
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        Constants.debug("finishDownload:" + info.id + SearchCriteria.LT + info.isHd + ">:" + Formatter.formatFileSize(this.mContext, info.total));
        synchronized (this.downloadMaps) {
            this.downloadMaps.remove(id);
        }
        NotifyManage.notifyFinish(this.mContext, info);
        startPendingDownload();
    }

    public DownloadListener newListener() {
        return new 1(this);
    }

    public void calculateDownloadSpeed(long lastStarted, long curtime, long downloadedSize) {
        long time = (curtime - lastStarted) / 1000;
        if (time > 0) {
            this.speed = new StringBuilder(String.valueOf(Formatter.formatFileSize(this.mContext, downloadedSize / time))).append("/s").toString();
            return;
        }
        this.speed = "0.00 B/s";
    }

    public String getSpeed() {
        return this.speed;
    }

    public synchronized int getDownloadingNum() {
        int num;
        num = 0;
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadStatus.STARTED) {
                num++;
            }
        }
        return num;
    }

    public synchronized int getErrorDownloadNum() {
        int num;
        num = 0;
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadStatus.ERROR || info.state == DownloadStatus.STARTED) {
                num++;
            }
        }
        return num;
    }

    private DownloadInfo getErrorDownloadInfo() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.state == DownloadStatus.ERROR) {
                return info;
            }
        }
        return null;
    }

    private synchronized DownloadInfo getaPendingDownload() {
        DownloadInfo downloadInfo;
        if (getDownloadingNum() >= Constants.DOWNLOAD_JOB_NUM_LIMIT) {
            downloadInfo = null;
        } else {
            Iterator<DownloadInfo> ite = this.downloadMaps.values().iterator();
            synchronized (this.downloadMaps) {
                do {
                    if (!ite.hasNext()) {
                        downloadInfo = null;
                        break;
                    }
                    downloadInfo = (DownloadInfo) ite.next();
                    Constants.debug("in downloadMaps: " + downloadInfo.id + "--" + downloadInfo.isHd);
                } while (downloadInfo.state != DownloadStatus.TOSTART);
                Constants.debug("a pending download" + downloadInfo.id + "--" + downloadInfo.isHd);
            }
        }
        return downloadInfo;
    }

    public DownloadInfo getDownloadInfo(String id) {
        return (DownloadInfo) this.downloadMaps.get(id);
    }

    public boolean isDownloadExist(String id) {
        return this.downloadTraceHandler.isExistById(id);
    }

    public int getUnFinishNum() {
        return this.downloadTraceHandler.fetchAllUnFinishDownloadNum();
    }

    public void removeDownload(String id) {
        Constants.debug("removeDonwload");
        DownloadInfo info = (DownloadInfo) this.downloadMaps.get(id);
        if (info != null) {
            if (info.downloader != null) {
                if (info.state == DownloadStatus.STARTED) {
                    info.downloader.doCancelled(true);
                } else {
                    info.downloader.doCancelled(false);
                }
            }
            Constants.deleteFile(new File(info.fileDir, info.fileName));
        }
        this.downloadTraceHandler.deleteDownloadById(id);
        this.downloadMaps.remove(id);
    }

    public synchronized void removeAllDownload() {
        for (DownloadInfo info : this.downloadMaps.values()) {
            if (info.downloader != null) {
                info.downloader.doCancelled(false);
            }
            Constants.deleteFile(new File(info.fileDir, info.fileName));
        }
        this.downloadMaps.clear();
        this.downloadTraceHandler.deleteAll();
    }
}
