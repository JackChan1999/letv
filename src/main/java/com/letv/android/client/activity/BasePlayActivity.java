package com.letv.android.client.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.barrage.panorama.PanoramaDanmakuControlHelper;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.AlbumCommentDetailActivityConfig;
import com.letv.android.client.commonlib.config.FloatBallConfig;
import com.letv.android.client.commonlib.view.LetvPlayGestureLayout;
import com.letv.android.client.controller.BasePlayController;
import com.letv.android.client.controller.LiveBarrageController;
import com.letv.android.client.controller.PlayLiveController;
import com.letv.android.client.fragment.BasePlayFragment;
import com.letv.android.client.fragment.PlayLiveFragment;
import com.letv.android.client.listener.PlayActivityCallback;
import com.letv.android.client.view.LiveInterpretBarrageView;
import com.letv.business.flow.live.BasePlayLiveFlow;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.listener.OnRelevantStateChangeListener;
import com.letv.core.listener.OrientationSensorListener;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequestQueue.RequestFilter;
import com.letv.core.utils.ChangeOrientationHandler;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ScreenInfoUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class BasePlayActivity extends LetvBaseActivity implements PlayActivityCallback {
    private boolean isRegisterReceiver;
    private int mBatteryPower;
    private int mBatteryStatus;
    private BroadcastReceiver mBroadcastReceiver;
    private ImageView mBtnGestureLock;
    private FloatBallConfig mFloatBallConfig;
    private int mFromLaunch;
    private boolean mFull;
    private Handler mHandler;
    public boolean mIsPanoramaVideo;
    public boolean mIsVr;
    private int mLaunchMode;
    public LiveBarrageController mLiveBarrageController;
    private OnRelevantStateChangeListener mOnRelevantStateChangeListener;
    private Handler mOrientationHandler;
    private OrientationSensorListener mOrientationSensorListener;
    PanoramaDanmakuControlHelper mPanoramaDanmakuControlHelper;
    private SensorEventListener mPanoramaSensorListener;
    private SensorEventListener mPanoramaSensorListenerG;
    public BasePlayController mPlayController;
    protected BasePlayFragment mPlayFragment;
    protected LetvPlayGestureLayout mPlayGestrue;
    protected RelativeLayout mPlayLower;
    private int mPlayType;
    protected FrameLayout mPlayUpper;
    protected FrameLayout mPlayUpperLayout;
    public Rect mRect;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    public BasePlayActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mFull = false;
        this.mHandler = new Handler(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                if (this.this$0.mPlayUpperLayout != null) {
                    this.this$0.mPlayUpperLayout.getGlobalVisibleRect(this.this$0.mRect);
                    this.this$0.callAdsPlayInterface(6, false);
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.TIME_TICK".equals(intent.getAction())) {
                    if (this.this$0.mOnRelevantStateChangeListener != null) {
                        this.this$0.mOnRelevantStateChangeListener.onTimeChange();
                    }
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    if (this.this$0.mOnRelevantStateChangeListener != null && ScreenInfoUtils.reflectScreenState()) {
                        this.this$0.mOnRelevantStateChangeListener.onNetChange();
                    }
                } else if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                    this.this$0.mBatteryStatus = intent.getIntExtra("status", 1);
                    int level = intent.getExtras().getInt(AlbumCommentDetailActivityConfig.LEVEL, 0);
                    this.this$0.mBatteryPower = (level * 100) / intent.getExtras().getInt("scale", 100);
                    if (this.this$0.mOnRelevantStateChangeListener != null) {
                        this.this$0.mOnRelevantStateChangeListener.onBatteryChange(this.this$0.mBatteryStatus, this.this$0.mBatteryPower);
                    }
                } else if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                    if (this.this$0.mOnRelevantStateChangeListener != null) {
                        this.this$0.mOnRelevantStateChangeListener.onHeadsetPlug();
                    }
                } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    if (this.this$0.mPlayFragment != null) {
                        this.this$0.mPlayFragment.setScreenLock(true);
                        this.this$0.mPlayFragment.pause();
                    }
                    if (this.this$0.mPlayController != null) {
                        this.this$0.mPlayController.lockScreenPause();
                    }
                } else if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                    if (this.this$0.mPlayFragment != null) {
                        this.this$0.mPlayFragment.setScreenLock(false);
                        this.this$0.mPlayFragment.resume();
                    }
                    if (this.this$0.mPlayController != null) {
                        this.this$0.mPlayController.unLockSceenResume();
                    }
                }
            }
        };
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_base_play);
        initDataFromIntent();
        initFloatBall();
        initViews();
    }

    private void initFloatBall() {
        if (this.mFloatBallConfig == null) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_FLOAT_BALL_INIT, this));
            if (LeResponseMessage.checkResponseMessageValidity(response, FloatBallConfig.class)) {
                this.mFloatBallConfig = (FloatBallConfig) response.getData();
            }
        }
        if (this.mFloatBallConfig == null) {
            return;
        }
        if (!NetworkUtils.isNetworkAvailable() || UIsUtils.isLandscape()) {
            this.mFloatBallConfig.hideFloat();
        } else {
            this.mFloatBallConfig.showFloat("9");
        }
    }

    private void initViews() {
        this.mPlayUpperLayout = (FrameLayout) findViewById(R.id.play_upper_layout);
        this.mPlayUpper = (FrameLayout) findViewById(2131361905);
        this.mPlayLower = (RelativeLayout) findViewById(R.id.play_lower);
        this.mPlayGestrue = (LetvPlayGestureLayout) findViewById(2131363812);
        this.mBtnGestureLock = (ImageView) findViewById(2131364024);
        this.mRect = new Rect();
        if (this.mPlayType == 1) {
            this.mPlayFragment = new PlayLiveFragment();
        }
        this.mPlayFragment.setIntent(getIntent());
        if (this.mIsVr) {
            this.mPlayFragment.setSourceType(2);
        } else {
            this.mPlayFragment.setSourceType(this.mIsPanoramaVideo ? 1 : 0);
        }
        showFragmentIfNeeded(this.mPlayFragment, false);
        initSensor();
        initController();
        this.mLiveBarrageController = new LiveBarrageController(this, this.mPlayController.mLiveBarrageSentCallback, getSupportFragmentManager(), R.id.play_live_barrage_contain, 3);
        initWindowDirection(true);
        registerReceiver();
        LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(801));
        initTask();
    }

    private void initTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_BARRAGE_LIVE_RED_PACKAGE_CLICK, new TaskRunnable(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (!(this.this$0.getController() == null || this.this$0.getBaseRedPacket() == null)) {
                    this.this$0.getBaseRedPacket().openRedPacket();
                }
                return null;
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_BARRAGE_CHECK_IS_PANORAMA, new TaskRunnable(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                return new LeResponseMessage(LeMessageIds.MSG_BARRAGE_CHECK_IS_PANORAMA, Boolean.valueOf(this.this$0.mIsPanoramaVideo));
            }
        }));
    }

    public LiveBarrageController getLiveBarrageController() {
        return this.mLiveBarrageController;
    }

    public BasePlayController getController() {
        return this.mPlayController;
    }

    public void initByNewIntent() {
        initDataFromIntent();
        this.mPlayController.format();
        this.mPlayController = null;
        if (this.mIsPanoramaVideo) {
            initSensor();
            if (this.mIsVr) {
                this.mPlayFragment.setSourceType(2);
            } else {
                this.mPlayFragment.setSourceType(this.mIsPanoramaVideo ? 1 : 0);
            }
        }
        initController();
        initFloatBall();
        initWindowDirection(false);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getIntent().putExtra("isLiveFromPush", LetvApplication.getInstance().isPush());
        if (this.mPlayFragment != null) {
            this.mPlayFragment.pause();
            this.mPlayFragment.stopPlayback();
            this.mPlayFragment.setFirstStart(true);
        }
        initByNewIntent();
        if (this.mOnRelevantStateChangeListener != null && ScreenInfoUtils.reflectScreenState()) {
            this.mOnRelevantStateChangeListener.onTimeChange();
            this.mOnRelevantStateChangeListener.onBatteryChange(this.mBatteryStatus, this.mBatteryPower);
            this.mOnRelevantStateChangeListener.onNetChange();
        }
    }

    protected void onResume() {
        super.onResume();
        PreferencesManager.getInstance().setShowMiGuDialog(true);
        this.mPlayController.onResume();
        callAdsPlayInterface(8, false);
        float britness = PreferencesManager.getInstance().getBritness();
        if (britness != 0.0f) {
            this.mPlayController.setBrightness(britness);
        }
        LiveInterpretBarrageView.isOpenedWebView = false;
    }

    protected void onPause() {
        super.onPause();
        PreferencesManager.getInstance().setShowMiGuDialog(true);
        if (this.mPlayFragment != null) {
            this.mPlayFragment.pause();
        }
        callAdsPlayInterface(7, false);
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.onPause();
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.mPlayFragment != null) {
            this.mPlayFragment.pause();
        }
        this.mPlayController.onStop();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changedFloatUI();
        initWindowDirection(false);
    }

    public void changedFloatUI() {
        if (this.mFloatBallConfig == null) {
            return;
        }
        if (UIsUtils.isLandscape(this)) {
            this.mFloatBallConfig.hideFloat(true);
        } else {
            this.mFloatBallConfig.showFloat();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean z = true;
        super.onActivityResult(requestCode, resultCode, data);
        LogInfo.log("clf", "onActivityResult..requestCode=" + requestCode + ",resultCode=" + resultCode);
        if (requestCode == 18) {
            BasePlayController basePlayController = this.mPlayController;
            if (resultCode != 257) {
                z = false;
            }
            basePlayController.onActivityResultPaySuccess(z);
        } else if (requestCode == 19) {
            if (resultCode == 257) {
                this.mPlayController.onActivityResultPaySuccess(true);
            } else if (PreferencesManager.getInstance().isLogin()) {
                this.mPlayController.onActivityResultLoginSuccess();
            }
        } else if (requestCode == 16 && resultCode == 1) {
            this.mPlayController.onActivityResultLoginSuccess();
        }
        if (this.mPlayController != null) {
            this.mPlayController.onShareActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (!(keyCode == 82 || this.mPlayController.onKeyDown(keyCode, event))) {
            z = super.onKeyDown(keyCode, event);
            if (this.mPlayController != null) {
                int volNum = this.mPlayController.getCurSoundVolume();
                boolean isUp = false;
                if (keyCode == 24) {
                    volNum++;
                    isUp = true;
                } else if (keyCode == 25) {
                    volNum--;
                    isUp = false;
                }
                this.mPlayController.curVolume(this.mPlayController.getMaxSoundVolume(), volNum, isUp);
            }
        }
        return z;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == 0 && this.mPlayController != null) {
            this.mPlayController.startLongWatchCountDown();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initDataFromIntent() {
        Intent intent = getIntent();
        this.mPlayType = intent.getIntExtra(PlayConstant.PLAY_TYPE, 0);
        this.mLaunchMode = intent.getIntExtra("launchMode", 0);
        this.mFromLaunch = intent.getIntExtra("from", 0);
        this.mFull = intent.getBooleanExtra(PlayConstant.LIVE_FULL_ONLY, false);
        this.mIsPanoramaVideo = intent.getSerializableExtra(PlayConstant.VIDEO_TYPE) == VideoType.Panorama;
        if (this.mLaunchMode == 0) {
            String path = null;
            if (!(getIntent() == null || getIntent().getData() == null)) {
                Uri uriPath = getIntent().getData();
                String scheme = uriPath.getScheme();
                path = (scheme == null || scheme.equals(IDataSource.SCHEME_FILE_TAG)) ? uriPath.getPath() : uriPath.toString();
            }
            this.mLaunchMode = 1;
            intent.putExtra("launchMode", 1);
            intent.putExtra(PlayConstant.URI, path);
            intent.putExtra("seek", 0);
            intent.putExtra(PlayConstant.PLAY_MODE, 1);
        }
        if (this.mFromLaunch == 13) {
            LetvApplication.getInstance().setPush(true);
        } else if (intent.getBooleanExtra("isFromPush", false)) {
            LetvApplication.getInstance().setPush(true);
        } else if (intent.getBooleanExtra("isLiveFromPush", false)) {
            LetvApplication.getInstance().setPush(true);
        }
    }

    private void initController() {
        this.mPlayController = BasePlayController.getPlayController(this, this.mLaunchMode, this.mFull, this.mIsPanoramaVideo, this);
        this.mPlayFragment.setStateChangeListener(this.mPlayController);
        this.mPlayFragment.setPlayFragmentListener(this);
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.setJustLandscape(this.mFull);
        }
        this.mOnRelevantStateChangeListener = this.mPlayController;
    }

    private void initWindowDirection(boolean first) {
        boolean isLandscape = UIsUtils.isLandscape(this);
        this.mBtnGestureLock.setVisibility(isLandscape ? 8 : 0);
        if (first && LetvUtils.isSMNote() && !this.mFull) {
            this.mBtnGestureLock.setVisibility(0);
            this.mPlayLower.setVisibility(0);
            this.mPlayLower.requestLayout();
            UIsUtils.zoomView(320, 180, this.mPlayUpperLayout);
            UIsUtils.cancelFullScreen(this);
            if (this.mOrientationSensorListener != null) {
                this.mOrientationSensorListener.setLock(true);
            }
        } else if (isLandscape) {
            this.mBtnGestureLock.setVisibility(8);
            this.mPlayLower.setVisibility(8);
            this.mPlayLower.requestLayout();
            UIsUtils.zoomViewFull(this.mPlayUpperLayout);
            UIsUtils.fullScreen(this);
        } else {
            this.mBtnGestureLock.setVisibility(0);
            this.mPlayLower.setVisibility(0);
            this.mPlayLower.requestLayout();
            UIsUtils.zoomView(320, 180, this.mPlayUpperLayout);
            UIsUtils.cancelFullScreen(this);
            if (LiveLunboUtils.isLunboType(this.mLaunchMode)) {
                if (this.mOrientationSensorListener != null) {
                    this.mOrientationSensorListener.setLock(false);
                }
                this.mPlayController.wakeLock();
            }
        }
        this.mPlayController.changeDirection(isLandscape);
        this.mHandler.sendEmptyMessageDelayed(0, 1000);
        showThirdPartBtnState(isLandscape);
    }

    private void initSensor() {
        if (this.mIsPanoramaVideo) {
            initPanoramaSensor();
        } else {
            initDefaultSensor();
        }
    }

    private void initDefaultSensor() {
        this.mOrientationHandler = new ChangeOrientationHandler(this);
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
        this.mOrientationSensorListener = new OrientationSensorListener(this.mOrientationHandler, this);
        this.mSensorManager.registerListener(this.mOrientationSensorListener, this.mSensor, 1);
    }

    private void initPanoramaSensor() {
        LogInfo.log("tanfulun", "baseplayActivity--initPanoramaSensor");
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        Sensor orientationSensorG = this.mSensorManager.getDefaultSensor(9);
        this.mPanoramaSensorListener = new SensorEventListener(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSensorChanged(SensorEvent event) {
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];
                if (this.this$0.mPlayFragment != null && this.this$0.mPlayFragment.getVideoView() != null) {
                    try {
                        this.this$0.mPlayFragment.getVideoView().setgravity_yroInfomation(axisX, axisY, axisZ);
                        PanoramaDanmakuControlHelper panoramaDanmakuControlHelper = this.this$0.mPanoramaDanmakuControlHelper;
                        PanoramaDanmakuControlHelper.setgravity_yroInfomation(axisX, axisY, axisZ);
                    } catch (Exception e) {
                    }
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        this.mPanoramaSensorListenerG = new SensorEventListener(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onSensorChanged(SensorEvent event) {
                float gx = event.values[0];
                float gy = event.values[1];
                float gz = event.values[2];
                if (!(event.sensor.getType() != 9 || this.this$0.mPlayFragment == null || this.this$0.mPlayFragment.getVideoView() == null)) {
                    try {
                        this.this$0.mPlayFragment.getVideoView().setGravityInfomation(gx, gy, gz);
                        PanoramaDanmakuControlHelper panoramaDanmakuControlHelper = this.this$0.mPanoramaDanmakuControlHelper;
                        PanoramaDanmakuControlHelper.setGravityInfomation(gx, gy, gz);
                    } catch (Exception e) {
                    }
                }
                if (4.0f * ((gx * gx) + (gy * gy)) >= gz * gz) {
                    int orientation = 90 - Math.round(((float) Math.atan2((double) (-gy), (double) gx)) * 57.29578f);
                    while (orientation >= 360) {
                        orientation -= 360;
                    }
                    while (orientation < 0) {
                        orientation += 360;
                    }
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        this.mSensorManager.registerListener(this.mPanoramaSensorListener, this.mSensorManager.getDefaultSensor(4), 1);
        this.mSensorManager.registerListener(this.mPanoramaSensorListenerG, orientationSensorG, 1);
    }

    private void showThirdPartBtnState(boolean isLandscape) {
    }

    public void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.intent.action.TIME_TICK");
            filter.addAction("android.intent.action.BATTERY_CHANGED");
            filter.addAction("android.intent.action.HEADSET_PLUG");
            filter.addAction("android.intent.action.SCREEN_OFF");
            filter.addAction("android.intent.action.USER_PRESENT");
            registerReceiver(this.mBroadcastReceiver, filter);
            this.isRegisterReceiver = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterSensorListner() {
        if (this.mSensorManager != null) {
            if (this.mOrientationSensorListener != null) {
                this.mSensorManager.unregisterListener(this.mOrientationSensorListener);
            }
            if (this.mPanoramaSensorListener != null) {
                this.mSensorManager.unregisterListener(this.mPanoramaSensorListener);
            }
            if (this.mPanoramaSensorListenerG != null) {
                this.mSensorManager.unregisterListener(this.mPanoramaSensorListenerG);
            }
        }
    }

    public void unregisterReceiver() {
        try {
            if (this.isRegisterReceiver) {
                unregisterReceiver(this.mBroadcastReceiver);
                unregisterSensorListner();
                this.isRegisterReceiver = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finish() {
        super.finish();
        Volley.getQueue().cancelAll(new RequestFilter(this) {
            final /* synthetic */ BasePlayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean apply(VolleyRequest<?> request) {
                return (request == null || TextUtils.isEmpty(request.getTag()) || !request.getTag().startsWith(BasePlayLiveFlow.LIVE_FLOW_TAG)) ? false : true;
            }
        });
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_BARRAGE_LIVE_RED_PACKAGE_CLICK);
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_BARRAGE_CHECK_IS_PANORAMA);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.onDestory();
            this.mFloatBallConfig = null;
        }
        unregisterReceiver();
        LetvApplication.getInstance().setPush(false);
        callAdsPlayInterface(9, false);
        if (this.mPlayController != null) {
            this.mPlayController.onDestroy();
            this.mPlayController.destroyStatisticsInfo();
        }
        this.mPlayUpper.removeAllViews();
        this.mPlayLower.removeAllViews();
        this.mPlayUpperLayout.removeAllViews();
        this.mPlayController = null;
        this.mPlayUpper = null;
        this.mPlayLower = null;
        this.mPlayUpperLayout = null;
    }

    private void callAdsPlayInterface(int whichStatus, boolean isHand) {
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.ALBUM_FRAGMENT_TAG_ARRAY;
    }

    public String getActivityName() {
        return BasePlayActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void callAdsInterface(int whichStatus, boolean isHand) {
        callAdsPlayInterface(whichStatus, isHand);
    }

    public void cancelLongTimeWatch() {
        if (this.mPlayController != null) {
            this.mPlayController.cancelLongTimeWatch();
        }
    }

    public int getPlayLevel() {
        if (this.mPlayController != null) {
            return this.mPlayController.getPlayLevel();
        }
        return LetvApplication.getInstance().getDefaultLevel();
    }

    public void blockStart() {
        if (this.mPlayController instanceof PlayLiveController) {
            ((PlayLiveController) this.mPlayController).blockStart();
        }
    }

    public void blockEnd() {
        if (this.mPlayController instanceof PlayLiveController) {
            ((PlayLiveController) this.mPlayController).blockEnd();
        }
    }

    public void blockTwiceAlert() {
        if (this.mPlayController instanceof PlayLiveController) {
            ((PlayLiveController) this.mPlayController).blockTwiceAlert();
        }
    }

    public void setLock(boolean isLock) {
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.setLock(isLock);
        }
    }

    public boolean isPlaying() {
        if (this.mPlayFragment != null) {
            return this.mPlayFragment.isPlaying();
        }
        return false;
    }

    public boolean isDlnaPlaying() {
        return this.mPlayController != null ? this.mPlayController.isDlnaPlaying() : false;
    }

    public void setEnforcementPause(boolean isPause) {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.setEnforcementPause(isPause);
        }
    }

    public boolean isEnforcementPause() {
        if (this.mPlayFragment != null) {
            return this.mPlayFragment.isEnforcementPause();
        }
        return false;
    }

    public void setEnforcementWait(boolean isWait) {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.setEnforcementWait(isWait);
        }
    }

    public boolean isEnforcementWait() {
        if (this.mPlayFragment != null) {
            return this.mPlayFragment.isEnforcementWait();
        }
        return false;
    }

    public int getCurrentPlayPosition() {
        if (this.mPlayFragment != null) {
            return this.mPlayFragment.getCurrentPosition();
        }
        return 0;
    }

    public void playPause() {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.pause();
        }
    }

    public void resumeVideo() {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.resumeVideo();
        }
    }

    public void pauseVideo() {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.pauseVideo();
        }
    }

    public void playNet(String uriString, boolean isLive, boolean isDolby, int msec) {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.playNet(uriString, isDolby, msec);
        }
    }

    public void start(String url, boolean isAdsFinished) {
        if (this.mPlayType == 1) {
            ((PlayLiveFragment) this.mPlayFragment).start(url, isAdsFinished);
        }
    }

    public void lockOnce(int orientation) {
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.lockOnce(orientation);
        }
    }

    public void closeSensor() {
        unregisterSensorListner();
    }

    public void openSensor() {
        initSensor();
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        if (this.mPlayFragment == null || this.mPlayFragment.getVideoView() == null) {
            return 0;
        }
        return this.mPlayFragment.getVideoView().setOneFingertouchInfomation(begin_x, begin_y, end_x, end_y);
    }

    public int setTwoScale(float scale) {
        if (this.mPlayFragment == null || this.mPlayFragment.getVideoView() == null) {
            return 0;
        }
        return this.mPlayFragment.getVideoView().setTwoFingerZoom(scale);
    }

    public void stopPlayback() {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.stopPlayback();
        }
    }

    public void finishPlayer() {
        if (this.mPlayFragment != null) {
            this.mPlayFragment.destroyVedioView();
        }
    }

    public void setFloatBallVisible(boolean isVisible) {
        if (isVisible) {
            if (this.mFloatBallConfig != null) {
                this.mFloatBallConfig.showFloat("8", this.mLaunchMode + "");
            }
        } else if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.hideFloat();
        }
    }
}
