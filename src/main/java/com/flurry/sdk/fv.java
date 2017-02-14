package com.flurry.sdk;

import android.content.Context;
import java.io.File;
import java.util.List;
import java.util.Map;

public class fv {
    private static final String b = fv.class.getSimpleName();
    boolean a;
    private final fw c;
    private final File d;
    private String e;

    public fv() {
        this(hn.a().c());
    }

    public fv(Context context) {
        this.c = new fw();
        this.d = context.getFileStreamPath(".flurryinstallreceiver.");
        ib.a(3, b, "Referrer file name if it exists:  " + this.d);
    }

    public synchronized void a() {
        this.d.delete();
        this.e = null;
        this.a = true;
    }

    public synchronized Map<String, List<String>> a(boolean z) {
        Map<String, List<String>> a;
        b();
        a = this.c.a(this.e);
        if (z) {
            a();
        }
        return a;
    }

    public synchronized void a(String str) {
        this.a = true;
        b(str);
        c();
    }

    private void b(String str) {
        if (str != null) {
            this.e = str;
        }
    }

    private void b() {
        if (!this.a) {
            this.a = true;
            ib.a(4, b, "Loading referrer info from file: " + this.d.getAbsolutePath());
            String c = jm.c(this.d);
            ib.a(b, "Referrer file contents: " + c);
            b(c);
        }
    }

    private void c() {
        jm.a(this.d, this.e);
    }
}
