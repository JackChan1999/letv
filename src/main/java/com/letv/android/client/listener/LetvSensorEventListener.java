package com.letv.android.client.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.letv.android.client.LetvApplication;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.ShackVideoInfoListBean.ShackVideoInfoBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.ShackCommitParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LetvSensorEventListener implements SensorEventListener {
    private Context context;
    private AlbumPlayFlow flow;
    private String from;
    private boolean isLive;
    private boolean isRun;
    private boolean isStop;
    private Sensor mSensor;
    private SensorManager sm;
    private Vibrator vibrator;

    public LetvSensorEventListener(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isStop = false;
        this.isRun = false;
        this.isLive = false;
        this.context = context;
        if (context != null) {
            this.sm = (SensorManager) context.getSystemService("sensor");
            this.vibrator = (Vibrator) context.getSystemService("vibrator");
            this.mSensor = this.sm.getDefaultSensor(1);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (!this.isStop && !this.isLive && PreferencesManager.getInstance().isShack() && this.context != null) {
            int sensorType = event.sensor.getType();
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (sensorType == 1 && Math.abs(x) > ((float) 16) && Math.abs(y) > ((float) 14) && Math.abs(z) > ((float) 17)) {
                this.sm.unregisterListener(LetvApplication.getInstance().getLetvSensorEventListener());
                LogInfo.log("zhuqiao", "...onSensorChanged...1");
                AMapLocation location = AMapLocationTool.getInstance().location();
                LogInfo.log("zhuqiao", "...onSensorChanged...isRun=" + this.isRun);
                if (!this.isRun && location != null) {
                    this.isRun = true;
                    this.isStop = true;
                    LogInfo.log("zhuqiao", "from =" + this.from);
                    if (!TextUtils.equals(this.from, "AlbumPlayActivity") || this.flow == null) {
                        LogInfo.log("zhuqiao", "context =" + this.context);
                        new LetvRequest().setUrl(LetvUrlMaker.getShakeCommitUrl(DataUtils.generateDeviceId(this.context), location.getLongitude() + "", location.getLatitude() + "")).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setParser(new ShackCommitParser()).setCallback(new 3(this)).add();
                        return;
                    }
                    ShackVideoInfoBean videoInfo = this.flow.getShackVideoInfo();
                    LogInfo.log("zhuqiao", "...onSensorChanged...videoInfo=" + videoInfo);
                    if (videoInfo != null) {
                        new LetvRequest().setUrl(LetvUrlMaker.getShakeSubmitUrl(videoInfo.aid + "", videoInfo.vid + "", DataUtils.generateDeviceId(this.context), videoInfo.playtime + "", (videoInfo.aid != 0 ? 1 : 3) + "", location.getLongitude() + "", location.getLatitude() + "")).setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setParser(new 2(this)).setCallback(new 1(this)).add();
                    }
                } else if (PreferencesManager.getInstance().isShack()) {
                    this.sm.registerListener(LetvApplication.getInstance().getLetvSensorEventListener(), this.mSensor, 1);
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public AlbumPlayFlow getPlayFlow() {
        return this.flow;
    }

    public void setPlayFlow(AlbumPlayFlow playController) {
        this.flow = playController;
    }

    public void setPlayLive(boolean isLive) {
        this.isLive = isLive;
    }

    public void stop() {
        if (this.sm != null) {
            this.from = null;
            this.isStop = true;
            this.sm.unregisterListener(LetvApplication.getInstance().getLetvSensorEventListener());
        }
    }

    public void start(String className) {
        if (this.sm != null) {
            this.from = className;
            if (!"TopMyActivity".equals(className)) {
                this.isStop = false;
                if (PreferencesManager.getInstance().isShack()) {
                    this.sm.registerListener(LetvApplication.getInstance().getLetvSensorEventListener(), this.mSensor, 1);
                }
            }
        }
    }
}
