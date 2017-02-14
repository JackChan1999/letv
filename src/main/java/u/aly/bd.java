package u.aly;

import com.letv.lemallsdk.util.Constants;
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

/* compiled from: ImprintValue */
public class bd implements Serializable, Cloneable, bz<bd, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("ImprintValue");
    private static final ct f = new ct(Constants.VALUE_ID, (byte) 11, (short) 1);
    private static final ct g = new ct("ts", (byte) 10, (short) 2);
    private static final ct h = new ct("guid", (byte) 11, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    public String a;
    public long b;
    public String c;
    private byte k;
    private e[] l;

    /* compiled from: ImprintValue */
    private static class a extends di<bd> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bd) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bd) bzVar);
        }

        public void a(cy cyVar, bd bdVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (bdVar.i()) {
                        bdVar.m();
                        return;
                    }
                    throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bdVar.a = cyVar.z();
                        bdVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bdVar.b = cyVar.x();
                        bdVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bdVar.c = cyVar.z();
                        bdVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bd bdVar) throws cf {
            bdVar.m();
            cyVar.a(bd.e);
            if (bdVar.a != null && bdVar.e()) {
                cyVar.a(bd.f);
                cyVar.a(bdVar.a);
                cyVar.c();
            }
            cyVar.a(bd.g);
            cyVar.a(bdVar.b);
            cyVar.c();
            if (bdVar.c != null) {
                cyVar.a(bd.h);
                cyVar.a(bdVar.c);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: ImprintValue */
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

    /* compiled from: ImprintValue */
    private static class c extends dj<bd> {
        private c() {
        }

        public void a(cy cyVar, bd bdVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(bdVar.b);
            deVar.a(bdVar.c);
            BitSet bitSet = new BitSet();
            if (bdVar.e()) {
                bitSet.set(0);
            }
            deVar.a(bitSet, 1);
            if (bdVar.e()) {
                deVar.a(bdVar.a);
            }
        }

        public void b(cy cyVar, bd bdVar) throws cf {
            de deVar = (de) cyVar;
            bdVar.b = deVar.x();
            bdVar.b(true);
            bdVar.c = deVar.z();
            bdVar.c(true);
            if (deVar.b(1).get(0)) {
                bdVar.a = deVar.z();
                bdVar.a(true);
            }
        }
    }

    /* compiled from: ImprintValue */
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

    /* compiled from: ImprintValue */
    public enum e implements cg {
        VALUE((short) 1, Constants.VALUE_ID),
        TS((short) 2, "ts"),
        GUID((short) 3, "guid");
        
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
                    return VALUE;
                case 2:
                    return TS;
                case 3:
                    return GUID;
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
        enumMap.put(e.VALUE, new cl(Constants.VALUE_ID, (byte) 2, new cm((byte) 11)));
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.GUID, new cl("guid", (byte) 1, new cm((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(bd.class, d);
    }

    public bd() {
        this.k = (byte) 0;
        this.l = new e[]{e.VALUE};
    }

    public bd(long j, String str) {
        this();
        this.b = j;
        b(true);
        this.c = str;
    }

    public bd(bd bdVar) {
        this.k = (byte) 0;
        this.l = new e[]{e.VALUE};
        this.k = bdVar.k;
        if (bdVar.e()) {
            this.a = bdVar.a;
        }
        this.b = bdVar.b;
        if (bdVar.l()) {
            this.c = bdVar.c;
        }
    }

    public bd a() {
        return new bd(this);
    }

    public void b() {
        this.a = null;
        b(false);
        this.b = 0;
        this.c = null;
    }

    public String c() {
        return this.a;
    }

    public bd a(String str) {
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

    public bd a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void h() {
        this.k = bw.b(this.k, 0);
    }

    public boolean i() {
        return bw.a(this.k, 0);
    }

    public void b(boolean z) {
        this.k = bw.a(this.k, 0, z);
    }

    public String j() {
        return this.c;
    }

    public bd b(String str) {
        this.c = str;
        return this;
    }

    public void k() {
        this.c = null;
    }

    public boolean l() {
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
        StringBuilder stringBuilder = new StringBuilder("ImprintValue(");
        Object obj = 1;
        if (e()) {
            stringBuilder.append("value:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            obj = null;
        }
        if (obj == null) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void m() throws cf {
        if (this.c == null) {
            throw new cz("Required field 'guid' was not present! Struct: " + toString());
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
