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
import java.util.Map.Entry;

/* compiled from: Imprint */
public class bc implements Serializable, Cloneable, bz<bc, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("Imprint");
    private static final ct f = new ct("property", df.k, (short) 1);
    private static final ct g = new ct("version", (byte) 8, (short) 2);
    private static final ct h = new ct("checksum", (byte) 11, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    public Map<String, bd> a;
    public int b;
    public String c;
    private byte k;

    /* compiled from: Imprint */
    private static class a extends di<bc> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bc) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bc) bzVar);
        }

        public void a(cy cyVar, bc bcVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (bcVar.j()) {
                        bcVar.n();
                        return;
                    }
                    throw new cz("Required field 'version' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != df.k) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cv n = cyVar.n();
                        bcVar.a = new HashMap(n.c * 2);
                        for (int i = 0; i < n.c; i++) {
                            String z = cyVar.z();
                            bd bdVar = new bd();
                            bdVar.a(cyVar);
                            bcVar.a.put(z, bdVar);
                        }
                        cyVar.o();
                        bcVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bcVar.b = cyVar.w();
                        bcVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bcVar.c = cyVar.z();
                        bcVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bc bcVar) throws cf {
            bcVar.n();
            cyVar.a(bc.e);
            if (bcVar.a != null) {
                cyVar.a(bc.f);
                cyVar.a(new cv((byte) 11, (byte) 12, bcVar.a.size()));
                for (Entry entry : bcVar.a.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    ((bd) entry.getValue()).b(cyVar);
                }
                cyVar.e();
                cyVar.c();
            }
            cyVar.a(bc.g);
            cyVar.a(bcVar.b);
            cyVar.c();
            if (bcVar.c != null) {
                cyVar.a(bc.h);
                cyVar.a(bcVar.c);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Imprint */
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

    /* compiled from: Imprint */
    private static class c extends dj<bc> {
        private c() {
        }

        public void a(cy cyVar, bc bcVar) throws cf {
            cyVar = (de) cyVar;
            cyVar.a(bcVar.a.size());
            for (Entry entry : bcVar.a.entrySet()) {
                cyVar.a((String) entry.getKey());
                ((bd) entry.getValue()).b(cyVar);
            }
            cyVar.a(bcVar.b);
            cyVar.a(bcVar.c);
        }

        public void b(cy cyVar, bc bcVar) throws cf {
            cyVar = (de) cyVar;
            cv cvVar = new cv((byte) 11, (byte) 12, cyVar.w());
            bcVar.a = new HashMap(cvVar.c * 2);
            for (int i = 0; i < cvVar.c; i++) {
                String z = cyVar.z();
                bd bdVar = new bd();
                bdVar.a(cyVar);
                bcVar.a.put(z, bdVar);
            }
            bcVar.a(true);
            bcVar.b = cyVar.w();
            bcVar.b(true);
            bcVar.c = cyVar.z();
            bcVar.c(true);
        }
    }

    /* compiled from: Imprint */
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

    /* compiled from: Imprint */
    public enum e implements cg {
        PROPERTY((short) 1, "property"),
        VERSION((short) 2, "version"),
        CHECKSUM((short) 3, "checksum");
        
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
                    return PROPERTY;
                case 2:
                    return VERSION;
                case 3:
                    return CHECKSUM;
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
        return c(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.PROPERTY, new cl("property", (byte) 1, new co(df.k, new cm((byte) 11), new cq((byte) 12, bd.class))));
        enumMap.put(e.VERSION, new cl("version", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.CHECKSUM, new cl("checksum", (byte) 1, new cm((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(bc.class, d);
    }

    public bc() {
        this.k = (byte) 0;
    }

    public bc(Map<String, bd> map, int i, String str) {
        this();
        this.a = map;
        this.b = i;
        b(true);
        this.c = str;
    }

    public bc(bc bcVar) {
        this.k = (byte) 0;
        this.k = bcVar.k;
        if (bcVar.f()) {
            Map hashMap = new HashMap();
            for (Entry entry : bcVar.a.entrySet()) {
                hashMap.put((String) entry.getKey(), new bd((bd) entry.getValue()));
            }
            this.a = hashMap;
        }
        this.b = bcVar.b;
        if (bcVar.m()) {
            this.c = bcVar.c;
        }
    }

    public bc a() {
        return new bc(this);
    }

    public void b() {
        this.a = null;
        b(false);
        this.b = 0;
        this.c = null;
    }

    public int c() {
        return this.a == null ? 0 : this.a.size();
    }

    public void a(String str, bd bdVar) {
        if (this.a == null) {
            this.a = new HashMap();
        }
        this.a.put(str, bdVar);
    }

    public Map<String, bd> d() {
        return this.a;
    }

    public bc a(Map<String, bd> map) {
        this.a = map;
        return this;
    }

    public void e() {
        this.a = null;
    }

    public boolean f() {
        return this.a != null;
    }

    public void a(boolean z) {
        if (!z) {
            this.a = null;
        }
    }

    public int h() {
        return this.b;
    }

    public bc a(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public void i() {
        this.k = bw.b(this.k, 0);
    }

    public boolean j() {
        return bw.a(this.k, 0);
    }

    public void b(boolean z) {
        this.k = bw.a(this.k, 0, z);
    }

    public String k() {
        return this.c;
    }

    public bc a(String str) {
        this.c = str;
        return this;
    }

    public void l() {
        this.c = null;
    }

    public boolean m() {
        return this.c != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    public e c(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Imprint(");
        stringBuilder.append("property:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void n() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'property' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new cz("Required field 'checksum' was not present! Struct: " + toString());
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
