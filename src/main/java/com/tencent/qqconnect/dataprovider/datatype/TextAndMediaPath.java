package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class TextAndMediaPath implements Parcelable {
    public static final Creator<TextAndMediaPath> CREATOR = new Creator<TextAndMediaPath>() {
        public TextAndMediaPath createFromParcel(Parcel parcel) {
            return new TextAndMediaPath(parcel);
        }

        public TextAndMediaPath[] newArray(int i) {
            return new TextAndMediaPath[i];
        }
    };
    private String a;
    private String b;

    public TextAndMediaPath(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getText() {
        return this.a;
    }

    public String getMediaPath() {
        return this.b;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
    }

    private TextAndMediaPath(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
    }
}
