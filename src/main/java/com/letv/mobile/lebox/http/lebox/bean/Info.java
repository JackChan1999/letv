package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class Info implements LetvHttpBaseModel {
    AlbumInfo albumInfo;
    VideoInfo videoInfo;

    public VideoInfo getVideoInfo() {
        return this.videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public AlbumInfo getAlbumInfo() {
        return this.albumInfo;
    }

    public void setAlbumInfo(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    public String toString() {
        return "Info [videoInfo=" + this.videoInfo + ", albumInfo=" + this.albumInfo + "]";
    }
}
