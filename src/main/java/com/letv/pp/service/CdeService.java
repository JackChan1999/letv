package com.letv.pp.service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.letv.lemallsdk.util.Constants;
import com.letv.pp.service.ICdeBinder.Stub;
import com.letv.pp.update.DownloadEngine;
import com.letv.pp.utils.ContextUtils;
import com.letv.pp.utils.LogTool;
import com.letv.pp.utils.NetworkUtils;
import com.letv.pp.utils.SPHelper;
import com.letv.pp.utils.StringUtils;
import com.letv.pp.utils.ZipUtils;

public class CdeService extends Service {
    public static final String ACTION_CDE_READY = "com.letv.pp.action.cde_ready";
    private static final String DEFAULT_PORT = "6990";
    public static final String KEY_ACTIVITY_CLASS = "activity_class";
    public static final String KEY_AUTO_START_UPGRADE = "auto_start_upgrade";
    public static final String KEY_BROADCAST_FLAG = "broadcast_flag";
    public static final String KEY_NOTIFACION_CONTENTTEXT = "notifacion_contentText";
    public static final String KEY_NOTIFACION_CONTENTTITLE = "notifacion_contentTitle";
    public static final String KEY_NOTIFACION_ICON = "notifacion_icon";
    public static final String KEY_START_AFTER_UPGRADE = "start_after_upgrade";
    public static final String KEY_START_PARAMS = "start_params";
    private static final int MSG_OPEN_UPGRADE_CHECK = 3;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final String TAG = "CdeService";
    private String mAppChannel;
    private String mAppId;
    private final Stub mCdeBinder = new CdeBinder();
    private volatile DownloadEngine mDownloadEngine;
    private boolean mIndependentProcess;
    private volatile LeService mLeService;
    private volatile boolean mLinkShellStarted;
    private volatile boolean mManualStartUpgradeTag;
    private volatile ServiceHandler mServiceHandler;
    private volatile Looper mServiceLooper;
    private boolean mStartAfterUpgrade;

    private final class CdeBinder extends Stub {
        private CdeBinder() {
        }

        public boolean isCdeReady() throws RemoteException {
            return CdeService.this.mLeService != null && CdeService.this.mLeService.getCdePort() > 0;
        }

        public String getCdeVersion() throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getCdeVersion() : null;
        }

