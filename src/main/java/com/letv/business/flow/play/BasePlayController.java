package com.letv.business.flow.play;

import android.os.Bundle;

public abstract class BasePlayController {

    public interface GestureChangeListener {
        void onGestureChange();
    }

    public interface PlayAlbumControllerCallBack {
        public static final int STATE_DATA_NULL = 4;
        public static final int STATE_DIRECTION_CHANGE = 6;
        public static final int STATE_FINISH = 1;
        public static final int STATE_FIRST_PLAYING = 8;
        public static final int STATE_NET_ERR = 3;
        public static final int STATE_NET_NULL = 2;
        public static final int STATE_NEXT = 7;
        public static final int STATE_OTHER = 5;
        public static final int STATE_REFRESH_DATA = 9;
        public static final int STATE_RUNNING = 0;

        void notify(int i);

        void notify(Bundle bundle);
    }
}
