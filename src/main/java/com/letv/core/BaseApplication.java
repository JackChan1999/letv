package com.letv.core;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.bean.DataStatusInfoBean;
import com.letv.core.bean.FloatBallBean;
import com.letv.core.bean.IPBean;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.bean.VideoListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.manager.PackageHelper;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.pp.func.CdeHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import rx.subscriptions.CompositeSubscription;

public class BaseApplication extends MultiDexApplication {
    public static final String CDELOG1 = (Environment.getExternalStorageDirectory() + "/letv/exceptionInfo/cde.txt");
    private static final String CDE_APPID = "3000";
    public static String CDE_PARAMS = "";
    public static final String CDE_SERVICE_NAME = "com.letv.pp.service.CdeService";
    private static final long GC_INTERVAL = 30000;
    protected static final String MAIN_PROCESS_NAME = "com.letv.android.client";
    public static final int MEMORY_LOW_RETRY_MAX_COUNT = 3;
    protected static final String PIP_PROCESS_NAME = "com.letv.android.client:windowplay";
    public static BaseApplication instance;
    private static Map<Class<?>, Class<?>> mActivityMap = new HashMap();
    protected static int pushType;
    protected static long sAppStartTime = 0;
    public static Class<?> sColorCls;
    public static float sDensity;
    public static Class<?> sDimenCls;
    public static Class<?> sDrawableCls;
    public static int sHeight;
    public static boolean sIsChangeStream = true;
    public static int sRawHeight;
    public static Class<?> sStringCls;
    public static Class<?> sStyleCls;
    public static int sWidth;
    protected boolean WxisShare = false;
    public Object iAdJSBridge;
    protected boolean isFromHalf = false;
    protected boolean isMI3Phone = false;
    protected boolean isPlayerFavouriteClick = false;
    protected boolean isPush = false;
    protected boolean isSettingPlayLevel = false;
    private long lastGcTime = 0;
    public Intent mCallLiteIntent;
    protected CdeHelper mCdeHelper;
    private ChannelListBean mChannelList = new ChannelListBean();
    private ChannelListBean mChannelMore = new ChannelListBean();
    private DataStatusInfoBean mDataStatusInfo;
    protected boolean mDefaultHardStreamDecorder = false;
    protected int mDefaultLevel = 0;
    private ArrayList<FloatBallBean> mFloatBallBeanList;
    protected boolean mHasInited = false;
    public boolean mHasLoadDrmSo = false;
    private boolean mHasNavigationBar;
    protected IPBean mIp;
    protected boolean mIsAdsPinjie = true;
    public boolean mIsAlbumActivityAlive = false;
    protected boolean mIsForceUpdating = false;
    public boolean mIsMainActivityAlive = false;
    public boolean mIsPluginInitedSuccess;
    protected boolean mIsShack = false;
    protected boolean mIsShowNav = true;
    protected boolean mIsVipTagShow = false;
    private LiveDateInfo mLiveDateInfo;
    protected Bundle mLiveLunboBundle = null;
    protected long mLogInTime = 0;
    private int mNavigationBarLandscapeWidth;
    public CompositeSubscription mSubscription;
    protected int mSupportLevel = 0;
    public UnicomFreeInfoCache mUnicomFreeInfoCache = null;
    protected String mVideoFormat = "ios";
    private VideoListBean mVideoListBean;
    protected int memoryPlayLevel = -1;
    protected boolean showRecordFlag = true;

    public class UnicomFreeInfoCache {
        public boolean mIsOrder;
        public boolean mIsSupportProvince;
        public String mPhoneNumber;
    }

    public UnicomFreeInfoCache getUnicomFreeInfoCache() {
        if (this.mUnicomFreeInfoCache == null) {
            this.mUnicomFreeInfoCache = new UnicomFreeInfoCache();
        }
        return this.mUnicomFreeInfoCache;
    }

    public ArrayList<FloatBallBean> getmFloatBallBeanList() {
        return this.mFloatBallBeanList;
    }

    public void setmFloatBallBeanList(ArrayList<FloatBallBean> mFloatBallBeanList) {
        this.mFloatBallBeanList = mFloatBallBeanList;
    }

