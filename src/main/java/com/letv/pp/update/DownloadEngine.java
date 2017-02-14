package com.letv.pp.update;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import com.letv.lemallsdk.util.Constants;
import com.letv.pp.common.TaskEngine;
import com.letv.pp.service.CdeService;
import com.letv.pp.task.ReportTask;
import com.letv.pp.utils.AppIdKeyUtils;
import com.letv.pp.utils.DeviceUtils;
import com.letv.pp.utils.DomainHelper;
import com.letv.pp.utils.FileHelper;
import com.letv.pp.utils.LibraryHelper;
import com.letv.pp.utils.LogTool;
import com.letv.pp.utils.MD5Utils;
import com.letv.pp.utils.NetworkUtils;
import com.letv.pp.utils.ProductUtils;
import com.letv.pp.utils.SPHelper;
import com.letv.pp.utils.StringUtils;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.net.URLEncoder;
import org.json.JSONObject;

public class DownloadEngine extends Thread {
    private static final String ACTION_TYPE_LOAD = "load";
    private static final String ACTION_TYPE_RESULT = "result";
    private static final String ACTION_TYPE_UPGRADE = "upgrade";
    public static final long DELAY_TIME_ABNORMAL = 120000;
    public static final long DELAY_TIME_NETWORK_CHANGE = 10000;
    public static final long DELAY_TIME_NORMAL = 10800000;
    private static final String FORMAT_UPGRADE_URL = "%s&appversion=%s&macaddr=%s";
    private static final int MSG_PULL_COMPLETE = 3;
    private static final int MSG_PULL_LIBRARY = 1;
    private static final int MSG_UPGRADE_COMPLETE = 4;
    private static final int MSG_UPGRADE_LIBRARY = 2;
    private static final String STATUS_CODE_SUCCESS = "A000000";
    private static final String TAG = "DownloadEngine";
    private final String mAppId;
    private final Context mContext;
    private long mDelayTime;
    private final DownloadHandler mDownloadHandler;
    private volatile int mDownloadPercent;
    private final String mLetvRomVersion;
    private String mLocalVersion;
    private Looper mLooper;
    private final MainHandler mMainHandler;
    private String mNativeLibMd5;
    private OnPullCompleteListener mOnPullCompleteListener;
    private OnUpgradeCompleteListener mOnUpgradeCompleteListener;
    private int mPullFailedNum;
    private volatile boolean mPullStopFlag = true;
    private String mPullUrl;
    private String mReportUrl;
    private final String mSaveDir;
    private final boolean mSendBroadcast;
    private boolean mTheFirstTimeUpgrade = true;
    private boolean mUpgradeEnabled = true;
    private int mUpgradeFailedNum;
    private long mUpgradeStartTime;
    private volatile boolean mUpgradeStopFlag = true;
    private String mUpgradeUrl;

    public DownloadEngine(Context context, String appId, String appChannel, boolean sendBroadcast) {
        if (context == null) {
            throw new IllegalArgumentException("Illegal Context argument");
        }
        this.mAppId = appId;
        this.mContext = context;
        this.mSendBroadcast = sendBroadcast;
        this.mSaveDir = LibraryHelper.getLibraryRootPath(context);
        this.mLetvRomVersion = DeviceUtils.getLetvRomVersion();
        this.mLocalVersion = SPHelper.getInstance(this.mContext).getString(SPHelper.KEY_LIB_LOCAL_VERSION);
        initUrl(appChannel);
        start();
        this.mMainHandler = new MainHandler(this, Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper(), this);
        this.mDownloadHandler = new DownloadHandler(this, getLooper(), this);
    }

