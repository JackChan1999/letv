package com.letv.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoFilesInfoBean implements Serializable {
    private static final long serialVersionUID = 7362772535691590409L;
    private List<PhotoInfoBean> photo;

    public PhotoFilesInfoBean(int MAX_PHOTO_NUMBER) {
        this.photo = new ArrayList(MAX_PHOTO_NUMBER);
    }

    public PhotoFilesInfoBean() {
        this.photo = new ArrayList();
    }

    public List<PhotoInfoBean> getPhoto() {
        return this.photo;
    }

    public void setPhoto(List<PhotoInfoBean> photo) {
        this.photo = photo;
    }
}
