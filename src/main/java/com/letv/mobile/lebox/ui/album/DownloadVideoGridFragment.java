package com.letv.mobile.lebox.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.PublicLoadLayout;
import com.letv.mobile.letvhttplib.bean.VideoListBean;
import com.letv.mobile.letvhttplib.volley.listener.OnEntryResponse;
import com.letv.mobile.letvhttplib.volley.toolbox.SimpleResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DownloadVideoGridFragment extends BaseDownloadPageFragment {
    private static final String TAG = DownloadVideoGridFragment.class.getSimpleName();
    private int clickEpisodePage;
    private DetailVideosEpisodeAdapter episodeAdapter;
    private final OnItemClickListener episodeClickListener = new 3(this);
    private GridView episodeGallery;
    private View episodeScroll;
    private final HashMap<Long, TaskVideoBean> mDownloadMap = new HashMap();
    private DownloadVideosGridAdapter mDownloadVideoAdapter;
    private final OnItemClickListener mGridItemClickListenerForDownload = new 1(this);
    SimpleResponse<VideoListBean> mSimpleResponse = new 4(this);
    private PublicLoadLayout mView;
    private GridView videosGridView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = PublicLoadLayout.createPage(this.mContext, R.layout.lebox_download_grid_layout);
        this.mView.loading(false);
        initDownloadData();
        super.onCreateView(inflater, container, savedInstanceState);
        return this.mView;
    }

    public void onInitView() {
        this.mView.finish();
        this.episodeGallery = (GridView) this.mView.findViewById(R.id.detailplay_half_video_anthology_horigallery);
        this.episodeGallery.setVisibility(8);
        this.episodeScroll = this.mView.findViewById(R.id.detailplay_half_video_anthology_scroll);
        this.episodeScroll.setVisibility(8);
        this.videosGridView = (GridView) this.mView.findViewById(R.id.detailplay_half_video_anthology_gridview);
        this.mDownloadVideoAdapter = new DownloadVideosGridAdapter(this.mContext, this.mDownloadPage.getVideoStreamHandler(), this.mDownloadMap);
        this.videosGridView.setAdapter(this.mDownloadVideoAdapter);
        this.videosGridView.setVisibility(0);
        this.videosGridView.setOnItemClickListener(this.mGridItemClickListenerForDownload);
        createEpisode();
        this.mDownloadVideoAdapter.setList((VideoListBean) this.mDownloadPage.getVideoMap().get(Integer.valueOf(this.mDownloadPage.getCurPage())));
        this.mDownloadVideoAdapter.setCurId(this.mDownloadPage.getCurrentPlayVid());
        this.mDownloadVideoAdapter.notifyDataSetChanged();
    }

    public void onUpdateAdapter() {
        asynUpdateDownloadData();
    }

    private void asynUpdateDownloadData() {
        new 2(this).execute(new Void[0]);
    }

    private void updateEpisode() {
        if (this.episodeAdapter != null) {
            this.episodeAdapter.setCurPage(this.mDownloadPage.getCurPage());
            this.episodeAdapter.notifyDataSetChanged();
        }
    }

    private void createEpisode() {
        if (this.mDownloadConfig.total > 50) {
            int cl = this.mDownloadConfig.pageNum;
            this.episodeGallery.getLayoutParams().width = Util.zoomWidth(this.mDownloadConfig.pageNum * 70);
            this.episodeGallery.setStretchMode(2);
            this.episodeGallery.setNumColumns(cl);
            if (this.episodeAdapter == null) {
                this.episodeAdapter = new DetailVideosEpisodeAdapter(this.mContext);
                this.episodeGallery.setAdapter(this.episodeAdapter);
                this.episodeGallery.setVisibility(0);
                this.episodeScroll.setVisibility(0);
                this.episodeGallery.setOnItemClickListener(this.episodeClickListener);
            }
            this.episodeAdapter.setTotle(this.mDownloadConfig.total);
            this.episodeAdapter.setCurPage(this.mDownloadPage.getCurPage());
            this.episodeAdapter.setPageSize(50);
            this.episodeAdapter.notifyDataSetChanged();
        }
    }

    public boolean getVideoList(int page, OnEntryResponse<VideoListBean> onEntryResponse) {
        if (page != this.mDownloadPage.getCurPage()) {
            if (this.mVideosMap.get(Integer.valueOf(page)) != null) {
                this.mDownloadPage.setCurPage(page);
                return true;
            }
            this.mView.loading(false);
            this.mView.setRefreshData(new 5(this, page, onEntryResponse));
            this.mDownloadPage.requestVideoTask(this.mContext, this.mDownloadConfig.aid, page, onEntryResponse);
        }
        return false;
    }

    public void initDownloadData() {
        Logger.d(TAG, "updateDownloadData>>");
        this.mDownloadMap.clear();
        ArrayList<TaskVideoBean> arrayDownload = (ArrayList) HttpRequesetManager.getInstance().getAlbumFormCache(this.mDownloadConfig.aid);
        Logger.d(TAG, "----initDownloadData--arrayDownload=" + arrayDownload);
        if (arrayDownload != null) {
            Iterator it = arrayDownload.iterator();
            while (it.hasNext()) {
                TaskVideoBean videoBean = (TaskVideoBean) it.next();
                this.mDownloadMap.put(Long.valueOf(Util.getLong(videoBean.getVid(), 0)), videoBean);
            }
        }
    }

    public PublicLoadLayout getPublicLoadLayout() {
        return this.mView;
    }

    public void notifyAdapter() {
        if (this.mDownloadVideoAdapter != null) {
            this.mDownloadVideoAdapter.notifyDataSetChanged();
        }
    }
}
