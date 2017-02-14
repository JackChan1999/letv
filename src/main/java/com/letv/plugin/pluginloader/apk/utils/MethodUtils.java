package com.letv.plugin.pluginloader.apk.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MethodUtils {
    private static Map<String, Method> sMethodCache = new HashMap();

    private static String getKey(Class<?> cls, String methodName, Class<?>... parameterTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.toString()).append("#").append(methodName);
        if (parameterTypes == null || parameterTypes.length <= 0) {
            sb.append(Void.class.toString());
        } else {
            for (Class<?> parameterType : parameterTypes) {
                sb.append(parameterType.toString()).append("#");
            }
        }
        return sb.toString();
    }

    private static Method getAccessibleMethodFromSuperclass(Class<?> cls, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        Class<?> parentClass = cls.getSuperclass();
        while (parentClass != null) {
            if (Modifier.isPublic(parentClass.getModifiers())) {
                try {
                    method = parentClass.getMethod(methodName, parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                }
            } else {
                parentClass = parentClass.getSuperclass();
            }
        }
        return method;
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, String methodName, Class<?>... parameterTypes) {
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (Modifier.isPublic(interfaces[i].getModifiers())) {
                    try {
                        return interfaces[i].getDeclaredMethod(methodName, parameterTypes);
                    } catch (NoSuchMethodException e) {
                        Method method = getAccessibleMethodFromInterfaceNest(interfaces[i], methodName, parameterTypes);
                        if (method != null) {
                            return method;
                        }
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    private static Method getAccessibleMethod(Method method) {
        if (!MemberUtils.isAccessible(method)) {
            return null;
        }
        Class<?> cls = method.getDeclaringClass();
        if (Modifier.isPublic(cls.getModifiers())) {
            return method;
        }
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);
        if (method == null) {
            return getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
        }
        return method;
    }

    public static Method getAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        String key = getKey(cls, methodName, parameterTypes);
        synchronized (sMethodCache) {
            Method method = (Method) sMethodCache.get(key);
        }
        if (method == null) {
            Method accessibleMethod = getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
            synchronized (sMethodCache) {
                sMethodCache.put(key, accessibleMethod);
            }
            return accessibleMethod;
        } else if (method.isAccessible()) {
            return method;
        } else {
            method.setAccessible(true);
            return method;
        }
    }

    private static Method getMatchingAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
        Method method;
        String key = getKey(cls, methodName, parameterTypes);
        synchronized (sMethodCache) {
            Method cachedMethod = (Method) sMethodCache.get(key);
        }
        if (cachedMethod == null) {
            try {
                method = cls.getMethod(methodName, parameterTypes);
                MemberUtils.setAccessibleWorkaround(method);
                synchronized (sMethodCache) {
                    sMethodCache.put(key, method);
                }
                return method;
            } catch (NoSuchMethodException e) {
                Method bestMatch = null;
                for (Method method2 : cls.getMethods()) {
                    if (method2.getName().equals(methodName) && MemberUtils.isAssignable(parameterTypes, method2.getParameterTypes(), true)) {
                        Method accessibleMethod = getAccessibleMethod(method2);
                        if (accessibleMethod != null && (bestMatch == null || MemberUtils.compareParameterTypes(accessibleMethod.getParameterTypes(), bestMatch.getParameterTypes(), parameterTypes) < 0)) {
                            bestMatch = accessibleMethod;
                        }
                    }
                }
                if (bestMatch != null) {
                    MemberUtils.setAccessibleWorkaround(bestMatch);
                }
                synchronized (sMethodCache) {
                    sMethodCache.put(key, bestMatch);
                    return bestMatch;
                }
            }
        } else if (cachedMethod.isAccessible()) {
            return cachedMethod;
        } else {
            cachedMethod.setAccessible(true);
            return cachedMethod;
        }
    }

    public static Object invokeMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        parameterTypes = Utils.nullToEmpty((Class[]) parameterTypes);
        args = Utils.nullToEmpty(args);
        Method method = getMatchingAccessibleMethod(object.getClass(), methodName, parameterTypes);
        if (method != null) {
            return method.invoke(object, args);
        }
        throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
    }

    public static Object invokeStaticMethod(Class clazz, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        parameterTypes = Utils.nullToEmpty((Class[]) parameterTypes);
        args = Utils.nullToEmpty(args);
        Method method = getMatchingAccessibleMethod(clazz, methodName, parameterTypes);
        if (method != null) {
            return method.invoke(null, args);
        }
        throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + clazz.getName());
    }

    public static Object invokeStaticMethod(Class clazz, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args = Utils.nullToEmpty(args);
        return invokeStaticMethod(clazz, methodName, args, Utils.toClass(args));
    }

    public static Object invokeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args = Utils.nullToEmpty(args);
        return invokeMethod(object, methodName, args, Utils.toClass(args));
    }

    public static <T> T invokeConstructor(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        args = Utils.nullToEmpty(args);
        return invokeConstructor(cls, args, Utils.toClass(args));
    }

    public static <T> T invokeConstructor(Class<T> cls, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        args = Utils.nullToEmpty(args);
        Constructor<T> ctor = getMatchingAccessibleConstructor(cls, Utils.nullToEmpty((Class[]) parameterTypes));
        if (ctor != null) {
            return ctor.newInstance(args);
        }
        throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName());
    }

    public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> cls, Class<?>... parameterTypes) {
        int i = 0;
        Validate.isTrue(cls != null, "class cannot be null", new Object[0]);
        try {
            Constructor<T> ctor = cls.getConstructor(parameterTypes);
            MemberUtils.setAccessibleWorkaround(ctor);
            return ctor;
        } catch (NoSuchMethodException e) {
            Constructor<T> result = null;
            Constructor<?>[] ctors = cls.getConstructors();
            int length = ctors.length;
            while (i < length) {
                Constructor<?> ctor2 = ctors[i];
                if (MemberUtils.isAssignable(parameterTypes, ctor2.getParameterTypes(), true)) {
                    ctor2 = getAccessibleConstructor(ctor2);
                    if (ctor2 != null) {
                        MemberUtils.setAccessibleWorkaround(ctor2);
                        if (result == null || MemberUtils.compareParameterTypes(ctor2.getParameterTypes(), result.getParameterTypes(), parameterTypes) < 0) {
                            result = ctor2;
                        }
                    }
                }
                i++;
            }
            return result;
        }
    }

    private static <T> Constructor<T> getAccessibleConstructor(Constructor<T> ctor) {
        boolean z;
        if (ctor != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "constructor cannot be null", new Object[0]);
        return (MemberUtils.isAccessible(ctor) && isAccessible(ctor.getDeclaringClass())) ? ctor : null;
    }

    private static boolean isAccessible(Class<?> type) {
        for (Class<?> cls = type; cls != null; cls = cls.getEnclosingClass()) {
            if (!Modifier.isPublic(cls.getModifiers())) {
                return false;
            }
        }
        return true;
    }
}
