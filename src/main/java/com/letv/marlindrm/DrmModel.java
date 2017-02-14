package com.letv.marlindrm;

import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.media.PlaylistProxy;
import com.letv.core.utils.LogInfo;

public class DrmModel {
    private static String TAG = "drmTest";
    private static PlaylistProxy sPlayProxy;

    public static void setPlayProxy(PlaylistProxy playProxy) {
        sPlayProxy = playProxy;
    }

    public static void stopProxy() {
        if (sPlayProxy != null) {
            try {
                sPlayProxy.stop();
            } catch (ErrorCodeException e) {
                e.printStackTrace();
            }
            LogInfo.log(TAG, "drmModel stop playerProxy....");
            sPlayProxy = null;
        }
    }
}
