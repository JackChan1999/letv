package com.letv.plugin.pluginloader.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.letv.plugin.pluginloader.util.JLog;
import com.letv.plugin.pluginloader.util.JarUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class JarLoader {
    private static final int MAX_RELOAD = 1;
    private static HashMap<String, JarClassLoader> dexLoaders = new HashMap();
    private static int mReloadNum;

    @SuppressLint({"NewApi"})
    public static JarClassLoader getJarClassLoader(Context context, String jarname, String packagename) {
        String dexInternalPath = JarUtil.getJarInFolderName(context, jarname);
        String optimizedDexOutputPath = JarUtil.getJarOutFolderName(context);
        if (dexLoaders.containsKey(packagename)) {
            return (JarClassLoader) dexLoaders.get(packagename);
        }
        JarClassLoader cl = new JarClassLoader(packagename, dexInternalPath, optimizedDexOutputPath, context.getApplicationInfo().nativeLibraryDir, context.getClassLoader());
        dexLoaders.put(packagename, cl);
        return cl;
    }

    @SuppressLint({"NewApi"})
    public static Class loadClass(Context context, String jarName, String packageName, String className) {
        String dexInternalPath = JarUtil.getJarInFolderName(context, jarName);
        String optimizedDexOutputPath = JarUtil.getJarOutFolderName(context);
        if (!TextUtils.isEmpty(dexInternalPath)) {
            try {
                JarClassLoader cl;
                if (dexLoaders.containsKey(packageName)) {
                    cl = (JarClassLoader) dexLoaders.get(packageName);
                } else {
                    cl = new JarClassLoader(packageName, dexInternalPath, optimizedDexOutputPath, context.getApplicationInfo().nativeLibraryDir, context.getClassLoader());
                    dexLoaders.put(packageName, cl);
                }
                return cl.loadClass(packageName + "." + className);
            } catch (Exception e) {
                JLog.i("clf", "!!!!!!! loadClass--" + packageName + " e is " + e.getMessage());
                return null;
            }
        } else if (mReloadNum >= 1) {
            return null;
        } else {
            mReloadNum++;
            JLog.i("plugin", "JarUtil.updatePlugin 333333333333333333");
            JarUtil.updatePlugin(context, 0, false);
            return loadClass(context, jarName, packageName, className);
        }
    }

    @SuppressLint({"NewApi"})
    public static Class loadClassByFullName(Context context, String jarname, String packagename, String classpath) {
        String dexInternalPath = JarUtil.getJarInFolderName(context, jarname);
        String optimizedDexOutputPath = JarUtil.getJarOutFolderName(context);
        if (TextUtils.isEmpty(dexInternalPath)) {
            JLog.i("plugin", "JarUtil.updatePlugin 444444444444444444");
            JarUtil.updatePlugin(context, 0, false);
            return loadClassByFullName(context, jarname, packagename, classpath);
        }
        try {
            JarClassLoader cl;
            if (dexLoaders.containsKey(packagename)) {
                cl = (JarClassLoader) dexLoaders.get(packagename);
            } else {
                cl = new JarClassLoader(packagename, dexInternalPath, optimizedDexOutputPath, context.getApplicationInfo().nativeLibraryDir, context.getClassLoader());
                dexLoaders.put(packagename, cl);
            }
            return cl.loadClass(classpath);
        } catch (Exception e) {
            JLog.i("clf", "!!!!!!! loadClassByFullName---" + packagename + " e is " + e.getMessage());
            return null;
        }
    }

    public static Object newInstance(Class clazz, Class[] constructors_class, Object[] constructors_args) {
        Object instance = null;
        try {
            Constructor constructor = clazz.getDeclaredConstructor(constructors_class);
            constructor.setAccessible(true);
            instance = constructor.newInstance(constructors_args);
        } catch (NoSuchMethodException e) {
            JLog.i("clf", "NoSuchMethodException..e=" + e.getMessage());
        } catch (IllegalArgumentException e2) {
            JLog.i("clf", "IllegalArgumentException..e=" + e2.getMessage());
        } catch (InstantiationException e3) {
            JLog.i("clf", "InstantiationException..e=" + e3.getMessage());
        } catch (IllegalAccessException e4) {
            JLog.i("clf", "IllegalAccessException..e=" + e4.getMessage());
        } catch (InvocationTargetException e5) {
            JLog.i("clf", "InvocationTargetException..e=" + e5.getMessage());
        }
        return instance;
    }

    public static Object invokeMethod(Class clazz, String method_name, Class[] method_class, Object[] method_args) {
        Object result = null;
        try {
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod(method_name, method_class);
            method.setAccessible(true);
            result = method.invoke(obj, method_args);
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e2) {
        } catch (IllegalAccessException e3) {
        } catch (InvocationTargetException e4) {
        } catch (InstantiationException e5) {
        }
        return result;
    }

    public static Object invokeMethodByObj(Object obj, String method_name, Class[] method_class, Object[] method_args) {
        Object result = null;
        try {
            Method method = obj.getClass().getMethod(method_name, method_class);
            method.setAccessible(true);
            result = method.invoke(obj, method_args);
        } catch (NoSuchMethodException e) {
            JLog.d("WindowPlayer", "NoSuchMethodException error = " + e.toString());
        } catch (IllegalArgumentException e2) {
            JLog.d("WindowPlayer", "IllegalArgumentException error = " + e2.toString());
        } catch (IllegalAccessException e3) {
            JLog.d("WindowPlayer", "IllegalAccessException error = " + e3.toString());
        } catch (InvocationTargetException e4) {
            JLog.d("WindowPlayer", "InvocationTargetException error = " + e4.toString());
        }
        return result;
    }

    public static Object invokeStaticMethod(Class clazz, String method_name, Class[] method_class, Object[] method_args) {
        Object result = null;
        try {
            Method method = clazz.getMethod(method_name, method_class);
            method.setAccessible(true);
            result = method.invoke(clazz, method_args);
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e2) {
        } catch (IllegalAccessException e3) {
        } catch (InvocationTargetException e4) {
        }
        return result;
    }
}
