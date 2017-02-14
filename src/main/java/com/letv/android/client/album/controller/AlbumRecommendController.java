package com.letv.android.client.album.controller;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.core.bean.VideoBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.tencent.open.SocialConstants;
import java.util.Observable;
import java.util.Observer;

public class AlbumRecommendController implements Observer {
    private static final int DELAYCLOSE = 8000;
    private final int MSG = 1;
    private final int SHOW_PLAY_RECOMMEND_TIP_TIME = 20;
    private final int SHOW_PLAY_RECOMMEND_TIP_TIME_CONTINUE = 8;
    private AlbumPlayActivity mActivity;
    private View mClose;
    private View mContainView;
    private TextView mContentTextView;
    private TextView mHalfContentTextView;
    private Handler mHandler = new 1(this);
    private boolean mHasShowed = false;
    private View mImageFrameView;
    private boolean mIsCloseVideoRecommend = false;
    private ImageView mRecommendImageView;

    public AlbumRecommendController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        this.mContainView = this.mActivity.findViewById(R.id.detailplay_full_recommend_tip);
        this.mImageFrameView = this.mActivity.findViewById(R.id.recommend_frame);
        this.mRecommendImageView = (ImageView) this.mActivity.findViewById(R.id.imagev_recommend);
        this.mClose = this.mActivity.findViewById(R.id.imagev_recommend_close);
        this.mContentTextView = (TextView) this.mActivity.findViewById(R.id.textv_recommend_content);
        this.mHalfContentTextView = (TextView) this.mActivity.findViewById(R.id.textv_recommend_content_half);
        this.mClose.setOnClickListener(new 2(this));
        this.mContainView.setOnClickListener(new 3(this));
    }

    public void showPlayRecommendTip(long curTime, long endTime) {
        if (this.mContainView.getVisibility() != 0 && !this.mHasShowed && !this.mActivity.mIsPlayingNonCopyright && !this.mActivity.getVideoController().isSwitchingTipViewShowing()) {
            long curTimeTmp = (curTime / 1000) + 20;
            if (curTime / 1000 > endTime - 8 || curTimeTmp < endTime || this.mIsCloseVideoRecommend) {
                this.mHasShowed = false;
                this.mHandler.removeMessages(1);
                if (this.mContainView.getVisibility() != 8) {
                    this.mHandler.post(new 5(this));
                    return;
                }
                return;
            }
            this.mHandler.removeMessages(1);
            this.mHandler.post(new 4(this));
            this.mHandler.sendEmptyMessageDelayed(1, 8000);
        }
    }

    private void showRecommendTipView() {
        if (isControllerShowing() || this.mActivity.getPlayNextController().mRecommendVideo == null || !NetworkUtils.isNetworkAvailable()) {
            hideRecommendTipView();
            return;
        }
        VideoBean recommend = this.mActivity.getPlayNextController().mRecommendVideo;
        this.mContainView.setVisibility(0);
        ImageDownloader.getInstance().download(this.mRecommendImageView, recommend.pic320_200);
        if (TextUtils.isEmpty(recommend.nameCn)) {
            this.mContentTextView.setText(recommend.subTitle);
            this.mHalfContentTextView.setText(recommend.subTitle);
        } else {
            this.mContentTextView.setText(recommend.nameCn);
            this.mHalfContentTextView.setText(recommend.nameCn);
        }
        LogInfo.log("zhuqiao", "大tip曝光");
        StatisticsUtils.staticticsInfoPostAddTargetUrl(this.mActivity, "19", "c68", "1008", 3, SocialConstants.PARAM_AVATAR_URI, null, null, null, null, PageIdConstant.fullPlayPage, null, null, null, null, null, null, null);
    }

    public void hideRecommendTipView() {
        if (this.mContainView.getVisibility() != 8) {
            this.mContainView.setVisibility(8);
        }
    }

    public boolean isControllerShowing() {
        if (this.mActivity.getVideoController() != null) {
            return this.mActivity.getVideoController().isVisible();
        }
        return false;
    }

    public void reset() {
        this.mHasShowed = false;
        this.mIsCloseVideoRecommend = false;
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            if (!TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, (String) data)) {
                return;
            }
            if (UIsUtils.isLandscape(this.mActivity)) {
                this.mContainView.getLayoutParams().width = UIsUtils.dipToPx(292.0f);
                this.mContainView.getLayoutParams().height = UIsUtils.dipToPx(80.0f);
                this.mImageFrameView.getLayoutParams().width = UIsUtils.dipToPx(120.0f);
                this.mImageFrameView.getLayoutParams().height = UIsUtils.dipToPx(68.0f);
                this.mContentTextView.setVisibility(0);
                this.mHalfContentTextView.setVisibility(8);
                return;
            }
            this.mContainView.getLayoutParams().width = UIsUtils.dipToPx(226.0f);
            this.mContainView.getLayoutParams().height = UIsUtils.dipToPx(60.0f);
            this.mImageFrameView.getLayoutParams().width = UIsUtils.dipToPx(90.0f);
            this.mImageFrameView.getLayoutParams().height = UIsUtils.dipToPx(50.0f);
            this.mContentTextView.setVisibility(8);
            this.mHalfContentTextView.setVisibility(0);
        }
    }
}
