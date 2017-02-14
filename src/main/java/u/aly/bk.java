package u.aly;

import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
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

/* compiled from: Resolution */
public class bk implements Serializable, Cloneable, bz<bk, e> {
    public static final Map<e, cl> c;
    private static final dd d = new dd("Resolution");
    private static final ct e = new ct(SettingsJsonConstants.ICON_HEIGHT_KEY, (byte) 8, (short) 1);
    private static final ct f = new ct(SettingsJsonConstants.ICON_WIDTH_KEY, (byte) 8, (short) 2);
    private static final Map<Class<? extends dg>, dh> g = new HashMap();
    private static final int h = 0;
    private static final int i = 1;
    public int a;
    public int b;
    private byte j;

    /* compiled from: Resolution */
    private static class a extends di<bk> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bk) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bk) bzVar);
        }

        public void a(cy cyVar, bk bkVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!bkVar.e()) {
                        throw new cz("Required field 'height' was not found in serialized data! Struct: " + toString());
                    } else if (bkVar.i()) {
                        bkVar.j();
                        return;
                    } else {
                        throw new cz("Required field 'width' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bkVar.a = cyVar.w();
                        bkVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bkVar.b = cyVar.w();
                        bkVar.b(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bk bkVar) throws cf {
            bkVar.j();
            cyVar.a(bk.d);
            cyVar.a(bk.e);
            cyVar.a(bkVar.a);
            cyVar.c();
            cyVar.a(bk.f);
            cyVar.a(bkVar.b);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Resolution */
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

    /* compiled from: Resolution */
    private static class c extends dj<bk> {
        private c() {
        }

        public void a(cy cyVar, bk bkVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(bkVar.a);
            deVar.a(bkVar.b);
        }

        public void b(cy cyVar, bk bkVar) throws cf {
            de deVar = (de) cyVar;
            bkVar.a = deVar.w();
            bkVar.a(true);
            bkVar.b = deVar.w();
            bkVar.b(true);
        }
    }

    /* compiled from: Resolution */
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

    /* compiled from: Resolution */
    public enum e implements cg {
        HEIGHT((short) 1, SettingsJsonConstants.ICON_HEIGHT_KEY),
        WIDTH((short) 2, SettingsJsonConstants.ICON_WIDTH_KEY);
        
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
                    return HEIGHT;
                case 2:
                    return WIDTH;
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
        enumMap.put(e.HEIGHT, new cl(SettingsJsonConstants.ICON_HEIGHT_KEY, (byte) 1, new cm((byte) 8)));
        enumMap.put(e.WIDTH, new cl(SettingsJsonConstants.ICON_WIDTH_KEY, (byte) 1, new cm((byte) 8)));
        c = Collections.unmodifiableMap(enumMap);
        cl.a(bk.class, c);
    }

    public bk() {
        this.j = (byte) 0;
    }

    public bk(int i, int i2) {
        this();
        this.a = i;
        a(true);
        this.b = i2;
        b(true);
    }

    public bk(bk bkVar) {
        this.j = (byte) 0;
        this.j = bkVar.j;
        this.a = bkVar.a;
        this.b = bkVar.b;
    }

    public bk a() {
        return new bk(this);
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

    public bk a(int i) {
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

    public bk c(int i) {
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
        StringBuilder stringBuilder = new StringBuilder("Resolution(");
        stringBuilder.append("height:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("width:");
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
