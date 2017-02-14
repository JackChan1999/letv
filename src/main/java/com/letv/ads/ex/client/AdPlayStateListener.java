package com.letv.ads.ex.client;

import android.os.Bundle;

public interface AdPlayStateListener {
    public static final String KEY_STATE = "state";
    public static final int PLAY_STATE_COMPLETE = 4;
    public static final int PLAY_STATE_DILE = 1;
    public static final int PLAY_STATE_ERROR = -1;
    public static final int PLAY_STATE_LOADING = 0;
    public static final int PLAY_STATE_PREPARED = 2;
    public static final int PLAY_STATE_STARTED = 3;

    void onADPlayStateChange(Bundle bundle);
}
