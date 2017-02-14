package com.nostra13.universalimageloader.core.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.L;
import java.io.IOException;
import java.io.InputStream;

public class BaseImageDecoder implements ImageDecoder {
    protected static final String ERROR_CANT_DECODE_IMAGE = "Image can't be decoded [%s]";
    protected static final String LOG_FLIP_IMAGE = "Flip image horizontally [%s]";
    protected static final String LOG_ROTATE_IMAGE = "Rotate image on %1$d° [%2$s]";
    protected static final String LOG_SCALE_IMAGE = "Scale subsampled image (%1$s) to %2$s (scale = %3$.5f) [%4$s]";
    protected static final String LOG_SUBSAMPLE_IMAGE = "Subsample original image (%1$s) to %2$s (scale = %3$d) [%4$s]";
    protected final boolean loggingEnabled;

    protected static class ExifInfo {
        public final boolean flipHorizontal;
        public final int rotation;

        protected ExifInfo() {
            this.rotation = 0;
            this.flipHorizontal = false;
        }

        protected ExifInfo(int rotation, boolean flipHorizontal) {
            this.rotation = rotation;
            this.flipHorizontal = flipHorizontal;
        }
    }

    protected static class ImageFileInfo {
        public final ExifInfo exif;
        public final ImageSize imageSize;

        protected ImageFileInfo(ImageSize imageSize, ExifInfo exif) {
            this.imageSize = imageSize;
            this.exif = exif;
        }
    }

    public BaseImageDecoder(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public Bitmap decode(ImageDecodingInfo decodingInfo) throws IOException {
        InputStream imageStream = getImageStream(decodingInfo);
        try {
            ImageFileInfo imageInfo = defineImageSizeAndRotation(imageStream, decodingInfo);
            imageStream = resetStream(imageStream, decodingInfo);
            Bitmap decodedBitmap = BitmapFactory.decodeStream(imageStream, null, prepareDecodingOptions(imageInfo.imageSize, decodingInfo));
            if (decodedBitmap != null) {
                return considerExactScaleAndOrientatiton(decodedBitmap, decodingInfo, imageInfo.exif.rotation, imageInfo.exif.flipHorizontal);
            }
            L.e(ERROR_CANT_DECODE_IMAGE, decodingInfo.getImageKey());
            return decodedBitmap;
        } finally {
            IoUtils.closeSilently(imageStream);
        }
    }

    protected InputStream getImageStream(ImageDecodingInfo decodingInfo) throws IOException {
        return decodingInfo.getDownloader().getStream(decodingInfo.getImageUri(), decodingInfo.getExtraForDownloader());
    }

    protected ImageFileInfo defineImageSizeAndRotation(InputStream imageStream, ImageDecodingInfo decodingInfo) throws IOException {
        ExifInfo exif;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageStream, null, options);
        String imageUri = decodingInfo.getImageUri();
        if (decodingInfo.shouldConsiderExifParams() && canDefineExifParams(imageUri, options.outMimeType)) {
            exif = defineExifOrientation(imageUri);
        } else {
            exif = new ExifInfo();
        }
        return new ImageFileInfo(new ImageSize(options.outWidth, options.outHeight, exif.rotation), exif);
    }

