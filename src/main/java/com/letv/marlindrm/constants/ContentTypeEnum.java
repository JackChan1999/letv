package com.letv.marlindrm.constants;

public enum ContentTypeEnum {
    DASH("application/dash+xml"),
    HLS("application/vnd.apple.mpegurl"),
    PDCF("video/mp4"),
    M4F("video/mp4"),
    DCF("application/vnd.oma.drm.dcf"),
    BBTS("video/mp2t");
    
    private String type;

    private ContentTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
