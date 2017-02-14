package org.cybergarage.upnp.std.av.server.object.search;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SearchCap;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class IdSearchCap implements SearchCap {
    public String getPropertyName() {
        return SearchCriteria.ID;
    }

    public boolean compare(SearchCriteria searchCri, ContentNode conNode) {
        String searchCriID = searchCri.getValue();
        String conID = conNode.getID();
        if (searchCriID == null || conID == null || !searchCri.isEQ() || searchCriID.compareTo(conID) != 0) {
            return false;
        }
        return true;
    }
}
