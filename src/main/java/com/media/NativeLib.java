package com.media;

import com.letv.component.player.utils.LogTag;

public class NativeLib {
    private static final String TAG = "NativeLib";
    private static boolean sLoaded = false;

    public NativeLib() throws Exception {
        if (!loadLibs()) {
            throw new Exception("Couldn't load native libs!!");
        }
    }

    private static boolean loadLibs() {
        if (sLoaded) {
            return true;
        }
        boolean err = false;
        try {
            System.loadLibrary("ffmpeg_neon_hs");
            System.loadLibrary("ffmpeg_jni_neon_hs");
        } catch (UnsatisfiedLinkError e) {
            LogTag.i(TAG, "Couldn't load lib: " + e.getMessage());
            err = true;
        }
        if (!err) {
            sLoaded = true;
        }
        return sLoaded;
    }
}
