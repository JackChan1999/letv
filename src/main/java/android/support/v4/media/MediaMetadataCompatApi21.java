package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.os.Parcel;
import java.util.Set;

class MediaMetadataCompatApi21 {
    MediaMetadataCompatApi21() {
    }

    public static Set<String> keySet(Object metadataObj) {
        return ((MediaMetadata) metadataObj).keySet();
    }

    public static Bitmap getBitmap(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getBitmap(key);
    }

    public static long getLong(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getLong(key);
    }

    public static Object getRating(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getRating(key);
    }

    public static CharSequence getText(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getText(key);
    }

    public static void writeToParcel(Object metadataObj, Parcel dest, int flags) {
        ((MediaMetadata) metadataObj).writeToParcel(dest, flags);
    }

    public static Object createFromParcel(Parcel in) {
        return MediaMetadata.CREATOR.createFromParcel(in);
    }
}
