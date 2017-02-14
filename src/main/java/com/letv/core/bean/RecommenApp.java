package com.letv.core.bean;

public class RecommenApp implements LetvBaseBean {
    private static final long serialVersionUID = 6209688347776857744L;
    private String app_name;
    private String desc;
    private String dwonUrl;
    private boolean flag;
    private String imgBigUrl;
    private String imgUrl;
    private boolean isInstall = false;
    private String name;

    public boolean isInstall() {
        return this.isInstall;
    }

    public void setInstall(boolean b) {
        this.isInstall = b;
    }

    public String getImgBigUrl() {
        return this.imgBigUrl;
    }

    public void setImgBigUrl(String imgBigUrl) {
        this.imgBigUrl = imgBigUrl;
    }

    public String getApp_name() {
        return this.app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDwonUrl() {
        return this.dwonUrl;
    }

    public void setDwonUrl(String dwonUrl) {
        this.dwonUrl = dwonUrl;
    }
}
