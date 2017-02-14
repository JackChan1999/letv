package com.letv.component.player.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class CpuInfosUtils {
    private static final String TAG = "CpuInfosUtils";

    public static int getNumCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static float getCurCpuFrequence() {
        try {
            String line = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"}).start().getInputStream())).readLine();
            if (line != null) {
                return ((float) Long.parseLong(line)) / 1000000.0f;
            }
            return 0.0f;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "获取cpu当前主频发生错误。");
            return getMaxCpuFrequence();
        }
    }

    public static float getMaxCpuFrequence() {
        try {
            String line = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).start().getInputStream())).readLine();
            if (line != null) {
                return ((float) Long.parseLong(line)) / 1000000.0f;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "获取cpu最大主频发生错误。");
        }
        return 0.0f;
    }

    public static float getMinCpuFrequence() {
        try {
            String line = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"}).start().getInputStream())).readLine();
            LogTag.i(TAG, "cpu最小主频 =" + line);
            if (line != null) {
                return ((float) Long.parseLong(line)) / 1000000.0f;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            LogTag.i(TAG, "获取cpu最小主频发生错误。");
        }
        return 0.0f;
    }

    public static boolean ifSupportNeon() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[]{"/system/bin/cat", "/proc/cpuinfo"}).start().getInputStream()));
            while (true) {
                String l = reader.readLine();
                if (l == null) {
                    break;
                } else if (l.indexOf("Features") != -1) {
                    result.append(l);
                }
            }
            LogTag.i(TAG, result.toString());
        } catch (IOException ex) {
            result.append("Read InputStream Failure !!!");
            ex.printStackTrace();
        }
        if (result.toString().indexOf("neon") != -1) {
            return true;
        }
        return false;
    }

    public static String getCpuInfo() {
        String str2 = "";
        String[] cpuInfo = new String[]{"", ""};
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] arrayOfString = localBufferedReader.readLine().split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            cpuInfo[1] = cpuInfo[1] + localBufferedReader.readLine().split("\\s+")[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo[0].toString();
    }
}
