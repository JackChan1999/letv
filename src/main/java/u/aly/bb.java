package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IdTracking */
public class bb implements Serializable, Cloneable, bz<bb, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("IdTracking");
    private static final ct f = new ct("snapshots", df.k, (short) 1);
    private static final ct g = new ct("journals", df.m, (short) 2);
    private static final ct h = new ct("checksum", (byte) 11, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    public Map<String, ba> a;
    public List<az> b;
    public String c;
    private e[] j;

    /* compiled from: IdTracking */
    private static class a extends di<bb> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bb) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bb) bzVar);
        }

        public void a(cy cyVar, bb bbVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    bbVar.p();
                    return;
                }
                int i;
                switch (l.c) {
                    case (short) 1:
                        if (l.b != df.k) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cv n = cyVar.n();
                        bbVar.a = new HashMap(n.c * 2);
                        for (i = 0; i < n.c; i++) {
                            String z = cyVar.z();
                            ba baVar = new ba();
                            baVar.a(cyVar);
                            bbVar.a.put(z, baVar);
                        }
                        cyVar.o();
                        bbVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != df.m) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cu p = cyVar.p();
                        bbVar.b = new ArrayList(p.b);
                        for (i = 0; i < p.b; i++) {
                            az azVar = new az();
                            azVar.a(cyVar);
                            bbVar.b.add(azVar);
                        }
                        cyVar.q();
                        bbVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bbVar.c = cyVar.z();
                        bbVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bb bbVar) throws cf {
            bbVar.p();
            cyVar.a(bb.e);
            if (bbVar.a != null) {
                cyVar.a(bb.f);
                cyVar.a(new cv((byte) 11, (byte) 12, bbVar.a.size()));
                for (Entry entry : bbVar.a.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    ((ba) entry.getValue()).b(cyVar);
                }
                cyVar.e();
                cyVar.c();
            }
            if (bbVar.b != null && bbVar.l()) {
                cyVar.a(bb.g);
                cyVar.a(new cu((byte) 12, bbVar.b.size()));
                for (az b : bbVar.b) {
                    b.b(cyVar);
                }
                cyVar.f();
                cyVar.c();
            }
            if (bbVar.c != null && bbVar.o()) {
                cyVar.a(bb.h);
                cyVar.a(bbVar.c);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: IdTracking */
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

    /* compiled from: IdTracking */
    private static class c extends dj<bb> {
        private c() {
        }

        public void a(cy cyVar, bb bbVar) throws cf {
            cyVar = (de) cyVar;
            cyVar.a(bbVar.a.size());
            for (Entry entry : bbVar.a.entrySet()) {
                cyVar.a((String) entry.getKey());
                ((ba) entry.getValue()).b(cyVar);
            }
            BitSet bitSet = new BitSet();
            if (bbVar.l()) {
                bitSet.set(0);
            }
            if (bbVar.o()) {
                bitSet.set(1);
            }
            cyVar.a(bitSet, 2);
            if (bbVar.l()) {
                cyVar.a(bbVar.b.size());
                for (az b : bbVar.b) {
                    b.b(cyVar);
                }
            }
            if (bbVar.o()) {
                cyVar.a(bbVar.c);
            }
        }

        public void b(cy cyVar, bb bbVar) throws cf {
            int i = 0;
            cyVar = (de) cyVar;
            cv cvVar = new cv((byte) 11, (byte) 12, cyVar.w());
            bbVar.a = new HashMap(cvVar.c * 2);
            for (int i2 = 0; i2 < cvVar.c; i2++) {
                String z = cyVar.z();
                ba baVar = new ba();
                baVar.a(cyVar);
                bbVar.a.put(z, baVar);
            }
            bbVar.a(true);
            BitSet b = cyVar.b(2);
            if (b.get(0)) {
                cu cuVar = new cu((byte) 12, cyVar.w());
                bbVar.b = new ArrayList(cuVar.b);
                while (i < cuVar.b) {
                    az azVar = new az();
                    azVar.a(cyVar);
                    bbVar.b.add(azVar);
                    i++;
                }
                bbVar.b(true);
            }
            if (b.get(1)) {
                bbVar.c = cyVar.z();
                bbVar.c(true);
            }
        }
    }

    /* compiled from: IdTracking */
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

    /* compiled from: IdTracking */
    public enum e implements cg {
        SNAPSHOTS((short) 1, "snapshots"),
        JOURNALS((short) 2, "journals"),
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
                    return SNAPSHOTS;
                case 2:
                    return JOURNALS;
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
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.SNAPSHOTS, new cl("snapshots", (byte) 1, new co(df.k, new cm((byte) 11), new cq((byte) 12, ba.class))));
        enumMap.put(e.JOURNALS, new cl("journals", (byte) 2, new cn(df.m, new cq((byte) 12, az.class))));
        enumMap.put(e.CHECKSUM, new cl("checksum", (byte) 2, new cm((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(bb.class, d);
    }

    public bb() {
        this.j = new e[]{e.JOURNALS, e.CHECKSUM};
    }

    public bb(Map<String, ba> map) {
        this();
        this.a = map;
    }

    public bb(bb bbVar) {
        this.j = new e[]{e.JOURNALS, e.CHECKSUM};
        if (bbVar.f()) {
            Map hashMap = new HashMap();
            for (Entry entry : bbVar.a.entrySet()) {
                hashMap.put((String) entry.getKey(), new ba((ba) entry.getValue()));
            }
            this.a = hashMap;
        }
        if (bbVar.l()) {
            List arrayList = new ArrayList();
            for (az azVar : bbVar.b) {
                arrayList.add(new az(azVar));
            }
            this.b = arrayList;
        }
        if (bbVar.o()) {
            this.c = bbVar.c;
        }
    }

    public bb a() {
        return new bb(this);
    }

    public void b() {
        this.a = null;
        this.b = null;
        this.c = null;
    }

    public int c() {
        return this.a == null ? 0 : this.a.size();
    }

    public void a(String str, ba baVar) {
        if (this.a == null) {
            this.a = new HashMap();
        }
        this.a.put(str, baVar);
    }

    public Map<String, ba> d() {
        return this.a;
    }

    public bb a(Map<String, ba> map) {
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
        return this.b == null ? 0 : this.b.size();
    }

    public Iterator<az> i() {
        return this.b == null ? null : this.b.iterator();
    }

    public void a(az azVar) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        this.b.add(azVar);
    }

    public List<az> j() {
        return this.b;
    }

    public bb a(List<az> list) {
        this.b = list;
        return this;
    }

    public void k() {
        this.b = null;
    }

    public boolean l() {
        return this.b != null;
    }

    public void b(boolean z) {
        if (!z) {
            this.b = null;
        }
    }

    public String m() {
        return this.c;
    }

    public bb a(String str) {
        this.c = str;
        return this;
    }

    public void n() {
        this.c = null;
    }

    public boolean o() {
        return this.c != null;
    }

    public void c(boolean z) {
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
        StringBuilder stringBuilder = new StringBuilder("IdTracking(");
        stringBuilder.append("snapshots:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("journals:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        }
        if (o()) {
            stringBuilder.append(", ");
            stringBuilder.append("checksum:");
            if (this.c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.c);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void p() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'snapshots' was not present! Struct: " + toString());
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
