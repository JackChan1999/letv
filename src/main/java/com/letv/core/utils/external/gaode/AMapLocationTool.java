package com.letv.core.utils.external.gaode;

import android.os.Handler;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.letv.core.BaseApplication;

public class AMapLocationTool {
    private static String TAG = "AMAP";
    private static volatile AMapLocationTool locationTool;
    public boolean isStart;
    private AMapLocation lastBdLocation;
    private AMapLocationClientOption locationOption;
    private Handler mHandler;
    public AMapLocationClient mLocationClient;
    public MyLocationListenner myListener;

    private AMapLocationTool() {
        this.mLocationClient = null;
        this.locationOption = null;
        this.myListener = new MyLocationListenner(this);
        this.mHandler = new Handler();
        this.isStart = false;
        this.mLocationClient = new AMapLocationClient(BaseApplication.getInstance().getBaseContext());
    }

    public static synchronized AMapLocationTool getInstance() {
        AMapLocationTool aMapLocationTool;
        synchronized (AMapLocationTool.class) {
            if (locationTool == null) {
                locationTool = new AMapLocationTool();
            }
            aMapLocationTool = locationTool;
        }
        return aMapLocationTool;
    }

    public synchronized AMapLocation location() {
        if (!this.isStart) {
            if (this.mLocationClient != null) {
                this.mLocationClient.setLocationListener(this.myListener);
                setLocationOption();
                this.mLocationClient.startLocation();
                this.isStart = true;
            } else {
                this.mLocationClient = new AMapLocationClient(BaseApplication.getInstance().getBaseContext());
                this.mLocationClient.setLocationListener(this.myListener);
                setLocationOption();
                this.mLocationClient.startLocation();
                this.isStart = true;
            }
        }
        this.mHandler.postDelayed(new 1(this), 5000);
        if (this.mLocationClient != null) {
            AMapLocation location = this.mLocationClient.getLastKnownLocation();
            if (location != null) {
                this.lastBdLocation = location;
            }
        }
        return this.lastBdLocation;
    }

    private void setLocationOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        this.mLocationClient.setLocationOption(option);
    }

    public void stop() {
        if (this.mLocationClient != null) {
            this.mLocationClient.unRegisterLocationListener(this.myListener);
            this.mLocationClient.stopLocation();
        }
    }
}
