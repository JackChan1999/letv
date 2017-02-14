package org.cybergarage.upnp.std.av.server.object.container;

import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.Node;

public class ContainerNode extends ContentNode {
    public static final String CHILD_COUNT = "childCount";
    public static final String NAME = "container";
    public static final String OBJECT_CONTAINER = "object.container";
    public static final String SEARCHABLE = "searchable";

    public ContainerNode() {
        setID(-1);
        setName("container");
        setSearchable(0);
        setChildCount(0);
        setUPnPClass("object.container");
        setWriteStatus(ContentNode.UNKNOWN);
    }

    public static final boolean isContainerNode(Node node) {
        String name = node.getName();
        if (name == null) {
            return false;
        }
        return name.equals("container");
    }

    public void set(Node node) {
        int n;
        int nNode = node.getNNodes();
        for (n = 0; n < nNode; n++) {
            Node cnode = node.getNode(n);
            if (!(isContainerNode(cnode) || ItemNode.isItemNode(cnode))) {
                setProperty(cnode.getName(), cnode.getValue());
            }
        }
        int nAttr = node.getNAttributes();
        for (n = 0; n < nAttr; n++) {
            Attribute attr = node.getAttribute(n);
            setAttribute(attr.getName(), attr.getValue());
        }
    }

    public boolean hasContentNodes() {
        return hasNodes();
    }

    public int getNContentNodes() {
        return getNNodes();
    }

    public ContentNode getContentNode(int index) {
        return (ContentNode) getNode(index);
    }

    public ContentNode getContentNode(String name) {
        return (ContentNode) getNode(name);
    }

    public void removeAllContentNodes() {
        removeAllNodes();
    }

    public void addContentNode(ContentNode node) {
        addNode(node);
        node.setParentID(getID());
        setChildCount(getNContentNodes());
        node.setContentDirectory(getContentDirectory());
    }

    public boolean removeContentNode(ContentNode node) {
        boolean ret = removeNode(node);
        setChildCount(getNContentNodes());
        return ret;
    }

    public ContentNode findContentNodeByID(String id) {
        if (id == null) {
            return null;
        }
        if (id.equals(getID())) {
            return this;
        }
        int nodeCnt = getNContentNodes();
        for (int n = 0; n < nodeCnt; n++) {
            ContentNode cnode = getContentNode(n);
            if (cnode.isContainerNode()) {
                ContentNode fnode = ((ContainerNode) cnode).findContentNodeByID(id);
                if (fnode != null) {
                    return fnode;
                }
            }
        }
        return null;
    }

    public void setChildCount(int id) {
        setAttribute(CHILD_COUNT, id);
    }

    public int getChildCount() {
        return getAttributeIntegerValue(CHILD_COUNT);
    }

    public void setSearchable(int value) {
        setAttribute("searchable", value);
    }

    public int getSearchable() {
        return getAttributeIntegerValue("searchable");
    }
}
