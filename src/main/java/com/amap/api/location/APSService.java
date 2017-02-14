package com.amap.api.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.loc.af;
import com.loc.d;
import com.loc.e;
import com.loc.n;

public class APSService extends Service {
    Messenger a;
    APSServiceBase b;

    public IBinder onBind(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("apiKey");
            if (!TextUtils.isEmpty(stringExtra)) {
                n.a(stringExtra);
            }
            this.a = new Messenger(this.b.getHandler());
            return this.a.getBinder();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void onCreate(Context context) {
        try {
            Context context2 = context;
            this.b = (APSServiceBase) af.a(context2, e.a("2.3.0"), "com.amap.api.location.APSServiceWrapper", d.class, new Class[]{Context.class}, new Object[]{context});
        } catch (Throwable th) {
            th.printStackTrace();
            this.b = new d(this);
        }
        this.b.onCreate();
        super.onCreate();
    }

    public void onCreate() {
        onCreate(this);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            return this.b.onStartCommand(intent, i, i2);
        } catch (Throwable th) {
            th.printStackTrace();
            return super.onStartCommand(intent, i, i2);
        }
    }

    public void onDestroy() {
        try {
            this.b.onDestroy();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        super.onDestroy();
    }
}
