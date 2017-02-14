package com.letv.pp.utils;

import android.content.Context;
import com.letv.android.client.album.service.SimplePluginDownloadService;
import java.io.File;
import java.util.HashMap;

public class LibraryHelper {
    private static final String FORMAT_LIB_ASSETS_NAME = "lib%s.so.%s";
    private static final String FORMAT_LIB_LOCAL_NAME = "lib%s.so";
    private static final String FORMAT_LIB_NATIVE_NAME = "lib%s-native.so";
    private static final String FORMAT_LIB_UPGRADE_NAME = "lib%s-upgrade.so";
    private static final String KEY_BACKUP_LIBRAY_TO_SDCARD = "backup_libray_to_sdcard";
    private static final String KEY_PRIORITY_LOAD_EXTERNAL_LIB = "priority_load_external_lib";
    public static final String LIB_NAME_CDE = "cde";
    public static final String LIB_UPGRADE_OLD_NAME = "libcde.so.upgrade";
    private static final String TAG = "LibraryHelper";
    private static boolean sPriorityLoadExternalLib;

    public static void init(HashMap<String, String> paramMap) {
        if (paramMap != null) {
            sPriorityLoadExternalLib = "1".equals(paramMap.get(KEY_PRIORITY_LOAD_EXTERNAL_LIB));
        }
    }

