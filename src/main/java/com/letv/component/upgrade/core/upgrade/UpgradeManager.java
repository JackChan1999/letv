package com.letv.component.upgrade.core.upgrade;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.letv.component.core.async.TaskCallBack;
import com.letv.component.core.http.bean.LetvBaseBean;
import com.letv.component.upgrade.bean.DownloadInfo;
import com.letv.component.upgrade.bean.DownloadInfo.DownloadState;
import com.letv.component.upgrade.bean.RelatedAppUpgradeInfo;
import com.letv.component.upgrade.bean.UpgradeInfo;
import com.letv.component.upgrade.core.AppDownloadConfiguration;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DataCallbackCategory;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DownloadServiceManage;
import com.letv.component.upgrade.core.db.UpgradeDbInitialUtil;
import com.letv.component.upgrade.core.service.DownLoadFunction;
import com.letv.component.upgrade.core.service.DownloadListenerImpl;
import com.letv.component.upgrade.core.upgrade.host.DownloadHostCallBack;
import com.letv.component.upgrade.core.upgrade.host.ForceUpgradeDownloadAsyncTask;
import com.letv.component.upgrade.core.upgrade.host.UpgradeDownloadAsyncTask;
import com.letv.component.upgrade.utils.AppUpgradeConstants;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.component.upgrade.utils.RemoteDownloadUtil;
import com.letv.component.upgrade.utils.ResourceUtil;
import com.letv.component.upgrade.utils.UpgradePreferenceUtil;
import com.letv.component.upgrade.utils.UpgradeUIs;
import com.letv.component.utils.DebugLog;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.download.manager.StoreManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UpgradeManager {
    private static final boolean DIALOG_CAN_CANCEL = true;
    private static final boolean DIALOG_CAN_NOT_CANCEL = false;
    private static final boolean FILE_HAS_ALREADY_EXIST = true;
    private static final int FILE_IS_INTEGRATED = 20011;
    private static final int FILE_IS_UNINTEGRATED = 40011;
    private static final boolean FILE_NOT_ALREADY_EXIST = false;
    private static final int NETWORK_NOT = 50011;
    private static final int NOTICE_DIALOG = 113345;
    private static final int NOTICE_NOTIFACATION = 114455;
    private static final int NOTICE_NULL = 889911;
    private static final String TAG = "UpgradeManager";
    private static HashMap<String, Integer> notiIdMap = new HashMap();
    private static UpgradeManager upgradeManagerInstance;
    private boolean IS_CURRENT_APP = true;
    private String accesstype;
    private String appkey;
    public boolean bt = false;
    private DataCallbackCategory callbackCategory;
    private ArrayList<RelatedAppUpgradeInfo> canUpgradeList = new ArrayList();
    private int checkType;
    private CheckUpgradeController checkUpgradeController;
    private AppDownloadConfiguration config;
    private Context context;
    private int currentDownLoadType;
    private String descStatic;
    private String devid;
    private int dialogBootomStyleId;
    private boolean dialogCanCancel;
    private int dialogResViewId;
    private int dialogStyleId;
    private DownLoadFunction downLoadFunction;
    private DownloadHostCallBack downloadHostCallBack = new 3(this);
    private String downloadLocation;
    private DownloadServiceManage downloadServiceType;
    private ForceUpgradeDownloadAsyncTask forceUpgradeDownloadAsyncTask;
    private Handler handler = new 1(this);
    private HashMap<String, Integer> hashMap = new HashMap();
    private String hostAppName = "LetvAndroidClient.apk";
    private boolean isDebug;
    public boolean isHostFileDownloaded;
    private boolean isRegDownloadReceiver;
    public boolean isRelatedFileDownloaded;
    private DownloadListenerImpl listener;
    private UpgradeCallBack mCallBack;
    private UpgradeDownloadReceiver mReceiver;
    private String macAddr;
    private String model;
    private ArrayList<RelatedAppUpgradeInfo> needUpgradeList;
    private UpgradeNetChangeReceiver netReceiver;
    private String osversion;
    private String p1;
    private String p2;
    private String p3;
    private String pcode;
    private UpgradePackageReceiver pkgReceiver;
    private String resolution;
    private UpgradeDownloadAsyncTask upgradeAsyncTask;
    private TaskCallBack upgradeCallback = new 2(this);
    private UpgradeInfo upgradeInfo;
    private int userSelectRelatedAppCount;
    private String version;

    private UpgradeManager() {
    }

    public void setBoottom(int dialogBootomStyleId) {
        this.dialogBootomStyleId = dialogBootomStyleId;
    }

    public static synchronized UpgradeManager getInstance() {
        UpgradeManager upgradeManager;
        synchronized (UpgradeManager.class) {
            if (upgradeManagerInstance == null) {
                upgradeManagerInstance = new UpgradeManager();
                DebugLog.log(TAG, "upgradeManagerInstance 为  null");
            }
            upgradeManager = upgradeManagerInstance;
        }
        return upgradeManager;
    }

    public void init(Activity context, String pcode, boolean isDebug, String appKey, int dialogViewResId, int dialogStyleId, String descStatic) {
        this.dialogResViewId = dialogViewResId;
        this.dialogStyleId = dialogStyleId;
        this.context = context;
        this.pcode = pcode;
        this.isDebug = isDebug;
        this.appkey = appKey;
        this.descStatic = descStatic;
        this.downLoadFunction = DownLoadFunction.getInstance(context);
        new UpgradeDbInitialUtil().initDB(context.getApplicationContext());
    }

    public void init(Activity context, String pcode, boolean isDebug, String appKey, int dialogViewResId, int dialogStyleId) {
        init(context, pcode, isDebug, appKey, dialogViewResId, dialogStyleId, null);
    }

    public void init(Activity context, String pcode, boolean isDebug, String appKey, int dialogViewResId, int dialogStyleId, String p1, String p2, String p3) {
        this.descStatic = null;
        this.dialogResViewId = dialogViewResId;
        this.dialogStyleId = dialogStyleId;
        this.context = context;
        this.pcode = pcode;
        this.isDebug = isDebug;
        this.appkey = appKey;
        this.downLoadFunction = DownLoadFunction.getInstance(context);
        new UpgradeDbInitialUtil().initDB(context.getApplicationContext());
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    private void initDownLoadConfig() {
        this.config = this.downLoadFunction.getDownLoadConfig();
        this.callbackCategory = this.config.callbackCategory;
        this.downloadServiceType = this.config.downloadServiceType;
        this.downloadLocation = this.config.downloadLocation;
        if (this.callbackCategory == DataCallbackCategory.BROADCAST) {
            registReceiver();
        } else {
            registListener();
        }
        registNetChangeReceiver();
        registPackageReceiver();
    }

    public void upgrade(UpgradeCallBack callBack, CheckUpgradeController checkUpgradeController, int checktype) {
        if (1 == checktype) {
            initDownLoadConfig();
        }
        if (LetvUtil.getAvailableNetWorkInfo(this.context) == null) {
            DebugLog.log(TAG, "网络异常");
            Toast.makeText(this.context, this.context.getResources().getString(ResourceUtil.getStringId(this.context, "upgrade_net_null")), 0).show();
            return;
        }
        this.checkUpgradeController = checkUpgradeController;
        this.mCallBack = callBack;
        this.checkType = checktype;
        if (2 != checktype || this.upgradeInfo == null) {
            new HttpUpgradeRequest(this.context, this.upgradeCallback).execute(new Object[]{this.pcode, this.appkey});
            return;
        }
        checkRelatedAndUpdateInfo();
    }

    private void checkRelatedAndUpdateInfo() {
        this.mCallBack.setUpgradeData(this.upgradeInfo);
        this.needUpgradeList = this.upgradeInfo.getNeedUpgradeList();
        checkUpgradeAppList();
        if (1 == this.checkType) {
            checkUpdateVersionInfo();
        } else {
            checkManualUpdateVersionInfo();
        }
    }

    private void registReceiver() {
        try {
            this.mReceiver = new UpgradeDownloadReceiver(this.handler);
            IntentFilter filter = new IntentFilter();
            filter.addAction(AppUpgradeConstants.NOTIFY_INTENT_ACTION);
            this.context.registerReceiver(this.mReceiver, filter);
            this.isRegDownloadReceiver = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registNetChangeReceiver() {
        try {
            this.netReceiver = new UpgradeNetChangeReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.context.registerReceiver(this.netReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registPackageReceiver() {
        try {
            this.pkgReceiver = new UpgradePackageReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PACKAGE_ADDED");
            filter.addDataScheme("package");
            this.context.registerReceiver(this.pkgReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void checkUpgradeAppList() {
        if (this.canUpgradeList != null) {
            this.canUpgradeList.clear();
        }
        Iterator it = this.needUpgradeList.iterator();
        while (it.hasNext()) {
            RelatedAppUpgradeInfo info = (RelatedAppUpgradeInfo) it.next();
            if (!LetvUtil.checkAppIsInstalled(this.context, info.getPackageName())) {
                this.canUpgradeList.add(info);
            }
        }
        if (this.canUpgradeList != null) {
            this.upgradeInfo.needUpgradeList.clear();
            it = this.canUpgradeList.iterator();
            int notiId = 0;
            while (it.hasNext()) {
                info = (RelatedAppUpgradeInfo) it.next();
                this.upgradeInfo.needUpgradeList.add(info);
                int notiId2 = notiId + 1;
                notiIdMap.put(info.getAppName(), Integer.valueOf(notiId));
                notiId = notiId2;
            }
        }
    }

    private void registListener() {
        if (this.downloadServiceType != DownloadServiceManage.REMOTESERVICE) {
            this.listener = new DownloadListenerImpl(this.handler);
            this.downLoadFunction.registerListener(this.listener);
        }
    }

    private void unregisterDownloadReceiver() {
        try {
            if (this.isRegDownloadReceiver) {
                this.context.unregisterReceiver(this.mReceiver);
                this.isRegDownloadReceiver = false;
                DebugLog.log(TAG, "unregist receiver ok");
            }
            this.context.unregisterReceiver(this.netReceiver);
            this.context.unregisterReceiver(this.pkgReceiver);
        } catch (Exception e) {
            DebugLog.log(TAG, "unregist receiver error" + e);
        }
    }

    public void exitApp() {
        this.downLoadFunction.pauseAll(false, true);
        unregisterDownloadReceiver();
    }

    public void checkUpdateVersionInfo() {
        DebugLog.log(TAG, "检查升级");
        if (this.upgradeInfo == null) {
            return;
        }
        if (1 == this.upgradeInfo.getUpgrade()) {
            this.mCallBack.setUpgradeState(200);
            new Thread(new 4(this)).start();
            return;
        }
        this.mCallBack.setUpgradeState(401);
        startLastVersionDownLoadTask();
    }

    private void checkUpgradePrompt(int downloadType, boolean fHasAlreadyExist) {
        if (1 == this.upgradeInfo.getIsPrompt()) {
            DebugLog.log(TAG, "需要升级");
            Log.i("RemoteDownloadTaskService", new StringBuilder(String.valueOf(this.upgradeInfo.getPromptAlways())).toString());
            if (1 == this.upgradeInfo.getPromptAlways()) {
                Log.i("RemoteDownloadTaskService", "show dialog");
                excuteUpgradeServ(downloadType, fHasAlreadyExist);
                DebugLog.log(TAG, "需要升级");
                return;
            }
            long upgradeTimePoint = UpgradePreferenceUtil.getUpgradeTimePoint(this.context);
            long currentTimeMillis = System.currentTimeMillis();
            Log.i("RemoteDownloadTaskService", "检查时间段:upgradeTimePoint=" + upgradeTimePoint);
            Log.i("RemoteDownloadTaskService", "检查时间段:currentTimeMillis=" + currentTimeMillis);
            if (currentTimeMillis >= upgradeTimePoint) {
                Log.i("RemoteDownloadTaskService", "弹出APP内提示框");
                excuteUpgradeServ(downloadType, fHasAlreadyExist);
                return;
            }
            Log.i("RemoteDownloadTaskService", "不能弹出APP内提示框");
            this.mCallBack.setUpgradeDialog(0, this.upgradeInfo);
        }
    }

    private void excuteUpgradeServ(int downloadType, boolean fHasAlreadyExist) {
        ((NotificationManager) this.context.getSystemService("notification")).cancel(RemoteDownloadUtil.NOTIFICATION_ID);
        UpgradePreferenceUtil.setUpgradeThisRemoteDownValue(this.upgradeInfo, this.context, "desc_01", System.currentTimeMillis());
        RemoteDownloadUtil.setNextDownloadTrigger(this.context, UpgradePreferenceUtil.getUpgradeThisRemoteDownValue(this.context));
        showUpdateDialog(downloadType, fHasAlreadyExist);
    }

    private void startLastVersionDownLoadTask() {
        DebugLog.log(TAG, "无需升级");
        Map<String, DownloadInfo> downloadTask = this.downLoadFunction.getDownloadTask();
        DebugLog.log(TAG, "task" + downloadTask);
        if (downloadTask == null || downloadTask.size() < 1) {
            DebugLog.log(TAG, "task 不存在");
            return;
        }
        int increaseNotiId = 0;
        for (String id : downloadTask.keySet()) {
            DownloadInfo downloadInfo = (DownloadInfo) downloadTask.get(id);
            if (!(downloadInfo == null || downloadInfo.state == DownloadState.FINISHED)) {
                DebugLog.log(TAG, "filename" + downloadInfo.fileName + "____" + id);
                downloadInfo.state = DownloadState.STOPPED;
                String fileName = downloadInfo.fileName;
                if (!(fileName == null || "".equalsIgnoreCase(fileName))) {
                    boolean hasInstall = LetvUtil.checkHasInstallByFileName(this.context, downloadInfo.fileName);
                    DebugLog.log(TAG, "是否已安装" + hasInstall);
                    File file = LetvUtil.getFile(downloadInfo.fileDir, downloadInfo.fileName);
                    if (hasInstall) {
                        this.downLoadFunction.remove(downloadInfo.url);
                    } else {
                        RelatedAppUpgradeInfo bean = new RelatedAppUpgradeInfo();
                        bean.setAppName(fileName);
                        bean.setDownloadUrl(downloadInfo.url);
                        int increaseNotiId2 = increaseNotiId + 1;
                        notiIdMap.put(fileName, Integer.valueOf(increaseNotiId));
                        if (file == null || !file.exists()) {
                            this.downLoadFunction.remove(bean.getDownloadUrl());
                            startDownloadNewVersion(bean, NOTICE_NOTIFACATION, !this.IS_CURRENT_APP, 2);
                            DebugLog.log(TAG, "重新下载" + id);
                            increaseNotiId = increaseNotiId2;
                        } else {
                            resumeDownLoad(NOTICE_NOTIFACATION, !this.IS_CURRENT_APP, bean, 2);
                            DebugLog.log(TAG, "恢复下载resume" + id);
                            increaseNotiId = increaseNotiId2;
                        }
                    }
                }
            }
        }
    }

    private void checkAndStartDownRelatedApp(int downloadType) {
        this.needUpgradeList = this.upgradeInfo.needUpgradeList;
        if (this.needUpgradeList == null || this.needUpgradeList.size() <= 0) {
            this.isRelatedFileDownloaded = true;
            return;
        }
        Iterator it = this.needUpgradeList.iterator();
        while (it.hasNext()) {
            RelatedAppUpgradeInfo info = (RelatedAppUpgradeInfo) it.next();
            boolean z;
            if (2 != downloadType) {
                if (this.IS_CURRENT_APP) {
                    z = false;
                } else {
                    z = true;
                }
                startDownloadNewVersion(info, NOTICE_NOTIFACATION, z, downloadType);
            } else if (info.isSelect()) {
                if (this.IS_CURRENT_APP) {
                    z = false;
                } else {
                    z = true;
                }
                startDownloadNewVersion(info, NOTICE_NOTIFACATION, z, downloadType);
            }
        }
    }

    public void showUpdateDialog(int downloadType, boolean fHasAlreadyExist) {
        this.dialogCanCancel = false;
        this.mCallBack.setUpgradeDialog(1, this.upgradeInfo);
        if (1 != this.upgradeInfo.getUptype()) {
            getInstance().uLog(this.context, "0", "19");
        } else {
            getInstance().uLog(this.context, "4", "19");
        }
        if (!this.bt) {
            UpgradeUIs.callDialogMsgPosNegCancel((Activity) this.context, this.upgradeInfo, new 5(this, downloadType, fHasAlreadyExist), new 6(this), this.dialogCanCancel, this.dialogResViewId, this.dialogStyleId, this.dialogBootomStyleId, new String[]{this.upgradeInfo.getVersion()});
        }
    }

    public void showNotificationAndInstallApk() {
        UpgradeDownloadAsyncTask.showHostNotification(this.context, this.upgradeInfo);
        int size = this.needUpgradeList.size();
        for (int i = 0; i < size; i++) {
            RelatedAppUpgradeInfo relatedAppUpgradeInfo = (RelatedAppUpgradeInfo) this.needUpgradeList.get(i);
            if (relatedAppUpgradeInfo.isSelect()) {
                UpgradeUIs.showReleatedNotification(this.context, relatedAppUpgradeInfo, i);
            }
        }
        startInstallApk();
    }

    public void startInstallApk() {
        String fileDir = this.downLoadFunction.getDownLoadConfig().downloadLocation;
        if (!(Build.PRODUCT.contains("meizu_mx2") || Build.DEVICE.contains("mx2"))) {
            Iterator it = this.needUpgradeList.iterator();
            while (it.hasNext()) {
                RelatedAppUpgradeInfo info = (RelatedAppUpgradeInfo) it.next();
                if (info.isSelect()) {
                    LetvUtil.installApk(this.context, new StringBuilder(String.valueOf(fileDir)).append(File.separator).append(info.getAppName()).toString(), 0, this.mCallBack);
                }
            }
        }
        LetvUtil.installApk(this.context, new StringBuilder(String.valueOf(fileDir)).append(File.separator).append(this.hostAppName).toString(), 0, this.mCallBack);
        if (1 == this.upgradeInfo.getUptype()) {
            this.mCallBack.setUpgradeType(1, 200);
        }
    }

    public void startSilentAppDownload(UpgradeInfo result) {
        try {
            if (1 == LetvUtil.getNetWorkType(this.context)) {
                if (!LetvUtil.isSdcardAvailable() || LetvUtil.getSdcardAvailableSpace(this.context.getApplicationContext()) >= StoreManager.DEFAULT_SDCARD_SIZE) {
                    this.currentDownLoadType = 1;
                    checkAndStartDownRelatedApp(1);
                    this.upgradeAsyncTask = new UpgradeDownloadAsyncTask((Activity) this.context, result.getUpurl(), result.getAppName(), this.downloadHostCallBack, this.downloadLocation, this.checkType, 1, this.upgradeInfo);
                    this.upgradeAsyncTask.execute();
                    return;
                }
                Toast.makeText(this.context, ResourceUtil.getStringId(this.context, "upgrade_toast_sdcard_lower"), 1).show();
            }
        } catch (Exception e) {
            DebugLog.log(TAG, "start download error");
            e.printStackTrace();
        }
    }

    public void startUnSilentDownload() {
        try {
            if (LetvUtil.getNetWorkType(this.context) == 0) {
                Toast.makeText(this.context, this.context.getResources().getString(ResourceUtil.getStringId(this.context, "upgrade_net_null")), 0).show();
            } else if (!LetvUtil.isSdcardAvailable() || LetvUtil.getSdcardAvailableSpace(this.context.getApplicationContext()) >= StoreManager.DEFAULT_SDCARD_SIZE) {
                this.currentDownLoadType = 2;
                if (UpgradeDownloadAsyncTask.state == 0 && this.upgradeAsyncTask != null) {
                    this.upgradeAsyncTask.cancel();
                }
                this.downLoadFunction.removeAll();
                this.handler.postDelayed(new 7(this), 1500);
            } else {
                Toast.makeText(this.context, ResourceUtil.getStringId(this.context, "upgrade_toast_sdcard_lower"), 1).show();
            }
        } catch (Exception e) {
            DebugLog.log(TAG, "UpdataAppException param error");
        }
    }

    private int checkUserSelectRelatedApp() {
        this.userSelectRelatedAppCount = 0;
        ArrayList<RelatedAppUpgradeInfo> arrayList = this.upgradeInfo.needUpgradeList;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((RelatedAppUpgradeInfo) it.next()).isSelect()) {
                    this.userSelectRelatedAppCount++;
                }
            }
        }
        return this.userSelectRelatedAppCount;
    }

    private void startDownloadNewVersion(LetvBaseBean bean, int noticeType, boolean isCurrentApp, int downloadType) {
        if (isCurrentApp) {
            initShowStyle(noticeType, isCurrentApp, bean, downloadType);
            UpgradeInfo upgradeInfo = (UpgradeInfo) bean;
            this.downLoadFunction.startDownload(this.context, upgradeInfo.getUpurl(), upgradeInfo.getAppName(), isCurrentApp, upgradeInfo.getIsSilentInstall());
            return;
        }
        initShowStyle(noticeType, isCurrentApp, bean, downloadType);
        RelatedAppUpgradeInfo relatedInfo = (RelatedAppUpgradeInfo) bean;
        this.downLoadFunction.startDownload(this.context, relatedInfo.getDownloadUrl(), relatedInfo.getAppName(), isCurrentApp, relatedInfo.getIsInstallSlient());
    }

    private void resumeDownLoad(int noticeType, boolean isCurrentApp, LetvBaseBean bean, int downloadType) {
        initShowStyle(noticeType, isCurrentApp, bean, downloadType);
        if (isCurrentApp) {
            this.downLoadFunction.resumeDownload(((UpgradeInfo) bean).getUpurl());
        } else {
            this.downLoadFunction.resumeDownload(((RelatedAppUpgradeInfo) bean).getDownloadUrl());
        }
    }

    private void initShowStyle(int noticeType, boolean isCurrentApp, LetvBaseBean bean, int downloadType) {
        UpgradeInfo upInfo;
        RelatedAppUpgradeInfo rlInfo;
        switch (noticeType) {
            case NOTICE_DIALOG /*113345*/:
                if (isCurrentApp) {
                    upInfo = (UpgradeInfo) bean;
                    UpgradeUIs.initDialog(this.context, upInfo.getVersion());
                    this.hashMap.put(upInfo.getAppName(), Integer.valueOf(NOTICE_DIALOG));
                    return;
                }
                rlInfo = (RelatedAppUpgradeInfo) bean;
                UpgradeUIs.initDialog(this.context, rlInfo.getAppName());
                this.hashMap.put(rlInfo.getAppName(), Integer.valueOf(NOTICE_DIALOG));
                return;
            case NOTICE_NOTIFACATION /*114455*/:
                if (isCurrentApp) {
                    upInfo = (UpgradeInfo) bean;
                    UpgradeUIs.initNotification(this.context, upInfo.getAppName(), this.checkType, downloadType, (Integer) notiIdMap.get(upInfo.getAppName()), upInfo.getVerName());
                    this.hashMap.put(upInfo.getAppName(), Integer.valueOf(NOTICE_NOTIFACATION));
                    return;
                }
                rlInfo = (RelatedAppUpgradeInfo) bean;
                UpgradeUIs.initNotification(this.context, rlInfo.getAppName(), this.checkType, downloadType, (Integer) notiIdMap.get(rlInfo.getAppName()), rlInfo.getVerName());
                this.hashMap.put(rlInfo.getAppName(), Integer.valueOf(NOTICE_NOTIFACATION));
                return;
            default:
                return;
        }
    }

    public void checkShowUpgradeDialog() {
        Log.i("checkShowUpgradeDialog", "isHostFileDownloaded=" + this.isHostFileDownloaded + ",isRelatedFileDownloaded=" + this.isRelatedFileDownloaded);
        if (this.isHostFileDownloaded && this.isRelatedFileDownloaded && 1 == this.checkType) {
            checkUpgradePrompt(1, false);
        }
    }

    protected void checkManualUpdateVersionInfo() {
        if (this.upgradeInfo == null) {
            this.mCallBack.setUpgradeState(402);
            UpgradeUIs.showToast(this.context, ResourceUtil.getStringId(this.context, "upgrade_dialog_loading_fail"));
            getInstance().eLog(this.context);
        } else if (1 == this.upgradeInfo.getUpgrade()) {
            this.mCallBack.setUpgradeState(200);
            showManualUpdateDialog(this.upgradeInfo);
        } else {
            this.mCallBack.setUpgradeState(401);
            UpgradeUIs.callDialogMsgPositiveButton((Activity) this.context, null);
        }
    }

    private void showManualUpdateDialog(UpgradeInfo result) {
        DebugLog.log("UpgradeUIs", "showManualUpdateDialog");
        this.dialogCanCancel = false;
        this.mCallBack.setUpgradeDialog(1, this.upgradeInfo);
        if (1 != this.upgradeInfo.getUptype()) {
            getInstance().uLog(this.context, "0", "19");
        } else {
            getInstance().uLog(this.context, "4", "19");
        }
        if (!this.bt) {
            UpgradeUIs.callDialogMsgPosNegCancel((Activity) this.context, this.upgradeInfo, new 8(this), new 9(this), this.dialogCanCancel, this.dialogResViewId, this.dialogStyleId, this.dialogBootomStyleId, new String[]{result.getVersion()});
        }
    }

    private void showUpgradeErrorToast() {
        if (2 == this.checkType) {
            UpgradeUIs.showToast(this.context, ResourceUtil.getStringId(this.context, "upgrade_dialog_loading_fail"));
        }
    }

    public void uLog(Context context, String wz, String acode) {
        if (this.p1 != null && this.p2 != null && LetvUtil.getAvailableNetWorkInfo(context) != null) {
            TaskCallBack upgradeCallback = new 10(this);
            if (acode != null) {
                new HttpUpgradeNormalLogRequest(context, upgradeCallback).execute(new Object[]{this.p1, this.p2, this.p3, wz, AppUpgradeConstants.UpgradeLogVer, acode, this.pcode});
            }
        }
    }

    public void eLog(Context context) {
        if (this.p1 != null && this.p2 != null && LetvUtil.getAvailableNetWorkInfo(context) != null) {
            TaskCallBack upgradeCallback = new 11(this);
            String err = LetvErrorCode.VIDEO_CRASH_UPGRADE;
            new HttpUpgradeErrorLogRequest(context, upgradeCallback).execute(new Object[]{this.p1, this.p2, this.p3, err, AppUpgradeConstants.UpgradeLogVer});
        }
    }

    public UpgradeCallBack getCallBack() {
        return this.mCallBack;
    }
}
