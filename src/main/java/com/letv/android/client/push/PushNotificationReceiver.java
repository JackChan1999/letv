package com.letv.android.client.push;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.LiveRoomConfig;
import com.letv.android.client.commonlib.view.LoadingDialog;
import com.letv.android.client.hot.LetvHotActivity;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.bean.PushData;
import com.letv.core.bean.TopicDetailInfoListBean;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LetvConstant;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveDateInfoParser;
import com.letv.core.parser.TopicInfoListParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StatisticsUtils.StatisticsPushData;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.http.LetvHttpConstant;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.SocialConstants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.List;

public class PushNotificationReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_TYPE_ALBUM = 1;
    public static final int NOTIFICATION_TYPE_DEFAULT = 0;
    public static final int NOTIFICATION_TYPE_H5 = 6;
    public static final int NOTIFICATION_TYPE_HOT = 8;
    public static final int NOTIFICATION_TYPE_LIVE = 5;
    public static final int NOTIFICATION_TYPE_LOCAL = 7;
    public static final int NOTIFICATION_TYPE_LOCAL_2 = 9;
    public static final int NOTIFICATION_TYPE_TOPIC = 4;
    public static final int NOTIFICATION_TYPE_VIDEO = 2;
    public static final int NOTIFICATION_TYPE_VIDEO2 = 3;
    private static final String PUSH_REQUEST_SERVER_TIME = "push_request_server_time";
    private String cid;
    boolean isShowToast;
    private String liveEndTime;
    private String livePlayTime;
    private Context mContext;
    private Handler mHandler;
    private LoadingDialog mLoadingDialog;
    long msgId;
    private int pFrom;
    private String playId;
    boolean startRequestServerTime;
    int type;

    public PushNotificationReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.livePlayTime = null;
        this.liveEndTime = null;
        this.cid = null;
        this.playId = null;
        this.msgId = 0;
        this.type = 0;
        this.mHandler = null;
        this.startRequestServerTime = false;
        this.isShowToast = true;
    }

    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        LogInfo.log("+-->", "---------------------------------onReceive");
        BaseApplication.setAppStartTime(System.currentTimeMillis());
        this.mHandler = new Handler(this, this.mContext.getMainLooper()) {
            final /* synthetic */ PushNotificationReceiver this$0;

            public void handleMessage(Message msg) {
                LogInfo.log(LetvHttpConstant.LOG, "handleMessage startRequestServerTime = " + this.this$0.startRequestServerTime);
                if (this.this$0.startRequestServerTime) {
                    Volley.getQueue().cancelWithTag(PushNotificationReceiver.PUSH_REQUEST_SERVER_TIME);
                    this.this$0.isShowToast = false;
                    this.this$0.pushLiveOver();
                }
            }
        };
        LogInfo.log("save_", "LetvApplication.getInstance().isForceUpdating() = " + LetvApplication.getInstance().isForceUpdating());
        if (!LetvApplication.getInstance().isForceUpdating()) {
            try {
                this.msgId = intent.getLongExtra("msgId", 0);
                this.type = intent.getIntExtra("type", 0);
                this.playId = intent.getStringExtra(PageJumpUtil.IN_TO_ALBUM_PID);
                this.pFrom = intent.getIntExtra("pfrom", 0);
                if (StatisticsUtils.sStatisticsPushData == null) {
                    StatisticsUtils.sStatisticsPushData = new StatisticsPushData();
                }
                StatisticsUtils.sStatisticsPushData.mType = String.valueOf(this.type);
                StatisticsUtils.sStatisticsPushData.mAllMsg = intent.getStringExtra("allMsg");
                StatisticsUtils.sStatisticsPushData.mContentType = intent.getStringExtra("contentType");
                if (intent.hasExtra("force")) {
                    boolean isForce = intent.getBooleanExtra("force", false);
                    String fl = intent.getStringExtra("fl");
                    int wz = intent.getIntExtra("wz", 0);
                    if (!TextUtils.isEmpty(fl) && fl.equals("h62")) {
                        LogInfo.log("zhuqiao", "setForcePlay and setFlAndWz");
                        StatisticsUtils.setActionProperty(fl, wz, NetworkUtils.DELIMITER_LINE);
                    }
                }
                if (TextUtils.isEmpty(this.playId)) {
                    this.playId = "0";
                }
                LogInfo.log("PushReceiver", "type =" + this.type + " playId =" + this.playId);
                String code = intent.getStringExtra("code");
                String liveMode = intent.getStringExtra("live_mode");
                this.liveEndTime = null;
                this.cid = null;
                if (this.type == 5) {
                    this.liveEndTime = intent.getStringExtra(PushData.KEY_LIVEENDDATE);
                    this.livePlayTime = intent.getStringExtra("play_time");
                    this.cid = intent.getStringExtra(PushData.KEY_CID);
                }
                BaseApplication.setPushType(this.type);
                LetvApplication.getInstance().setPush(true);
                if (!intent.getBooleanExtra(LetvPushService.INTENT_KEY_FORCE_PUSH, false)) {
                    StatisticsUtils.setActionProperty("c5", -1, PageIdConstant.pushPage);
                }
                if (!(this.type == 5 && TextUtils.isEmpty(liveMode))) {
                    isAppOnForeground(context);
                }
                if (MainActivity.getInstance() != null) {
                    if (this.type == 1) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).create(BaseTypeUtils.stol(this.playId), 0, 13)));
                    } else if (this.type == 2 || this.type == 3) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).create(0, BaseTypeUtils.stol(this.playId), 13)));
                    } else if (this.type == 5) {
                        RxBus.getInstance().send(Integer.valueOf(114));
                        String pName = intent.getStringExtra(Field.PROGRAMNAME);
                        if (TextUtils.isEmpty(liveMode)) {
                            requestServerTime();
                        } else {
                            LogInfo.log("clf", "push...code=" + code + ",id=" + intent.getStringExtra("id") + ",pName=" + pName);
                            if (TextUtils.isEmpty(code) || !LetvUtils.toStringChannelType(Integer.valueOf(liveMode).intValue()).equals("weishi")) {
                                MainLaunchUtils.launchGotoLive(MainActivity.getInstance(), LetvUtils.toStringChannelType(Integer.valueOf(liveMode).intValue()), intent.getStringExtra("id"), false, true);
                            } else {
                                LiveRoomConfig.launchLivePushWeishi(context, code, intent.getStringExtra("id"), false, -1);
                            }
                        }
                    } else if (this.type == 4) {
                        requestTopicPlay(this.playId);
                    } else if (this.type == 6) {
                        if (TextUtils.isEmpty(this.playId)) {
                            UIsUtils.showToast(2131101159);
                        } else {
                            new LetvWebViewActivityConfig(context).launch(this.playId, "");
                        }
                    } else if (this.type == 7) {
                        isAppOnForeground(MainActivity.getInstance() != null ? MainActivity.getInstance() : context);
                    } else if (this.type == 9) {
                        StatisticsUtils.staticticsInfoPost(context, "0", "tc10", null, -1, null, null, null, null, null);
                        LogInfo.LogStatistics("PushNotifacationReceiver....");
                        isAppOnForeground(MainActivity.getInstance() != null ? MainActivity.getInstance() : context);
                    } else if (this.type != 8) {
                        MainLaunchUtils.launch(MainActivity.getInstance(), true);
                    } else if (MainActivity.getInstance() != null) {
                        LetvHotActivity.launch(MainActivity.getInstance(), this.playId, 0, true);
                    } else {
                        MainLaunchUtils.launchToHot(context, this.playId, 0, true);
                    }
                } else if (this.type == 1) {
                    MainLaunchUtils.launchToPlay(context, (long) Integer.parseInt(this.playId), 0, true);
                } else if (this.type == 2 || this.type == 3) {
                    MainLaunchUtils.launchToPlay(context, 0, (long) Integer.parseInt(this.playId), true);
                } else if (this.type == 5) {
                    if (TextUtils.isEmpty(liveMode)) {
                        requestServerTime();
                    } else if (TextUtils.isEmpty(code) || !LetvUtils.toStringChannelType(Integer.valueOf(liveMode).intValue()).equals("weishi")) {
                        MainLaunchUtils.launchGotoLive(context, LetvUtils.toStringChannelType(Integer.valueOf(liveMode).intValue()), intent.getStringExtra("id"), false, true);
                    } else {
                        LiveRoomConfig.launchLivePushWeishi(context, code, intent.getStringExtra("id"), false, -1);
                    }
                } else if (this.type == 4) {
                    MainLaunchUtils.launchToTopicPlay(context, BaseTypeUtils.stol(this.playId), true);
                } else if (this.type == 6) {
                    MainLaunchUtils.launchToWebView(context, this.playId, true);
                } else if (this.type == 7) {
                    LogInfo.log("dialog", "----com.letv.android.client.push.DialogService----");
                    MainLaunchUtils.launch(context, true);
                    DialogService.closePipView(context);
                } else if (this.type == 9) {
                    StatisticsUtils.staticticsInfoPost(context, "0", "tc10", null, -1, null, null, null, null, null);
                    LogInfo.LogStatistics("PushNotifacationReceiver........");
                    LogInfo.log("dialog", "----com.letv.android.client.push.DialogService----");
                    MainLaunchUtils.launch(context, true);
                    DialogService.closePipView(context);
                } else if (this.type == 8) {
                    MainLaunchUtils.launchToHot(context, this.playId, 0, true);
                } else {
                    MainLaunchUtils.launch(context, true);
                }
                if (intent.getBooleanExtra(LetvPushService.INTENT_KEY_FORCE_PUSH, false)) {
                    DialogService.closePipView(context);
                } else if (this.type != 7) {
                    postStatistics(context, intent, this.type, this.playId, "1", Long.toString(this.msgId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean isAppOnForeground(Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        String packageName = mContext.getPackageName();
        LogInfo.log("PushReceiver", "packageName =" + packageName);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            LogInfo.log("PushReceiver", "------appProcesses == null-----");
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            LogInfo.log("PushReceiver", "------appProcess.processName =" + appProcess.processName);
            if (appProcess.processName.equals(packageName) && appProcess.importance == 100) {
                for (RunningTaskInfo rti : activityManager.getRunningTasks(100)) {
                    if (!(rti == null || rti.baseActivity == null || mContext.getPackageName() == null || !mContext.getPackageName().equals(rti.baseActivity.getPackageName()) || VERSION.SDK_INT < 11)) {
                        activityManager.moveTaskToFront(rti.id, 1);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void postStatistics(Context context, Intent intent, int type, String id, String mstype, String msgId) {
        LogInfo.LogStatistics("push 点击");
        if (type != 7) {
            String pageId = this.pFrom != 0 ? PageIdConstant.pushPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.pFrom : PageIdConstant.pushPage;
            String pid = null;
            String vid = null;
            String zid = null;
            String lid = null;
            String url = NetworkUtils.DELIMITER_LINE;
            switch (type) {
                case 1:
                    pid = id;
                    break;
                case 2:
                case 3:
                    vid = id;
                    break;
                case 4:
                    zid = id;
                    break;
                case 5:
                    lid = id;
                    break;
                case 6:
                    url = id;
                    break;
            }
            StatisticsUtils.staticticsInfoPostAddTargetUrl(context, "18", "c5", null, -1, intent.getBooleanExtra(LetvPushService.INTENT_KEY_PUSH_HAS_PIC, false) ? SocialConstants.PARAM_AVATAR_URI : "word", type + "", mstype, msgId, null, pageId, null, pid, vid, url, zid, lid, NetworkUtils.DELIMITER_LINE, this.pFrom);
        }
    }

    private void requestServerTime() {
        Volley.getQueue().cancelWithTag(PUSH_REQUEST_SERVER_TIME);
        this.isShowToast = true;
        this.startRequestServerTime = true;
        this.mHandler.sendEmptyMessageDelayed(0, 3000);
        LogInfo.log(LetvHttpConstant.LOG, "mHandler.sendEmptyMessageDelayed(0, 3 * 1000)");
        new LetvRequest().setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setUrl(LetvUrlMaker.getDataUrl()).setParser(new LiveDateInfoParser()).setTag(PUSH_REQUEST_SERVER_TIME).setCallback(new SimpleResponse<LiveDateInfo>(this) {
            final /* synthetic */ PushNotificationReceiver this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<LiveDateInfo> volleyRequest, LiveDateInfo result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("zhuqiao", "requestDate over");
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.startRequestServerTime = false;
                    if (result == null || TextUtils.isEmpty(this.this$0.liveEndTime) || TextUtils.isEmpty(this.this$0.cid)) {
                        if (MainActivity.getInstance() != null) {
                            MainLaunchUtils.launch(MainActivity.getInstance(), true);
                        } else {
                            MainLaunchUtils.launch(this.this$0.mContext, true);
                        }
                    } else if (result.date.compareTo(this.this$0.liveEndTime) >= 0) {
                        LogInfo.log("push_", "requestDate pushLiveOver");
                        this.this$0.isShowToast = true;
                        this.this$0.pushLiveOver();
                    } else {
                        LogInfo.log(LetvHttpConstant.LOG, "requestDate launchLivePush");
                        if (MainActivity.getInstance() != null) {
                            LiveLaunchUtils.launchLiveById(this.this$0.mContext, this.this$0.playId);
                        } else {
                            MainLaunchUtils.launchToLivePush(this.this$0.mContext, this.this$0.playId, true);
                        }
                    }
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR) {
                    this.this$0.isShowToast = false;
                    this.this$0.pushLiveOver();
                }
            }
        }).add();
    }

    @SuppressLint({"NewApi"})
    private void pushLiveOver() {
        LetvConstant.setForcePlay(false);
        StatisticsUtils.setActionProperty(NetworkUtils.DELIMITER_LINE, -1, NetworkUtils.DELIMITER_LINE);
        if (MainActivity.getInstance() != null) {
            if (this.isShowToast) {
                UIsUtils.showToast(TipUtils.getTipMessage("70004", 2131100703));
            }
            ActivityManager am = (ActivityManager) this.mContext.getSystemService("activity");
            for (RunningTaskInfo rti : am.getRunningTasks(100)) {
                if (!(rti == null || rti.baseActivity == null || this.mContext.getPackageName() == null || !this.mContext.getPackageName().equals(rti.baseActivity.getPackageName()))) {
                    if (VERSION.SDK_INT >= 11) {
                        am.moveTaskToFront(rti.id, 1);
                    }
                    LogInfo.log("push_", "moveTaskToFront rti.id = " + rti.id);
                }
            }
            if (TextUtils.isEmpty(this.cid)) {
                MainLaunchUtils.launch(MainActivity.getInstance(), true);
            } else if (!(this.cid.equals("3") || this.cid.equals("8") || this.cid.equals("4") || this.cid.equals("9"))) {
                MainLaunchUtils.launch(MainActivity.getInstance(), true);
            }
            LogInfo.log("push_", "pushLiveOver live pushCid " + this.cid);
            return;
        }
        if (TextUtils.isEmpty(this.cid)) {
            MainLaunchUtils.launch(this.mContext, true);
        } else if (this.cid.equals("3")) {
            MainLaunchUtils.launchGotoLive(this.mContext, "ent", null, this.isShowToast, true);
        } else if (this.cid.equals("8")) {
            MainLaunchUtils.launchGotoLive(this.mContext, "other", null, this.isShowToast, true);
        } else if (this.cid.equals("4")) {
            MainLaunchUtils.launchGotoLive(this.mContext, "sports", null, this.isShowToast, true);
        } else if (this.cid.equals("9")) {
            MainLaunchUtils.launchGotoLive(this.mContext, "music", null, this.isShowToast, true);
        } else {
            MainLaunchUtils.launch(this.mContext, true);
        }
        LogInfo.log("push_", "pushLiveOver MainActivity.getInstance() == null no jump to live");
    }

    private void requestTopicPlay(final String zid) {
        if (TextUtils.isEmpty(zid)) {
            UIsUtils.showToast(2131101024);
            return;
        }
        showDialog();
        new LetvRequest().setCache(new VolleyDiskCache(TopicDetailInfoListBean.CACHE_KEY_TOPIC_ALBUM_LIST + zid)).setRequestType(RequestManner.CACHE_FAIL_THEN_NETWORK).setParser(new TopicInfoListParser()).setCallback(new SimpleResponse<TopicDetailInfoListBean>(this) {
            final /* synthetic */ PushNotificationReceiver this$0;

            public void onNetworkResponse(VolleyRequest<TopicDetailInfoListBean> volleyRequest, TopicDetailInfoListBean result, DataHull hull, NetworkResponseState state) {
                this.this$0.cancelDialog();
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.onFetchTopicPlayResoinseSuccess(result, zid);
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    UIsUtils.showToast(2131100495);
                } else if (state == NetworkResponseState.NETWORK_ERROR) {
                    UIsUtils.showToast(2131100493);
                } else if (state == NetworkResponseState.RESULT_ERROR || state == NetworkResponseState.RESULT_NOT_UPDATE) {
                    UIsUtils.showToast(2131101024);
                }
            }

            public void onCacheResponse(VolleyRequest<TopicDetailInfoListBean> request, TopicDetailInfoListBean result, DataHull hull, CacheResponseState state) {
                request.setUrl(MediaAssetApi.getInstance().getTopicDeatil(zid, hull.markId));
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.onFetchTopicPlayResoinseSuccess(result, zid);
                }
            }

            public void onErrorReport(VolleyRequest<TopicDetailInfoListBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    private void onFetchTopicPlayResoinseSuccess(TopicDetailInfoListBean result, String zid) {
        if (result.subject != null) {
            cancelDialog();
            if (!BaseTypeUtils.isListEmpty(result.list)) {
                int i = 1;
            }
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mContext).createTopic(BaseTypeUtils.stol(zid), 13)));
            return;
        }
        UIsUtils.showToast(2131101024);
    }

    private void showDialog() {
        if ((this.mLoadingDialog == null || !this.mLoadingDialog.isShowing()) && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && !this.mContext.isRestricted()) {
            try {
                this.mLoadingDialog = new LoadingDialog(this.mContext, 2131100006);
                this.mLoadingDialog.setCancelable(true);
                this.mLoadingDialog.show();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void cancelDialog() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            try {
                this.mLoadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
