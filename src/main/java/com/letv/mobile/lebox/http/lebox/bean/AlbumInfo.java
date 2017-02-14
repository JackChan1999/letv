package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class AlbumInfo implements LetvHttpBaseModel {
    String cid;
    String id;
    String isEnd;
    String nameCn;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String toString() {
        return "AlbumInfo [id=" + this.id + ", nameCn=" + this.nameCn + ", cid=" + this.cid + ", isEnd=" + this.isEnd + "]";
    }
}
