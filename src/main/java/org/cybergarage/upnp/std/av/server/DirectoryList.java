package org.cybergarage.upnp.std.av.server;

import java.util.Vector;

public class DirectoryList extends Vector {
    public Directory getDirectory(int n) {
        return (Directory) get(n);
    }

    public Directory getDirectory(String name) {
        int dirCnt = size();
        for (int n = 0; n < dirCnt; n++) {
            Directory dir = getDirectory(n);
            String dirName = dir.getFriendlyName();
            if (dirName != null && dirName.equals(name)) {
                return dir;
            }
        }
        return null;
    }

    public void update() {
        int dirCnt = size();
        for (int n = 0; n < dirCnt; n++) {
            getDirectory(n).updateContentList();
        }
    }
}
