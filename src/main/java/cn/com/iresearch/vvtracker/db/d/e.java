package cn.com.iresearch.vvtracker.db.d;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class e {
    private static SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String a;
    private String b;
    private Class<?> c;
    private Field d;
    private Method e;
    private Method f;

    public final void a(Object obj, Object obj2) {
        Date date = null;
        if (this.f == null || obj2 == null) {
            try {
                this.d.setAccessible(true);
                this.d.set(obj, obj2);
                return;
            } catch (Exception e) {
                return;
            }
        }
        try {
            if (this.c == String.class) {
                this.f.invoke(obj, new Object[]{obj2.toString()});
            } else if (this.c == Integer.TYPE || this.c == Integer.class) {
                int intValue;
                r1 = this.f;
                r2 = new Object[1];
                if (obj2 == null) {
                    Integer num = null;
                    intValue = num.intValue();
                } else {
                    intValue = Integer.parseInt(obj2.toString());
                }
                r2[0] = Integer.valueOf(intValue);
                r1.invoke(obj, r2);
            } else if (this.c == Float.TYPE || this.c == Float.class) {
                float floatValue;
                r1 = this.f;
                r2 = new Object[1];
                if (obj2 == null) {
                    Float f = null;
                    floatValue = f.floatValue();
                } else {
                    floatValue = Float.parseFloat(obj2.toString());
                }
                r2[0] = Float.valueOf(floatValue);
                r1.invoke(obj, r2);
            } else if (this.c == Double.TYPE || this.c == Double.class) {
                double doubleValue;
                r2 = this.f;
                r3 = new Object[1];
                if (obj2 == null) {
                    Double d = null;
                    doubleValue = d.doubleValue();
                } else {
                    doubleValue = Double.parseDouble(obj2.toString());
                }
                r3[0] = Double.valueOf(doubleValue);
                r2.invoke(obj, r3);
            } else if (this.c == Long.TYPE || this.c == Long.class) {
                long longValue;
                r2 = this.f;
                r3 = new Object[1];
                if (obj2 == null) {
                    Long l = null;
                    longValue = l.longValue();
                } else {
                    longValue = Long.parseLong(obj2.toString());
                }
                r3[0] = Long.valueOf(longValue);
                r2.invoke(obj, r3);
            } else if (this.c == Date.class || this.c == java.sql.Date.class) {
                r1 = this.f;
                r2 = new Object[1];
                if (obj2 != null) {
                    date = c(obj2.toString());
                }
                r2[0] = date;
                r1.invoke(obj, r2);
            } else if (this.c == Boolean.TYPE || this.c == Boolean.class) {
                boolean booleanValue;
                r1 = this.f;
                r2 = new Object[1];
                if (obj2 == null) {
                    Boolean bool = null;
                    booleanValue = bool.booleanValue();
                } else {
                    booleanValue = "1".equals(obj2.toString());
                }
                r2[0] = Boolean.valueOf(booleanValue);
                r1.invoke(obj, r2);
            } else {
                this.f.invoke(obj, new Object[]{obj2});
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final <T> T a(Object obj) {
        if (!(obj == null || this.e == null)) {
            try {
                return this.e.invoke(obj, new Object[0]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    private static Date c(String str) {
        if (str != null) {
            try {
                return g.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void b() {
    }

    public final String c() {
        return this.a;
    }

    public final void a(String str) {
        this.a = str;
    }

    public final String d() {
        return this.b;
    }

    public final void b(String str) {
        this.b = str;
    }

    public final Class<?> e() {
        return this.c;
    }

    public final void a(Class<?> cls) {
        this.c = cls;
    }

    public final void a(Method method) {
        this.e = method;
    }

    public final void b(Method method) {
        this.f = method;
    }

    public final void a(Field field) {
        this.d = field;
    }
}
