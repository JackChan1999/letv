package com.letv.download.service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.download.bean.DownloadUrl;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.bean.PartInfoBean;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.exception.LetvDownloadException;
import com.letv.download.exception.NetWorkErrorException;
import com.letv.download.exception.ServerErrorException;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.DownloadSubtitleManager;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.download.manager.VideoFileManager;
import com.letv.download.util.DownloadStatisticsUtil;
import com.letv.download.util.DownloadStatisticsUtil.DownloadPauseStatistics;
import com.letv.download.util.DownloadUtil;
import com.letv.download.util.L;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FileDownloader extends AsyncTask<Void, Void, DownloadVideo> {
    private static final String TAG = FileDownloader.class.getSimpleName();
    private static byte[] sLock = new byte[0];
    private static String speed;
    public static String userAgent = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1";
    private volatile boolean isCancelled = false;
    private volatile boolean isSpecialCancel = false;
    private Context mContext;
    private DownloadService mDownloadService;
    private DownloadVideo mDownloadVideo;
    private FilePartDownloader[] parts;
    private Thread[] threads;

    public static String getDownloadSpeed() {
        return speed;
    }

    public FileDownloader(DownloadVideo downloadVideo, Context context, DownloadService downloadService) {
        this.mDownloadVideo = downloadVideo;
        this.mContext = context;
        this.mDownloadService = downloadService;
    }

    protected DownloadVideo doInBackground(Void... params) {
        LogInfo.log("fornia", " mDownloadVideo.state:" + this.mDownloadVideo.state + " name : " + this.mDownloadVideo.name);
        Thread.currentThread().setName("asytask name " + this.mDownloadVideo.name);
        if (this.mDownloadVideo.state == 4) {
            LogInfo.log("fornia", "doInBackground last return mDownloadVideo.state == DownloadState.FINISHED_STATE:" + this.mDownloadVideo);
            return this.mDownloadVideo;
        } else if (TextUtils.isEmpty(this.mDownloadVideo.filePath) || !new File(this.mDownloadVideo.filePath).exists()) {
            this.mDownloadVideo.state = 8;
            return null;
        } else if (!NetworkUtils.isNetworkAvailable()) {
            this.mDownloadVideo.state = 6;
            return null;
        } else if (StoreDeviceInfo.getAvailableSpace(this.mDownloadVideo.filePath) < StoreManager.DEFUALT_DOWNLOAD_MINI_SIZE) {
            this.mDownloadVideo.state = 3;
            return null;
        } else {
            File file = new File(this.mDownloadVideo.filePath, VideoFileManager.createFileName(this.mDownloadVideo.vid));
            try {
                String realUrl = getRealUrl();
                if (realUrl == null) {
                    this.mDownloadVideo.state = 7;
                    return null;
                }
                int i;
                this.mDownloadVideo.downloadUrl = realUrl;
                if (this.mDownloadVideo.mParts == null) {
                    try {
                        this.mDownloadVideo.totalsize = getContentLength(this.mDownloadVideo.downloadUrl);
                        DownloadUtil.saveException(" FileDownloader doInBackground getContentLength : " + this.mDownloadVideo.totalsize + " vid = " + this.mDownloadVideo.vid + " isCancelled : " + this.isCancelled);
                        LogInfo.log("fornia", "mDownloadVideo.totalsize:" + this.mDownloadVideo.totalsize + " isCancelled : " + this.isCancelled);
                        if ((this.mDownloadVideo.totalsize == -1 || this.mDownloadVideo.totalsize == 0) && !this.isCancelled) {
                            LogInfo.log("fornia", "doInBackground last return mDownloadVideo.totalsize == -1 || mDownloadVideo.totalsize == 0:" + this.mDownloadVideo);
                            if (NetworkUtils.isNetworkAvailable() && this.mDownloadVideo.totalsize == -1) {
                                throw new ServerErrorException(" service totalsize == 0 !!!!!!!");
                            }
                            throw new NetWorkErrorException("network totalsize == 0 !!!!!!!");
                        }
                    } catch (LetvDownloadException e) {
                        e.reportFailed();
                        e.printException();
                        this.mDownloadVideo.state = e.getState();
                        LogInfo.log(TAG, ">> LetvDownloadException state : " + this.mDownloadVideo.state);
                        return null;
                    }
                }
                for (PartInfoBean partInfoBean : this.mDownloadVideo.mParts) {
                    partInfoBean.cancelled = false;
                }
                if (this.mDownloadVideo.state == 0 || this.mDownloadVideo.state == 1) {
                    this.mDownloadVideo.timestamp = System.currentTimeMillis();
                }
                if (this.isCancelled) {
                    LogInfo.log(TAG, " fileDownload doInBackground cancel ");
                    return this.mDownloadVideo;
                }
                synchronized (sLock) {
                    makeParts();
                }
                LogInfo.log("fornia", "doInBackground:" + this.mDownloadVideo.mParts.length + "|mDownloadVideo.state:" + this.mDownloadVideo.state);
                if (!this.isSpecialCancel) {
                    DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
                }
                try {
                    URL url = new URL(this.mDownloadVideo.downloadUrl);
                    if (this.isCancelled) {
                        LogInfo.log(TAG, " fileDownload doInBackground cancel ");
                        return this.mDownloadVideo;
                    }
                    DownloadUtil.saveException(" Download Thread start download  vid = " + this.mDownloadVideo.vid);
                    this.parts = new FilePartDownloader[this.mDownloadVideo.mParts.length];
                    this.threads = new Thread[this.mDownloadVideo.mParts.length];
                    for (i = 0; i < this.mDownloadVideo.mParts.length; i++) {
                        this.parts[i] = new FilePartDownloader(this.mDownloadVideo.mParts[i], this.mDownloadVideo, url, file);
                        this.threads[i] = new Thread(this.parts[i]);
                        this.threads[i].setName("thread name : " + this.mDownloadVideo.name + " id : " + i);
                        this.threads[i].setPriority(10);
                        this.threads[i].start();
                    }
                    long beforeTime = System.currentTimeMillis();
                    long lastDownloadSize = this.mDownloadVideo.downloaded;
                    DownloadStatisticsUtil downloadStatisticsUtil = new DownloadStatisticsUtil(beforeTime, lastDownloadSize);
                    while (this.mDownloadVideo.downloaded < this.mDownloadVideo.totalsize && this.mDownloadVideo.state != 4 && this.mDownloadVideo.state != 3 && !this.mDownloadVideo.isErrorState() && !this.isCancelled) {
                        downloadStatisticsUtil.statisticDownloadSpeed(this.mDownloadVideo);
                        if (System.currentTimeMillis() - beforeTime > SPConstant.DELAY_BUFFER_DURATION) {
                            long partInfoDownloaded = 0;
                            for (i = 0; i < this.mDownloadVideo.mParts.length; i++) {
                                partInfoDownloaded += this.parts[i].mPartInfo.downloaded;
                            }
                            if (partInfoDownloaded >= this.mDownloadVideo.totalsize) {
                                this.mDownloadVideo.downloaded = this.mDownloadVideo.totalsize;
                            }
                            LogInfo.log(TAG, " >>serverTotalSize : " + this.mDownloadVideo.serverTotalSize + " totalsize: " + this.mDownloadVideo.totalsize);
                            LogInfo.log(TAG, ">>><<downloaded : " + this.mDownloadVideo.downloaded + " partInfoDownloaded : " + partInfoDownloaded + " partInfoDownloaded >= totalsize : " + (partInfoDownloaded >= this.mDownloadVideo.totalsize) + " name : " + this.mDownloadVideo.name);
                            this.mDownloadVideo.speed = DownloadUtil.calculateDownloadSpeed(beforeTime, System.currentTimeMillis(), this.mDownloadVideo.downloaded - lastDownloadSize);
                            speed = this.mDownloadVideo.speed;
                            if (!this.isSpecialCancel) {
                                DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
                            }
                            beforeTime = System.currentTimeMillis();
                            lastDownloadSize = this.mDownloadVideo.downloaded;
                            handleStoreLack(this.mDownloadVideo.state, this.mDownloadVideo.filePath);
                            handleNetWorkChange();
                        }
                    }
                    handleResult(this.mDownloadVideo);
                    return this.mDownloadVideo;
                } catch (MalformedURLException e2) {
                    this.mDownloadVideo.state = 7;
                    return null;
                }
            } catch (LetvDownloadException e3) {
                e3.printException();
                this.mDownloadVideo.state = e3.getState();
                LogInfo.log(TAG, "getRealUrl exception state : " + this.mDownloadVideo.state);
                return null;
            }
        }
    }

    private void handleNetWorkChange() {
        if (NetworkUtils.isMobileNetwork() && !PreferencesManager.getInstance().isAllowMobileNetwork()) {
            LogInfo.log(TAG, "handleNetWorkChange mobile network pauseAll download");
            FilePartDownloader.isMobileNetWorkNotDownload = true;
            DownloadUtil.saveException(" download process handleNetWorkChange isAllowMobileNetwork is false !!!  vid = " + this.mDownloadVideo.vid);
            DownloadManager.pauseAllDownload();
        }
    }

    private void handleStoreLack(int status, String filePath) {
        if (status == 1 && !TextUtils.isEmpty(filePath) && StoreDeviceInfo.getAvailableSpace(filePath) < StoreManager.DEFUALT_DOWNLOAD_MINI_SIZE) {
            LogInfo.log(TAG, "22availableSize < DEFUALT_DOWNLOAD_MINI_SIZE");
            DownloadUtil.saveException(" download process availableSize < DEFUALT_DOWNLOAD_MINI_SIZE !!!  vid = " + this.mDownloadVideo.vid);
            DownloadManager.pauseAllDownload();
        }
    }

    protected void onCancelled() {
        super.onCancelled();
        if (this.mDownloadVideo != null) {
            LogInfo.log(TAG, "onCancelled mDownloadVideo state : " + this.mDownloadVideo.state + " isSpecialCancel : " + this.isSpecialCancel);
            if (!this.isSpecialCancel) {
                DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
            }
        }
    }

    private void handleResult(DownloadVideo result) {
        if (result == null) {
            LogInfo.log(TAG, "onPostExecute result == null isErrorState: " + this.mDownloadVideo.isErrorState() + " state : " + this.mDownloadVideo.state);
            if (!(this.mDownloadVideo.isErrorState() || this.mDownloadVideo.state == 3)) {
                this.mDownloadVideo.state = 5;
            }
            if (!this.isSpecialCancel) {
                DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
            }
            if (!this.isCancelled) {
                this.mDownloadService.startPendingDownload();
            }
        } else if (this.mDownloadVideo.downloaded < this.mDownloadVideo.totalsize || this.mDownloadVideo.totalsize <= 0) {
            stopPartDownload();
            LogInfo.log(TAG, "onPostExecute isErrorState : " + this.mDownloadVideo.isErrorState() + " isSpecialCancel : " + this.isSpecialCancel);
            if (!this.mDownloadVideo.isErrorState()) {
                this.mDownloadVideo.state = 3;
            }
            if (!this.isSpecialCancel) {
                DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
            }
            if (!this.isCancelled) {
                this.mDownloadService.startPendingDownload();
            }
        } else {
            this.mDownloadVideo.state = 4;
            stopPartDownload();
            DownloadDBDao.getInstance(this.mContext).removeAllPartInfo(this.mDownloadVideo);
            if (!this.mDownloadVideo.hasSubtitle || TextUtils.isEmpty(this.mDownloadVideo.subtitleUrl)) {
                this.mDownloadService.updateDownloading(this.mDownloadVideo);
            } else if (DownloadSubtitleManager.downloadSubtitle(this.mContext, this.mDownloadVideo.subtitleUrl)) {
                LogInfo.log("wangtao", "字幕下载成功");
                this.mDownloadService.updateDownloading(this.mDownloadVideo);
            } else {
                LogInfo.log("wangtao", "字幕下载失败");
                this.mDownloadVideo.state = 5;
                this.mDownloadService.updateDownloading(this.mDownloadVideo);
            }
            DownloadUtil.asyUpdateFileData();
            DownloadPauseStatistics.downloadPauseReport(getDownloadSpeed(), 5);
            DownloadUtil.saveException(" download finished  downloadvideo vid = " + this.mDownloadVideo.vid);
        }
    }

    protected void onPostExecute(DownloadVideo result) {
        L.v(TAG, "onPostExecute result : " + result + " isCancelled : " + this.isCancelled);
    }

    private void stopPartDownload() {
        L.v(TAG, "stopPartDownload>> parts " + this.parts);
        synchronized (sLock) {
            int i;
            if (this.parts != null) {
                for (i = 0; i < this.parts.length; i++) {
                    if (this.parts[i] != null) {
                        LogInfo.log("onCancelled thread #" + this.parts[i].mPartInfo.rowId);
                        this.parts[i].stopFilePartDownloader();
                    }
                }
                if (this.threads != null) {
                    for (i = 0; i < this.threads.length; i++) {
                        if (this.threads[i] != null) {
                            this.threads[i].interrupt();
                            LogInfo.log("onCancelled thread interrupt name : " + this.threads[i].getName());
                        }
                    }
                }
            } else {
                L.v(TAG, "stopPartDownload>> mDownloadVideo " + this.mDownloadVideo);
                if (!(this.mDownloadVideo == null || this.mDownloadVideo.mParts == null)) {
                    for (PartInfoBean partInfoBean : this.mDownloadVideo.mParts) {
                        if (partInfoBean != null) {
                            L.v(TAG, "stopPartDownload222 cancelled vid >> " + partInfoBean.vid);
                            partInfoBean.cancelled = true;
                        }
                    }
                }
            }
        }
    }

    public boolean isCancelDownload() {
        return this.isCancelled;
    }

    public void doCancelled(boolean isSpecialCancel) {
        cancel(true);
        this.isSpecialCancel = isSpecialCancel;
        this.isCancelled = true;
        stopPartDownload();
        this.mDownloadVideo.state = 3;
        if (!isSpecialCancel) {
            DownloadDBDao.getInstance(this.mContext).updateDownloadVideoByVid(this.mDownloadVideo);
        }
    }

    public String calculateDownloadSpeed(long timestamp, long curtime, long downloadedSize) {
        long time = (curtime - timestamp) / 1000;
        if (time <= 0 || downloadedSize <= 0) {
            return "";
        }
        return Formatter.formatFileSize(this.mContext, downloadedSize / time) + "/s";
    }

    private void makeParts() {
        LogInfo.log("fornia", "makeParts:mDownloadVideo" + this.mDownloadVideo);
        if (this.mDownloadVideo.mParts == null && !this.isCancelled) {
            LogInfo.log("fornia", "makeParts:mDownloadVideo" + this.mDownloadVideo.vid + "|mDownloadVideo.threadNum" + this.mDownloadVideo.threadNum);
            int i;
            if (DownloadDBDao.getInstance(this.mContext).isHasPartInfo(this.mDownloadVideo.vid)) {
                List<PartInfoBean> list = DownloadDBDao.getInstance(this.mContext).getAllPartInfoByVid(this.mDownloadVideo.vid);
                if (list != null) {
                    this.mDownloadVideo.mParts = new PartInfoBean[list.size()];
                    for (i = 0; i < list.size(); i++) {
                        this.mDownloadVideo.mParts[i] = (PartInfoBean) list.get(i);
                    }
                    return;
                }
                return;
            }
            this.mDownloadVideo.mParts = new PartInfoBean[this.mDownloadVideo.threadNum];
            LogInfo.log("fornia", "makeParts: mDownloadVideo.mParts.length" + this.mDownloadVideo.mParts.length);
            if (this.mDownloadVideo.threadNum != 0) {
                long bytesPerThread = this.mDownloadVideo.totalsize / ((long) this.mDownloadVideo.threadNum);
                long firstByte = 0;
                LogInfo.log("fornia", "makeParts: bytesPerThread" + bytesPerThread);
                for (i = 0; i < this.mDownloadVideo.threadNum; i++) {
                    long lastByte = (firstByte + bytesPerThread) - 1;
                    if (i == this.mDownloadVideo.threadNum - 1) {
                        lastByte = this.mDownloadVideo.totalsize - 1;
                    }
                    LogInfo.log("fornia", "firstByte:" + firstByte + "lastByte:" + lastByte + "i:" + i);
                    this.mDownloadVideo.mParts[i] = new PartInfoBean(this.mDownloadVideo.vid, i, firstByte, lastByte, 0, false);
                    this.mDownloadVideo.mParts[i].rowId = DownloadDBDao.getInstance(this.mContext).insertPartInfo(this.mDownloadVideo.vid, this.mDownloadVideo.mParts[i]);
                    firstByte = lastByte + 1;
                }
            }
        }
    }

    private long getContentLength(String urlString) throws LetvDownloadException {
        try {
            URL url = new URL(urlString);
            int tryNum = 0;
            LetvDownloadException exception = null;
            while (!this.isCancelled) {
                if (tryNum < 3) {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(30000);
                        connection.setRequestProperty("User-Agent", userAgent);
                        connection.setRequestMethod(HttpRequest.METHOD_HEAD);
                        connection.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "identity");
                        connection.connect();
                        long length = -1;
                        try {
                            length = Long.parseLong(connection.getHeaderField(HttpRequest.HEADER_CONTENT_LENGTH));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connection.disconnect();
                        return length;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        tryNum++;
                        exception = new NetWorkErrorException(e2.getMessage());
                        if (this.isCancelled) {
                            return 0;
                        }
                    }
                } else if (exception == null || this.isCancelled) {
                    return 0;
                } else {
                    throw exception;
                }
            }
            return 0;
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
            return 0;
        }
    }

    private String getRealUrl() throws LetvDownloadException {
        DownloadUtil letvUtil = new DownloadUtil(String.valueOf(this.mDownloadVideo.vid), this.mDownloadVideo, this.mDownloadVideo.mmsid, this.mDownloadVideo.streamType, this.mDownloadVideo.pcode, this.mDownloadVideo.version, this.mContext);
        int tryNum = 0;
        LetvDownloadException exception = null;
        while (!this.isCancelled) {
            if (tryNum == 3) {
                LogInfo.log(TAG, "tryNum == 5 !!!");
                if (exception == null || this.isCancelled) {
                    return null;
                }
                throw exception;
            }
            try {
                DownloadUrl du = letvUtil.getDownloadUrl();
                if (du == null) {
                    return null;
                }
                if (!TextUtils.isEmpty(du.subtitleDownloadUrl)) {
                    this.mDownloadVideo.hasSubtitle = true;
                    this.mDownloadVideo.subtitleUrl = du.subtitleDownloadUrl;
                    this.mDownloadVideo.subtitleCode = du.subtitleCode;
                }
                if (du.streamType != this.mDownloadVideo.streamType) {
                    this.mDownloadVideo.streamType = du.streamType;
                }
                if (du.isMultipleAudio) {
                    this.mDownloadVideo.isMultipleAudio = du.isMultipleAudio;
                    this.mDownloadVideo.multipleAudioCode = du.multipleAudioCode;
                }
                return du.videoDownloadUrl;
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof LetvDownloadException) {
                    ((LetvDownloadException) e).reportFailed();
                }
                tryNum++;
                if (e instanceof LetvDownloadException) {
                    exception = (LetvDownloadException) e;
                }
                if (this.isCancelled) {
                    return null;
                }
                try {
                    Thread.sleep(SPConstant.DELAY_BUFFER_DURATION);
                } catch (InterruptedException e2) {
                }
            }
        }
        return null;
    }

    private long getTotalTime() {
        long total = this.mDownloadVideo.timestamp;
        if (this.mDownloadVideo.state == 1) {
            return total + (System.currentTimeMillis() - this.mDownloadVideo.timestamp);
        }
        return total;
    }

    public String getFormattedDuration() {
        return formatDuration(getTotalTime());
    }

    public String getFormattedSpeed() {
        if (this.mDownloadVideo.timestamp >= System.currentTimeMillis()) {
            return "";
        }
        return "@ " + Formatter.formatFileSize(this.mContext, (long) (((double) this.mDownloadVideo.downloaded) / (((double) getTotalTime()) / 1000.0d))) + "/s";
    }

    private static String formatDuration(long millis) {
        if (millis > 60000) {
            return String.format("%.1fmins", new Object[]{Double.valueOf(((double) millis) / 60000.0d)});
        }
        return String.format("%.1fs", new Object[]{Double.valueOf(((double) millis) / 1000.0d)});
    }
}
