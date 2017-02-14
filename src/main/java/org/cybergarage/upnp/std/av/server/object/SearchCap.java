package org.cybergarage.upnp.std.av.server.object;

public interface SearchCap {
    boolean compare(SearchCriteria searchCriteria, ContentNode contentNode);

    String getPropertyName();
}
