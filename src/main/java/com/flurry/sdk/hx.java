package com.flurry.sdk;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class hx {
    private static hx a = null;
    private static final String b = hx.class.getSimpleName();
    private final hs<String, ik<hw<?>>> c = new hs();
    private final hs<ik<hw<?>>, String> d = new hs();

    public static synchronized hx a() {
        hx hxVar;
        synchronized (hx.class) {
            if (a == null) {
                a = new hx();
            }
            hxVar = a;
        }
        return hxVar;
    }

    public static synchronized void b() {
        synchronized (hx.class) {
            if (a != null) {
                a.c();
                a = null;
            }
        }
    }

    private hx() {
    }

    public synchronized void a(String str, hw<?> hwVar) {
        if (!(TextUtils.isEmpty(str) || hwVar == null)) {
            Object ikVar = new ik(hwVar);
            if (!this.c.c(str, ikVar)) {
                this.c.a((Object) str, ikVar);
                this.d.a(ikVar, (Object) str);
            }
        }
    }

    public synchronized void b(String str, hw<?> hwVar) {
        if (!TextUtils.isEmpty(str)) {
            ik ikVar = new ik(hwVar);
            this.c.b(str, ikVar);
            this.d.b(ikVar, str);
        }
    }

    public synchronized void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (ik b : this.c.a((Object) str)) {
                this.d.b(b, str);
            }
            this.c.b(str);
        }
    }

    public synchronized void a(hw<?> hwVar) {
        if (hwVar != null) {
            Object ikVar = new ik(hwVar);
            for (String b : this.d.a(ikVar)) {
                this.c.b(b, ikVar);
            }
            this.d.b(ikVar);
        }
    }

    public synchronized void c() {
        this.c.a();
        this.d.a();
    }

    public synchronized int b(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = this.c.a((Object) str).size();
        }
        return i;
    }

    public synchronized List<hw<?>> c(String str) {
        List<hw<?>> emptyList;
        if (TextUtils.isEmpty(str)) {
            emptyList = Collections.emptyList();
        } else {
            List<hw<?>> arrayList = new ArrayList();
            Iterator it = this.c.a((Object) str).iterator();
            while (it.hasNext()) {
                hw hwVar = (hw) ((ik) it.next()).get();
                if (hwVar == null) {
                    it.remove();
                } else {
                    arrayList.add(hwVar);
                }
            }
            emptyList = arrayList;
        }
        return emptyList;
    }

    public void a(final hv hvVar) {
        if (hvVar != null) {
            for (final hw hwVar : c(hvVar.a())) {
                hn.a().b(new jp(this) {
                    final /* synthetic */ hx c;

                    public void a() {
                        hwVar.a(hvVar);
                    }
                });
            }
        }
    }
}
