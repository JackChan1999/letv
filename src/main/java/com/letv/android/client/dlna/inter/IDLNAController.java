package com.letv.android.client.dlna.inter;

import org.cybergarage.upnp.Device;

public interface IDLNAController {
    int getMaxVolumeValue(Device device);

    String getMediaDuration(Device device);

    int getMinVolumeValue(Device device);

    String getMute(Device device);

    String[] getPositionInfo(Device device);

    String getTransportState(Device device);

    int getVoice(Device device);

    boolean goon(Device device, String str);

    boolean pause(Device device);

    boolean play(Device device, String str);

    boolean seek(Device device, String str);

    boolean setMute(Device device, String str);

    boolean setVoice(Device device, int i);

    boolean stop(Device device);
}
