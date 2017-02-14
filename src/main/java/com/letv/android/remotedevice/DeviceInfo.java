package com.letv.android.remotedevice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DeviceInfo implements Parcelable {
    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        public DeviceInfo createFromParcel(Parcel source) {
            return new DeviceInfo(source);
        }

        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };
    public String appVersion;
    public String deviceId;
    public String deviceName;
    public int deviceType = 1;
    public String deviceVersion;

    public DeviceInfo(int deviceType, String deviceId, String deviceName) {
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }

    public DeviceInfo(int deviceType, String deviceId, String deviceName, String deviceVersion, String appVersion) {
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceVersion = deviceVersion;
        this.appVersion = appVersion;
    }

    public DeviceInfo(Parcel in) {
        this.deviceType = in.readInt();
        this.deviceId = in.readString();
        this.deviceName = in.readString();
        this.deviceVersion = in.readString();
        this.appVersion = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.deviceType);
        dest.writeString(this.deviceId);
        dest.writeString(this.deviceName);
        dest.writeString(this.deviceVersion);
        dest.writeString(this.appVersion);
    }

    public String toString() {
        return "DeviceInfo {deviceType=" + this.deviceType + ", deviceId=" + this.deviceId + ", deviceName=" + this.deviceName + "}";
    }
}
