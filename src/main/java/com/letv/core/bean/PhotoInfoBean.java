package com.letv.core.bean;

import java.io.Serializable;

public class PhotoInfoBean implements Serializable {
    private static final long serialVersionUID = 4567695659360500759L;
    private String photoDesc;
    public String photoPath;
    public String webPath;

    public PhotoInfoBean(String imagePath, String imageDesc) {
        this.photoPath = imagePath;
        this.photoDesc = imageDesc;
    }

    public PhotoInfoBean(String imagePath, String imageDesc, String webPath) {
        this.photoPath = imagePath;
        this.photoDesc = imageDesc;
        this.webPath = webPath;
    }

    public String getPhotoDesc() {
        return this.photoDesc == null ? "" : this.photoDesc;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }
}
