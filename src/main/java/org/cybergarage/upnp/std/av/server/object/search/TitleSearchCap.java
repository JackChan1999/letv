package org.cybergarage.upnp.std.av.server.object.search;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SearchCap;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class TitleSearchCap implements SearchCap {
    public String getPropertyName() {
        return "dc:title";
    }

    public boolean compare(SearchCriteria searchCri, ContentNode conNode) {
        String searchCriTitle = searchCri.getValue();
        String conTitle = conNode.getTitle();
        if (searchCriTitle == null || conTitle == null) {
            return false;
        }
        int cmpRet = conTitle.compareTo(searchCriTitle);
        if (cmpRet == 0 && (searchCri.isEQ() || searchCri.isLE() || searchCri.isGE())) {
            return true;
        }
        if (cmpRet < 0 && searchCri.isLT()) {
            return true;
        }
        if (cmpRet > 0 && searchCri.isGT()) {
            return true;
        }
        if ((conTitle.indexOf(searchCriTitle) < 0 || !searchCri.isContains()) && !searchCri.isDoesNotContain()) {
            return false;
        }
        return true;
    }
}
