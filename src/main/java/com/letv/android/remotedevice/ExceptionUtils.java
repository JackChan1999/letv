package com.letv.android.remotedevice;

import android.os.Parcel;
import android.util.Log;

public class ExceptionUtils {
    private static final String TAG = "ExceptionUtils";

    public static final void writeExceptionToParcel(Parcel reply, Exception e) {
        if (e instanceof DeviceUnavailableException) {
            reply.writeInt(1);
            reply.writeString(e.getMessage());
            if (true) {
                Log.e(TAG, "Writing exception to parcel", e);
                return;
            }
            return;
        }
        reply.writeException(e);
        Log.e(TAG, "Writing exception to parcel", e);
    }

    public static final void readExceptionFromParcel(Parcel reply) {
        int code = reply.readExceptionCode();
        if (code != 0) {
            readExceptionFromParcel(reply, reply.readString(), code);
        }
    }

    private static final void readExceptionFromParcel(Parcel reply, String msg, int code) {
        switch (code) {
            case 1:
                throw new DeviceUnavailableException(msg);
            default:
                reply.readException(code, msg);
                return;
        }
    }
}
