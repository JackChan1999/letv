package com.loc;

import android.content.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* compiled from: InstanceFactory */
public class af {
    public static <T> T a(Context context, v vVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws l {
        ai a;
        try {
            a = ai.a(context, vVar, ah.a(context, vVar.a(), vVar.b()), ah.a(context), null, context.getClassLoader());
        } catch (Throwable th) {
            th.printStackTrace();
            a = null;
        }
        if (a != null) {
            try {
                if (a.a() && a.a) {
                    Class loadClass = a.loadClass(str);
                    if (loadClass != null) {
                        T newInstance = loadClass.getConstructor(clsArr).newInstance(objArr);
                        return newInstance;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (IllegalArgumentException e5) {
                e5.printStackTrace();
            } catch (InvocationTargetException e6) {
                e6.printStackTrace();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        try {
            Constructor constructor = cls.getConstructor(clsArr);
            constructor.setAccessible(true);
            newInstance = constructor.newInstance(objArr);
            return newInstance;
        } catch (NoSuchMethodException e22) {
            e22.printStackTrace();
            throw new l("获取对象错误");
        } catch (InstantiationException e32) {
            e32.printStackTrace();
            throw new l("获取对象错误");
        } catch (IllegalAccessException e42) {
            e42.printStackTrace();
            throw new l("获取对象错误");
        } catch (IllegalArgumentException e52) {
            e52.printStackTrace();
            throw new l("获取对象错误");
        } catch (InvocationTargetException e62) {
            e62.printStackTrace();
            throw new l("获取对象错误");
        } catch (Throwable th22) {
            th22.printStackTrace();
            throw new l("获取对象错误");
        }
    }
}
