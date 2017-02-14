package org.cybergarage.upnp.std.av.server.object;

public interface SortCap {
    int compare(ContentNode contentNode, ContentNode contentNode2);

    String getType();
}
