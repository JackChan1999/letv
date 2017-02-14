package org.cybergarage.upnp.std.av.player.action;

import org.cybergarage.xml.Node;

public class BrowseResult {
    private Node resultNode;

    public BrowseResult(Node node) {
        setResultNode(node);
    }

    public void setResultNode(Node node) {
        this.resultNode = node;
    }

    public Node getResultNode() {
        return this.resultNode;
    }

    public int getNContentNodes() {
        return this.resultNode.getNNodes();
    }

    public Node getContentNode(int n) {
        return this.resultNode.getNode(n);
    }
}
