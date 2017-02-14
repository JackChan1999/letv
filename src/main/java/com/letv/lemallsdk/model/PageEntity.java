package com.letv.lemallsdk.model;

public class PageEntity extends BaseBean {
    private static final long serialVersionUID = 1;
    private String title;
    private String url;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