    public static synchronized BaseApplication getInstance() {
        BaseApplication baseApplication;
        synchronized (BaseApplication.class) {
            baseApplication = instance;
        }
        return baseApplication;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        this.mHasNavigationBar = UIsUtils.hasNavigationBar(this);
        this.mNavigationBarLandscapeWidth = UIsUtils.getNavigationBarLandscapeWidth(this);
        CDE_PARAMS = "port=6990&app_id=3000&ostype=android&channel_default_multi=0&log_type=4&log_file=" + CDELOG1 + "&channel_default_multi=1&channel_max_count=2&dcache_enabled=1&dcache_capacity=80&show_letv_cks=1&app_version=" + LetvUtils.getClientVersionName();
        this.mSubscription = new CompositeSubscription();
        PackageHelper.loadAllStaticClass();
        initIntentTask();
    }

    public boolean hasNavigationBar() {
        return this.mHasNavigationBar;
    }

    public int getNavigationBarLandscapeWidth() {
        return this.mNavigationBarLandscapeWidth;
    }

    protected boolean isMainProcess() {
        return TextUtils.equals(LetvUtils.getProcessName(this, Process.myPid()), MAIN_PROCESS_NAME);
    }

    protected boolean isPipProcess() {
        return TextUtils.equals(LetvUtils.getProcessName(this, Process.myPid()), PIP_PROCESS_NAME);
    }

    public void init() {
        initScreenInfo();
        this.mHasInited = true;
    }

    @TargetApi(17)
    public void initScreenInfo() {
        Display display = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        sWidth = dm.widthPixels;
        sHeight = dm.heightPixels;
        sDensity = dm.density;
        if (LetvUtils.getSDKVersion() >= 17) {
            display.getRealMetrics(dm);
            sRawHeight = dm.heightPixels;
            return;
        }
        sRawHeight = sHeight;
    }

