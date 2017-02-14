package com.letv.mobile.lebox.ui.album;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.album.IDownloadPage.DownloadPageConfig;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.PublicLoadLayout;
import com.letv.mobile.letvhttplib.bean.VideoBean;
import com.letv.mobile.letvhttplib.bean.VideoListBean;
import java.util.Map;
import org.json.JSONException;

public abstract class BaseDownloadPageFragment extends Fragment implements IDownloadVideoFragment {
    private static final int LOADER_ALBUM_ID = 1;
    private static final String TAG = BaseDownloadPageFragment.class.getSimpleName();
    private boolean isOnPause = false;
    public Activity mActivity;
    public Context mContext;
    public DownloadPageConfig mDownloadConfig;
    public IDownloadPage mDownloadPage;
    public Map<Integer, VideoListBean> mVideosMap;

    public abstract PublicLoadLayout getPublicLoadLayout();

    public abstract void onInitView();

    public abstract void onUpdateAdapter();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = getActivity();
        this.mContext = getActivity().getApplicationContext();
        this.mDownloadPage = (IDownloadPage) getActivity();
        this.mDownloadConfig = this.mDownloadPage.getDownloadPageConfig();
        this.mVideosMap = this.mDownloadPage.getVideoMap();
    }

    public void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume isOnPause : " + this.isOnPause);
        try {
            if (this.isOnPause) {
                this.isOnPause = false;
                onUpdateAdapter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
        this.isOnPause = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void requestVideoTask() {
        getPublicLoadLayout().loading(false);
        this.mDownloadPage.requestVideoTask(this.mContext, this.mDownloadConfig.aid, this.mDownloadPage.getCurPage(), new 1(this));
    }

    private void initData() {
        if (this.mVideosMap.get(Integer.valueOf(this.mDownloadPage.getCurPage())) == null) {
            getPublicLoadLayout().setRefreshData(new 2(this));
            requestVideoTask();
            return;
        }
        onInitView();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void downloadItemClick(Context context, VideoBean video, View view, int position, Runnable addSuccessRun) {
        if (!LeBoxNetworkManager.getInstance().isLeboxConnected()) {
            Util.showToast(view.getContext(), R.string.lebox_disconnect);
        } else if (!video.canPlay()) {
            Util.showToast(view.getContext(), R.string.download_copy_right);
        } else if (video.needJump()) {
            Util.showToast(view.getContext(), R.string.download_jump_player);
        } else if (video.needPay()) {
            Util.showToast(view.getContext(), R.string.download_copy_right);
        } else if (HttpRequesetManager.getInstance().isHasDownload(video.vid)) {
            Util.showToast(view.getContext(), R.string.toast_added_download);
        } else {
            String stream = this.mDownloadPage.getVideoStreamHandler().getCurrentStreamString();
            String tag = "";
            try {
                tag = HttpRequesetManager.getAddTaskParamTag(video.nameCn, "", "", "", String.valueOf(video.isEnd));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Logger.d(TAG, "---downloadItemClick--vid=" + video.vid + "--pid=" + video.pid + "--stream=" + stream + "--tag=" + tag);
            if (!TextUtils.isEmpty(stream)) {
                HttpRequesetManager.getInstance().addTask(video.vid, this.mDownloadConfig.aid, stream, tag, new 3(this, addSuccessRun));
            }
        }
    }
}
