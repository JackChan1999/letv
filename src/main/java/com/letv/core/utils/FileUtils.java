package com.letv.core.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.letv.core.BaseApplication;
import com.letv.core.bean.DownloadLocalVideoItemBean;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.media.NativeThumbnail;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
    private static final String DATA_INFO_FOLDER = "Letv/storage/relevant_data/";
    private static final String IMAGE_CACHE_FOLDER = "cache/pics/";
    public static final String ROOT_FOLDER = "Letv/";
    public static final String ROOT_FOLDER_SMALL = "letv/";
    private static final String THEME_IMAGE_CACHE_FOLDER = "cache/theme/";
    public static final String VIDEOSHOT_DOWNLOAD_INTENT_BROADCAST = "DOWNLOAD_INTENT_BROADCAST";
    public static final String VIDEOSHOT_PIC_NAME_DIVIDER = "uts";

    public static boolean saveBitmap2File(android.content.Context r10, java.lang.String r11, android.graphics.Bitmap r12) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r6 = 0;
        r7 = "fornia";
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r8 = r8.append(r11);
        r9 = "|";
        r8 = r8.append(r9);
        r8 = r8.append(r12);
        r8 = r8.toString();
        com.letv.core.utils.LogInfo.log(r7, r8);
        r7 = android.text.TextUtils.isEmpty(r11);
        if (r7 == 0) goto L_0x0025;
    L_0x0024:
        return r6;
    L_0x0025:
        if (r12 == 0) goto L_0x0024;
    L_0x0027:
        r1 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r1.<init>(r11);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r7 = r1.exists();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        if (r7 != 0) goto L_0x0035;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
    L_0x0032:
        r1.createNewFile();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
    L_0x0035:
        r4 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r4.<init>(r1);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r7 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r8 = 100;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r12.compress(r7, r8, r4);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r4.flush();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r4.close();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r4 = 0;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        if (r10 == 0) goto L_0x006d;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
    L_0x004a:
        r7 = "fornia";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r8 = "！！！！！发送截图保存文件广播";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        com.letv.core.utils.LogInfo.log(r7, r8);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r2 = new android.content.Intent;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r7 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r2.<init>(r7);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r5 = android.net.Uri.fromFile(r1);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r2.setData(r5);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r10.sendBroadcast(r2);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r3 = new android.content.Intent;	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r7 = "DOWNLOAD_INTENT_BROADCAST";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r3.<init>(r7);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r10.sendBroadcast(r3);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
    L_0x006d:
        if (r12 == 0) goto L_0x0078;
    L_0x006f:
        r6 = r12.isRecycled();
        if (r6 != 0) goto L_0x0078;
    L_0x0075:
        r12.recycle();
    L_0x0078:
        r6 = 1;
        goto L_0x0024;
    L_0x007a:
        r0 = move-exception;
        r7 = "fornia";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r8 = r0.getMessage();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        com.letv.core.utils.LogInfo.log(r7, r8);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        if (r12 == 0) goto L_0x0024;
    L_0x0086:
        r7 = r12.isRecycled();
        if (r7 != 0) goto L_0x0024;
    L_0x008c:
        r12.recycle();
        goto L_0x0024;
    L_0x0090:
        r0 = move-exception;
        r7 = "fornia";	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        r8 = r0.getMessage();	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        com.letv.core.utils.LogInfo.log(r7, r8);	 Catch:{ FileNotFoundException -> 0x007a, IOException -> 0x0090, all -> 0x00a7 }
        if (r12 == 0) goto L_0x0024;
    L_0x009c:
        r7 = r12.isRecycled();
        if (r7 != 0) goto L_0x0024;
    L_0x00a2:
        r12.recycle();
        goto L_0x0024;
    L_0x00a7:
        r6 = move-exception;
        if (r12 == 0) goto L_0x00b3;
    L_0x00aa:
        r7 = r12.isRecycled();
        if (r7 != 0) goto L_0x00b3;
    L_0x00b0:
        r12.recycle();
    L_0x00b3:
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.utils.FileUtils.saveBitmap2File(android.content.Context, java.lang.String, android.graphics.Bitmap):boolean");
    }

    public static String getSDCardPath(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getPath() + File.separator + ROOT_FOLDER;
        }
        return null;
    }

    public static String getSysCachePath(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        }
        return context.getCacheDir().getPath();
    }

    public static String getBitmapCachePath(Context context) {
        return getCachePath(IMAGE_CACHE_FOLDER);
    }

    public static String getThemeImageCachePath() {
        return getCachePath(THEME_IMAGE_CACHE_FOLDER);
    }

    private static String getCachePath(String dir) {
        String path = dir == null ? "" : dir.trim();
        File dirFile = new File(Environment.getExternalStorageDirectory(), ROOT_FOLDER + path);
        if (!(dirFile.exists() || dirFile.mkdirs())) {
            dirFile = new File(Environment.getExternalStorageDirectory(), ROOT_FOLDER_SMALL + path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
        if (dirFile != null) {
            return dirFile.getAbsolutePath();
        }
        return "";
    }

    public static boolean saveBitmap(Context context, Bitmap bp) {
        return saveBitmap2File(context, StoreUtils.getLocalRestoreNomediaPath(context, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH) + (LetvDateUtils.getFormatPhotoName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + ".jpg"), bp);
    }

    public static boolean saveBitmap(Context context, Bitmap bp, String picFullPath) {
        return saveBitmap2File(context, picFullPath, bp);
    }

    public static String getFileNameAddedTimestemp(String picName, long timeStemp) {
        return picName + (TextUtils.isEmpty(picName) ? "" : "") + StringUtils.getNumberTextTime(timeStemp) + VIDEOSHOT_PIC_NAME_DIVIDER + timeStemp;
    }

    public static String getTempSaveFilePath(Context context, String picName) {
        return StoreUtils.getLocalRestoreNomediaPath(context, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH) + (picName + ".jpg");
    }

    public static String getVideoshotTimestemp(String picName) {
        if (picName.contains(VIDEOSHOT_PIC_NAME_DIVIDER)) {
            return getFileNameWithoutSuf(getFileName(picName)).split(VIDEOSHOT_PIC_NAME_DIVIDER)[1];
        }
        return null;
    }

    public static String getVideoshotPicName(String picName) {
        if (picName.contains(VIDEOSHOT_PIC_NAME_DIVIDER)) {
            return getFileNameWithoutSuf(getFileName(picName)).split(VIDEOSHOT_PIC_NAME_DIVIDER)[0] + ".jpg";
        }
        return getFileNameWithoutSuf(getFileName(picName));
    }

    public static boolean saveBitmapByUser(Context context, Bitmap bp) {
        return saveBitmap2File(context, StoreUtils.getLocalRestorePath(context, StoreUtils.VIDEOSHOT_PIC_SAVE_PATH) + (LetvDateUtils.getFormatPhotoName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + ".jpg"), bp);
    }

    public static void clearPicsAfterChangeVideo(Context context) {
        new Thread(new 1(context)).start();
    }

    public static boolean cleanFiles(String filePath) {
        int i = 0;
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File dir = new File(filePath);
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                File[] listFiles = dir.listFiles();
                int length = listFiles.length;
                while (i < length) {
                    listFiles[i].delete();
                    i++;
                }
            }
        }
        return true;
    }

    public static boolean copyFile(String from, String to) {
        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(to) || from.equalsIgnoreCase(to)) {
            return false;
        }
        File fileFrom = new File(from);
        File fileTo = new File(to);
        try {
            FileInputStream fis = new FileInputStream(fileFrom);
            FileOutputStream fos = new FileOutputStream(fileTo);
            byte[] buffer = new byte[1024];
            while (true) {
                int cnt = fis.read(buffer);
                if (cnt != -1) {
                    fos.write(buffer, 0, cnt);
                } else {
                    fis.close();
                    fos.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            Log.i("fornia", e.getMessage());
            return false;
        } catch (IOException e2) {
            Log.i("fornia", e2.getMessage());
            return false;
        }
    }

    public static List<File> getFiles(Context context, String dir) {
        List<File> fileList = new ArrayList();
        LogInfo.log("fornia", "getFiles: context:" + context + "|dir:" + dir);
        if (!(context == null || TextUtils.isEmpty(dir))) {
            String tempDirString = StoreUtils.getLocalRestorePath(context, dir);
            LogInfo.log("fornia", "getFiles: tempDirString:" + tempDirString);
            if (!TextUtils.isEmpty(tempDirString)) {
                File tempDir = new File(tempDirString);
                if (tempDir != null && tempDir.isDirectory()) {
                    File[] pics = tempDir.listFiles();
                    LogInfo.log("fornia", "getFiles pics:" + pics);
                    if (!(pics == null || pics.length == 0)) {
                        for (File file : pics) {
                            if (!file.isDirectory() && file.length() > 0) {
                                fileList.add(file);
                            }
                        }
                    }
                }
            }
        }
        return fileList;
    }

    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while (true) {
                int n = fis.read(b);
                if (n == -1) {
                    break;
                }
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return buffer;
    }

    public static List<File> getFilesByFullPath(Context context, String dir) {
        List<File> fileList = new ArrayList();
        LogInfo.log("fornia", "getFiles: context:" + context + "|dir:" + dir);
        if (!(context == null || TextUtils.isEmpty(dir))) {
            File tempDir = new File(dir);
            if (tempDir != null && tempDir.isDirectory()) {
                File[] pics = tempDir.listFiles();
                LogInfo.log("fornia", "getFiles pics:" + pics);
                if (!(pics == null || pics.length == 0)) {
                    for (File file : pics) {
                        if (!file.isDirectory() && file.length() > 0) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
        return fileList;
    }

    public static boolean hasValidFile(Context context, String dir) {
        LogInfo.log("fornia", "hasValidFile: context:" + context + "|dir:" + dir);
        if (context == null || TextUtils.isEmpty(dir)) {
            return false;
        }
        String tempDirString = StoreUtils.getLocalRestorePath(context, dir);
        if (TextUtils.isEmpty(tempDirString)) {
            return false;
        }
        File tempDir = new File(tempDirString);
        if (tempDir == null || !tempDir.isDirectory()) {
            return false;
        }
        File[] pics = tempDir.listFiles();
        LogInfo.log("fornia", "getFiles pics:" + pics);
        if (pics == null || pics.length == 0) {
            return false;
        }
        for (File file : pics) {
            if (!file.isDirectory() && file.length() > 0) {
                return true;
            }
        }
        return false;
    }

    public static int getFileCount(Context context, String dir) {
        int i = 0;
        if (TextUtils.isEmpty(dir)) {
            return 0;
        }
        if (TextUtils.isEmpty(StoreUtils.getLocalRestorePath(context, dir))) {
            return 0;
        }
        int validCount = 0;
        File tempDir = new File(StoreUtils.getLocalRestorePath(context, dir));
        if (tempDir == null || !tempDir.isDirectory()) {
            return 0;
        }
        File[] pics = tempDir.listFiles();
        LogInfo.log("fornia", "getFiles pics:" + pics);
        if (pics == null || pics.length == 0) {
            return 0;
        }
        int length = pics.length;
        while (i < length) {
            File file = pics[i];
            if (!file.isDirectory() && file.length() > 0) {
                validCount++;
            }
            i++;
        }
        return validCount;
    }

    public static String getFileName(String file) {
        if (TextUtils.isEmpty(file)) {
            return "";
        }
        return file.substring(file.lastIndexOf("/") + 1);
    }

    public static String getFileDir(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        LogInfo.log("fornia", "filePath.substring(0, filePath.lastIndexOf(\"/\")):" + filePath.substring(0, filePath.lastIndexOf("/")));
        return filePath.substring(0, filePath.lastIndexOf("/") + 1);
    }

    public static String getFileSuf(String file) {
        if (TextUtils.isEmpty(file)) {
            return "";
        }
        String name = file.substring(file.lastIndexOf("/") + 1);
        String[] arrayStrings = name.split("\\.");
        return arrayStrings[1] != null ? arrayStrings[1] : name;
    }

    public static String getFileNameWithoutSuf(String file) {
        if (TextUtils.isEmpty(file)) {
            return "";
        }
        String name = file.substring(file.lastIndexOf("/") + 1);
        String[] arrayStrings = name.split("\\.");
        return arrayStrings[0] != null ? arrayStrings[0] : name;
    }

    public static StateListDrawable createStateDrawable(Context context, String imageNormal, String imagePressed, String imageFocused, String imageChecked) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = TextUtils.isEmpty(imageNormal) ? null : createDrawableByName(context, imageNormal);
        Drawable pressed = TextUtils.isEmpty(imagePressed) ? null : createDrawableByName(context, imagePressed);
        Drawable focus;
        if (TextUtils.isEmpty(imageFocused)) {
            focus = null;
        } else {
            focus = createDrawableByName(context, imageFocused);
        }
        Drawable checked = TextUtils.isEmpty(imageChecked) ? null : createDrawableByName(context, imageChecked);
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{16842912}, checked);
        sd.addState(new int[]{16842913}, pressed);
        sd.addState(new int[0], normal);
        return sd;
    }

    public static Drawable createDrawableByName(Context context, String imagePath) {
        Drawable dw = null;
        try {
            if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists() || new File(imagePath).length() <= 0) {
                return null;
            }
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            context.getResources().getDisplayMetrics();
            bm.setDensity(240);
            dw = new BitmapDrawable(context.getResources(), bm);
            return dw;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
            BaseApplication.getInstance().onAppMemoryLow();
        }
    }

    public static String getRelevantData(Context context, String filename) {
        Exception e;
        Throwable th;
        File file = getRelevantFile(context, filename);
        if (!TextUtils.isEmpty(filename)) {
            ObjectInputStream in = null;
            try {
                ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(file));
                try {
                    String str = (String) in2.readObject();
                    if (in2 == null) {
                        return str;
                    }
                    try {
                        in2.close();
                        return str;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return str;
                    }
                } catch (Exception e3) {
                    e = e3;
                    in = in2;
                    try {
                        e.printStackTrace();
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    in = in2;
                    if (in != null) {
                        in.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (in != null) {
                    in.close();
                }
                return null;
            }
        }
        return null;
    }

    public static void saveRelevantData(Context context, String filename, String data) {
        Exception e;
        Throwable th;
        File file = getRelevantFile(context, filename);
        if (file != null && !TextUtils.isEmpty(data)) {
            ObjectOutputStream out = null;
            try {
                ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(file));
                try {
                    out2.writeObject(data);
                    out2.flush();
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    out = out2;
                    try {
                        e.printStackTrace();
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    private static File getRelevantFile(Context context, String filename) {
        if (TextUtils.isEmpty(getSDCardPath(context))) {
            return null;
        }
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), DATA_INFO_FOLDER);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            File file = new File(dir, filename);
            if (file.exists()) {
                return file;
            }
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveApiFileCache(Context context, String cacheName, String data) {
        boolean z = false;
        if (!(context == null || TextUtils.isEmpty(cacheName))) {
            BaseTypeUtils.ensureStringValidate(data);
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(cacheName, 0);
                fos.write(data.getBytes());
                z = true;
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    public static String getApiFileCache(Context context, String cacheName) {
        if (context == null || TextUtils.isEmpty(cacheName)) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(cacheName);
            if (fis != null) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                String str = new String(buffer);
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return str;
            } else if (fis == null) {
                return null;
            } else {
                try {
                    fis.close();
                    return null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
            if (fis == null) {
                return null;
            }
            try {
                fis.close();
                return null;
            } catch (Exception e222) {
                e222.printStackTrace();
                return null;
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e2222) {
                    e2222.printStackTrace();
                }
            }
        }
    }

    public static void deleteApiFileCache(Context context, String cacheName) {
        if (context != null && !TextUtils.isEmpty(cacheName)) {
            context.deleteFile(cacheName);
        }
    }

    public static void deleteAllApiFileCache(Context context) {
        if (context != null) {
            String[] fileNames = context.fileList();
            if (!BaseTypeUtils.isArrayEmpty(fileNames)) {
                synchronized (fileNames) {
                    for (String cacheName : fileNames) {
                        context.deleteFile(cacheName);
                    }
                }
            }
        }
    }

    public File getDownloadDir() {
        File downloadDir = new File(PreferencesManager.getInstance().getDownloadLocation());
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    public static DownloadLocalVideoItemBean fileToLocalVideoItem(File file) {
        DownloadLocalVideoItemBean item = new DownloadLocalVideoItemBean();
        NativeThumbnail retriever = new NativeThumbnail(file.getAbsolutePath());
        item.fileDate = file.lastModified();
        item.fileDuration = (long) retriever.getDuration();
        item.fileSize = file.length();
        item.path = file.getAbsolutePath();
        item.position = -1000;
        item.title = pathToTitle(file);
        item.video_W_H = retriever.getResolution();
        item.videoType = fileToType(file);
        return item;
    }

    public static DownloadLocalVideoItemBean fileToLocalVideoItemBySystem(File file) {
        DownloadLocalVideoItemBean item = new DownloadLocalVideoItemBean();
        LetvMediaMetadataRetriever retriever = new LetvMediaMetadataRetriever();
        retriever.setDataSource(file.getAbsolutePath());
        item.fileDate = file.lastModified();
        item.fileDuration = retriever.getFileDuration();
        item.fileSize = file.length();
        item.path = file.getAbsolutePath();
        item.position = -1000;
        item.title = pathToTitle(file);
        item.video_W_H = retriever.getVideo_W_H();
        item.videoType = fileToType(file);
        return item;
    }

    public static String fileToType(File file) {
        String path = file.getAbsolutePath();
        String type = null;
        if (path.lastIndexOf(46) != -1) {
            type = path.substring(path.lastIndexOf(46), path.length());
        }
        if (type == null) {
            return "";
        }
        return type.replace(".", "").toLowerCase();
    }

    public static String pathToTitle(File file) {
        if (file != null) {
            return pathToTitle(file.getName());
        }
        return "";
    }

    public static String pathToTitle(String fileName) {
        String tmp;
        if (fileName == null || "".equals(fileName)) {
            tmp = "";
        } else {
            tmp = fileName.substring(0, fileName.lastIndexOf("."));
        }
        LogInfo.log("pathToTitle", "pathToTitle = " + tmp);
        return tmp;
    }

    public static void checkFileIsEnabledPath(ArrayList<DownloadLocalVideoItemBean> listFiles) {
        int i = 0;
        while (i < listFiles.size()) {
            if (!checkFileIsEnabledPath(((DownloadLocalVideoItemBean) listFiles.get(i)).path)) {
                listFiles.remove(i);
                i--;
            }
            i++;
        }
    }

    public static boolean checkFileIsEnabledPath(String path) {
        if (path == null || "".equals(path)) {
            return false;
        }
        return checkFileIsEnabledPath(new File(path));
    }

    public static boolean checkFileIsEnabledPath(File file) {
        return file != null && file.exists();
    }

    public String timeToString(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(time));
    }

    public static String longToMbString(long bytesize) {
        DecimalFormat df = new DecimalFormat("0.##");
        Double btypesizedouble = new Double((double) bytesize);
        Double sizefloat = Double.valueOf(0.0d);
        String suffix = "";
        if (bytesize == 0) {
            sizefloat = Double.valueOf(0.0d);
            suffix = "MB";
        } else if (bytesize < 1024) {
            sizefloat = btypesizedouble;
            suffix = "bytes";
        } else if (bytesize >= 1024 && bytesize < 1048576) {
            sizefloat = Double.valueOf(btypesizedouble.doubleValue() / 1024.0d);
            suffix = "KB";
        } else if (bytesize >= 1048576 && bytesize < 1073741824) {
            sizefloat = Double.valueOf((btypesizedouble.doubleValue() / 1024.0d) / 1024.0d);
            suffix = "MB";
        } else if (bytesize >= 1073741824) {
            sizefloat = Double.valueOf(((btypesizedouble.doubleValue() / 1024.0d) / 1024.0d) / 1024.0d);
            suffix = "GB";
        }
        return df.format(sizefloat) + suffix;
    }

    public static Bitmap getBitmapForPreview(Bitmap image) {
        if (image == null || image.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 10000) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 5, baos);
        } else if (baos.toByteArray().length / 1024 > 1000) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 20, baos);
        } else if (baos.toByteArray().length / 1024 > 600) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 45, baos);
        } else if (baos.toByteArray().length / 1024 > LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 55, baos);
        } else if (baos.toByteArray().length / 1024 > 400) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 65, baos);
        } else if (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 75, baos);
        } else if (baos.toByteArray().length / 1024 > 200) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 85, baos);
        } else if (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 90, baos);
        } else {
            baos.reset();
            image.compress(CompressFormat.JPEG, 100, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > 120.0f) {
            be = (int) (((float) newOpts.outWidth) / 120.0f);
        } else if (w < h && ((float) h) > 160.0f) {
            be = (int) (((float) newOpts.outHeight) / 160.0f);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, newOpts);
    }

    public static Bitmap getBitmapForShare(String path) {
        LogInfo.log("fornia", "path:" + path);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (options != null) {
            int maxSize;
            Rect rect = new Rect(0, 0, 100, 60);
            int w = rect.width();
            int h = rect.height();
            if (w > h) {
                maxSize = w;
            } else {
                maxSize = h;
            }
            options.inSampleSize = computeSampleSize(options, maxSize, w * h);
            options.inJustDecodeBounds = false;
        }
        LogInfo.log("fornia", "options:" + options);
        Bitmap image = BitmapFactory.decodeStream(in, null, options);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 10000) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 5, baos);
        } else if (baos.toByteArray().length / 1024 > 1000) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 20, baos);
        } else if (baos.toByteArray().length / 1024 > 600) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 45, baos);
        } else if (baos.toByteArray().length / 1024 > 500) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 65, baos);
        } else if (baos.toByteArray().length / 1024 > 400) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 75, baos);
        } else if (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 85, baos);
        } else if (baos.toByteArray().length / 1024 > 200) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 95, baos);
        } else {
            baos.reset();
            image.compress(CompressFormat.JPEG, 100, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        LogInfo.log("fornia", "share pic !!!!!!!!");
        return BitmapFactory.decodeStream(isBm);
    }

    public static Bitmap getBitmapByPath(String path, int screenWidth, int screenHeight) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            FileInputStream in = new FileInputStream(file);
            if (options != null) {
                int maxSize;
                Rect r = new Rect(0, 0, screenWidth, screenHeight);
                int w = r.width();
                int h = r.height();
                if (w > h) {
                    maxSize = w;
                } else {
                    maxSize = h;
                }
                options.inSampleSize = computeSampleSize(options, maxSize, w * h);
                options.inJustDecodeBounds = false;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        throw new FileNotFoundException();
    }

    public static Bitmap getBitmapByPath(String filename) {
        if (!checkFileIsEnabledPath(filename)) {
            return null;
        }
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        if (bitmap != null) {
            bitmap.recycle();
        }
        int be = 1;
        if (w > h && ((float) w) > 300.0f) {
            be = (int) (((float) newOpts.outWidth) / 300.0f);
        } else if (w < h && ((float) h) > 400.0f) {
            be = (int) (((float) newOpts.outHeight) / 400.0f);
        }
        if (be <= 0) {
            be = 1;
        }
        Options newOpts2 = new Options();
        newOpts2.inSampleSize = be;
        newOpts2.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, newOpts2);
    }

    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        if (initialSize > 8) {
            return ((initialSize + 7) / 8) * 8;
        }
        int roundedSize = 1;
        while (roundedSize < initialSize) {
            roundedSize <<= 1;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        int lowerBound = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt((w * h) / ((double) maxNumOfPixels)));
        int upperBound = minSideLength == -1 ? 128 : (int) Math.min(Math.floor(w / ((double) minSideLength)), Math.floor(h / ((double) minSideLength)));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1;
        }
        if (minSideLength != -1) {
            return upperBound;
        }
        return lowerBound;
    }

    public static void deleteFile(File file) {
        new 2(file).execute(new Void[0]);
    }

    public static void copyBigDataToSD(Context context, String strOutFileName) {
        try {
            LogInfo.log("LM", "strOutFileName  " + strOutFileName);
            File file = new File(strOutFileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            File files = new File(strOutFileName + "/letv_icon.png");
            files.createNewFile();
            OutputStream myOutput = new FileOutputStream(files.getAbsolutePath());
            InputStream myInput = context.getResources().getAssets().open("letv_icon.png");
            byte[] buffer = new byte[1024];
            for (int length = myInput.read(buffer); length > 0; length = myInput.read(buffer)) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static Bitmap clipRectBitmap(Bitmap bitmap, int posx, int posy, int width, int height) {
        LogInfo.log("fornia", "clipRectBitmap bitmap:" + bitmap + "|posx:" + posx + "|posy" + posy + "|width:" + width + "|height:" + height);
        if (bitmap == null) {
            LogInfo.log("fornia", "clipRectBitmap 图片为null");
            return null;
        }
        int widthSrc = bitmap.getWidth();
        int heightSrc = bitmap.getHeight();
        if (posx < 0) {
            posx = 0;
        }
        if (posx > widthSrc) {
            LogInfo.log("fornia", "clipRectBitmap 起始坐标x异常");
            return null;
        }
        if (posy < 0) {
            posy = 0;
        }
        if (posy > heightSrc) {
            LogInfo.log("fornia", "clipRectBitmap 起始坐标y异常");
            return null;
        } else if (width <= 0 || height <= 0) {
            LogInfo.log("fornia", "clipRectBitmap 目标长宽小于0异常");
            return null;
        } else if (posx + width > widthSrc || posy + height > heightSrc) {
            LogInfo.log("fornia", "clipRectBitmap 目标长宽超过原图");
            return null;
        } else {
            try {
                Bitmap result = Bitmap.createBitmap(bitmap, posx, posy, width, height);
                LogInfo.log("fornia", "clipRectBitmap result:" + result);
                return result;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static Bitmap clipRectBitmap(Bitmap bitmap, int[] pos0, int[] pos1) {
        LogInfo.log("fornia", "clipRectBitmap bitmap:" + bitmap + "|pos0[0]:" + pos0[0] + "|pos0[1]" + pos0[1] + "|pos1[0]:" + pos1[0] + "|pos1[1]" + pos1[1]);
        return clipRectBitmap(bitmap, pos0[0], pos0[1], pos1[0] - pos0[0], pos1[1] - pos0[1]);
    }

    private Bitmap takeScreenShot(Activity context) {
        View view = context.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap screenBitmap = view.getDrawingCache();
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return screenBitmap;
    }

    public static void saveObjectToFile(LetvBaseBean baseBean, String fileName) {
        String path = getCachePath("");
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path + fileName);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(baseBean);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static LetvBaseBean getObjectFromFile(String fileName) {
        String path = getCachePath("");
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        LetvBaseBean letvBaseBean = null;
        try {
            File file = new File(path + fileName);
            file.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            letvBaseBean = (LetvBaseBean) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return letvBaseBean;
        } catch (Exception e) {
            e.printStackTrace();
            return letvBaseBean;
        }
    }
}
