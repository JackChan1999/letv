package com.letv.pp.func;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.lemallsdk.util.Constants;
import com.letv.pp.listener.OnLibraryUpgradeListener;
import com.letv.pp.listener.OnStartStatusChangeListener;
import com.letv.pp.security.Encryption;
import com.letv.pp.service.CdeService;
import com.letv.pp.service.CloudService;
import com.letv.pp.service.ICdeBinder;
import com.letv.pp.update.DownloadEngine;
import com.letv.pp.url.PlayUrl;
import com.letv.pp.utils.ContextUtils;
import com.letv.pp.utils.CpuUtils;
import com.letv.pp.utils.DomainHelper;
import com.letv.pp.utils.FileHelper;
import com.letv.pp.utils.LibraryHelper;
import com.letv.pp.utils.LogTool;
import com.letv.pp.utils.MD5Utils;
import com.letv.pp.utils.NetworkUtils;
import com.letv.pp.utils.SPHelper;
import com.letv.pp.utils.StringUtils;
import com.letv.pp.utils.ZipUtils;
import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

public class CdeHelper {
    private static final String FILE_NAME_CDE = "cde.xml";
    private static final int LIBRARY_STATE_INIT = 0;
    private static final int LIBRARY_STATE_PULL = 1;
    private static final int LIBRARY_STATE_READY = 3;
    private static final int LIBRARY_STATE_UPGRADE = 2;
    private static final String SDK_VERSION = "155-20160516";
    private static final String TAG = "CdeHelper";
    private static final String TAG_SERIAL_NUMBER = "serialnumber";
    private static final String TAG_VERSION_NAME = "versionname";
    private static CdeHelper sSingleton;
    private boolean mAlreadyStarted;
    private String mAppChannel;
    private String mAppId;
    private boolean mAsServerSide;
    private final boolean mAutoStartUpgrade;
    private boolean mBoundService;
    private ICdeBinder mCdeBinder;
    private CdeServiceConnection mCdeServiceConnection;
    private int mCdeStartStatusCode;
    private Class<? extends Activity> mClass;
    private boolean mConnectedService;
    private String mContentText;
    private String mContentTitle;
    private final Context mContext;
    private DownloadEngine mDownloadEngine;
    private Encryption mEncryption;
    private int mIcon;
    private InternalBroadcastReceiver mInternalBroadcastReceiver;
    private int mLibraryState;
    private boolean mManuallyStartUpgradeTag;
    private String mNativeLibMd5;
    private String mNativeVersion;
    private boolean mNeedPullLibrary;
    private boolean mNeedStartPull;
    private NetworkBroadcastReceive mNetworkBroadcastReceive;
    private OnLibraryUpgradeListener mOnLibraryUpgradeListener;
    private OnStartStatusChangeListener mOnStartStatusChangeListener;
    private boolean mOpenedServerSide;
    private boolean mReceivedBroadcast;
    private boolean mRemoteVersion;
    private final boolean mStartAfterUpgrade;
    private final String mStartParams;
    private long mStartTime;

    private CdeHelper(Context context, String params, boolean startAfterUpgrade, boolean autoStartUpgrade) {
        this.mContext = context.getApplicationContext();
        this.mStartParams = params;
        this.mAutoStartUpgrade = autoStartUpgrade;
        this.mStartAfterUpgrade = startAfterUpgrade;
        init();
    }

