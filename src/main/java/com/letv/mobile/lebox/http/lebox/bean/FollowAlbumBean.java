package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;
import java.util.List;

public class FollowAlbumBean implements LetvHttpBaseModel {
    private FollowAlbumInfo albumInfo;
    private FollowGetAllExtBean ext;
    private String pid;
    private FollowAlbumTagBean tag;
    private List<FollowVideoBean> videoInfoList;

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public FollowAlbumTagBean getTag() {
        return this.tag;
    }

    public void setTag(FollowAlbumTagBean tag) {
        this.tag = tag;
    }

    public List<FollowVideoBean> getVideoInfoList() {
        return this.videoInfoList;
    }

    public void setVideoInfoList(List<FollowVideoBean> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    public FollowAlbumInfo getAlbumInfo() {
        return this.albumInfo;
    }

    public void setAlbumInfo(FollowAlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    public FollowGetAllExtBean getExt() {
        return this.ext;
    }

    public void setExt(FollowGetAllExtBean ext) {
        this.ext = ext;
    }

    public String getIsEnd() {
        if (this.ext != null) {
            return this.ext.getIsEnd();
        }
        return "";
    }

    public String getAlbumName() {
        if (this.albumInfo != null) {
            return this.albumInfo.getNameCn();
        }
        return "";
    }

    public String toString() {
        return "FollowAlbumBean [pid=" + this.pid + ", tag=" + this.tag + ", ext=" + this.ext + ", albumInfo=" + this.albumInfo + ", videoInfoList=" + this.videoInfoList + "]";
    }
}
