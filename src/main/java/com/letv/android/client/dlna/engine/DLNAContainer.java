package com.letv.android.client.dlna.engine;

import com.letv.android.client.dlna.utils.DLNAUtil;
import com.letv.core.utils.LogInfo;
import java.util.ArrayList;
import java.util.List;
import org.cybergarage.upnp.Device;

public class DLNAContainer {
    private static final DLNAContainer mDLNAContainer = new DLNAContainer();
    private DeviceChangeListener mDeviceChangeListener;
    private List<Device> mDevices = new ArrayList();
    private Device mSelectedDevice;

    public interface DeviceChangeListener {
        void onDeviceChange(Device device);
    }

    public static DLNAContainer getInstance() {
        return mDLNAContainer;
    }

    public synchronized void addDevice(Device d) {
        if (DLNAUtil.isMediaRenderDevice(d)) {
            int size = this.mDevices.size();
            for (int i = 0; i < size; i++) {
                if (d.getUDN().equalsIgnoreCase(((Device) this.mDevices.get(i)).getUDN())) {
                    break;
                }
            }
            this.mDevices.add(d);
            LogInfo.log("dlna", "添加设备：" + d.getDeviceType());
            if (this.mDeviceChangeListener != null) {
                this.mDeviceChangeListener.onDeviceChange(d);
            }
        }
    }

    public synchronized void removeDevice(Device d) {
        if (DLNAUtil.isMediaRenderDevice(d)) {
            int size = this.mDevices.size();
            int i = 0;
            while (i < size) {
                if (d.getUDN().equalsIgnoreCase(((Device) this.mDevices.get(i)).getUDN())) {
                    Device device = (Device) this.mDevices.remove(i);
                    if (device != null) {
                        LogInfo.log("dlna", "删除设备：" + device.getDeviceType());
                    }
                    boolean ret = false;
                    if (this.mSelectedDevice != null) {
                        ret = this.mSelectedDevice.getUDN().equalsIgnoreCase(device.getUDN());
                    }
                    if (ret) {
                        this.mSelectedDevice = null;
                    }
                    if (this.mDeviceChangeListener != null) {
                        this.mDeviceChangeListener.onDeviceChange(d);
                    }
                } else {
                    i++;
                }
            }
        }
    }

    public synchronized void clear() {
        if (this.mDevices != null) {
            this.mDevices.clear();
            this.mSelectedDevice = null;
        }
    }

    public Device getSelectedDevice() {
        return this.mSelectedDevice;
    }

    public void setSelectedDevice(Device mSelectedDevice) {
        this.mSelectedDevice = mSelectedDevice;
    }

    public void setDeviceChangeListener(DeviceChangeListener deviceChangeListener) {
        this.mDeviceChangeListener = deviceChangeListener;
    }

    public List<Device> getDevices() {
        return this.mDevices;
    }
}
