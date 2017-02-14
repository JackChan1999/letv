package com.soundink.lib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import cn.qguang.signal.SignalCheck;
import com.soundink.lib.c.a;
import com.soundink.lib.c.b;

public class SoundInkService extends Service {
    static String a = "";
    private static SignalCheck c;
    private Context b;

    public int onStartCommand(Intent intent, int i, int i2) {
        this.b = getApplicationContext();
        g.a();
        g.a(this.b);
        if (c == null) {
            c = new SignalCheck();
        }
        a aVar = new a(this);
        a = a.b().toString();
        new a(this.b).execute(new Void[0]);
        c.SetMQMethod(2);
        c.a(g.a().b);
        Log.d(SoundInkInterface.getLogTag(), "soundink service is started");
        b.a("soundink service is started");
        return 2;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        try {
            if (c != null) {
                c.b();
                c = null;
                Log.d(SoundInkInterface.getLogTag(), "soundink service is stop");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public static void a() {
        if (c != null) {
            c.a();
        }
        Log.d(SoundInkInterface.getLogTag(), "soundink service is pause");
        b.a("soundink service is pause");
    }
}
