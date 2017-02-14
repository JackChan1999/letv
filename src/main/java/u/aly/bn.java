package u.aly;

import com.letv.download.db.Download.DownloadVideoTable;
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

/* compiled from: Session */
public class bn implements Serializable, Cloneable, bz<bn, e> {
    public static final Map<e, cl> h;
    private static final dd i = new dd("Session");
    private static final ct j = new ct("id", (byte) 11, (short) 1);
    private static final ct k = new ct("start_time", (byte) 10, (short) 2);
    private static final ct l = new ct("end_time", (byte) 10, (short) 3);
    private static final ct m = new ct(DownloadVideoTable.COLUMN_DURATION, (byte) 10, (short) 4);
    private static final ct n = new ct("pages", df.m, (short) 5);
    private static final ct o = new ct("locations", df.m, (short) 6);
    private static final ct p = new ct("traffic", (byte) 12, (short) 7);
    private static final Map<Class<? extends dg>, dh> q = new HashMap();
    private static final int r = 0;
    private static final int s = 1;
    private static final int t = 2;
    public String a;
    public long b;
    public long c;
    public long d;
    public List<bi> e;
    public List<bg> f;
    public bo g;
    private byte u;
    private e[] v;

    /* compiled from: Session */
    private static class a extends di<bn> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bn) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bn) bzVar);
        }

        public void a(cy cyVar, bn bnVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!bnVar.i()) {
                        throw new cz("Required field 'start_time' was not found in serialized data! Struct: " + toString());
                    } else if (!bnVar.l()) {
                        throw new cz("Required field 'end_time' was not found in serialized data! Struct: " + toString());
                    } else if (bnVar.o()) {
                        bnVar.C();
                        return;
                    } else {
                        throw new cz("Required field 'duration' was not found in serialized data! Struct: " + toString());
                    }
                }
                cu p;
                int i;
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bnVar.a = cyVar.z();
                        bnVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bnVar.b = cyVar.x();
                        bnVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bnVar.c = cyVar.x();
                        bnVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bnVar.d = cyVar.x();
                        bnVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != df.m) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        p = cyVar.p();
                        bnVar.e = new ArrayList(p.b);
                        for (i = 0; i < p.b; i++) {
                            bi biVar = new bi();
                            biVar.a(cyVar);
                            bnVar.e.add(biVar);
                        }
                        cyVar.q();
                        bnVar.e(true);
                        break;
                    case (short) 6:
                        if (l.b != df.m) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        p = cyVar.p();
                        bnVar.f = new ArrayList(p.b);
                        for (i = 0; i < p.b; i++) {
                            bg bgVar = new bg();
                            bgVar.a(cyVar);
                            bnVar.f.add(bgVar);
                        }
                        cyVar.q();
                        bnVar.f(true);
                        break;
                    case (short) 7:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bnVar.g = new bo();
                        bnVar.g.a(cyVar);
                        bnVar.g(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bn bnVar) throws cf {
            bnVar.C();
            cyVar.a(bn.i);
            if (bnVar.a != null) {
                cyVar.a(bn.j);
                cyVar.a(bnVar.a);
                cyVar.c();
            }
            cyVar.a(bn.k);
            cyVar.a(bnVar.b);
            cyVar.c();
            cyVar.a(bn.l);
            cyVar.a(bnVar.c);
            cyVar.c();
            cyVar.a(bn.m);
            cyVar.a(bnVar.d);
            cyVar.c();
            if (bnVar.e != null && bnVar.t()) {
                cyVar.a(bn.n);
                cyVar.a(new cu((byte) 12, bnVar.e.size()));
                for (bi b : bnVar.e) {
                    b.b(cyVar);
                }
                cyVar.f();
                cyVar.c();
            }
            if (bnVar.f != null && bnVar.y()) {
                cyVar.a(bn.o);
                cyVar.a(new cu((byte) 12, bnVar.f.size()));
                for (bg b2 : bnVar.f) {
                    b2.b(cyVar);
                }
                cyVar.f();
                cyVar.c();
            }
            if (bnVar.g != null && bnVar.B()) {
                cyVar.a(bn.p);
                bnVar.g.b(cyVar);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Session */
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

    /* compiled from: Session */
    private static class c extends dj<bn> {
        private c() {
        }

        public void a(cy cyVar, bn bnVar) throws cf {
            cyVar = (de) cyVar;
            cyVar.a(bnVar.a);
            cyVar.a(bnVar.b);
            cyVar.a(bnVar.c);
            cyVar.a(bnVar.d);
            BitSet bitSet = new BitSet();
            if (bnVar.t()) {
                bitSet.set(0);
            }
            if (bnVar.y()) {
                bitSet.set(1);
            }
            if (bnVar.B()) {
                bitSet.set(2);
            }
            cyVar.a(bitSet, 3);
            if (bnVar.t()) {
                cyVar.a(bnVar.e.size());
                for (bi b : bnVar.e) {
                    b.b(cyVar);
                }
            }
            if (bnVar.y()) {
                cyVar.a(bnVar.f.size());
                for (bg b2 : bnVar.f) {
                    b2.b(cyVar);
                }
            }
            if (bnVar.B()) {
                bnVar.g.b(cyVar);
            }
        }

        public void b(cy cyVar, bn bnVar) throws cf {
            int i = 0;
            cyVar = (de) cyVar;
            bnVar.a = cyVar.z();
            bnVar.a(true);
            bnVar.b = cyVar.x();
            bnVar.b(true);
            bnVar.c = cyVar.x();
            bnVar.c(true);
            bnVar.d = cyVar.x();
            bnVar.d(true);
            BitSet b = cyVar.b(3);
            if (b.get(0)) {
                cu cuVar = new cu((byte) 12, cyVar.w());
                bnVar.e = new ArrayList(cuVar.b);
                for (int i2 = 0; i2 < cuVar.b; i2++) {
                    bi biVar = new bi();
                    biVar.a(cyVar);
                    bnVar.e.add(biVar);
                }
                bnVar.e(true);
            }
            if (b.get(1)) {
                cu cuVar2 = new cu((byte) 12, cyVar.w());
                bnVar.f = new ArrayList(cuVar2.b);
                while (i < cuVar2.b) {
                    bg bgVar = new bg();
                    bgVar.a(cyVar);
                    bnVar.f.add(bgVar);
                    i++;
                }
                bnVar.f(true);
            }
            if (b.get(2)) {
                bnVar.g = new bo();
                bnVar.g.a(cyVar);
                bnVar.g(true);
            }
        }
    }

    /* compiled from: Session */
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

    /* compiled from: Session */
    public enum e implements cg {
        ID((short) 1, "id"),
        START_TIME((short) 2, "start_time"),
        END_TIME((short) 3, "end_time"),
        DURATION((short) 4, DownloadVideoTable.COLUMN_DURATION),
        PAGES((short) 5, "pages"),
        LOCATIONS((short) 6, "locations"),
        TRAFFIC((short) 7, "traffic");
        
        private static final Map<String, e> h = null;
        private final short i;
        private final String j;

        static {
            h = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                h.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return ID;
                case 2:
                    return START_TIME;
                case 3:
                    return END_TIME;
                case 4:
                    return DURATION;
                case 5:
                    return PAGES;
                case 6:
                    return LOCATIONS;
                case 7:
                    return TRAFFIC;
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
            return (e) h.get(str);
        }

        private e(short s, String str) {
            this.i = s;
            this.j = str;
        }

        public short a() {
            return this.i;
        }

        public String b() {
            return this.j;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        q.put(di.class, new b());
        q.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.ID, new cl("id", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.START_TIME, new cl("start_time", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.END_TIME, new cl("end_time", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.DURATION, new cl(DownloadVideoTable.COLUMN_DURATION, (byte) 1, new cm((byte) 10)));
        enumMap.put(e.PAGES, new cl("pages", (byte) 2, new cn(df.m, new cq((byte) 12, bi.class))));
        enumMap.put(e.LOCATIONS, new cl("locations", (byte) 2, new cn(df.m, new cq((byte) 12, bg.class))));
        enumMap.put(e.TRAFFIC, new cl("traffic", (byte) 2, new cq((byte) 12, bo.class)));
        h = Collections.unmodifiableMap(enumMap);
        cl.a(bn.class, h);
    }

    public bn() {
        this.u = (byte) 0;
        this.v = new e[]{e.PAGES, e.LOCATIONS, e.TRAFFIC};
    }

    public bn(String str, long j, long j2, long j3) {
        this();
        this.a = str;
        this.b = j;
        b(true);
        this.c = j2;
        c(true);
        this.d = j3;
        d(true);
    }

    public bn(bn bnVar) {
        List arrayList;
        this.u = (byte) 0;
        this.v = new e[]{e.PAGES, e.LOCATIONS, e.TRAFFIC};
        this.u = bnVar.u;
        if (bnVar.e()) {
            this.a = bnVar.a;
        }
        this.b = bnVar.b;
        this.c = bnVar.c;
        this.d = bnVar.d;
        if (bnVar.t()) {
            arrayList = new ArrayList();
            for (bi biVar : bnVar.e) {
                arrayList.add(new bi(biVar));
            }
            this.e = arrayList;
        }
        if (bnVar.y()) {
            arrayList = new ArrayList();
            for (bg bgVar : bnVar.f) {
                arrayList.add(new bg(bgVar));
            }
            this.f = arrayList;
        }
        if (bnVar.B()) {
            this.g = new bo(bnVar.g);
        }
    }

    public bn a() {
        return new bn(this);
    }

    public void b() {
        this.a = null;
        b(false);
        this.b = 0;
        c(false);
        this.c = 0;
        d(false);
        this.d = 0;
        this.e = null;
        this.f = null;
        this.g = null;
    }

    public String c() {
        return this.a;
    }

    public bn a(String str) {
        this.a = str;
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

    public long f() {
        return this.b;
    }

    public bn a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void h() {
        this.u = bw.b(this.u, 0);
    }

    public boolean i() {
        return bw.a(this.u, 0);
    }

    public void b(boolean z) {
        this.u = bw.a(this.u, 0, z);
    }

    public long j() {
        return this.c;
    }

    public bn b(long j) {
        this.c = j;
        c(true);
        return this;
    }

    public void k() {
        this.u = bw.b(this.u, 1);
    }

    public boolean l() {
        return bw.a(this.u, 1);
    }

    public void c(boolean z) {
        this.u = bw.a(this.u, 1, z);
    }

    public long m() {
        return this.d;
    }

    public bn c(long j) {
        this.d = j;
        d(true);
        return this;
    }

    public void n() {
        this.u = bw.b(this.u, 2);
    }

    public boolean o() {
        return bw.a(this.u, 2);
    }

    public void d(boolean z) {
        this.u = bw.a(this.u, 2, z);
    }

    public int p() {
        return this.e == null ? 0 : this.e.size();
    }

    public Iterator<bi> q() {
        return this.e == null ? null : this.e.iterator();
    }

    public void a(bi biVar) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(biVar);
    }

    public List<bi> r() {
        return this.e;
    }

    public bn a(List<bi> list) {
        this.e = list;
        return this;
    }

    public void s() {
        this.e = null;
    }

    public boolean t() {
        return this.e != null;
    }

    public void e(boolean z) {
        if (!z) {
            this.e = null;
        }
    }

    public int u() {
        return this.f == null ? 0 : this.f.size();
    }

    public Iterator<bg> v() {
        return this.f == null ? null : this.f.iterator();
    }

    public void a(bg bgVar) {
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(bgVar);
    }

    public List<bg> w() {
        return this.f;
    }

    public bn b(List<bg> list) {
        this.f = list;
        return this;
    }

    public void x() {
        this.f = null;
    }

    public boolean y() {
        return this.f != null;
    }

    public void f(boolean z) {
        if (!z) {
            this.f = null;
        }
    }

    public bo z() {
        return this.g;
    }

    public bn a(bo boVar) {
        this.g = boVar;
        return this;
    }

    public void A() {
        this.g = null;
    }

    public boolean B() {
        return this.g != null;
    }

    public void g(boolean z) {
        if (!z) {
            this.g = null;
        }
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) q.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) q.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Session(");
        stringBuilder.append("id:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("start_time:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("end_time:");
        stringBuilder.append(this.c);
        stringBuilder.append(", ");
        stringBuilder.append("duration:");
        stringBuilder.append(this.d);
        if (t()) {
            stringBuilder.append(", ");
            stringBuilder.append("pages:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        if (y()) {
            stringBuilder.append(", ");
            stringBuilder.append("locations:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        if (B()) {
            stringBuilder.append(", ");
            stringBuilder.append("traffic:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void C() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'id' was not present! Struct: " + toString());
        } else if (this.g != null) {
            this.g.j();
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
            this.u = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
