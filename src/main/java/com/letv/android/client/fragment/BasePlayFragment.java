package com.letv.android.client.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.listener.PlayActivityCallback;
import com.letv.android.client.widget.PlayBlock;
import com.letv.android.client.widget.PlayBlock$PlayBlockCallback;
import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.LetvVideoViewBuilder;
import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.component.player.fourd.LetvMediaPlayerControl4D;
import com.letv.component.player.fourd.LetvVideoViewBuilder4D;
import com.letv.component.player.utils.NativeInfos;
import com.letv.core.constant.PlayConstant;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.ScreenInfoUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.media.ffmpeg.FFMpegPlayer;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;

public abstract class BasePlayFragment extends LetvBaseFragment implements OnErrorListener, OnPreparedListener, OnCompletionListener, OnBlockListener, PlayBlock$PlayBlockCallback {
    private int mCurrentPostion;
    private int mDecorderType;
    private boolean mEnforcementWait;
    private boolean mFirstStart;
    private String mHaptUrl;
    private int mLaunchMode;
    private RelativeLayout mLayout;
    private Type mOldType;
    private String mPageId;
    private PlayBlock mPlayBlock;
    protected PlayActivityCallback mPlayFragmentListener;
    private int mPlayType;
    private Uri mPlayUri;
    private ViewGroup mRoot;
    private boolean mScreenLock;
    private int mSourceType;
    private OnVideoViewStateChangeListener mStateChangeListener;
    protected LetvMediaPlayerControl mVideoView;

