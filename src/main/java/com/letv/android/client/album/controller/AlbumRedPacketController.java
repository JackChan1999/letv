package com.letv.android.client.album.controller;

import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.core.utils.LetvBaseObservable;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.redpacketsdk.ui.RedPacketUI;

public class AlbumRedPacketController extends LetvBaseObservable {
    public static final int REDPAKET_DIALOG_DISMISS = 1;
    public static final int REDPAKET_DIALOG_SHOW = 2;
    private boolean isPausePrevious;
    private AlbumPlayActivity mActivity;
    private boolean mChangedLock = false;

    public AlbumRedPacketController(AlbumPlayActivity activity) {
        this.mActivity = activity;
    }

    public void initRedPacketView() {
        if (getRedPacketEntry() != null) {
            getRedPacketEntry().setDialogDisplayCallback(new 1(this));
            if (this.mActivity.getFlow() != null) {
                this.mActivity.getFlow().setVideoChangeListener(new 2(this));
            }
        }
    }

    public RedPacketUI getRedPacketEntry() {
        return this.mActivity.getBaseRedPacket();
    }

    public void onResume() {
        if (getRedPacketEntry() != null && getRedPacketEntry().getIsOpenDialog() && !this.mActivity.mIsPlayingDlna) {
            this.mActivity.getController().pause(false);
        }
    }

    private RedPacketFrom getRedPacketCurrentType() {
        RedPacketFrom redPackFrom = new RedPacketFrom();
        redPackFrom.from = 3;
        redPackFrom.pageid = PageIdConstant.halpPlayPage;
        if (!(this.mActivity.getFlow() == null || this.mActivity.getFlow().mCurrentPlayingVideo == null)) {
            redPackFrom.pid = this.mActivity.getFlow().mCurrentPlayingVideo.pid + "";
            redPackFrom.cid = this.mActivity.getFlow().mCurrentPlayingVideo.cid + "";
            redPackFrom.zid = this.mActivity.getFlow().mCurrentPlayingVideo.zid;
            LogInfo.log("RedPacket", "RedPacket+albumPlayActivity: pid=" + redPackFrom.pid + ";cid=" + redPackFrom.cid + ";zid=" + redPackFrom.zid);
        }
        return redPackFrom;
    }

    public void changeRedPacketLocation(boolean mIsLandspace) {
        this.mActivity.setRedPacketEntryLocation(mIsLandspace);
    }
}
