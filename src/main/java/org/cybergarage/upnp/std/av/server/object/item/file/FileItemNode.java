package org.cybergarage.upnp.std.av.server.object.item.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;
import org.cybergarage.util.Debug;
import org.cybergarage.util.FileUtil;

public class FileItemNode extends ItemNode {
    private File itemFile;

    public FileItemNode() {
        setFile(null);
    }

    public void setFile(File file) {
        this.itemFile = file;
    }

    public File getFile() {
        return this.itemFile;
    }

    public long getFileTimeStamp() {
        long itemFileTimeStamp = 0;
        if (this.itemFile != null) {
            try {
                itemFileTimeStamp = this.itemFile.lastModified();
            } catch (Exception e) {
                Debug.warning(e);
            }
        }
        return itemFileTimeStamp;
    }

    public boolean equals(File file) {
        if (this.itemFile == null) {
            return false;
        }
        return this.itemFile.equals(file);
    }

    public byte[] getContent() {
        byte[] fileByte = new byte[0];
        try {
            fileByte = FileUtil.load(this.itemFile);
        } catch (Exception e) {
        }
        return fileByte;
    }

    public long getContentLength() {
        return this.itemFile.length();
    }

    public InputStream getContentInputStream() {
        try {
            return new FileInputStream(this.itemFile);
        } catch (Exception e) {
            Debug.warning(e);
            return null;
        }
    }

    public String getMimeType() {
        Format itemFormat = getContentDirectory().getFormat(getFile());
        if (itemFormat == null) {
            return "*/*";
        }
        return itemFormat.getMimeType();
    }
}
