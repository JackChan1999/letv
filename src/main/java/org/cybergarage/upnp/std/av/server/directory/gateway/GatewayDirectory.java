package org.cybergarage.upnp.std.av.server.directory.gateway;

import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.std.av.controller.MediaController;
import org.cybergarage.upnp.std.av.player.MediaPlayer;
import org.cybergarage.upnp.std.av.server.Directory;
import org.cybergarage.upnp.std.av.server.MediaServer;
import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.container.ContainerNode;

public class GatewayDirectory extends Directory {
    private static final String NAME = "CyberMediaGate";
    private MediaPlayer mplayer;

    public GatewayDirectory(String name) {
        super(name);
        this.mplayer = new MediaPlayer();
        getMediaPlayer().start();
    }

    public GatewayDirectory() {
        this(NAME);
    }

    public MediaPlayer getMediaPlayer() {
        return this.mplayer;
    }

    private boolean updateMediaServerList() {
        MediaPlayer dmp = getMediaPlayer();
        if (dmp == null) {
            return false;
        }
        MediaController controller = dmp.getController();
        if (controller == null) {
            return false;
        }
        int n;
        DeviceList devList = controller.getDeviceList();
        int devCnt = devList.size();
        for (n = 0; n < devCnt; n++) {
            Device dev = devList.getDevice(n);
            if (dev.isDeviceType(MediaServer.DEVICE_TYPE)) {
                System.out.println("[" + n + "] " + dev.getFriendlyName() + ", " + dev.getLeaseTime() + ", " + dev.getElapsedTime());
            }
        }
        int nContents = getNContentNodes();
        ContentNode[] cnode = new ContentNode[nContents];
        for (n = 0; n < nContents; n++) {
            cnode[n] = getContentNode(n);
        }
        for (n = 0; n < nContents; n++) {
            String containerName = cnode[n].getName();
            if (!controller.hasDevice(containerName)) {
                ContainerNode mserverNode = new ContainerNode();
                mserverNode.setName(containerName);
                addContentNode(mserverNode);
            }
        }
        return false;
    }

    public boolean update() {
        return updateMediaServerList();
    }
}