    public BasePlayFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mLaunchMode = 1;
        this.mDecorderType = 0;
        this.mFirstStart = false;
        this.mScreenLock = false;
        this.mEnforcementWait = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRoot = (ViewGroup) inflater.inflate(R.layout.fragment_play_playerlibs, null);
        this.mLayout = (RelativeLayout) this.mRoot.findViewById(R.id.play_video_layout);
        initData();
        return this.mRoot;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configurationChanged();
    }

    public void onResume() {
        super.onResume();
        boolean isDlnaPlaying;
        if (this.mPlayFragmentListener != null) {
            isDlnaPlaying = this.mPlayFragmentListener.isDlnaPlaying();
        } else {
            isDlnaPlaying = false;
        }
        if (!(!ScreenInfoUtils.reflectScreenState() || this.mFirstStart || isDlnaPlaying)) {
            resume();
        }
        this.mFirstStart = false;
    }

    private void initData() {
        this.mPlayBlock = new PlayBlock(getActivity(), this);
    }

    public void setIntent(Intent intent) {
        if (intent != null) {
            this.mPageId = intent.getStringExtra(PlayConstant.PAGE_ID);
            this.mLaunchMode = intent.getIntExtra("launchMode", 0);
            this.mHaptUrl = intent.getStringExtra(PlayConstant.HAPT_URL);
            this.mPlayType = intent.getIntExtra(PlayConstant.PLAY_TYPE, 0);
        }
    }

    public void setSourceType(int type) {
        this.mSourceType = type;
        if (this.mVideoView != null) {
            this.mVideoView.setSourceType(this.mSourceType);
        }
    }

    public void setPlayFragmentListener(PlayActivityCallback playFragmentListener) {
        this.mPlayFragmentListener = playFragmentListener;
    }

    public void setFirstStart(boolean firstStart) {
        this.mFirstStart = firstStart;
    }

    public void setScreenLock(boolean screenLock) {
        this.mScreenLock = screenLock;
    }

    public void setStateChangeListener(OnVideoViewStateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
        if (this.mVideoView != null) {
            this.mVideoView.setVideoViewStateChangeListener(this.mStateChangeListener);
        }
    }

    public void initVideoview(boolean isLocal) {
        PlayLiveFlow.LogAddInfo("开始初始化播放器", "isLocal=" + isLocal);
        String videoFormat = LetvApplication.getInstance().getVideoFormat();
        boolean defaultStreamDecorder = LetvApplication.getInstance().getDefaultHardStreamDecorder();
        if (!(this.mVideoView == null || this.mLayout == null)) {
            this.mLayout.removeView(this.mVideoView.getView());
        }
        if (isLocal) {
            this.mVideoView = LetvVideoViewBuilder.getInstants().build(this.mContext, Type.MOBILE_H264_MP4);
            this.mOldType = Type.MOBILE_H264_MP4;
            this.mDecorderType = 0;
        } else {
            PlayLiveFlow.LogAddInfo("开始初始化播放器", "mPlayType=" + this.mPlayType);
            if (this.mPlayType == 1) {
                this.mVideoView = LetvVideoViewBuilder.getInstants().build(getActivity(), Type.MOBILE_H264_M3U8);
                this.mOldType = Type.MOBILE_H264_M3U8;
                this.mDecorderType = 0;
            } else if (TextUtils.equals(this.mPageId, PageIdConstant.localPage)) {
                this.mVideoView = LetvVideoViewBuilder.getInstants().build(getActivity(), Type.MOBILE_H264_MP4);
                this.mOldType = Type.MOBILE_H264_MP4;
                this.mDecorderType = 0;
            } else if (!TextUtils.equals(videoFormat, "no") || this.mLaunchMode == 1 || this.mLaunchMode == 4) {
                if (TextUtils.isEmpty(this.mHaptUrl)) {
                    this.mVideoView = LetvVideoViewBuilder.getInstants().build(getActivity(), Type.MOBILE_H264_MP4);
                    this.mOldType = Type.MOBILE_H264_MP4;
                    this.mDecorderType = 0;
                } else {
                    LetvMediaPlayerControl4D control4D = LetvVideoViewBuilder4D.getInstants().build(this.mContext, LetvVideoViewBuilder4D.Type.MOBILE_H264_MP4_4D);
                    control4D.setHaptUrl(this.mHaptUrl);
                    LogInfo.log("wlx", "= " + this.mHaptUrl);
                    this.mHaptUrl = "";
                    this.mOldType = Type.MOBILE_H264_MP4;
                    this.mVideoView = control4D;
                    this.mDecorderType = 0;
                }
            } else if (defaultStreamDecorder) {
                this.mVideoView = LetvVideoViewBuilder.getInstants().build(this.mContext, Type.MOBILE_H264_M3U8_HW);
                this.mOldType = Type.MOBILE_H264_M3U8_HW;
                this.mDecorderType = 1;
            } else {
                this.mVideoView = LetvVideoViewBuilder.getInstants().build(this.mContext, Type.MOBILE_H264_M3U8);
                this.mOldType = Type.MOBILE_H264_M3U8;
                this.mDecorderType = 0;
            }
        }
        StatisticsUtils.mType = this.mOldType;
        addVideoView();
    }

    private void addVideoView() {
        PlayLiveFlow.LogAddInfo("开始初始化播放器 添加VideoView到父容器", "");
        this.mLayout.removeAllViews();
        if (this.mVideoView != null) {
            this.mLayout.addView(this.mVideoView.getView(), new LayoutParams(-2, -2));
        }
        this.mVideoView.setSourceType(this.mSourceType);
        this.mVideoView.setOnBlockListener(this);
        this.mVideoView.setVideoViewStateChangeListener(this.mStateChangeListener);
    }

    public void changeVideoView(Type type) {
        if (this.mOldType != type && this.mVideoView != null) {
            this.mVideoView.stopPlayback();
            this.mVideoView = LetvVideoViewBuilder.getInstants().build(getActivity(), type);
            addVideoView();
            LogInfo.log("zhuqiao", "---PlayNet---changeVideoView" + this.mVideoView.getClass().getSimpleName());
            this.mOldType = type;
            StatisticsUtils.mType = this.mOldType;
        }
    }

    public void initNativeInfos() {
        String vf = LetvApplication.getInstance().getVideoFormat();
        if (TextUtils.equals("ios", vf)) {
            NativeInfos.mOffLinePlay = false;
            NativeInfos.mIsLive = false;
        } else if (TextUtils.equals("no", vf)) {
            NativeInfos.mOffLinePlay = true;
            NativeInfos.mIfNative3gpOrMp4 = true;
            NativeInfos.mIsLive = false;
        }
    }

    public void playNet(String uriString, boolean isDolby, int msec) {
        PlayLiveFlow.LogAddInfo("开始交给播放器播放", "uriString=" + uriString);
        if (!TextUtils.isEmpty(uriString) && getActivity() != null) {
            if (!StatisticsUtils.isFirstPlay) {
                StatisticsUtils.isFirstPlay = true;
                StatisticsUtils.statisticsLoginAndEnv(this.mContext, 4, true);
                StatisticsUtils.statisticsLoginAndEnv(this.mContext, 4, false);
            }
            PlayLiveFlow.LogAddInfo("开始交给播放器播放", "mVideoView=" + this.mVideoView);
            if (this.mVideoView == null) {
                initVideoview(false);
            }
            PlayLiveFlow.LogAddInfo("开始交给播放器播放", "mPlayType=" + this.mPlayType);
            if (this.mPlayType == 1) {
                this.mVideoView.setVideoPath(uriString);
            } else {
                if (!TextUtils.equals("ios", LetvApplication.getInstance().getVideoFormat())) {
                    changeVideoView(Type.MOBILE_H264_MP4);
                } else if (LetvApplication.getInstance().getDefaultHardStreamDecorder()) {
                    changeVideoView(Type.MOBILE_H264_M3U8_HW);
                } else {
                    changeVideoView(Type.MOBILE_H264_M3U8);
                }
                int playLevel = 0;
                if (this.mPlayFragmentListener != null) {
                    playLevel = this.mPlayFragmentListener.getPlayLevel();
                }
                LogInfo.log("zhuqiao", "oldType:" + this.mOldType + "====level:" + playLevel);
                this.mVideoView.setVideoPlayUrl(PlayUtils.getPlayUrl(uriString, this.mOldType, playLevel));
            }
            this.mPlayUri = Uri.parse(uriString);
            NativeInfos.mOffLinePlay = false;
            initNativeInfos();
            NativeInfos.mIsLive = this.mPlayType == 1;
            if (isDolby) {
                NativeInfos.mOffLinePlay = true;
                NativeInfos.mIfNative3gpOrMp4 = true;
                NativeInfos.mIsLive = false;
            }
            this.mVideoView.setOnErrorListener(this);
            this.mVideoView.setOnCompletionListener(this);
            this.mVideoView.setOnPreparedListener(this);
            this.mVideoView.getView().requestFocus();
            this.mVideoView.setEnforcementPause(false);
            this.mVideoView.setEnforcementWait(this.mEnforcementWait);
            if (msec > 0) {
                this.mVideoView.seekTo(msec);
            }
            PlayLiveFlow.LogAddInfo("开始交给播放器播放", "mScreenLock=" + this.mScreenLock);
            if (!this.mScreenLock && !this.mEnforcementWait) {
                this.mVideoView.getView().setVisibility(0);
                PlayLiveFlow.LogAddInfo("播放器开始播放ed", "uriString=" + uriString);
                this.mVideoView.start();
            }
        }
    }

    public void resume() {
        if (this.mVideoView != null) {
            if (this.mPlayType == 0) {
                if (this.mVideoView.getLastSeekWhenDestoryed() != 0) {
                    this.mVideoView.seekTo(this.mVideoView.getLastSeekWhenDestoryed());
                } else if (!(this.mCurrentPostion == 0 || this.mVideoView == null)) {
                    this.mVideoView.seekTo(this.mCurrentPostion);
                }
            }
            this.mVideoView.setSourceType(this.mSourceType);
            this.mVideoView.start();
            if (this.mPlayFragmentListener != null) {
                this.mPlayFragmentListener.callAdsInterface(3, false);
                this.mPlayFragmentListener.cancelLongTimeWatch();
            }
        }
        if (VERSION.SDK_INT > 8 && this.mContext != null) {
            IRVideo.getInstance().videoPlay(this.mContext);
        }
    }

    public void pause() {
        if (this.mVideoView != null) {
            this.mVideoView.pause();
        }
        if (VERSION.SDK_INT > 8) {
            IRVideo.getInstance().videoPause();
        }
        if (this.mPlayFragmentListener != null) {
            this.mPlayFragmentListener.callAdsInterface(2, true);
        }
    }

    public void stopPlayback() {
        if (this.mVideoView != null) {
            this.mVideoView.stopPlayback();
        }
        if (VERSION.SDK_INT > 8) {
            IRVideo.getInstance().videoEnd(this.mContext);
        }
    }

    public void destroyVedioView() {
        if (!(this.mVideoView == null || this.mLayout == null)) {
            this.mVideoView.stopPlayback();
            this.mLayout.removeView(this.mVideoView.getView());
        }
        this.mOldType = null;
        this.mVideoView = null;
        StatisticsUtils.mType = null;
    }

    public void seekTo(int msec) {
        if (this.mVideoView != null) {
            this.mVideoView.seekTo(msec);
        }
    }

    public void forward() {
        if (this.mVideoView != null) {
            this.mVideoView.forward();
        }
    }

    public void rewind() {
        if (this.mVideoView != null) {
            this.mVideoView.rewind();
        }
    }

    public boolean isPlaying() {
        return this.mVideoView == null ? false : this.mVideoView.isPlaying();
    }

    public boolean isPaused() {
        return this.mVideoView == null ? false : this.mVideoView.isPaused();
    }

    public LetvMediaPlayerControl getVideoView() {
        return this.mVideoView;
    }

    public int getCurrentPosition() {
        return this.mVideoView.getCurrentPosition();
    }

    public int getBufferPercentage() {
        return this.mVideoView.getBufferPercentage();
    }

    public int getDuration() {
        return this.mVideoView.getDuration();
    }

    public boolean isInPlaybackState() {
        return this.mVideoView.isInPlaybackState();
    }

    public void setEnforcementWait(boolean enforcementWait) {
        this.mEnforcementWait = enforcementWait;
        if (this.mVideoView != null) {
            this.mVideoView.setEnforcementWait(enforcementWait);
        }
    }

    public boolean isEnforcementWait() {
        return this.mEnforcementWait;
    }

    public void setEnforcementPause(boolean enforcementPause) {
        if (this.mVideoView != null) {
            this.mVideoView.setEnforcementPause(enforcementPause);
        }
    }

    public boolean isEnforcementPause() {
        if (this.mVideoView != null) {
            return this.mVideoView.isEnforcementPause();
        }
        return false;
    }

    private void configurationChanged() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        configurationChanged();
    }

    public void onPause() {
        super.onPause();
        pause();
        if (this.mVideoView != null) {
            this.mCurrentPostion = this.mVideoView.getCurrentPosition();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mPlayUri = null;
        destroyVedioView();
    }

    public void onCompletion(MediaPlayer mp) {
        if (this.mPlayFragmentListener != null) {
            this.mPlayFragmentListener.callAdsInterface(4, false);
        }
    }

    public void onPrepared(MediaPlayer mp) {
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        StringBuilder sb = new StringBuilder();
        if (this.mPlayUri != null) {
            sb.append("playurl=").append(this.mPlayUri.toString());
        }
        if (what == -91) {
            DataStatistics.getInstance().sendErrorInfo(getActivity(), "0", "0", LetvErrorCode.VIDEO_PLAY_TIMEOUT, null, sb.toString(), null, null, null, null);
        } else if (what == -103) {
            DataStatistics.getInstance().sendErrorInfo(getActivity(), "0", "0", LetvErrorCode.VIDEO_PLAY_NOT_LEGITIMATE, null, sb.toString(), null, null, null, null);
        } else {
            DataStatistics.getInstance().sendErrorInfo(getActivity(), "0", "0", LetvErrorCode.VIDEO_PLAY_OTHER_ERROR, null, sb.toString(), null, null, null, null);
        }
        if (this.mPlayFragmentListener != null) {
            this.mPlayFragmentListener.callAdsInterface(5, false);
        }
        return false;
    }

    public void resumeVideo() {
        if (this.mVideoView != null) {
            this.mVideoView.start();
        }
    }

    public void pauseVideo() {
        if (this.mVideoView != null) {
            this.mVideoView.pause();
        }
    }

    public Rect getPlayerRect() {
        Rect rect = new Rect();
        this.mRoot.getGlobalVisibleRect(rect);
        return rect;
    }

    public void onBlock(FFMpegPlayer arg0, int i) {
        if (this.mPlayFragmentListener != null) {
            if (i == 10003) {
                this.mPlayFragmentListener.blockStart();
                this.mPlayBlock.blockStart();
            } else if (i == FFMpegPlayer.MEDIA_CACHE_END) {
                this.mPlayFragmentListener.blockEnd();
                this.mPlayBlock.blockEnd();
            }
        }
    }

    public void blockTwiceAlert() {
        if (this.mPlayFragmentListener != null) {
            this.mPlayFragmentListener.blockTwiceAlert();
        }
    }
}
