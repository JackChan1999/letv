package com.letv.component.player.utils;

import android.util.Log;
import com.letv.component.player.hardwaredecode.CodecWrapper;

public class HardDecodeUtils {
    private static final int ANDROID_OS_API_LEVEL_HW_LIMIT = 16;
    private static final int HIGH_PROFILE_VALUE = 16;
    private static int decodeProfile = -1;

    public static boolean isSupportHWDecodeUseNative() {
        Log.d("lxb", "isSupport");
        int get = getProfileUseNative();
        Log.d("lxb", "get=" + get);
        return get >= 16;
    }

    private static int getProfileUseNative() {
        Log.d("lxb", "decodeProfile=" + decodeProfile);
        if (decodeProfile == -1) {
            decodeProfile = getProfile();
        }
        return decodeProfile;
    }

    private static synchronized int getProfile() {
        int capbility;
        synchronized (HardDecodeUtils.class) {
            capbility = CodecWrapper.getCapbility();
        }
        return capbility;
    }

    public static int getAVCLevel() {
        return CodecWrapper.getAVCLevel();
    }
}