    private void initIntentTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(1, new TaskRunnable() {
            public LeResponseMessage run(LeMessage message) {
                LogInfo.log(LeMessageManager.TAG, "页面跳转 task run...");
                if (LeMessage.checkMessageValidity(message, LeIntentConfig.class)) {
                    LeIntentConfig config = (LeIntentConfig) message.getData();
                    config.setComponentClass((Class) BaseApplication.mActivityMap.get(config.getClass()));
                    config.run();
                }
                return null;
            }
        }));
    }

    public void registerActivity(Class<?> congifCls, Class<?> activityCls) {
        mActivityMap.put(congifCls, activityCls);
    }

    public Map<Class<?>, Class<?>> getActivityMap() {
        return mActivityMap;
    }

    public boolean hasInited() {
        return this.mHasInited;
    }

    public static long getAppStartTime() {
        return sAppStartTime;
    }

    public static void setAppStartTime(long time) {
        sAppStartTime = time;
    }

    public IPBean getIp() {
        return this.mIp;
    }

    public void setIp(IPBean ip) {
        this.mIp = ip;
    }

    public boolean getShowRecordFlag() {
        return this.showRecordFlag;
    }

    public boolean isPush() {
        return this.isPush;
    }

    public void setPush(boolean isPush) {
        this.isPush = isPush;
    }

    public boolean isSettingPlayLevel() {
        return this.isSettingPlayLevel;
    }

    public void setSettingPlayLevel(boolean isSetPlayLevel) {
        this.isSettingPlayLevel = isSetPlayLevel;
    }

    public int getMemoryPlayLevel() {
        return this.memoryPlayLevel;
    }

    public void setMemoryPlayLevel(int memoryPlayLevel) {
        this.memoryPlayLevel = memoryPlayLevel;
    }

    public Bundle getLiveLunboBundle() {
        return this.mLiveLunboBundle;
    }

    public void setLiveLunboBundle(Bundle liveLunboBundle) {
        this.mLiveLunboBundle = liveLunboBundle;
    }

    public CdeHelper getCdeHelper() {
        return this.mCdeHelper;
    }

    public void startCde() {
    }

    public void stopCde() {
    }

    public boolean isCdeStarting() {
        return this.mCdeHelper != null && this.mCdeHelper.isReady();
    }

    public static void setPushType(int type) {
        pushType = type;
    }

    public void setPlayerFavouriteClick(boolean isPlayerFavouriteClick) {
        this.isPlayerFavouriteClick = isPlayerFavouriteClick;
    }

    public boolean isVipTagShow() {
        return this.mIsVipTagShow;
    }

    public void setVipTagShow(boolean isVipTagShow) {
        this.mIsVipTagShow = isVipTagShow;
    }

    public void setFromHalf(boolean isFromHalf) {
        this.isFromHalf = isFromHalf;
    }

    public boolean isShow() {
        return this.mIsShowNav;
    }

    public void setShow(boolean isShow) {
        this.mIsShowNav = isShow;
    }

    public void setAdsPinjie(boolean pinjie) {
        this.mIsAdsPinjie = pinjie;
    }

    public boolean getPinjie() {
        return this.mIsAdsPinjie;
    }

    public void setIsShack(boolean isShack) {
        PreferencesManager.getInstance().setIsShack(isShack);
        this.mIsShack = isShack;
    }

    public boolean isForceUpdating() {
        return this.mIsForceUpdating;
    }

    public boolean isWxisShare() {
        return this.WxisShare;
    }

    public void setWxisShare(boolean wxisShare) {
        this.WxisShare = wxisShare;
    }

    public boolean getDefaultHardStreamDecorder() {
        return this.mDefaultHardStreamDecorder;
    }

    public String getVideoFormat() {
        String model = LetvUtils.getModelName();
        LogInfo.log("wxf", "playLibsVideoFormat/model:" + model);
        if ("LG-P970".equals(model)) {
            this.mVideoFormat = "no";
            LogInfo.log("wxf", "--XT910 change videoFormat to no");
        }
        this.isMI3Phone = false;
        return this.mVideoFormat;
    }

    public int getSuppportTssLevel() {
        return this.mSupportLevel;
    }

    public int getDefaultLevel() {
        return this.mDefaultLevel;
    }

    public void setLogInTime(long logInTime) {
        this.mLogInTime = logInTime;
    }

    public DataStatusInfoBean getDataStatusInfo() {
        return this.mDataStatusInfo;
    }

    public void setDataStatusInfo(DataStatusInfoBean info) {
        this.mDataStatusInfo = info;
    }

    public void setBritness(float bri) {
        PreferencesManager.getInstance().setBritness(bri);
    }

    public float getBritness() {
        return PreferencesManager.getInstance().getBritness();
    }

    public VideoListBean getVideoListPlayerLibs() {
        return this.mVideoListBean;
    }

    public void setmVideoList(VideoListBean videoList) {
        this.mVideoListBean = videoList;
    }

    public LiveDateInfo getLiveDateInfo() {
        if (this.mLiveDateInfo == null) {
            this.mLiveDateInfo = new LiveDateInfo();
            this.mLiveDateInfo.date = StringUtils.timeStringAll(System.currentTimeMillis());
            this.mLiveDateInfo.week_day = StringUtils.getLocalWeekDay() + "";
        }
        return this.mLiveDateInfo;
    }

    public void setLiveDateInfo(LiveDateInfo liveDateInfo) {
        this.mLiveDateInfo = liveDateInfo;
    }

    public ChannelListBean getChannelList() {
        return this.mChannelList;
    }

    public void setChannelList(ChannelListBean mChannelList) {
        this.mChannelList = mChannelList;
    }

    public ChannelListBean getChannelMoreList() {
        return this.mChannelMore;
    }

    public void setChannelMoreList(ChannelListBean channelList) {
        this.mChannelMore = channelList;
    }

    public int setVType() {
        return 0;
    }

    public void startShake(String className) {
    }

    public void stopShake() {
    }

    public void onAppMemoryLow() {
        long curTime = System.currentTimeMillis();
        if (curTime - this.lastGcTime > GC_INTERVAL) {
            this.lastGcTime = curTime;
            System.gc();
            Runtime.getRuntime().gc();
        }
    }
}
