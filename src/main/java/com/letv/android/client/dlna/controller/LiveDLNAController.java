package com.letv.android.client.dlna.controller;

import android.content.Context;
import android.view.View;
import com.letv.android.client.commonlib.messagemodel.DLNAToPlayerProtocol;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.LogInfo;
import org.cybergarage.upnp.Device;

public class LiveDLNAController extends DLNAController {
    private View mVideoLayout = this.mToPlayerProtocol.getPlayerRoot();

    public LiveDLNAController(Context context) {
        super(context);
        this.mContext = context;
        initProtocol();
    }

    protected void initProtocol() {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_DLNA_LIVE_PROTOCOL));
        if (LeResponseMessage.checkResponseMessageValidity(response, DLNAToPlayerProtocol.class)) {
            this.mToPlayerProtocol = (DLNAToPlayerProtocol) response.getData();
            return;
        }
        throw new NullPointerException("LiveDlnaProtocol is Null");
    }

    protected String syncGetPlayUrl(Device device) {
        String playUrl = this.mToPlayerProtocol.syncGetPlayUrl(device);
        LogInfo.log("dlna", "直播投屏播放地址 syncGetPlayUrl playUrl=" + playUrl);
        return playUrl;
    }

    protected void onStartPlay() {
        LogInfo.log("dlna", "LiveDLNAController onStartPlay dlna开始播放");
        if (this.mVideoLayout != null) {
            this.mVideoLayout.setVisibility(8);
        }
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onStartPlay();
        }
        this.mIsPlayingDlna = true;
    }

    protected void onStopPlay(boolean isActive) {
        LogInfo.log("dlna", "LiveDLNAController onStopPlay dlna停止播放 isActive=" + isActive);
        if (isActive) {
            this.mIsPlayingDlna = false;
            if (this.mVideoLayout != null) {
                this.mVideoLayout.setVisibility(0);
            }
            if (this.mToPlayerProtocol != null) {
                this.mToPlayerProtocol.onStopPlay(isActive, 0);
                return;
            }
            return;
        }
        onPause();
    }

    protected void onProcess(int progress) {
    }

    protected void onPause() {
        LogInfo.log("dlna", "LiveDLNAController onPause");
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onPause();
        }
    }

    protected void onStart() {
        LogInfo.log("dlna", "LiveDLNAController onStart");
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onStart();
        }
    }

    protected int getVideoDuration() {
        return 0;
    }

    public boolean isDlnaPlaying() {
        return this.mIsPlayingDlna;
    }
}