    private boolean canDefineExifParams(String imageUri, String mimeType) {
        return "image/jpeg".equalsIgnoreCase(mimeType) && Scheme.ofUri(imageUri) == Scheme.FILE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected com.nostra13.universalimageloader.core.decode.BaseImageDecoder.ExifInfo defineExifOrientation(java.lang.String r9) {
        /*
        r8 = this;
        r7 = 1;
        r4 = 0;
        r3 = 0;
        r1 = new android.media.ExifInterface;	 Catch:{ IOException -> 0x002d }
        r5 = com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme.FILE;	 Catch:{ IOException -> 0x002d }
        r5 = r5.crop(r9);	 Catch:{ IOException -> 0x002d }
        r1.<init>(r5);	 Catch:{ IOException -> 0x002d }
        r5 = "Orientation";
        r6 = 1;
        r2 = r1.getAttributeInt(r5, r6);	 Catch:{ IOException -> 0x002d }
        switch(r2) {
            case 1: goto L_0x001f;
            case 2: goto L_0x001e;
            case 3: goto L_0x0026;
            case 4: goto L_0x0025;
            case 5: goto L_0x0029;
            case 6: goto L_0x0022;
            case 7: goto L_0x0021;
            case 8: goto L_0x002a;
            default: goto L_0x0018;
        };
    L_0x0018:
        r5 = new com.nostra13.universalimageloader.core.decode.BaseImageDecoder$ExifInfo;
        r5.<init>(r4, r3);
        return r5;
    L_0x001e:
        r3 = 1;
    L_0x001f:
        r4 = 0;
        goto L_0x0018;
    L_0x0021:
        r3 = 1;
    L_0x0022:
        r4 = 90;
        goto L_0x0018;
    L_0x0025:
        r3 = 1;
    L_0x0026:
        r4 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        goto L_0x0018;
    L_0x0029:
        r3 = 1;
    L_0x002a:
        r4 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        goto L_0x0018;
    L_0x002d:
        r0 = move-exception;
        r5 = "Can't read EXIF tags from file [%s]";
        r6 = new java.lang.Object[r7];
        r7 = 0;
        r6[r7] = r9;
        com.nostra13.universalimageloader.utils.L.w(r5, r6);
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nostra13.universalimageloader.core.decode.BaseImageDecoder.defineExifOrientation(java.lang.String):com.nostra13.universalimageloader.core.decode.BaseImageDecoder$ExifInfo");
    }

    protected Options prepareDecodingOptions(ImageSize imageSize, ImageDecodingInfo decodingInfo) {
        int scale;
        ImageScaleType scaleType = decodingInfo.getImageScaleType();
        if (scaleType == ImageScaleType.NONE) {
            scale = 1;
        } else if (scaleType == ImageScaleType.NONE_SAFE) {
            scale = ImageSizeUtils.computeMinImageSampleSize(imageSize);
        } else {
            boolean powerOf2;
            ImageSize targetSize = decodingInfo.getTargetSize();
            if (scaleType == ImageScaleType.IN_SAMPLE_POWER_OF_2) {
                powerOf2 = true;
            } else {
                powerOf2 = false;
            }
            scale = ImageSizeUtils.computeImageSampleSize(imageSize, targetSize, decodingInfo.getViewScaleType(), powerOf2);
        }
        if (scale > 1 && this.loggingEnabled) {
            L.d(LOG_SUBSAMPLE_IMAGE, imageSize, imageSize.scaleDown(scale), Integer.valueOf(scale), decodingInfo.getImageKey());
        }
        Options decodingOptions = decodingInfo.getDecodingOptions();
        decodingOptions.inSampleSize = scale;
        return decodingOptions;
    }

    protected InputStream resetStream(InputStream imageStream, ImageDecodingInfo decodingInfo) throws IOException {
        try {
            imageStream.reset();
            return imageStream;
        } catch (IOException e) {
            IoUtils.closeSilently(imageStream);
            return getImageStream(decodingInfo);
        }
    }

    protected Bitmap considerExactScaleAndOrientatiton(Bitmap subsampledBitmap, ImageDecodingInfo decodingInfo, int rotation, boolean flipHorizontal) {
        Matrix m = new Matrix();
        ImageScaleType scaleType = decodingInfo.getImageScaleType();
        if (scaleType == ImageScaleType.EXACTLY || scaleType == ImageScaleType.EXACTLY_STRETCHED) {
            float scale = ImageSizeUtils.computeImageScale(new ImageSize(subsampledBitmap.getWidth(), subsampledBitmap.getHeight(), rotation), decodingInfo.getTargetSize(), decodingInfo.getViewScaleType(), scaleType == ImageScaleType.EXACTLY_STRETCHED);
            if (Float.compare(scale, 1.0f) != 0) {
                m.setScale(scale, scale);
                if (this.loggingEnabled) {
                    L.d(LOG_SCALE_IMAGE, srcSize, srcSize.scale(scale), Float.valueOf(scale), decodingInfo.getImageKey());
                }
            }
        }
        if (flipHorizontal) {
            m.postScale(-1.0f, 1.0f);
            if (this.loggingEnabled) {
                L.d(LOG_FLIP_IMAGE, decodingInfo.getImageKey());
            }
        }
        if (rotation != 0) {
            m.postRotate((float) rotation);
            if (this.loggingEnabled) {
                L.d(LOG_ROTATE_IMAGE, Integer.valueOf(rotation), decodingInfo.getImageKey());
            }
        }
        Bitmap finalBitmap = Bitmap.createBitmap(subsampledBitmap, 0, 0, subsampledBitmap.getWidth(), subsampledBitmap.getHeight(), m, true);
        if (finalBitmap != subsampledBitmap) {
            subsampledBitmap.recycle();
        }
        return finalBitmap;
    }
}
