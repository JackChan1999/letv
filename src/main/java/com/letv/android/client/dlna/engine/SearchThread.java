package com.letv.android.client.dlna.engine;

import com.letv.core.utils.LogInfo;
import com.umeng.analytics.a;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.DeviceChangeListener;

public class SearchThread extends Thread {
    private static final int mFastInternalTime = 15000;
    private static final int mNormalInternalTime = 3600000;
    private boolean flag = true;
    private ControlPoint mControlPoint;
    private DeviceChangeListener mDeviceChangeListener = new DeviceChangeListener() {
        public void deviceRemoved(Device dev) {
            DLNAContainer.getInstance().removeDevice(dev);
        }

        public void deviceAdded(Device dev) {
            DLNAContainer.getInstance().addDevice(dev);
        }
    };
    private int mSearchTimes;
    private boolean mStartComplete;

    public SearchThread(ControlPoint mControlPoint) {
        this.mControlPoint = mControlPoint;
        this.mControlPoint.addDeviceChangeListener(this.mDeviceChangeListener);
    }

    public void run() {
        while (this.flag && this.mControlPoint != null) {
            searchDevices();
        }
    }

    private void searchDevices() {
        try {
            if (this.mStartComplete) {
                this.mControlPoint.search();
                LogInfo.log("dlna", "controlpoint search...");
            } else {
                this.mControlPoint.stop();
                boolean startRet = this.mControlPoint.start();
                LogInfo.log("dlna", "controlpoint start:" + startRet);
                if (startRet) {
                    this.mStartComplete = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (this) {
            try {
                this.mSearchTimes++;
                if (this.mSearchTimes >= 5) {
                    wait(a.h);
                } else {
                    wait(15000);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public synchronized void setSearcTimes(int searchTimes) {
        this.mSearchTimes = searchTimes;
    }

    public void awake() {
        synchronized (this) {
            notifyAll();
        }
    }

    public void stopThread() {
        this.flag = false;
        awake();
    }
}
