package org.cybergarage.upnp.std.av.server.object.format;

import java.io.File;
import org.cybergarage.upnp.std.av.server.object.FormatObject;

public class PNGFormat extends ImageIOFormat {
    public PNGFormat(File file) {
        super(file);
    }

    public boolean equals(File file) {
        if (Header.getIDString(file, 1, 3).startsWith("PNG")) {
            return true;
        }
        return false;
    }

    public FormatObject createObject(File file) {
        return new PNGFormat(file);
    }

    public String getMimeType() {
        return "image/png";
    }
}