        public long getCdePort() throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getCdePort() : 0;
        }

        public String getCacheUrlWithData(String data, String ext, String g3Url, String other) throws RemoteException {
            if (CdeService.this.mLeService == null) {
                return null;
            }
            try {
                long startTime = System.currentTimeMillis();
                String uncompressData = ZipUtils.uncompress(data);
                LogTool.i(CdeService.TAG, "getCacheUrlWithData. uncompress data spend time(%s ms)", Long.valueOf(System.currentTimeMillis() - startTime));
                if (TextUtils.isEmpty(uncompressData)) {
                    return null;
                }
                String key = String.valueOf(System.currentTimeMillis());
                if (!CdeService.this.mLeService.setKeyDataCache(key, uncompressData)) {
                    return null;
                }
                String params = "";
                if (g3Url != null && g3Url.length() > 0) {
                    int startPos = g3Url.indexOf("?");
                    int endPos = startPos >= 0 ? g3Url.indexOf("#", startPos) : g3Url.indexOf("#");
                    if (startPos >= 0) {
                        params = endPos > startPos ? g3Url.substring(startPos + 1, endPos) : g3Url.substring(startPos + 1);
                    }
                    if (params != null) {
                        params = params.replace("?key=", "?oldkey=").replace("&key=", "&oldkey=").replace("?stream_id=", "?oldstream_id=").replace("&stream_id=", "&oldstream_id=");
                    }
                }
                String separator1 = (TextUtils.isEmpty(params) && TextUtils.isEmpty(other)) ? "" : "&";
                String separator = (TextUtils.isEmpty(separator1) || TextUtils.isEmpty(params) || TextUtils.isEmpty(other)) ? "" : "&";
                if (other == null) {
                    other = "";
                }
                String str = "http://127.0.0.1:%d/play/caches/%s.%s?key=%s%s%s%s%s";
                Object[] objArr = new Object[8];
                objArr[0] = Long.valueOf(CdeService.this.mLeService.getCdePort());
                objArr[1] = key;
                if (TextUtils.isEmpty(ext)) {
                    ext = "m3u8";
                }
                objArr[2] = ext;
                objArr[3] = key;
                objArr[4] = separator1;
                objArr[5] = params;
                objArr[6] = separator;
                objArr[7] = other;
                return String.format(str, objArr);
            } catch (Throwable t) {
                LogTool.e(CdeService.TAG, "getCacheUrlWithData. " + t.toString());
                return null;
            }
        }

        public long getStateLastReceiveSpeed(String url) throws RemoteException {
            return CdeService.this.mLeService == null ? CdeService.this.mLeService.getStateLastReceiveSpeed(url) : -2;
        }

        public long getStateUrgentReceiveSpeed(String url) throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getStateUrgentReceiveSpeed(url) : -2;
        }

        public long getStateTotalDuration(String url) throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getStateTotalDuration(url) : -2;
        }

        public long getStateDownloadedDuration(String url) throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getStateDownloadedDuration(url) : -2;
        }

        public double getStateDownloadedPercent(String url) throws RemoteException {
            return CdeService.this.mLeService != null ? CdeService.this.mLeService.getStateDownloadedPercent(url) : -2.0d;
        }

        public void setChannelSeekPosition(String url, double pos) throws RemoteException {
            if (CdeService.this.mLeService != null) {
                CdeService.this.mLeService.setChannelSeekPosition(url, pos);
            }
        }

        public int getUpgradePercentage() throws RemoteException {
            return CdeService.this.mDownloadEngine != null ? CdeService.this.mDownloadEngine.getDownloadPercentage() : 0;
        }

        public void startUpgrade() throws RemoteException {
            if (!CdeService.this.mManualStartUpgradeTag) {
                CdeService.this.mManualStartUpgradeTag = true;
                CdeService.this.sendMsg(null, -1, 3);
            }
        }

        public void notifyNetworkChanged(int networkType, String networkName) throws RemoteException {
            if (CdeService.this.mIndependentProcess) {
                NetworkUtils.setNetwork(networkType, networkName);
                CdeService.this.notifyLinkShellNetworkChanged();
            }
            if (CdeService.this.mLeService != null) {
                CdeService.this.mLeService.notifyNetworkChanged();
            }
            if (CdeService.this.mDownloadEngine == null) {
                return;
            }
            if (NetworkUtils.hasNetwork()) {
                CdeService.this.mDownloadEngine.startUpgrade(10000);
            } else {
                CdeService.this.mDownloadEngine.stopUpgrade();
            }
        }
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CdeService.this.start((Intent) msg.obj);
                    return;
                case 2:
                    CdeService.this.stop();
                    return;
                case 3:
                    CdeService.this.startUpgrade();
                    return;
                default:
                    return;
            }
        }
    }

    private native int getTime();

    private native String getURLFromLinkShell(String str);

    private native String getVersion();

    private native int initLinkShell();

    private native int setEnv(String str, String str2);

    public void onCreate() {
        boolean z;
        super.onCreate();
        String processName = ContextUtils.getProcessName((Context) this, Process.myPid());
        if (getPackageName().equals(processName)) {
            z = false;
        } else {
            z = true;
        }
        this.mIndependentProcess = z;
        if (z) {
            String logLevel = SPHelper.getInstance(this).getString(SPHelper.KEY_LOG_LEVEL);
            if (StringUtils.isNumeric(logLevel)) {
                LogTool.setLogLevel(Integer.parseInt(logLevel));
            }
        }
        LogTool.i(TAG, "onCreate. process name(%s)", processName);
        HandlerThread thread = new HandlerThread("CdeService[" + processName + "]");
        thread.start();
        this.mServiceLooper = thread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, 1);
        LogTool.i(TAG, "onStart. intent(%s)", intent);
        sendMsg(intent, startId, 1);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogTool.i(TAG, "onStartCommand. intent(%s)", intent);
        sendMsg(intent, startId, 1);
        return 1;
    }

    public IBinder onBind(Intent intent) {
        LogTool.i(TAG, "onBind. intent(%s)", intent);
        sendMsg(intent, -1, 1);
        return this.mCdeBinder;
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogTool.i(TAG, "onRebind. intent(%s)", intent);
    }

    public boolean onUnbind(Intent intent) {
        LogTool.i(TAG, "onUnbind. intent(%s)", intent);
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        LogTool.i(TAG, "onDestroy.");
        stopForeground(true);
        if (this.mIndependentProcess) {
            try {
                Process.killProcess(Process.myPid());
                return;
            } catch (Throwable th) {
                return;
            }
        }
        sendMsg(null, -1, 2);
    }

    private void sendMsg(Intent intent, int startId, int msgWhat) {
        if (intent == null) {
            LogTool.i(TAG, "sendMsg. Intent is null.");
            return;
        }
        Intent data = new Intent();
        data.putExtra(KEY_START_PARAMS, intent.getStringExtra(KEY_START_PARAMS));
        data.putExtra(KEY_AUTO_START_UPGRADE, intent.getBooleanExtra(KEY_AUTO_START_UPGRADE, true));
        data.putExtra(KEY_START_AFTER_UPGRADE, intent.getBooleanExtra(KEY_START_AFTER_UPGRADE, true));
        Class<? extends Activity> clazz = (Class) intent.getSerializableExtra(KEY_ACTIVITY_CLASS);
        if (clazz != null) {
            data.putExtra(KEY_ACTIVITY_CLASS, clazz);
            data.putExtra(KEY_NOTIFACION_ICON, intent.getIntExtra(KEY_NOTIFACION_ICON, 17301540));
            data.putExtra(KEY_NOTIFACION_CONTENTTITLE, intent.getStringExtra(KEY_NOTIFACION_CONTENTTITLE));
            data.putExtra(KEY_NOTIFACION_CONTENTTEXT, intent.getStringExtra(KEY_NOTIFACION_CONTENTTEXT));
        }
        Message msg = this.mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = data;
        msg.what = msgWhat;
        this.mServiceHandler.sendMessage(msg);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void start(android.content.Intent r19) {
        /*
        r18 = this;
        r0 = r18;
        r13 = r0.mLeService;
        if (r13 == 0) goto L_0x000f;
    L_0x0006:
        r13 = "CdeService";
        r14 = "start. CDE has started.";
        com.letv.pp.utils.LogTool.i(r13, r14);
    L_0x000e:
        return;
    L_0x000f:
        r10 = 0;
        r3 = 0;
        r2 = 1;
        if (r19 == 0) goto L_0x0050;
    L_0x0014:
        r13 = "start_params";
        r0 = r19;
        r10 = r0.getStringExtra(r13);
        r13 = "auto_start_upgrade";
        r14 = 1;
        r0 = r19;
        r2 = r0.getBooleanExtra(r13, r14);
        r13 = "start_after_upgrade";
        r14 = 1;
        r0 = r19;
        r13 = r0.getBooleanExtra(r13, r14);
        r0 = r18;
        r0.mStartAfterUpgrade = r13;
        r13 = "activity_class";
        r0 = r19;
        r3 = r0.getSerializableExtra(r13);
        r3 = (java.lang.Class) r3;
        if (r3 == 0) goto L_0x0047;
    L_0x0040:
        r0 = r18;
        r1 = r19;
        r0.startForeground(r3, r1);
    L_0x0047:
        if (r2 != 0) goto L_0x0050;
    L_0x0049:
        r0 = r18;
        r13 = r0.mManualStartUpgradeTag;
        if (r13 == 0) goto L_0x0050;
    L_0x004f:
        r2 = 1;
    L_0x0050:
        r13 = "CdeService";
        r14 = "start. start parameter(%s), start auto upgrade check(%s), class(%s)";
        r15 = 3;
        r15 = new java.lang.Object[r15];
        r16 = 0;
        r15[r16] = r10;
        r16 = 1;
        r17 = java.lang.Boolean.valueOf(r2);
        r15[r16] = r17;
        r16 = 2;
        r15[r16] = r3;
        com.letv.pp.utils.LogTool.i(r13, r14, r15);
        r8 = com.letv.pp.utils.StringUtils.parseParams(r10);
        r0 = r18;
        r13 = r0.mIndependentProcess;
        if (r13 == 0) goto L_0x007e;
    L_0x0075:
        com.letv.pp.utils.DomainHelper.init(r8);
        com.letv.pp.utils.LibraryHelper.init(r8);
        com.letv.pp.utils.NetworkUtils.detectNetwork(r18);
    L_0x007e:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        if (r8 != 0) goto L_0x0152;
    L_0x0085:
        r13 = "0";
        r0 = r18;
        r0.mAppId = r13;
        r13 = "app_id=";
        r13 = r11.append(r13);
        r0 = r18;
        r14 = r0.mAppId;
        r13.append(r14);
        r13 = "&port=";
        r13 = r11.append(r13);
        r14 = "6990";
        r13.append(r14);
        r13 = "&data_dir=";
        r13 = r11.append(r13);
        r14 = "datas";
        r15 = 0;
        r0 = r18;
        r14 = r0.getDir(r14, r15);
        r14 = r14.getAbsolutePath();
        r13.append(r14);
    L_0x00b9:
        r13 = "&hwtype=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.ProductUtils.getProductName();
        r14 = java.net.URLEncoder.encode(r14);
        r13.append(r14);
        r13 = "&ostype=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.DeviceUtils.getOsVersion();
        r14 = java.net.URLEncoder.encode(r14);
        r13.append(r14);
        r13 = "&memory_size=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.DeviceUtils.getMemorySize();
        r16 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r14 = r14 / r16;
        r13.append(r14);
        r13 = "&device_screen_resolution=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.DeviceUtils.getScreenResolution(r18);
        r14 = java.net.URLEncoder.encode(r14);
        r13.append(r14);
        r13 = "&device_dpi=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.DeviceUtils.getDensityDpi(r18);
        r13.append(r14);
        r13 = "&device_id=";
        r13 = r11.append(r13);
        r14 = com.letv.pp.utils.DeviceUtils.getIMEI(r18);
        r13.append(r14);
        r13 = "-";
        r7 = com.letv.pp.utils.NetworkUtils.getEthMac(r13);
        r13 = "-";
        r12 = com.letv.pp.utils.NetworkUtils.getWlanMac(r13);
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r7);
        if (r13 == 0) goto L_0x022c;
    L_0x0129:
        r7 = r12;
        if (r7 != 0) goto L_0x012e;
    L_0x012c:
        r7 = "";
    L_0x012e:
        r13 = "&local_mac_address=";
        r13 = r11.append(r13);
        r13.append(r7);
        r10 = r11.toString();
        r13 = "cde";
        r0 = r18;
        r13 = com.letv.pp.utils.LibraryHelper.loadLibrary(r0, r13);
        if (r13 != 0) goto L_0x024b;
    L_0x0145:
        r13 = -3;
        r0 = r18;
        r0.sendBoradcast(r13);
        if (r2 == 0) goto L_0x000e;
    L_0x014d:
        r18.startUpgrade();
        goto L_0x000e;
    L_0x0152:
        r13 = "app_channel";
        r13 = r8.get(r13);
        r13 = (java.lang.String) r13;
        r0 = r18;
        r0.mAppChannel = r13;
        r13 = "app_id";
        r13 = r8.get(r13);
        r13 = (java.lang.String) r13;
        r0 = r18;
        r0.mAppId = r13;
        r0 = r18;
        r13 = r0.mAppId;
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r13);
        if (r13 == 0) goto L_0x017a;
    L_0x0174:
        r13 = "0";
        r0 = r18;
        r0.mAppId = r13;
    L_0x017a:
        r13 = "port";
        r9 = r8.get(r13);
        r9 = (java.lang.String) r9;
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r9);
        if (r13 != 0) goto L_0x0197;
    L_0x0189:
        r13 = "http_port";
        r9 = r8.get(r13);
        r9 = (java.lang.String) r9;
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r9);
        if (r13 == 0) goto L_0x0199;
    L_0x0197:
        r9 = "6990";
    L_0x0199:
        r13 = "data_dir";
        r4 = r8.get(r13);
        r4 = (java.lang.String) r4;
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r4);
        if (r13 == 0) goto L_0x01b4;
    L_0x01a7:
        r13 = "datas";
        r14 = 0;
        r0 = r18;
        r13 = r0.getDir(r13, r14);
        r4 = r13.getAbsolutePath();
    L_0x01b4:
        r13 = "app_id=";
        r13 = r11.append(r13);
        r0 = r18;
        r14 = r0.mAppId;
        r13.append(r14);
        r13 = "&port=";
        r13 = r11.append(r13);
        r13.append(r9);
        r13 = "&data_dir=";
        r13 = r11.append(r13);
        r13.append(r4);
        r13 = r8.entrySet();
        r6 = r13.iterator();
    L_0x01db:
        r13 = r6.hasNext();
        if (r13 == 0) goto L_0x00b9;
    L_0x01e1:
        r5 = r6.next();
        r5 = (java.util.Map.Entry) r5;
        r13 = "app_id";
        r14 = r5.getKey();
        r13 = r13.equals(r14);
        if (r13 != 0) goto L_0x01db;
    L_0x01f3:
        r13 = "port";
        r14 = r5.getKey();
        r13 = r13.equals(r14);
        if (r13 != 0) goto L_0x01db;
    L_0x0200:
        r13 = "data_dir";
        r14 = r5.getKey();
        r13 = r13.equals(r14);
        if (r13 != 0) goto L_0x01db;
    L_0x020c:
        r13 = "&";
        r14 = r11.append(r13);
        r13 = r5.getKey();
        r13 = (java.lang.String) r13;
        r13 = r14.append(r13);
        r14 = "=";
        r14 = r13.append(r14);
        r13 = r5.getValue();
        r13 = (java.lang.String) r13;
        r14.append(r13);
        goto L_0x01db;
    L_0x022c:
        r13 = com.letv.pp.utils.StringUtils.isEmpty(r12);
        if (r13 != 0) goto L_0x012e;
    L_0x0232:
        r13 = new java.lang.StringBuilder;
        r14 = java.lang.String.valueOf(r7);
        r13.<init>(r14);
        r14 = ",";
        r13 = r13.append(r14);
        r13 = r13.append(r12);
        r7 = r13.toString();
        goto L_0x012e;
    L_0x024b:
        r0 = r18;
        r13 = r0.mIndependentProcess;
        if (r13 == 0) goto L_0x0254;
    L_0x0251:
        r18.startLinkShell();
    L_0x0254:
        r13 = new com.letv.pp.service.LeService;
        r0 = r18;
        r13.<init>(r0, r10);
        r0 = r18;
        r0.mLeService = r13;
        r0 = r18;
        r13 = r0.mLeService;
        r13 = r13.start();
        if (r13 == 0) goto L_0x0276;
    L_0x0269:
        r13 = 0;
    L_0x026a:
        r0 = r18;
        r0.sendBoradcast(r13);
        if (r2 == 0) goto L_0x000e;
    L_0x0271:
        r18.startUpgrade();
        goto L_0x000e;
    L_0x0276:
        r13 = -4;
        goto L_0x026a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.pp.service.CdeService.start(android.content.Intent):void");
    }

    private void startLinkShell() {
        boolean ready = true;
        try {
            long startTime = System.currentTimeMillis();
            setEnv("LINKSHELL_CONFIG_NETWORK_TYPE", getLinkShellNetworkType());
            if (initLinkShell() != 0) {
                ready = false;
            }
            this.mLinkShellStarted = true;
            String str = TAG;
            String str2 = "startLinkShell. LinkShell init %s, spend time(%s ms)";
            Object[] objArr = new Object[2];
            objArr[0] = ready ? "successfully" : Constants.CALLBACK_FAILD;
            objArr[1] = Long.valueOf(System.currentTimeMillis() - startTime);
            LogTool.i(str, str2, objArr);
        } catch (Throwable t) {
            LogTool.e(TAG, "startLinkShell. " + t.toString());
        }
    }

    public void notifyLinkShellNetworkChanged() {
        if (this.mLinkShellStarted) {
            try {
                setEnv("LINKSHELL_CONFIG_NETWORK_TYPE", getLinkShellNetworkType());
            } catch (Throwable t) {
                LogTool.e(TAG, "notifyLinkShellNetworkChanged. " + t.toString());
            }
        }
    }

    private String getLinkShellNetworkType() {
        int networkType = NetworkUtils.getNetworkType();
        if (NetworkUtils.isMobileNetwork()) {
            networkType = 2;
        } else if (NetworkUtils.unknownNetwork() || NetworkUtils.noPermissionNetwork()) {
            networkType = 4;
        }
        return String.valueOf(networkType);
    }

    private void startUpgrade() {
        if (this.mAppId != null && this.mDownloadEngine == null) {
            this.mDownloadEngine = new DownloadEngine(this, this.mAppId, this.mAppChannel, true);
            if (this.mStartAfterUpgrade) {
                this.mDownloadEngine.setTheFirstTimeUpgrade(false);
            }
            if (!this.mDownloadEngine.isUpgradeEnabled()) {
                this.mDownloadEngine.quit();
                this.mDownloadEngine = null;
            } else if (NetworkUtils.hasNetwork()) {
                this.mDownloadEngine.startUpgrade(this.mStartAfterUpgrade ? 10800000 : 0);
            }
        }
    }

    private void sendBoradcast(int errorCode) {
        Intent intent = new Intent("com.letv.pp.action.CDE_START_COMPLETE");
        intent.putExtra(KEY_BROADCAST_FLAG, getPackageName());
        intent.putExtra("cde_start_status_code", errorCode);
        try {
            getApplicationContext().sendBroadcast(intent);
            LogTool.i(TAG, "sendBoradcast. the first times send com.letv.pp.action.CDE_START_COMPLETE boradcast.");
        } catch (Exception e) {
            LogTool.w(TAG, "sendBoradcast. the first times send com.letv.pp.action.CDE_START_COMPLETE boradcast failed, " + e.toString());
            try {
                intent.setFlags(67108864);
                getApplicationContext().sendBroadcast(intent);
                LogTool.i(TAG, "sendBoradcast. the second times send com.letv.pp.action.CDE_START_COMPLETE boradcast before boot completed.");
            } catch (Exception e2) {
                LogTool.w(TAG, "sendBoradcast. the second times send com.letv.pp.action.CDE_START_COMPLETE boradcast failed, " + e2.toString());
            }
        }
    }

    private void stop() {
        if (this.mDownloadEngine != null) {
            this.mDownloadEngine.stopUpgrade();
            this.mDownloadEngine.quit();
            this.mDownloadEngine = null;
        }
        if (this.mLeService != null) {
            this.mLeService.stop();
            this.mLeService = null;
        }
        this.mServiceLooper.quit();
    }

    private void startForeground(Class<? extends Activity> clazz, Intent intent) {
        int icon = intent.getIntExtra(KEY_NOTIFACION_ICON, 17301540);
        String contentTitle = intent.getStringExtra(KEY_NOTIFACION_CONTENTTITLE);
        String contentText = intent.getStringExtra(KEY_NOTIFACION_CONTENTTEXT);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, clazz), 0);
        Notification notification = new Notification(icon, contentText, System.currentTimeMillis());
        notification.flags = 2;
        notification.flags |= 32;
        notification.flags |= 64;
        notification.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
        startForeground(0, notification);
    }
}
