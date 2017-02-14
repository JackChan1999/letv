package cn.com.iresearch.vvtracker.db.b;

import cn.com.iresearch.vvtracker.db.annotation.sqlite.Id;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.ManyToOne;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.OneToMany;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.Property;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class a {
    public static Method a(Class<?> cls, Field field) {
        String name = field.getName();
        Method method = null;
        if (field.getType() == Boolean.TYPE) {
            method = a((Class) cls, name);
        }
        if (method == null) {
            return b((Class) cls, name);
        }
        return method;
    }

    private static Method a(Class<?> cls, String str) {
        String str2 = "is" + str.substring(0, 1).toUpperCase() + str.substring(1);
        if (!a(str)) {
            str = str2;
        }
        try {
            return cls.getDeclaredMethod(str, new Class[0]);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method c(Class<?> cls, Field field) {
        String name = field.getName();
        String str = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        if (a(field.getName())) {
            str = "set" + name.substring(2, 3).toUpperCase() + name.substring(3);
        }
        try {
            return cls.getDeclaredMethod(str, new Class[]{field.getType()});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static boolean a(String str) {
        if (str == null || str.trim().length() == 0 || !str.startsWith("is") || Character.isLowerCase(str.charAt(2))) {
            return false;
        }
        return true;
    }

    private static Method b(Class<?> cls, String str) {
        try {
            return cls.getDeclaredMethod("get" + str.substring(0, 1).toUpperCase() + str.substring(1), new Class[0]);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method b(Class<?> cls, Field field) {
        String name = field.getName();
        try {
            return cls.getDeclaredMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[]{field.getType()});
        } catch (NoSuchMethodException e) {
            if (field.getType() == Boolean.TYPE) {
                return c(cls, field);
            }
            return null;
        }
    }

    public static String a(Field field) {
        Property property = (Property) field.getAnnotation(Property.class);
        if (property != null && property.column().trim().length() != 0) {
            return property.column();
        }
        ManyToOne manyToOne = (ManyToOne) field.getAnnotation(ManyToOne.class);
        if (manyToOne != null && manyToOne.column().trim().length() != 0) {
            return manyToOne.column();
        }
        OneToMany oneToMany = (OneToMany) field.getAnnotation(OneToMany.class);
        if (oneToMany != null && oneToMany.manyColumn() != null && oneToMany.manyColumn().trim().length() != 0) {
            return oneToMany.manyColumn();
        }
        Id id = (Id) field.getAnnotation(Id.class);
        if (id == null || id.column().trim().length() == 0) {
            return field.getName();
        }
        return id.column();
    }

    public static String b(Field field) {
        Property property = (Property) field.getAnnotation(Property.class);
        if (property == null || property.defaultValue().trim().length() == 0) {
            return null;
        }
        return property.defaultValue();
    }

    public static boolean c(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    public static boolean d(Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    public static boolean e(Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    public static boolean f(Field field) {
        Class type = field.getType();
        return type.equals(String.class) || type.equals(Integer.class) || type.equals(Byte.class) || type.equals(Long.class) || type.equals(Double.class) || type.equals(Float.class) || type.equals(Character.class) || type.equals(Short.class) || type.equals(Boolean.class) || type.equals(Date.class) || type.equals(Date.class) || type.equals(java.sql.Date.class) || type.isPrimitive();
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
