package u.aly;

import com.letv.download.db.Download.DownloadVideoTable;
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

/* compiled from: Page */
public class bi implements Serializable, Cloneable, bz<bi, e> {
    public static final Map<e, cl> c;
    private static final dd d = new dd("Page");
    private static final ct e = new ct("page_name", (byte) 11, (short) 1);
    private static final ct f = new ct(DownloadVideoTable.COLUMN_DURATION, (byte) 10, (short) 2);
    private static final Map<Class<? extends dg>, dh> g = new HashMap();
    private static final int h = 0;
    public String a;
    public long b;
    private byte i;

    /* compiled from: Page */
    private static class a extends di<bi> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bi) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bi) bzVar);
        }

        public void a(cy cyVar, bi biVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (biVar.i()) {
                        biVar.j();
                        return;
                    }
                    throw new cz("Required field 'duration' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        biVar.a = cyVar.z();
                        biVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        biVar.b = cyVar.x();
                        biVar.b(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bi biVar) throws cf {
            biVar.j();
            cyVar.a(bi.d);
            if (biVar.a != null) {
                cyVar.a(bi.e);
                cyVar.a(biVar.a);
                cyVar.c();
            }
            cyVar.a(bi.f);
            cyVar.a(biVar.b);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Page */
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

    /* compiled from: Page */
    private static class c extends dj<bi> {
        private c() {
        }

        public void a(cy cyVar, bi biVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(biVar.a);
            deVar.a(biVar.b);
        }

        public void b(cy cyVar, bi biVar) throws cf {
            de deVar = (de) cyVar;
            biVar.a = deVar.z();
            biVar.a(true);
            biVar.b = deVar.x();
            biVar.b(true);
        }
    }

    /* compiled from: Page */
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

    /* compiled from: Page */
    public enum e implements cg {
        PAGE_NAME((short) 1, "page_name"),
        DURATION((short) 2, DownloadVideoTable.COLUMN_DURATION);
        
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
                    return PAGE_NAME;
                case 2:
                    return DURATION;
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
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        g.put(di.class, new b());
        g.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.PAGE_NAME, new cl("page_name", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.DURATION, new cl(DownloadVideoTable.COLUMN_DURATION, (byte) 1, new cm((byte) 10)));
        c = Collections.unmodifiableMap(enumMap);
        cl.a(bi.class, c);
    }

    public bi() {
        this.i = (byte) 0;
    }

    public bi(String str, long j) {
        this();
        this.a = str;
        this.b = j;
        b(true);
    }

    public bi(bi biVar) {
        this.i = (byte) 0;
        this.i = biVar.i;
        if (biVar.e()) {
            this.a = biVar.a;
        }
        this.b = biVar.b;
    }

    public bi a() {
        return new bi(this);
    }

    public void b() {
        this.a = null;
        b(false);
        this.b = 0;
    }

    public String c() {
        return this.a;
    }

    public bi a(String str) {
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

    public bi a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void h() {
        this.i = bw.b(this.i, 0);
    }

    public boolean i() {
        return bw.a(this.i, 0);
    }

    public void b(boolean z) {
        this.i = bw.a(this.i, 0, z);
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) g.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Page(");
        stringBuilder.append("page_name:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("duration:");
        stringBuilder.append(this.b);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void j() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'page_name' was not present! Struct: " + toString());
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
            this.i = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
