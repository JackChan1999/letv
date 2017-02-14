package cn.com.iresearch.vvtracker;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import cn.com.iresearch.vvtracker.a.c;
import cn.com.iresearch.vvtracker.dao.VideoPlayInfo;
import cn.com.iresearch.vvtracker.db.a;

public class IRVideo {
    private VideoPlayInfo a;
    private a b;
    private int c;
    private long d;
    private long e;
    private long f;

    private IRVideo() {
        this.a = new VideoPlayInfo();
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
    }

    public static IRVideo getInstance() {
        return e.a;
    }

    public void init(Context context, String str) {
        if (str == null || str.trim().length() <= 0) {
            throw new RuntimeException("please fill the value");
        }
        Context applicationContext = context.getApplicationContext();
        a(applicationContext);
        Editor edit = applicationContext.getSharedPreferences("VV_Tracker", 0).edit();
        edit.putString("vv_uaid", str);
        try {
            edit.apply();
        } catch (Exception e) {
            edit.commit();
            e.printStackTrace();
        }
    }

    private void a(Context context) {
        if (this.b == null) {
            this.b = a.a(context, "vvtracker.db");
        }
    }

    public void newVideoPlay(Context context, String str, long j, Boolean bool) {
        try {
            this.c = 0;
            this.a = new VideoPlayInfo();
            this.a.setVideoID(str);
            this.a.setPauseCount(this.c);
            this.a.setPlayTime(this.d);
            this.a.setHeartTime(0);
            this.a.setVideoLength(j);
            this.a.setAction("init");
            Context applicationContext = context.getApplicationContext();
            a(applicationContext);
            try {
                if (cn.com.iresearch.vvtracker.dao.a.isNetworkAvailable(applicationContext)) {
                    c.b().a(new a(this, applicationContext));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            "创建视频数据异常" + e2.toString();
            e2.printStackTrace();
        }
    }

    public void videoPlay(Context context) {
        try {
            this.e = System.currentTimeMillis() / 1000;
            this.a.setAction("play");
            Context applicationContext = context.getApplicationContext();
            a(applicationContext);
            c.b().a(new d(this, this.a));
            VideoPlayInfo videoPlayInfo = this.a;
            if (applicationContext != null && videoPlayInfo != null) {
                c.a().a(new b(applicationContext, videoPlayInfo));
            }
        } catch (Exception e) {
            "存放视频A点数据到数据库异常" + e.toString();
            e.printStackTrace();
        }
    }

    public void videoPause() {
        try {
            this.c++;
            this.a.setAction("pause");
            this.a.setPauseCount(this.c);
        } catch (Exception e) {
            "更新视频暂停数据异常" + e.toString();
            e.printStackTrace();
        }
    }

    public void videoEnd(Context context) {
        try {
            this.f = System.currentTimeMillis() / 1000;
            this.d = this.f - this.e;
            this.a.setPlayTime(this.d);
            this.a.setAction("end");
            Context applicationContext = context.getApplicationContext();
            a(applicationContext);
            VideoPlayInfo videoPlayInfo = this.a;
            if (applicationContext != null && videoPlayInfo != null) {
                c.a().a(new c(this, applicationContext, videoPlayInfo));
            }
        } catch (Exception e) {
            "更新视频B点数据异常" + e.toString();
            e.printStackTrace();
        }
    }

    static /* synthetic */ void a(IRVideo iRVideo, VideoPlayInfo videoPlayInfo) {
        try {
            iRVideo.b.b((Object) videoPlayInfo);
        } catch (Exception e) {
            "删除视频数据到数据库异常" + e.toString();
            e.printStackTrace();
        }
    }
}
