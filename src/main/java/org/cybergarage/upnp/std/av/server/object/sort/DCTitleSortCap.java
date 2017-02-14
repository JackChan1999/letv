package org.cybergarage.upnp.std.av.server.object.sort;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SortCap;

public class DCTitleSortCap implements SortCap {
    public String getType() {
        return "dc:title";
    }

    public int compare(ContentNode conNode1, ContentNode conNode2) {
        if (conNode1 == null || conNode2 == null) {
            return 0;
        }
        String title1 = conNode1.getTitle();
        String title2 = conNode2.getTitle();
        if (title1 == null || title2 == null) {
            return 0;
        }
        return title1.compareTo(title2);
    }
}
