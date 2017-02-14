package com.letv.android.client.fragment;

import android.os.Build.VERSION;
import android.text.TextUtils;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.android.client.R;
import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.core.constant.FragmentConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.media.ffmpeg.FFMpegPlayer;

public class PlayLiveFragment extends BasePlayFragment {
    public PlayLiveFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void start(String url, boolean isAdsFinished) {
        PlayLiveFlow.LogAddInfo("播放器开始播放", "url=" + url + ",isAdsFinished=" + isAdsFinished + ",mVideoView" + this.mVideoView);
        if (this.mVideoView == null) {
            initVideoview(false);
            if (!(this.mVideoView == null || TextUtils.isEmpty(url))) {
                this.mVideoView.setEnforcementPause(false);
                this.mVideoView.setEnforcementWait(false);
                this.mVideoView.setVideoPath(url);
                this.mVideoView.getView().setVisibility(0);
                this.mVideoView.start();
            }
        } else {
            PlayLiveFlow.LogAddInfo("播放器开始播放", "mVideoView.isPlaying()=" + this.mVideoView.isPlaying());
            if (!this.mVideoView.isPlaying()) {
                this.mVideoView.setEnforcementPause(false);
                this.mVideoView.setEnforcementWait(false);
                PlayLiveFlow.LogAddInfo("交给播放器开始播放end", "url=" + url);
                this.mVideoView.getView().setVisibility(0);
                this.mVideoView.start();
            }
        }
        if (VERSION.SDK_INT > 8) {
            IRVideo.getInstance().videoPlay(this.mContext);
        }
        if (this.mPlayFragmentListener != null) {
            this.mPlayFragmentListener.callAdsInterface(3, true);
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_PLAY_LIVE;
    }

    public int getDisappearFlag() {
        return 2;
    }

    public int getContainerId() {
        return R.id.play_album_contain;
    }

    public void onBlock(FFMpegPlayer ffMpegPlayer, int blockInfo) {
    }
}
