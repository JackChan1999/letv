package com.letv.android.client;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import com.facebook.FacebookSdk;
import com.flurry.android.FlurryAgent;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.ads.ex.utils.AdsManagerProxy.VipCallBack;
import com.letv.ads.ex.utils.IAdJSBridge;
import com.letv.android.client.R.color;
import com.letv.android.client.R.dimen;
import com.letv.android.client.R.drawable;
import com.letv.android.client.R.string;
import com.letv.android.client.R.style;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.utils.PluginInitedCallback;
import com.letv.android.client.listener.LetvSensorEventListener;
import com.letv.android.client.push.LetvPushService;
import com.letv.android.client.service.RedPacketPollingService;
import com.letv.android.client.thirdpartlogin.HongKongLoginWebview;
import com.letv.android.client.utils.CrashHandler;
import com.letv.android.client.utils.IniFile;
import com.letv.android.client.worldcup.LetvAlarmService;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration.Builder;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.utils.CpuInfosUtils;
import com.letv.component.player.utils.NativeInfos;
import com.letv.component.upgrade.core.AppDownloadConfiguration.ConfigurationBuild;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DBSaveManage;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DataCallbackCategory;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DownloadServiceManage;
import com.letv.component.upgrade.core.service.DownLoadFunction;
import com.letv.component.upgrade.utils.AppUpgradeConstants;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.core.BaseApplication;
import com.letv.core.BaseApplication.UnicomFreeInfoCache;
import com.letv.core.api.LetvHttpApiConfig;
import com.letv.core.bean.TimestampBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.AutoSwitchHandler;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.api.ILemallPlatformListener;
import com.letv.lemallsdk.model.AppInfo;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import com.letv.mobile.lebox.LeboxApiManager.LetvMediaPlayer;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginconfig.utils.JarLaunchUtils;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.utils.PluginHelper;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.util.JarUtil;
import com.letv.plugin.pluginloader.util.JarUtil.OnPluginInitedListener;
import com.letv.pp.func.CdeHelper;
import com.letv.pp.listener.OnStartStatusChangeListener;
import com.letv.redpacketsdk.RedPacketSdkManager;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LetvApplication extends BaseApplication implements ILemallPlatformListener {
    private static final String APP_ID_FOR_LEMALL = "41054b7ccff4dd718eb2fdb8553c6eb8";
    private CrashHandler mCrashHandler;
    private LeBoxApp mLeBoxApp;
    private LetvSensorEventListener mLetvSensorEventListener;
    private BroadcastReceiver mPluginInstallSuccessReceiver;

    public LetvApplication() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mPluginInstallSuccessReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ LetvApplication this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                String data = intent.getDataString();
                LogInfo.log("plugin", "接收到插件安装成功消息... action = " + action);
                if (action.equals(ApkManager.ACTION_PACKAGE_ADDED) && data.substring(data.lastIndexOf("/") + 1).equals("com.letv.android.lite")) {
                    this.this$0.mIsPluginInitedSuccess = true;
                    if (this.this$0.mCallLiteIntent != null) {
                        if (LetvUtils.isTopRunning(this.this$0.getApplicationContext())) {
                            Toast.makeText(this.this$0.getApplicationContext(), 2131100676, 0).show();
                            LogInfo.log("plugin", "之前有影视大全的lite调起，现在自动补上...");
                            LeMessageManager.getInstance().dispatchMessage(BaseApplication.instance, new LeMessage(115, LetvApplication.getInstance().mCallLiteIntent));
                            JarLaunchUtils.launchLitePlayerDefault(this.this$0.getApplicationContext(), LetvApplication.getInstance().mCallLiteIntent);
                        } else {
                            Toast.makeText(this.this$0.getApplicationContext(), 2131101586, 0).show();
                        }
                    }
                    this.this$0.mCallLiteIntent = null;
                }
            }
        };
    }

    public static synchronized LetvApplication getInstance() {
        LetvApplication letvApplication;
        synchronized (LetvApplication.class) {
            letvApplication = (LetvApplication) instance;
        }
        return letvApplication;
    }

    public void onCreate() {
        super.onCreate();
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        LogInfo.log("test_yang", "application init ----->begining");
        StatisticsUtils.init();
        LogInfo.log("test_yang", "application init ----->begining");
        initChannel();
        initOriginalChannelFile();
        StatisticsUtils.initDoubleChannel();
        FacebookSdk.sdkInitialize(getApplicationContext());
        LogInfo.log("test_yang", "application init ----->begining");
        boolean z = !LetvUtils.is60() || PreferencesManager.getInstance().isApplyPermissionsSuccess();
        JarUtil.sHasApplyPermissionsSuccess = z;
        if (isMainProcess()) {
            LogInfo.log("test_yang", "application init ----->begining");
            LogInfo.log("plugin", "主进程调用initPlugin...");
            JarUtil.initPlugin(this, new OnPluginInitedListener(this) {
                final /* synthetic */ LetvApplication this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onInited() {
                    this.this$0.mIsPluginInitedSuccess = true;
                    if (PluginInitedCallback.mPluginInitedListener != null) {
                        PluginInitedCallback.mPluginInitedListener.onInited();
                    }
                    PluginInitedCallback.mPluginInitedListener = null;
                }
            });
        }
        if (isPipProcess()) {
            LogInfo.log("zhuqiao", "call init pip");
            initLetvMediaPlayerManager();
            setVType();
        }
        LogInfo.log("test_yang", "application init ----->begining");
        LogInfo.log("test_yang", "application init ----->begining");
        if (!hasInited()) {
            FlurryAgent.setLogEnabled(false);
            FlurryAgent.init(this, LetvConfig.getFlurryKey());
            if (LetvUtils.is60()) {
                DownloadManager.stopDownloadService();
            }
        }
        LogInfo.log("test_yang", "application init ----->end");
    }

    public void init() {
        super.init();
        LogInfo.log("zhuqiao", "call init");
        initInMainProcess();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        this.mLeBoxApp = LeBoxApp.getInstanced();
        this.mLeBoxApp.init((Application) this);
        if (NetworkUtils.isUnicom3G(true)) {
            ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).initSDK(this, Boolean.valueOf(false));
            LogInfo.log("zhuqiao", "init wo");
            this.mUnicomFreeInfoCache = new UnicomFreeInfoCache();
        }
        initLetvMediaPlayerManager();
        setVType();
        LetvPushService.schedule(this);
        startCde();
        LetvHttpApiConfig.initialize(Global.PCODE, Global.VERSION);
        LeboxApiManager.getInstance().setLetvMediaPlayer(new LetvMediaPlayer(this) {
            final /* synthetic */ LetvApplication this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void doPlay(Context context, LeboxVideoBean video) {
                LogInfo.log("zhuqiao", "vid:" + video.vid + ";videoUrl:" + video.videoURL);
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).createLebox(video)));
            }
        });
    }

    private void initInMainProcess() {
        initResClass();
        if (sAppStartTime == 0) {
            sAppStartTime = System.currentTimeMillis();
        }
        new Builder(this, LetvConfig.getPcode(), LetvUtils.getClientVersionName()).contentProviderUri(LetvContentProvider.URI_WORLDCUPTRACE.toString()).downloadPath(StoreManager.getDownloadPath(), StoreManager.DOWLOAD_LOCATION).threadSize(2).downloadMaxSize(1).enableLogging(false).build();
        if (PreferencesManager.getInstance().getWorldCupFunc() && !LetvAlarmService.checkServiceIsRunning(this)) {
            LetvAlarmService.startPollingService(this);
        }
        initAppDownLoadParams();
        if (LetvConfig.isErrorCatch()) {
            this.mCrashHandler = CrashHandler.getInstance();
            this.mCrashHandler.init(getApplicationContext());
        }
        initLemallPlatform();
        initAds();
        initRedPacketSdk();
        registerPluginInstalledBroadcastReceiver();
    }

    private void registerPluginInstalledBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ApkManager.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");
        LogInfo.log("plugin", "注册广播： com.letv.plugin.pluginloader.PACKAGE_ADDED");
        registerReceiver(this.mPluginInstallSuccessReceiver, filter);
    }

    private void unregisterPluginInstalledBroadcastReceiver() {
        unregisterReceiver(this.mPluginInstallSuccessReceiver);
    }

    private void initResClass() {
        sStringCls = string.class;
        sDrawableCls = drawable.class;
        sColorCls = color.class;
        sDimenCls = dimen.class;
        sStyleCls = style.class;
    }

    private void initLemallPlatform() {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(APP_ID_FOR_LEMALL);
        appInfo.setAppName(getResources().getString(2131099758));
        appInfo.setInlay(false);
        LemallPlatform.Init(this, appInfo, this);
    }

    public void initRedPacketSdk() {
        RedPacketSdkManager.getInstance().init(this, LetvUtil.getDeviceID(this), "letv01", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getSso_tk() : "");
        RedPacketSdkManager.getInstance().setAppRunid(StatisticsUtils.getApprunId(this));
        RedPacketSdkManager instance = RedPacketSdkManager.getInstance();
        boolean z = (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) ? false : true;
        instance.setIsOnline(z);
    }

    private void initAppDownLoadParams() {
        String path;
        if (Environment.getExternalStorageState().equals("mounted")) {
            path = AppUpgradeConstants.DOWLOAD_LOCATION;
        } else {
            path = getDir("updata", 3).getPath();
        }
        DownLoadFunction.getInstance(getApplicationContext()).initDownLoadConfig(new ConfigurationBuild(getApplicationContext()).downloadTaskNum(1).downloadTaskThreadNum(1).limitSdcardSize(104857600).notifyIntentAction(AppUpgradeConstants.NOTIFY_INTENT_ACTION).pathDownload(path).setCallbackCategoty(DataCallbackCategory.BROADCAST).downloadServiceType(DownloadServiceManage.LOCALSERVICE).isOnStartAddTaskToDB(DBSaveManage.START_ADD_TO_DB).build());
    }

    private void initLetvMediaPlayerManager() {
        LetvMediaPlayerManager.getInstance().setDebugMode(LetvConfig.isDebug());
        LetvMediaPlayerManager.getInstance().setLogMode(LetvConfig.isDebug());
        LetvMediaPlayerManager.getInstance().init(this, LetvConfig.getAppKey(), "hlXD", LetvConfig.getPcode(), LetvUtils.getClientVersionName());
    }

    public int setVType() {
        if (this.mDefaultHardStreamDecorder) {
            return this.mSupportLevel;
        }
        int sLevel;
        LogInfo.log("zhuqiao", "hard decode state:" + LetvMediaPlayerManager.getInstance().getHardDecodeState());
        LogInfo.log("zhuqiao", "最大主频：" + CpuInfosUtils.getMaxCpuFrequence());
        if (NativeInfos.getSupportLevel() == 0 || !NativeInfos.ifSupportVfpOrNeon()) {
            this.mDefaultHardStreamDecorder = false;
            this.mVideoFormat = "no";
            this.mSupportLevel = 0;
            this.mDefaultLevel = 0;
        }
        if (LetvMediaPlayerManager.getInstance().getHardDecodeState() != 1) {
            LogInfo.log("zhuqiao", "不支持硬解");
            this.mDefaultHardStreamDecorder = false;
            sLevel = LetvMediaPlayerManager.getInstance().getSoftDecodeSupportLevel();
            if (sLevel == 1) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 1;
                this.mDefaultLevel = 0;
            } else if (sLevel == 3) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 3;
                this.mDefaultLevel = 2;
            } else if (sLevel == 4) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 4;
                this.mDefaultLevel = 2;
            } else if (sLevel == 5) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 5;
                this.mDefaultLevel = 0;
            } else if (sLevel == 6) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 6;
                this.mDefaultLevel = 2;
            } else if (sLevel == 7) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 7;
                this.mDefaultLevel = 2;
            }
        } else {
            LogInfo.log("zhuqiao", "支持硬解");
            this.mDefaultHardStreamDecorder = true;
            sLevel = LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel();
            if (sLevel == 1) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 1;
                this.mDefaultLevel = 0;
            } else if (sLevel == 3) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 3;
                this.mDefaultLevel = 2;
            } else if (sLevel == 4) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 4;
                this.mDefaultLevel = 2;
            } else if (sLevel == 5) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 5;
                this.mDefaultLevel = 0;
            } else if (sLevel == 6) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 6;
                this.mDefaultLevel = 2;
            } else if (sLevel == 7) {
                this.mVideoFormat = "ios";
                this.mSupportLevel = 7;
                this.mDefaultLevel = 2;
            }
        }
        LogInfo.log("zhuqiao", "---playNet--MainsetVType =" + this.mSupportLevel + " videoFormat =" + this.mVideoFormat + ";;;defaultHardStreamDecorder---" + this.mDefaultHardStreamDecorder + " SupportLevel = " + sLevel);
        return this.mSupportLevel;
    }

    public void resetVType() {
        int sLevel = LetvMediaPlayerManager.getInstance().getSoftDecodeSupportLevel();
        if (sLevel == 1) {
            this.mVideoFormat = "ios";
            this.mSupportLevel = 1;
            this.mDefaultLevel = 0;
        } else if (sLevel == 3) {
            this.mVideoFormat = "ios";
            this.mSupportLevel = 3;
            this.mDefaultLevel = 2;
        } else if (sLevel == 4) {
            this.mVideoFormat = "ios";
            this.mSupportLevel = 4;
            this.mDefaultLevel = 2;
        } else if (sLevel == 6) {
            this.mVideoFormat = "ios";
            this.mSupportLevel = 6;
            this.mDefaultLevel = 2;
        } else if (sLevel == 5) {
            this.mVideoFormat = "ios";
            this.mSupportLevel = 5;
            this.mDefaultLevel = 0;
        }
        this.mDefaultHardStreamDecorder = false;
        LogInfo.log("zhuqiao", "---playNet--resetVType =" + this.mSupportLevel + " videoFormat =" + this.mVideoFormat + ";;;defaultHardStreamDecorder---" + this.mDefaultHardStreamDecorder);
    }

    private void initAds() {
        AdsManagerProxy.getInstance(instance).initAd(instance, this.mSupportLevel, LetvUtils.generateDeviceId(this), "androidPhone", AbstractSpiCall.ANDROID_CLIENT_TYPE, LetvUtils.getClientVersionName(), LetvConfig.getPcode(), (IAdJSBridge) this.iAdJSBridge, LetvConfig.isDebug(), LetvConfig.isDebug(), DataUtils.getData(DataUtils.getUUID(instance)), LetvUtils.getMacAddress(), LetvUtils.getIMEI(), LetvUtils.getIMSI());
        AdsManagerProxy.getInstance(instance).setVipCallBack(new VipCallBack(this) {
            final /* synthetic */ LetvApplication this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean isVip() {
                return PreferencesManager.getInstance().isVip() && PreferencesManager.getInstance().getVipCancelTime() > ((long) TimestampBean.getTm().getCurServerTime());
            }
        });
    }

    public void startCde() {
        if (this.mCdeHelper == null) {
            this.mCdeHelper = CdeHelper.getInstance(this, CDE_PARAMS, false);
            LogInfo.log("zhuqiao", "cde参数:" + CDE_PARAMS);
            this.mCdeHelper.setOnStartStatusChangeListener(new OnStartStatusChangeListener(this) {
                final /* synthetic */ LetvApplication this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onLinkShellStartComplete(int statusCode) {
                    if (statusCode == 0) {
                        LogInfo.log("zhuqiao", "*****************LinkShell启动成功*****************");
                        LetvLogApiTool.getInstance().saveExceptionInfo("LinkShell启动成功");
                        return;
                    }
                    LetvLogApiTool.getInstance().saveExceptionInfo("LinkShell启动失败,statusCode=" + statusCode);
                }

                public void onCdeStartComplete(int statusCode) {
                    if (statusCode == 0) {
                        LogInfo.log("zhuqiao", "*****************cde连接成功*****************");
                        LetvLogApiTool.getInstance().saveExceptionInfo("cde连接成功");
                        return;
                    }
                    LetvLogApiTool.getInstance().saveExceptionInfo("cde连接失败,statusCode=" + statusCode);
                }

                public void onCdeServiceDisconnected() {
                    LogInfo.log("zhuqiao", "*****************cde断开连接*****************");
                    LetvLogApiTool.getInstance().saveExceptionInfo("cde断开连接");
                }
            });
        }
        this.mCdeHelper.start();
    }

    public void stopCde() {
        if (this.mCdeHelper != null) {
            this.mCdeHelper.stop();
        }
    }

    public LetvSensorEventListener getLetvSensorEventListener() {
        if (this.mLetvSensorEventListener == null) {
            this.mLetvSensorEventListener = new LetvSensorEventListener(MainActivity.getInstance());
        }
        return this.mLetvSensorEventListener;
    }

    public void startShake(String className) {
        getLetvSensorEventListener();
        if (this.mLetvSensorEventListener != null) {
            this.mLetvSensorEventListener.start(className);
        }
    }

    public void stopShake() {
        if (this.mLetvSensorEventListener != null) {
            this.mLetvSensorEventListener.stop();
        }
    }

    public static void onAppExit() {
        ((LetvApplication) instance).mCallLiteIntent = null;
        PluginInitedCallback.mPluginInitedListener = null;
        getInstance().stopCde();
        ImageDownloader.getInstance().onDestory();
        AutoSwitchHandler.autoScrollObservable.clearAll();
        if (AMapLocationTool.getInstance().mLocationClient != null) {
            AMapLocationTool.getInstance().isStart = false;
            AMapLocationTool.getInstance().mLocationClient.onDestroy();
            AMapLocationTool.getInstance().mLocationClient = null;
        }
        RedPacketPollingService.stopPollingService(instance);
        RedPacketSdkManager.getInstance().clean();
        sAppStartTime = 0;
        getInstance().isPush = false;
        ActivityUtils.getInstance().removeAll();
    }

    private void initChannel() {
        IOException e;
        String[] data;
        Throwable th;
        String ret = "";
        ZipFile zipfile = null;
        try {
            ZipFile zipfile2 = new ZipFile(getApplicationInfo().sourceDir);
            try {
                Enumeration<?> entries = zipfile2.entries();
                while (entries.hasMoreElements()) {
                    String entryName = ((ZipEntry) entries.nextElement()).getName();
                    if (entryName.startsWith("META-INF/lechannel")) {
                        ret = entryName.replace("\r", "").replace("\n", "");
                        break;
                    }
                }
                if (zipfile2 != null) {
                    try {
                        zipfile2.close();
                        zipfile = zipfile2;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        zipfile = zipfile2;
                    }
                }
            } catch (IOException e3) {
                e2 = e3;
                zipfile = zipfile2;
                try {
                    e2.printStackTrace();
                    if (zipfile != null) {
                        try {
                            zipfile.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    data = ret.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                    if (data.length < 3) {
                        LetvConfig.setPCode(data[1]);
                        LetvConfig.setAppKey(data[2]);
                        if (data.length == 4) {
                            LetvConfig.setIsUmeng(false);
                        }
                        LetvConfig.setUmengID(data[3]);
                        LetvConfig.setIsUmeng(true);
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (zipfile != null) {
                        try {
                            zipfile.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                zipfile = zipfile2;
                if (zipfile != null) {
                    zipfile.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            e222 = e4;
            e222.printStackTrace();
            if (zipfile != null) {
                zipfile.close();
            }
            data = ret.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (data.length < 3) {
                LetvConfig.setPCode(data[1]);
                LetvConfig.setAppKey(data[2]);
                if (data.length == 4) {
                    LetvConfig.setUmengID(data[3]);
                    LetvConfig.setIsUmeng(true);
                    return;
                }
                LetvConfig.setIsUmeng(false);
            }
        }
        data = ret.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (data.length < 3) {
            LetvConfig.setPCode(data[1]);
            LetvConfig.setAppKey(data[2]);
            if (data.length == 4) {
                LetvConfig.setUmengID(data[3]);
                LetvConfig.setIsUmeng(true);
                return;
            }
            LetvConfig.setIsUmeng(false);
        }
    }

    private void initOriginalChannelFile() {
        File file = new File("/data/letv.ini");
        if (file.exists()) {
            Log.e("letv.ini", "channel file exsits ");
            IniFile iniFile = new IniFile(file);
            iniFile.init(this);
            PreferencesManager.getInstance().setDoubleChannel(true);
            String pcode = (String) iniFile.get((Context) this, "config", "pcode");
            String appkey = (String) iniFile.get((Context) this, "config", IniFile.APPKEY);
            String version = (String) iniFile.get((Context) this, "config", "version");
            if (!TextUtils.isEmpty(pcode)) {
                PreferencesManager.getInstance().setOriginalPcode(pcode);
            }
            if (!TextUtils.isEmpty(appkey)) {
                PreferencesManager.getInstance().setOriginalAppkey(appkey);
            }
            if (!TextUtils.isEmpty(version)) {
                PreferencesManager.getInstance().setOriginalVersion(version);
            }
            Log.e("letv.ini", "channel : " + pcode);
            if (iniFile.check(this)) {
                PreferencesManager.getInstance().setActivieState(true);
                return;
            } else {
                PreferencesManager.getInstance().setActivieState(false);
                return;
            }
        }
        Log.e("letv.ini", "channel file not exsits");
        PreferencesManager.getInstance().setDoubleChannel(false);
    }

    public void openLoginPage() {
        if (LetvUtils.isInHongKong()) {
            Intent intent = new Intent(this, HongKongLoginWebview.class);
            intent.addFlags(268435456);
            startActivity(intent);
            return;
        }
        intent = new Intent(this, LetvLoginActivity.class);
        intent.addFlags(268435456);
        startActivity(intent);
    }

    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
        MultiDex.install(this);
        HotFix.init(this);
        HotFix.loadPatch(this, getFilesDir());
    }
}
