package org.cybergarage.upnp.std.av.server.object.item.file;

import java.io.File;
import java.util.Vector;

public class FileItemNodeList extends Vector {
    public FileItemNode getFileItemNode(int n) {
        return (FileItemNode) get(n);
    }

    public FileItemNode getFileItemNode(File file) {
        int itemNodeCnt = size();
        for (int n = 0; n < itemNodeCnt; n++) {
            FileItemNode itemNode = getFileItemNode(n);
            if (itemNode.getFile() != null && itemNode.equals(file)) {
                return itemNode;
            }
        }
        return null;
    }
}
