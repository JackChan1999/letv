package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.UPnP;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatObject;
import org.cybergarage.util.Debug;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;

public class MPEGFormat implements Format, FormatObject {
    private File mpegFile;

    public MPEGFormat(File file) {
        this.mpegFile = file;
    }

    public boolean equals(File file) {
        String ext = Header.getSuffix(file);
        if (ext == null) {
            return false;
        }
        if (ext.startsWith("mpeg") || ext.startsWith("mpg")) {
            return true;
        }
        return false;
    }

    public FormatObject createObject(File file) {
        return new MPEGFormat(file);
    }

    public String getMimeType() {
        return "video/mpeg";
    }

    public String getMediaClass() {
        return UPnP.OBJECT_ITEM_VIDEOITEM_MOVIE;
    }

    public AttributeList getAttributeList() {
        AttributeList attrList = new AttributeList();
        try {
            attrList.add(new Attribute("size", Long.toString(this.mpegFile.length())));
        } catch (Exception e) {
            Debug.warning(e);
        }
        return attrList;
    }

    public String getTitle() {
        String fname = this.mpegFile.getName();
        int idx = fname.lastIndexOf(".");
        if (idx < 0) {
            return "";
        }
        return fname.substring(0, idx);
    }

    public String getCreator() {
        return "";
    }
}
