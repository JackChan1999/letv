package org.cybergarage.upnp.std.av.server.object;

import java.util.Vector;

public class SortCriterionList extends Vector {
    public String getSortCriterion(int n) {
        return (String) get(n);
    }
}
