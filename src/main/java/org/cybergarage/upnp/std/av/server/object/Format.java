package org.cybergarage.upnp.std.av.server.object;

import java.io.File;

public interface Format {
    FormatObject createObject(File file);

    boolean equals(File file);

    String getMediaClass();

    String getMimeType();
}
