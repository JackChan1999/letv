package org.cybergarage.upnp.std.av.player.action;

import java.io.InputStream;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;

public class BrowseResultNode extends ItemNode {
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
