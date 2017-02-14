package org.cybergarage.upnp.std.av.server.object.format;

public class ID3Frame {
    public static final String TIT1 = "TIT1";
    public static final String TIT2 = "TIT2";
    public static final String TIT3 = "TIT3";
    public static final String TPE1 = "TPE1";
    public static final String TPE2 = "TPE2";
    public static final String TPE3 = "TPE3";
    public static final String TPE4 = "TPE4";
    private byte[] data;
    private int flag;
    private String id;
    private int size;

    public ID3Frame() {
        setID("");
        setFlag(0);
        setSize(0);
    }

    public void setID(String val) {
        this.id = val;
    }

    public String getID() {
        return this.id;
    }

    public void setFlag(int val) {
        this.flag = val;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setSize(int val) {
        this.size = val;
    }

    public int getSize() {
        return this.size;
    }

    public void setData(byte[] val) {
        this.data = val;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getStringData() {
        return new String(this.data, 1, getSize() - 1);
    }
}
