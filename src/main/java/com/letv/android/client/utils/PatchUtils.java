package com.letv.android.client.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.letv.android.client.LetvApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.parser.HotPatchParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PatchUtils {
    public static boolean isPatchDownload = false;

    public PatchUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    private static File getPatchDownloadPatchDir(Context context) {
        File patch = context.getExternalCacheDir();
        if (patch == null || !patch.exists()) {
            return context.getCacheDir();
        }
        return patch;
    }

    public static void loadNewPatch() {
        Context context = LetvApplication.getInstance().getApplicationContext();
        String url = LetvUrlMaker.getDexPatchUrl();
        LogInfo.log("wlx", "url : " + url);
        new LetvRequest().setUrl(url).setParser(new HotPatchParser()).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new 1(context)).add();
    }

    private static File downLoadPatchFile(Context context, String patchUrl) throws IOException {
        if (TextUtils.isEmpty(patchUrl)) {
            return null;
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(patchUrl).openConnection();
        connection.connect();
        File file = null;
        if (connection.getResponseCode() == 200) {
            InputStream is = connection.getInputStream();
            String filename = getFileName(patchUrl);
            file = new File(getPatchDownloadPatchDir(context) + filename);
            LogInfo.log("wlx", "开始下载patch:" + filename);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[4028];
            while (true) {
                int length = is.read(buffer);
                if (length == -1) {
                    break;
                }
                fos.write(buffer, 0, length);
            }
            fos.flush();
            fos.close();
            is.close();
        }
        connection.disconnect();
        return file;
    }

    private static String getFileName(String filepath) {
        String filename = "";
        String[] strs = filepath.split("/");
        if (strs.length > 0) {
            return strs[strs.length - 1];
        }
        return filename;
    }

    public static boolean unZip(File zipFile, File outDir) {
        try {
            if (!outDir.exists() && !outDir.mkdirs()) {
                return false;
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
            while (true) {
                ZipEntry ze = zin.getNextEntry();
                if (ze != null) {
                    Log.d("wlx", "Unzipping " + ze.getName());
                    if (ze.isDirectory()) {
                        mkDir(ze.getName(), outDir.getAbsolutePath());
                    } else {
                        FileOutputStream fout = new FileOutputStream(outDir + "/" + ze.getName());
                        byte[] data = new byte[2048];
                        while (true) {
                            int count = zin.read(data, 0, 2048);
                            if (count == -1) {
                                break;
                            }
                            fout.write(data, 0, count);
                        }
                        zin.closeEntry();
                        fout.close();
                    }
                } else {
                    zin.close();
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("wlx", "unzip", e);
            return false;
        }
    }

    private static boolean mkDir(String dir, String outDir) {
        File f = new File(outDir + "/" + dir);
        return !f.isDirectory() && f.mkdirs();
    }
}
