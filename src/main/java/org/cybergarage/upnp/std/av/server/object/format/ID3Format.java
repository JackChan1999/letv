package org.cybergarage.upnp.std.av.server.object.format;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatObject;
import org.cybergarage.util.Debug;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;

public class ID3Format extends Header implements Format, FormatObject {
    public static final int FRAME_HEADER_SIZE = 10;
    public static final String HEADER_ID = "ID3";
    public static final int HEADER_SIZE = 10;
    private byte[] extHeader;
    private byte[] frameHeader;
    private ID3FrameList frameList;
    private byte[] header;
    private File mp3File;

    public ID3Format() {
        this.header = new byte[10];
        this.extHeader = new byte[4];
        this.frameHeader = new byte[10];
        this.frameList = new ID3FrameList();
        this.mp3File = null;
    }

    public ID3Format(File file) {
        this.header = new byte[10];
        this.extHeader = new byte[4];
        this.frameHeader = new byte[10];
        this.frameList = new ID3FrameList();
        this.mp3File = file;
        loadHeader(file);
    }

    public boolean loadHeader(InputStream inputStream) {
        try {
            int n;
            DataInputStream dataIn = new DataInputStream(inputStream);
            for (n = 0; n < 10; n++) {
                this.header[n] = dataIn.readByte();
            }
            if (hasExtendedHeader()) {
                for (n = 0; n < 4; n++) {
                    this.header[n] = dataIn.readByte();
                }
                int extHeaderSize = getExtHeaderSize();
                for (n = 0; n < extHeaderSize - 4; n++) {
                    dataIn.readByte();
                }
            }
            this.frameList.clear();
            int frameDataSize = getHeaderSize() - 10;
            if (hasExtendedHeader()) {
                frameDataSize -= getExtHeaderSize();
            }
            int frameDataCnt = 0;
            while (frameDataCnt < frameDataSize) {
                for (n = 0; n < 10; n++) {
                    this.frameHeader[n] = dataIn.readByte();
                }
                String frameID = getFrameID(this.frameHeader);
                int frameSize = getFrameSize(this.frameHeader);
                int frameFlag = getFrameFlag(this.frameHeader);
                byte[] frameData = new byte[frameSize];
                for (int i = 0; i < frameSize; i++) {
                    frameData[i] = dataIn.readByte();
                }
                ID3Frame frame = new ID3Frame();
                frame.setID(frameID);
                frame.setSize(frameSize);
                frame.setFlag(frameFlag);
                frame.setData(frameData);
                this.frameList.add(frame);
                frameDataCnt += frameSize + 10;
            }
            dataIn.close();
        } catch (EOFException e) {
        } catch (Exception e2) {
            Debug.warning(e2);
            return false;
        }
        return true;
    }

    public boolean loadHeader(File file) {
        try {
            return loadHeader(new FileInputStream(file));
        } catch (Exception e) {
            Debug.warning(e);
            return false;
        }
    }

    public boolean hasHeader() {
        String id = getHeaderID();
        if (id == null) {
            return false;
        }
        return id.equals(HEADER_ID);
    }

    public String getHeaderID() {
        return new String(this.header, 0, 3);
    }

    public int getHeaderSize() {
        int size = 0;
        for (int n = 0; n < 4; n++) {
            size += (this.header[9 - n] & 255) << n;
        }
        return size;
    }

    public int getFlag() {
        return this.header[5] & 255;
    }

    public boolean isUnsynchronisation() {
        return (getFlag() & 128) == 1;
    }

    public boolean hasExtendedHeader() {
        return (getFlag() & 64) == 1;
    }

    public boolean isExperimental() {
        return (getFlag() & 32) == 1;
    }

    public boolean hasFooter() {
        return (getFlag() & 16) == 1;
    }

    public int getExtHeaderSize() {
        int size = 0;
        for (int n = 0; n < 4; n++) {
            size += (this.extHeader[3 - n] & 255) << n;
        }
        return size;
    }

    private String getFrameID(byte[] frameHeader) {
        return new String(frameHeader, 0, 4);
    }

    private int getFrameSize(byte[] frameHeader) {
        int size = 0;
        for (int n = 0; n < 4; n++) {
            size += (frameHeader[7 - n] & 255) << n;
        }
        return size;
    }

    private int getFrameFlag(byte[] frameHeader) {
        return ((frameHeader[8] & 255) << 8) + (frameHeader[9] & 255);
    }

    public byte[] getFrameData(String name) {
        return this.frameList.getFrameData(name);
    }

    public String getFrameStringData(String name) {
        return this.frameList.getFrameStringData(name);
    }

    public boolean equals(File file) {
        if (Header.getIDString(file, 3).startsWith(HEADER_ID)) {
            return true;
        }
        return false;
    }

    public FormatObject createObject(File file) {
        return new ID3Format(file);
    }

    public String getMimeType() {
        return "audio/mpeg";
    }

    public String getMediaClass() {
        return "object.item.audioItem.musicTrack";
    }

    public AttributeList getAttributeList() {
        AttributeList attrList = new AttributeList();
        attrList.add(new Attribute("size", Long.toString(this.mp3File.length())));
        return attrList;
    }

    public String getTitle() {
        String title = getFrameStringData(ID3Frame.TIT2);
        if (title.length() > 0) {
            return title;
        }
        title = getFrameStringData(ID3Frame.TIT1);
        if (title.length() > 0) {
            return title;
        }
        return getFrameStringData(ID3Frame.TIT2);
    }

    public String getCreator() {
        String creator = getFrameStringData(ID3Frame.TPE1);
        if (creator.length() > 0) {
            return creator;
        }
        creator = getFrameStringData(ID3Frame.TPE2);
        if (creator.length() > 0) {
            return creator;
        }
        creator = getFrameStringData(ID3Frame.TPE3);
        if (creator.length() > 0) {
            return creator;
        }
        return getFrameStringData(ID3Frame.TPE4);
    }

    public void print() {
        System.out.println("header = " + new String(this.header));
        System.out.println("ID = " + getHeaderID());
        System.out.println("Size = " + getHeaderSize());
        System.out.println("isUnsynchronisation = " + isUnsynchronisation());
        System.out.println("hasExtendedHeader = " + hasExtendedHeader());
        System.out.println("isExperimental = " + isExperimental());
        System.out.println("hasFooter = " + hasFooter());
        int frameCnt = this.frameList.size();
        for (int n = 0; n < frameCnt; n++) {
            ID3Frame frame = this.frameList.getFrame(n);
            System.out.println("[" + n + "] : " + frame.getID());
            System.out.println("     " + frame.getData());
        }
    }
}
