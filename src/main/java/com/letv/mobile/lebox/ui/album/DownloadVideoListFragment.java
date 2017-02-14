package com.letv.mobile.lebox.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.view.PublicLoadLayout;
import com.letv.mobile.lebox.view.XListView;
import com.letv.mobile.lebox.view.XListView$IXListViewListener;
import com.letv.mobile.letvhttplib.bean.VideoListBean;

public class DownloadVideoListFragment extends BaseDownloadPageFragment implements IDownloadVideoFragment, XListView$IXListViewListener {
    private static final String TAG = DownloadVideoListFragment.class.getSimpleName();
    private DownloadVideosListAdapter listAdapter;
    private final OnItemClickListener listItemClickListenerForDownload = new 1(this);
    private VideoListBean mVideoListBean = new VideoListBean();
    private PublicLoadLayout mView;
    private XListView videosListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = PublicLoadLayout.createPage(this.mContext, R.layout.lebox_download_video_list_layout);
        super.onCreateView(inflater, container, savedInstanceState);
        return this.mView;
    }

    public void onInitView() {
        boolean z = true;
        this.mView.finish();
        this.videosListView = (XListView) this.mView.findViewById(R.id.detailplay_half_video_anthology_listview);
        this.videosListView.setXListViewListener(this);
        this.videosListView.setPullRefreshEnable(true);
        this.videosListView.setPullLoadEnable(true);
        this.videosListView.setEnableDragLoadMore(true);
        this.videosListView.setVisibility(0);
        if (this.mDownloadPage == null || this.mDownloadPage.getVideoMap().size() <= 0) {
            boolean z2;
            XListView xListView = this.videosListView;
            if (this.mDownloadConfig.pageNum > this.mDownloadPage.getCurPage()) {
                z2 = true;
            } else {
                z2 = false;
            }
            xListView.setPullLoadEnable(z2);
            XListView xListView2 = this.videosListView;
            if (this.mDownloadConfig.pageNum <= this.mDownloadPage.getCurPage()) {
                z = false;
            }
            xListView2.setEnableDragLoadMore(z);
            this.videosListView.setPullRefreshEnable(false);
        } else {
            this.videosListView.setPullLoadEnable(false);
            this.videosListView.setEnableDragLoadMore(false);
            this.videosListView.setPullRefreshEnable(false);
        }
        if (!this.mDownloadConfig.isVideoNormal) {
            this.videosListView.setPullLoadEnable(false);
            this.videosListView.setPullRefreshEnable(false);
            Logger.d("onInitView", " video is single ");
        }
        this.videosListView.stopLoadMore();
        this.videosListView.stopRefresh();
        this.mVideoListBean = (VideoListBean) this.mVideosMap.get(Integer.valueOf(this.mDownloadPage.getCurPage()));
        this.listAdapter = new DownloadVideosListAdapter(this.mContext, this.mDownloadPage.getVideoStreamHandler());
        this.listAdapter.setmOnClickListener(this.listItemClickListenerForDownload);
        this.listAdapter.setList(this.mVideoListBean);
        this.listAdapter.setCurId(this.mDownloadPage.getCurrentPlayVid());
        this.videosListView.setAdapter(this.listAdapter);
    }

    public void onUpdateAdapter() {
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void onRefresh() {
    }

    public void onLoadMore() {
        Logger.d(TAG, "onLoadMore>>>> pageNum : " + this.mDownloadConfig.pageNum + " getCurPage : " + this.mDownloadPage.getCurPage());
        if (this.mDownloadConfig.pageNum > this.mDownloadPage.getCurPage()) {
            int curPage = this.mDownloadPage.getCurPage() + 1;
        }
    }

    public PublicLoadLayout getPublicLoadLayout() {
        return this.mView;
    }

    public void notifyAdapter() {
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
