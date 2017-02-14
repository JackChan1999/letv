package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: Traffic */
public class bo implements Serializable, Cloneable, bz<bo, e> {
    public static final Map<e, cl> c;
    private static final dd d = new dd("Traffic");
    private static final ct e = new ct("upload_traffic", (byte) 8, (short) 1);
    private static final ct f = new ct("download_traffic", (byte) 8, (short) 2);
    private static final Map<Class<? extends dg>, dh> g = new HashMap();
    private static final int h = 0;
    private static final int i = 1;
    public int a;
    public int b;
    private byte j;

    /* compiled from: Traffic */
    private static class a extends di<bo> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bo) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bo) bzVar);
        }

        public void a(cy cyVar, bo boVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!boVar.e()) {
                        throw new cz("Required field 'upload_traffic' was not found in serialized data! Struct: " + toString());
                    } else if (boVar.i()) {
                        boVar.j();
                        return;
                    } else {
                        throw new cz("Required field 'download_traffic' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        boVar.a = cyVar.w();
                        boVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        boVar.b = cyVar.w();
                        boVar.b(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bo boVar) throws cf {
            boVar.j();
            cyVar.a(bo.d);
            cyVar.a(bo.e);
            cyVar.a(boVar.a);
            cyVar.c();
            cyVar.a(bo.f);
            cyVar.a(boVar.b);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Traffic */
    private static class b implements dh {
        private b() {
        }

        public /* synthetic */ dg b() {
            return a();
        }

        public a a() {
            return new a();
        }
    }

    /* compiled from: Traffic */
    private static class c extends dj<bo> {
        private c() {
        }

        public void a(cy cyVar, bo boVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(boVar.a);
            deVar.a(boVar.b);
        }

        public void b(cy cyVar, bo boVar) throws cf {
            de deVar = (de) cyVar;
            boVar.a = deVar.w();
            boVar.a(true);
            boVar.b = deVar.w();
            boVar.b(true);
        }
    }

    /* compiled from: Traffic */
    private static class d implements dh {
        private d() {
        }

        public /* synthetic */ dg b() {
            return a();
        }

        public c a() {
            return new c();
        }
    }

    /* compiled from: Traffic */
    public enum e implements cg {
        UPLOAD_TRAFFIC((short) 1, "upload_traffic"),
        DOWNLOAD_TRAFFIC((short) 2, "download_traffic");
        
        private static final Map<String, e> c = null;
        private final short d;
        private final String e;

        static {
            c = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                c.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return UPLOAD_TRAFFIC;
                case 2:
                    return DOWNLOAD_TRAFFIC;
                default:
                    return null;
            }
        }

        public static e b(int i) {
            e a = a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static e a(String str) {
            return (e) c.get(str);
        }

        private e(short s, String str) {
            this.d = s;
            this.e = str;
        }

        public short a() {
            return this.d;
        }

        public String b() {
            return this.e;
        }
    }

    public /* synthetic */ cg b(int i) {
        return d(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        g.put(di.class, new b());
        g.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.UPLOAD_TRAFFIC, new cl("upload_traffic", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.DOWNLOAD_TRAFFIC, new cl("download_traffic", (byte) 1, new cm((byte) 8)));
        c = Collections.unmodifiableMap(enumMap);
        cl.a(bo.class, c);
    }

    public bo() {
        this.j = (byte) 0;
    }

    public bo(int i, int i2) {
        this();
        this.a = i;
        a(true);
        this.b = i2;
        b(true);
    }

    public bo(bo boVar) {
        this.j = (byte) 0;
        this.j = boVar.j;
        this.a = boVar.a;
        this.b = boVar.b;
    }

    public bo a() {
        return new bo(this);
    }

    public void b() {
        a(false);
        this.a = 0;
        b(false);
        this.b = 0;
    }

    public int c() {
        return this.a;
    }

    public bo a(int i) {
        this.a = i;
        a(true);
        return this;
    }

    public void d() {
        this.j = bw.b(this.j, 0);
    }

    public boolean e() {
        return bw.a(this.j, 0);
    }

    public void a(boolean z) {
        this.j = bw.a(this.j, 0, z);
    }

    public int f() {
        return this.b;
    }

    public bo c(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public void h() {
        this.j = bw.b(this.j, 1);
    }

    public boolean i() {
        return bw.a(this.j, 1);
    }

    public void b(boolean z) {
        this.j = bw.a(this.j, 1, z);
    }

    public e d(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Traffic(");
        stringBuilder.append("upload_traffic:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("download_traffic:");
        stringBuilder.append(this.b);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void j() throws cf {
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            b(new cs(new dk((OutputStream) objectOutputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.j = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
