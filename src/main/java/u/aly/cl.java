package u.aly;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FieldMetaData */
public class cl implements Serializable {
    private static Map<Class<? extends bz>, Map<? extends cg, cl>> d = new HashMap();
    public final String a;
    public final byte b;
    public final cm c;

    public cl(String str, byte b, cm cmVar) {
        this.a = str;
        this.b = b;
        this.c = cmVar;
    }

    public static void a(Class<? extends bz> cls, Map<? extends cg, cl> map) {
        d.put(cls, map);
    }

    public static Map<? extends cg, cl> a(Class<? extends bz> cls) {
        if (!d.containsKey(cls)) {
            try {
                cls.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("InstantiationException for TBase class: " + cls.getName() + ", message: " + e.getMessage());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("IllegalAccessException for TBase class: " + cls.getName() + ", message: " + e2.getMessage());
            }
        }
        return (Map) d.get(cls);
    }
}
