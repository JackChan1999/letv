package com.letv.android.client.listener;

import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.LivePriceBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean;
import org.cybergarage.upnp.Device;

public interface LiveControllerWidgetCallback {
    void back();

    void book();

    boolean changeLockStatus();

    void changeLunboChannel(String str, String str2, String str3, String str4, String str5, String str6);

    void changeMultiBranch(String str);

    void changePlay(LiveRemenBaseBean liveRemenBaseBean, boolean z, boolean z2, int i);

    void chat();

    void clickPlayOrPause();

    void closeSensor();

    void favourite();

    void finishPlayer();

    void full();

    String getChannelId();

    String getChannelName();

    String getChannelNum();

    String getCode();

    boolean getCollected();

    CurrentProgram getCurProgram();

    String getGuestImgUrl();

    String getGuestName();

    String getHomeImgUrl();

    String getHomeName();

    int getLaunchMode();

    PlayLiveFlow getLiveFlow();

    LiveRemenBaseBean getLiveInfo();

    LivePriceBean getLivePrice();

    LiveStreamBean getLiveStreamBean();

    String getLiveType();

    String getPlayTime();

    String getProgramName();

    String getShareLiveUrl();

    String getShareProgramName();

    int getTicketCount();

    String getUniqueId();

    void half();

    boolean hasVote();

    void hideBookView();

    boolean isAdShowing();

    boolean isDanmaku();

    boolean isDlnaPlaying();

    boolean isLock();

    boolean isPanorama();

    boolean isPlaying();

    void notifyHalfLivePlayFragment(int i);

    void onDlnaChange(boolean z);

    void openSensor();

    void pause();

    void play();

    void playHd(int i);

    void setChannelId(String str);

    void setChannelName(String str);

    void setChannelNum(String str);

    void setCode(String str);

    void setCollected(boolean z);

    void setPlayBtnStatus(boolean z);

    int setSoundVolume(int i, boolean z);

    void setmProgramName(String str);

    void setmSignal(String str);

    void share();

    void star();

    void star3G();

    void startDlna();

    void startLongWatchCountDown();

    String syncGetPlayUrl(Device device);

    void toPip();
}
