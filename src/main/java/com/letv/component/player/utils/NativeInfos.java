package com.letv.component.player.utils;

import android.text.TextUtils;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;

public class NativeInfos {
    public static String CPUClock = null;
    public static String CPUFeatures = null;
    private static final int CPU_Core_Nums_Eight = 8;
    private static final int CPU_Core_Nums_Four = 4;
    public static final int SUPPORT_MP4_LEVEL = 0;
    public static final int SUPPORT_TS1000K_LEVEL = 3;
    public static final int SUPPORT_TS1080P_LEVEL = 7;
    public static final int SUPPORT_TS1300K_LEVEL = 4;
    public static final int SUPPORT_TS180K_LEVEL = 5;
    public static final int SUPPORT_TS350K_LEVEL = 1;
    public static final int SUPPORT_TS720P_LEVEL = 6;
    public static final int SUPPORT_TS800K_LEVEL = 2;
    private static final String TAG = "NativeInfos";
    public static boolean mIfNative3gpOrMp4 = false;
    public static boolean mIsLive = false;
    public static boolean mOffLinePlay = false;

    public static String getCPUFeatures() {
        if (!TextUtils.isEmpty(CPUFeatures)) {
            return CPUFeatures;
        }
        Map<String, Object> map = NativeCMD.runCmd("cat /proc/cpuinfo");
        if (map == null) {
            CPUFeatures = "Sorry, Run Cmd Failure !!!";
            return CPUFeatures;
        }
        InputStream in = (InputStream) map.get("input");
        InputStreamReader is = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(is);
        StringBuilder sb = new StringBuilder();
        String str = "";
        while (true) {
            try {
                str = br.readLine();
                if (str == null) {
                    break;
                } else if (str.indexOf("Features") != -1) {
                    sb.append(str);
                }
            } catch (Exception e) {
                CPUFeatures = "Read InputStream Failure !!!";
                String str2 = CPUFeatures;
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e2) {
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e3) {
                    }
                }
                if (in == null) {
                    return str2;
                }
                try {
                    in.close();
                    return str2;
                } catch (IOException e4) {
                    return str2;
                }
            } catch (Throwable th) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e5) {
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e6) {
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e7) {
                    }
                }
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e8) {
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e9) {
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e10) {
            }
        }
        CPUFeatures = sb.toString();
        return CPUFeatures;
    }

    public static String getCPUClock() throws Exception {
        if (!TextUtils.isEmpty(CPUClock)) {
            return CPUClock;
        }
        Map<String, Object> map = NativeCMD.runCmd("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
        if (map == null) {
            CPUClock = "Sorry, Run Cmd Failure !!!";
            return CPUClock;
        }
        InputStream in = (InputStream) map.get("input");
        InputStreamReader is = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(is);
        StringBuilder sb = new StringBuilder();
        String str = "";
        while (true) {
            try {
                str = br.readLine();
                if (str == null) {
                    break;
                }
                sb.append(str);
            } catch (Exception e) {
                CPUClock = "Read InputStream Failure !!!";
                String str2 = CPUClock;
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e2) {
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e3) {
                    }
                }
                if (in == null) {
                    return str2;
                }
                try {
                    in.close();
                    return str2;
                } catch (IOException e4) {
                    return str2;
                }
            } catch (Throwable th) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e5) {
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e6) {
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e7) {
                    }
                }
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e8) {
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e9) {
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e10) {
            }
        }
        CPUClock = sb.toString();
        return CPUClock;
    }

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

    public static boolean ifSupportVfpOrNeon() {
        String ret = getCPUFeatures();
        if (ret.indexOf("neon") == -1 && ret.indexOf("vfp") == -1 && ret.indexOf("asimd") == -1) {
            return false;
        }
        return true;
    }

    public static int getSupportLevel() {
        int cpuClock = 0;
        int NumCores = 0;
        try {
            cpuClock = Integer.parseInt(getCPUClock());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            NumCores = getNumCores();
        } catch (Exception eu) {
            eu.printStackTrace();
        }
        if (NumCores < 4) {
            if (cpuClock < 900000) {
                return 0;
            }
            if (cpuClock >= 900000 && cpuClock < 1200000) {
                return 1;
            }
            if (cpuClock < 1200000 || cpuClock >= 1536000) {
                return 4;
            }
            return 3;
        } else if (NumCores < 4 || NumCores >= 8) {
            if (cpuClock >= 2000000) {
                return 7;
            }
            return 6;
        } else if (cpuClock < 1000000) {
            return 5;
        } else {
            if (cpuClock >= 1000000 && cpuClock < 1100000) {
                return 1;
            }
            if (cpuClock >= 1100000 && cpuClock < 1200000) {
                return 3;
            }
            if (cpuClock < 1200000 || cpuClock >= 1600000) {
                return 6;
            }
            return 4;
        }
    }

    public static boolean ifNativePlayer() {
        if (mIsLive) {
            return true;
        }
        if (getSupportLevel() == 0 || ((mOffLinePlay && mIfNative3gpOrMp4) || !ifSupportVfpOrNeon())) {
            return false;
        }
        return true;
    }

    public static void doWithNativePlayUrl(String url) {
        url = url.toLowerCase();
        if (url.indexOf(".letv") != -1 || url.indexOf(".3gp") != -1 || url.indexOf(".mp4") != -1) {
            mIfNative3gpOrMp4 = true;
        } else if (url.startsWith(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
            mIfNative3gpOrMp4 = true;
        } else {
            mIfNative3gpOrMp4 = false;
        }
    }
}
