package com.letv.lemallsdk.model;

import java.util.List;

public class ShareEntity extends BaseBean {
    private static final long serialVersionUID = 1;
    private String content;
    private List<String> photoUrls;
    private String title;
    private String url;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPhotoUrls() {
        return this.photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
