package com.letv.adlib.sdk.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AdAli implements Parcelable {
    public static final Creator<AdAli> CREATOR = new Creator<AdAli>() {
        public AdAli createFromParcel(Parcel source) {
            return new AdAli(source);
        }

        public AdAli[] newArray(int size) {
            return new AdAli[size];
        }
    };
    public String title;
    public String titleEx;
    public String url;
    public String url_click;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.titleEx);
        dest.writeString(this.url_click);
    }

    private AdAli(Parcel source) {
        this.url = source.readString();
        this.title = source.readString();
        this.titleEx = source.readString();
        this.url_click = source.readString();
    }
}
