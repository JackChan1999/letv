package com.nostra13.universalimageloader.core.decode;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

public class ImageDecodingInfo {
    private final boolean considerExifParams;
    private final Options decodingOptions = new Options();
    private final ImageDownloader downloader;
    private final Object extraForDownloader;
    private final String imageKey;
    private final ImageScaleType imageScaleType;
    private final String imageUri;
    private final String originalImageUri;
    private final ImageSize targetSize;
    private final ViewScaleType viewScaleType;

    public ImageDecodingInfo(String imageKey, String imageUri, String originalImageUri, ImageSize targetSize, ViewScaleType viewScaleType, ImageDownloader downloader, DisplayImageOptions displayOptions) {
        this.imageKey = imageKey;
        this.imageUri = imageUri;
        this.originalImageUri = originalImageUri;
        this.targetSize = targetSize;
        this.imageScaleType = displayOptions.getImageScaleType();
        this.viewScaleType = viewScaleType;
        this.downloader = downloader;
        this.extraForDownloader = displayOptions.getExtraForDownloader();
        this.considerExifParams = displayOptions.isConsiderExifParams();
        copyOptions(displayOptions.getDecodingOptions(), this.decodingOptions);
    }

    private void copyOptions(Options srcOptions, Options destOptions) {
        destOptions.inDensity = srcOptions.inDensity;
        destOptions.inDither = srcOptions.inDither;
        destOptions.inInputShareable = srcOptions.inInputShareable;
        destOptions.inJustDecodeBounds = srcOptions.inJustDecodeBounds;
        destOptions.inPreferredConfig = srcOptions.inPreferredConfig;
        destOptions.inPurgeable = srcOptions.inPurgeable;
        destOptions.inSampleSize = srcOptions.inSampleSize;
        destOptions.inScaled = srcOptions.inScaled;
        destOptions.inScreenDensity = srcOptions.inScreenDensity;
        destOptions.inTargetDensity = srcOptions.inTargetDensity;
        destOptions.inTempStorage = srcOptions.inTempStorage;
        if (VERSION.SDK_INT >= 10) {
            copyOptions10(srcOptions, destOptions);
        }
        if (VERSION.SDK_INT >= 11) {
            copyOptions11(srcOptions, destOptions);
        }
    }

    @TargetApi(10)
    private void copyOptions10(Options srcOptions, Options destOptions) {
        destOptions.inPreferQualityOverSpeed = srcOptions.inPreferQualityOverSpeed;
    }

    @TargetApi(11)
    private void copyOptions11(Options srcOptions, Options destOptions) {
        destOptions.inBitmap = srcOptions.inBitmap;
        destOptions.inMutable = srcOptions.inMutable;
    }

    public String getImageKey() {
        return this.imageKey;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public String getOriginalImageUri() {
        return this.originalImageUri;
    }

    public ImageSize getTargetSize() {
        return this.targetSize;
    }

    public ImageScaleType getImageScaleType() {
        return this.imageScaleType;
    }

    public ViewScaleType getViewScaleType() {
        return this.viewScaleType;
    }

    public ImageDownloader getDownloader() {
        return this.downloader;
    }

    public Object getExtraForDownloader() {
        return this.extraForDownloader;
    }

    public boolean shouldConsiderExifParams() {
        return this.considerExifParams;
    }

    public Options getDecodingOptions() {
        return this.decodingOptions;
    }
}
