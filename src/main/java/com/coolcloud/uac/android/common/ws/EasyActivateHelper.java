package com.coolcloud.uac.android.common.ws;

import android.content.Context;
import android.os.Handler;
import com.coolcloud.uac.android.common.util.Executor;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.SystemUtils;
import com.coolcloud.uac.android.common.util.SystemUtils.SimInfo;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnActivateListener;
import java.util.concurrent.TimeUnit;

public class EasyActivateHelper {
    private static final int MAX_POLLING_LOOP = 10;
    private static final String TAG = "EasyActivateHelper";
    private WsApi api = null;
    private String appId = null;
    private Context context = null;
    private Handler handler = null;
    private boolean isRunning = false;
    private OnActivateListener listener = null;
    private SMSHelper sh = null;
    private long startMillis = 0;
    private Stat stat = new Stat(this, null);

    private EasyActivateHelper(Context context, String appId, SMSHelper sh, WsApi api, Handler handler, OnActivateListener listener) {
        this.context = context;
        this.appId = appId;
        this.sh = sh;
        this.api = api;
        this.handler = handler;
        this.listener = listener;
    }

    public static EasyActivateHelper get(Context context, String appId, SMSHelper sh, WsApi api, Handler handler, OnActivateListener listener) {
        return new EasyActivateHelper(context.getApplicationContext(), appId, sh, api, handler, listener);
    }

    public synchronized boolean execute() {
        if (this.isRunning) {
            LOG.w(TAG, "[appId:" + this.appId + "] easy activate is running ...");
        } else {
            this.isRunning = true;
            LOG.i(TAG, "[appId:" + this.appId + "] easy activate ...");
            this.startMillis = System.currentTimeMillis();
            this.stat = new Stat(this, null);
            doCheckPresent();
        }
        return true;
    }

    private void doCheckPresent() {
        SimInfo simInfo = SystemUtils.getDefaultSimInfo(this.context);
        if (SimInfo.valid(simInfo)) {
            String ccid = simInfo.getCCID();
            String imsi = simInfo.getIMSI();
            String deviceId = SystemUtils.getDeviceId(this.context);
            String deviceModel = SystemUtils.getDeviceModel();
            if (invalid(ccid) && invalid(imsi)) {
                LOG.w(TAG, "[ccid:" + ccid + "][imsi:" + imsi + "] ccid && imsi invalid");
                doGetSMSChannels(simInfo);
                return;
            }
            this.api.checkPresentOnActivate(ccid, imsi, deviceId, deviceModel, this.appId, new 1(this, ccid, imsi, deviceId, deviceModel, simInfo));
            return;
        }
        callbackResult(5005, "sim info invalid", null, null, null);
    }

    private boolean invalid(String s) {
        return TextUtils.empty(s) || s.length() <= 6;
    }

    private void doGetSMSChannels(SimInfo simInfo) {
        this.api.getSMSChannels(this.appId, new 2(this, simInfo));
    }

    private void handleGetSMSChannelsCallback(SimInfo simInfo, String[] channels) {
        doSendActivateMessage(simInfo, channels, 0, System.currentTimeMillis());
    }

    private void doSendActivateMessage(SimInfo simInfo, String[] channels, int index, long startMessageMillis) {
        if (index < channels.length) {
            String channel = channels[index];
            String ccid = simInfo.getCCID();
            String imsi = simInfo.getIMSI();
            try {
                this.sh.sendActivateMessage(channel, ccid, imsi, SystemUtils.getDeviceId(this.context), null, new 3(this, channel, ccid, imsi, startMessageMillis, index, simInfo, channels));
                return;
            } catch (Exception e) {
                LOG.e(TAG, "[channel:" + channel + "][ccid:" + ccid + "][imsi:" + imsi + "][index:" + index + "][appId:" + this.appId + "] send activate message failed(Exception)", e);
                this.stat.setSmsMillis(System.currentTimeMillis() - startMessageMillis, index);
                callbackResult(2, e.getMessage(), null, null, null);
                return;
            }
        }
        LOG.e(TAG, "[ccid:" + simInfo.getCCID() + "][imsi:" + simInfo.getIMSI() + "][channels:" + TextUtils.a2s(channels) + "][index:" + index + "][appId:" + this.appId + "] failed on channels");
        this.stat.setSmsMillis(System.currentTimeMillis() - startMessageMillis, index);
        callbackResult(4018, "sending retries beyond maximum", null, null, null);
    }

    private void handleSendActivateMessageCallback(String simId) {
        doPollingOnActivate(simId, 0, System.currentTimeMillis());
    }

    private void doPollingOnActivate(String simId, int loop, long startPollMillis) {
        if (loop < 10) {
            Executor.schedule(new 4(this, simId, loop, startPollMillis), 1, TimeUnit.SECONDS);
            return;
        }
        LOG.e(TAG, "[simId:" + simId + "][appId:" + this.appId + "][loop:" + loop + "] polling no response");
        this.stat.setPollMillis(System.currentTimeMillis() - startPollMillis, loop);
        callbackResult(4019, "polling retries beyond maximum", null, null, null);
    }

    private void callbackResult(int rcode, String message, String phone, String uid, String tkt) {
        long millis = System.currentTimeMillis() - this.startMillis;
        if (rcode == 0) {
            LOG.i(TAG, "[appId:" + this.appId + "][rcode:" + rcode + "][phone:" + phone + "][uid:" + uid + "][tkt:" + tkt + "][millis:" + millis + "] easy activate ok");
        } else {
            LOG.e(TAG, "[appId:" + this.appId + "][rcode:" + rcode + "][millis:" + millis + "] easy activate failed");
        }
        this.stat.setMillis(System.currentTimeMillis() - this.startMillis);
        this.stat.setResult(rcode, message);
        this.stat.report();
        this.isRunning = false;
        if (this.handler != null) {
            this.handler.post(new 5(this, "callbackResult", rcode, phone, uid, tkt));
        } else if (this.listener != null) {
            this.listener.onDone(rcode, phone, uid, tkt);
        }
    }
}
