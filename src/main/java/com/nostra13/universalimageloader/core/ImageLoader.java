package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

public class ImageLoader {
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";
    private static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
    private static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
    static final String LOG_DESTROY = "Destroy ImageLoader";
    static final String LOG_INIT_CONFIG = "Initialize ImageLoader with configuration";
    static final String LOG_LOAD_IMAGE_FROM_MEMORY_CACHE = "Load image from memory cache [%s]";
    public static final String TAG = ImageLoader.class.getSimpleName();
    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.";
    private static volatile ImageLoader instance;
    private ImageLoaderConfiguration configuration;
    private final ImageLoadingListener emptyListener = new SimpleImageLoadingListener();
    private ImageLoaderEngine engine;

    private static class SyncImageLoadingListener extends SimpleImageLoadingListener {
        private Bitmap loadedImage;

        private SyncImageLoadingListener() {
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            this.loadedImage = loadedImage;
        }

        public Bitmap getLoadedBitmap() {
            return this.loadedImage;
        }
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    protected ImageLoader() {
    }

    public synchronized void init(ImageLoaderConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        } else if (this.configuration == null) {
            L.d(LOG_INIT_CONFIG, new Object[0]);
            this.engine = new ImageLoaderEngine(configuration);
            this.configuration = configuration;
        } else {
            L.w(WARNING_RE_INIT_CONFIG, new Object[0]);
        }
    }

    public boolean isInited() {
        return this.configuration != null;
    }

    public void displayImage(String uri, ImageAware imageAware) {
        displayImage(uri, imageAware, null, null, null);
    }

