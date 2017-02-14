package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.object.FormatObject;

public class GIFFormat extends ImageIOFormat {
    public GIFFormat(File file) {
        super(file);
    }

    public boolean equals(File file) {
        if (Header.getIDString(file, 3).startsWith("GIF")) {
            return true;
        }
        return false;
    }

    public FormatObject createObject(File file) {
        return new GIFFormat(file);
    }

    public String getMimeType() {
        return "image/gif";
    }
}
