package com.letv.business.flow.live;

import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;

public interface LiveFragmentCallback {
    public static final int STATE_DATA_NULL = 4;
    public static final int STATE_DATTE_CHANGED = 11;
    public static final int STATE_DIRECTION_CHANGE = 6;
    public static final int STATE_FINISH = 1;
    public static final int STATE_FIRST_PLAYING = 8;
    public static final int STATE_NET_ERR = 3;
    public static final int STATE_NET_NULL = 2;
    public static final int STATE_NEXT = 7;
    public static final int STATE_OTHER = 5;
    public static final int STATE_REFRESH_DATA = 9;
    public static final int STATE_RE_POSITION = 10;
    public static final int STATE_RUNNING = 0;

    void changeLunboChannel(String str, String str2, String str3, String str4, String str5, String str6);

    void changePlay(LiveRemenBaseBean liveRemenBaseBean, boolean z, boolean z2, int i);

    void finishPlayer();

    String getChannelId();

    String getChannelName();

    String getCode();

    boolean getCollected();

    LetvBaseBean getCurrentLiveData();

    LetvBaseBean getData();

    int getFlowState();

    int getLaunchMode();

    String getUniqueId();

    boolean isPay();

    void loadPrograms(int i);

    void requestData(boolean z, boolean z2);

    void setCollected(boolean z);

    void setFloatControllerVisible(boolean z);

    void setOrientationSensorLock(boolean z);

    void updateCollectState();
}