    public static boolean loadLibrary(Context context, String libraryName) {
        LogTool.i(TAG, "loadLibrary. start load %s library.", libraryName);
        if (context == null || StringUtils.isEmpty(libraryName)) {
            LogTool.e(TAG, "loadLibrary. illegal parameter.");
            return false;
        }
        String loadLibraryPath;
        boolean loadLibrarySuccess;
        String from;
        long startTime = System.nanoTime();
        String libraryRootPath = getLibraryRootPath(context);
        File localLibraryFile = new File(libraryRootPath, getLibraryLocalName(libraryName));
        File nativeLibraryFile = new File(libraryRootPath, getLibraryNativeName(libraryName));
        File upgradeLibraryFile = new File(libraryRootPath, getLibraryUpgradeName(libraryName));
        File externalLibraryFile = new File(FileHelper.getExternalStoragePath(), getLibraryLocalName(libraryName));
        boolean usedNativeLibrary = false;
        boolean usedExternalLibrary = false;
        if (sPriorityLoadExternalLib) {
            if (ContextUtils.checkPermission(context, "android.permission.READ_EXTERNAL_STORAGE") && externalLibraryFile.exists()) {
                if (localLibraryFile.exists() && !upgradeLibraryFile.exists()) {
                    FileHelper.renameFile(localLibraryFile, upgradeLibraryFile);
                }
                FileHelper.copyFile(externalLibraryFile, localLibraryFile);
                loadLibraryPath = localLibraryFile.getAbsolutePath();
                usedExternalLibrary = true;
                loadLibrarySuccess = false;
                from = usedExternalLibrary ? "external" : usedNativeLibrary ? "native" : "upgrade";
                LogTool.i(TAG, "loadLibrary. the first time load %s library start(%s), load path(%s)", libraryName, from, loadLibraryPath);
                System.load(loadLibraryPath);
                LogTool.i(TAG, "loadLibrary. the first time load %s library successfully(%s), spend time(%s)", libraryName, from, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                loadLibrarySuccess = true;
                if (usedExternalLibrary) {
                    localLibraryFile.delete();
                }
                if (loadLibrarySuccess) {
                    return true;
                }
                if (usedNativeLibrary) {
                    return false;
                }
                if (upgradeLibraryFile.exists()) {
                    FileHelper.renameFile(upgradeLibraryFile, localLibraryFile);
                    loadLibraryPath = localLibraryFile.getAbsolutePath();
                } else if (localLibraryFile.exists()) {
                    if (!nativeLibraryFile.exists()) {
                        extractLibraryFromAssets(context, libraryName, nativeLibraryFile.getAbsolutePath());
                    }
                    loadLibraryPath = nativeLibraryFile.getAbsolutePath();
                    usedNativeLibrary = true;
                } else {
                    loadLibraryPath = localLibraryFile.getAbsolutePath();
                }
                from = usedNativeLibrary ? "native" : "upgrade";
                LogTool.i(TAG, "loadLibrary. the second time load %s library start(%s), load path(%s)", libraryName, from, loadLibraryPath);
                try {
                    System.load(loadLibraryPath);
                    LogTool.i(TAG, "loadLibrary. the second time load %s library successfully(%s), spend time(%s)", libraryName, from, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                    loadLibrarySuccess = true;
                } catch (Throwable t) {
                    LogTool.e(TAG, "loadLibrary. the second time load %s library failed(%s). %s", libraryName, from, t.toString());
                }
                if (loadLibrarySuccess) {
                    return true;
                }
                if (usedNativeLibrary) {
                    return false;
                }
                if (!nativeLibraryFile.exists()) {
                    extractLibraryFromAssets(context, libraryName, nativeLibraryFile.getAbsolutePath());
                }
                LogTool.i(TAG, "loadLibrary. the third time load %s library start(native), load path(%s)", libraryName, nativeLibraryFile.getAbsolutePath());
                try {
                    System.load(nativeLibraryFile.getAbsolutePath());
                    LogTool.i(TAG, "loadLibrary. the third time load %s library successfully(native), spend time(%s)", libraryName, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
                    return true;
                } catch (Throwable t2) {
                    LogTool.e(TAG, "loadLibrary. the third time load %s library failed(native). %s", libraryName, t2.toString());
                    return loadLibrarySuccess;
                }
            }
        }
        if (upgradeLibraryFile.exists()) {
            FileHelper.renameFile(upgradeLibraryFile, localLibraryFile);
            loadLibraryPath = localLibraryFile.getAbsolutePath();
        } else if (localLibraryFile.exists()) {
            loadLibraryPath = localLibraryFile.getAbsolutePath();
        } else {
            if (!nativeLibraryFile.exists()) {
                extractLibraryFromAssets(context, libraryName, nativeLibraryFile.getAbsolutePath());
            }
            loadLibraryPath = nativeLibraryFile.getAbsolutePath();
            usedNativeLibrary = true;
        }
        loadLibrarySuccess = false;
        if (usedExternalLibrary) {
        }
        LogTool.i(TAG, "loadLibrary. the first time load %s library start(%s), load path(%s)", libraryName, from, loadLibraryPath);
        try {
            System.load(loadLibraryPath);
            LogTool.i(TAG, "loadLibrary. the first time load %s library successfully(%s), spend time(%s)", libraryName, from, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
            loadLibrarySuccess = true;
        } catch (Throwable t22) {
            LogTool.e(TAG, "loadLibrary. the first time load %s library failed(%s). %s", libraryName, from, t22.toString());
        }
        if (usedExternalLibrary) {
            localLibraryFile.delete();
        }
        if (loadLibrarySuccess) {
            return true;
        }
        if (usedNativeLibrary) {
            return false;
        }
        if (upgradeLibraryFile.exists()) {
            FileHelper.renameFile(upgradeLibraryFile, localLibraryFile);
            loadLibraryPath = localLibraryFile.getAbsolutePath();
        } else if (localLibraryFile.exists()) {
            if (nativeLibraryFile.exists()) {
                extractLibraryFromAssets(context, libraryName, nativeLibraryFile.getAbsolutePath());
            }
            loadLibraryPath = nativeLibraryFile.getAbsolutePath();
            usedNativeLibrary = true;
        } else {
            loadLibraryPath = localLibraryFile.getAbsolutePath();
        }
        if (usedNativeLibrary) {
        }
        LogTool.i(TAG, "loadLibrary. the second time load %s library start(%s), load path(%s)", libraryName, from, loadLibraryPath);
        System.load(loadLibraryPath);
        LogTool.i(TAG, "loadLibrary. the second time load %s library successfully(%s), spend time(%s)", libraryName, from, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
        loadLibrarySuccess = true;
        if (loadLibrarySuccess) {
            return true;
        }
        if (usedNativeLibrary) {
            return false;
        }
        if (nativeLibraryFile.exists()) {
            extractLibraryFromAssets(context, libraryName, nativeLibraryFile.getAbsolutePath());
        }
        LogTool.i(TAG, "loadLibrary. the third time load %s library start(native), load path(%s)", libraryName, nativeLibraryFile.getAbsolutePath());
        System.load(nativeLibraryFile.getAbsolutePath());
        LogTool.i(TAG, "loadLibrary. the third time load %s library successfully(native), spend time(%s)", libraryName, StringUtils.formatTime((double) (System.nanoTime() - startTime)));
        return true;
    }

    public static boolean existLibraryInAssets(Context context, String libraryName) {
        if (context == null || StringUtils.isEmpty(libraryName)) {
            return false;
        }
        return FileHelper.existFileInAssets(context, String.format(FORMAT_LIB_ASSETS_NAME, new Object[]{libraryName, CpuUtils.getCpuType()}));
    }

    public static boolean extractLibraryFromAssets(Context context, String libraryName, String desLibraryPath) {
        if (context == null || StringUtils.isEmpty(libraryName)) {
            return false;
        }
        return FileHelper.extractFileFromAssets(context, String.format(FORMAT_LIB_ASSETS_NAME, new Object[]{libraryName, CpuUtils.getCpuType()}), desLibraryPath);
    }

    public static boolean libraryAvailable(Context context, String libraryName, boolean remoteVersion) {
        if (context == null || StringUtils.isEmpty(libraryName)) {
            return false;
        }
        String libraryRootPath = getLibraryRootPath(context);
        if ((sPriorityLoadExternalLib && ContextUtils.checkPermission(context, "android.permission.READ_EXTERNAL_STORAGE") && new File(libraryRootPath, getLibraryLocalName(libraryName)).exists()) || new File(libraryRootPath, getLibraryUpgradeName(libraryName)).exists() || new File(libraryRootPath, getLibraryLocalName(libraryName)).exists() || !remoteVersion) {
            return true;
        }
        return new File(libraryRootPath, getLibraryNativeName(libraryName)).exists();
    }

    public static String getLibraryRootPath(Context context) {
        if (context == null) {
            return "";
        }
        return new StringBuilder(String.valueOf(context.getFilesDir().getParent())).append(File.separator).append("cdelibs").append(File.separator).toString();
    }

    public static String getLibraryOldRootPath(Context context) {
        if (context == null) {
            return "";
        }
        return new StringBuilder(String.valueOf(context.getFilesDir().getParent())).append(File.separator).append(SimplePluginDownloadService.FLODER_NAME).append(File.separator).toString();
    }

    public static String getLibraryLocalName(String libraryName) {
        return String.format(FORMAT_LIB_LOCAL_NAME, new Object[]{libraryName});
    }

    public static String getLibraryNativeName(String libraryName) {
        return String.format(FORMAT_LIB_NATIVE_NAME, new Object[]{libraryName});
    }

    public static String getLibraryUpgradeName(String libraryName) {
        return String.format(FORMAT_LIB_UPGRADE_NAME, new Object[]{libraryName});
    }
}
