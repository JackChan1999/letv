package org.cybergarage.upnp.std.av.server.object.item.mythtv;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.cybergarage.upnp.std.av.server.UPnP;
import org.cybergarage.upnp.std.av.server.directory.mythtv.MythRecordedInfo;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;
import org.cybergarage.util.Debug;
import org.cybergarage.util.FileUtil;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;

public class MythRecordedItemNode extends ItemNode {
    private static final String MIME_TYPE = "video/mpeg";
    private MythRecordedInfo recInfo;

    public MythRecordedItemNode() {
        setRecordedInfo(null);
    }

    public MythRecordedInfo getRecordedInfo() {
        return this.recInfo;
    }

    public void setRecordedInfo(MythRecordedInfo info) {
        this.recInfo = info;
        if (info != null) {
            setTitle(info.getTitle());
            setCreator("");
            setUPnPClass(UPnP.OBJECT_ITEM_VIDEOITEM_MOVIE);
            setDate(info.getStartTime());
            setStorageUsed(info.getFileSize());
            String url = getContentDirectory().getContentExportURL(getID());
            AttributeList attrList = new AttributeList();
            attrList.add(new Attribute("size", Long.toString(info.getFileSize())));
            setResource(url, "http-get:*:video/mpeg:*", attrList);
        }
    }

    public boolean equals(MythRecordedInfo info) {
        MythRecordedInfo recInfo = getRecordedInfo();
        if (info == null || recInfo == null || info.getChanID() != recInfo.getChanID()) {
            return false;
        }
        return true;
    }

    public byte[] getContent() {
        File recFile = getRecordedInfo().getFile();
        if (!recFile.exists()) {
            return new byte[0];
        }
        byte[] bArr = new byte[0];
        try {
            return FileUtil.load(recFile);
        } catch (Exception e) {
            return bArr;
        }
    }

    public long getContentLength() {
        return getRecordedInfo().getFile().length();
    }

    public InputStream getContentInputStream() {
        try {
            return new FileInputStream(getRecordedInfo().getFile());
        } catch (Exception e) {
            Debug.warning(e);
            return null;
        }
    }

    public String getMimeType() {
        return MIME_TYPE;
    }
}
