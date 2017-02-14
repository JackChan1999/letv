package com.letv.android.client.album.controller;

import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.core.bean.VideoBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;

public class AlbumShareController {
    private AlbumPlayActivity mActivity;

    public AlbumShareController(AlbumPlayActivity activity) {
        this.mActivity = activity;
    }

    private boolean clickShare() {
        if (NetworkUtils.isNetworkAvailable()) {
            if (!(this.mActivity.getHalfFragment() == null || this.mActivity.getHalfFragment().getCurrPlayingVideo() == null)) {
                VideoBean video = this.mActivity.getHalfFragment().getCurrPlayingVideo();
                LogInfo.LogStatistics("点播--分享");
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "h22", LetvErrorCode.VIDEO_NOT_HAD, 3, null, PageIdConstant.halpPlayPage, video.cid + "", video.pid + "", video.vid + "", null, null);
            }
            return true;
        }
        ToastUtils.showToast(TipUtils.getTipMessage("500003", R.string.network_unavailable));
        return false;
    }

    public void share(int type, String comment) {
        if (clickShare() && this.mActivity.getShareWindowProtocol() != null) {
            this.mActivity.getShareWindowProtocol().share(type, comment, this.mActivity.findViewById(R.id.play_album_parent_view), this.mActivity.getHalfFragment().getCurrPlayingVideo(), this.mActivity.getHalfFragment().getAlbumCardList());
        }
    }

    public void share() {
        if (clickShare() && this.mActivity.getShareWindowProtocol() != null) {
            this.mActivity.getShareWindowProtocol().share(this.mActivity.findViewById(R.id.play_album_parent_view), this.mActivity.getHalfFragment().getCurrPlayingVideo(), this.mActivity.getHalfFragment().getAlbumCardList());
        }
    }

    public void hideShareDialog() {
        if (this.mActivity.getShareWindowProtocol() != null) {
            this.mActivity.getShareWindowProtocol().hideShareDialog();
        }
    }
}
