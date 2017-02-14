package org.cybergarage.upnp.std.av.server.object;

import org.cybergarage.xml.Node;

public class DIDLLiteNode extends Node {
    public DIDLLiteNode() {
        setName(DIDLLite.NAME);
        setAttribute(DIDLLite.XMLNS, DIDLLite.XMLNS_URL);
        setAttribute(DIDLLite.XMLNS_DC, DIDLLite.XMLNS_DC_URL);
        setAttribute(DIDLLite.XMLNS_UPNP, DIDLLite.XMLNS_UPNP_URL);
    }

    public void addContentNode(ContentNode node) {
        addNode(node);
    }

    public boolean removeContentNode(ContentNode node) {
        return removeNode(node);
    }
}
