package org.cybergarage.upnp.std.av.server.object;

import java.util.Vector;

public class SortCapList extends Vector {
    public SortCap getSortCap(int n) {
        return (SortCap) get(n);
    }

    public SortCap getSortCap(String type) {
        if (type == null) {
            return null;
        }
        int nLists = size();
        for (int n = 0; n < nLists; n++) {
            SortCap scap = getSortCap(n);
            if (type.compareTo(scap.getType()) == 0) {
                return scap;
            }
        }
        return null;
    }
}
