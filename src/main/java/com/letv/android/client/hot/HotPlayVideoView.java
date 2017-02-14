package com.letv.android.client.hot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.business.flow.album.AlbumPlayHotFlow;
import com.letv.business.flow.album.hot.listener.HotPlayListener;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.utils.NativeInfos;
import com.letv.component.player.videoview.VideoViewH264m3u8;
import com.letv.component.player.videoview.VideoViewH264mp4;
import com.letv.core.bean.VideoBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvDateUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.bean.StatisticsPlayInfo;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;

@SuppressLint({"NewApi"})
public class HotPlayVideoView implements OnErrorListener, OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener, OnVideoViewStateChangeListener, HotPlayListener {
    public static final int REFRESH_PROGRESS = 1;
    public String Tag;
    private final int UPDATE_STATICICS_TIME;
    private HotVideoClickCallBack callBack;
    public int cpulever;
    public TextView duration;
    public RelativeLayout errorLayout;
    public TextView errorRetry;
    public TextView errorTip;
    public ImageView image;
    public boolean isAutoPlay;
    public boolean isComplete;
    public boolean isDestroy;
    public boolean isFirstPlay;
    public boolean isOnPreparePause;
    public boolean isPrepared;
    boolean isShow3gToast;
    public boolean isShowToast;
    public boolean isStaticsInit;
    public boolean isStatisticsLaunch;
    public boolean isWo3GtoWifiPause;
    public int lastPlayTime;
    private long lastTimeElapsed;
    public ProgressBar loading;
    public LetvHotActivity mActivity;
    private AudioManager mAudioManager;
    private Handler mHandler;
    public boolean mIsFromPush;
    private boolean mIsSendingHandler;
    private boolean mShouldCheckBlock;
    public LetvMediaPlayerControl mVideoView;
    public View parentView;
    private AdPlayFragmentProxy playAdFragment;
    public ImageView playButton;
    public int playTime;
    public Uri playUri;
    public View root;
    public RelativeLayout rootView;
    public SeekBar seekBar;
    public int selection;
    private long timeElapsed;
    private int updateCount;
    public int vid;
    public WakeLock wakeLock;

    public interface HotVideoClickCallBack {
        void play3G();

        void refreshList();
    }

    private void stateViewPlay() {
        this.seekBar.setVisibility(0);
        this.isFirstPlay = true;
        this.playButton.setVisibility(8);
        this.image.setVisibility(8);
        this.loading.setVisibility(8);
        if (this.rootView.getVisibility() == 8) {
            this.rootView.setVisibility(0);
        }
        if (this.errorLayout.getVisibility() == 0) {
            this.errorLayout.setVisibility(8);
        }
    }

    private void stateViewPause() {
        this.seekBar.setVisibility(8);
        this.image.setVisibility(0);
        this.playButton.setVisibility(0);
        this.loading.setVisibility(8);
        this.rootView.setVisibility(8);
        if (this.errorLayout.getVisibility() == 0) {
            this.errorLayout.setVisibility(8);
        }
    }