    private void initUrl(String appChannel) {
        DomainHelper domainHelper = DomainHelper.getInstance();
        this.mReportUrl = domainHelper.getReportUrl();
        String appKey = AppIdKeyUtils.getAppKey(this.mAppId);
        if (StringUtils.isEmpty(appKey)) {
            LogTool.i(TAG, "initUrl. upgrade key is empty.");
            this.mUpgradeEnabled = false;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(domainHelper.getUpgradeUrl());
        stringBuilder.append("appkey=").append(appKey);
        stringBuilder.append("&appid=").append(URLEncoder.encode(this.mAppId));
        if (!StringUtils.isEmpty(appChannel)) {
            stringBuilder.append("&app_channel=").append(URLEncoder.encode(appChannel));
        }
        stringBuilder.append("&devmodel=CMFID").append(URLEncoder.encode(this.mAppId));
        stringBuilder.append("&devmodel2=").append(URLEncoder.encode(Build.MODEL));
        stringBuilder.append("&package_name=").append(URLEncoder.encode(this.mContext.getPackageName()));
        this.mUpgradeUrl = stringBuilder.toString().trim();
    }

    public void run() {
        Looper.prepare();
        synchronized (this) {
            this.mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(10);
        Looper.loop();
    }

    public boolean isUpgradeEnabled() {
        return this.mUpgradeEnabled;
    }

    public void startUpgrade(long delayMillis) {
        if (this.mUpgradeEnabled && this.mUpgradeStopFlag) {
            LogTool.i(TAG, "startUpgrade. delay time(%s)", StringUtils.formatTime2(delayMillis));
            this.mUpgradeStopFlag = false;
            this.mDownloadHandler.sendEmptyMessageDelayed(2, delayMillis);
        }
    }

    public void stopUpgrade() {
        if (this.mUpgradeEnabled && !this.mUpgradeStopFlag) {
            LogTool.i(TAG, "stopUpgrade.");
            this.mUpgradeEnabled = true;
            this.mDownloadHandler.removeMessages(2);
        }
    }

    public void startPull(String libraryVersion, String nativeLibMd5, String serialNumber) {
        if (this.mPullStopFlag) {
            LogTool.i(TAG, "startPull.");
            this.mPullStopFlag = false;
            this.mNativeLibMd5 = nativeLibMd5;
            this.mPullUrl = DomainHelper.getInstance().getPullUrl(libraryVersion, serialNumber);
            this.mDownloadHandler.sendEmptyMessage(1);
        }
    }

    public void stopPull() {
        if (!this.mPullStopFlag) {
            LogTool.i(TAG, "stopPull.");
            this.mPullStopFlag = true;
            this.mDownloadHandler.removeMessages(1);
        }
    }

    public boolean quit() {
        Looper looper = getLooper();
        if (looper == null) {
            return false;
        }
        looper.quit();
        return true;
    }

    private Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && this.mLooper == null) {
                try {
                    wait();
                } catch (Exception e) {
                }
            }
        }
        return this.mLooper;
    }

    private void handlePullMsg() {
        if (pullLibrary()) {
            stopPull();
            this.mPullFailedNum = 0;
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(3, Boolean.valueOf(true)));
            return;
        }
        Object[] objArr = new Object[1];
        int i = this.mPullFailedNum + 1;
        this.mPullFailedNum = i;
        objArr[0] = Integer.valueOf(i);
        LogTool.i(TAG, "handlePullMsg. the number of pull remote failed(%s)", objArr);
        int pullDomainlistSize = DomainHelper.getInstance().getPullDomainListSize();
        if (this.mPullFailedNum == pullDomainlistSize * 3) {
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(3, Boolean.valueOf(false)));
        }
        if (!this.mPullStopFlag) {
            this.mPullUrl = DomainHelper.getInstance().replaceUrlForPull(this.mPullUrl, this.mPullFailedNum);
            this.mDownloadHandler.sendEmptyMessageDelayed(1, this.mPullFailedNum >= pullDomainlistSize * 3 ? 120000 : 0);
        }
    }

    private boolean pullLibrary() {
        try {
            long startTime = System.nanoTime();
            LogTool.i(TAG, "pullLibrary. download the gz file start, url(%s)", this.mPullUrl);
            byte[] byteData = downloadFile(this.mPullUrl, true);
            if (byteData == null) {
                LogTool.i(TAG, "pullLibrary. download the gz file failed.");
                reportCmfAction(ACTION_TYPE_LOAD, null, -2);
                return false;
            }
            LogTool.i(TAG, "pullLibrary. download the gz file successfully, spend time(%s)", StringUtils.formatTime((double) (System.nanoTime() - startTime)));
            startTime = System.nanoTime();
            String soReadMd5 = MD5Utils.getByteMD5(byteData);
            if (StringUtils.isEmpty(this.mNativeLibMd5) || !this.mNativeLibMd5.equalsIgnoreCase(soReadMd5)) {
                LogTool.i(TAG, "pullLibrary. check the so file md5 error, read md5(%s), native md5(%s)", soReadMd5, this.mNativeLibMd5);
                reportCmfAction(ACTION_TYPE_LOAD, null, -6);
                return false;
            } else if (FileHelper.writeFile(byteData, this.mSaveDir + LibraryHelper.getLibraryNativeName(LibraryHelper.LIB_NAME_CDE))) {
                LogTool.i(TAG, "pullLibrary. sava the so file to local successfully, spend time(%s)", StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                reportCmfAction(ACTION_TYPE_LOAD, null, 0);
                return true;
            } else {
                LogTool.i(TAG, "pullLibrary. sava the so file to local failed.");
                reportCmfAction(ACTION_TYPE_LOAD, null, -7);
                return false;
            }
        } finally {
        }
    }

    private void handleUpgradeMsg() {
        long j = 120000;
        boolean result = upgradeLibrary();
        this.mTheFirstTimeUpgrade = false;
        if (result) {
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(4, Boolean.valueOf(true)));
            if (!DomainHelper.getInstance().isTestUpgrade()) {
                j = 10800000;
            }
            this.mDelayTime = j;
            this.mUpgradeFailedNum = 0;
            if (!this.mUpgradeStopFlag) {
                LogTool.i(TAG, "handleUpgradeMsg. delay time(%s)", StringUtils.formatTime2(this.mDelayTime));
                this.mDownloadHandler.sendEmptyMessageDelayed(2, this.mDelayTime);
                return;
            }
            return;
        }
        Object[] objArr = new Object[1];
        int i = this.mUpgradeFailedNum + 1;
        this.mUpgradeFailedNum = i;
        objArr[0] = Integer.valueOf(i);
        LogTool.i(TAG, "handleUpgradeMsg. the number of upgrade failed(%s)", objArr);
        this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(4, Boolean.valueOf(false)));
        if (!DomainHelper.getInstance().isTestUpgrade() && this.mUpgradeFailedNum >= 5) {
            j = 10800000;
        }
        this.mDelayTime = j;
        if (!this.mUpgradeStopFlag) {
            LogTool.i(TAG, "handleUpgradeMsg. delay time(%s)", StringUtils.formatTime2(this.mDelayTime));
            this.mDownloadHandler.sendEmptyMessageDelayed(2, this.mDelayTime);
        }
    }

    private boolean upgradeLibrary() {
        this.mUpgradeStartTime = System.currentTimeMillis();
        long startTime = System.nanoTime();
        LogTool.i(TAG, "upgradeLibrary. upgrade request start, url(%s)", getUpgradeUrl());
        String json = NetworkUtils.doHttpGet(url, true, 10, 10, 30);
        LogTool.i(TAG, "upgradeLibrary. upgrade request end, spend time(%s), json(%s)", StringUtils.formatTime((double) (System.nanoTime() - startTime)), json);
        if (StringUtils.isEmpty(json)) {
            reportCmfAction(ACTION_TYPE_UPGRADE, null, -1);
            return false;
        } else if (!this.mTheFirstTimeUpgrade || System.currentTimeMillis() - this.mUpgradeStartTime < 20000) {
            String upgradeVersion = "";
            boolean isSendStartBroadcast = false;
            try {
                JSONObject jsonObject = new JSONObject(json);
                if ("A000000".equals(jsonObject.getString("code"))) {
                    jsonObject = jsonObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                    if (jsonObject.getInt(ACTION_TYPE_UPGRADE) == 0) {
                        LogTool.i(TAG, "upgradeLibrary. don't need to upgrade, upgrade value(%s)", Integer.valueOf(jsonObject.getInt(ACTION_TYPE_UPGRADE)));
                        reportCmfAction(ACTION_TYPE_UPGRADE, this.mLocalVersion, 1);
                        return true;
                    }
                    upgradeVersion = jsonObject.optString("version");
                    reportCmfAction(ACTION_TYPE_UPGRADE, upgradeVersion, 0);
                    if (jsonObject.getInt("uptype") == 1) {
                        LogTool.i(TAG, "upgradeLibrary. forced to upgrade, start to download data.");
                        if (this.mSendBroadcast) {
                            isSendStartBroadcast = sendBroadcast(true, true);
                        }
                    } else if (NetworkUtils.noPermissionNetwork() || NetworkUtils.isMobileNetwork()) {
                        LogTool.i(TAG, "upgradeLibrary. is not forced to upgrade, the current mobile network, not to download.");
                        reportCmfAction(ACTION_TYPE_RESULT, upgradeVersion, -8);
                        return true;
                    }
                    startTime = System.nanoTime();
                    LogTool.i(TAG, "upgradeLibrary. download the gz file start, url(%s)", DomainHelper.getInstance().replaceUrlForG3(jsonObject.getString("upurl")));
                    byte[] byteData = downloadFile(downloadUrl, false);
                    if (byteData == null) {
                        LogTool.i(TAG, "upgradeLibrary. download the gz file failed.");
                        reportCmfAction(ACTION_TYPE_RESULT, upgradeVersion, -2);
                        if (isSendStartBroadcast) {
                            sendBroadcast(false, false);
                        }
                        return false;
                    }
                    LogTool.i(TAG, "upgradeLibrary. download the gz file successfully, spend time(%s)", StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                    startTime = System.nanoTime();
                    String soReadMd5 = MD5Utils.getByteMD5(byteData);
                    if (!jsonObject.optString("somd5").equalsIgnoreCase(soReadMd5)) {
                        LogTool.i(TAG, "upgradeLibrary. check the so file md5 error, read md5(%s), server md5(%s)", soReadMd5, jsonObject.optString("somd5"));
                        reportCmfAction(ACTION_TYPE_RESULT, upgradeVersion, -6);
                        if (isSendStartBroadcast) {
                            sendBroadcast(false, false);
                        }
                        return false;
                    } else if (FileHelper.writeFile(byteData, this.mSaveDir + LibraryHelper.getLibraryUpgradeName(LibraryHelper.LIB_NAME_CDE))) {
                        reportCmfAction(ACTION_TYPE_RESULT, upgradeVersion, 0);
                        this.mLocalVersion = upgradeVersion;
                        SPHelper.getInstance(this.mContext).putStringAndCommit(SPHelper.KEY_LIB_LOCAL_VERSION, upgradeVersion);
                        LogTool.i(TAG, "upgradeLibrary. sava the so file to local successfully, spend time(%s)", StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                        if (isSendStartBroadcast) {
                            sendBroadcast(false, true);
                        }
                        return true;
                    } else {
                        LogTool.i(TAG, "upgradeLibrary. sava the so file to local failed.");
                        reportCmfAction(ACTION_TYPE_RESULT, upgradeVersion, -7);
                        if (isSendStartBroadcast) {
                            sendBroadcast(false, false);
                        }
                        return false;
                    }
                }
                LogTool.i(TAG, "upgradeLibrary. abnormal status code(%s)", jsonObject.getString("code"));
                boolean z = ACTION_TYPE_UPGRADE;
                reportCmfAction(z, null, -1);
                return z;
            } catch (Throwable e) {
                LogTool.e(TAG, "", e);
                String str = ACTION_TYPE_RESULT;
                if (null != null) {
                    sendBroadcast(str, false);
                }
                return false;
            } finally {
            }
        } else {
            LogTool.i(TAG, "upgradeLibrary. upgrade request timeout.");
            reportCmfAction(ACTION_TYPE_UPGRADE, null, -1);
            return false;
        }
    }

    private void reportCmfAction(String action, String serverVersion, int result) {
        if (NetworkUtils.hasNetwork()) {
            String mac;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("act=").append(action);
            stringBuilder.append("&time=").append(System.currentTimeMillis());
            stringBuilder.append("&appid=").append(this.mAppId);
            String imei = DeviceUtils.getIMEI(this.mContext);
            if (!StringUtils.isEmpty(imei)) {
                stringBuilder.append("&did=").append(imei);
            }
            if (NetworkUtils.isEthernetNetwork()) {
                mac = NetworkUtils.getEthMac(NetworkUtils.DELIMITER_COLON);
                String wifiMac = NetworkUtils.getWlanMac(NetworkUtils.DELIMITER_COLON);
                if (!StringUtils.isEmpty(wifiMac)) {
                    mac = new StringBuilder(String.valueOf(mac)).append("*").append(wifiMac).toString();
                }
            } else {
                mac = NetworkUtils.getWlanMac(NetworkUtils.DELIMITER_COLON);
                String ethMac = NetworkUtils.getEthMac(NetworkUtils.DELIMITER_COLON);
                if (!StringUtils.isEmpty(ethMac)) {
                    mac = new StringBuilder(String.valueOf(mac)).append("*").append(ethMac).toString();
                }
            }
            if (!StringUtils.isEmpty(mac)) {
                stringBuilder.append("&mac=").append(mac);
            }
            stringBuilder.append("&nt=").append(NetworkUtils.getNetworkName());
            stringBuilder.append("&dt=").append(ProductUtils.getProductName());
            if (!StringUtils.isEmpty(this.mLocalVersion)) {
                stringBuilder.append("&cdev=cde.").append(this.mLocalVersion);
            }
            if (!ACTION_TYPE_UPGRADE.equals(action)) {
                stringBuilder.append("&um=").append(1);
            }
            if (!(ACTION_TYPE_LOAD.equals(action) || StringUtils.isEmpty(serverVersion))) {
                stringBuilder.append("&scv=cde.").append(serverVersion);
            }
            if (!StringUtils.isEmpty(this.mLetvRomVersion)) {
                stringBuilder.append("&romv=").append(this.mLetvRomVersion);
            }
            stringBuilder.append("&result=").append(result);
            TaskEngine.getInstance().submit(new ReportTask(this.mReportUrl, stringBuilder.toString()));
        }
    }

    private String getUpgradeUrl() {
        String mac = NetworkUtils.getEthMac(NetworkUtils.DELIMITER_COLON);
        if (StringUtils.isEmpty(mac)) {
            mac = NetworkUtils.getWlanMac(NetworkUtils.DELIMITER_COLON);
        }
        if (mac == null) {
            mac = "";
        }
        return String.format(FORMAT_UPGRADE_URL, new Object[]{this.mUpgradeUrl, this.mLocalVersion, URLEncoder.encode(mac)});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] downloadFile(java.lang.String r21, boolean r22) {
        /*
        r20 = this;
        r13 = 0;
        r0 = r20;
        r0.mDownloadPercent = r13;
        r2 = 0;
        r7 = 0;
        r10 = 0;
        r6 = 0;
        r13 = 5;
        r14 = 5;
        r0 = r21;
        r6 = com.letv.pp.utils.NetworkUtils.getHttpURLConnection(r0, r13, r14);	 Catch:{ Exception -> 0x01e6 }
        if (r22 == 0) goto L_0x0044;
    L_0x0013:
        r13 = "http://so.cde";
        r0 = r21;
        r13 = r0.startsWith(r13);	 Catch:{ Exception -> 0x01e6 }
        if (r13 != 0) goto L_0x0044;
    L_0x001d:
        r13 = "DownloadEngine";
        r14 = "downloadFile. set host(%s), url(%s)";
        r15 = 2;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x01e6 }
        r16 = 0;
        r17 = com.letv.pp.utils.DomainHelper.getInstance();	 Catch:{ Exception -> 0x01e6 }
        r17 = r17.getPullDomain();	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 1;
        r15[r16] = r21;	 Catch:{ Exception -> 0x01e6 }
        com.letv.pp.utils.LogTool.i(r13, r14, r15);	 Catch:{ Exception -> 0x01e6 }
        r13 = "Host";
        r14 = com.letv.pp.utils.DomainHelper.getInstance();	 Catch:{ Exception -> 0x01e6 }
        r14 = r14.getPullDomain();	 Catch:{ Exception -> 0x01e6 }
        r6.setRequestProperty(r13, r14);	 Catch:{ Exception -> 0x01e6 }
    L_0x0044:
        r12 = r6.getResponseCode();	 Catch:{ Exception -> 0x01e6 }
        r5 = r6.getContentLength();	 Catch:{ Exception -> 0x01e6 }
        r13 = "DownloadEngine";
        r14 = "downloadFile. server(%s:%s), response code(%s), content length(%s), content type(%s), content encoding(%s), url(%s)";
        r15 = 7;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x01e6 }
        r16 = 0;
        r17 = r6.getURL();	 Catch:{ Exception -> 0x01e6 }
        r17 = r17.getHost();	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 1;
        r17 = r6.getURL();	 Catch:{ Exception -> 0x01e6 }
        r17 = r17.getDefaultPort();	 Catch:{ Exception -> 0x01e6 }
        r17 = java.lang.Integer.valueOf(r17);	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 2;
        r17 = java.lang.Integer.valueOf(r12);	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 3;
        r17 = new java.text.DecimalFormat;	 Catch:{ Exception -> 0x01e6 }
        r18 = "##.##";
        r17.<init>(r18);	 Catch:{ Exception -> 0x01e6 }
        r0 = (double) r5;	 Catch:{ Exception -> 0x01e6 }
        r18 = r0;
        r17 = com.letv.pp.utils.StringUtils.formatSize(r17, r18);	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 4;
        r17 = r6.getContentType();	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 5;
        r17 = r6.getContentEncoding();	 Catch:{ Exception -> 0x01e6 }
        r15[r16] = r17;	 Catch:{ Exception -> 0x01e6 }
        r16 = 6;
        r15[r16] = r21;	 Catch:{ Exception -> 0x01e6 }
        com.letv.pp.utils.LogTool.i(r13, r14, r15);	 Catch:{ Exception -> 0x01e6 }
        r13 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r12 == r13) goto L_0x00d0;
    L_0x00a4:
        r13 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;
        r13.returnBuf(r2);
        com.letv.pp.utils.IOUtils.closeSilently(r10);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x00b4;
    L_0x00b1:
        r6.disconnect();	 Catch:{ Throwable -> 0x00b6 }
    L_0x00b4:
        r13 = 0;
    L_0x00b5:
        return r13;
    L_0x00b6:
        r4 = move-exception;
        r13 = "DownloadEngine";
        r14 = new java.lang.StringBuilder;
        r15 = "downloadFile. ";
        r14.<init>(r15);
        r15 = r4.toString();
        r14 = r14.append(r15);
        r14 = r14.toString();
        com.letv.pp.utils.LogTool.e(r13, r14);
        goto L_0x00b4;
    L_0x00d0:
        r8 = new java.util.zip.GZIPInputStream;	 Catch:{ Exception -> 0x01e6 }
        r13 = r6.getInputStream();	 Catch:{ Exception -> 0x01e6 }
        r8.<init>(r13);	 Catch:{ Exception -> 0x01e6 }
        r11 = new com.letv.pp.utils.NetworkUtils$PoolingByteArrayOutputStream;	 Catch:{ Exception -> 0x0253, all -> 0x024c }
        r13 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;	 Catch:{ Exception -> 0x0253, all -> 0x024c }
        r11.<init>(r13);	 Catch:{ Exception -> 0x0253, all -> 0x024c }
        r13 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r14 = 10240; // 0x2800 float:1.4349E-41 double:5.059E-320;
        r2 = r13.getBuf(r14);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r9 = -1;
        r3 = 0;
    L_0x00ea:
        r9 = r8.read(r2);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = -1;
        if (r9 != r13) goto L_0x010e;
    L_0x00f1:
        r13 = 100;
        r0 = r20;
        r0.mDownloadPercent = r13;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = r11.toByteArray();	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r14 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;
        r14.returnBuf(r2);
        com.letv.pp.utils.IOUtils.closeSilently(r11);
        com.letv.pp.utils.IOUtils.closeSilently(r8);
        if (r6 == 0) goto L_0x010b;
    L_0x0108:
        r6.disconnect();	 Catch:{ Throwable -> 0x01cb }
    L_0x010b:
        r10 = r11;
        r7 = r8;
        goto L_0x00b5;
    L_0x010e:
        r13 = 0;
        r11.write(r2, r13, r9);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r3 = r3 + r9;
        if (r5 <= 0) goto L_0x0144;
    L_0x0115:
        r13 = r3 * 100;
        r13 = r13 / r5;
        r0 = r20;
        r0.mDownloadPercent = r13;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = "DownloadEngine";
        r14 = "downloadFile. download size(%s), file size(%s), download percent(%s)";
        r15 = 3;
        r15 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r16 = 0;
        r17 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r16 = 1;
        r17 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r16 = 2;
        r0 = r20;
        r0 = r0.mDownloadPercent;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r17 = r0;
        r17 = java.lang.Integer.valueOf(r17);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r15[r16] = r17;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        com.letv.pp.utils.LogTool.d(r13, r14, r15);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
    L_0x0144:
        if (r3 == r5) goto L_0x00ea;
    L_0x0146:
        if (r22 == 0) goto L_0x014e;
    L_0x0148:
        r0 = r20;
        r13 = r0.mPullStopFlag;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        if (r13 != 0) goto L_0x016e;
    L_0x014e:
        if (r22 != 0) goto L_0x00ea;
    L_0x0150:
        r0 = r20;
        r13 = r0.mUpgradeStopFlag;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        if (r13 != 0) goto L_0x016e;
    L_0x0156:
        r0 = r20;
        r13 = r0.mTheFirstTimeUpgrade;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        if (r13 == 0) goto L_0x00ea;
    L_0x015c:
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r0 = r20;
        r0 = r0.mUpgradeStartTime;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r16 = r0;
        r14 = r14 - r16;
        r16 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r13 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r13 < 0) goto L_0x00ea;
    L_0x016e:
        r14 = "DownloadEngine";
        r15 = "downloadFile. task %s, url(%s)";
        r13 = 2;
        r0 = new java.lang.Object[r13];	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r16 = r0;
        r17 = 0;
        if (r22 == 0) goto L_0x0181;
    L_0x017b:
        r0 = r20;
        r13 = r0.mPullStopFlag;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        if (r13 != 0) goto L_0x0189;
    L_0x0181:
        if (r22 != 0) goto L_0x01ad;
    L_0x0183:
        r0 = r20;
        r13 = r0.mUpgradeStopFlag;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        if (r13 == 0) goto L_0x01ad;
    L_0x0189:
        r13 = "canceled";
    L_0x018b:
        r16[r17] = r13;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = 1;
        r16[r13] = r21;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        com.letv.pp.utils.LogTool.w(r14, r15, r16);	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = 0;
        r0 = r20;
        r0.mDownloadPercent = r13;	 Catch:{ Exception -> 0x0256, all -> 0x024f }
        r13 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;
        r13.returnBuf(r2);
        com.letv.pp.utils.IOUtils.closeSilently(r11);
        com.letv.pp.utils.IOUtils.closeSilently(r8);
        if (r6 == 0) goto L_0x01a8;
    L_0x01a5:
        r6.disconnect();	 Catch:{ Throwable -> 0x01b1 }
    L_0x01a8:
        r13 = 0;
        r10 = r11;
        r7 = r8;
        goto L_0x00b5;
    L_0x01ad:
        r13 = "timeout";
        goto L_0x018b;
    L_0x01b1:
        r4 = move-exception;
        r13 = "DownloadEngine";
        r14 = new java.lang.StringBuilder;
        r15 = "downloadFile. ";
        r14.<init>(r15);
        r15 = r4.toString();
        r14 = r14.append(r15);
        r14 = r14.toString();
        com.letv.pp.utils.LogTool.e(r13, r14);
        goto L_0x01a8;
    L_0x01cb:
        r4 = move-exception;
        r14 = "DownloadEngine";
        r15 = new java.lang.StringBuilder;
        r16 = "downloadFile. ";
        r15.<init>(r16);
        r16 = r4.toString();
        r15 = r15.append(r16);
        r15 = r15.toString();
        com.letv.pp.utils.LogTool.e(r14, r15);
        goto L_0x010b;
    L_0x01e6:
        r4 = move-exception;
    L_0x01e7:
        r13 = "DownloadEngine";
        r14 = "";
        com.letv.pp.utils.LogTool.e(r13, r14, r4);	 Catch:{ all -> 0x0220 }
        r13 = 0;
        r0 = r20;
        r0.mDownloadPercent = r13;	 Catch:{ all -> 0x0220 }
        r13 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;
        r13.returnBuf(r2);
        com.letv.pp.utils.IOUtils.closeSilently(r10);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x0203;
    L_0x0200:
        r6.disconnect();	 Catch:{ Throwable -> 0x0206 }
    L_0x0203:
        r13 = 0;
        goto L_0x00b5;
    L_0x0206:
        r4 = move-exception;
        r13 = "DownloadEngine";
        r14 = new java.lang.StringBuilder;
        r15 = "downloadFile. ";
        r14.<init>(r15);
        r15 = r4.toString();
        r14 = r14.append(r15);
        r14 = r14.toString();
        com.letv.pp.utils.LogTool.e(r13, r14);
        goto L_0x0203;
    L_0x0220:
        r13 = move-exception;
    L_0x0221:
        r14 = com.letv.pp.utils.NetworkUtils.sByteArrayPool;
        r14.returnBuf(r2);
        com.letv.pp.utils.IOUtils.closeSilently(r10);
        com.letv.pp.utils.IOUtils.closeSilently(r7);
        if (r6 == 0) goto L_0x0231;
    L_0x022e:
        r6.disconnect();	 Catch:{ Throwable -> 0x0232 }
    L_0x0231:
        throw r13;
    L_0x0232:
        r4 = move-exception;
        r14 = "DownloadEngine";
        r15 = new java.lang.StringBuilder;
        r16 = "downloadFile. ";
        r15.<init>(r16);
        r16 = r4.toString();
        r15 = r15.append(r16);
        r15 = r15.toString();
        com.letv.pp.utils.LogTool.e(r14, r15);
        goto L_0x0231;
    L_0x024c:
        r13 = move-exception;
        r7 = r8;
        goto L_0x0221;
    L_0x024f:
        r13 = move-exception;
        r10 = r11;
        r7 = r8;
        goto L_0x0221;
    L_0x0253:
        r4 = move-exception;
        r7 = r8;
        goto L_0x01e7;
    L_0x0256:
        r4 = move-exception;
        r10 = r11;
        r7 = r8;
        goto L_0x01e7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.pp.update.DownloadEngine.downloadFile(java.lang.String, boolean):byte[]");
    }

    public int getDownloadPercentage() {
        return this.mDownloadPercent;
    }

    private boolean sendBroadcast(boolean start, boolean result) {
        Intent intent;
        if (start) {
            LogTool.i(TAG, "sendBroadcast. send upgrade start boradcast, process name(%s)", this.mContext.getPackageName());
            intent = new Intent("com.letv.pp.action.UPGRADE_START");
        } else {
            String str = TAG;
            String str2 = "sendBroadcast. send upgrade end boradcast, process name(%s), upgrade result(%s)";
            Object[] objArr = new Object[2];
            objArr[0] = this.mContext.getPackageName();
            objArr[1] = result ? "successfully" : Constants.CALLBACK_FAILD;
            LogTool.i(str, str2, objArr);
            intent = new Intent("com.letv.pp.UPGRADE_END");
            intent.putExtra("upgrade_result", result);
        }
        intent.putExtra(CdeService.KEY_BROADCAST_FLAG, this.mContext.getPackageName());
        try {
            this.mContext.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            LogTool.e(TAG, "sendBroadcast. " + e.toString());
            return false;
        }
    }

    public void setTheFirstTimeUpgrade(boolean first) {
        this.mTheFirstTimeUpgrade = first;
    }

    public void setOnPullCompleteListener(OnPullCompleteListener listener) {
        this.mOnPullCompleteListener = listener;
    }

    public void setOnUpgradeCompleteListener(OnUpgradeCompleteListener listener) {
        this.mOnUpgradeCompleteListener = listener;
    }
}
