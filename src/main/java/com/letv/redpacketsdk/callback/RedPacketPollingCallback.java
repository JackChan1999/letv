package com.letv.redpacketsdk.callback;

import com.letv.redpacketsdk.bean.PollingResult;

public interface RedPacketPollingCallback {
    void onReceivePollingResult(PollingResult pollingResult);
}
