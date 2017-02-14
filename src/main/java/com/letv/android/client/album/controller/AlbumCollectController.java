package com.letv.android.client.album.controller;

import android.text.TextUtils;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.DBManager;
import com.letv.core.db.FavoriteTraceHandler;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

public class AlbumCollectController {
    private CollectState curCollectState = CollectState.DISABLE_COLLECT;
    private AlbumPlayActivity mActivity;
    private String mCancelFavoriteMessage;
    private String mFavoriteMessage;
    private boolean mIsCollect;
    private List<AlbumCollectListener> mListenerList = new ArrayList();

    public AlbumCollectController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        this.mFavoriteMessage = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_FAVORITE_SUCCESS, this.mActivity.getString(R.string.toast_favorite_ok));
        this.mCancelFavoriteMessage = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_FAVORITE_CANCEL_SUCCESS, this.mActivity.getString(R.string.toast_favorite_cancel));
    }

    public void addCollectListener(AlbumCollectListener listener) {
        if (listener != null) {
            this.mListenerList.add(listener);
        }
    }

    public void setIsCollect(boolean isCollect) {
        this.mIsCollect = isCollect;
        setCollectState(isCollect ? CollectState.HAS_COLLECTED : CollectState.NOT_COLLECT);
    }

    public void updateCollectState() {
        if (NetworkUtils.isNetworkAvailable() || this.mIsCollect) {
            setIsCollect(this.mIsCollect);
        } else {
            setCollectState(CollectState.DISABLE_COLLECT);
        }
    }

    public void setCollectState(CollectState state) {
        this.curCollectState = state;
        if (NetworkUtils.isNetworkAvailable()) {
            for (AlbumCollectListener albumCollectListener : this.mListenerList) {
                albumCollectListener.setCollectState(state);
            }
        }
    }

    public CollectState getCurCollectState() {
        return this.curCollectState;
    }

    public void collectClick() {
        if (!NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast(LetvTools.getTextFromServer("500003", this.mActivity.getString(R.string.network_unavailable)));
        } else if (this.curCollectState != CollectState.DISABLE_COLLECT) {
            AlbumHalfFragment controller = this.mActivity.getHalfFragment();
            if (controller == null) {
                return;
            }
            if (controller.getCurrPlayingVideo() == null) {
                LogInfo.log("songhang", "当点击半屏页 收藏时 当前视频不存在");
            } else if (PreferencesManager.getInstance().isLogin()) {
                changeCloudFavoriteState();
            } else {
                changeLoaclPavoriteState();
            }
        }
    }

    private void changeCloudFavoriteState() {
        AlbumHalfFragment controller = this.mActivity.getHalfFragment();
        if (controller != null) {
            VideoBean curVideoBean = controller.getCurrPlayingVideo();
            if (curVideoBean != null) {
                FavoriteTraceHandler favoriteTraceHandler = DBManager.getInstance().getFavoriteTrace();
                long pid = curVideoBean.pid;
                long vid = curVideoBean.vid;
                if (this.mIsCollect) {
                    favoriteTraceHandler.requestGetDeleteFavourite(pid, vid, 1, new 1(this));
                } else {
                    favoriteTraceHandler.requestGetAddFavourite(pid, vid, 1, 3, new 2(this));
                }
            }
        }
    }

    private void changeLoaclPavoriteState() {
        if (this.mIsCollect) {
            setIsCollect(false);
            DBManager.getInstance().getFavoriteTrace().remove(generateAlbumNew());
            ToastUtils.showToast(this.mCancelFavoriteMessage);
            return;
        }
        setIsCollect(true);
        DBManager.getInstance().getFavoriteTrace().saveFavoriteTrace(generateAlbumNew(), System.currentTimeMillis() / 1000);
        ToastUtils.showToast(this.mFavoriteMessage);
    }

    public AlbumInfo generateAlbumNew() {
        AlbumHalfFragment controller = this.mActivity.getHalfFragment();
        if (controller == null) {
            return new AlbumInfo();
        }
        AlbumInfo fromAlbumInfo = controller.getAlbumInfo();
        VideoBean fromVideobean = controller.getCurrPlayingVideo();
        if (fromVideobean == null) {
            return null;
        }
        AlbumInfo albumInfo = new AlbumInfo();
        if (fromAlbumInfo == null || !AlbumInfo.isMovieOrTvOrCartoon(fromAlbumInfo.cid) || fromAlbumInfo.pid <= 0) {
            if (!TextUtils.isEmpty(fromVideobean.nameCn)) {
                albumInfo.nameCn = fromVideobean.nameCn;
            } else if (TextUtils.isEmpty(fromVideobean.title)) {
                albumInfo.nameCn = fromVideobean.pidname;
            } else {
                albumInfo.nameCn = fromVideobean.title;
            }
            albumInfo.subTitle = fromVideobean.subTitle;
            albumInfo.pic320_200 = fromVideobean.pic320_200;
            albumInfo.nowEpisodes = fromVideobean.nowEpisodes;
            albumInfo.episode = fromVideobean.episode;
            albumInfo.isEnd = fromVideobean.isEnd;
            albumInfo.starring = fromVideobean.starring;
            albumInfo.type = 3;
        } else {
            albumInfo = fromAlbumInfo;
            albumInfo.type = 1;
        }
        if (fromVideobean == null) {
            return albumInfo;
        }
        albumInfo.pid = fromVideobean.pid;
        albumInfo.vid = fromVideobean.vid;
        albumInfo.cid = fromVideobean.cid;
        return albumInfo;
    }

    public boolean ismIsCollect() {
        return this.mIsCollect;
    }
}