    private void init() {
        LogTool.i(TAG, "init. process name(%s), sdk version(%s)", ContextUtils.getProcessName(this.mContext, Process.myPid()), SDK_VERSION);
        this.mStartTime = System.nanoTime();
        this.mLibraryState = 0;
        HashMap<String, String> paramMap = StringUtils.parseParams(this.mStartParams);
        initLog(paramMap);
        parseParam(paramMap);
        deleteOldLib();
        detectVersion();
        DomainHelper.init(paramMap);
        LibraryHelper.init(paramMap);
        this.mNetworkBroadcastReceive = new NetworkBroadcastReceive(this, null);
        this.mContext.registerReceiver(this.mNetworkBroadcastReceive, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        LogTool.i(TAG, "init. spend time(%s)", StringUtils.formatTime((double) (System.nanoTime() - this.mStartTime)));
    }

    private void initLog(HashMap<String, String> paramMap) {
        SPHelper spHelper = SPHelper.getInstance(this.mContext);
        if (paramMap != null) {
            String logLevel = (String) paramMap.get(SPHelper.KEY_LOG_LEVEL);
            if (StringUtils.isNumeric(logLevel)) {
                LogTool.setLogLevel(Integer.parseInt(logLevel));
                spHelper.putStringAndCommit(SPHelper.KEY_LOG_LEVEL, logLevel);
            }
        } else if (spHelper.getString(SPHelper.KEY_LOG_LEVEL, null) != null) {
            spHelper.removeAndCommit(SPHelper.KEY_LOG_LEVEL);
        }
    }

    private void parseParam(HashMap<String, String> paramMap) {
        LogTool.i(TAG, "parseParam. original params(%s)", this.mStartParams);
        if (paramMap == null) {
            this.mAppId = "0";
            return;
        }
        this.mAppChannel = (String) paramMap.get("app_channel");
        this.mAppId = (String) paramMap.get("app_id");
        if (StringUtils.isEmpty(this.mAppId)) {
            this.mAppId = "0";
        }
    }

    private void deleteOldLib() {
        String libraryOldRootPath = LibraryHelper.getLibraryOldRootPath(this.mContext);
        File localOldFile = new File(libraryOldRootPath, LibraryHelper.getLibraryLocalName(LibraryHelper.LIB_NAME_CDE));
        File nativeOldFile = new File(libraryOldRootPath, LibraryHelper.getLibraryNativeName(LibraryHelper.LIB_NAME_CDE));
        File upgradeOldFile = new File(libraryOldRootPath, LibraryHelper.LIB_UPGRADE_OLD_NAME);
        if (localOldFile.exists()) {
            localOldFile.delete();
        }
        if (nativeOldFile.exists()) {
            nativeOldFile.delete();
        }
        if (upgradeOldFile.exists()) {
            upgradeOldFile.delete();
        }
    }

    private void detectVersion() {
        SPHelper spHelper = SPHelper.getInstance(this.mContext);
        String localVersion = spHelper.getString(SPHelper.KEY_LIB_LOCAL_VERSION, null);
        this.mNativeVersion = FileHelper.getValueFromAssetsFile(this.mContext, FILE_NAME_CDE, TAG_VERSION_NAME);
        this.mRemoteVersion = !LibraryHelper.existLibraryInAssets(this.mContext, LibraryHelper.LIB_NAME_CDE);
        LogTool.i(TAG, "detectVersion. lib native version(%s), lib local version(%s), sdk remote version(%s)", this.mNativeVersion, localVersion, Boolean.valueOf(this.mRemoteVersion));
        if (!StringUtils.isEmpty(this.mNativeVersion)) {
            String libraryRootPath = LibraryHelper.getLibraryRootPath(this.mContext);
            File localFile = new File(libraryRootPath, LibraryHelper.getLibraryLocalName(LibraryHelper.LIB_NAME_CDE));
            File nativeFile = new File(libraryRootPath, LibraryHelper.getLibraryNativeName(LibraryHelper.LIB_NAME_CDE));
            File upgradeFile = new File(libraryRootPath, LibraryHelper.getLibraryUpgradeName(LibraryHelper.LIB_NAME_CDE));
            if (StringUtils.isEmpty(localVersion) || localVersion.compareTo(this.mNativeVersion) < 0) {
                spHelper.putStringAndCommit(SPHelper.KEY_LIB_LOCAL_VERSION, this.mNativeVersion);
                if (!this.mRemoteVersion) {
                    FileHelper.deleteFileOrDir(nativeFile.getParentFile(), true);
                }
            } else if (localVersion.compareTo(this.mNativeVersion) > 0) {
                if (!(localFile.exists() || upgradeFile.exists())) {
                    spHelper.putStringAndCommit(SPHelper.KEY_LIB_LOCAL_VERSION, this.mNativeVersion);
                }
            } else if (!this.mRemoteVersion) {
                if (localFile.exists()) {
                    localFile.delete();
                }
                if (upgradeFile.exists()) {
                    upgradeFile.delete();
                }
            }
            this.mNativeLibMd5 = FileHelper.getValueFromAssetsFile(this.mContext, FILE_NAME_CDE, CpuUtils.getCpuType() + Field.MD5_ID);
            if (!nativeFile.exists()) {
                this.mNeedPullLibrary = this.mRemoteVersion;
            } else if (!MD5Utils.checkFileMD5(nativeFile, this.mNativeLibMd5)) {
                this.mNeedPullLibrary = this.mRemoteVersion;
                if (!this.mRemoteVersion) {
                    nativeFile.delete();
                }
            }
        }
    }

    public static CdeHelper getInstance(Context context, String startParams, boolean startAfterUpgrade, boolean autoStartUpgrade) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        if (sSingleton == null) {
            synchronized (CdeHelper.class) {
                if (sSingleton == null) {
                    sSingleton = new CdeHelper(context, startParams, startAfterUpgrade, autoStartUpgrade);
                }
            }
        }
        return sSingleton;
    }

