package org.cybergarage.upnp.std.av.server.directory.mythtv;

import java.io.File;
import org.cybergarage.util.Debug;

public class MythRecordedInfo {
    private static final String NUV_FILE_DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String NUV_FILE_EXT = "nuv";
    private String category;
    private int chanID;
    private String description;
    private long endTime;
    private String fname;
    private long fsize;
    private String recGroup;
    private String recordFilePrefix;
    private int recordID;
    private long startTime;
    private String subTitle;
    private String title;

    public void setRecordFilePrefix(String value) {
        this.recordFilePrefix = value;
    }

    public String getRecordFilePrefix() {
        return this.recordFilePrefix;
    }

    public void setCategory(String string) {
        this.category = string;
    }

    public String getCategory() {
        return this.category;
    }

    public void setChanID(int i) {
        this.chanID = i;
    }

    public int getChanID() {
        return this.chanID;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public String getDescription() {
        return this.description;
    }

    public void setEndTime(long l) {
        this.endTime = l;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setRecGroup(String string) {
        this.recGroup = string;
    }

    public String getRecGroup() {
        return this.recGroup;
    }

    public void setRecordID(int i) {
        this.recordID = i;
    }

    public int getRecordID() {
        return this.recordID;
    }

    public void setStartTime(long l) {
        this.startTime = l;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubTitle(String string) {
        this.subTitle = string;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public File getFile() {
        return new File(getRecordFilePrefix() + getFileName());
    }

    public void print() {
        Debug.message("title = " + getTitle());
        Debug.message("subTitle = " + getSubTitle());
        Debug.message("file = " + getFile());
    }

    public void setFileSize(long s) {
        this.fsize = s;
    }

    public long getFileSize() {
        return this.fsize;
    }

    public void setFileName(String s) {
        this.fname = s;
    }

    public String getFileName() {
        return this.fname;
    }
}
