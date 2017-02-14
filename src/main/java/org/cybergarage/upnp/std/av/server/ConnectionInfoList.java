package org.cybergarage.upnp.std.av.server;

import java.util.Vector;

public class ConnectionInfoList extends Vector {
    public ConnectionInfo getConnectionInfo(int n) {
        return (ConnectionInfo) get(n);
    }
}
