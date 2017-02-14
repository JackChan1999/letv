package cn.com.iresearch.vvtracker.db.d;

import cn.com.iresearch.vvtracker.dao.a;
import cn.com.iresearch.vvtracker.db.a.b;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class f {
    private static final HashMap<String, f> g = new HashMap();
    public final LinkedHashMap<String, e> a = new LinkedHashMap();
    public final HashMap<String, c> b = new HashMap();
    private String c;
    private a d;
    private HashMap<String, d> e = new HashMap();
    private boolean f;

    private f() {
    }

    public static f a(Class<?> cls) {
        if (cls == null) {
            throw new b("table info get error,because the clazz is null");
        }
        f fVar = (f) g.get(cls.getName());
        if (fVar == null) {
            f fVar2 = new f();
            Table table = (Table) cls.getAnnotation(Table.class);
            String replace = (table == null || table.name().trim().length() == 0) ? cls.getName().replace('.', '_') : table.name();
            fVar2.c = replace;
            cls.getName();
            Field primaryKeyField = a.getPrimaryKeyField(cls);
            if (primaryKeyField != null) {
                a aVar = new a();
                aVar.a(cn.com.iresearch.vvtracker.db.b.a.a(primaryKeyField));
                primaryKeyField.getName();
                e.b();
                aVar.b(cn.com.iresearch.vvtracker.db.b.a.b((Class) cls, primaryKeyField));
                aVar.a(cn.com.iresearch.vvtracker.db.b.a.a((Class) cls, primaryKeyField));
                aVar.a(primaryKeyField.getType());
                fVar2.d = aVar;
                List<e> propertyList = a.getPropertyList(cls);
                if (propertyList != null) {
                    for (e eVar : propertyList) {
                        if (eVar != null) {
                            fVar2.a.put(eVar.c(), eVar);
                        }
                    }
                }
                List<c> manyToOneList = a.getManyToOneList(cls);
                if (manyToOneList != null) {
                    for (c cVar : manyToOneList) {
                        if (cVar != null) {
                            fVar2.b.put(cVar.c(), cVar);
                        }
                    }
                }
                List<d> oneToManyList = a.getOneToManyList(cls);
                if (oneToManyList != null) {
                    for (d dVar : oneToManyList) {
                        if (dVar != null) {
                            fVar2.e.put(dVar.c(), dVar);
                        }
                    }
                }
                g.put(cls.getName(), fVar2);
                fVar = fVar2;
            } else {
                throw new b("the class[" + cls + "]'s idField is null");
            }
        }
        if (fVar != null) {
            return fVar;
        }
        throw new b("the class[" + cls + "]'s table is null");
    }

    public final String a() {
        return this.c;
    }

    public final a b() {
        return this.d;
    }

    public final boolean c() {
        return this.f;
    }

    public final void d() {
        this.f = true;
    }
}
