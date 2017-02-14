package com.letv.android.client.dlna.controller;

import android.content.Context;
import android.view.View;
import com.letv.android.client.commonlib.messagemodel.DLNAToPlayerProtocol;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import org.cybergarage.upnp.Device;

public class AlbumDLNAController extends DLNAController {
    private View mPlayerRoot;

    public AlbumDLNAController(Context context) {
        super(context);
        this.mContext = context;
        this.mIsAlbum = true;
        initProtocol();
        this.mPlayerRoot = this.mToPlayerProtocol.getPlayerRoot();
    }

    private void initProtocol() {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_DLNA_ALBUM_PROTOCOL));
        if (LeResponseMessage.checkResponseMessageValidity(response, DLNAToPlayerProtocol.class)) {
            this.mToPlayerProtocol = (DLNAToPlayerProtocol) response.getData();
            return;
        }
        throw new NullPointerException("AlbumDlnaProtocol is Null");
    }

    protected String syncGetPlayUrl(Device device) {
        if (this.mToPlayerProtocol != null) {
            return this.mToPlayerProtocol.syncGetPlayUrl(device);
        }
        return "";
    }

    protected void onStartPlay() {
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onStartPlay();
            this.mIsPlayingNext = false;
            this.mPlayerRoot.setVisibility(8);
            if (this.mSeek != 0) {
                seek(this.mSeek);
            } else if (!(this.mToPlayerProtocol.getCurrPosition() == 0 || this.mIsRetry)) {
                seek((int) (this.mToPlayerProtocol.getCurrPosition() / 1000));
            }
            this.mIsRetry = false;
        }
    }

    protected void onStopPlay(boolean isActive) {
        if (isActive && this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onStopPlay(isActive, this.mPosition);
            this.mIsPlayingNext = false;
            this.mPlayerRoot.setVisibility(0);
        }
    }

    protected void onProcess(int progress) {
        if (shouldPlayNext(progress)) {
            playNext();
        } else if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onProcess(progress);
        }
    }

    protected void onPause() {
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onPause();
        }
    }

    protected void onStart() {
        getProgress();
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.onStart();
        }
    }

    public void playNext() {
        super.playNext();
        if (this.mToPlayerProtocol != null) {
            this.mToPlayerProtocol.playNext();
        }
        this.mIsPlayingNext = true;
    }

    protected int getVideoDuration() {
        if (this.mToPlayerProtocol != null) {
            return this.mToPlayerProtocol.getVideoDuration();
        }
        return 0;
    }

    private boolean shouldPlayNext(int progress) {
        if (this.mIsPlayingNext || this.mToPlayerProtocol == null) {
            return false;
        }
        return this.mToPlayerProtocol.shouldPlayNext(progress);
    }
}
