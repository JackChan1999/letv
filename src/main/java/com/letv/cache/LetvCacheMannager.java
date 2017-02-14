package com.letv.cache;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.widget.ImageView;
import com.letv.cache.LetvCacheTools.ConstantTool;
import com.letv.cache.LetvCacheTools.SDCardTool;
import com.letv.cache.LetvCacheTools.SDCardTool.cleanCacheListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.LetvThumbnailUtils;
import java.io.File;

public final class LetvCacheMannager {
    private static LetvCacheMannager mLetvCacheMannager;
    private DisplayImageOptions options;

    private LetvCacheMannager() {
    }

    public static synchronized LetvCacheMannager getInstance() {
        LetvCacheMannager letvCacheMannager;
        synchronized (LetvCacheMannager.class) {
            if (mLetvCacheMannager == null) {
                mLetvCacheMannager = new LetvCacheMannager();
            }
            letvCacheMannager = mLetvCacheMannager;
        }
        return letvCacheMannager;
    }

    public void init(Context context, LetvThumbnailUtils letvThumbnailUtils) {
        LetvCacheConfiguration.initCacheLibrary(context, letvThumbnailUtils);
        this.options = new Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
    }

    private BitmapDisplayer getDisplayer() {
        if (VERSION.SDK_INT >= 9) {
            return new FadeInBitmapDisplayer(200);
        }
        return new SimpleBitmapDisplayer();
    }

    public synchronized void loadImage(String url, ImageView imageView) {
        if (ImageLoader.getInstance().isInited()) {
            if (!(TextUtils.isEmpty(url) || imageView == null)) {
                ImageLoader.getInstance().displayImage(url, imageView, this.options);
            }
        }
    }

    public synchronized void loadImage(String url, ImageView imageView, DisplayImageOptions options) {
        if (ImageLoader.getInstance().isInited()) {
            if (!(TextUtils.isEmpty(url) || imageView == null)) {
                ImageLoader.getInstance().displayImage(url, imageView, options);
            }
        }
    }

    public synchronized void loadImage(String url, ImageView imageView, ImageLoadingListener loadingListener) {
        if (ImageLoader.getInstance().isInited()) {
            if (!(TextUtils.isEmpty(url) || imageView == null)) {
                ImageLoader.getInstance().displayImage(url, imageView, this.options, loadingListener);
            }
        }
    }

    public synchronized void loadImage(String url, ImageView imageView, DisplayImageOptions options, ImageLoadingListener loadingListener) {
        if (ImageLoader.getInstance().isInited()) {
            if (!(TextUtils.isEmpty(url) || imageView == null)) {
                ImageLoader.getInstance().displayImage(url, imageView, options, loadingListener);
            }
        }
    }

    public synchronized void loadVideoImage(String path, ImageView imageView) {
        if (ImageLoader.getInstance().isInited()) {
            if (!TextUtils.isEmpty(path)) {
                ImageLoader.getInstance().displayImage("LetvThumbnailUtils" + path, imageView, this.options);
            }
        }
    }

    public void loadImageSync(String imgUrl) {
        if (ImageLoader.getInstance().isInited() && !TextUtils.isEmpty(imgUrl)) {
            ImageLoader.getInstance().loadImageSync(imgUrl);
        }
    }

    public void destroy() {
        if (ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().stop();
            ImageLoader.getInstance().clearMemoryCache();
            ImageLoader.getInstance().destroy();
        }
    }

    public void clearCacheBitmap() {
        try {
            if (ImageLoader.getInstance().isInited()) {
                ImageLoader.getInstance().clearMemoryCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanCache(cleanCacheListener listener) {
        if (SDCardTool.sdCardMounted()) {
            SDCardTool.deleteAllFile(ConstantTool.IMAGE_CACHE_PATH, listener);
        } else {
            listener.onErr();
        }
    }

    public static String getCacheSize() {
        if (!SDCardTool.sdCardMounted()) {
            return "";
        }
        File file = new File(ConstantTool.IMAGE_CACHE_PATH);
        if (file == null || !file.exists()) {
            return " 0.00M ";
        }
        return SDCardTool.FormetFileSize(SDCardTool.getFileSize(file));
    }
}
