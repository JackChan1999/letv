package org.cybergarage.upnp.std.av.server.object.item;

import java.util.Vector;

public class ItemNodeList extends Vector {
    public ItemNode getItemNode(int n) {
        return (ItemNode) get(n);
    }
}
