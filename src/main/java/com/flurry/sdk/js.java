package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.jf.a;
import java.lang.Thread.UncaughtExceptionHandler;

public class js implements ie, a, UncaughtExceptionHandler {
    private static final String a = js.class.getSimpleName();
    private boolean b;

    public void a(Context context) {
        jf a = je.a();
        this.b = ((Boolean) a.a("CaptureUncaughtExceptions")).booleanValue();
        a.a("CaptureUncaughtExceptions", (a) this);
        ib.a(4, a, "initSettings, CrashReportingEnabled = " + this.b);
        jt.a().a(this);
    }

    public void b() {
        jt.b();
        je.a().b("CaptureUncaughtExceptions", (a) this);
    }

    public void a(String str, Object obj) {
        if (str.equals("CaptureUncaughtExceptions")) {
            this.b = ((Boolean) obj).booleanValue();
            ib.a(4, a, "onSettingUpdate, CrashReportingEnabled = " + this.b);
            return;
        }
        ib.a(6, a, "onSettingUpdate internal error!");
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        if (this.b) {
            String str = "";
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (th.getMessage() != null) {
                    stringBuilder.append(" (" + th.getMessage() + ")\n");
                }
                str = stringBuilder.toString();
            } else if (th.getMessage() != null) {
                str = th.getMessage();
            }
            fu.a().a("uncaught", str, th);
        }
        jb.a().g();
        hf.a().d();
    }
}
