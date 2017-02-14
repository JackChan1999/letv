package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.flurry.sdk.hq.a;

public class hr {
    private static hr a;
    private static final String b = hr.class.getSimpleName();
    private Object c;

    public static synchronized hr a() {
        hr hrVar;
        synchronized (hr.class) {
            if (a == null) {
                a = new hr();
            }
            hrVar = a;
        }
        return hrVar;
    }

    public static synchronized void b() {
        synchronized (hr.class) {
            if (a != null) {
                a.f();
            }
            a = null;
        }
    }

    private hr() {
        e();
    }

    public boolean c() {
        return this.c != null;
    }

    @TargetApi(14)
    private void e() {
        if (VERSION.SDK_INT >= 14 && this.c == null) {
            Context c = hn.a().c();
            if (c instanceof Application) {
                this.c = new ActivityLifecycleCallbacks(this) {
                    final /* synthetic */ hr a;

                    {
                        this.a = r1;
                    }

                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        ib.a(3, hr.b, "onActivityCreated for activity:" + activity);
                        a(activity, a.kCreated);
                    }

                    public void onActivityStarted(Activity activity) {
                        ib.a(3, hr.b, "onActivityStarted for activity:" + activity);
                        a(activity, a.kStarted);
                    }

                    public void onActivityResumed(Activity activity) {
                        ib.a(3, hr.b, "onActivityResumed for activity:" + activity);
                        a(activity, a.kResumed);
                    }

                    public void onActivityPaused(Activity activity) {
                        ib.a(3, hr.b, "onActivityPaused for activity:" + activity);
                        a(activity, a.kPaused);
                    }

                    public void onActivityStopped(Activity activity) {
                        ib.a(3, hr.b, "onActivityStopped for activity:" + activity);
                        a(activity, a.kStopped);
                    }

                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                        ib.a(3, hr.b, "onActivitySaveInstanceState for activity:" + activity);
                        a(activity, a.kSaveState);
                    }

                    public void onActivityDestroyed(Activity activity) {
                        ib.a(3, hr.b, "onActivityDestroyed for activity:" + activity);
                        a(activity, a.kDestroyed);
                    }

                    protected void a(Activity activity, a aVar) {
                        hq hqVar = new hq();
                        hqVar.a = activity;
                        hqVar.b = aVar;
                        hqVar.b();
                    }
                };
                ((Application) c).registerActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.c);
            }
        }
    }

    @TargetApi(14)
    private void f() {
        if (VERSION.SDK_INT >= 14 && this.c != null) {
            Context c = hn.a().c();
            if (c instanceof Application) {
                ((Application) c).unregisterActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.c);
                this.c = null;
            }
        }
    }
}
