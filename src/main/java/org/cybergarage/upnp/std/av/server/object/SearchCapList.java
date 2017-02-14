package org.cybergarage.upnp.std.av.server.object;

import java.util.Vector;

public class SearchCapList extends Vector {
    public SearchCap getSearchCap(int n) {
        return (SearchCap) get(n);
    }

    public SearchCap getSearchCap(String propName) {
        if (propName == null) {
            return null;
        }
        int nLists = size();
        for (int n = 0; n < nLists; n++) {
            SearchCap scap = getSearchCap(n);
            if (propName.compareTo(scap.getPropertyName()) == 0) {
                return scap;
            }
        }
        return null;
    }
}
