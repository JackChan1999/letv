package com.tencent.open.yyb;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class ShareModel implements Parcelable {
    public static final Creator<ShareModel> CREATOR = new Creator<ShareModel>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public ShareModel a(Parcel parcel) {
            ShareModel shareModel = new ShareModel();
            shareModel.a = parcel.readString();
            shareModel.b = parcel.readString();
            shareModel.c = parcel.readString();
            shareModel.d = parcel.readString();
            return shareModel;
        }

        public ShareModel[] a(int i) {
            return null;
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
