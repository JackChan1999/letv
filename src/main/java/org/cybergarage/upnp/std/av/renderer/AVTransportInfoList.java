package org.cybergarage.upnp.std.av.renderer;

import java.util.Vector;

public class AVTransportInfoList extends Vector {
    public AVTransportInfo getAVTransportInfo(int n) {
        return (AVTransportInfo) get(n);
    }
}
