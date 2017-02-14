package com.letv.component.player.core;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import com.letv.component.player.utils.DecodeConfigTable;
import com.letv.component.player.utils.HardDecodeUtils;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.NativeInfos;
import com.letv.component.player.utils.PreferenceUtil;

public class Configuration {
    public static final String SDK_VERSION = "v1.6";
    public static final float SDK_VERSION_CODE = 1.6f;
    public static final int STATUS_NO_VALUE = -1;
    public static int hardDecodeState = -1;
    private static Configuration sConfiguration = null;
    private Context mContext;
    public DecodeCapability mHardDecodeCapability;
    private int mLocalStatus = -1;
    public DecodeCapability mSoftDecodeCapability;

    public static synchronized void createConfig(Context context) {
        synchronized (Configuration.class) {
            if (sConfiguration == null) {
                sConfiguration = new Configuration(context);
            }
        }
    }

    public static Configuration getInstance(Context context) {
        if (sConfiguration == null) {
            createConfig(context);
        }
        return sConfiguration;
    }

    private Configuration(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        this.mHardDecodeCapability = new DecodeCapability();
        this.mSoftDecodeCapability = new DecodeCapability();
        String statusAndAdapterd = getLocalDecodeCapability();
        String[] statusAndAdapterdArray;
        if (TextUtils.isEmpty(statusAndAdapterd)) {
            String configStatus = new DecodeConfigTable().getStatus(this.mContext);
            if (TextUtils.isEmpty(configStatus)) {
                initDecodeCapability();
                LogTag.i("本地计算");
            } else {
                statusAndAdapterdArray = configStatus.split(",");
                parser(Integer.parseInt(statusAndAdapterdArray[0]), Integer.parseInt(statusAndAdapterdArray[1]));
            }
        } else {
            statusAndAdapterdArray = statusAndAdapterd.split(",");
            this.mLocalStatus = Integer.parseInt(statusAndAdapterdArray[0]);
            parser(this.mLocalStatus, Integer.parseInt(statusAndAdapterdArray[1]));
            LogTag.i("从sharedpreference读取软硬解码能力及是否是配过");
        }
        LogTag.i("初始化，mHardDecodeCapability=" + this.mHardDecodeCapability.toString() + ", mSoftDecodeCapability=" + this.mSoftDecodeCapability.toString());
        float sdkVersionCode = PreferenceUtil.getVersionCode(this.mContext);
        if (SDK_VERSION_CODE > sdkVersionCode) {
            PreferenceUtil.setVersionCode(this.mContext, SDK_VERSION_CODE);
            if (sdkVersionCode != 0.0f) {
                PreferenceUtil.removeFirsHardDecode(this.mContext);
            }
        }
    }

    private void initDecodeCapability() {
        boolean z;
        boolean z2 = true;
        this.mHardDecodeCapability.isWhite = false;
        this.mHardDecodeCapability.isBlack = false;
        hardDecodeState = 3;
        if (HardDecodeUtils.isSupportHWDecodeUseNative()) {
            int avcLevel = HardDecodeUtils.getAVCLevel();
            if (avcLevel >= 2048) {
                this.mHardDecodeCapability.isSupport1080p = true;
                this.mHardDecodeCapability.isSupport720p = true;
                this.mHardDecodeCapability.isSupport1300k = true;
                this.mHardDecodeCapability.isSupport1000k = true;
                this.mHardDecodeCapability.isSupport350k = true;
                this.mHardDecodeCapability.isSupport180k = true;
            } else if (avcLevel >= 512) {
                this.mHardDecodeCapability.isSupport1080p = false;
                this.mHardDecodeCapability.isSupport720p = true;
                this.mHardDecodeCapability.isSupport1300k = true;
                this.mHardDecodeCapability.isSupport1000k = true;
                this.mHardDecodeCapability.isSupport350k = true;
                this.mHardDecodeCapability.isSupport180k = true;
            } else if (avcLevel >= 256) {
                this.mHardDecodeCapability.isSupport1080p = false;
                this.mHardDecodeCapability.isSupport720p = false;
                this.mHardDecodeCapability.isSupport1300k = false;
                this.mHardDecodeCapability.isSupport1000k = false;
                this.mHardDecodeCapability.isSupport350k = true;
                this.mHardDecodeCapability.isSupport180k = true;
            } else if (avcLevel >= 128) {
                this.mHardDecodeCapability.isSupport1080p = false;
                this.mHardDecodeCapability.isSupport720p = false;
                this.mHardDecodeCapability.isSupport1300k = false;
                this.mHardDecodeCapability.isSupport1000k = false;
                this.mHardDecodeCapability.isSupport350k = false;
                this.mHardDecodeCapability.isSupport180k = true;
            } else {
                this.mHardDecodeCapability.isSupport1080p = false;
                this.mHardDecodeCapability.isSupport720p = false;
                this.mHardDecodeCapability.isSupport1300k = false;
                this.mHardDecodeCapability.isSupport1000k = false;
                this.mHardDecodeCapability.isSupport350k = false;
                this.mHardDecodeCapability.isSupport180k = false;
            }
        } else {
            hardDecodeState = 0;
            this.mHardDecodeCapability.isSupport1080p = false;
            this.mHardDecodeCapability.isSupport720p = false;
            this.mHardDecodeCapability.isSupport1300k = false;
            this.mHardDecodeCapability.isSupport1000k = false;
            this.mHardDecodeCapability.isSupport350k = false;
            this.mHardDecodeCapability.isSupport180k = false;
        }
        if (NativeInfos.getSupportLevel() <= 0) {
            this.mSoftDecodeCapability.isWhite = false;
            this.mSoftDecodeCapability.isBlack = true;
        } else {
            this.mSoftDecodeCapability.isWhite = true;
            this.mSoftDecodeCapability.isBlack = false;
        }
        this.mSoftDecodeCapability.isSupport1080p = false;
        DecodeCapability decodeCapability = this.mSoftDecodeCapability;
        if (NativeInfos.getSupportLevel() >= 6) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport720p = z;
        decodeCapability = this.mSoftDecodeCapability;
        if (NativeInfos.getSupportLevel() >= 4) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1300k = z;
        decodeCapability = this.mSoftDecodeCapability;
        if (NativeInfos.getSupportLevel() >= 3) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1000k = z;
        decodeCapability = this.mSoftDecodeCapability;
        if (NativeInfos.getSupportLevel() >= 1) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport350k = z;
        DecodeCapability decodeCapability2 = this.mSoftDecodeCapability;
        if (NativeInfos.getSupportLevel() < 1) {
            z2 = false;
        }
        decodeCapability2.isSupport180k = z2;
    }

