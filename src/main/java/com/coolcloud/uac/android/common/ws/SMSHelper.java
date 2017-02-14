package com.coolcloud.uac.android.common.ws;

import android.content.Context;
import android.os.Handler;
import com.coolcloud.uac.android.common.util.Executor;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.util.TimeUtils;

public class SMSHelper {
    private static final String TAG = "SMSHelper";
    private static SMSHelper helper = null;
    private Context context = null;
    private SMSReceiver receiver = null;

    public interface OnRecvListener {
        void onReceived(String str);
    }

    private SMSHelper(Context context) {
        this.context = context;
    }

    public static synchronized SMSHelper get(Context context) {
        SMSHelper sMSHelper;
        synchronized (SMSHelper.class) {
            if (helper == null) {
                helper = new SMSHelper(context.getApplicationContext());
            }
            sMSHelper = helper;
        }
        return sMSHelper;
    }

    public synchronized boolean recvAuthCode(OnRecvListener listener) {
        if (this.receiver != null) {
            LOG.i(TAG, "unregister old receiver, register new");
            this.context.unregisterReceiver(this.receiver);
        }
        this.receiver = new 1(this, listener);
        this.context.registerReceiver(this.receiver, SMSReceiver.getIntentFilter());
        return true;
    }

    public synchronized boolean sendActivateMessage(String address, String ccid, String imsi, String deviceId, Handler handler, OnSentListener listener) {
        String prefix = "[address:" + address + "][ccid:" + ccid + "][imsi:" + imsi + "][deviceId:" + deviceId + "]";
        Executor.execute(new 2(this, prefix, ccid, imsi, deviceId, address, prefix, handler, listener));
        return true;
    }

    private String buildSimId(String ccid, String imsi) {
        String simId;
        if (invalid(ccid)) {
            simId = imsi;
        } else {
            simId = ccid;
        }
        return invalid(simId) ? TimeUtils.nowTime() : simId;
    }

    private boolean invalid(String s) {
        return TextUtils.empty(s) || s.length() <= 6;
    }

    private String buildActivateContent(String simId, String deviceId) {
        if (simId == null) {
            simId = "";
        }
        if (deviceId == null) {
            deviceId = "";
        }
        StringBuffer sb = new StringBuffer(64);
        sb.append("ACTIVATE:").append(simId).append("@").append(deviceId);
        return sb.toString();
    }

    private void handleSentCallback(int rcode, String simId, Handler handler, OnSentListener listener) {
        String prefix = "[rcode:" + rcode + "][simId:" + simId + "] callback for sent";
        if (handler != null) {
            handler.post(new 3(this, prefix, listener, rcode, simId));
        } else if (listener != null) {
            try {
                listener.onSent(rcode, simId);
            } catch (Throwable e) {
                LOG.e(TAG, prefix + " failed(Throwable)", e);
            }
        }
    }

    private String getAuthCode(String message) {
        for (String s : message.split("[^0-9]{1,}")) {
            if (s.length() >= 4) {
                return s;
            }
        }
        return "";
    }
}
