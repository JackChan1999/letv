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

/* compiled from: Latent */
public class bf implements Serializable, Cloneable, bz<bf, e> {
    public static final Map<e, cl> c;
    private static final dd d = new dd("Latent");
    private static final ct e = new ct("latency", (byte) 8, (short) 1);
    private static final ct f = new ct("interval", (byte) 10, (short) 2);
    private static final Map<Class<? extends dg>, dh> g = new HashMap();
    private static final int h = 0;
    private static final int i = 1;
    public int a;
    public long b;
    private byte j;

    /* compiled from: Latent */
    private static class a extends di<bf> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bf) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bf) bzVar);
        }

        public void a(cy cyVar, bf bfVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!bfVar.e()) {
                        throw new cz("Required field 'latency' was not found in serialized data! Struct: " + toString());
                    } else if (bfVar.i()) {
                        bfVar.j();
                        return;
                    } else {
                        throw new cz("Required field 'interval' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bfVar.a = cyVar.w();
                        bfVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bfVar.b = cyVar.x();
                        bfVar.b(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bf bfVar) throws cf {
            bfVar.j();
            cyVar.a(bf.d);
            cyVar.a(bf.e);
            cyVar.a(bfVar.a);
            cyVar.c();
            cyVar.a(bf.f);
            cyVar.a(bfVar.b);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Latent */
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

    /* compiled from: Latent */
    private static class c extends dj<bf> {
        private c() {
        }

        public void a(cy cyVar, bf bfVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(bfVar.a);
            deVar.a(bfVar.b);
        }

        public void b(cy cyVar, bf bfVar) throws cf {
            de deVar = (de) cyVar;
            bfVar.a = deVar.w();
            bfVar.a(true);
            bfVar.b = deVar.x();
            bfVar.b(true);
        }
    }

    /* compiled from: Latent */
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

    /* compiled from: Latent */
    public enum e implements cg {
        LATENCY((short) 1, "latency"),
        INTERVAL((short) 2, "interval");
        
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
                    return LATENCY;
                case 2:
                    return INTERVAL;
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
        return c(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        g.put(di.class, new b());
        g.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.LATENCY, new cl("latency", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.INTERVAL, new cl("interval", (byte) 1, new cm((byte) 10)));
        c = Collections.unmodifiableMap(enumMap);
        cl.a(bf.class, c);
    }

    public bf() {
        this.j = (byte) 0;
    }

    public bf(int i, long j) {
        this();
        this.a = i;
        a(true);
        this.b = j;
        b(true);
    }

    public bf(bf bfVar) {
        this.j = (byte) 0;
        this.j = bfVar.j;
        this.a = bfVar.a;
        this.b = bfVar.b;
    }

    public bf a() {
        return new bf(this);
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

    public bf a(int i) {
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

    public long f() {
        return this.b;
    }

    public bf a(long j) {
        this.b = j;
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

    public e c(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Latent(");
        stringBuilder.append("latency:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("interval:");
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
