package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: Error */
public class av implements Serializable, Cloneable, bz<av, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("Error");
    private static final ct f = new ct("ts", (byte) 10, (short) 1);
    private static final ct g = new ct("context", (byte) 11, (short) 2);
    private static final ct h = new ct("source", (byte) 8, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    public long a;
    public String b;
    public aw c;
    private byte k;
    private e[] l;

    /* compiled from: Error */
    private static class a extends di<av> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (av) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (av) bzVar);
        }

        public void a(cy cyVar, av avVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (avVar.e()) {
                        avVar.m();
                        return;
                    }
                    throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        avVar.a = cyVar.x();
                        avVar.b(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        avVar.b = cyVar.z();
                        avVar.c(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        avVar.c = aw.a(cyVar.w());
                        avVar.d(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, av avVar) throws cf {
            avVar.m();
            cyVar.a(av.e);
            cyVar.a(av.f);
            cyVar.a(avVar.a);
            cyVar.c();
            if (avVar.b != null) {
                cyVar.a(av.g);
                cyVar.a(avVar.b);
                cyVar.c();
            }
            if (avVar.c != null && avVar.l()) {
                cyVar.a(av.h);
                cyVar.a(avVar.c.a());
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Error */
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

    /* compiled from: Error */
    private static class c extends dj<av> {
        private c() {
        }

        public void a(cy cyVar, av avVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(avVar.a);
            deVar.a(avVar.b);
            BitSet bitSet = new BitSet();
            if (avVar.l()) {
                bitSet.set(0);
            }
            deVar.a(bitSet, 1);
            if (avVar.l()) {
                deVar.a(avVar.c.a());
            }
        }

        public void b(cy cyVar, av avVar) throws cf {
            de deVar = (de) cyVar;
            avVar.a = deVar.x();
            avVar.b(true);
            avVar.b = deVar.z();
            avVar.c(true);
            if (deVar.b(1).get(0)) {
                avVar.c = aw.a(deVar.w());
                avVar.d(true);
            }
        }
    }

    /* compiled from: Error */
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

    /* compiled from: Error */
    public enum e implements cg {
        TS((short) 1, "ts"),
        CONTEXT((short) 2, "context"),
        SOURCE((short) 3, "source");
        
        private static final Map<String, e> d = null;
        private final short e;
        private final String f;

        static {
            d = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                d.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return TS;
                case 2:
                    return CONTEXT;
                case 3:
                    return SOURCE;
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
            return (e) d.get(str);
        }

        private e(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public short a() {
            return this.e;
        }

        public String b() {
            return this.f;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.CONTEXT, new cl("context", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.SOURCE, new cl("source", (byte) 2, new ck(df.n, aw.class)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(av.class, d);
    }

    public av() {
        this.k = (byte) 0;
        this.l = new e[]{e.SOURCE};
    }

    public av(long j, String str) {
        this();
        this.a = j;
        b(true);
        this.b = str;
    }

    public av(av avVar) {
        this.k = (byte) 0;
        this.l = new e[]{e.SOURCE};
        this.k = avVar.k;
        this.a = avVar.a;
        if (avVar.i()) {
            this.b = avVar.b;
        }
        if (avVar.l()) {
            this.c = avVar.c;
        }
    }

    public av a() {
        return new av(this);
    }

    public void b() {
        b(false);
        this.a = 0;
        this.b = null;
        this.c = null;
    }

    public long c() {
        return this.a;
    }

    public av a(long j) {
        this.a = j;
        b(true);
        return this;
    }

    public void d() {
        this.k = bw.b(this.k, 0);
    }

    public boolean e() {
        return bw.a(this.k, 0);
    }

    public void b(boolean z) {
        this.k = bw.a(this.k, 0, z);
    }

    public String f() {
        return this.b;
    }

    public av a(String str) {
        this.b = str;
        return this;
    }

    public void h() {
        this.b = null;
    }

    public boolean i() {
        return this.b != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.b = null;
        }
    }

    public aw j() {
        return this.c;
    }

    public av a(aw awVar) {
        this.c = awVar;
        return this;
    }

    public void k() {
        this.c = null;
    }

    public boolean l() {
        return this.c != null;
    }

    public void d(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Error(");
        stringBuilder.append("ts:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("context:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("source:");
            if (this.c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.c);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void m() throws cf {
        if (this.b == null) {
            throw new cz("Required field 'context' was not present! Struct: " + toString());
        }
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
            this.k = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