    public void update(int status, int adaptered) {
        LogTag.i("更新解码能力");
        parser(status, adaptered);
        if (!(this.mLocalStatus == -1 || this.mLocalStatus == status || hardDecodeState != 2 || LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel() == PreferenceUtil.getFirsHardDecode(this.mContext))) {
            PreferenceUtil.removeFirsHardDecode(this.mContext);
        }
        saveDecodeCapability(status, adaptered);
        LogTag.i("test status:" + status);
        LogTag.i("test adaper:" + adaptered);
    }

    private void saveDecodeCapability(int status, int adaptered) {
        PreferenceUtil.setDecodeCapability(this.mContext, status, adaptered);
    }

    private String getLocalDecodeCapability() {
        return PreferenceUtil.getDecodeCapability(this.mContext);
    }

    private void parser(int status, int adapterd) {
        boolean z;
        boolean z2 = true;
        this.mHardDecodeCapability.isBlack = (status & 2048) == 2048;
        DecodeCapability decodeCapability = this.mHardDecodeCapability;
        if ((status & 1024) == 1024) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isWhite = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((ViewCompat.MEASURED_STATE_TOO_SMALL & status) == ViewCompat.MEASURED_STATE_TOO_SMALL) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSwitch = z;
        if (this.mHardDecodeCapability.isBlack) {
            hardDecodeState = 0;
        } else if (this.mHardDecodeCapability.isWhite) {
            if (HardDecodeUtils.isSupportHWDecodeUseNative()) {
                hardDecodeState = 1;
            } else {
                hardDecodeState = 0;
            }
        } else if (!(this.mHardDecodeCapability.isBlack || this.mHardDecodeCapability.isWhite)) {
            if (this.mHardDecodeCapability.isSwitch) {
                hardDecodeState = 2;
            } else {
                hardDecodeState = 3;
            }
        }
        LogTag.i("hardDecodeStae:" + hardDecodeState);
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 32) == 32) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1080p = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 16) == 16) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport720p = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 8) == 8) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1300k = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 4) == 4) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1000k = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 2) == 2) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport350k = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((status & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport180k = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 32) == 32) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is1080pAdapted = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 16) == 16) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is720pAdapted = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 8) == 8) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is1300kAdapted = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 4) == 4) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is1000kAdapted = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 2) == 2) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is350kAdapted = z;
        decodeCapability = this.mHardDecodeCapability;
        if ((adapterd & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.is180kAdapted = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((GravityCompat.RELATIVE_LAYOUT_DIRECTION & status) == GravityCompat.RELATIVE_LAYOUT_DIRECTION) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isBlack = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((4194304 & status) == 4194304) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isWhite = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((131072 & status) == 131072) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1080p = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((65536 & status) == 65536) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport720p = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((32768 & status) == 32768) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1300k = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((status & 16384) == 16384) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport1000k = z;
        decodeCapability = this.mSoftDecodeCapability;
        if ((status & 8192) == 8192) {
            z = true;
        } else {
            z = false;
        }
        decodeCapability.isSupport350k = z;
        DecodeCapability decodeCapability2 = this.mSoftDecodeCapability;
        if ((status & 4096) != 4096) {
            z2 = false;
        }
        decodeCapability2.isSupport180k = z2;
    }

    public int getHardDecodeState() {
        return hardDecodeState;
    }

    public String toString() {
        return "hardDecodeCapability(" + this.mHardDecodeCapability.toString() + "), softDecodeCapability(" + this.mSoftDecodeCapability.toString() + ")";
    }
}