    public HotPlayVideoView(LetvHotActivity activity, int vid, View view, HotVideoClickCallBack callBack, int cpuLever) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.Tag = getClass().getSimpleName();
        this.UPDATE_STATICICS_TIME = 2;
        this.updateCount = 0;
        this.timeElapsed = 0;
        this.lastTimeElapsed = 0;
        this.isDestroy = false;
        this.isComplete = false;
        this.selection = 0;
        this.isAutoPlay = true;
        this.isShowToast = false;
        this.isFirstPlay = true;
        this.isOnPreparePause = false;
        this.isPrepared = false;
        this.isStaticsInit = false;
        this.isStatisticsLaunch = false;
        this.mIsFromPush = false;
        this.isWo3GtoWifiPause = false;
        this.mShouldCheckBlock = true;
        this.mIsSendingHandler = false;
        this.mHandler = new Handler(this) {
            final /* synthetic */ HotPlayVideoView this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        HotPlayVideoView hotPlayVideoView;
                        HotPlayVideoView hotPlayVideoView2;
                        int currentPosition;
                        if (NetworkUtils.getNetworkType() != 0) {
                            this.this$0.seekBar.setVisibility(0);
                        } else {
                            this.this$0.seekBar.setVisibility(8);
                        }
                        if (this.this$0.mVideoView != null && this.this$0.mVideoView.getCurrentPosition() > 0 && ((this.this$0.mVideoView.getCurrentPosition() > this.this$0.lastPlayTime && this.this$0.lastPlayTime != 0) || !this.this$0.mShouldCheckBlock)) {
                            LogInfo.log("king", "-----------play---------------");
                            this.this$0.mShouldCheckBlock = true;
                            this.this$0.stateViewPlay();
                            this.this$0.rootView.setOnClickListener(new 1(this));
                        } else if (this.this$0.mVideoView != null && this.this$0.mVideoView.getCurrentPosition() == this.this$0.lastPlayTime) {
                            LogInfo.log(this.this$0.Tag + "||wlx" + "mVideoView.getCurrentPosition() == lastPlayTime = " + this.this$0.lastPlayTime + " net type = " + NetworkUtils.getNetworkType());
                            if (NetworkUtils.getNetworkType() == 0) {
                                this.this$0.mVideoView.pause();
                                this.this$0.loading.setVisibility(8);
                                this.this$0.image.setVisibility(8);
                                this.this$0.rootView.setVisibility(8);
                                this.this$0.errorLayout.setVisibility(0);
                                this.this$0.errorTip.setText(2131100176);
                                this.this$0.errorRetry.setVisibility(0);
                                this.this$0.rootView.setOnClickListener(null);
                                LogInfo.log("LM", "process no net");
                                this.this$0.stopSendHander();
                                this.this$0.noNetError();
                            } else {
                                LogInfo.log("king", "-----------loading---------------");
                                this.this$0.loading.setVisibility(0);
                                if (this.this$0.lastPlayTime == 0) {
                                    this.this$0.image.setVisibility(0);
                                }
                                hotPlayVideoView = this.this$0;
                                hotPlayVideoView2 = this.this$0;
                                currentPosition = this.this$0.mVideoView.getCurrentPosition();
                                hotPlayVideoView2.lastPlayTime = currentPosition;
                                hotPlayVideoView.timeElapsed = (long) currentPosition;
                                sendEmptyMessageDelayed(1, 1000);
                                this.this$0.mShouldCheckBlock = true;
                                return;
                            }
                        }
                        this.this$0.mShouldCheckBlock = true;
                        hotPlayVideoView = this.this$0;
                        hotPlayVideoView2 = this.this$0;
                        currentPosition = this.this$0.mVideoView.getCurrentPosition();
                        hotPlayVideoView2.lastPlayTime = currentPosition;
                        hotPlayVideoView.timeElapsed = (long) currentPosition;
                        this.this$0.seekBar.setProgress(this.this$0.mVideoView.getCurrentPosition());
                        if (this.this$0.lastPlayTime == 0 || !this.this$0.isFirstPlay) {
                            sendEmptyMessage(1);
                            return;
                        } else if (this.this$0.lastPlayTime != 0 && this.this$0.isFirstPlay) {
                            this.this$0.image.setVisibility(8);
                            this.this$0.loading.setVisibility(8);
                            sendEmptyMessageDelayed(1, 1000);
                            return;
                        } else {
                            return;
                        }
                    case 2:
                        if (this.this$0.updateCount == 0) {
                            if (this.this$0.timeElapsed - this.this$0.lastTimeElapsed < 15) {
                                this.this$0.updateCount = 0;
                                this.this$0.mHandler.sendEmptyMessageDelayed(2, (15 - (this.this$0.timeElapsed - this.this$0.lastTimeElapsed)) * 1000);
                                return;
                            }
                            this.this$0.updatePlayDataStatistics("time", 15, this.this$0.isAutoPlay);
                            this.this$0.lastTimeElapsed = this.this$0.timeElapsed;
                            this.this$0.updateCount = 1;
                            this.this$0.mHandler.sendEmptyMessageDelayed(2, 60000);
                            return;
                        } else if (this.this$0.updateCount == 1) {
                            if (this.this$0.timeElapsed - this.this$0.lastTimeElapsed < 60) {
                                this.this$0.updateCount = 1;
                                this.this$0.mHandler.sendEmptyMessageDelayed(2, (60 - (this.this$0.timeElapsed - this.this$0.lastTimeElapsed)) * 1000);
                                return;
                            }
                            this.this$0.updatePlayDataStatistics("time", 60, this.this$0.isAutoPlay);
                            this.this$0.lastTimeElapsed = this.this$0.timeElapsed;
                            this.this$0.updateCount = 2;
                            this.this$0.mHandler.sendEmptyMessageDelayed(2, 180000);
                            return;
                        } else if (this.this$0.updateCount != 2) {
                            return;
                        } else {
                            if (this.this$0.timeElapsed - this.this$0.lastTimeElapsed < 180) {
                                this.this$0.updateCount = 2;
                                this.this$0.mHandler.sendEmptyMessageDelayed(2, (180 - (this.this$0.timeElapsed - this.this$0.lastTimeElapsed)) * 1000);
                                return;
                            }
                            this.this$0.updatePlayDataStatistics("time", 180, this.this$0.isAutoPlay);
                            this.this$0.lastTimeElapsed = this.this$0.timeElapsed;
                            this.this$0.updateCount = 2;
                            this.this$0.mHandler.sendEmptyMessageDelayed(2, 180000);
                            return;
                        }
                    default:
                        return;
                }
            }
        };
        this.isShow3gToast = true;
        this.mActivity = activity;
        this.vid = vid;
        this.parentView = view;
        this.callBack = callBack;
        this.cpulever = cpuLever;
        try {
            this.wakeLock = ((PowerManager) this.mActivity.getSystemService("power")).newWakeLock(536870922, "HotPlayVideoView");
            this.wakeLock.acquire();
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        LogInfo.log("king", "initView");
        this.loading = (ProgressBar) this.parentView.findViewById(R.id.hot_play_loading);
        this.playButton = (ImageView) this.parentView.findViewById(R.id.hot_play_playButton);
        this.seekBar = (SeekBar) this.parentView.findViewById(R.id.hot_play_seekbar);
        this.image = (ImageView) this.parentView.findViewById(R.id.hot_play_image);
        this.errorLayout = (RelativeLayout) this.parentView.findViewById(R.id.hot_play_errer_layout);
        this.errorTip = (TextView) this.parentView.findViewById(R.id.hot_play_errer_tip);
        this.errorRetry = (TextView) this.parentView.findViewById(R.id.hot_play_errer_retry);
        this.duration = (TextView) this.parentView.findViewById(R.id.hot_play_duration);
        this.rootView = (RelativeLayout) this.parentView.findViewById(R.id.hot_play_root_view);
        this.rootView.removeAllViews();
        try {
            if (this.cpulever == 0) {
                this.root = LayoutInflater.from(this.mActivity).inflate(R.layout.fragment_play_hot2, this.rootView);
                this.mVideoView = (VideoViewH264mp4) this.root.findViewById(R.id.video_view);
            } else {
                this.root = LayoutInflater.from(this.mActivity).inflate(R.layout.fragment_play_hot, this.rootView);
                this.mVideoView = (VideoViewH264m3u8) this.root.findViewById(R.id.video_view);
            }
            this.mVideoView.setVideoViewStateChangeListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.seekBar.setThumb(null);
        this.playButton.setVisibility(8);
        this.loading.setVisibility(0);
        this.image.setVisibility(0);
        this.errorLayout.setVisibility(8);
        this.errorRetry.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HotPlayVideoView this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                this.this$0.stopSendHander();
                LogInfo.log("LM", "onclick");
                LogInfo.log("errorRetry is onclick NetWorkTypeUtils.getNetType() = " + NetworkUtils.getNetworkType() + this.this$0.isShowToast);
                if (NetworkUtils.getNetworkType() == 1) {
                    ToastUtils.showToast(2131100178);
                    this.this$0.isShowToast = false;
                    this.this$0.mVideoView.start();
                    this.this$0.startSendHander();
                } else if (NetworkUtils.getNetworkType() == 2 || NetworkUtils.getNetworkType() == 3) {
                    this.this$0.callBack.refreshList();
                    this.this$0.stateViewPause();
                    this.this$0.pause();
                } else {
                    this.this$0.startSendHander();
                }
            }
        });
        this.rootView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HotPlayVideoView this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View arg0) {
                this.this$0.clickOnPause(this.this$0.mVideoView.isPlaying());
            }
        });
        this.rootView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ HotPlayVideoView this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });
        this.playButton.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HotPlayVideoView this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View arg0) {
                this.this$0.clickOnPause(false);
            }
        });
    }

    public void clickOnPause(boolean isPlaying) {
        LogInfo.log(this.Tag + "||wlx", "clickOnPause isPlaying = " + isPlaying);
        LogInfo.log("king", "clickOnPause  vid = " + this.vid);
        if (!this.isWo3GtoWifiPause) {
            int netType = NetworkUtils.getNetworkType();
            if (isPlaying) {
                if (this.mVideoView != null) {
                    this.mVideoView.pause();
                }
                AlbumPlayHotFlow.sAutoPlay = false;
                LetvHotActivity.isClickPause = true;
                this.seekBar.setVisibility(8);
                this.loading.setVisibility(8);
                this.playButton.setVisibility(0);
                stopSendHander();
                LogInfo.log("LM", "clickOnPause");
                try {
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c331", null, -1, null, PageIdConstant.hotIndexCategoryPage, null, null, null, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (netType == 2 || netType == 3) {
                    this.isShow3gToast = false;
                    return;
                }
                return;
            }
            if (this.isShowToast && netType == 1) {
                ToastUtils.showToast(2131100178);
            } else if (this.isComplete && NetworkUtils.isMobileNetwork()) {
                if (AlbumPlayHotFlow.sIsWo3GUser) {
                    new Handler(this.mActivity.getMainLooper()).post(new Runnable(this) {
                        final /* synthetic */ HotPlayVideoView this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void run() {
                            UnicomWoFlowDialogUtils.showWoFreeActivatedToast(this.this$0.mActivity);
                        }
                    });
                } else if (this.isShow3gToast) {
                    ToastUtils.showToast(LetvTools.getTextFromServer("100006", this.mActivity.getString(2131100656)));
                }
            } else if (NetworkUtils.isMobileNetwork() && !AlbumPlayHotFlow.sIsWo3GUser) {
                if (AlbumPlayHotFlow.sIsWo3GUser) {
                    new Handler(this.mActivity.getMainLooper()).post(new Runnable(this) {
                        final /* synthetic */ HotPlayVideoView this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void run() {
                            UnicomWoFlowDialogUtils.showWoFreeActivatedToast(this.this$0.mActivity);
                        }
                    });
                    return;
                }
                this.callBack.play3G();
                this.isShow3gToast = true;
                return;
            }
            pauseToPlay(false);
            this.isShow3gToast = true;
        }
    }

    public void startSendHander() {
        this.mShouldCheckBlock = false;
        this.mIsSendingHandler = true;
        this.mHandler.sendEmptyMessage(1);
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessageDelayed(2, 15000);
    }

    public void stopSendHander() {
        LogInfo.log("LM", "stopSendHander");
        this.mIsSendingHandler = false;
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
    }

    private boolean isSupportM3U8() {
        return "ios".equals(LetvApplication.getInstance().getVideoFormat());
    }

    public void stopPlayback() {
        LogInfo.log("jc666", "热点 播放停止");
        this.playTime = 0;
        if (this.mVideoView == null) {
            this.mIsFromPush = false;
            return;
        }
        if (this.isStaticsInit && !this.isComplete) {
            updatePlayDataStatistics("time", (this.timeElapsed - this.lastTimeElapsed) / 1000 == 0 ? (this.timeElapsed - this.lastTimeElapsed) % 1000 : ((this.timeElapsed - this.lastTimeElapsed) / 1000) + 1, this.isAutoPlay);
            if (this.mActivity.mIsHomeClicked) {
                StatisticsUtils.mIsHomeClicked = false;
            } else {
                updatePlayDataStatistics("end", -1, this.isAutoPlay);
            }
        }
        this.mIsFromPush = false;
        this.mVideoView.stopPlayback();
        this.seekBar.setVisibility(8);
        this.loading.setVisibility(8);
        this.image.setVisibility(0);
        this.playButton.setVisibility(0);
        stopSendHander();
    }

    public void seekTo(int msec) {
        LogInfo.log("king", "seekTo");
        if (this.mVideoView != null) {
            this.mVideoView.seekTo(msec);
        }
    }

    public int getVid() {
        return this.vid;
    }

    public boolean isDestroy() {
        return this.isDestroy;
    }

    public void initNativeInfos() {
        String vf = LetvApplication.getInstance().getVideoFormat();
        if ("ios".equals(vf)) {
            NativeInfos.mOffLinePlay = false;
            NativeInfos.mIsLive = false;
        } else if ("no".equals(vf)) {
            NativeInfos.mOffLinePlay = true;
            NativeInfos.mIfNative3gpOrMp4 = true;
            NativeInfos.mIsLive = false;
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        LogInfo.log("clf", "...arg0.getCurrentPosition().." + mediaPlayer.getCurrentPosition());
        LogInfo.log("clf", "...arg0.getDuration().." + mediaPlayer.getDuration());
        LogInfo.log("clf", "...isComplete..");
        this.isComplete = true;
        this.playTime = 0;
        this.seekBar.setVisibility(8);
        this.image.setVisibility(0);
        this.playButton.setVisibility(0);
        this.loading.setVisibility(8);
        if (this.mVideoView != null) {
            this.mVideoView.pause();
        }
        stopSendHander();
        LogInfo.log("LM", "completion");
        updatePlayDataStatistics("time", (this.timeElapsed - this.lastTimeElapsed) / 1000 == 0 ? (this.timeElapsed - this.lastTimeElapsed) % 1000 : ((this.timeElapsed - this.lastTimeElapsed) / 1000) + 1, this.isAutoPlay);
        updatePlayDataStatistics("end", -1, this.isAutoPlay);
        this.mActivity.setHomeClickNum(0);
        this.mActivity.setUuidTime(null);
        this.mActivity.mIsHomeClicked = false;
        LogInfo.log("jc666", "complete");
    }

    public void onPrepared(MediaPlayer arg0) {
        LogInfo.log("king", "onPrepared");
        this.isPrepared = true;
        updatePlayDataStatistics("play", -1, this.isAutoPlay);
        updatePlayDataStatistics("finish", -1, this.isAutoPlay);
        if (this.mVideoView != null) {
            requestAudioFocus();
            this.image.setVisibility(0);
            this.seekBar.setVisibility(0);
            if ("0".equals(this.duration.getTag())) {
                this.duration.setText(LetvUtils.getNumberTime2((long) (this.mVideoView.getDuration() / 1000)));
                this.duration.setVisibility(0);
            }
            setSeekBarProgress(this.mVideoView.getDuration(), 0);
            if (this.isOnPreparePause) {
                this.loading.setVisibility(8);
                this.playButton.setVisibility(0);
                stopSendHander();
                LogInfo.log("LM", "prepared");
                return;
            }
            this.playButton.setVisibility(8);
        }
    }

    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        LogInfo.log(this.Tag + "||wlx", "onError:" + arg1);
        stopPlayback();
        return false;
    }

    public void setSeekBarProgress(int duration, int current) {
        this.seekBar.setMax(duration);
        this.seekBar.setProgress(current);
    }

    public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
        this.loading.setVisibility(0);
    }

    public void destory() {
        try {
            LogInfo.log("king", "destory");
            abandonAudioFocus();
            if (this.wakeLock != null) {
                try {
                    this.wakeLock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stopPlayback();
            stopSendHander();
            LogInfo.log("LM", "destroy");
            this.errorLayout.setVisibility(8);
            this.loading.setVisibility(8);
            this.seekBar.setVisibility(8);
            this.image.setVisibility(0);
            this.playButton.setVisibility(8);
            this.rootView.removeAllViews();
            this.rootView.setOnClickListener(null);
            this.playButton.setOnClickListener(null);
            this.errorLayout.setVisibility(8);
            this.errorLayout.setOnClickListener(null);
            this.mVideoView = null;
            this.root = null;
            this.isDestroy = true;
            this.updateCount = 0;
            this.lastTimeElapsed = 0;
            this.timeElapsed = 0;
            this.lastPlayTime = 0;
            this.playAdFragment = null;
            this.isStaticsInit = false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void requestAudioFocus() {
        if (this.mActivity != null) {
            this.mAudioManager = (AudioManager) this.mActivity.getSystemService("audio");
            if (VERSION.SDK_INT >= 8) {
                this.mAudioManager.requestAudioFocus(null, 3, 2);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void abandonAudioFocus() {
        if (this.mActivity != null) {
            this.mAudioManager = (AudioManager) this.mActivity.getSystemService("audio");
            if (VERSION.SDK_INT >= 8) {
                this.mAudioManager.abandonAudioFocus(null);
            }
        }
    }

    public void updatePlayDataStatistics(String actionCode, long pt, boolean isAutoPlay) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("gslb=1&");
            sb.append("cload=1&");
            if (this.mIsFromPush) {
                sb.append("push=1");
                sb.append("&type=" + StatisticsUtils.sStatisticsPushData.mContentType);
                sb.append("&pushtype=" + StatisticsUtils.sStatisticsPushData.mType);
                sb.append("&pushmsg=" + StatisticsUtils.sStatisticsPushData.mAllMsg);
            } else {
                sb.append("push=0&");
                sb.append("pushtype=-&");
            }
            sb.append("vsend=CDN&");
            sb.append("vformat=m3u8&");
            sb.append("app=" + LetvUtils.getClientVersionName() + "&");
            sb.append("&speed=" + StatisticsUtils.getSpeed());
            if (StatisticsUtils.mType != null) {
                sb.append("&player=" + StatisticsUtils.mType);
            }
            sb.append("&time=").append(LetvDateUtils.timeClockString("yyyyMMdd_HH:mm:ss"));
            if (PreferencesManager.getInstance().isVip()) {
                sb.append("&vip=1");
            } else {
                sb.append("&vip=0");
            }
            sb.append("&sdk_ver=" + LetvMediaPlayerManager.getInstance().getSdkVersion());
            sb.append("&cpu=" + NativeInfos.getCPUClock());
            if ("ios".equals(LetvApplication.getInstance().getVideoFormat())) {
                sb.append("&cs=m3u8");
            } else if ("no".equals(LetvApplication.getInstance().getVideoFormat())) {
                sb.append("&cs=mp4");
            }
            sb.append("&su=1");
            sb.append("&vip=").append(PreferencesManager.getInstance().isVip() ? 1 : 0);
            String cdeVer = LetvApplication.getInstance().getCdeHelper().getServiceVersion();
            String cdeId = "3000";
            String dura = (String) this.duration.getTag();
            String ref = PageIdConstant.hotIndexCategoryPage + "_-_-";
            AlbumPlayHotFlow flow = this.mActivity.getFlow();
            if (flow == null) {
                LogInfo.LogStatistics("hot flow is null");
                return;
            }
            String cid = flow.mCurrentPlayingVideo != null ? String.valueOf(flow.mCurrentPlayingVideo.cid) : "";
            String pid = flow.mCurrentPlayingVideo != null ? String.valueOf(flow.mCurrentPlayingVideo.pid) : "";
            String fVid = flow.mCurrentPlayingVideo != null ? String.valueOf(flow.mCurrentPlayingVideo.vid) : this.vid + "";
            StatisticsPlayInfo playInfo = new StatisticsPlayInfo();
            playInfo.setcTime(System.currentTimeMillis());
            playInfo.setIpt(0);
            LogInfo.log("jc666", "acode=" + actionCode + ",vid=" + this.vid + ",flow vid=" + fVid + ",hashcode=" + hashCode());
            if (actionCode.equals("init")) {
                DataStatistics instance = DataStatistics.getInstance();
                Context context = this.mActivity;
                String str = "0";
                String str2 = "0";
                String str3 = "0";
                StringBuilder stringBuilder = new StringBuilder();
                if (pt <= 0) {
                    pt = 0;
                }
                instance.sendPlayInfoInit(context, str, str2, actionCode, str3, stringBuilder.append(pt).append("").toString(), com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, LetvUtils.getUID(), this.mActivity.getUuidTime(), cid, pid, fVid, dura, "0", "0", flow.getStreamLevel(), this.playUri == null ? "" : this.playUri.toString(), ref, sb.toString(), null, null, LetvUtils.getPcode(), PreferencesManager.getInstance().isLogin() ? 0 : 1, null, null, isAutoPlay ? 1 : 0, cdeVer, cdeId, playInfo);
                return;
            }
            DataStatistics instance2;
            Context context2;
            String str4;
            String str5;
            String str6;
            StringBuilder stringBuilder2;
            if (actionCode.equals("play")) {
                playInfo.setJoint(0);
                playInfo.setPay(0);
                instance2 = DataStatistics.getInstance();
                context2 = this.mActivity;
                str4 = "0";
                str5 = "0";
                str6 = "0";
                stringBuilder2 = new StringBuilder();
                if (pt <= 0) {
                    pt = 0;
                }
                instance2.sendPlayInfoPlay(context2, str4, str5, actionCode, str6, stringBuilder2.append(pt).append("").toString(), com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, LetvUtils.getUID(), this.mActivity.getUuidTime(), cid, pid, fVid, dura, "0", "0", flow.getStreamLevel(), this.playUri == null ? "" : this.playUri.toString(), ref, sb.toString(), null, null, LetvUtils.getPcode(), PreferencesManager.getInstance().isLogin() ? 0 : 1, null, null, isAutoPlay ? 1 : 0, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, 1, playInfo);
                return;
            }
            if (actionCode.equals("launch")) {
                fVid = this.vid + "";
            }
            instance2 = DataStatistics.getInstance();
            context2 = this.mActivity;
            str4 = "0";
            str5 = "0";
            str6 = "0";
            stringBuilder2 = new StringBuilder();
            if (pt <= 0) {
                pt = 0;
            }
            instance2.sendPlayInfo24New(context2, str4, str5, actionCode, str6, stringBuilder2.append(pt).append("").toString(), com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, LetvUtils.getUID(), this.mActivity.getUuidTime(), cid, pid, fVid, dura, "0", "0", flow.getStreamLevel(), this.playUri == null ? "" : this.playUri.toString(), ref, sb.toString(), null, null, LetvUtils.getPcode(), PreferencesManager.getInstance().isLogin() ? 0 : 1, null, null, isAutoPlay ? 1 : 0, playInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onChange(int state) {
        if (state == 3) {
            if (this.errorLayout.getVisibility() == 0) {
                this.errorLayout.setVisibility(8);
            }
            if (!this.mIsSendingHandler) {
                startSendHander();
            }
        } else if (state == 4) {
            this.playTime = this.mVideoView.getCurrentPosition();
        }
    }

    public void noNetError() {
        this.mVideoView.pause();
        this.loading.setVisibility(8);
        this.image.setVisibility(8);
        this.seekBar.setVisibility(8);
        this.rootView.setVisibility(8);
        this.errorLayout.setVisibility(0);
        this.errorTip.setText(2131100176);
        this.errorRetry.setVisibility(0);
        this.rootView.setOnClickListener(null);
        stopSendHander();
    }

    public boolean isPlaying() {
        LogInfo.log("king", "isPlaying");
        if (this.mVideoView == null) {
            return false;
        }
        return this.mVideoView.isPlaying();
    }

    public void setPlayUri(String playUrl) {
        if (playUrl != null) {
            this.playUri = Uri.parse(playUrl);
            this.isWo3GtoWifiPause = false;
        }
    }

    public void setIsOnPreparePause(boolean isOnPreparePause) {
        this.isOnPreparePause = isOnPreparePause;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.isAutoPlay = autoPlay;
    }

    public void setShowToast(boolean showToast) {
        this.isShowToast = showToast;
    }

    public void setUrlNull() {
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            this.isWo3GtoWifiPause = true;
        }
    }

    public void setPlayTime(int currentTime) {
        this.playTime = currentTime;
    }

    public boolean isVideoviewNull() {
        if (this.mVideoView != null && this.mVideoView.isInPlaybackState()) {
            return false;
        }
        return true;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public int getCurrentTime() {
        if (this.mVideoView == null || isComplete()) {
            return 0;
        }
        LogInfo.log("king", ".getCurrentTime()..playTime=" + this.playTime);
        LogInfo.log("king", "...isInPlaybackState() =" + this.mVideoView.isInPlaybackState());
        LogInfo.log("king", ".getCurrentTime()..mVideoView.getCurrentPosition()=" + this.mVideoView.getCurrentPosition());
        return this.mVideoView.isInPlaybackState() ? this.mVideoView.getCurrentPosition() : this.playTime;
    }

    public boolean isShow3gToast() {
        return this.isShow3gToast;
    }

    public void playNet(String uriString, int msec) {
        LogInfo.log("jc666", "热点 播放开始");
        this.isWo3GtoWifiPause = false;
        if (!this.isStatisticsLaunch) {
            updatePlayDataStatistics("launch", -1, this.isAutoPlay);
        }
        updatePlayDataStatistics("init", -1, this.isAutoPlay);
        this.isStaticsInit = true;
        this.isComplete = false;
        this.isStatisticsLaunch = false;
        NativeInfos.mOffLinePlay = false;
        initNativeInfos();
        NativeInfos.mIsLive = false;
        if (this.mVideoView != null && !TextUtils.isEmpty(uriString)) {
            this.playUri = Uri.parse(uriString);
            this.root.setVisibility(0);
            this.rootView.setVisibility(0);
            this.mVideoView.setVideoPath(uriString);
            this.mVideoView.setOnErrorListener(this);
            this.mVideoView.setOnCompletionListener(this);
            this.mVideoView.setOnPreparedListener(this);
            this.image.setVisibility(0);
            this.loading.setVisibility(0);
            this.playButton.setVisibility(8);
            this.lastPlayTime = 0;
            this.isOnPreparePause = false;
            if (msec > 0) {
                this.playTime = msec;
                this.isFirstPlay = false;
                this.mVideoView.seekTo(msec);
            }
            LogInfo.log(this.Tag + "||wlx", "mVideoView.start() playTime = " + this.playTime);
            pushHotAd();
            this.mVideoView.start();
        }
    }

    private void pushHotAd() {
        try {
            if (this.mActivity instanceof LetvHotActivity) {
                VideoBean video = this.mActivity.getFlow().mCurrentPlayingVideo;
                if (this.playAdFragment == null) {
                    this.playAdFragment = new AdPlayFragmentProxy(this.mActivity);
                }
                if (video != null) {
                    this.playAdFragment.getDemandFrontAdForHot(this.mActivity, video.cid, 0, (long) this.vid, video.mid, DataUtils.getUUID(this.mActivity), PreferencesManager.getInstance().getUserId(), video.duration + "", "", "0", isSupportM3U8(), video.needPay(), false, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(int msec) {
        LogInfo.log(this.Tag + "||wlx", JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START);
        if (this.mVideoView != null) {
            if (this.errorLayout.getVisibility() == 0) {
                this.errorLayout.setVisibility(8);
            }
            if (msec > 0) {
                this.mVideoView.seekTo(msec);
            }
            this.mVideoView.start();
            this.playButton.setVisibility(8);
            this.image.setVisibility(0);
            startSendHander();
        }
    }

    public void pause() {
        LogInfo.log(this.Tag + "||wlx", "pause");
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            this.seekBar.setVisibility(8);
            this.playButton.setVisibility(0);
            this.loading.setVisibility(8);
            if (this.mVideoView.isInPlaybackState()) {
                this.image.setVisibility(8);
            } else {
                this.image.setVisibility(0);
            }
            stopSendHander();
        }
    }

    public void pauseFor3GtoWifi() {
        LogInfo.log("king", "pause");
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            this.loading.setVisibility(0);
            setUrlNull();
            stopSendHander();
            LogInfo.log(this.Tag + "||wlx", "pause to wifi");
        }
    }

    public void pauseToPlay(boolean isFromShare) {
        if (this.mVideoView != null && !this.isComplete && this.isPrepared) {
            this.mVideoView.start();
        } else if (!(this.mVideoView == null || this.playUri == null)) {
            if (this.isComplete) {
                this.playTime = 0;
                this.mVideoView.stopPlayback();
            }
            playNet(this.playUri.toString(), this.playTime);
        }
        AlbumPlayHotFlow.sAutoPlay = true;
        AlbumPlayHotFlow.sIsClickToPlay = false;
        this.playButton.setVisibility(8);
        startSendHander();
        if (!isFromShare) {
            try {
                LogInfo.LogStatistics("热点暂停上报");
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c332", null, -1, null, PageIdConstant.hotIndexCategoryPage, null, null, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
