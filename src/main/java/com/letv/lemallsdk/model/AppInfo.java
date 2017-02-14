package com.letv.lemallsdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AppInfo implements Parcelable {
    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        public AppInfo createFromParcel(Parcel source) {
            AppInfo appInfo = new AppInfo();
            appInfo.setId(source.readString());
            appInfo.setAppName(source.readString());
            appInfo.setInlay(source.readByte() != (byte) 0);
            appInfo.setUiStyle(source.readInt());
            return appInfo;
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
    private String appName;
    private String id;
    private boolean isInlay = false;
    private int uiStyle = 0;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.appName);
        dest.writeByte((byte) (this.isInlay ? 1 : 0));
        dest.writeInt(this.uiStyle);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isInlay() {
        return this.isInlay;
    }

    public void setInlay(boolean isInlay) {
        this.isInlay = isInlay;
    }

    public int getUiStyle() {
        return this.uiStyle;
    }

    public void setUiStyle(int uiStyle) {
        this.uiStyle = uiStyle;
    }
}
