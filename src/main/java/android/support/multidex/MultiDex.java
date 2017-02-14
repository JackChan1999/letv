package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

public final class MultiDex {
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final String SECONDARY_FOLDER_NAME = ("code_cache" + File.separator + OLD_SECONDARY_FOLDER_NAME);
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<String> installedApk = new HashSet();

    private static final class V14 {
        private V14() {
        }

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
        }
    }

    private static final class V19 {
        private V19() {
        }

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator i$ = suppressedExceptions.iterator();
                while (i$.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", (IOException) i$.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(loader, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions = (IOException[]) suppressedExceptionsField.get(loader);
                if (dexElementsSuppressedExceptions == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(loader, dexElementsSuppressedExceptions);
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V4 {
        private V4() {
        }

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, "path");
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }

    private MultiDex() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void install(android.content.Context r11) {
        /*
        r9 = 20;
        r10 = 4;
        r7 = "MultiDex";
        r8 = "install";
        android.util.Log.i(r7, r8);
        r7 = IS_VM_MULTIDEX_CAPABLE;
        if (r7 == 0) goto L_0x0016;
    L_0x000e:
        r7 = "MultiDex";
        r8 = "VM has multidex support, MultiDex support library is disabled.";
        android.util.Log.i(r7, r8);
    L_0x0015:
        return;
    L_0x0016:
        r7 = android.os.Build.VERSION.SDK_INT;
        if (r7 >= r10) goto L_0x0045;
    L_0x001a:
        r7 = new java.lang.RuntimeException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Multi dex installation failed. SDK ";
        r8 = r8.append(r9);
        r9 = android.os.Build.VERSION.SDK_INT;
        r8 = r8.append(r9);
        r9 = " is unsupported. Min SDK version is ";
        r8 = r8.append(r9);
        r8 = r8.append(r10);
        r9 = ".";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
    L_0x0045:
        r1 = getApplicationInfo(r11);	 Catch:{ Exception -> 0x005d }
        if (r1 == 0) goto L_0x0015;
    L_0x004b:
        r8 = installedApk;	 Catch:{ Exception -> 0x005d }
        monitor-enter(r8);	 Catch:{ Exception -> 0x005d }
        r0 = r1.sourceDir;	 Catch:{ all -> 0x005a }
        r7 = installedApk;	 Catch:{ all -> 0x005a }
        r7 = r7.contains(r0);	 Catch:{ all -> 0x005a }
        if (r7 == 0) goto L_0x0088;
    L_0x0058:
        monitor-exit(r8);	 Catch:{ all -> 0x005a }
        goto L_0x0015;
    L_0x005a:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x005a }
        throw r7;	 Catch:{ Exception -> 0x005d }
    L_0x005d:
        r3 = move-exception;
        r7 = "MultiDex";
        r8 = "Multidex installation failure";
        android.util.Log.e(r7, r8, r3);
        r7 = new java.lang.RuntimeException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Multi dex installation failed (";
        r8 = r8.append(r9);
        r9 = r3.getMessage();
        r8 = r8.append(r9);
        r9 = ").";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
    L_0x0088:
        r7 = installedApk;	 Catch:{ all -> 0x005a }
        r7.add(r0);	 Catch:{ all -> 0x005a }
        r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x005a }
        if (r7 <= r9) goto L_0x00da;
    L_0x0091:
        r7 = "MultiDex";
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005a }
        r9.<init>();	 Catch:{ all -> 0x005a }
        r10 = "MultiDex is not guaranteed to work in SDK version ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x005a }
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = ": SDK version higher than ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = 20;
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = " should be backed by ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = "runtime with built-in multidex capabilty but it's not the ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = "case here: java.vm.version=\"";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = "java.vm.version";
        r10 = java.lang.System.getProperty(r10);	 Catch:{ all -> 0x005a }
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r10 = "\"";
        r9 = r9.append(r10);	 Catch:{ all -> 0x005a }
        r9 = r9.toString();	 Catch:{ all -> 0x005a }
        android.util.Log.w(r7, r9);	 Catch:{ all -> 0x005a }
    L_0x00da:
        r5 = r11.getClassLoader();	 Catch:{ RuntimeException -> 0x00ea }
        if (r5 != 0) goto L_0x00f5;
    L_0x00e0:
        r7 = "MultiDex";
        r9 = "Context class loader is null. Must be running in test mode. Skip patching.";
        android.util.Log.e(r7, r9);	 Catch:{ all -> 0x005a }
        monitor-exit(r8);	 Catch:{ all -> 0x005a }
        goto L_0x0015;
    L_0x00ea:
        r3 = move-exception;
        r7 = "MultiDex";
        r9 = "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.";
        android.util.Log.w(r7, r9, r3);	 Catch:{ all -> 0x005a }
        monitor-exit(r8);	 Catch:{ all -> 0x005a }
        goto L_0x0015;
    L_0x00f5:
        clearOldDexDir(r11);	 Catch:{ Throwable -> 0x0119 }
    L_0x00f8:
        r2 = new java.io.File;	 Catch:{ all -> 0x005a }
        r7 = r1.dataDir;	 Catch:{ all -> 0x005a }
        r9 = SECONDARY_FOLDER_NAME;	 Catch:{ all -> 0x005a }
        r2.<init>(r7, r9);	 Catch:{ all -> 0x005a }
        r7 = 0;
        r4 = android.support.multidex.MultiDexExtractor.load(r11, r1, r2, r7);	 Catch:{ all -> 0x005a }
        r7 = checkValidZipFiles(r4);	 Catch:{ all -> 0x005a }
        if (r7 == 0) goto L_0x0122;
    L_0x010c:
        installSecondaryDexes(r5, r2, r4);	 Catch:{ all -> 0x005a }
    L_0x010f:
        monitor-exit(r8);	 Catch:{ all -> 0x005a }
        r7 = "MultiDex";
        r8 = "install done";
        android.util.Log.i(r7, r8);
        goto L_0x0015;
    L_0x0119:
        r6 = move-exception;
        r7 = "MultiDex";
        r9 = "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.";
        android.util.Log.w(r7, r9, r6);	 Catch:{ all -> 0x005a }
        goto L_0x00f8;
    L_0x0122:
        r7 = "MultiDex";
        r9 = "Files were not valid zip files.  Forcing a reload.";
        android.util.Log.w(r7, r9);	 Catch:{ all -> 0x005a }
        r7 = 1;
        r4 = android.support.multidex.MultiDexExtractor.load(r11, r1, r2, r7);	 Catch:{ all -> 0x005a }
        r7 = checkValidZipFiles(r4);	 Catch:{ all -> 0x005a }
        if (r7 == 0) goto L_0x0138;
    L_0x0134:
        installSecondaryDexes(r5, r2, r4);	 Catch:{ all -> 0x005a }
        goto L_0x010f;
    L_0x0138:
        r7 = new java.lang.RuntimeException;	 Catch:{ all -> 0x005a }
        r9 = "Zip files were not valid.";
        r7.<init>(r9);	 Catch:{ all -> 0x005a }
        throw r7;	 Catch:{ all -> 0x005a }
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDex.install(android.content.Context):void");
    }

    private static ApplicationInfo getApplicationInfo(Context context) throws NameNotFoundException {
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            if (pm == null || packageName == null) {
                return null;
            }
            return pm.getApplicationInfo(packageName, 128);
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    isMultidexCapable = major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i(TAG, "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
        if (!files.isEmpty()) {
            if (VERSION.SDK_INT >= 19) {
                V19.install(loader, files, dexDir);
            } else if (VERSION.SDK_INT >= 14) {
                V14.install(loader, files, dexDir);
            } else {
                V4.install(loader, files);
            }
        }
    }

    private static boolean checkValidZipFiles(List<File> files) {
        for (File file : files) {
            if (!MultiDexExtractor.verifyZipFile(file)) {
                return false;
            }
        }
        return true;
    }

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    private static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    private static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File dexDir = new File(context.getFilesDir(), OLD_SECONDARY_FOLDER_NAME);
        if (dexDir.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
            File[] files = dexDir.listFiles();
            if (files == null) {
                Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
                return;
            }
            for (File oldFile : files) {
                Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
                if (oldFile.delete()) {
                    Log.i(TAG, "Deleted old file " + oldFile.getPath());
                } else {
                    Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                }
            }
            if (dexDir.delete()) {
                Log.i(TAG, "Deleted old secondary dex dir " + dexDir.getPath());
            } else {
                Log.w(TAG, "Failed to delete secondary dex dir " + dexDir.getPath());
            }
        }
    }
}
