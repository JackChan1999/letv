package com.letv.adlib.sdk.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.view.View;
import com.letv.adlib.sdk.utils.EncryptionUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class AdElementMime extends AdElement implements Parcelable, Cloneable {
    public static final Creator<AdElementMime> CREATOR = new Creator<AdElementMime>() {
        public AdElementMime createFromParcel(Parcel source) {
            return new AdElementMime(source);
        }

        public AdElementMime[] newArray(int size) {
            return new AdElementMime[size];
        }
    };
    public ArrayList<AdAli> adAliList;
    public View adview;
    public int cis;
    public int clickShowType;
    public String closeTime;
    public HashMap<String, String> cmValues;
    public int duration;
    public int errCode;
    public boolean hadComplete;
    public boolean hadExposed;
    public int index;
    public int isRequestOffline;
    public String localPath;
    public int mediaFileType;
    public String mediaFileUrl;
    public String oiid;
    public String pid;
    public String productId;
    private String shortPath;
    public String sid;
    public int startTime;
    public String streamURL;
    public String text;
    public String textEx;
    public String vid;

    public AdElementMime() {
        this.errCode = -1;
        this.hadExposed = false;
        this.hadComplete = false;
    }

    public boolean isComplexAD() {
        return this.mediaFileType == 5;
    }

    private AdElementMime(Parcel source) {
        boolean z = true;
        this.errCode = -1;
        this.hadExposed = false;
        this.hadComplete = false;
        this.vastTag = source.readInt();
        this.adTag = source.readInt();
        this.adReqType = source.readInt();
        this.adZoneType = source.readInt();
        this.cuePointType = source.readInt();
        this.dspType = source.readInt();
        this.hasProgressTracking = source.readInt();
        this.commonType = source.readInt();
        this.mediaFileUrl = source.readString();
        this.mediaFileType = source.readInt();
        this.duration = source.readInt();
        this.clickShowType = source.readInt();
        this.text = source.readString();
        this.textEx = source.readString();
        this.isRequestOffline = source.readInt();
        this.vid = source.readString();
        this.pid = source.readString();
        this.sid = source.readString();
        this.streamURL = source.readString();
        this.index = source.readInt();
        if (source.readInt() != 1) {
            z = false;
        }
        this.hadExposed = z;
        this.adAliList = source.readArrayList(AdAli.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.vastTag);
        dest.writeInt(this.adTag);
        dest.writeInt(this.adReqType);
        dest.writeInt(this.adZoneType);
        dest.writeInt(this.cuePointType);
        dest.writeInt(this.dspType);
        dest.writeInt(this.hasProgressTracking);
        dest.writeInt(this.commonType);
        dest.writeString(this.mediaFileUrl);
        dest.writeInt(this.mediaFileType);
        dest.writeInt(this.duration);
        dest.writeInt(this.clickShowType);
        dest.writeString(this.text);
        dest.writeString(this.textEx);
        dest.writeInt(this.isRequestOffline);
        dest.writeString(this.vid);
        dest.writeString(this.pid);
        dest.writeString(this.sid);
        dest.writeString(this.streamURL);
        dest.writeInt(this.index);
        dest.writeInt(this.hadExposed ? 1 : 0);
        dest.writeList(this.adAliList);
    }

    public String getShortPath() {
        if (!TextUtils.isEmpty(this.shortPath)) {
            return this.shortPath;
        }
        if (TextUtils.isEmpty(this.mediaFileUrl)) {
            return "";
        }
        return EncryptionUtil.md5(this.mediaFileUrl);
    }

    public String toString() {
        return super.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
