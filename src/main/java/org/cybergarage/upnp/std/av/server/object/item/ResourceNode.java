package org.cybergarage.upnp.std.av.server.object.item;

import com.letv.core.constant.LiveRoomConstant;
import com.letv.pp.utils.NetworkUtils;
import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.Node;

public class ResourceNode extends ContentNode {
    public static final String COLOR_DEPTH = "colorDepth";
    public static final String IMPORT_URI = "importUri";
    public static final String NAME = "res";
    public static final String PROTOCOL_INFO = "protocolInfo";
    public static final String RESOLUTION = "resolution";
    public static final String SIZE = "size";

    public static final boolean isResourceNode(Node node) {
        String name = node.getName();
        if (name == null) {
            return false;
        }
        return name.equals("res");
    }

    public void set(Node node) {
        setValue(node.getValue());
        int nAttr = node.getNAttributes();
        for (int n = 0; n < nAttr; n++) {
            Attribute attr = node.getAttribute(n);
            setAttribute(attr.getName(), attr.getValue());
        }
    }

    public String getURL() {
        return getValue();
    }

    public String getProtocolInfo() {
        return getAttributeValue("protocolInfo");
    }

    public String getProtocolInfoAtIndex(int anIndex) {
        String protocolInfo = getProtocolInfo();
        if (protocolInfo == null) {
            return "";
        }
        String[] protocols = protocolInfo.split(NetworkUtils.DELIMITER_COLON);
        if (protocols == null || protocols.length <= anIndex) {
            return "";
        }
        return protocols[anIndex];
    }

    public String getProtocol() {
        return getProtocolInfoAtIndex(0);
    }

    public String getNetwork() {
        return getProtocolInfoAtIndex(1);
    }

    public String getContentFormat() {
        return getProtocolInfoAtIndex(2);
    }

    public String getAdditionalInfo() {
        return getProtocolInfoAtIndex(3);
    }

    public String getAdditionalInfoForKey(String aKey) {
        if (aKey == null) {
            return "";
        }
        String fullAddInfo = getAdditionalInfo();
        if (fullAddInfo == null) {
            return "";
        }
        String[] addInfos = fullAddInfo.split(";");
        if (addInfos == null || addInfos.length <= 0) {
            return "";
        }
        for (String addInfo : addInfos) {
            if (addInfo.startsWith(aKey)) {
                String[] tokens = addInfo.split(SearchCriteria.EQ);
                if (tokens == null || tokens.length < 2) {
                    return "";
                }
                return tokens[1];
            }
        }
        return "";
    }

    public String getDlnaOrgPn() {
        return getAdditionalInfoForKey("DLNA.ORG_PN");
    }

    public String getDlnaOrgOp() {
        return getAdditionalInfoForKey("DLNA.ORG_OP");
    }

    public String getDlnaOrgFlags() {
        return getAdditionalInfoForKey("DLNA.ORG_FLAGS");
    }

    public boolean isThumbnail() {
        String dlnaOrgPn = getDlnaOrgPn();
        if (dlnaOrgPn != null && dlnaOrgPn.endsWith("_TN")) {
            return true;
        }
        return false;
    }

    public boolean isSmallImage() {
        String dlnaOrgPn = getDlnaOrgPn();
        if (dlnaOrgPn != null && dlnaOrgPn.endsWith("_SM")) {
            return true;
        }
        return false;
    }

    public boolean isMediumImage() {
        String dlnaOrgPn = getDlnaOrgPn();
        if (dlnaOrgPn != null && dlnaOrgPn.endsWith("_MED")) {
            return true;
        }
        return false;
    }

    public boolean isLargeImage() {
        String dlnaOrgPn = getDlnaOrgPn();
        if (dlnaOrgPn != null && dlnaOrgPn.endsWith("_LRG")) {
            return true;
        }
        return false;
    }

    public boolean isImage() {
        String mimeType = getContentFormat();
        if (mimeType == null) {
            return false;
        }
        return mimeType.startsWith("image");
    }

    public boolean isMovie() {
        String mimeType = getContentFormat();
        if (mimeType == null) {
            return false;
        }
        if (mimeType.startsWith(LiveRoomConstant.CHANNEL_TYPE_SUBTYPE_MOVIE)) {
            return true;
        }
        return mimeType.startsWith("video");
    }

    public boolean isVideo() {
        return isMovie();
    }

    public boolean isAudio() {
        String mimeType = getContentFormat();
        if (mimeType == null) {
            return false;
        }
        return mimeType.startsWith("audio");
    }
}
