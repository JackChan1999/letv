package com.letv.mobile.lebox.utils.imagecache;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.letv.mobile.lebox.utils.imagecache.LetvCacheTools.ConstantTool;
import com.letv.mobile.lebox.utils.imagecache.LetvCacheTools.SDCardTool;
import com.letv.mobile.lebox.utils.imagecache.LetvCacheTools.SDCardTool.cleanCacheListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.io.File;

public final class LetvCacheMannager {
    public static final int ANIMATION_DURATION = 800;

    private LetvCacheMannager() {
    }

    public static LetvCacheMannager getInstance() {
        return InstanceHolder.cacheManager;
    }

    public void init(Context context) {
        LetvCacheConfiguration.initCacheLibrary(context);
    }

    public void loadPrestrainImage(String imgUrl, ImageLoadingListener listener) {
        InstanceHolder.imageLoader.loadImage(imgUrl, listener);
    }

    public void loadImage(String url, ImageView imageView) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView);
        }
    }

    public void loadImageWithAnimation(String url, ImageView imageView) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView, InstanceHolder.animationListener);
        }
    }

    public void loadImage(String url, ImageView imageView, DisplayImageOptions options) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView, options);
        }
    }

    public void loadImageWithAnimation(String url, ImageView imageView, DisplayImageOptions options) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView, options, InstanceHolder.animationListener);
        }
    }

    public void loadImage(String url, ImageView imageView, ImageLoadingListener loadingListener) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView, loadingListener);
        }
    }

    public void loadImage(String url, ImageView imageView, DisplayImageOptions options, ImageLoadingListener loadingListener) {
        if (imageView != null) {
            InstanceHolder.imageLoader.displayImage(url, imageView, options, loadingListener);
        }
    }

    public void cancelLoadImage(ImageView imageView) {
        if (imageView != null) {
            InstanceHolder.imageLoader.cancelDisplayTask(imageView);
        }
    }

    public void loadVideoImage(String path, ImageView imageView) {
        InstanceHolder.imageLoader.displayImage("LetvThumbnailUtils" + path, imageView);
    }

    public void loadImageSync(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            InstanceHolder.imageLoader.loadImageSync(imgUrl);
        }
    }

    public void destroy() {
        if (InstanceHolder.imageLoader.isInited()) {
            InstanceHolder.imageLoader.stop();
            InstanceHolder.imageLoader.clearMemoryCache();
            InstanceHolder.imageLoader.destroy();
        }
    }

    public void clearCacheBitmap() {
        try {
            if (InstanceHolder.imageLoader.isInited()) {
                InstanceHolder.imageLoader.clearMemoryCache();
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
        if (file.exists()) {
            return SDCardTool.FormetFileSize(SDCardTool.getFileSize(file));
        }
        return " 0.00M ";
    }
}
