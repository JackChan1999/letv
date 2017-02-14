package com.letv.redpacketsdk.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.letv.redpacketsdk.callback.LetvSensorEventListenerCallback;

public class LetvSensorEventListener implements SensorEventListener {
    private LetvSensorEventListenerCallback callback;
    private boolean isRun = false;
    private boolean isStop = false;
    private Sensor mSensor;
    private SensorManager sm;

    public LetvSensorEventListener(Context context) {
        if (context != null) {
            this.sm = (SensorManager) context.getSystemService("sensor");
            this.mSensor = this.sm.getDefaultSensor(1);
        }
    }

    public void setCallBack(LetvSensorEventListenerCallback callback) {
        this.callback = callback;
    }

    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (sensorType == 1 && !this.isRun && Math.abs(x) > ((float) 10) && Math.abs(y) > ((float) 8) && Math.abs(z) > ((float) 9)) {
            LogInfo.log("LetvSensorEventListener", "onSensorChanged+Sensor.TYPE_ACCELEROMETER+ture");
            this.isRun = true;
            if (this.callback != null) {
                this.callback.getShakeAction();
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void stop() {
        if (this.sm != null) {
            this.isStop = true;
            this.sm.unregisterListener(this);
        }
    }

    public void start() {
        if (this.sm != null) {
            this.isStop = false;
            this.isRun = false;
            this.sm.registerListener(this, this.mSensor, 1);
        }
    }
}
