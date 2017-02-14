package com.coolcloud.uac.android.common;

public class QikuCaptchaData {
    private byte[] bytes;
    private String key;

    public QikuCaptchaData(String key, byte[] bytes) {
        this.key = key;
        this.bytes = bytes;
    }

    public String getCaptchaKey() {
        return this.key;
    }

    public byte[] getCaptchaByte() {
        return this.bytes;
    }
}
