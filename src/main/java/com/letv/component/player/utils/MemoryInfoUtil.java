package com.letv.component.player.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MemoryInfoUtil {
    private static final String TAG = "MemoryInfoUtil";

    public static long getMemUnused(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1024;
    }

    public static long getTotalInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
    }

    public static long getMemTotal() {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        String content = null;
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader("/proc/meminfo"), 8);
            try {
                String line = br2.readLine();
                if (line != null) {
                    content = line;
                }
                if (br2 != null) {
                    try {
                        br2.close();
                        br = br2;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
                }
                br = br2;
            } catch (FileNotFoundException e4) {
                e2 = e4;
                br = br2;
                try {
                    Log.e(TAG, "", e2);
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
                } catch (Throwable th2) {
                    th = th2;
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e322) {
                            e322.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e322 = e5;
                br = br2;
                Log.e(TAG, "", e322);
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
                return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
            } catch (Throwable th3) {
                th = th3;
                br = br2;
                if (br != null) {
                    br.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e2 = e6;
            Log.e(TAG, "", e2);
            if (br != null) {
                br.close();
            }
            return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
        } catch (IOException e7) {
            e3222 = e7;
            Log.e(TAG, "", e3222);
            if (br != null) {
                br.close();
            }
            return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
        }
        return (long) Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim());
    }

    public static long getSDCardMemory() {
        Long sdCardInfo = Long.valueOf(0);
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long availBlocks = (long) sf.getAvailableBlocks();
            sdCardInfo = Long.valueOf(((long) sf.getBlockSize()) * ((long) sf.getBlockCount()));
        }
        return sdCardInfo.longValue();
    }
}