    public static CdeHelper getInstance(Context context, String startParams, boolean startAfterUpgrade) {
        return getInstance(context, startParams, startAfterUpgrade, true);
    }

    public static CdeHelper getInstance(Context context, String startParams) {
        return getInstance(context, startParams, true);
    }

    public static CdeHelper getInstance() {
        return sSingleton;
    }

    public void setForeground(Class<? extends Activity> clazz, int icon, String contentTitle, String contentText) {
        this.mClass = clazz;
        this.mIcon = icon;
        this.mContentTitle = contentTitle;
        this.mContentText = contentText;
    }

    public void start() {
        start(false);
    }

    public void start(boolean asServerSide) {
        this.mStartTime = System.nanoTime();
        if (StringUtils.isEmpty(this.mNativeVersion)) {
            LogTool.e(TAG, "start. config file not found.");
            callbackLinkShellStartComplete(-1);
            callbackCdeStartComplete(-1);
        } else if (this.mAlreadyStarted) {
            LogTool.i(TAG, "start. hava already started.");
            switch (this.mLibraryState) {
                case 0:
                    if (!this.mNeedPullLibrary || new File(LibraryHelper.getLibraryRootPath(this.mContext), LibraryHelper.getLibraryNativeName(LibraryHelper.LIB_NAME_CDE)).exists()) {
                        startLinkShell();
                        startService();
                        return;
                    }
                    pullLibrary();
                    return;
                case 1:
                    LogTool.i(TAG, "start. the library in the pull.");
                    return;
                case 2:
                    LogTool.i(TAG, "start. the library in the upgrade.");
                    return;
                case 3:
                    startLinkShell();
                    startService();
                    return;
                default:
                    return;
            }
        } else {
            LogTool.i(TAG, "start. to start.");
            this.mAlreadyStarted = true;
            this.mAsServerSide = asServerSide;
            if (!pullLibrary() && !upgradeLibrary()) {
                this.mLibraryState = 3;
                startLinkShell();
                startService();
            }
        }
    }

    private void callbackLinkShellStartComplete(int statusCode) {
        LogTool.i(TAG, "callbackLinkShellStartComplete. status code(%s), spend time(%s)", Integer.valueOf(statusCode), StringUtils.formatTime((double) (System.nanoTime() - this.mStartTime)));
        if (this.mOnStartStatusChangeListener != null) {
            this.mOnStartStatusChangeListener.onLinkShellStartComplete(statusCode);
        }
    }

    private void callbackCdeStartComplete(int statusCode) {
        LogTool.i(TAG, "callbackCdeStartComplete. status code(%s), spend time(%s)", Integer.valueOf(statusCode), StringUtils.formatTime((double) (System.nanoTime() - this.mStartTime)));
        if (this.mOnStartStatusChangeListener != null) {
            this.mOnStartStatusChangeListener.onCdeStartComplete(statusCode);
        }
    }

    private boolean pullLibrary() {
        this.mNeedStartPull = false;
        if (!this.mNeedPullLibrary) {
            return false;
        }
        this.mLibraryState = 1;
        this.mDownloadEngine = new DownloadEngine(this.mContext, this.mAppId, this.mAppChannel, false);
        this.mDownloadEngine.setOnPullCompleteListener(new 1(this));
        NetworkUtils.detectNetwork(this.mContext);
        if (NetworkUtils.hasNetwork()) {
            this.mDownloadEngine.startPull(this.mNativeVersion, this.mNativeLibMd5, FileHelper.getValueFromAssetsFile(this.mContext, FILE_NAME_CDE, TAG_SERIAL_NUMBER));
            return true;
        }
        LogTool.i(TAG, "pullLibrary. no network, pull the library after waiting for networking.");
        this.mNeedStartPull = true;
        return true;
    }

