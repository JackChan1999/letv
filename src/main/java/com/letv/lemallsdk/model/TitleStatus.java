package com.letv.lemallsdk.model;

import java.util.List;

public class TitleStatus extends BaseBean {
    private static final long serialVersionUID = 1;
    private String hasMore;
    private String hasShare;
    private List<MenuEntity> menuList;
    private String pageFlag;
    private String title;

    public List<MenuEntity> getMenuList() {
        return this.menuList;
    }

    public void setMenuList(List<MenuEntity> menuList) {
        this.menuList = menuList;
    }

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

    public String getHasShare() {
        return this.hasShare;
    }

    public void setHasShare(String hasShare) {
        this.hasShare = hasShare;
    }

    public String getHasMore() {
        return this.hasMore;
    }

    public void setHasMore(String hasMore) {
        this.hasMore = hasMore;
    }
}
