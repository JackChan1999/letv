package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;
import com.letv.lemallsdk.util.Constants;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    private static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private static Method sApplyMethod;

    MultiDexExtractor() {
    }

    static List<File> load(Context context, ApplicationInfo applicationInfo, File dexDir, boolean forceReload) throws IOException {
        List<File> files;
        Log.i(TAG, "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + forceReload + ")");
        File sourceApk = new File(applicationInfo.sourceDir);
        long currentCrc = getZipCrc(sourceApk);
        if (forceReload || isModified(context, sourceApk, currentCrc)) {
            Log.i(TAG, "Detected that extraction must be performed.");
            files = performExtractions(sourceApk, dexDir);
            putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
        } else {
            try {
                files = loadExistingExtractions(context, sourceApk, dexDir);
            } catch (IOException ioe) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                files = performExtractions(sourceApk, dexDir);
                putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
            }
        }
        Log.i(TAG, "load found " + files.size() + " secondary dex files");
        return files;
    }

    private static List<File> loadExistingExtractions(Context context, File sourceApk, File dexDir) throws IOException {
        Log.i(TAG, "loading existing secondary dex files");
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);
        List<File> files = new ArrayList(totalDexNumber);
        int secondaryNumber = 2;
        while (secondaryNumber <= totalDexNumber) {
            File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
            if (extractedFile.isFile()) {
                files.add(extractedFile);
                if (verifyZipFile(extractedFile)) {
                    secondaryNumber++;
                } else {
                    Log.i(TAG, "Invalid zip file: " + extractedFile);
                    throw new IOException("Invalid ZIP file.");
                }
            }
            throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
        }
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        return (prefs.getLong("timestamp", -1) == getTimeStamp(archive) && prefs.getLong(KEY_CRC, -1) == currentCrc) ? false : true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private static List<File> performExtractions(File sourceApk, File dexDir) throws IOException {
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        prepareDexDir(dexDir, extractedFilePrefix);
        List<File> files = new ArrayList();
        ZipFile apk = new ZipFile(sourceApk);
        int secondaryNumber = 2;
        try {
            ZipEntry dexFile = apk.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX);
            while (dexFile != null) {
                File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
                files.add(extractedFile);
                Log.i(TAG, "Extraction is needed for file " + extractedFile);
                int numAttempts = 0;
                boolean isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    numAttempts++;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    isExtractionSuccessful = verifyZipFile(extractedFile);
                    Log.i(TAG, "Extraction " + (isExtractionSuccessful ? Constants.CALLBACK_SUCCESS : Constants.CALLBACK_FAILD) + " - length " + extractedFile.getAbsolutePath() + ": " + extractedFile.length());
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                        }
                    }
                }
                if (isExtractionSuccessful) {
                    secondaryNumber++;
                    dexFile = apk.getEntry(DEX_PREFIX + secondaryNumber + DEX_SUFFIX);
                } else {
                    throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + ")");
                }
            }
            return files;
        } finally {
            try {
                apk.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
        }
    }

    private static void putStoredApkInfo(Context context, long timeStamp, long crc, int totalDexNumber) {
        Editor edit = getMultiDexPreferences(context).edit();
        edit.putLong("timestamp", timeStamp);
        edit.putLong(KEY_CRC, crc);
        edit.putInt(KEY_DEX_NUMBER, totalDexNumber);
        apply(edit);
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private static void prepareDexDir(File dexDir, final String extractedFilePrefix) throws IOException {
        mkdirChecked(dexDir.getParentFile());
        mkdirChecked(dexDir);
        File[] files = dexDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith(extractedFilePrefix);
            }
        });
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
    }

    private static void mkdirChecked(File dir) throws IOException {
        dir.mkdir();
        if (!dir.isDirectory()) {
            File parent = dir.getParentFile();
            if (parent == null) {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". Parent file is null.");
            } else {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". parent file is a dir " + parent.isDirectory() + ", a file " + parent.isFile() + ", exists " + parent.exists() + ", readable " + parent.canRead() + ", writable " + parent.canWrite());
            }
            throw new IOException("Failed to create cache directory " + dir.getPath());
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {
        Throwable th;
        InputStream in = apk.getInputStream(dexFile);
        File tmp = File.createTempFile(extractedFilePrefix, EXTRACTED_SUFFIX, extractTo.getParentFile());
        Log.i(TAG, "Extracting " + tmp.getPath());
        try {
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            try {
                ZipEntry classesDex = new ZipEntry("classes.dex");
                classesDex.setTime(dexFile.getTime());
                out.putNextEntry(classesDex);
                byte[] buffer = new byte[16384];
                for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                    out.write(buffer, 0, length);
                }
                out.closeEntry();
                out.close();
                Log.i(TAG, "Renaming to " + extractTo.getPath());
                if (tmp.renameTo(extractTo)) {
                    closeQuietly(in);
                    tmp.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
            } catch (Throwable th2) {
                th = th2;
                ZipOutputStream zipOutputStream = out;
                closeQuietly(in);
                tmp.delete();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            closeQuietly(in);
            tmp.delete();
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean verifyZipFile(java.io.File r6) {
        /*
        r2 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r2.<init>(r6);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r2.close();	 Catch:{ IOException -> 0x000a, ZipException -> 0x0029 }
        r3 = 1;
    L_0x0009:
        return r3;
    L_0x000a:
        r0 = move-exception;
        r3 = "MultiDex";
        r4 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r4.<init>();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r5 = "Failed to close zip file: ";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r5 = r6.getAbsolutePath();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r4 = r4.toString();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        android.util.Log.w(r3, r4);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
    L_0x0027:
        r3 = 0;
        goto L_0x0009;
    L_0x0029:
        r1 = move-exception;
        r3 = "MultiDex";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "File ";
        r4 = r4.append(r5);
        r5 = r6.getAbsolutePath();
        r4 = r4.append(r5);
        r5 = " is not a valid zip file.";
        r4 = r4.append(r5);
        r4 = r4.toString();
        android.util.Log.w(r3, r4, r1);
        goto L_0x0027;
    L_0x004d:
        r1 = move-exception;
        r3 = "MultiDex";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Got an IOException trying to open zip file: ";
        r4 = r4.append(r5);
        r5 = r6.getAbsolutePath();
        r4 = r4.append(r5);
        r4 = r4.toString();
        android.util.Log.w(r3, r4, r1);
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDexExtractor.verifyZipFile(java.io.File):boolean");
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }

    static {
        try {
            sApplyMethod = Editor.class.getMethod("apply", new Class[0]);
        } catch (NoSuchMethodException e) {
            sApplyMethod = null;
        }
    }

    private static void apply(Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor, new Object[0]);
                return;
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
        editor.commit();
    }
}
