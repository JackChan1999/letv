package com.letv.mobile.lebox.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.view.PublicLoadLayout;
import com.letv.mobile.lebox.view.VarietyShowExpandableListView;
import com.letv.mobile.lebox.view.VarietyShowExpandableListView.IVarietyShowExpandableList;
import com.letv.mobile.lebox.view.VarietyShowExpandableListView.RequestVarietyShow;

public class DownloadVideoExpandFragment extends BaseDownloadPageFragment implements IDownloadVideoFragment {
    private static final String TAG = DownloadVideoExpandFragment.class.getSimpleName();
    private String aid;
    private String episode;
    private boolean isPositive;
    IVarietyShowExpandableList mVarietyExpandableList;
    PublicLoadLayout mView;
    private String vid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readArguments();
        this.mView = PublicLoadLayout.createPage(this.mContext, R.layout.lebox_download_video_expand_layout);
        initVarietyShow();
        return this.mView;
    }

    private void readArguments() {
        Bundle bundle = getArguments();
        this.vid = bundle.getString("vid", "");
        this.aid = bundle.getString("aid", "");
        this.episode = bundle.getString("episode", "");
        this.isPositive = bundle.getBoolean("isPositive", false);
    }

    private void initVarietyShow() {
        this.mVarietyExpandableList = ((VarietyShowExpandableListView) this.mView.findViewById(R.id.variety_expandablelist)).getIVarietyShowExpandableList();
        RequestVarietyShow requestVariety = this.mVarietyExpandableList.getReuqestVariety();
        requestVariety.videoStreamHandler = this.mDownloadPage.getVideoStreamHandler();
        requestVariety.id = String.valueOf(this.aid);
        requestVariety.isPositive = this.isPositive;
        requestVariety.vid = String.valueOf(this.vid);
        requestVariety.episode = this.episode;
        this.mVarietyExpandableList.setVarietyListCallBack(new 1(this));
        Logger.d(TAG, " isPositive: >> " + this.isPositive + " aid : " + this.aid + " vid: " + this.vid);
        if (this.isPositive) {
            requestVariety.videoType = "0";
        } else {
            requestVariety.videoType = "1";
        }
        this.mVarietyExpandableList.showVarietyVideo();
    }

    public void onInitView() {
    }

    public void onUpdateAdapter() {
        if (this.mVarietyExpandableList != null) {
            this.mVarietyExpandableList.notifyDataSetChanged();
        }
    }

    public PublicLoadLayout getPublicLoadLayout() {
        return this.mView;
    }

    public void notifyAdapter() {
        if (this.mVarietyExpandableList != null) {
            this.mVarietyExpandableList.notifyDataSetChanged();
        }
    }
}
