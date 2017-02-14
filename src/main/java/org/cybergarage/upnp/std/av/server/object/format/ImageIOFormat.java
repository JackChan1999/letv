package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.UPnP;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatObject;
import org.cybergarage.xml.AttributeList;

public abstract class ImageIOFormat extends Header implements Format, FormatObject {
    private File imgFile;

    public abstract FormatObject createObject(File file);

    public abstract boolean equals(File file);

    public abstract String getMimeType();

    public ImageIOFormat() {
        this.imgFile = null;
    }

    public ImageIOFormat(File file) {
        this.imgFile = file;
    }

    public String getMediaClass() {
        return UPnP.OBJECT_ITEM_IMAGEITEM_PHOTO;
    }

    public AttributeList getAttributeList() {
        return new AttributeList();
    }

    public String getTitle() {
        String fname = this.imgFile.getName();
        int idx = fname.lastIndexOf(".");
        if (idx < 0) {
            return "";
        }
        return fname.substring(0, idx);
    }

    public String getCreator() {
        return "";
    }

    public void print() {
    }
}
