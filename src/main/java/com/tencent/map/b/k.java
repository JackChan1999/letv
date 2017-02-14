package com.tencent.map.b;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.NetworkConstant;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProGuard */
public final class k {
    private static int a;
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static ArrayList<a> g;
    private static long h;
    private static long i;
    private static long j;
    private static long k;
    private static long l;
    private static long m;
    private static long n;
    private static long o;
    private static long p;
    private static long q;
    private static int r;
    private static int s;
    private static int t;
    private static int u;

    /* compiled from: ProGuard */
    public static class a {
        public long a;
        public long b;
        public long c;
        public long d;
        public int e;
        public long f;
        public int g;
        public int h;
    }

    static {
        a = 10000;
        b = LetvConstant.WIDGET_UPDATE_UI_TIME;
        c = 5000;
        d = 20000;
        e = 25000;
        f = LetvConstant.WIDGET_UPDATE_UI_TIME;
        a = 12000;
        b = 20000;
        c = 8000;
        d = 20000;
        e = 25000;
        f = LetvConstant.WIDGET_UPDATE_UI_TIME;
        ConnectivityManager connectivityManager = (ConnectivityManager) l.b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                int type = activeNetworkInfo.getType();
                if (activeNetworkInfo.isConnected() && type == 0) {
                    String subscriberId = ((TelephonyManager) l.b().getSystemService("phone")).getSubscriberId();
                    if (subscriberId != null && subscriberId.length() > 3 && !subscriberId.startsWith(NetworkConstant.NETWORK_NUMBER_46000) && !subscriberId.startsWith(NetworkConstant.NETWORK_NUMBER_46002)) {
                        a = LetvConstant.WIDGET_UPDATE_UI_TIME;
                        b = 25000;
                        c = 10000;
                        d = 25000;
                        e = 35000;
                        f = LetvConstant.WIDGET_UPDATE_UI_TIME;
                    }
                }
            }
        }
    }

    public static int a() {
        int i;
        int i2 = a;
        if (j <= 0 || k <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(m, j) + k) - l);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) l.b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = b;
                } else if (k > 0 && k < ((long) c)) {
                    i = c;
                }
            }
        }
        i2 = (u * c) + i;
        if (i2 <= c) {
            i2 = c;
        }
        if (((long) i2) <= k) {
            i2 = (int) (k + ((long) c));
        }
        if (i2 >= b) {
            i2 = b;
        }
        a b = b(Thread.currentThread().getId());
        if (b == null) {
            b = a(Thread.currentThread().getId());
        }
        if (i2 < b.g + c) {
            i2 = b.g + c;
        }
        b.g = i2;
        return i2;
    }

    public static int b() {
        int i;
        int i2 = d;
        if (n <= 0 || o <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(q, n) + o) - p);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) l.b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = e;
                } else if (o > 0 && o < ((long) f)) {
                    i = f;
                }
            }
        }
        i2 = (u * c) + i;
        if (i2 <= f) {
            i2 = f;
        }
        if (((long) i2) <= o) {
            i2 = (int) (o + ((long) f));
        }
        if (i2 >= e) {
            i2 = e;
        }
        a b = b(Thread.currentThread().getId());
        if (b != null) {
            if (i2 < b.h + f) {
                i2 = b.h + f;
            }
            if (i2 < b.g + f) {
                i2 = b.g + f;
            }
            b.h = i2;
        }
        return i2;
    }

    public static void a(boolean z) {
        if (!z) {
            u++;
        }
        a c = c(Thread.currentThread().getId());
        if (c != null) {
            long j = c.b;
        }
    }

    public static void a(HttpURLConnection httpURLConnection) {
        a b = b(Thread.currentThread().getId());
        if (b == null) {
            b = a(Thread.currentThread().getId());
        }
        if (b != null) {
            b.b = System.currentTimeMillis();
        }
    }

    public static void c() {
        a b = b(Thread.currentThread().getId());
        if (b != null) {
            b.c = System.currentTimeMillis() - b.b;
            b.b = System.currentTimeMillis();
            m = b.c;
            k = b.c > k ? b.c : k;
            long j = b.c < l ? b.c : l == 0 ? b.c : l;
            l = j;
            if (g != null) {
                synchronized (g) {
                    Iterator it = g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        a aVar = (a) it.next();
                        if (aVar.c > 0) {
                            j += aVar.c;
                            i++;
                        }
                    }
                    if (i > 0) {
                        j /= (long) i;
                    }
                }
            }
        }
    }

    public static void d() {
        a b = b(Thread.currentThread().getId());
        if (b != null) {
            long j;
            b.d = System.currentTimeMillis() - b.b;
            b.b = System.currentTimeMillis();
            q = b.d;
            if (b.d > o) {
                j = b.d;
            } else {
                j = o;
            }
            o = j;
            j = b.d < p ? b.d : p == 0 ? b.d : p;
            p = j;
            if (g != null) {
                synchronized (g) {
                    Iterator it = g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        a aVar = (a) it.next();
                        if (aVar.d > 0) {
                            n += aVar.d;
                            i++;
                        }
                    }
                    if (i > 0) {
                        n /= (long) i;
                    }
                }
            }
        }
    }

    public static void a(int i) {
        a b = b(Thread.currentThread().getId());
        if (b != null) {
            b.f = System.currentTimeMillis() - b.b;
            b.b = System.currentTimeMillis();
            b.e = i;
            int i2 = (int) (((long) (i * 1000)) / (b.f == 0 ? 1 : b.f));
            t = i2;
            r = i2 > r ? t : r;
            i2 = t < s ? t : s == 0 ? t : s;
            s = i2;
            if (g != null) {
                synchronized (g) {
                    Iterator it = g.iterator();
                    while (it.hasNext()) {
                        a aVar = (a) it.next();
                        int i3 = aVar.e;
                        long j = aVar.f;
                    }
                }
            }
            if (u > 0 && b.c < ((long) c) && b.d < ((long) f)) {
                u--;
            }
            b.g = (int) b.c;
        }
    }

    private static a a(long j) {
        a aVar;
        if (g == null) {
            g = new ArrayList();
        }
        synchronized (g) {
            if (g.size() > 20) {
                int size = g.size();
                int i = 0;
                Object obj = null;
                int i2 = 0;
                while (i < size / 2) {
                    Object obj2;
                    int i3;
                    if (((a) g.get(i2)).f > 0 || System.currentTimeMillis() - ((a) g.get(i2)).b > 600000) {
                        g.remove(i2);
                        obj2 = 1;
                        i3 = i2;
                    } else {
                        Object obj3 = obj;
                        i3 = i2 + 1;
                        obj2 = obj3;
                    }
                    i++;
                    i2 = i3;
                    obj = obj2;
                }
                if (obj != null) {
                    g.get(0);
                    h = 0;
                    g.get(0);
                    i = 0;
                    k = ((a) g.get(0)).c;
                    l = ((a) g.get(0)).c;
                    o = ((a) g.get(0)).d;
                    p = ((a) g.get(0)).d;
                    if (((a) g.get(0)).f > 0) {
                        r = (int) (((long) (((a) g.get(0)).e * 1000)) / ((a) g.get(0)).f);
                    }
                    s = r;
                    Iterator it = g.iterator();
                    while (it.hasNext()) {
                        aVar = (a) it.next();
                        if (0 > h) {
                            h = 0;
                        }
                        if (0 < i) {
                            i = 0;
                        }
                        if (aVar.c > k) {
                            k = aVar.c;
                        }
                        if (aVar.c < l) {
                            l = aVar.c;
                        }
                        if (aVar.d > o) {
                            o = aVar.d;
                        }
                        if (aVar.d < p) {
                            p = aVar.d;
                        }
                        if (aVar.f > 0) {
                            int i4 = (int) (((long) (aVar.e * 1000)) / aVar.f);
                            if (i4 > r) {
                                r = i4;
                            }
                            if (i4 < s) {
                                s = i4;
                            }
                        }
                    }
                }
            }
            aVar = new a();
            aVar.a = j;
            g.add(aVar);
        }
        return aVar;
    }

    private static a b(long j) {
        if (g == null) {
            return null;
        }
        synchronized (g) {
            Iterator it = g.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.a == j) {
                    return aVar;
                }
            }
            return null;
        }
    }

    private static a c(long j) {
        if (g != null) {
            synchronized (g) {
                for (int size = g.size() - 1; size >= 0; size--) {
                    if (((a) g.get(size)).a == j) {
                        a aVar = (a) g.remove(size);
                        return aVar;
                    }
                }
            }
        }
        return null;
    }
}
