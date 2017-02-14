package com.letv.component.player.core;

public class PlayUrl {
    public StreamType mStreamType;
    public String mUrl;
    public int mVid;

    public enum StreamType {
        STREAM_TYPE_UNKNOWN("0K"),
        STREAM_TYPE_180K("180K"),
        STREAM_TYPE_350K("350K"),
        STREAM_TYPE_1000K("1000K"),
        STREAM_TYPE_1300K("1300K"),
        STREAM_TYPE_720P("720P"),
        STREAM_TYPE_1080P("1080P");
        
        private String mValue;

        private StreamType(String streamType) {
            this.mValue = streamType;
        }

        public String value() {
            return this.mValue;
        }
    }

    public PlayUrl(int vid, StreamType streamType, String url) {
        this.mVid = vid;
        this.mStreamType = streamType;
        this.mUrl = url;
    }

    public int getVid() {
        return this.mVid;
    }

    public void setVid(int vid) {
        this.mVid = vid;
    }

    public StreamType getStreamType() {
        return this.mStreamType;
    }

    public void setStreamType(StreamType streamType) {
        this.mStreamType = streamType;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
