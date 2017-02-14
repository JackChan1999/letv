package com.letv.download.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.letv.core.constant.DownloadConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.VideoFileManager;
import com.letv.download.service.IDownloadService.Stub;
import com.letv.download.util.DownloadStatisticsUtil.DownloadPauseStatistics;
import com.letv.download.util.DownloadUtil;
import com.letv.download.util.L;
import com.letv.pp.listener.OnStartStatusChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DownloadService extends LetvIntentService {
    public static final String ACTION_ADD_DOWNLOAD = "add_download";
    public static final String ACTION_INIT_DOWNLOAD = "init_download";
    public static final String ACTION_INIT_NOTIFICAITON = "init_notificaiton";
    public static final String ACTION_PAUSE_ALL_DOWNLOAD = "pauseAll_download";
    public static final String ACTION_PAUSE_DOWNLOAD = "pause_download";
    public static final String ACTION_PAUSE_VIP_DOWNLOAD = "action_pause_vip_download";
    public static final String ACTION_REMOVE_DOWNLOAD = "remove_download";
    public static final String ACTION_RESUME_DOWNLOAD = "resume_download";
    public static final String ACTION_STARTALL_DOWNLOAD = "startAll_Download";
    public static final int DOWNLOAD_JOB_NUM_LIMIT = 1;
    public static final String DOWNLOAD_VIDEO_ARG = "downloadVideo";
    public static final String IS_USER_PAUSE_ARG = "is_user_pause_arg";
    public static final String TAG = DownloadService.class.getSimpleName();
    public static final String VID_ARG = "vid";
    private static Context mContext;
    static DownloadDBDao mDownloadDBDao;
    private static long mVid = 0;
    private boolean hasInitialRunningVideo = false;
    private boolean hasNetStatusReceiver = false;
    private boolean isNeed2DealChange = false;
    private Map<Long, DownloadVideo> mDownloadMaps = Collections.synchronizedMap(new LinkedHashMap());
    private DownloadNotification mDownloadNotification = null;
    private final Stub mIDownloadService = new Stub() {
        public void synRemoveDownload(long vid) throws RemoteException {
            L.v(DownloadService.TAG, "IDownloadService removeDownload vid : " + vid);
            DownloadService.this.removeDownload(vid);
        }
    };
    private volatile boolean mIsStartAllDownloaded = false;
    private NetStatusChangedReceiver mNetChangedReceiver = null;
    private String mSpeed = "";
    public Class<?> mTargetClass = null;

    private class NetStatusChangedReceiver extends BroadcastReceiver {
        public boolean firstIn;
        private boolean oldNetState;
        private int oldNetType;

        private NetStatusChangedReceiver() {
            this.firstIn = true;
            this.oldNetState = false;
            this.oldNetType = 0;
        }

        private void netWorkToNoNet() {
            L.v(DownloadService.TAG, "netWorkToNoNet>>");
            DownloadPauseStatistics.downloadPauseReport(FileDownloader.getDownloadSpeed(), 2);
            DownloadManager.pauseAllDownload();
        }

        private void noNetToNetWork() {
            if (NetworkUtils.isWifi()) {
                DownloadManager.startAllDownload();
            }
        }

        private void wifiToMobile() {
            L.v(DownloadService.TAG, "wifiToMobile", "  wifiToMobile >>> ");
            if (NetworkUtils.isMobileNetwork()) {
                boolean isAllow = PreferencesManager.getInstance().isAllowMobileNetwork();
                L.v(DownloadService.TAG, "wifiToMobile isAllow : " + isAllow);
                if (isAllow) {
                    DownloadManager.startAllDownload();
                    return;
                }
                DownloadPauseStatistics.downloadPauseReport(FileDownloader.getDownloadSpeed(), 3);
                DownloadManager.pauseAllDownload();
                return;
            }
            L.e(DownloadService.TAG, "wifiToMobile", " not MobileNetwork !!!! ");
        }

        public void onReceive(Context context, Intent intent) {
            L.v(DownloadService.TAG, "NetStatusChangedReceiver firstIn : " + this.firstIn);
            if (this.firstIn) {
                this.firstIn = false;
                this.oldNetType = NetworkUtils.getNetworkType();
                if (this.oldNetType != 0) {
                    this.oldNetState = true;
                    return;
                }
                return;
            }
            int currentNetType = NetworkUtils.getNetworkType();
            L.v(DownloadService.TAG, "NetStatusChangedReceiver currentNetType : " + currentNetType + " oldNetType : " + this.oldNetType + " oldNetState: " + this.oldNetState);
            if (NetworkUtils.isMobileNetwork() && currentNetType != this.oldNetType) {
                L.v(DownloadService.TAG, ">><<wifi to mobile");
                wifiToMobile();
            } else if (currentNetType != 0 && (!this.oldNetState || currentNetType != this.oldNetType)) {
                L.v(DownloadService.TAG, ">><<无网转有网");
                this.oldNetType = currentNetType;
                this.oldNetState = true;
                noNetToNetWork();
            } else if (currentNetType == 0 && this.oldNetState) {
                L.v(DownloadService.TAG, "有网转无网>><<");
                this.oldNetType = currentNetType;
                this.oldNetState = false;
                netWorkToNoNet();
            }
        }
    }

    public DownloadService() {
        super("DownloadService");
    }

    public DownloadService(String name) {
        super(name);
    }

    public IBinder onBind(Intent intent) {
        L.v(TAG, "onBind>>");
        return this.mIDownloadService;
    }

    private void initDownloadStartAndPause() {
        boolean isAllow = PreferencesManager.getInstance().isAllowMobileNetwork();
        if (this.mDownloadMaps.size() > 0 && (NetworkUtils.isWifi() || (NetworkUtils.isMobileNetwork() && isAllow))) {
            L.v(TAG, " initDownloadStartAndPause startAllDownload");
            DownloadManager.startAllDownload();
        } else if (this.mDownloadMaps.size() > 0 && !NetworkUtils.isNetworkAvailable()) {
            L.v(TAG, "initDownloadStartAndPause pauseAllDownload");
            DownloadManager.pauseAllDownload();
        }
    }

    private void initLinkShell() {
        this.mIsStartAllDownloaded = false;
        try {
            DownloadUtil.startCde();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (DownloadUtil.getCdeHelper() != null) {
            DownloadUtil.getCdeHelper().setOnStartStatusChangeListener(new OnStartStatusChangeListener() {
                public void onLinkShellStartComplete(int i) {
                }

                public void onCdeStartComplete(int statusCode) {
                    if (statusCode == 0 && DownloadUtil.getCdeHelper() != null) {
                        boolean isLinkShellReady = DownloadUtil.getCdeHelper().linkshellReady();
                        LogInfo.log(DownloadService.TAG, "onServiceConnected linkshellReady : " + isLinkShellReady + " mIsStartAllDownloaded " + DownloadService.this.mIsStartAllDownloaded);
                        if (isLinkShellReady && !DownloadService.this.mIsStartAllDownloaded) {
                            DownloadService.this.mIsStartAllDownloaded = true;
                            DownloadService.this.initDownloadStartAndPause();
                        }
                    }
                }

                public void onCdeServiceDisconnected() {
                }
            });
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (DownloadUtil.getCdeHelper() != null) {
                    boolean isLinkShellReady = DownloadUtil.getCdeHelper().linkshellReady();
                    LogInfo.log(DownloadService.TAG, " postDelayed isLinkShellReady : " + isLinkShellReady + " mIsStartAllDownloaded : " + DownloadService.this.mIsStartAllDownloaded);
                    if (isLinkShellReady && !DownloadService.this.mIsStartAllDownloaded) {
                        DownloadService.this.mIsStartAllDownloaded = true;
                        DownloadService.this.initDownloadStartAndPause();
                    }
                }
            }
        }, 3000);
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mDownloadDBDao = DownloadDBDao.getInstance(mContext);
        initDownloading();
        initReceiver();
        initLinkShell();
    }

    private void setClass(Class<?> target) {
        this.mTargetClass = target;
    }

    private void initDownloadNotification(Class<?> target) {
        L.v(TAG, "initDownloadNotification target : " + target + " mContext: " + mContext);
        if (mContext != null && target != null) {
            this.mDownloadNotification = new DownloadNotification(mContext, target);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            DownloadUtil.stopCde();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (this.mDownloadMaps.size() > 0) {
            DownloadPauseStatistics.downloadPauseReport(FileDownloader.getDownloadSpeed(), 1);
        }
        try {
            pauseAllDownload(false);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            unregisterNetStatusReceiver();
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (this.mDownloadNotification != null) {
                this.mDownloadNotification.unregisterNotifyObserver();
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }

    private void initReceiver() {
        try {
            this.mNetChangedReceiver = new NetStatusChangedReceiver();
            registerNetStatusReceiver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerNetStatusReceiver() {
        registerReceiver(this.mNetChangedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.hasNetStatusReceiver = true;
    }

    private void unregisterNetStatusReceiver() {
        if (this.hasNetStatusReceiver) {
            unregisterReceiver(this.mNetChangedReceiver);
            this.hasNetStatusReceiver = false;
        }
    }

    private void initDownloading() {
        synchronized (this.mDownloadMaps) {
            L.v(TAG, "initDownloading size >>> " + this.mDownloadMaps.size());
            this.mDownloadMaps.clear();
            ArrayList<DownloadVideo> arrayDownloadVideo = mDownloadDBDao.getAllDownloadVideoing();
            if (arrayDownloadVideo != null) {
                for (int i = 0; i < arrayDownloadVideo.size(); i++) {
                    DownloadVideo downloadVideo = (DownloadVideo) arrayDownloadVideo.get(i);
                    this.mDownloadMaps.put(Long.valueOf(downloadVideo.vid), downloadVideo);
                    if (downloadVideo.state == 1) {
                        this.hasInitialRunningVideo = true;
                    }
                }
            }
        }
    }

    public synchronized void updateDownloading(DownloadVideo downloadedVideo) {
        LogInfo.log("MainAcitivityNotification updateDownloading " + downloadedVideo);
        setVid4Notification(0);
        DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(downloadedVideo);
        DownloadDBDao.getInstance(mContext).removeAllPartInfo(downloadedVideo);
        this.mDownloadMaps.clear();
        if (mDownloadDBDao == null) {
            mDownloadDBDao = DownloadDBDao.getInstance(mContext);
        }
        ArrayList<DownloadVideo> arrayDownloadVideo = mDownloadDBDao.getAllDownloadVideoing();
        if (arrayDownloadVideo != null) {
            for (int i = 0; i < arrayDownloadVideo.size(); i++) {
                DownloadVideo downloadVideo = (DownloadVideo) arrayDownloadVideo.get(i);
                this.mDownloadMaps.put(Long.valueOf(downloadVideo.vid), downloadVideo);
            }
        }
        startPendingDownload();
    }

    public void calculateDownloadSpeed(long lastStarted, long curtime, long downloadedSize) {
        long time = (curtime - lastStarted) / 1000;
        if (time > 0) {
            this.mSpeed = Formatter.formatFileSize(mContext, downloadedSize / time) + "/s";
            return;
        }
        this.mSpeed = "0.00 B/s";
    }

    public String getSpeed() {
        return this.mSpeed;
    }

    protected void pauseDownload(long vid) {
        L.v(TAG, "pauseDownload start vid: " + vid);
        DownloadVideo info = (DownloadVideo) this.mDownloadMaps.get(Long.valueOf(vid));
        if (info != null) {
            info.state = 3;
            L.v(TAG, "pauseDownload mDownloader " + info.mDownloader + " name : " + info.name);
            if (info.mDownloader != null) {
                info.mDownloader.doCancelled(false);
                DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
                startPendingDownload();
                return;
            }
            DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
        }
    }

    private void resumeDownload(long vid) {
        L.v(TAG, "resumeDownload start vid " + vid + " hasInitialRunningVideo : " + this.hasInitialRunningVideo);
        DownloadVideo info = (DownloadVideo) this.mDownloadMaps.get(Long.valueOf(vid));
        if (info != null) {
            if (this.hasInitialRunningVideo) {
                if (info.state != 1) {
                    info.state = 0;
                    DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
                    return;
                }
                if (!(info.mDownloader == null || info.mDownloader.isCancelDownload())) {
                    L.v(TAG, "resumeDownload", " mDownloader canceldownload111");
                    info.mDownloader.doCancelled(false);
                }
                startDownloadVideo(info);
            } else if (info.state == 1) {
                DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
            } else if (getDownloadingNum() < DownloadConstant.DOWNLOAD_JOB_NUM_LIMIT) {
                if (!(info.mDownloader == null || info.mDownloader.isCancelDownload())) {
                    L.v(TAG, "resumeDownload", " mDownloader canceldownload222");
                    info.mDownloader.doCancelled(false);
                }
                startDownloadVideo(info);
            } else {
                info.state = 0;
                DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
            }
        }
    }

    private void startAllDownload() {
        L.v(TAG, " startAllDownload start >> ");
        for (DownloadVideo info : this.mDownloadMaps.values()) {
            resumeDownload(info.vid);
        }
        this.hasInitialRunningVideo = false;
    }

    protected void pauseAllDownload(boolean isUserPause) {
        L.v(TAG, " pauseAllDownload start isUserPause >> " + isUserPause);
        DownloadVideo info;
        if (isUserPause) {
            for (Object obj : this.mDownloadMaps.keySet()) {
                info = (DownloadVideo) this.mDownloadMaps.get(obj);
                if (info != null) {
                    if (info.mDownloader != null) {
                        info.state = 3;
                        info.mDownloader.doCancelled(false);
                    } else {
                        info.state = 3;
                    }
                    DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(info);
                }
            }
            return;
        }
        L.v(TAG, "pauseAllDownload userpause >>>>>>>>>>>>>>>> ");
        for (Object obj2 : this.mDownloadMaps.keySet()) {
            info = (DownloadVideo) this.mDownloadMaps.get(obj2);
            if (info != null) {
                L.v(TAG, "pauseAllDownload userpause name >>> " + info.name + " info.mDownloader : " + info.mDownloader);
                if (info.mDownloader != null) {
                    info.mDownloader.doCancelled(true);
                }
            }
        }
    }

    private void addDownloadVideo(DownloadVideo downloadVideo) {
        L.v(TAG, "addDownloadVideo start >> ");
        synchronized (this.mDownloadMaps) {
            mDownloadDBDao.addNewDownload(downloadVideo);
            this.mDownloadMaps.put(Long.valueOf(downloadVideo.vid), downloadVideo);
        }
        downloadVideo.state = 0;
        int downloadingNum = getDownloadingNum();
        L.v(TAG, "addDownloadVideo", ">>downloadingNum : " + downloadingNum);
        File saveFile = new File(downloadVideo.filePath, VideoFileManager.createFileName(downloadVideo.vid));
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (downloadingNum < 1) {
            DownloadUtil.saveException(" New download taks addDownloadVideo  vid = " + downloadVideo.vid + " name: " + downloadVideo.name);
            startDownloadVideo(downloadVideo);
        }
    }

    private int getDownloadingNum() {
        int num;
        synchronized (this.mDownloadMaps) {
            num = 0;
            for (DownloadVideo info : this.mDownloadMaps.values()) {
                if (info.state == 1) {
                    num++;
                }
            }
            L.v(TAG, "getDownloadingNum num : " + num);
        }
        return num;
    }

    private void removeDownload(long vid) {
        DownloadVideo info = (DownloadVideo) this.mDownloadMaps.get(Long.valueOf(vid));
        if (info != null) {
            if (info.mDownloader != null) {
                if (info.state == 3) {
                    info.mDownloader.doCancelled(false);
                } else {
                    info.mDownloader.doCancelled(false);
                }
            }
            File file = new File(info.filePath, VideoFileManager.createFileName(info.vid));
            L.v(TAG, "removeDownload file is exists : " + file.exists());
            if (file.exists()) {
                file.delete();
            }
            DownloadDBDao.getInstance(mContext).removeDownloadVideoing(info.vid, info.aid);
            this.mDownloadMaps.remove(Long.valueOf(vid));
            startPendingDownload();
        }
    }

    public synchronized void startPendingDownload() {
        DownloadVideo pending = getPendingDownload();
        if (pending != null) {
            L.v(TAG, "startPendingDownload pending : " + pending);
            if (!(pending.mDownloader == null || pending.mDownloader.isCancelDownload())) {
                L.v(TAG, "startPendingDownload pending doCancelled ");
                pending.mDownloader.doCancelled(false);
            }
            startDownloadVideo(pending);
        }
    }

    private synchronized void startDownloadVideo(DownloadVideo video) {
        video.mDownloader = new FileDownloader(video, mContext, this);
        video.state = 1;
        LogInfo.log(TAG, " startDownloadVideo isVipDownload : " + video.isVipDownload + " isVip : " + PreferencesManager.getInstance().isVip());
        if (PreferencesManager.getInstance().isVip() || !video.isVipDownload) {
            video.mDownloader.execute(new Void[0]);
        } else {
            pauseDownload(video.vid);
        }
        DownloadDBDao.getInstance(mContext).updateDownloadVideoByVid(video);
        LogInfo.log("MainAcitivityNotification startDownloadVideo " + video.vid + " name " + video.name);
        setVid4Notification(video.vid);
    }

    private synchronized DownloadVideo getPendingDownload() {
        DownloadVideo downloadVideo;
        if (getDownloadingNum() >= DownloadConstant.DOWNLOAD_JOB_NUM_LIMIT) {
            downloadVideo = null;
        } else {
            Iterator<DownloadVideo> ite = this.mDownloadMaps.values().iterator();
            synchronized (this.mDownloadMaps) {
                do {
                    if (!ite.hasNext()) {
                        downloadVideo = null;
                        break;
                    }
                    downloadVideo = (DownloadVideo) ite.next();
                } while (downloadVideo.state != 0);
            }
        }
        return downloadVideo;
    }

    protected void onHandleIntent(Intent intent) {
        LogInfo.log(TAG, "onHandleIntent intent " + intent);
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            String action = intent.getAction();
            long vid = intent.getLongExtra("vid", 0);
            boolean isUserPause = intent.getBooleanExtra(IS_USER_PAUSE_ARG, true);
            DownloadVideo downloadVideo = (DownloadVideo) intent.getParcelableExtra(DOWNLOAD_VIDEO_ARG);
            if (action.equals(ACTION_PAUSE_DOWNLOAD)) {
                if (vid != 0) {
                    pauseDownload(vid);
                }
            } else if (action.equals(ACTION_REMOVE_DOWNLOAD)) {
                if (vid != 0) {
                    removeDownload(vid);
                }
            } else if (action.equals(ACTION_STARTALL_DOWNLOAD)) {
                startAllDownload();
            } else if (action.equals(ACTION_PAUSE_ALL_DOWNLOAD)) {
                try {
                    pauseAllDownload(isUserPause);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (action.equals(ACTION_ADD_DOWNLOAD)) {
                if (downloadVideo != null) {
                    addDownloadVideo(downloadVideo);
                }
            } else if (action.equals(ACTION_RESUME_DOWNLOAD)) {
                if (vid != 0) {
                    resumeDownload(vid);
                }
            } else if (action.equals(ACTION_INIT_DOWNLOAD)) {
                L.v(TAG, "ACTION_INIT_DOWNLOAD>>");
                initDownloading();
            } else if (action.equals(ACTION_INIT_NOTIFICAITON)) {
                if (intent.getSerializableExtra("class") != null) {
                    setClass((Class) intent.getSerializableExtra("class"));
                }
                L.v(TAG, "onHandleIntent ACTION_INIT_NOTIFICAITON " + this.mDownloadNotification);
                if (this.mDownloadNotification == null && this.mTargetClass != null) {
                    initDownloadNotification(this.mTargetClass);
                }
            } else if (action.equals(ACTION_PAUSE_VIP_DOWNLOAD)) {
                pauseVipDownload();
            }
        }
    }

    private void pauseVipDownload() {
        LogInfo.log(TAG, "pauseVipDownload >>>>>>>>");
        ArrayList<DownloadVideo> arrayDownloadVideo = DownloadDBDao.getInstance(mContext).getAllDownloadVideoing();
        if (arrayDownloadVideo != null) {
            Iterator it = arrayDownloadVideo.iterator();
            while (it.hasNext()) {
                DownloadVideo video = (DownloadVideo) it.next();
                if (video.state == 1) {
                    LogInfo.log(TAG, " video name run : " + video.name);
                    if (video.isVipDownload) {
                        LogInfo.log(TAG, " video name pause : " + video.name);
                        PreferencesManager.getInstance().setVip(false);
                        DownloadManager.pauseDownload(video.vid);
                        return;
                    }
                }
            }
        }
    }

    private void setVid4Notification(long vid) {
        mVid = vid;
    }

    public static long getVid4Notification() {
        return mVid;
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogInfo.log(TAG, "onTrimMemory level : " + level);
    }

    public void onLowMemory() {
        super.onLowMemory();
    }
}
