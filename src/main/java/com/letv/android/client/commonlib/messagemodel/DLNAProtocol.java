package com.letv.android.client.commonlib.messagemodel;

public interface DLNAProtocol {
    boolean isPlayingDlna();

    void protocolClickPauseOrPlay();

    void protocolDestory();

    void protocolDisconnect();

    void protocolPlayNext();

    void protocolPlayOther();

    void protocolSearch();

    void protocolSeek(int i);

    void protocolStart(int i);

    void protocolStartTracking();

    void protocolStop(boolean z, boolean z2);

    void protocolStopTracking(int i);
}
