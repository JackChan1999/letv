package org.cybergarage.upnp.std.av.renderer;

public class AVTransportInfo {
    private int instanceID;
    private String uri;
    private String uriMetaData;

    public AVTransportInfo() {
        setInstanceID(0);
        setURI("");
        setURIMetaData("");
    }

    public int getInstanceID() {
        return this.instanceID;
    }

    public void setInstanceID(int instanceID) {
        this.instanceID = instanceID;
    }

    public String getURI() {
        return this.uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public String getURIMetaData() {
        return this.uriMetaData;
    }

    public void setURIMetaData(String uriMetaData) {
        this.uriMetaData = uriMetaData;
    }
}
