package com.letv.business.flow.album;

import android.app.Activity;
import com.letv.core.utils.LetvBaseObservable;

public class AlbumPlayFlowObservable extends LetvBaseObservable {
    public static final String ON_CHECK_PIP_VISIBILE = "PlayAlbumFlowObservable5";
    public static final String ON_CONTROLLER_DISABLE = "PlayAlbumFlowObservable14";
    public static final String ON_HIDE_3G = "PlayAlbumFlowObservable11";
    public static final String ON_NETWORK_DISCONNECTED = "PlayAlbumFlowObservable15";
    public static final String ON_SHOW_3G = "PlayAlbumFlowObservable10";
    public static final String ON_SHOW_3G_WITH_BLACK = "PlayAlbumFlowObservable12";
    public static final String ON_START_FETCHING = "PlayAlbumFlowObservable1";
    public static final String ON_STREAM_INIT = "PlayAlbumFlowObservable4";
    public static final String REFRESH_DATA_AFTER_REQUEST_VIDEO_URL = "PlayAlbumFlowObservable13";
    private static final String TAG = "PlayAlbumFlowObservable";

    public static class FloatBallNotify {
        public final Activity activity;
        public final String cid;
        public final String toHandleModel;

        public FloatBallNotify(Activity activity, String toHandleModel, String cid) {
            this.activity = activity;
            this.toHandleModel = toHandleModel;
            this.cid = cid;
        }
    }

    public static class LocalVideoSubtitlesPath {
        public final String path;

        public LocalVideoSubtitlesPath(String path) {
            this.path = path;
        }
    }

    public static class PlayErrorCodeNotify {
        public final String errorCode;
        public final boolean shouldShow;
        public final String subErrorCode;

        public PlayErrorCodeNotify(String errorCode, boolean shouldShow) {
            this(errorCode, shouldShow, null);
        }

        public PlayErrorCodeNotify(String errorCode, boolean shouldShow, String subErrorCode) {
            this.errorCode = errorCode;
            this.shouldShow = shouldShow;
            this.subErrorCode = subErrorCode;
        }
    }

    public static class RequestCombineParams {
        public final String cid;
        public final String pid;
        public final String vid;
        public final String zid;

        public RequestCombineParams(String pid, String vid, String cid, String zid) {
            this.pid = pid;
            this.vid = vid;
            this.cid = cid;
            this.zid = zid;
        }
    }

    public static class VideoTitleNotify {
        public final String title;

        public VideoTitleNotify(String title) {
            this.title = title;
        }
    }
}
