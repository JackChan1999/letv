package com.letv.lemallsdk.model;

public class MenuEntity extends BaseBean {
    private static final long serialVersionUID = 1;
    private String icon;
    private String pageFlag;
    private String title;
    private String url;

    public String getPageFlag() {
        return this.pageFlag;
    }

    public void setPageFlag(String pageFlag) {
        this.pageFlag = pageFlag;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
