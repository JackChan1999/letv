package com.letv.component.player.utils;

public class SoftDecodeUtils {
    private static final int CPU_CORE_NUM_FOUR = 4;
    private static final int CPU_CORE_NUM_ONE = 1;
    private static final int CPU_CORE_NUM_TWO = 2;
    private static final long CPU_FREQUENCE_LIMINT_1000MHZ = 1000000;
    private static final long CPU_FREQUENCE_LIMINT_1500MHZ = 1500000;
    private static final long CPU_FREQUENCE_LIMINT_1800MHZ = 1800000;
    private static final String TAG = "StreamUtils";

    public static boolean isSupport180k() {
        if (CpuInfosUtils.getNumCores() < 2 || !CpuInfosUtils.ifSupportNeon()) {
            return false;
        }
        return true;
    }

    public static boolean isSupport350k() {
        if (CpuInfosUtils.getNumCores() < 2 || CpuInfosUtils.getMaxCpuFrequence() < 1000000.0f || !CpuInfosUtils.ifSupportNeon()) {
            return false;
        }
        return true;
    }

    public static boolean isSupport1000k() {
        if (CpuInfosUtils.getNumCores() < 2 || CpuInfosUtils.getMaxCpuFrequence() < 1500000.0f || !CpuInfosUtils.ifSupportNeon()) {
            return false;
        }
        return true;
    }

    public static boolean isSupport1300k() {
        if (CpuInfosUtils.getNumCores() < 4 || CpuInfosUtils.getMaxCpuFrequence() < 1500000.0f || !CpuInfosUtils.ifSupportNeon()) {
            return false;
        }
        return true;
    }

    public static boolean isSupport720p() {
        return isSupport1300k();
    }

    public static boolean isSupport1080p() {
        return isSupport1300k();
    }
}
