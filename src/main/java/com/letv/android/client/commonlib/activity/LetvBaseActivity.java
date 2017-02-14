package com.letv.android.client.commonlib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import cn.com.iresearch.mapptracker.IRMonitor;
import com.flurry.android.FlurryAgent;
import com.letv.android.client.commonlib.R;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.broadcast.HomeKeyEventReceiver;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.fragement.LetvFragmentListener;
import com.letv.android.client.commonlib.messagemodel.RedPackageConfig.RedPackageShareGift;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.network.volley.Volley;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.plugin.pluginloader.util.JarUtil;
import com.letv.pp.utils.NetworkUtils;
import com.letv.redpacketsdk.RedPacketSdk;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.ClickCallBack;
import com.letv.redpacketsdk.callback.RedPacketViewListener;
import com.letv.redpacketsdk.ui.RedPacketForecastView;
import com.letv.redpacketsdk.ui.RedPacketUI;
import com.letv.redpacketsdk.utils.DensityUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

public abstract class LetvBaseActivity extends FragmentActivity implements PermissionCallbacks {
    private static final int PEM_INIT_APP = 101;
    private static boolean isLoginStatatistics = false;
    protected static HomeKeyEventReceiver mHomeKeyEventReceiver;
    private ActivityUtils mActivityUtils = ActivityUtils.getInstance();
    protected Context mContext;
    private boolean mHasDoInit = false;
    protected boolean mKeepSingle = true;
    protected boolean mNeedApplyPermissions = false;
    private RedPacketUI mRedPacketEntry;
    private RedPacketForecastView mRedPacketForecastView;
    private RedPacketFrom mRedPacketFrom;
    protected boolean mSaveFragmentWhenKilled = false;

    public static class BaseActivityOnActivityResult {
        public Intent data;
        public int requestCode;
        public int resultCode;

