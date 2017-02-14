package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import bolts.AppLinks;
import cn.jpush.android.api.JPushInterface;
import cn.shuzilm.core.Main;
import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RetentionRateUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.http.LetvHttpConstant;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.util.JarUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import io.fabric.sdk.android.Fabric;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

public class SplashActivity extends Activity implements PermissionCallbacks {
    private static final int PEM_INIT_APP = 100;
    private static boolean isFirstInitLeso = false;
    public static String path = "";
    private Handler mHandler;

    public SplashActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mHandler = new Handler();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent passedIntent = getIntent();
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        Bundle bundle = passedIntent.getExtras();
        if (bundle != null) {
            LogInfo.log("entryInfo", "splashActivity Intent Extra===" + bundle.toString());
        }
        if (passedIntent != null) {
            LogInfo.log("entryInfo", "splashActivity Intent===" + passedIntent.toString());
            if (targetUrl != null) {
                LogInfo.log("entryInfo", "splashActivity TargetUrl===" + targetUrl.toString());
            }
        }
        if (!isFromFacebook(targetUrl)) {
            LogInfo.log("zhuqiao", "splash init start");
            if (LetvUtils.is60()) {
                doApplyPermissions();
            } else {
                init();
            }
        }
    }

    private boolean isFromFacebook(Uri data) {
        if (data == null) {
            return false;
        }
        LogInfo.log("entryInfo", "splashActivity IntentDate===" + data.toString());
        int type = BaseTypeUtils.stoi(data.getQueryParameter("actionType"), -1);
        if (type == 10) {
            long vid = BaseTypeUtils.stol(data.getQueryParameter("vid"));
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this).create(BaseTypeUtils.stol(data.getQueryParameter("aid")), vid, 24)));
            finish();
            return true;
        } else if (type != 3) {
            return false;
        } else {
            LiveLaunchUtils.launchLives4M(this, data.getQueryParameter("livetype"), false, data.getQueryParameter("liveid"), true);
            finish();
            return true;
        }
    }

    @AfterPermissionGranted(100)
    private void doApplyPermissions() {
        if (EasyPermissions.hasPermissions(this, EasyPermissions.PERMS)) {
            LogInfo.log("zhuqiao", "doApplyPermissions success");
            init();
            return;
        }
        EasyPermissions.requestPermissions(this, getString(2131100595), 100, EasyPermissions.PERMS);
    }

    private void init() {
        Fabric.with(this, new Crashlytics());
        SharedPreferences pref = getSharedPreferences("LetvActivity", 32768);
        if (!pref.contains("isFirstIn")) {
            String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            Editor editor = pref.edit();
            editor.putString("firstRunAppTime", str);
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }
        if (BaseApplication.getAppStartTime() == 0) {
            BaseApplication.setAppStartTime(System.currentTimeMillis());
        }
        PreferencesManager.getInstance().setLesoNotification(false);
        boolean isNewApp = isNewAppVersion4Leso(PreferencesManager.getInstance().getVersionCode4Leso(), LetvUtils.getClientVersionCode());
        if (isNewApp) {
            LogInfo.log("Emerson", "--------------------isNewApp = " + isNewApp);
            PreferencesManager.getInstance().setIsShack(false);
        }
        initLesoIcon_notification(this);
        staticsCrashInfo();
        LetvApplication.getInstance().setIsShack(PreferencesManager.getInstance().isShack());
        LetvHttpConstant.setDebug(LetvConfig.isDebug());
        getDefaultImage(this);
        LetvUtils.setUa(this);
        JarUtil.sHasApplyPermissionsSuccess = true;
        if (ApkManager.getInstance().getPluginInstallState("com.letv.android.lite") != 1) {
            LogInfo.log("plugin", "重新启动了乐视视频，且liteapp之前未成功安装，重试重新下载");
            JarUtil.updatePlugin(this, 1, true);
        }
        if (LetvApplication.getInstance().hasInited()) {
            onAppInited();
        } else {
            startAppInitThread();
        }
        new RetentionRateUtils().doRequest(1);
        if (LetvConfig.isUmeng()) {
            AnalyticsConfig.setChannel(LetvConfig.getUmengID());
        }
    }

    private void startAppInitThread() {
        new Thread(this) {
            final /* synthetic */ SplashActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                Looper.prepare();
                LetvApplication.getInstance().init();
                this.this$0.mHandler.post(new 1(this));
                Looper.loop();
            }
        }.start();
    }

    private void onAppInited() {
        Handler handler;
        Runnable anonymousClass2;
        int i;
        addDuStatistics();
        if (LetvUtils.getBrandName().equalsIgnoreCase("meizu")) {
            UIsUtils.fullScreen(this);
        }
        if (PreferencesManager.getInstance().isShowNewFeaturesDialog()) {
            handler = this.mHandler;
            anonymousClass2 = new Runnable(this) {
                final /* synthetic */ SplashActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void run() {
                    LogInfo.log("jzzz", "MainActivity");
                    this.this$0.startActivity(new Intent(this.this$0, MainActivity.class));
                    SplashActivity.initLesoApplication();
                    this.this$0.finish();
                }
            };
        } else {
            handler = this.mHandler;
            anonymousClass2 = /* anonymous class already generated */;
        }
        if (LetvUtils.isLongWelcomePcode()) {
            i = 3000;
        } else {
            i = 0;
        }
        handler.postDelayed(anonymousClass2, (long) i);
        LogInfo.log("zhuqiao", "splash init finished");
    }

    private void addDuStatistics() {
        String currPCode = LetvConfig.getPcode();
        if (PreferencesManager.getInstance().getFristApp()) {
            Main.setData("existing", "false");
        } else {
            Main.setData("existing", "true");
            PreferencesManager.getInstance().setFristApp();
        }
        Main.go(this, currPCode, DataUtils.generateDeviceId(this));
    }

    private boolean isNewAppVersion4Leso(int cacheVersion, int curVersion) {
        LogInfo.log("Emerson", "--------------------cacheVersion = " + cacheVersion + "----curVersion = " + curVersion);
        if (curVersion <= cacheVersion) {
            return false;
        }
        PreferencesManager.getInstance().setVersionCode4Leso(curVersion);
        return true;
    }

    public static void initLesoIcon_notification(Context ctx) {
    }

    public void staticsCrashInfo() {
        int crashＣount = PreferencesManager.getInstance().getCrashCount();
        if (crashＣount > 0) {
            LogInfo.LogStatistics("splash crash statistic:" + crashＣount);
            DataStatistics.getInstance().sendErrorInfo(this, "0", "0", "20001", null, "cnt=" + crashＣount, null, null, null, null);
            PreferencesManager.getInstance().setCrashCount(0);
        }
    }

    public void getDefaultImage(final Context context) {
        new Thread(new Runnable(this) {
            final /* synthetic */ SplashActivity this$0;

            public void run() {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    SplashActivity.path = Environment.getExternalStorageDirectory().getPath();
                } else {
                    SplashActivity.path = LetvApplication.getInstance().getDir("updata", 3).getPath();
                }
                FileUtils.copyBigDataToSD(context, SplashActivity.path + "/letv/share");
            }
        }).start();
    }

    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this);
    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        if (LetvConfig.isUmeng()) {
            MobclickAgent.onResume(this);
        }
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        if (LetvConfig.isUmeng()) {
            MobclickAgent.onPause(this);
        }
    }

    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
        ActivityUtils.getInstance().removeActivity(getClass().getName(), false);
    }

    public static void initLesoApplication() {
        AMapLocationTool.getInstance().location();
        if (!isFirstInitLeso) {
            Class clazz_lesoinit = JarLoader.loadClass(LetvApplication.getInstance().getApplicationContext(), "LetvLeso.apk", JarConstant.LETV_LESO_PACKAGENAME, "utils.LesoInitData");
            if (clazz_lesoinit != null && clazz_lesoinit != null) {
                JarLoader.invokeStaticMethod(clazz_lesoinit, "initData", new Class[]{Context.class}, new Object[]{LetvApplication.getInstance().getApplicationContext()});
                isFirstInitLeso = true;
            }
        }
    }

    public void onPermissionsGranted(List<String> perms) {
        LogInfo.log("zhuqiao", "授权成功:" + perms.size());
        if (!BaseTypeUtils.isListEmpty(perms) && perms.size() == EasyPermissions.PERMS.length) {
            PreferencesManager.getInstance().setApplyPermissionsSuccess();
        }
    }

    public void onPermissionsDenied(List<String> perms) {
        LogInfo.log("zhuqiao", "授权失败:" + perms.size());
        UIsUtils.showToast(2131100594);
        finish();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
