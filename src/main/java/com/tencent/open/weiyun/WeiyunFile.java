package com.tencent.open.weiyun;

/* compiled from: ProGuard */
public class WeiyunFile {
    private String a;
    private String b;
    private String c;
    private long d;

    public WeiyunFile(String str, String str2, String str3, long j) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = j;
    }

    public String getFileId() {
        return this.a;
    }

    public String getFileName() {
        return this.b;
    }

    public String getCreateTime() {
        return this.c;
    }

    public long getFileSize() {
        return this.d;
    }

    public void setFileId(String str) {
        this.a = str;
    }

    public void setFileName(String str) {
        this.b = str;
    }

    public void setCreateTime(String str) {
        this.c = str;
    }

    public void setFileSize(long j) {
        this.d = j;
    }
}