        public BaseActivityOnActivityResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }
    }

    public abstract Activity getActivity();

    public abstract String getActivityName();

    public abstract String[] getAllFragmentTags();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        if (this.mNeedApplyPermissions && LetvUtils.is60()) {
            doApplyPermissions();
        } else {
            onApplyPermissionsSuccess();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxBus.getInstance().send(new BaseActivityOnActivityResult(requestCode, resultCode, data));
    }

    protected void _setContentView() {
    }

    @AfterPermissionGranted(101)
    private void doApplyPermissions() {
        if (EasyPermissions.hasPermissions(this, EasyPermissions.PERMS)) {
            onApplyPermissionsSuccess();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_granted), 101, EasyPermissions.PERMS);
        }
    }

    public void onPermissionsGranted(List<String> perms) {
        LogInfo.log("zhuqiao", "授权成功:" + perms.size());
        if (!BaseTypeUtils.isListEmpty(perms) && perms.size() == EasyPermissions.PERMS.length) {
            onApplyPermissionsSuccess();
        }
    }

    public void onPermissionsDenied(List<String> perms) {
        LogInfo.log("zhuqiao", "授权失败:" + perms.size());
        UIsUtils.showToast(R.string.permissions_auth);
        finish();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected void onApplyPermissionsSuccess() {
        if (!this.mHasDoInit) {
            this.mHasDoInit = true;
            JarUtil.sHasApplyPermissionsSuccess = true;
            PreferencesManager.getInstance().setApplyPermissionsSuccess();
            _setContentView();
            if (!BaseApplication.getInstance().hasInited()) {
                BaseApplication.getInstance().init();
            }
            if (!BaseApplication.getInstance().isCdeStarting()) {
                BaseApplication.getInstance().startCde();
            }
            this.mContext = this;
            if (this.mKeepSingle) {
                this.mActivityUtils.addActivity(getActivityName(), getActivity());
            }
            LogInfo.log("clf", "红包开关 是否使用reaPackageSDK=" + PreferencesManager.getInstance().getRedPackageSDK());
            if (!"0".equals(PreferencesManager.getInstance().getRedPackageSDK())) {
                initRedPacketView();
            }
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_REQUEST_LINK));
        }
    }

    protected void registerHomeKeyEventReceiver() {
        try {
            mHomeKeyEventReceiver = new HomeKeyEventReceiver();
            registerReceiver(mHomeKeyEventReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void unRegisterHomeKeyEventReceiver() {
        try {
            unregisterReceiver(mHomeKeyEventReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends View> T getViewById(int id) {
        return findViewById(id);
    }

    protected <T extends View> T getViewById(View parent, int id) {
        return parent.findViewById(id);
    }

    public void addFragment(Fragment fragment) {
        addFragments(fragment);
    }

    public void addFragments(Fragment... fragments) {
        if (fragments != null && fragments.length != 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (Fragment fragment : fragments) {
                if (fragment instanceof LetvFragmentListener) {
                    LetvFragmentListener listener = (LetvFragmentListener) fragment;
                    String tag = listener.getTagName();
                    if (!TextUtils.isEmpty(tag)) {
                        int fragmentRes = listener.getContainerId();
                        if (fragmentRes > 0 && fragmentManager.findFragmentByTag(tag) == null) {
                            transaction.add(fragmentRes, fragment, tag);
                        }
                    }
                }
            }
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showFragment(Fragment fragment) {
        showFragmentIfNeeded(fragment, false);
    }

    protected void showFragmentIfNeeded(Fragment fragment) {
        showFragmentIfNeeded(fragment, true);
    }

    public void showFragmentIfNeeded(Fragment fragment, boolean hideOrRemoveOthers) {
        if (fragment instanceof LetvFragmentListener) {
            LetvFragmentListener listener = (LetvFragmentListener) fragment;
            String tag = listener.getTagName();
            if (!TextUtils.isEmpty(tag)) {
                String[] tags = getAllFragmentTags();
                if (tags != null) {
                    int fragmentRes = listener.getContainerId();
                    if (fragmentRes > 0) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        if (fragmentManager.findFragmentByTag(tag) == null) {
                            transaction.add(fragmentRes, fragment, tag);
                        }
                        transaction.show(fragment);
                        listener.onShow();
                        if (hideOrRemoveOthers) {
                            for (String hideTag : tags) {
                                if (!TextUtils.equals(tag, hideTag)) {
                                    Fragment fragmentNeedHide = fragmentManager.findFragmentByTag(hideTag);
                                    if (fragmentNeedHide instanceof LetvFragmentListener) {
                                        int flag = ((LetvFragmentListener) fragmentNeedHide).getDisappearFlag();
                                        if (flag == 1) {
                                            transaction.remove(fragmentNeedHide);
                                        } else if (flag == 0) {
                                            transaction.hide(fragmentNeedHide);
                                            ((LetvFragmentListener) fragmentNeedHide).onHide();
                                        }
                                    }
                                }
                            }
                        }
                        try {
                            transaction.commitAllowingStateLoss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    protected void deleteAllFragments() {
        if (getAllFragmentTags() != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (!BaseTypeUtils.isListEmpty(fragmentManager.getFragments())) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                for (Fragment fragment : fragmentManager.getFragments()) {
                    transaction.remove(fragment);
                }
                try {
                    transaction.commitAllowingStateLoss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void hideFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(fragment);
            if (fragment instanceof LetvBaseFragment) {
                ((LetvBaseFragment) fragment).onHide();
            }
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideFragment(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            hideFragment(getSupportFragmentManager().findFragmentByTag(tag));
        }
    }

    protected boolean hasApplyPermissions() {
        return !LetvUtils.is60() || PreferencesManager.getInstance().isApplyPermissionsSuccess();
    }

    protected void onStart() {
        if (mHomeKeyEventReceiver != null && mHomeKeyEventReceiver.isHomeClicked()) {
            BaseApplication.setAppStartTime(System.currentTimeMillis());
        }
        super.onStart();
        if (hasApplyPermissions()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA));
            FlurryAgent.onStartSession(this, LetvConfig.getFlurryKey());
            if (mHomeKeyEventReceiver != null && mHomeKeyEventReceiver.isHomeClicked()) {
                StatisticsUtils.sHasStatisticsLaunch = false;
                isLoginStatatistics = false;
                statisticsLaunch(0, true);
                LogInfo.LogStatistics("app start from home");
            }
        }
    }

    protected void initRedPacketView() {
        if (this.mRedPacketEntry == null) {
            RedPacketSdk instance = RedPacketSdk.getInstance();
            this.mRedPacketFrom = new RedPacketFrom(1);
            this.mRedPacketEntry = (RedPacketUI) instance.getRedPacketView(this, new RedPacketViewListener() {
                public void show() {
                    LogInfo.log("RedPacket", "LetvBaseActivty收到红包回调show");
                    boolean flag = false;
                    LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(600, LetvBaseActivity.this.mRedPacketFrom));
                    if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class)) {
                        flag = ((Boolean) response.getData()).booleanValue();
                    }
                    if (flag) {
                        if (LetvBaseActivity.this.mRedPacketForecastView != null) {
                            LetvBaseActivity.this.mRedPacketForecastView.setVisibility(8);
                        }
                        LetvBaseActivity.this.mRedPacketEntry.setVisibility(0);
                        LetvBaseActivity.this.statisticsForRedPacket();
                        return;
                    }
                    LetvBaseActivity.this.mRedPacketEntry.setVisibility(8);
                }

                public void hide() {
                    LogInfo.log("RedPacket", "LetvBaseActivty收到红包回调hide");
                    LetvBaseActivity.this.mRedPacketEntry.setVisibility(8);
                }

                public void share(String url, String picUrl, String title) {
                    LeMessageManager.getInstance().dispatchMessage(LetvBaseActivity.this.getActivity(), new LeMessage(LeMessageIds.MSG_RED_PACKAGE_SHARE_GIFT, new RedPackageShareGift(LetvBaseActivity.this.mRedPacketEntry.getResultDialog(), url, picUrl, title)));
                }

                public void gotoGiftPage(String url) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_RED_PACKAGE_LAUNCH_GIFT, LetvBaseActivity.this.getActivity()));
                }

                public void gotoWeb(String url) {
                    LeMessageManager.getInstance().dispatchMessage(LetvBaseActivity.this.getActivity(), new LeMessage(LeMessageIds.MSG_RED_PACKAGE_LAUNCH_WEB, url));
                }

                public void showToast() {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_RED_PACKAGE_SHOW_TOAST));
                }
            });
            ((ViewGroup) getWindow().getDecorView()).addView(this.mRedPacketEntry);
            setRedPacketEntryLocation(getResources().getConfiguration().orientation == 2);
            this.mRedPacketEntry.setVisibility(8);
            instance.updateRedPacketUI(this.mRedPacketEntry);
            this.mRedPacketEntry.setOnClickCallBack(new ClickCallBack() {
                public void onClick() {
                    String giftId = "";
                    if (RedPacketSdkManager.getInstance().hasRedPacket() && RedPacketSdkManager.getInstance().getRedPacketBean() != null) {
                        giftId = RedPacketSdkManager.getInstance().getRedPacketBean().giftId;
                    }
                    StatisticsUtils.statisticsActionInfo(LetvBaseActivity.this.mContext, LetvBaseActivity.this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(LetvBaseActivity.this.mRedPacketFrom.pageid) : NetworkUtils.DELIMITER_LINE, "0", "rpid10", null, -1, "&rpid=" + DataUtils.getUnEmptyData(giftId));
                }
            });
        }
    }

    public void setRedPacketEntryLocation(boolean isLandspace) {
        if (this.mRedPacketEntry != null) {
            LayoutParams params = (LayoutParams) this.mRedPacketEntry.getLayoutParams();
            if (isLandspace) {
                params.gravity = 83;
                params.leftMargin = DensityUtil.dip2px(this, 23.0f);
                params.width = DensityUtil.px2dip(this, 70.0f);
                params.height = DensityUtil.px2dip(this, 70.0f);
            } else {
                params.gravity = 85;
                params.rightMargin = DensityUtil.dip2px(this, 23.0f);
                params.width = DensityUtil.px2dip(this, 87.0f);
                params.height = DensityUtil.px2dip(this, 87.0f);
            }
            params.bottomMargin = DensityUtil.dip2px(this, 57.0f);
            this.mRedPacketEntry.setLayoutParams(params);
        }
    }

    protected void setRedPacketForecastView(RedPacketForecastView view) {
        this.mRedPacketForecastView = view;
    }

    public void removeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().startShake(getActivityName());
        if (this.mRedPacketEntry != null) {
            this.mRedPacketEntry.onResume();
        }
        if (mHomeKeyEventReceiver != null) {
            mHomeKeyEventReceiver.setIsHomeClicked(false);
        }
        try {
            if (LetvUtils.isAppOnForeground(this.mContext) && !isLoginStatatistics) {
                isLoginStatatistics = true;
                statisticsFirstLaunch();
            }
            IRMonitor.getInstance().onResume(this);
            if (LetvConfig.isUmeng()) {
                AnalyticsConfig.setChannel(LetvConfig.getUmengID());
                MobclickAgent.onResume(this);
            }
        } catch (Exception e) {
            LogInfo.LogStatistics("main activity on resume exception:" + e.getMessage());
        } catch (OutOfMemoryError e2) {
            BaseApplication.getInstance().onAppMemoryLow();
        }
    }

    protected void onPause() {
        super.onPause();
        if (hasApplyPermissions()) {
            IRMonitor.getInstance().onPause(this);
            if (LetvConfig.isUmeng()) {
                MobclickAgent.onPause(this);
            }
            BaseApplication.getInstance().stopShake();
            ImageDownloader.getInstance().fluchCache();
            Volley.getInstance().flush();
        }
    }

    protected void onStop() {
        super.onStop();
        if (hasApplyPermissions()) {
            if (this.mRedPacketEntry != null) {
                this.mRedPacketEntry.stop();
            }
            FlurryAgent.onEndSession(this);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (BaseTypeUtils.isListEmpty(getSupportFragmentManager().getFragments()) || this.mSaveFragmentWhenKilled) {
            super.onSaveInstanceState(outState);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (hasApplyPermissions()) {
            if (this.mRedPacketEntry != null) {
                this.mRedPacketEntry.destroy();
            }
            if (this.mKeepSingle) {
                this.mActivityUtils.removeActivity(getActivityName(), false);
            }
            if (!LetvUtils.isAppOnForeground(this.mContext) && isLoginStatatistics) {
                StatisticsUtils.statisticsLoginAndEnv(this.mContext, 1, true);
                BaseApplication.setAppStartTime(0);
                if (!BaseApplication.getInstance().mIsMainActivityAlive) {
                    StatisticsUtils.clearStatisticsInfo(this.mContext);
                }
                isLoginStatatistics = false;
            }
        }
    }

    protected void statisticsLaunch(int waitTime, boolean isFromHomeBack) {
        if (BaseApplication.getAppStartTime() != 0 && !StatisticsUtils.sHasStatisticsLaunch) {
            String clientOpenTime = StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - BaseApplication.getAppStartTime()) + "";
            StatisticsUtils.statisticsAppLaunch(this.mContext, clientOpenTime, String.valueOf(waitTime), StringUtils.timeClockString("yyyyMMdd_HH:mm:ss"), isFromHomeBack, PageIdConstant.index);
            LogInfo.LogStatistics("开机启动:type1=" + clientOpenTime + "   startTime=" + BaseApplication.getAppStartTime());
            BaseApplication.setAppStartTime(0);
        }
    }

    public RedPacketUI getBaseRedPacket() {
        return this.mRedPacketEntry;
    }

    public void setRedPacketFrom(RedPacketFrom redPacketFrom) {
        this.mRedPacketFrom = redPacketFrom;
        if (this.mRedPacketEntry != null) {
            RedPacketSdk.getInstance().updateRedPacketUI(this.mRedPacketEntry);
        }
    }

    public void statisticsFirstLaunch() {
        LogInfo.LogStatistics("app start");
        StatisticsUtils.statisticsLoginAndEnv(this.mContext, 0, false);
        StatisticsUtils.statisticsLoginAndEnv(this.mContext, 0, true);
        StatisticsUtils.statisticsIRDeviceId(this);
        StatisticsUtils.initIRVideo(this);
        statisticsLaunch(0, false);
    }

    public void statisticsForRedPacket() {
        RedPacketBean redPacketBean = RedPacketSdkManager.getInstance().getRedPacketBean();
        if (redPacketBean != null) {
            String lastRpid = PreferencesManager.getInstance().getRedPacketIdForStatistics();
            LogInfo.log("wch", "lastrpid=" + lastRpid + ",currentrpid=" + redPacketBean.id + ",giftid=" + redPacketBean.giftId);
            if (!TextUtils.isEmpty(redPacketBean.id)) {
                if (TextUtils.isEmpty(lastRpid) || !lastRpid.equals(redPacketBean.id)) {
                    PreferencesManager.getInstance().setRedPacketIdForStatistics(redPacketBean.id);
                    StatisticsUtils.statisticsActionInfo(this.mContext, this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(this.mRedPacketFrom.pageid) : NetworkUtils.DELIMITER_LINE, "19", "rpid10", null, -1, "&rpid=" + DataUtils.getUnEmptyData(redPacketBean.giftId), this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(this.mRedPacketFrom.cid) : NetworkUtils.DELIMITER_LINE, this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(this.mRedPacketFrom.pid) : NetworkUtils.DELIMITER_LINE, null, this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(this.mRedPacketFrom.zid) : NetworkUtils.DELIMITER_LINE, this.mRedPacketFrom != null ? DataUtils.getUnEmptyData(this.mRedPacketFrom.content) : NetworkUtils.DELIMITER_LINE);
                }
            }
        }
    }
}
