package com.letv.android.remotedevice;

public class DeviceUnavailableException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public DeviceUnavailableException(String string) {
        super(string);
    }
}
