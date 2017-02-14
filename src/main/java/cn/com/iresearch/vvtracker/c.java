package cn.com.iresearch.vvtracker;

import android.content.Context;
import cn.com.iresearch.vvtracker.dao.VideoPlayInfo;
import cn.com.iresearch.vvtracker.dao.a;

final class c implements Runnable {
    private /* synthetic */ IRVideo a;
    private final /* synthetic */ Context b;
    private final /* synthetic */ VideoPlayInfo c;

    c(IRVideo iRVideo, Context context, VideoPlayInfo videoPlayInfo) {
        this.a = iRVideo;
        this.b = context;
        this.c = videoPlayInfo;
    }

    public final void run() {
        try {
            if (a.isNetworkAvailable(this.b) && a.urlGet(a.getUrl(this.b, this.c)) != 0) {
                IRVideo.a(this.a, this.c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
