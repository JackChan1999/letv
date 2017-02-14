package com.letv.android.client.dlna.inter;

import org.cybergarage.upnp.Device;

public interface DLNAListener {
    Device getDevice();

    void getProgress();

    void onTransportStateChange(String str, String str2);

    void pause();

    void play(Device device);

    void play(Device device, boolean z);

    void playNext();

    void rePlay(Device device);

    void seek(int i);

    void start();

    void startSearch(boolean z);

    void stop(boolean z, boolean z2);

    void stopProgressTimer();
}
