package com.letv.android.client.push;

import android.content.Context;
import android.media.AudioManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class OptionDeviceVoiceUtils {
    private static OptionDeviceVoiceUtils mInstance = null;
    private AudioManager mAudioManager;

    public OptionDeviceVoiceUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    private OptionDeviceVoiceUtils(Context context) {
        this.mAudioManager = null;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public static synchronized OptionDeviceVoiceUtils getInstance(Context context) {
        OptionDeviceVoiceUtils optionDeviceVoiceUtils;
        synchronized (OptionDeviceVoiceUtils.class) {
            if (mInstance == null) {
                mInstance = new OptionDeviceVoiceUtils(context);
            }
            optionDeviceVoiceUtils = mInstance;
        }
        return optionDeviceVoiceUtils;
    }

    public int getPhoneInitring() {
        return this.mAudioManager.getRingerMode();
    }

    public void setPhoneRing() {
        this.mAudioManager.setRingerMode(2);
        this.mAudioManager.setVibrateSetting(0, 1);
        this.mAudioManager.setVibrateSetting(1, 0);
    }

    public void setPhoneState(int mode) {
        this.mAudioManager.setRingerMode(mode);
    }
}
