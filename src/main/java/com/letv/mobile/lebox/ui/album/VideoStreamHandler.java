package com.letv.mobile.lebox.ui.album;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.util.DataConstant.ACTION.LE123.CHANNEL;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Util;
import com.tencent.connect.common.Constants;

public class VideoStreamHandler {
    public static final int HD_STREAM = 1;
    public static final int LOW_STREAM = 0;
    public static final int STANDARD_STREAM = 2;
    private static final String TAG = VideoStreamHandler.class.getSimpleName();
    private final Context mContext = LeBoxApp.getApplication();
    private int mCurrentStream;

    private void convertPlayStream(int originalCurrentStream) {
        Log.v(TAG, "convertPlayStream originalCurrentStream : " + originalCurrentStream);
        switch (originalCurrentStream) {
            case 1:
            case 2:
                this.mCurrentStream = 0;
                return;
            case 3:
                this.mCurrentStream = 2;
                return;
            case 4:
                this.mCurrentStream = 1;
                return;
            default:
                return;
        }
    }

    public void clickStreamDownloadTip() {
        int id = -1;
        switch (this.mCurrentStream) {
            case 0:
                id = R.string.toast_download_not_support_standard;
                break;
            case 1:
                id = R.string.toast_download_not_support_high;
                break;
            case 2:
                id = R.string.toast_download_not_support_standard2;
                break;
        }
        if (id != -1) {
            Util.showToast(this.mContext, id);
        }
    }

    public static int getCurrentStream(String definition) {
        if (LetvErrorCode.VRS_NETWORK_ERRORS.equals(definition)) {
            return 1;
        }
        if (Constants.DEFAULT_UIN.equals(definition)) {
            return 2;
        }
        if (CHANNEL.SERIAL_SPECIAL.equals(definition)) {
            return 0;
        }
        return 0;
    }

    public String getCurrentStreamString() {
        String s = "";
        switch (this.mCurrentStream) {
            case 0:
                return "21";
            case 1:
                return "22";
            case 2:
                return "13";
            default:
                return s;
        }
    }

    public VideoStreamHandler(int originalCurrentStream, boolean isFromPlay) {
        if (isFromPlay) {
            convertPlayStream(originalCurrentStream);
        } else {
            this.mCurrentStream = originalCurrentStream;
        }
    }

    public void setCurrentStream(int currentStream) {
        this.mCurrentStream = currentStream;
    }

    public int getCurrentStream() {
        return this.mCurrentStream;
    }

    public boolean isCPUSupportHD() {
        return H265Utils.isSupport1300();
    }

    public boolean isSupport(String brList) {
        boolean res = false;
        switch (this.mCurrentStream) {
            case 0:
                res = PlayUtils.isSupportLow(brList);
                break;
            case 1:
                res = PlayUtils.isSupportHD(brList);
                if (specificPhone() && res) {
                    res = false;
                    break;
                }
            case 2:
                res = PlayUtils.isSupportStandard(brList);
                if (specificPhone() && res) {
                    res = false;
                    break;
                }
        }
        Log.v(TAG, "isSupport brList : " + brList + " mCurrentStream : " + this.mCurrentStream + " res : " + res);
        return res;
    }

    public static boolean specificPhone() {
        Log.v(TAG, "Build.MODEL : " + Build.MODEL);
        return "lenovo k910".equalsIgnoreCase(Build.MODEL);
    }
}