    private boolean upgradeLibrary() {
        if (!this.mStartAfterUpgrade) {
            return false;
        }
        NetworkUtils.detectNetwork(this.mContext);
        if (!NetworkUtils.hasNetwork()) {
            return false;
        }
        if (this.mDownloadEngine == null) {
            this.mDownloadEngine = new DownloadEngine(this.mContext, this.mAppId, this.mAppChannel, false);
        }
        if (!this.mDownloadEngine.isUpgradeEnabled()) {
            return false;
        }
        this.mLibraryState = 2;
        this.mDownloadEngine.setOnUpgradeCompleteListener(new 2(this));
        this.mDownloadEngine.startUpgrade(0);
        return true;
    }

    private void stopDownloadEngine() {
        if (this.mDownloadEngine != null) {
            this.mDownloadEngine.setOnPullCompleteListener(null);
            this.mDownloadEngine.stopPull();
            this.mDownloadEngine.setOnUpgradeCompleteListener(null);
            this.mDownloadEngine.stopUpgrade();
            this.mDownloadEngine.quit();
            this.mDownloadEngine = null;
        }
    }

    private void startService() {
        if (this.mBoundService) {
            LogTool.i(TAG, "startService. the CdeService has been successfully bind, ignore.");
            if (this.mReceivedBroadcast && this.mConnectedService) {
                int i;
                if (this.mCdeBinder != null) {
                    i = this.mCdeStartStatusCode;
                } else {
                    i = -6;
                }
                callbackCdeStartComplete(i);
                return;
            }
            return;
        }
        try {
            if (this.mInternalBroadcastReceiver == null) {
                this.mInternalBroadcastReceiver = new InternalBroadcastReceiver(this);
                IntentFilter intentFilter = new IntentFilter("com.letv.pp.action.CDE_START_COMPLETE");
                intentFilter.addAction("com.letv.pp.action.UPGRADE_START");
                intentFilter.addAction("com.letv.pp.UPGRADE_END");
                this.mContext.registerReceiver(this.mInternalBroadcastReceiver, intentFilter);
            }
            Intent intent = new Intent(this.mContext, CdeService.class);
            intent.putExtra(CdeService.KEY_START_PARAMS, this.mStartParams);
            intent.putExtra(CdeService.KEY_AUTO_START_UPGRADE, this.mAutoStartUpgrade);
            intent.putExtra(CdeService.KEY_START_AFTER_UPGRADE, this.mStartAfterUpgrade);
            if (this.mClass != null) {
                intent.putExtra(CdeService.KEY_ACTIVITY_CLASS, this.mClass);
                intent.putExtra(CdeService.KEY_NOTIFACION_ICON, this.mIcon);
                intent.putExtra(CdeService.KEY_NOTIFACION_CONTENTTITLE, this.mContentTitle);
                intent.putExtra(CdeService.KEY_NOTIFACION_CONTENTTEXT, this.mContentText);
            }
            if (this.mCdeServiceConnection == null) {
                this.mCdeServiceConnection = new CdeServiceConnection(this, null);
            }
            this.mBoundService = this.mContext.bindService(intent, this.mCdeServiceConnection, 1);
            String str = TAG;
            String str2 = "startService. bind CdeService %s.";
            Object[] objArr = new Object[1];
            objArr[0] = this.mBoundService ? "successfully" : Constants.CALLBACK_FAILD;
            LogTool.i(str, str2, objArr);
        } catch (Exception e) {
            LogTool.e(TAG, "startService. " + e.toString());
        }
        if (!this.mBoundService) {
            callbackCdeStartComplete(-5);
        }
    }

    private void startLinkShell() {
        int i = 0;
        if (this.mEncryption != null) {
            if (!this.mEncryption.isLinkShellReady()) {
                i = -4;
            }
            callbackLinkShellStartComplete(i);
        } else if (LibraryHelper.loadLibrary(this.mContext, LibraryHelper.LIB_NAME_CDE)) {
            this.mEncryption = new Encryption(this.mContext);
            if (!this.mEncryption.isLinkShellReady()) {
                i = -4;
            }
            callbackLinkShellStartComplete(i);
        } else {
            callbackLinkShellStartComplete(-3);
        }
    }

