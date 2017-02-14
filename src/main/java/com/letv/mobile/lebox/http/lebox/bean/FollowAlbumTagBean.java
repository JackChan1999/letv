package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class FollowAlbumTagBean implements LetvHttpBaseModel {
    private String albumCover;
    private String albumTitle;

    public String getAlbumCover() {
        return this.albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getAlbumTitle() {
        return this.albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String toString() {
        return "{\"albumCover\":\"" + this.albumCover + "\",\"albumTitle\":\"" + this.albumTitle + "\"}";
    }
}
