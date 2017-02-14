package org.cybergarage.upnp.std.av.server;

import org.cybergarage.upnp.std.av.server.object.container.ContainerNode;

public abstract class Directory extends ContainerNode {
    public abstract boolean update();

    public Directory(ContentDirectory cdir, String name) {
        setContentDirectory(cdir);
        setFriendlyName(name);
    }

    public Directory(String name) {
        this(null, name);
    }

    public void setFriendlyName(String name) {
        setTitle(name);
    }

    public String getFriendlyName() {
        return getTitle();
    }

    public void updateContentList() {
        if (update()) {
            setChildCount(getNContentNodes());
            getContentDirectory().updateSystemUpdateID();
        }
    }
}