    public void stop() {
        LogTool.i(TAG, "stop. the CdeService is bind(%s)", Boolean.valueOf(this.mBoundService));
        if (this.mLibraryState == 1 || this.mLibraryState == 2) {
            this.mLibraryState = 0;
        }
        stopDownloadEngine();
        try {
            if (this.mInternalBroadcastReceiver != null) {
                this.mContext.unregisterReceiver(this.mInternalBroadcastReceiver);
                this.mInternalBroadcastReceiver = null;
            }
            if (this.mCdeServiceConnection != null) {
                this.mContext.unbindService(this.mCdeServiceConnection);
                this.mCdeServiceConnection = null;
            }
        } catch (Exception e) {
            LogTool.e(TAG, "stop. " + e.toString());
        }
        reset();
        if (this.mAsServerSide) {
            stopServer();
        }
    }

    private void openServer() {
        if (this.mOpenedServerSide) {
            LogTool.i(TAG, "openServer. the CloudService has been started, ignore.");
            return;
        }
        try {
            Intent intent = new Intent(this.mContext, CloudService.class);
            intent.setAction("com.letv.pp.service.IP2PBinder");
            if (this.mContext.startService(intent) == null) {
                LogTool.e(TAG, "openServer. start CloudService failed, CloudService not register.");
                this.mOpenedServerSide = false;
                return;
            }
            LogTool.i(TAG, "openServer. start CloudService successfully.");
            this.mOpenedServerSide = true;
        } catch (Exception e) {
            LogTool.e(TAG, "openServer. " + e.toString());
        }
    }

    private void stopServer() {
        if (this.mOpenedServerSide) {
            this.mOpenedServerSide = false;
            try {
                Intent intent = new Intent(this.mContext, CloudService.class);
                intent.setAction("com.letv.pp.service.IP2PBinder");
                boolean isStopSuccess = this.mContext.stopService(intent);
                String str = TAG;
                String str2 = "stopServer. stop CloudService %s.";
                Object[] objArr = new Object[1];
                objArr[0] = isStopSuccess ? "successfully" : Constants.CALLBACK_FAILD;
                LogTool.i(str, str2, objArr);
                return;
            } catch (Exception e) {
                LogTool.e(TAG, "stopServer. " + e.toString());
                return;
            }
        }
        LogTool.i(TAG, "stopServer. CloudService not start, do not need to stop.");
    }

    public void startUpgrade() {
        if (!this.mStartAfterUpgrade || !this.mManuallyStartUpgradeTag) {
            this.mManuallyStartUpgradeTag = true;
            if (this.mReceivedBroadcast && this.mConnectedService) {
                LogTool.i(TAG, "startUpgrade. manually start the upgrade.");
                try {
                    this.mCdeBinder.startUpgrade();
                    return;
                } catch (Exception e) {
                    LogTool.e(TAG, "startUpgrade. " + e.toString());
                    return;
                }
            }
            LogTool.i(TAG, "startUpgrade. after start the upgrade onServiceConnected callback.");
        }
    }

    public int getUpgradePercent() {
        try {
            if (this.mCdeBinder != null) {
                return this.mCdeBinder.getUpgradePercentage();
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getUpgradePercent. " + e.toString());
        }
        return 0;
    }

    public boolean linkshellReady() {
        return this.mEncryption != null ? this.mEncryption.isLinkShellReady() : false;
    }

    public String getLinkShellVersion() {
        return this.mEncryption != null ? this.mEncryption.getLinkShellVersion() : null;
    }

    public String getLinkshellUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String linkShellUrl = this.mEncryption != null ? this.mEncryption.getLinkShellUrl(url) : null;
        if (TextUtils.isEmpty(linkShellUrl)) {
            linkShellUrl = getLinkshellUrl2(url);
        }
        return linkShellUrl;
    }

