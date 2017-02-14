package org.cybergarage.upnp.std.av.server.directory.file;

import java.io.File;
import org.cybergarage.upnp.std.av.server.Directory;
import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatObject;
import org.cybergarage.upnp.std.av.server.object.item.file.FileItemNode;
import org.cybergarage.upnp.std.av.server.object.item.file.FileItemNodeList;
import org.cybergarage.util.Debug;

public class FileDirectory extends Directory {
    private String path;

    public FileDirectory(String name, String path) {
        super(name);
        setPath(path);
    }

    public void setPath(String value) {
        this.path = value;
    }

    public String getPath() {
        return this.path;
    }

    private boolean updateItemNode(FileItemNode itemNode, File file) {
        Format format = getContentDirectory().getFormat(file);
        if (format == null) {
            return false;
        }
        FormatObject formatObj = format.createObject(file);
        itemNode.setFile(file);
        String title = formatObj.getTitle();
        if (title.length() > 0) {
            itemNode.setTitle(title);
        }
        String creator = formatObj.getCreator();
        if (creator.length() > 0) {
            itemNode.setCreator(creator);
        }
        String mediaClass = format.getMediaClass();
        if (mediaClass.length() > 0) {
            itemNode.setUPnPClass(mediaClass);
        }
        itemNode.setDate(file.lastModified());
        try {
            itemNode.setStorageUsed(file.length());
        } catch (Exception e) {
            Debug.warning(e);
        }
        String protocol = "http-get:*:" + format.getMimeType() + ":*";
        itemNode.setResource(getContentDirectory().getContentExportURL(itemNode.getID()), protocol, formatObj.getAttributeList());
        getContentDirectory().updateSystemUpdateID();
        return true;
    }

    private FileItemNode createCompareItemNode(File file) {
        if (getContentDirectory().getFormat(file) == null) {
            return null;
        }
        FileItemNode itemNode = new FileItemNode();
        itemNode.setFile(file);
        return itemNode;
    }

    private int getDirectoryItemNodeList(File dirFile, FileItemNodeList itemNodeList) {
        for (File file : dirFile.listFiles()) {
            if (file.isDirectory()) {
                getDirectoryItemNodeList(file, itemNodeList);
            } else if (file.isFile()) {
                FileItemNode itemNode = createCompareItemNode(file);
                if (itemNode != null) {
                    itemNodeList.add(itemNode);
                }
            }
        }
        return itemNodeList.size();
    }

    private FileItemNodeList getCurrentDirectoryItemNodeList() {
        FileItemNodeList itemNodeList = new FileItemNodeList();
        getDirectoryItemNodeList(new File(getPath()), itemNodeList);
        return itemNodeList;
    }

    private FileItemNode getItemNode(File file) {
        int nContents = getNContentNodes();
        for (int n = 0; n < nContents; n++) {
            ContentNode cnode = getContentNode(n);
            if (cnode instanceof FileItemNode) {
                FileItemNode itemNode = (FileItemNode) cnode;
                if (itemNode.equals(file)) {
                    return itemNode;
                }
            }
        }
        return null;
    }

    private void addItemNode(FileItemNode itemNode) {
        addContentNode(itemNode);
    }

    private boolean updateItemNodeList(FileItemNode newItemNode) {
        File newItemNodeFile = newItemNode.getFile();
        FileItemNode currItemNode = getItemNode(newItemNodeFile);
        if (currItemNode == null) {
            newItemNode.setID(getContentDirectory().getNextItemID());
            updateItemNode(newItemNode, newItemNodeFile);
            addItemNode(newItemNode);
            return true;
        } else if (currItemNode.getFileTimeStamp() == newItemNode.getFileTimeStamp()) {
            return false;
        } else {
            updateItemNode(currItemNode, newItemNodeFile);
            return true;
        }
    }

    private boolean updateItemNodeList() {
        int n;
        boolean updateFlag = false;
        int nContents = getNContentNodes();
        ContentNode[] cnode = new ContentNode[nContents];
        for (n = 0; n < nContents; n++) {
            cnode[n] = getContentNode(n);
        }
        for (n = 0; n < nContents; n++) {
            if (cnode[n] instanceof FileItemNode) {
                File itemFile = cnode[n].getFile();
                if (!(itemFile == null || itemFile.exists())) {
                    removeContentNode(cnode[n]);
                    updateFlag = true;
                }
            }
        }
        FileItemNodeList itemNodeList = getCurrentDirectoryItemNodeList();
        int itemNodeCnt = itemNodeList.size();
        for (n = 0; n < itemNodeCnt; n++) {
            if (updateItemNodeList(itemNodeList.getFileItemNode(n))) {
                updateFlag = true;
            }
        }
        return updateFlag;
    }

    public boolean update() {
        return updateItemNodeList();
    }
}
