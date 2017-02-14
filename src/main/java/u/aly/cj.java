package u.aly;

import com.letv.pp.utils.NetworkUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

/* compiled from: TUnion */
public abstract class cj<T extends cj<?, ?>, F extends cg> implements bz<T, F> {
    private static final Map<Class<? extends dg>, dh> a = new HashMap();
    protected Object b;
    protected F c;

    /* compiled from: TUnion */
    private static class a extends di<cj> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (cj) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (cj) bzVar);
        }

        public void a(cy cyVar, cj cjVar) throws cf {
            cjVar.c = null;
            cjVar.b = null;
            cyVar.j();
            ct l = cyVar.l();
            cjVar.b = cjVar.a(cyVar, l);
            if (cjVar.b != null) {
                cjVar.c = cjVar.b(l.c);
            }
            cyVar.m();
            cyVar.l();
            cyVar.k();
        }

        public void b(cy cyVar, cj cjVar) throws cf {
            if (cjVar.i() == null || cjVar.j() == null) {
                throw new cz("Cannot write a TUnion with no set value!");
            }
            cyVar.a(cjVar.c());
            cyVar.a(cjVar.a(cjVar.c));
            cjVar.c(cyVar);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: TUnion */
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

    /* compiled from: TUnion */
    private static class c extends dj<cj> {
        private c() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (cj) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (cj) bzVar);
        }

        public void a(cy cyVar, cj cjVar) throws cf {
            cjVar.c = null;
            cjVar.b = null;
            short v = cyVar.v();
            cjVar.b = cjVar.a(cyVar, v);
            if (cjVar.b != null) {
                cjVar.c = cjVar.b(v);
            }
        }

        public void b(cy cyVar, cj cjVar) throws cf {
            if (cjVar.i() == null || cjVar.j() == null) {
                throw new cz("Cannot write a TUnion with no set value!");
            }
            cyVar.a(cjVar.c.a());
            cjVar.d(cyVar);
        }
    }

    /* compiled from: TUnion */
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

    protected abstract Object a(cy cyVar, ct ctVar) throws cf;

    protected abstract Object a(cy cyVar, short s) throws cf;

    protected abstract ct a(F f);

    protected abstract void a(F f, Object obj) throws ClassCastException;

    protected abstract F b(short s);

    protected abstract dd c();

    protected abstract void c(cy cyVar) throws cf;

    protected abstract void d(cy cyVar) throws cf;

    protected cj() {
        this.c = null;
        this.b = null;
    }

    static {
        a.put(di.class, new b());
        a.put(dj.class, new d());
    }

    protected cj(F f, Object obj) {
        b(f, obj);
    }

    protected cj(cj<T, F> cjVar) {
        if (cjVar.getClass().equals(getClass())) {
            this.c = cjVar.c;
            this.b = a(cjVar.b);
            return;
        }
        throw new ClassCastException();
    }

    private static Object a(Object obj) {
        if (obj instanceof bz) {
            return ((bz) obj).g();
        }
        if (obj instanceof ByteBuffer) {
            return ca.d((ByteBuffer) obj);
        }
        if (obj instanceof List) {
            return a((List) obj);
        }
        if (obj instanceof Set) {
            return a((Set) obj);
        }
        if (obj instanceof Map) {
            return a((Map) obj);
        }
        return obj;
    }

    private static Map a(Map<Object, Object> map) {
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put(a(entry.getKey()), a(entry.getValue()));
        }
        return hashMap;
    }

    private static Set a(Set set) {
        Set hashSet = new HashSet();
        for (Object a : set) {
            hashSet.add(a(a));
        }
        return hashSet;
    }

    private static List a(List list) {
        List arrayList = new ArrayList(list.size());
        for (Object a : list) {
            arrayList.add(a(a));
        }
        return arrayList;
    }

    public F i() {
        return this.c;
    }

    public Object j() {
        return this.b;
    }

    public Object b(F f) {
        if (f == this.c) {
            return j();
        }
        throw new IllegalArgumentException("Cannot get the value of field " + f + " because union's set field is " + this.c);
    }

    public Object c(int i) {
        return b(b((short) i));
    }

    public boolean k() {
        return this.c != null;
    }

    public boolean c(F f) {
        return this.c == f;
    }

    public boolean d(int i) {
        return c(b((short) i));
    }

    public void a(cy cyVar) throws cf {
        ((dh) a.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(F f, Object obj) {
        a((cg) f, obj);
        this.c = f;
        this.b = obj;
    }

    public void a(int i, Object obj) {
        b(b((short) i), obj);
    }

    public void b(cy cyVar) throws cf {
        ((dh) a.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SearchCriteria.LT);
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" ");
        if (i() != null) {
            Object j = j();
            stringBuilder.append(a(i()).a);
            stringBuilder.append(NetworkUtils.DELIMITER_COLON);
            if (j instanceof ByteBuffer) {
                ca.a((ByteBuffer) j, stringBuilder);
            } else {
                stringBuilder.append(j.toString());
            }
        }
        stringBuilder.append(SearchCriteria.GT);
        return stringBuilder.toString();
    }

    public final void b() {
        this.c = null;
        this.b = null;
    }
}