    public String getLinkshellUrl2(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (url.contains("?") && !url.endsWith("?") && url.contains("lstm")) {
            String[] array = url.split("\\?");
            stringBuilder.append(array[0]).append("?");
            int num = 0;
            for (Entry<String, String> entry : StringUtils.parseParams(array[1]).entrySet()) {
                num++;
                if (num > 1) {
                    stringBuilder.append("&");
                }
                if ("lstm".equals(entry.getKey())) {
                    stringBuilder.append("lstm=").append(new Date().getTime());
                } else {
                    stringBuilder.append((String) entry.getKey()).append(SearchCriteria.EQ).append((String) entry.getValue());
                }
            }
            return stringBuilder.toString();
        }
        if (!url.contains("?")) {
            url = new StringBuilder(String.valueOf(url)).append("?").toString();
        }
        stringBuilder.append(url);
        if (!(url.endsWith("?") || url.endsWith("&"))) {
            stringBuilder.append("&");
        }
        stringBuilder.append("lstm=").append(new Date().getTime());
        return stringBuilder.toString();
    }

    public boolean isReady() {
        try {
            if (this.mCdeBinder != null) {
                return this.mCdeBinder.isCdeReady();
            }
        } catch (Exception e) {
            LogTool.e(TAG, "isReady. " + e.toString());
        }
        return false;
    }

    public String getServiceVersion() {
        try {
            if (this.mCdeBinder != null) {
                return this.mCdeBinder.getCdeVersion();
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getServiceVersion. " + e.toString());
        }
        return null;
    }

    public long getServicePort() {
        try {
            if (this.mCdeBinder != null) {
                return this.mCdeBinder.getCdePort();
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getServicePort. " + e.toString());
        }
        return 0;
    }

    public String getCacheUrlWithData(String data, String ext, String g3Url, String other) {
        if (TextUtils.isEmpty(data)) {
            return g3Url;
        }
        String cacheUrl = null;
        try {
            long startTime = System.currentTimeMillis();
            String compressData = ZipUtils.compress(data);
            LogTool.i(TAG, "getCacheUrlWithData. compress data spend time(%s ms)", Long.valueOf(System.currentTimeMillis() - startTime));
            if (this.mCdeBinder != null) {
                cacheUrl = this.mCdeBinder.getCacheUrlWithData(compressData, ext, g3Url, other);
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getCacheUrlWithData. " + e.toString());
        }
        return !TextUtils.isEmpty(cacheUrl) ? cacheUrl : g3Url;
    }

    public long getStateLastReceiveSpeed(String linkshellUrl) {
        long j = -1;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    j = this.mCdeBinder.getStateLastReceiveSpeed(linkshellUrl);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "getStateLastReceiveSpeed. " + e.toString());
            }
        }
        return j;
    }

