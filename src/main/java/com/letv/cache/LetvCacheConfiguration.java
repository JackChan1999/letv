package com.letv.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import com.letv.cache.LetvCacheTools.ConstantTool;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.LetvThumbnailUtils;
import java.io.File;

public class LetvCacheConfiguration {
    private static final int MAX_FILE_COUNT = 100;
    private static final int MAX_FILE_TOTAL_SIZE = 20971520;

    public static void initCacheLibrary(Context context, LetvThumbnailUtils thumbnailUtils) {
        ImageLoader.getInstance().init(new Builder(context).memoryCache(getMemoryCache(context)).diskCache(getDiscCache()).threadPoolSize(3).threadPriority(3).denyCacheImageMultipleSizesInMemory().imageDownloader(new BaseImageDownloader(context)).tasksProcessingOrder(QueueProcessingType.LIFO).setThumbnailUtils(thumbnailUtils).writeDebugLogs().build());
    }

    public static LruMemoryCache getMemoryCache(Context context) {
        int cacheSize = 4194304;
        try {
            int i;
            int memClass = ((ActivityManager) context.getSystemService("activity")).getMemoryClass();
            int availableSize = memClass >> 3;
            if (availableSize == 0) {
                i = 4;
            } else {
                i = availableSize;
            }
            cacheSize = 1048576 * i;
            Log.d("ljn", "getMemoryCache---memClass:" + memClass + "----availableSize:" + availableSize);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new LruMemoryCache(cacheSize);
    }

    public static DiskCache getDiscCache() {
        return new UnlimitedDiscCache(new File(ConstantTool.IMAGE_CACHE_PATH), null, new Md5FileNameGenerator());
    }
}
