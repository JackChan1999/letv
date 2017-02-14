package com.flurry.sdk;

import com.letv.core.constant.LetvConstant;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

public class ij extends jq {
    private static final String a = ij.class.getSimpleName();
    private String b;
    private a c;
    private int d = 10000;
    private int e = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private boolean f = true;
    private final hs<String, String> g = new hs();
    private c j;
    private HttpURLConnection k;
    private boolean l;
    private boolean m;
    private Exception n;
    private int o = -1;
    private final hs<String, String> p = new hs();
    private final Object q = new Object();

    public interface c {
        void a(ij ijVar);

        void a(ij ijVar, InputStream inputStream) throws Exception;

        void a(ij ijVar, OutputStream outputStream) throws Exception;
    }

    public enum a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public String toString() {
            switch (this) {
                case kPost:
                    return "POST";
                case kPut:
                    return HttpRequest.METHOD_PUT;
                case kDelete:
                    return HttpRequest.METHOD_DELETE;
                case kHead:
                    return HttpRequest.METHOD_HEAD;
                case kGet:
                    return "GET";
                default:
                    return null;
            }
        }
    }

    public static class b implements c {
        public void a(ij ijVar, OutputStream outputStream) throws Exception {
        }

        public void a(ij ijVar, InputStream inputStream) throws Exception {
        }

        public void a(ij ijVar) {
        }
    }

    public void a(String str) {
        this.b = str;
    }

    public String b() {
        return this.b;
    }

    public void a(a aVar) {
        this.c = aVar;
    }

    public void a(boolean z) {
        this.f = z;
    }

    public void a(String str, String str2) {
        this.g.a((Object) str, (Object) str2);
    }

    public void a(c cVar) {
        this.j = cVar;
    }

    public boolean c() {
        boolean z;
        synchronized (this.q) {
            z = this.m;
        }
        return z;
    }

    public boolean d() {
        return !g() && e();
    }

    public boolean e() {
        return this.o >= 200 && this.o < 400;
    }

    public int f() {
        return this.o;
    }

    public boolean g() {
        return this.n != null;
    }

    public List<String> b(String str) {
        if (str == null) {
            return null;
        }
        return this.p.a((Object) str);
    }

    public void h() {
        synchronized (this.q) {
            this.m = true;
        }
        q();
    }

    public void a() {
        try {
            if (this.b != null) {
                if (hh.a().c()) {
                    if (this.c == null || a.kUnknown.equals(this.c)) {
                        this.c = a.kGet;
                    }
                    n();
                    ib.a(4, a, "HTTP status: " + this.o + " for url: " + this.b);
                    o();
                    return;
                }
                ib.a(3, a, "Network not available, aborting http request: " + this.b);
                o();
            }
        } catch (Throwable e) {
            ib.a(4, a, "HTTP status: " + this.o + " for url: " + this.b);
            ib.a(3, a, "Exception during http request: " + this.b, e);
            this.n = e;
        } finally {
            o();
        }
    }

    public void i() {
        h();
    }

    private void n() throws Exception {
        Closeable outputStream;
        Closeable bufferedOutputStream;
        Throwable th;
        Closeable closeable = null;
        if (!this.m) {
            this.b = jn.a(this.b);
            this.k = (HttpURLConnection) new URL(this.b).openConnection();
            this.k.setConnectTimeout(this.d);
            this.k.setReadTimeout(this.e);
            this.k.setRequestMethod(this.c.toString());
            this.k.setInstanceFollowRedirects(this.f);
            this.k.setDoOutput(a.kPost.equals(this.c));
            this.k.setDoInput(true);
            for (Entry entry : this.g.b()) {
                this.k.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(a.kGet.equals(this.c) || a.kPost.equals(this.c))) {
                this.k.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "");
            }
            if (this.m) {
                p();
                return;
            }
            if (a.kPost.equals(this.c)) {
                try {
                    outputStream = this.k.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                        try {
                            a((OutputStream) bufferedOutputStream);
                            jn.a(bufferedOutputStream);
                            jn.a(outputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            closeable = outputStream;
                            jn.a(bufferedOutputStream);
                            jn.a(closeable);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = null;
                        closeable = outputStream;
                        jn.a(bufferedOutputStream);
                        jn.a(closeable);
                        throw th;
                    }
                } catch (Throwable th4) {
                    p();
                }
            }
            this.o = this.k.getResponseCode();
            for (Entry entry2 : this.k.getHeaderFields().entrySet()) {
                for (Object a : (List) entry2.getValue()) {
                    this.p.a(entry2.getKey(), a);
                }
            }
            if (!a.kGet.equals(this.c) && !a.kPost.equals(this.c)) {
                p();
            } else if (this.m) {
                p();
            } else {
                try {
                    outputStream = this.k.getInputStream();
                    try {
                        bufferedOutputStream = new BufferedInputStream(outputStream);
                    } catch (Throwable th5) {
                        th = th5;
                        bufferedOutputStream = outputStream;
                        jn.a(closeable);
                        jn.a(bufferedOutputStream);
                        throw th;
                    }
                    try {
                        a((InputStream) bufferedOutputStream);
                        jn.a(bufferedOutputStream);
                        jn.a(outputStream);
                        p();
                    } catch (Throwable th6) {
                        th = th6;
                        closeable = bufferedOutputStream;
                        bufferedOutputStream = outputStream;
                        jn.a(closeable);
                        jn.a(bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    bufferedOutputStream = null;
                    jn.a(closeable);
                    jn.a(bufferedOutputStream);
                    throw th;
                }
            }
        }
    }

    private void a(OutputStream outputStream) throws Exception {
        if (this.j != null && !c() && outputStream != null) {
            this.j.a(this, outputStream);
        }
    }

    private void a(InputStream inputStream) throws Exception {
        if (this.j != null && !c() && inputStream != null) {
            this.j.a(this, inputStream);
        }
    }

    private void o() {
        if (this.j != null && !c()) {
            this.j.a(this);
        }
    }

    private void p() {
        if (!this.l) {
            this.l = true;
            if (this.k != null) {
                this.k.disconnect();
            }
        }
    }

    private void q() {
        if (!this.l) {
            this.l = true;
            if (this.k != null) {
                new Thread(this) {
                    final /* synthetic */ ij a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        try {
                            if (this.a.k != null) {
                                this.a.k.disconnect();
                            }
                        } catch (Throwable th) {
                        }
                    }
                }.start();
            }
        }
    }
}
