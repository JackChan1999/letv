package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class FollowAddExtBean implements LetvHttpBaseModel {
    private String startIgnoreEpisodeNum = "0";
    private String stream;

    public String getStartIgnoreEpisodeNum() {
        return this.startIgnoreEpisodeNum;
    }

    public void setStartIgnoreEpisodeNum(String startIgnoreEpisodeNum) {
        this.startIgnoreEpisodeNum = startIgnoreEpisodeNum;
    }

    public String getStream() {
        return this.stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String toString() {
        return "{\"startIgnoreEpisodeNum\":\"" + this.startIgnoreEpisodeNum + "\",\"stream\":\"" + this.stream + "\"}";
    }
}
