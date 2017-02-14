package com.letv.component.player.Interface;

public interface OnMediaStateTimeListener {

    public enum MeidaStateType {
        INITPATH,
        CREATE,
        PREPARED,
        DIAPLAY,
        STOP,
        RELEASE,
        ERROR,
        HARD_ERROR
    }

    void onMediaStateTime(MeidaStateType meidaStateType, String str);
}
