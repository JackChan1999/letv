package com.flurry.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class jf {
    private Map<String, Object> a = new HashMap();
    private Map<String, List<a>> b = new HashMap();

    public interface a {
        void a(String str, Object obj);
    }

    public synchronized void a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            if (!a(obj, this.a.get(str))) {
                if (obj == null) {
                    this.a.remove(str);
                } else {
                    this.a.put(str, obj);
                }
                b(str, obj);
            }
        }
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void b(String str, Object obj) {
        if (this.b.get(str) != null) {
            for (a a : (List) this.b.get(str)) {
                a.a(str, obj);
            }
        }
    }

    public synchronized Object a(String str) {
        return this.a.get(str);
    }

    public synchronized void a(String str, a aVar) {
        if (!(TextUtils.isEmpty(str) || aVar == null)) {
            List list = (List) this.b.get(str);
            if (list == null) {
                list = new LinkedList();
            }
            list.add(aVar);
            this.b.put(str, list);
        }
    }

    public synchronized boolean b(String str, a aVar) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            z = false;
        } else if (aVar == null) {
            z = false;
        } else {
            List list = (List) this.b.get(str);
            z = list == null ? false : list.remove(aVar);
        }
        return z;
    }

    public synchronized void d() {
        this.b.clear();
    }
}
