package com.letv.core.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadFileBean {
    public String mContentType = "application/octet-stream";
    public byte[] mData;
    public File mFile;
    public String mFilname;
    public InputStream mInStream;
    public String mParameterName;

    public UploadFileBean(String filname, byte[] data, String parameterName, String contentType) {
        this.mData = data;
        this.mFilname = filname;
        this.mParameterName = parameterName;
        if (contentType != null) {
            this.mContentType = contentType;
        }
    }

    public UploadFileBean(File file, String parameterName, String contentType) {
        this.mFilname = file.getName();
        this.mParameterName = parameterName;
        this.mFile = file;
        try {
            this.mInStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (contentType != null) {
            this.mContentType = contentType;
        }
    }
}
