package com.letv.android.client.listener;

import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.channel.ChannelFilterTypes;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.TopList;

public interface ChannelDetailCallback {
    void dataError(boolean z);

    void netError(boolean z);

    void onChannelSuccess(ChannelHomeBean channelHomeBean, boolean z, boolean z2, boolean z3);

    void onLiveListSuccess(LiveRemenListBean liveRemenListBean);

    void onSiftListSuccess(ChannelFilterTypes channelFilterTypes);

    void onTopSuccess(TopList topList);
}
