package cn.com.iresearch.vvtracker;

import android.content.Context;
import cn.com.iresearch.vvtracker.dao.VideoPlayInfo;
import java.util.List;

final class a implements Runnable {
    private /* synthetic */ IRVideo a;
    private final /* synthetic */ Context b;

    a(IRVideo iRVideo, Context context) {
        this.a = iRVideo;
        this.b = context;
    }

    public final void run() {
        List<VideoPlayInfo> a = this.a.b.a(VideoPlayInfo.class);
        if (a != null) {
            for (VideoPlayInfo videoPlayInfo : a) {
                if ("end".equals(videoPlayInfo.getAction()) && cn.com.iresearch.vvtracker.dao.a.urlGet(cn.com.iresearch.vvtracker.dao.a.getUrl(this.b, videoPlayInfo)) == 1) {
                    IRVideo.a(this.a, videoPlayInfo);
                }
            }
        }
    }
}
