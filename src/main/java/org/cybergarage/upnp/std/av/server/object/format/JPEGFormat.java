package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.object.FormatObject;

public class JPEGFormat extends ImageIOFormat {
    public JPEGFormat(File file) {
        super(file);
    }

    public boolean equals(File file) {
        byte[] headerID = Header.getID(file, 2);
        int header2 = headerID[1] & 255;
        if ((headerID[0] & 255) == 255 && header2 == 216) {
            return true;
        }
        return false;
    }

    public FormatObject createObject(File file) {
        return new JPEGFormat(file);
    }

    public String getMimeType() {
        return "image/jpeg";
    }
}
