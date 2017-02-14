package org.cybergarage.upnp.std.av.server.object.sort;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SortCap;

public class UPnPClassSortCap implements SortCap {
    public String getType() {
        return "upnp:class";
    }

    public int compare(ContentNode conNode1, ContentNode conNode2) {
        if (conNode1 == null || conNode2 == null) {
            return 0;
        }
        String upnpClass1 = conNode1.getUPnPClass();
        String upnpClass2 = conNode2.getUPnPClass();
        if (upnpClass1 == null || upnpClass2 == null) {
            return 0;
        }
        return upnpClass1.compareTo(upnpClass2);
    }
}
