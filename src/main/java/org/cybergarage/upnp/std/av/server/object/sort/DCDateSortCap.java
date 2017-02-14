package org.cybergarage.upnp.std.av.server.object.sort;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SortCap;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;

public class DCDateSortCap implements SortCap {
    public String getType() {
        return "dc:date";
    }

    public int compare(ContentNode conNode1, ContentNode conNode2) {
        if (conNode1 == null || conNode2 == null || !(conNode1 instanceof ItemNode) || !(conNode2 instanceof ItemNode)) {
            return 0;
        }
        ItemNode itemNode2 = (ItemNode) conNode2;
        long itemTime1 = ((ItemNode) conNode1).getDateTime();
        long itemTime2 = itemNode2.getDateTime();
        if (itemTime1 != itemTime2) {
            return itemTime1 < itemTime2 ? -1 : 1;
        } else {
            return 0;
        }
    }
}
