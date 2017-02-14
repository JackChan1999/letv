package com.letv.android.client.worldcup.bean;

public enum DownloadStatus {
    TOSTART(0),
    STARTED(1),
    STOPPED(3),
    FINISHED(4),
    ERROR(5);
    
    private int state;

    private DownloadStatus(int state) {
        this.state = state;
    }

    public int toInt() {
        return this.state;
    }

    public String toString() {
        return String.valueOf(this.state);
    }

    public static DownloadStatus fromInt(int state) {
        DownloadStatus[] states = values();
        for (int i = 0; i < states.length; i++) {
            if (states[i].state == state) {
                return states[i];
            }
        }
        return null;
    }
}
