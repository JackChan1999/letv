package com.nostra13.universalimageloader.utils;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import java.io.File;

public final class DiskCacheUtils {
    private DiskCacheUtils() {
    }

    public static File findInCache(String imageUri, DiskCache diskCache) {
        File image = diskCache.get(imageUri);
        return (image == null || !image.exists()) ? null : image;
    }

    public static boolean removeFromCache(String imageUri, DiskCache diskCache) {
        File image = diskCache.get(imageUri);
        return image != null && image.exists() && image.delete();
    }
}
