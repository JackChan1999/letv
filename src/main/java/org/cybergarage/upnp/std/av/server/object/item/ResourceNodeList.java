package org.cybergarage.upnp.std.av.server.object.item;

import java.util.Vector;

public class ResourceNodeList extends Vector {
    public ResourceNode getResourceNode(int n) {
        return (ResourceNode) get(n);
    }
}
