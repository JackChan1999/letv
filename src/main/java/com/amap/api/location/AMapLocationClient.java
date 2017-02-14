package com.amap.api.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import com.amap.api.fence.Fence;
import com.loc.a;
import com.loc.af;
import com.loc.e;

public class AMapLocationClient implements LocationManagerBase {
    a a;
    Context b;
    LocationManagerBase c;
    AMapLocationClientOption d;
    AMapLocationListener e;
    AMapLocationClient f;

    public AMapLocationClient(Context context) {
        if (context == null) {
            try {
                throw new IllegalArgumentException("Context参数不能为null");
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.b = context.getApplicationContext();
        this.f = new AMapLocationClient(this.b, null, true);
        if (Looper.myLooper() == null) {
            this.a = new a(this.f, this.b.getMainLooper());
        } else {
            this.a = new a(this.f);
        }
    }

    public AMapLocationClient(Context context, Intent intent) {
        if (context == null) {
            try {
                throw new IllegalArgumentException("Context参数不能为null");
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.b = context.getApplicationContext();
        this.f = new AMapLocationClient(this.b, intent, true);
        if (Looper.myLooper() == null) {
            this.a = new a(this.f, this.b.getMainLooper());
        } else {
            this.a = new a(this.f);
        }
    }

    private AMapLocationClient(Context context, Intent intent, boolean z) {
        try {
            this.b = context;
            Context context2 = context;
            this.c = (LocationManagerBase) af.a(context2, e.a("2.3.0"), "com.amap.api.location.LocationManagerWrapper", a.class, new Class[]{Context.class, Intent.class}, new Object[]{context, intent});
        } catch (Throwable th) {
            this.c = new a(context, intent);
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption == null) {
            try {
                throw new IllegalArgumentException("LocationManagerOption参数不能为null");
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        Message obtain = Message.obtain();
        obtain.arg1 = 1;
        obtain.obj = aMapLocationClientOption;
        this.a.sendMessage(obtain);
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            try {
                throw new IllegalArgumentException("listener参数不能为null");
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        Message obtain = Message.obtain();
        obtain.arg1 = 2;
        obtain.obj = aMapLocationListener;
        this.a.sendMessage(obtain);
    }

    public void startLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 3;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void stopLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 4;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void addGeoFenceAlert(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            Fence fence = new Fence();
            fence.b = str;
            fence.d = d;
            fence.c = d2;
            fence.e = f;
            fence.a = pendingIntent;
            fence.f = j;
            obtain.obj = fence;
            obtain.arg1 = 6;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent, String str) {
        try {
            Message obtain = Message.obtain();
            Fence fence = new Fence();
            fence.b = str;
            fence.a = pendingIntent;
            obtain.obj = fence;
            obtain.arg1 = 10;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        try {
            Message obtain = Message.obtain();
            obtain.obj = pendingIntent;
            obtain.arg1 = 7;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public AMapLocation getLastKnownLocation() {
        try {
            if (!(this.f == null || this.f.c == null)) {
                return this.f.c.getLastKnownLocation();
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public void startAssistantLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 8;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void stopAssistantLocation() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 9;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String getVersion() {
        try {
            if (this.f != null) {
                return this.f.c.getVersion();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    public static void setApiKey(String str) {
        try {
            e.a = str;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean isStarted() {
        try {
            if (this.f != null) {
                return this.f.c.isStarted();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 5;
            obtain.obj = aMapLocationListener;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            Message obtain = Message.obtain();
            obtain.arg1 = 11;
            this.a.sendMessage(obtain);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
