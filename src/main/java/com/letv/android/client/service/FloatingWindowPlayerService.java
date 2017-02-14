package com.letv.android.client.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.controller.FloatingWindowPlayerController;
import com.letv.android.client.model.VideoAttributes;
import com.letv.android.client.view.FloatingWindowPlayerView;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.LanguageSettings;
import com.letv.core.bean.PlayRecord;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FloatingWindowPlayerService extends Service {
    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static final String METHOD_NAME_SETFORGROUND = "setForeground";
    private static final String METHOD_NAME_STARTFORGROUND = "startForeground";
    private static final String METHOD_NAME_STOPFORGROUND = "stopForeground";
    public static final int NOTIFICATION_ID = 109001;
    private static final String TAG = "FloatingWindowPlayer";
    private static final Class<?>[] mSetForegroundSignature = new Class[]{Boolean.TYPE};
    private static final Class<?>[] mStartForegroundSignature = new Class[]{Integer.TYPE, Notification.class};
    private static final Class<?>[] mStopForegroundSignature = new Class[]{Boolean.TYPE};
    private FloatingWindowPlayerController mFloatingWindowPlayerController;
    private FloatingWindowPlayerView mFloatingWindowPlayerView;
    private Method mMethodSetForeground;
    private Method mMethodStartForeground;
    private Method mMethodStopForeground;
    protected BroadcastReceiver mNetworkChangedReceiver;
    private NotificationManager mNotificationManager;
    private int mOldNetworkState;
    private boolean mPausedByEvent;
    private PhoneStateListener mPhoneStateListener;
    public BroadcastReceiver mScreenStateReceiver;
    private Object[] mSetForegroundArgs;
    private Object[] mStartForegroundArgs;
    private Object[] mStopForegroundArgs;
    private VideoAttributes mVideoParams;
    public VipAuthPassReceiver mVipAuthPassReceiver;
    private WindowManager mWindowManager;

    private class VipAuthPassReceiver extends BroadcastReceiver {
        private VipAuthPassReceiver() {
        }

        /* synthetic */ VipAuthPassReceiver(FloatingWindowPlayerService x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void onReceive(Context context, Intent intent) {
            LogInfo.log(FloatingWindowPlayerService.TAG, "接收到广播消息");
            if (intent.getAction().equals(VipProductContant.ACTION_VIP_AUTH_PASS)) {
                LogInfo.log(FloatingWindowPlayerService.TAG, "接收到会员鉴权通过消息");
                FloatingWindowPlayerService.this.mFloatingWindowPlayerView.removeVipAuthenticationView();
                FloatingWindowPlayerService.this.mFloatingWindowPlayerView.getPlayLoadLayout().loading(true);
                FloatingWindowPlayerService.this.mFloatingWindowPlayerController.requestVideo(false);
            }
        }
    }

    public FloatingWindowPlayerService() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mSetForegroundArgs = new Object[1];
        this.mStartForegroundArgs = new Object[2];
        this.mStopForegroundArgs = new Object[1];
        this.mPausedByEvent = false;
        this.mPhoneStateListener = new PhoneStateListener(this) {
            final /* synthetic */ FloatingWindowPlayerService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case 0:
                        this.this$0.resumePlay();
                        break;
                    case 1:
                        this.this$0.pausePlay();
                        break;
                    case 2:
                        this.this$0.pausePlay();
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        this.mNetworkChangedReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ FloatingWindowPlayerService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                boolean changed = false;
                if (!this.this$0.isPlayingCacheVideo()) {
                    int state = NetworkUtils.getNetworkType();
                    if (this.this$0.mOldNetworkState != state) {
                        changed = true;
                    }
                    LogInfo.log("CRL FLOATING change == " + changed);
                    if (changed) {
                        LetvMediaPlayerControl mpc = this.this$0.mFloatingWindowPlayerController.getMediaPlayerController();
                        PlayLoadLayout pl = this.this$0.mFloatingWindowPlayerView.getPlayLoadLayout();
                        if (mpc != null && pl != null) {
                            if (NetworkUtils.getAvailableNetworkInfo() != null) {
                                if (NetworkUtils.isWifi()) {
                                    UIsUtils.showToast(TipUtils.getTipMessage("100007", 2131100012));
                                } else if (!this.this$0.mFloatingWindowPlayerController.mIsWo3GUser) {
                                    mpc.pause();
                                    this.this$0.mFloatingWindowPlayerView.getPlayLoadLayout().requestNotWifi();
                                    return;
                                }
                                if (!mpc.isPlaying()) {
                                    pl.loading(true);
                                    this.this$0.mFloatingWindowPlayerController.requestVideo(false);
                                }
                            } else {
                                mpc.pause();
                                this.this$0.mFloatingWindowPlayerView.getPlayLoadLayout().requestError();
                                this.this$0.mFloatingWindowPlayerController.sendPlayingEndMessage();
                                this.this$0.mFloatingWindowPlayerView.getBackgroundView().setVisibility(0);
                            }
                            this.this$0.mOldNetworkState = state;
                        }
                    }
                }
            }
        };
        this.mScreenStateReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ FloatingWindowPlayerService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    this.this$0.mFloatingWindowPlayerController.setScreenState(0);
                    this.this$0.pausePlay();
                } else if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                    this.this$0.mFloatingWindowPlayerController.setScreenState(1);
                    this.this$0.resumePlay();
                }
            }
        };
        this.mVipAuthPassReceiver = new VipAuthPassReceiver();
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public static void start(Context context, Bundle bundle) {
        LogInfo.log("zhaosumin", "FloatingWindowPlayerService start ");
        Intent intent = new Intent(context, FloatingWindowPlayerService.class);
        intent.putExtra(LetvConstant.Intent.Bundle.PLAY, bundle);
        context.startService(intent);
    }

    public void onCreate() {
        LogInfo.log(TAG, "onCreate");
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        this.mWindowManager = (WindowManager) getSystemService("window");
        configService();
        LetvApplication.getInstance().startCde();
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogInfo.log(TAG, "onStartCommand");
        if (intent == null) {
            stopSelf();
            return -1;
        }
        if (!(this.mWindowManager == null || this.mFloatingWindowPlayerView == null)) {
            this.mFloatingWindowPlayerController.onFinish();
            this.mWindowManager.removeView(this.mFloatingWindowPlayerView);
            this.mFloatingWindowPlayerView = null;
        }
        this.mOldNetworkState = NetworkUtils.getNetworkType();
        Bundle bundle = intent.getBundleExtra(LetvConstant.Intent.Bundle.PLAY);
        this.mVideoParams = parseVideoParams(bundle);
        this.mFloatingWindowPlayerView = new FloatingWindowPlayerView(this);
        this.mFloatingWindowPlayerView.setVideoParams(this.mVideoParams);
        this.mFloatingWindowPlayerView.setWindowManager(this.mWindowManager);
        LogInfo.log(TAG, "Service中设置FloatingWindowPlayerController");
        this.mFloatingWindowPlayerView.load();
        this.mFloatingWindowPlayerController = this.mFloatingWindowPlayerView.getFloatingWindowPlayerController();
        this.mFloatingWindowPlayerController.setBundle(bundle);
        registerBroadcastReceiver();
        return 3;
    }

    private VideoAttributes parseVideoParams(Bundle bundle) {
        VideoAttributes attrs = new VideoAttributes();
        attrs.format = bundle.getString(LetvConstant.Intent.Bundle.VIDEO_FORMAT);
        attrs.launchMode = bundle.getInt("launch_mode");
        attrs.url = bundle.getString("url");
        attrs.isDolby = bundle.getBoolean(FloatingWindowPlayerController.BUNDLE_PARAM_IS_DOLBY);
        attrs.isLive = bundle.getBoolean("isLive");
        attrs.albumTitle = bundle.getString(DownloadAlbumTable.COLUMN_ALBUMTITLE);
        attrs.episodeTitle = bundle.getString("episodetitle");
        attrs.order = bundle.getString("order");
        attrs.aid = bundle.getLong("aid");
        attrs.vid = bundle.getLong("vid", 0);
        attrs.mid = bundle.getString("mid");
        attrs.type = bundle.getInt("type");
        attrs.curPage = bundle.getInt("curPage");
        attrs.seek = bundle.getLong("seek");
        LogInfo.log(TAG, "传给service的进度 = " + attrs.seek);
        attrs.isFromLocal = bundle.getBoolean(FloatingWindowPlayerController.BUNDLE_PARAM_IS_FROM_LOCAL);
        Type type = genVideoType(attrs);
        attrs.album = (AlbumInfo) bundle.getSerializable("album");
        attrs.mediaType = type;
        attrs.playRecord = (PlayRecord) bundle.getSerializable("playRecord");
        attrs.albumCardList = (AlbumCardList) bundle.getSerializable("album_card_list");
        attrs.albumPageCard = (AlbumPageCard) bundle.getSerializable("album_page_card");
        attrs.languageSettings = (LanguageSettings) bundle.getSerializable("languageSettings");
        return attrs;
    }

    private Type genVideoType(VideoAttributes attrs) {
        Type type = Type.MOBILE_H264_MP4;
        if (attrs.isLive || (!"no".equals(attrs.format) && attrs.launchMode != 0 && attrs.launchMode != 3 && !attrs.isDolby)) {
            return Type.MOBILE_H264_M3U8;
        }
        return type;
    }

    public void onDestroy() {
        LogInfo.log(TAG, "Service onDestroy()...");
        if (!(this.mWindowManager == null || this.mFloatingWindowPlayerView == null)) {
            LogInfo.log(TAG, "mWindowManager 移除View...");
            this.mWindowManager.removeView(this.mFloatingWindowPlayerView);
        }
        LogInfo.log(TAG, "取消注册广播和监听器");
        unregisterBroadcastReceiver();
        stopForegroundCompatible(109001);
        super.onDestroy();
    }

    private void setPriority() {
        Notification n = new Notification(2130838675, getResources().getString(2131101169), System.currentTimeMillis());
        n.setLatestEventInfo(this, getResources().getString(2131101169), getResources().getString(2131101168), PendingIntent.getActivity(this, 0, new Intent(this, FloatingWindowPlayerService.class), 0));
        startForegroundCompatible(109001, n);
    }

    private void registerNetworkChangedReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CONNECTIVITY_CHANGE_ACTION);
        registerReceiver(this.mNetworkChangedReceiver, filter);
    }

    private void unregisterNetworkChangedReceiver() {
        unregisterReceiver(this.mNetworkChangedReceiver);
    }

    private void pausePlay() {
        if (this.mFloatingWindowPlayerController.isPlaying()) {
            this.mFloatingWindowPlayerController.onPause();
            this.mPausedByEvent = true;
        }
    }

    private void resumePlay() {
        if (!this.mFloatingWindowPlayerController.isPlaying() && this.mPausedByEvent) {
            this.mFloatingWindowPlayerController.onScreenResume();
            this.mPausedByEvent = false;
        }
    }

    private void registerCallListener() {
        ((TelephonyManager) getSystemService("phone")).listen(this.mPhoneStateListener, 32);
    }

    private void unregisterCallListener() {
        ((TelephonyManager) getSystemService("phone")).listen(this.mPhoneStateListener, 0);
    }

    private void registerScreenStateReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(this.mScreenStateReceiver, filter);
    }

    private void unregisterScreenStateReceiver() {
        unregisterReceiver(this.mScreenStateReceiver);
    }

    private void registerVipAuthPassReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(VipProductContant.ACTION_VIP_AUTH_PASS);
        registerReceiver(this.mVipAuthPassReceiver, filter);
    }

    private void unregisterVipAuthPassReceiver() {
        unregisterReceiver(this.mVipAuthPassReceiver);
    }

    private void startForegroundCompatible(int id, Notification notification) {
        if (this.mMethodStartForeground != null) {
            this.mStartForegroundArgs[0] = Integer.valueOf(id);
            this.mStartForegroundArgs[1] = notification;
            invokeMethod(this.mMethodStartForeground, this.mStartForegroundArgs);
            return;
        }
        this.mSetForegroundArgs[0] = Boolean.TRUE;
        invokeMethod(this.mMethodSetForeground, this.mSetForegroundArgs);
        this.mNotificationManager.notify(id, notification);
    }

    private void stopForegroundCompatible(int id) {
        if (this.mMethodStopForeground != null) {
            this.mStopForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mMethodStopForeground, this.mStopForegroundArgs);
            return;
        }
        this.mNotificationManager.cancel(id);
        this.mSetForegroundArgs[0] = Boolean.FALSE;
        invokeMethod(this.mMethodSetForeground, this.mSetForegroundArgs);
    }

    private void invokeMethod(Method method, Object[] args) {
        try {
            method.invoke(this, args);
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e2) {
        }
    }

    private void registerBroadcastReceiver() {
        registerScreenStateReceiver();
        registerNetworkChangedReceiver();
        registerCallListener();
        registerVipAuthPassReceiver();
    }

    private void unregisterBroadcastReceiver() {
        unregisterScreenStateReceiver();
        unregisterNetworkChangedReceiver();
        unregisterCallListener();
        unregisterVipAuthPassReceiver();
    }

    private void configService() {
        try {
            this.mMethodStartForeground = getClass().getMethod(METHOD_NAME_STARTFORGROUND, mStartForegroundSignature);
            this.mMethodStopForeground = getClass().getMethod(METHOD_NAME_STOPFORGROUND, mStopForegroundSignature);
            this.mMethodSetForeground = getClass().getMethod(METHOD_NAME_SETFORGROUND, mSetForegroundSignature);
            setPriority();
        } catch (NoSuchMethodException e) {
            this.mMethodStartForeground = null;
            this.mMethodStopForeground = null;
            this.mMethodSetForeground = null;
            throw new IllegalStateException("OS doesn't have Service.startForeground OR Service.setForeground!");
        }
    }

    private boolean isPlayingCacheVideo() {
        return this.mVideoParams.isFromLocal || this.mFloatingWindowPlayerController.mIsLocalFile;
    }
}
