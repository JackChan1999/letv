package org.cybergarage.upnp.std.av.server.object.item;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.cybergarage.upnp.std.av.server.UPnP;
import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.container.ContainerNode;
import org.cybergarage.util.Debug;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;
import org.cybergarage.xml.Node;

public class ItemNode extends ContentNode {
    public static final String COLOR_DEPTH = "colorDepth";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String IMPORT_URI = "importUri";
    public static final String NAME = "item";
    public static final String PROTOCOL_INFO = "protocolInfo";
    public static final String RES = "res";
    public static final String RESOLUTION = "resolution";
    public static final String SIZE = "size";
    private ResourceNodeList resourceNodeList = new ResourceNodeList();

    public ItemNode() {
        setID(-1);
        setName(NAME);
        setStorageMedium(ContentNode.UNKNOWN);
        setWriteStatus(ContentNode.UNKNOWN);
    }

    public static final boolean isItemNode(Node node) {
        String name = node.getName();
        if (name == null) {
            return false;
        }
        return name.equals(NAME);
    }

    public void set(Node node) {
        int n;
        int nNode = node.getNNodes();
        for (n = 0; n < nNode; n++) {
            Node cnode = node.getNode(n);
            if (!(ContainerNode.isContainerNode(cnode) || isItemNode(cnode))) {
                if (ResourceNode.isResourceNode(cnode)) {
                    ResourceNode resNode = new ResourceNode();
                    resNode.set(cnode);
                    addResourceNode(resNode);
                } else {
                    setProperty(cnode.getName(), cnode.getValue());
                }
            }
        }
        int nAttr = node.getNAttributes();
        for (n = 0; n < nAttr; n++) {
            Attribute attr = node.getAttribute(n);
            setAttribute(attr.getName(), attr.getValue());
        }
    }

    public void addContentNode(ContentNode node) {
        addNode(node);
        node.setParentID(getID());
        node.setContentDirectory(getContentDirectory());
    }

    public boolean removeContentNode(ContentNode node) {
        return removeNode(node);
    }

    public void setDate(String value) {
        setProperty("dc:date", value);
    }

    public String getDate() {
        return getPropertyValue("dc:date");
    }

    public void setDate(long dateTime) {
        try {
            setDate(new SimpleDateFormat(DATE_FORMAT).format(new Date(dateTime)));
        } catch (Exception e) {
            Debug.warning(e);
        }
    }

    public long getDateTime() {
        long j = 0;
        String dateStr = getDate();
        if (dateStr != null && dateStr.length() >= 10) {
            try {
                j = new SimpleDateFormat(DATE_FORMAT).parse(dateStr).getTime();
            } catch (Exception e) {
            }
        }
        return j;
    }

    public void setCreator(String name) {
        setProperty("dc:creator", name);
    }

    public String getCreator() {
        return getPropertyValue("dc:creator");
    }

    public void setStorageMedium(String value) {
        setProperty(UPnP.STORAGE_MEDIUM, value);
    }

    public String getStorageMedium() {
        return getPropertyValue(UPnP.STORAGE_MEDIUM);
    }

    public void setStorageUsed(long value) {
        setProperty(UPnP.STORAGE_USED, value);
    }

    public long getStorageUsed() {
        return getPropertyLongValue(UPnP.STORAGE_USED);
    }

    public void setAlbumArtURI(String value) {
        setProperty(UPnP.ALBUMART_URI, value);
    }

    public String getAlbumArtURI() {
        return getPropertyValue(UPnP.ALBUMART_URI);
    }

    public boolean isMovieClass() {
        if (isUPnPClassStartWith("object.item.movie") || isUPnPClassStartWith("object.item.video")) {
            return true;
        }
        return false;
    }

    public boolean isVideoClass() {
        return isMovieClass();
    }

    public boolean isAudioClass() {
        if (isUPnPClassStartWith("object.item.audio") || isUPnPClassStartWith("object.item.music")) {
            return true;
        }
        return false;
    }

    public boolean isImageClass() {
        if (isUPnPClassStartWith("object.item.image") || isUPnPClassStartWith("object.item.photo")) {
            return true;
        }
        return false;
    }

    public void addResourceNode(ResourceNode resNode) {
        this.resourceNodeList.add(resNode);
    }

    public ResourceNodeList getResourceNodeList() {
        return this.resourceNodeList;
    }

    public int getNResourceNodeLists() {
        return this.resourceNodeList.size();
    }

    public ResourceNode getResourceNode(int n) {
        return this.resourceNodeList.getResourceNode(n);
    }

    public ResourceNode getFirstResource() {
        int nProperties = getNResourceNodeLists();
        for (int n = 0; n < nProperties; n++) {
            ResourceNode resNode = getResourceNode(n);
            if (!resNode.isThumbnail()) {
                return resNode;
            }
        }
        return null;
    }

    public ResourceNode getThumbnailResource() {
        int nProperties = getNResourceNodeLists();
        for (int n = 0; n < nProperties; n++) {
            ResourceNode resNode = getResourceNode(n);
            if (resNode.isThumbnail()) {
                return resNode;
            }
        }
        return null;
    }

    public ResourceNode getSmallImageResource() {
        int nProperties = getNResourceNodeLists();
        for (int n = 0; n < nProperties; n++) {
            ResourceNode resNode = getResourceNode(n);
            if (resNode.isSmallImage()) {
                return resNode;
            }
        }
        return null;
    }

    public void setResource(String url, String protocolInfo, AttributeList attrList) {
        setProperty("res", url);
        setPropertyAttribure("res", "protocolInfo", protocolInfo);
        int attrCnt = attrList.size();
        for (int n = 0; n < attrCnt; n++) {
            Attribute attr = attrList.getAttribute(n);
            setPropertyAttribure("res", attr.getName(), attr.getValue());
        }
    }

    public void setResource(String url, String protocolInfo) {
        setResource(url, protocolInfo, new AttributeList());
    }

    public String getResource() {
        return getPropertyValue("res");
    }

    public String getProtocolInfo() {
        return getPropertyAttribureValue("res", "protocolInfo");
    }

    public long getContentLength() {
        return 0;
    }

    public InputStream getContentInputStream() {
        return null;
    }

    public String getMimeType() {
        return "*/*";
    }
}
