package cn.com.iresearch.vvtracker;

import android.content.Context;
import cn.com.iresearch.vvtracker.dao.VideoPlayInfo;
import cn.com.iresearch.vvtracker.dao.a;

final class b implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ VideoPlayInfo b;

    b(Context context, VideoPlayInfo videoPlayInfo) {
        this.a = context;
        this.b = videoPlayInfo;
    }

    public final void run() {
        try {
            if (!a.isNetworkAvailable(this.a) || a.urlGet(a.getUrl(this.a, this.b)) != 0) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
