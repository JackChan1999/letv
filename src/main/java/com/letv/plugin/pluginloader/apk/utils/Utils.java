package com.letv.plugin.pluginloader.apk.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class Utils {
    private static final String ALGORITHM = "MD5";
    static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String TAG = Utils.class.getSimpleName();

    static boolean isSameLength(Object[] array1, Object[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length));
    }

    static Class<?>[] toClass(Object... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CLASS_ARRAY;
        }
        Class<?>[] classes = new Class[array.length];
        for (int i = 0; i < array.length; i++) {
            classes[i] = array[i] == null ? null : array[i].getClass();
        }
        return classes;
    }

    static Class<?>[] nullToEmpty(Class<?>[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_CLASS_ARRAY;
        }
        return array;
    }

    static Object[] nullToEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet();
        getAllInterfaces(cls, interfacesFound);
        return new ArrayList(interfacesFound);
    }

    private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            for (Class<?> i : cls.getInterfaces()) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static void copyFile(String src, String dst) throws IOException {
        Throwable th;
        BufferedInputStream in = null;
        BufferedOutputStream ou = null;
        try {
            BufferedOutputStream ou2;
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(src));
            try {
                ou2 = new BufferedOutputStream(new FileOutputStream(dst));
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                    }
                }
                if (ou != null) {
                    try {
                        ou.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int read = in2.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    ou2.write(buffer, 0, read);
                }
                if (in2 != null) {
                    try {
                        in2.close();
                    } catch (Exception e3) {
                    }
                }
                if (ou2 != null) {
                    try {
                        ou2.close();
                    } catch (Exception e4) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                ou = ou2;
                in = in2;
                if (in != null) {
                    in.close();
                }
                if (ou != null) {
                    ou.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (in != null) {
                in.close();
            }
            if (ou != null) {
                ou.close();
            }
            throw th;
        }
    }

    public static void deleteDir(String file) {
        deleteFile(new File(file));
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File deleteFile : files) {
                deleteFile(deleteFile);
            }
        }
        file.delete();
    }

    public static void writeToFile(File file, byte[] data) throws IOException {
        Throwable th;
        FileOutputStream fou = null;
        try {
            FileOutputStream fou2 = new FileOutputStream(file);
            try {
                fou2.write(data);
                if (fou2 != null) {
                    try {
                        fou2.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fou = fou2;
                if (fou != null) {
                    try {
                        fou.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (fou != null) {
                fou.close();
            }
            throw th;
        }
    }

    public static byte[] readFromFile(File file) throws IOException {
        Throwable th;
        FileInputStream fin = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileInputStream fin2 = new FileInputStream(file);
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int read = fin2.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    out.write(buffer, 0, read);
                }
                byte[] data = out.toByteArray();
                out.close();
                if (fin2 != null) {
                    try {
                        fin2.close();
                    } catch (IOException e) {
                    }
                }
                return data;
            } catch (Throwable th2) {
                th = th2;
                fin = fin2;
                if (fin != null) {
                    try {
                        fin.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (fin != null) {
                fin.close();
            }
            throw th;
        }
    }

    public static String md5(byte[] data) {
        try {
            return toHex(MessageDigest.getInstance("MD5").digest(data));
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Md5 Fail", new Object[0]);
            return null;
        }
    }

    private static String toHex(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (int v : b) {
            builder.append(HEX[(v & 240) >> 4]);
            builder.append(HEX[v & 15]);
        }
        return builder.toString();
    }

    public static String getProcessName(Context context, int pid) {
        for (RunningAppProcessInfo rap : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (rap != null && rap.pid == pid) {
                return rap.processName;
            }
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        return getAvailableNetworkInfo(context) != null;
    }

    public static NetworkInfo getAvailableNetworkInfo(Context context) {
        NetworkInfo ni = null;
        try {
            ni = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ni == null || !ni.isAvailable()) ? null : ni;
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = getAvailableNetworkInfo(context);
        if (networkInfo == null || networkInfo.getType() != 1) {
            return false;
        }
        return true;
    }
}
