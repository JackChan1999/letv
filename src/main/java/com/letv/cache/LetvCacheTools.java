package com.letv.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.WindowManager;
import com.letv.pp.utils.NetworkUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class LetvCacheTools {

    public static class BasicParams {
        public static int cachePoolNum = 30;
        public static int locationThreadPoolNum = 3;
        public static int netThreadPoolNum = 5;

        public static void init(int cachePoolNum, int locationThreadPoolNum, int netThreadPoolNum) {
            locationThreadPoolNum = locationThreadPoolNum;
            netThreadPoolNum = netThreadPoolNum;
            cachePoolNum = cachePoolNum;
        }
    }

    public static class ConstantTool {
        public static final String DATA_PATH = (Environment.getExternalStorageDirectory().getPath() + "/Letv/");
        public static final String IMAGE_CACHE_PATH = (DATA_PATH + "cache/pics/");
    }

    public static class SDCardTool {

        public interface cleanCacheListener {
            void onComplete();

            void onErr();

            void onNull();

            void onStar();
        }

        public static boolean sdCardMounted() {
            String state = Environment.getExternalStorageState();
            if (!state.equals("mounted") || state.equals("mounted_ro")) {
                return false;
            }
            return true;
        }

        public static boolean checkCacheDirectory(String path) {
            File dir = new File(path);
            if (dir.exists()) {
                return true;
            }
            return dir.mkdirs();
        }

        public static long getAvailableSdCardSize() {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        }

        public static int getImageSize(Bitmap bmp) {
            if (bmp == null) {
                return 0;
            }
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(CompressFormat.PNG, 100, baos);
                int size = baos.size();
                baos.flush();
                baos.close();
                return size;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }

        public static String FormetFileSize(long fileS) {
            String fileSizeString = "";
            return " " + (new DecimalFormat("#0.00").format(((double) fileS) / 1048576.0d) + "M") + " ";
        }

        public static long getFileSize(File f) {
            long size = 0;
            File[] flist = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size += getFileSize(flist[i]);
                } else {
                    size += flist[i].length();
                }
            }
            return size;
        }

        public static void deleteAllFile(final String filePath, final cleanCacheListener listener) {
            new Thread(new Runnable() {
                public void run() {
                    File display = new File(filePath);
                    if (display.exists()) {
                        listener.onStar();
                        File[] items = display.listFiles();
                        int i = display.listFiles().length;
                        for (int j = 0; j < i; j++) {
                            if (items[j].isFile()) {
                                items[j].delete();
                            }
                            display.delete();
                        }
                        listener.onComplete();
                        return;
                    }
                    listener.onNull();
                }
            }).start();
        }
    }

    public static class StringTool {
        public static String createFileName(String url) {
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            try {
                String name = url.replace(NetworkUtils.DELIMITER_COLON, "").replace("/", "");
                return name.replace(".", "") + name.substring(name.lastIndexOf("."), name.length()) + "letvimage";
            } catch (Exception e) {
                return null;
            }
        }

        public static String createFileNameByPath(String path) {
            if (TextUtils.isEmpty(path)) {
                return null;
            }
            String name = path.replace("/", "");
            return name.replace(".", "") + name.substring(name.lastIndexOf("."), name.length()) + "letvimage";
        }

        public static String createFilePath(String url) {
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return ConstantTool.IMAGE_CACHE_PATH + createFileName(url);
        }

        public static String createFilePath2(String url) {
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            String name = url.replace(NetworkUtils.DELIMITER_COLON, "").replace("/", "");
            return ConstantTool.IMAGE_CACHE_PATH + (name.replace(".", "") + name.substring(name.lastIndexOf("."), name.length()));
        }
    }

    public static class UITool {
        public static int getScreenWidth(Context context) {
            return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
        }

        public static int getScreenHeight(Context context) {
            return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
        }
    }
}
