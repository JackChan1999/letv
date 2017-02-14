package org.cybergarage.upnp.std.av.server;

public class ConnectionInfo {
    public static final String INPUT = "Input";
    public static final String OK = "OK";
    public static final String OUTPUT = "Output";
    public static final String UNKNOWN = "Unknown";
    private String direction;
    private int id;
    private int peerConnectionID;
    private String peerConnectionManager;
    private String protocolInfo;
    private int rcsId;
    private String status;
    private int transId;

    public ConnectionInfo(int id) {
        setID(id);
        setRcsID(-1);
        setAVTransportID(-1);
        setProtocolInfo("");
        setPeerConnectionManager("");
        setPeerConnectionID(-1);
        setDirection("Output");
        setStatus("Unknown");
    }

    public void setID(int value) {
        this.id = value;
    }

    public int getID() {
        return this.id;
    }

    public void setRcsID(int value) {
        this.rcsId = value;
    }

    public int getRcsID() {
        return this.rcsId;
    }

    public void setAVTransportID(int value) {
        this.transId = value;
    }

    public int getAVTransportID() {
        return this.transId;
    }

    public void setProtocolInfo(String value) {
        this.protocolInfo = value;
    }

    public String getProtocolInfo() {
        return this.protocolInfo;
    }

    public void setPeerConnectionManager(String value) {
        this.peerConnectionManager = value;
    }

    public String getPeerConnectionManager() {
        return this.peerConnectionManager;
    }

    public void setPeerConnectionID(int value) {
        this.peerConnectionID = value;
    }

    public int getPeerConnectionID() {
        return this.peerConnectionID;
    }

    public void setDirection(String value) {
        this.direction = value;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getStatus() {
        return this.status;
    }
}
