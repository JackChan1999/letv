package com.letv.android.client.album.observable;

import com.letv.android.client.album.controller.AlbumGestureController.VolumnChangeStyle;
import com.letv.core.utils.LetvBaseObservable;

public class AlbumGestureObservable extends LetvBaseObservable {
    public static final String DOUBLE_CLICK = (TAG + 4);
    public static final String ON_CLICK = (TAG + 2);
    public static final String ON_GESTURE_CHANGE = (TAG + 1);
    public static final String ON_TOUCH_EVENT_UP = (TAG + 3);
    private static final String TAG = AlbumGestureObservable.class.getSimpleName();

    public static class ProgressRegulateNotify {
        public final int curPos;
        public final boolean forward;
        public final int total;

        public ProgressRegulateNotify(int curPos, int total, boolean forward) {
            this.curPos = curPos;
            this.total = total;
            this.forward = forward;
        }
    }

    public static class VolumeChangeNotify {
        public final int max;
        public final int progress;
        public final boolean showSeekbar;
        public final VolumnChangeStyle style;

        public VolumeChangeNotify(int max, int progress, VolumnChangeStyle style) {
            this(max, progress, style, true);
        }

        public VolumeChangeNotify(int max, int progress, VolumnChangeStyle style, boolean showSeekBar) {
            this.max = max;
            this.progress = progress;
            this.style = style;
            this.showSeekbar = showSeekBar;
        }
    }
}
