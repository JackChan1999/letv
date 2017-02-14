package com.tencent.connect.dataprovider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public final class DataType {
    public static final int CONTENT_AND_IMAGE_PATH = 1;
    public static final int CONTENT_AND_VIDEO_PATH = 2;
    public static final int CONTENT_ONLY = 4;

    /* compiled from: ProGuard */
    public static class TextAndMediaPath implements Parcelable {
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

    /* compiled from: ProGuard */
    public static class TextOnly implements Parcelable {
        public static final Creator<TextOnly> CREATOR = new Creator<TextOnly>() {
            public TextOnly createFromParcel(Parcel parcel) {
                return new TextOnly(parcel);
            }

            public TextOnly[] newArray(int i) {
                return new TextOnly[i];
            }
        };
        private String a;

        public TextOnly(String str) {
            this.a = str;
        }

        public String getText() {
            return this.a;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
        }

        private TextOnly(Parcel parcel) {
            this.a = parcel.readString();
        }
    }
}
