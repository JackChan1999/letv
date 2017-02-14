package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatObject;
import org.cybergarage.xml.AttributeList;

public class DefaultFormat implements Format, FormatObject {
    public boolean equals(File file) {
        return true;
    }

    public FormatObject createObject(File file) {
        return new DefaultFormat();
    }

    public String getMimeType() {
        return "*/*";
    }

    public String getMediaClass() {
        return "object.item";
    }

    public AttributeList getAttributeList() {
        return new AttributeList();
    }

    public String getTitle() {
        return "";
    }

    public String getCreator() {
        return "";
    }
}
