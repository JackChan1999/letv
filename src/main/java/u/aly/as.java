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

/* compiled from: ControlPolicy */
public class as implements Serializable, Cloneable, bz<as, e> {
    public static final Map<e, cl> b;
    private static final dd c = new dd("ControlPolicy");
    private static final ct d = new ct("latent", (byte) 12, (short) 1);
    private static final Map<Class<? extends dg>, dh> e = new HashMap();
    public bf a;
    private e[] f;

    /* compiled from: ControlPolicy */
    private static class a extends di<as> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (as) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (as) bzVar);
        }

        public void a(cy cyVar, as asVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    asVar.f();
                    return;
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        asVar.a = new bf();
                        asVar.a.a(cyVar);
                        asVar.a(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, as asVar) throws cf {
            asVar.f();
            cyVar.a(as.c);
            if (asVar.a != null && asVar.e()) {
                cyVar.a(as.d);
                asVar.a.b(cyVar);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: ControlPolicy */
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

    /* compiled from: ControlPolicy */
    private static class c extends dj<as> {
        private c() {
        }

        public void a(cy cyVar, as asVar) throws cf {
            cyVar = (de) cyVar;
            BitSet bitSet = new BitSet();
            if (asVar.e()) {
                bitSet.set(0);
            }
            cyVar.a(bitSet, 1);
            if (asVar.e()) {
                asVar.a.b(cyVar);
            }
        }

        public void b(cy cyVar, as asVar) throws cf {
            cyVar = (de) cyVar;
            if (cyVar.b(1).get(0)) {
                asVar.a = new bf();
                asVar.a.a(cyVar);
                asVar.a(true);
            }
        }
    }

    /* compiled from: ControlPolicy */
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

    /* compiled from: ControlPolicy */
    public enum e implements cg {
        LATENT((short) 1, "latent");
        
        private static final Map<String, e> b = null;
        private final short c;
        private final String d;

        static {
            b = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                b.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return LATENT;
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
            return (e) b.get(str);
        }

        private e(short s, String str) {
            this.c = s;
            this.d = str;
        }

        public short a() {
            return this.c;
        }

        public String b() {
            return this.d;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        e.put(di.class, new b());
        e.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.LATENT, new cl("latent", (byte) 2, new cq((byte) 12, bf.class)));
        b = Collections.unmodifiableMap(enumMap);
        cl.a(as.class, b);
    }

    public as() {
        this.f = new e[]{e.LATENT};
    }

    public as(as asVar) {
        this.f = new e[]{e.LATENT};
        if (asVar.e()) {
            this.a = new bf(asVar.a);
        }
    }

    public as a() {
        return new as(this);
    }

    public void b() {
        this.a = null;
    }

    public bf c() {
        return this.a;
    }

    public as a(bf bfVar) {
        this.a = bfVar;
        return this;
    }

    public void d() {
        this.a = null;
    }

    public boolean e() {
        return this.a != null;
    }

    public void a(boolean z) {
        if (!z) {
            this.a = null;
        }
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) e.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) e.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ControlPolicy(");
        if (e()) {
            stringBuilder.append("latent:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void f() throws cf {
        if (this.a != null) {
            this.a.j();
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
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
