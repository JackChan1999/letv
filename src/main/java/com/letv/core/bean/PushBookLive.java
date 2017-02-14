package com.letv.core.bean;

public class PushBookLive implements LetvBaseBean {
    public String channelName;
    public String code;
    public String id;
    public String launchMode;
    public String md5_id;
    public String play_time;
    public String programName;
    public String url;
    public String url_350 = null;

    public String toString() {
        return "PushBookLive [channelName=" + this.channelName + ", url=" + this.url + ", url_350=" + this.url_350 + ", code=" + this.code + ", programName=" + this.programName + ", play_time=" + this.play_time + " , launchMode = " + this.launchMode + "]";
    }
}
