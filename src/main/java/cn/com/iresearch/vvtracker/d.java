package cn.com.iresearch.vvtracker;

import cn.com.iresearch.vvtracker.dao.VideoPlayInfo;
import java.util.List;

final class d implements Runnable {
    private /* synthetic */ IRVideo a;
    private final /* synthetic */ VideoPlayInfo b;

    d(IRVideo iRVideo, VideoPlayInfo videoPlayInfo) {
        this.a = iRVideo;
        this.b = videoPlayInfo;
    }

    public final void run() {
        try {
            List a = this.a.b.a(VideoPlayInfo.class);
            if (a.size() > 9) {
                for (Object b : a.subList(9, a.size())) {
                    this.a.b.b(b);
                }
            }
            this.a.b.a(this.b);
        } catch (Exception e) {
            "保存视频数据到数据库异常" + e.toString();
            e.printStackTrace();
        }
    }
}
