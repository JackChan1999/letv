package com.letv.business.flow.album.listener;

import com.letv.core.bean.VideoBean;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;

public interface AlbumPlayFlowListener {
    void handleADBufferDone();

    void onAdsFinish(boolean z);

    void onClickShipAd();

    void play(VideoBean videoBean);

    void playLebox(LeboxVideoBean leboxVideoBean);

    void playNextLebox(LeboxVideoBean leboxVideoBean);

    void requestErr();

    void startPlayWith3g();
}