    public void displayImage(String uri, ImageAware imageAware, ImageLoadingListener listener) {
        displayImage(uri, imageAware, null, listener, null);
    }

    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options) {
        displayImage(uri, imageAware, options, null, null);
    }

    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageLoadingListener listener) {
        displayImage(uri, imageAware, options, listener, null);
    }

    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        checkConfiguration();
        if (imageAware == null) {
            throw new IllegalArgumentException(ERROR_WRONG_ARGUMENTS);
        }
        if (listener == null) {
            listener = this.emptyListener;
        }
        if (options == null) {
            options = this.configuration.defaultDisplayImageOptions;
        }
        if (TextUtils.isEmpty(uri)) {
            this.engine.cancelDisplayTaskFor(imageAware);
            listener.onLoadingStarted(uri, imageAware.getWrappedView());
            if (options.shouldShowImageForEmptyUri()) {
                imageAware.setImageDrawable(options.getImageForEmptyUri(this.configuration.resources));
            } else {
                imageAware.setImageDrawable(null);
            }
            listener.onLoadingComplete(uri, imageAware.getWrappedView(), null);
            return;
        }
        ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(imageAware, this.configuration.getMaxImageSize());
        String memoryCacheKey = MemoryCacheUtils.generateKey(uri, targetSize);
        this.engine.prepareDisplayTaskFor(imageAware, memoryCacheKey);
        listener.onLoadingStarted(uri, imageAware.getWrappedView());
        Bitmap bmp = (Bitmap) this.configuration.memoryCache.get(memoryCacheKey);
        if (bmp == null || bmp.isRecycled()) {
            if (options.shouldShowImageOnLoading()) {
                imageAware.setImageDrawable(options.getImageOnLoading(this.configuration.resources));
            } else if (options.isResetViewBeforeLoading()) {
                imageAware.setImageDrawable(null);
            }
            LoadAndDisplayImageTask displayTask = new LoadAndDisplayImageTask(this.engine, new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey, options, listener, progressListener, this.engine.getLockForUri(uri)), defineHandler(options));
            if (options.isSyncLoading()) {
                displayTask.run();
                return;
            } else {
                this.engine.submit(displayTask);
                return;
            }
        }
        L.d(LOG_LOAD_IMAGE_FROM_MEMORY_CACHE, memoryCacheKey);
        if (options.shouldPostProcess()) {
            ProcessAndDisplayImageTask displayTask2 = new ProcessAndDisplayImageTask(this.engine, bmp, new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey, options, listener, progressListener, this.engine.getLockForUri(uri)), defineHandler(options));
            if (options.isSyncLoading()) {
                displayTask2.run();
                return;
            } else {
                this.engine.submit(displayTask2);
                return;
            }
        }
        options.getDisplayer().display(bmp, imageAware, LoadedFrom.MEMORY_CACHE);
        listener.onLoadingComplete(uri, imageAware.getWrappedView(), bmp);
    }

    public void displayImage(String uri, ImageView imageView) {
        displayImage(uri, new ImageViewAware(imageView), null, null, null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
        displayImage(uri, new ImageViewAware(imageView), options, null, null);
    }

    public void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {
        displayImage(uri, new ImageViewAware(imageView), null, listener, null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
        displayImage(uri, imageView, options, listener, null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        displayImage(uri, new ImageViewAware(imageView), options, listener, progressListener);
    }

    public void loadImage(String uri, ImageLoadingListener listener) {
        loadImage(uri, null, null, listener, null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, ImageLoadingListener listener) {
        loadImage(uri, targetImageSize, null, listener, null);
    }

    public void loadImage(String uri, DisplayImageOptions options, ImageLoadingListener listener) {
        loadImage(uri, null, options, listener, null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, DisplayImageOptions options, ImageLoadingListener listener) {
        loadImage(uri, targetImageSize, options, listener, null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        checkConfiguration();
        if (targetImageSize == null) {
            targetImageSize = this.configuration.getMaxImageSize();
        }
        if (options == null) {
            options = this.configuration.defaultDisplayImageOptions;
        }
        displayImage(uri, new NonViewAware(uri, targetImageSize, ViewScaleType.CROP), options, listener, progressListener);
    }

    public Bitmap loadImageSync(String uri) {
        return loadImageSync(uri, null, null);
    }

    public Bitmap loadImageSync(String uri, DisplayImageOptions options) {
        return loadImageSync(uri, null, options);
    }

    public Bitmap loadImageSync(String uri, ImageSize targetImageSize) {
        return loadImageSync(uri, targetImageSize, null);
    }

    public Bitmap loadImageSync(String uri, ImageSize targetImageSize, DisplayImageOptions options) {
        if (options == null) {
            options = this.configuration.defaultDisplayImageOptions;
        }
        options = new Builder().cloneFrom(options).syncLoading(false).build();
        SyncImageLoadingListener listener = new SyncImageLoadingListener();
        loadImage(uri, targetImageSize, options, listener);
        return listener.getLoadedBitmap();
    }

    private void checkConfiguration() {
        if (this.configuration == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }

    public MemoryCache getMemoryCache() {
        checkConfiguration();
        return this.configuration.memoryCache;
    }

    public void clearMemoryCache() {
        checkConfiguration();
        this.configuration.memoryCache.clear();
    }

    @Deprecated
    public DiskCache getDiscCache() {
        return getDiskCache();
    }

    public DiskCache getDiskCache() {
        checkConfiguration();
        return this.configuration.diskCache;
    }

    @Deprecated
    public void clearDiscCache() {
        clearDiskCache();
    }

    public void clearDiskCache() {
        checkConfiguration();
        this.configuration.diskCache.clear();
    }

    public String getLoadingUriForView(ImageAware imageAware) {
        return this.engine.getLoadingUriForView(imageAware);
    }

    public String getLoadingUriForView(ImageView imageView) {
        return this.engine.getLoadingUriForView(new ImageViewAware(imageView));
    }

    public void cancelDisplayTask(ImageAware imageAware) {
        this.engine.cancelDisplayTaskFor(imageAware);
    }

    public void cancelDisplayTask(ImageView imageView) {
        this.engine.cancelDisplayTaskFor(new ImageViewAware(imageView));
    }

    public void denyNetworkDownloads(boolean denyNetworkDownloads) {
        this.engine.denyNetworkDownloads(denyNetworkDownloads);
    }

    public void handleSlowNetwork(boolean handleSlowNetwork) {
        this.engine.handleSlowNetwork(handleSlowNetwork);
    }

    public void pause() {
        if (this.engine != null) {
            this.engine.pause();
        }
    }

    public void resume() {
        if (this.engine != null) {
            this.engine.resume();
        }
    }

    public void stop() {
        if (this.engine != null) {
            this.engine.stop();
        }
    }

    public void destroy() {
        if (this.configuration != null) {
            L.d(LOG_DESTROY, new Object[0]);
        }
        stop();
        this.configuration.diskCache.close();
        this.engine = null;
        this.configuration = null;
    }

    private static Handler defineHandler(DisplayImageOptions options) {
        Handler handler = options.getHandler();
        if (options.isSyncLoading()) {
            return null;
        }
        if (handler == null && Looper.myLooper() == Looper.getMainLooper()) {
            return new Handler();
        }
        return handler;
    }
}