    public long getStateUrgentReceiveSpeed(String linkshellUrl) {
        long j = -1;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    j = this.mCdeBinder.getStateUrgentReceiveSpeed(linkshellUrl);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "getStateUrgentReceiveSpeed. " + e.toString());
            }
        }
        return j;
    }

    public long getStateTotalDuration(String linkshellUrl) {
        long j = -1;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    j = this.mCdeBinder.getStateTotalDuration(linkshellUrl);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "getStateTotalDuration. " + e.toString());
            }
        }
        return j;
    }

    public long getStateDownloadedDuration(String linkshellUrl) {
        long j = -1;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    j = this.mCdeBinder.getStateDownloadedDuration(linkshellUrl);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "getStateDownloadedDuration. " + e.toString());
            }
        }
        return j;
    }

    public double getStateDownloadedPercent(String linkshellUrl) {
        double d = -1.0d;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    d = this.mCdeBinder.getStateDownloadedPercent(linkshellUrl);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "getStateDownloadedPercent. " + e.toString());
            }
        }
        return d;
    }

    public void setChannelSeekPosition(String linkshellUrl, double pos) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            try {
                if (this.mCdeBinder != null) {
                    this.mCdeBinder.setChannelSeekPosition(linkshellUrl, pos);
                }
            } catch (Exception e) {
                LogTool.e(TAG, "setChannelSeekPosition. " + e.toString());
            }
        }
    }

    public String getPlayUrl(String linkshellUrl) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return linkshellUrl;
        }
        return new PlayUrl(getServicePort(), linkshellUrl, "", "ext=m3u8").getPlay();
    }

    public String getPlayUrl(String linkshellUrl, String taskId, String other) {
        return TextUtils.isEmpty(linkshellUrl) ? linkshellUrl : new PlayUrl(getServicePort(), linkshellUrl, taskId, other).getPlay();
    }

    public String getPlayUrlSync(String linkshellUrl) {
        return getPlayUrlSync(linkshellUrl, "ext=m3u8");
    }

    public String getPlayUrlSync(String linkshellUrl, String other) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return "{\"playUrl\":\"\",\"errCode\":-1}";
        }
        StringBuilder stringBuilder = new StringBuilder("{\"playUrl\":\"");
        String playUrl = getPlayUrl(linkshellUrl, "", other);
        stringBuilder.append(playUrl).append("\",\"errCode\":");
        int errCode = -2;
        String json = NetworkUtils.doHttpGet(new StringBuilder(String.valueOf(playUrl)).append("&overLoadProtect=1").toString());
        if (!TextUtils.isEmpty(json)) {
            try {
                errCode = new JSONObject(json).getInt("errCode");
            } catch (JSONException e) {
                LogTool.e(TAG, "getPlayUrlSync. " + e.toString());
            }
        }
        stringBuilder.append(errCode).append("}");
        return stringBuilder.toString();
    }

    public String getStopUrl(String linkshellUrl) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return linkshellUrl;
        }
        return new PlayUrl(getServicePort(), linkshellUrl, "", "").getStop();
    }

    public String getPauseUrl(String linkshellUrl) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return linkshellUrl;
        }
        return new PlayUrl(getServicePort(), linkshellUrl, "", "").getPause();
    }

    public String getResumeUrl(String linkshellUrl) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return linkshellUrl;
        }
        return new PlayUrl(getServicePort(), linkshellUrl, "", "").getResume();
    }

    public String getStatePlayUrl(String linkshellUrl) {
        if (TextUtils.isEmpty(linkshellUrl)) {
            return linkshellUrl;
        }
        return new PlayUrl(getServicePort(), linkshellUrl, "", "cde=1&simple=1&maxDuration=1000").getStatePlay();
    }

    public String getPlayErrorsUrl(String linkshellUrl) {
        return TextUtils.isEmpty(linkshellUrl) ? linkshellUrl : "http://127.0.0.1:" + getServicePort() + "/report/error?enc=base64&url=" + Base64.encodeToString(linkshellUrl.getBytes(), 2);
    }

    public void stopPlay(String linkshellUrl) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                NetworkUtils.doHttpGet(getStopUrl(linkshellUrl), false, 5, 5, 10);
            } else {
                new Thread(new 3(this, linkshellUrl)).start();
            }
        }
    }

    public void pausePlay(String linkshellUrl) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                NetworkUtils.doHttpGet(getPauseUrl(linkshellUrl), false, 5, 5, 10);
            } else {
                new Thread(new 4(this, linkshellUrl)).start();
            }
        }
    }

    public void resumePlay(String linkshellUrl) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                NetworkUtils.doHttpGet(getResumeUrl(linkshellUrl), false, 5, 5, 10);
            } else {
                new Thread(new 5(this, linkshellUrl)).start();
            }
        }
    }

    public void startBuffer(String linkshellUrl) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            String pauseUrl = new PlayUrl(getServicePort(), linkshellUrl, "", "needBuffer=1").getPause();
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                NetworkUtils.doHttpGet(pauseUrl, false, 5, 5, 10);
            } else {
                new Thread(new 6(this, pauseUrl)).start();
            }
        }
    }

    public void stopBuffer(String linkshellUrl) {
        if (!TextUtils.isEmpty(linkshellUrl)) {
            String resumeUrl = new PlayUrl(getServicePort(), linkshellUrl, "", "needBuffer=1").getResume();
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                NetworkUtils.doHttpGet(resumeUrl, false, 5, 5, 10);
            } else {
                new Thread(new 7(this, resumeUrl)).start();
            }
        }
    }

    public long getDownloadSpeed(String linkshellUrl) {
        long j = 0;
        if (!TextUtils.isEmpty(linkshellUrl)) {
            String json = NetworkUtils.doHttpGet(getDownloadSpeedUrl(linkshellUrl), true, 5, 5, 10);
            if (!TextUtils.isEmpty(json)) {
                try {
                    j = new JSONObject(json).getJSONObject("state").getJSONObject("resource").getLong("download_rate");
                } catch (Exception e) {
                    LogTool.e(TAG, "getDownloadSpeed. " + e.toString());
                }
            }
        }
        return j;
    }

    public String getDownloadSpeedUrl(String linkshellUrl) {
        return TextUtils.isEmpty(linkshellUrl) ? linkshellUrl : "http://127.0.0.1:" + getServicePort() + "/state/play?enc=base64&url=" + Base64.encodeToString(linkshellUrl.getBytes(), 2);
    }

    public String getHelpNumber(String phoneNumber) {
        String json = NetworkUtils.doHttpGet(getHelperNumberUrl(phoneNumber), true, 0, 0, 10);
        if (TextUtils.isEmpty(json)) {
            LogTool.i(TAG, "getHelpNumber. data exception.");
            return null;
        }
        try {
            LogTool.i(TAG, "getHelpNumber. service number(%s)", new JSONObject(json).getString("serviceNumber"));
            return new JSONObject(json).getString("serviceNumber");
        } catch (Exception e) {
            LogTool.e(TAG, "getHelpNumber. " + e.toString());
            return null;
        }
    }

    public String getHelperNumberUrl(String phoneNumber) {
        String numberEncode = "";
        if (!TextUtils.isEmpty(phoneNumber)) {
            try {
                numberEncode = URLEncoder.encode(phoneNumber, "UTF-8");
            } catch (Exception e) {
            }
        }
        return "http://127.0.0.1:" + getServicePort() + "/support/open?contact=" + numberEncode;
    }

    public void setOnLibraryUpgradeListener(OnLibraryUpgradeListener listener) {
        this.mOnLibraryUpgradeListener = listener;
    }

    public void setOnStartStatusChangeListener(OnStartStatusChangeListener listener) {
        this.mOnStartStatusChangeListener = listener;
    }

    private void reset() {
        this.mReceivedBroadcast = false;
        this.mConnectedService = false;
        this.mBoundService = false;
        this.mCdeBinder = null;
    }

    public void doNetworkChange() {
        NetworkUtils.detectNetwork(this.mContext);
        if (this.mNeedStartPull && this.mDownloadEngine != null && NetworkUtils.hasNetwork()) {
            this.mNeedStartPull = false;
            this.mDownloadEngine.startPull(this.mNativeVersion, this.mNativeLibMd5, FileHelper.getValueFromAssetsFile(this.mContext, FILE_NAME_CDE, TAG_SERIAL_NUMBER));
        }
        if (this.mConnectedService && this.mCdeBinder != null) {
            try {
                this.mCdeBinder.notifyNetworkChanged(NetworkUtils.getNetworkType(), NetworkUtils.getNetworkName());
            } catch (Exception e) {
                LogTool.e(TAG, "doNetworkChange. " + e.toString());
            }
        }
        if (this.mEncryption != null) {
            this.mEncryption.notifyNetworkChanged();
        }
    }

    private void doCdeStartComplete() {
        if (this.mReceivedBroadcast && this.mConnectedService) {
            callbackCdeStartComplete(this.mCdeStartStatusCode);
            if (this.mManuallyStartUpgradeTag) {
                LogTool.i(TAG, "doCdeStartComplete. manually start the upgrade.");
                try {
                    this.mCdeBinder.startUpgrade();
                } catch (Exception e) {
                    LogTool.e(TAG, "doCdeStartComplete. " + e.toString());
                }
            }
        }
    }

    private void sendBoradcast() {
        Intent intent = new Intent(CdeService.ACTION_CDE_READY);
        intent.putExtra(CdeService.KEY_BROADCAST_FLAG, this.mContext.getPackageName());
        try {
            this.mContext.sendBroadcast(intent);
            LogTool.i(TAG, "sendBoradcast. the first times send ready boradcast, process name(%s)", this.mContext.getPackageName());
        } catch (Exception e) {
            LogTool.w(TAG, "sendBoradcast. the first times send ready boradcast failed, " + e.toString());
            try {
                intent.setFlags(67108864);
                this.mContext.sendBroadcast(intent);
                LogTool.i(TAG, "sendBoradcast. the second times send ready boradcast before boot completed, process name(%s)", this.mContext.getPackageName());
            } catch (Exception e2) {
                LogTool.w(TAG, "sendBoradcast. the second times send ready boradcast failed, " + e2.toString());
            }
        }
    }
}
