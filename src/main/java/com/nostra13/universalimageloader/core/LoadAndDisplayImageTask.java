package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.FailReason.FailType;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.LetvThumbnailUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

final class LoadAndDisplayImageTask implements Runnable, CopyListener {
    private static final String ERROR_POST_PROCESSOR_NULL = "Post-processor returned null [%s]";
    private static final String ERROR_PRE_PROCESSOR_NULL = "Pre-processor returned null [%s]";
    private static final String ERROR_PROCESSOR_FOR_DISK_CACHE_NULL = "Bitmap processor for disk cache returned null [%s]";
    private static final String LOG_CACHE_IMAGE_IN_MEMORY = "Cache image in memory [%s]";
    private static final String LOG_CACHE_IMAGE_ON_DISK = "Cache image on disk [%s]";
    private static final String LOG_DELAY_BEFORE_LOADING = "Delay %d ms before loading...  [%s]";
    private static final String LOG_GET_IMAGE_FROM_MEMORY_CACHE_AFTER_WAITING = "...Get cached bitmap from memory after waiting. [%s]";
    private static final String LOG_LOAD_IMAGE_FROM_DISK_CACHE = "Load image from disk cache [%s]";
    private static final String LOG_LOAD_IMAGE_FROM_NETWORK = "Load image from network [%s]";
    private static final String LOG_POSTPROCESS_IMAGE = "PostProcess image before displaying [%s]";
    private static final String LOG_PREPROCESS_IMAGE = "PreProcess image before caching in memory [%s]";
    private static final String LOG_PROCESS_IMAGE_BEFORE_CACHE_ON_DISK = "Process image before cache on disk [%s]";
    private static final String LOG_RESIZE_CACHED_IMAGE_FILE = "Resize image in disk cache [%s]";
    private static final String LOG_RESUME_AFTER_PAUSE = ".. Resume loading [%s]";
    private static final String LOG_START_DISPLAY_IMAGE_TASK = "Start display image task [%s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED = "ImageAware was collected by GC. Task is cancelled. [%s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_REUSED = "ImageAware is reused for another image. Task is cancelled. [%s]";
    private static final String LOG_TASK_INTERRUPTED = "Task was interrupted [%s]";
    private static final String LOG_WAITING_FOR_IMAGE_LOADED = "Image already is loading. Waiting... [%s]";
    private static final String LOG_WAITING_FOR_RESUME = "ImageLoader is paused. Waiting...  [%s]";
    private final ImageLoaderConfiguration configuration;
    private final ImageDecoder decoder;
    private final ImageDownloader downloader;
    private final ImageLoaderEngine engine;
    private final Handler handler;
    final ImageAware imageAware;
    private final ImageLoadingInfo imageLoadingInfo;
    final ImageLoadingListener listener;
    private LoadedFrom loadedFrom = LoadedFrom.NETWORK;
    private final String memoryCacheKey;
    private final ImageDownloader networkDeniedDownloader;
    final DisplayImageOptions options;
    final ImageLoadingProgressListener progressListener;
    private final ImageDownloader slowNetworkDownloader;
    private final boolean syncLoading;
    private final ImageSize targetSize;
    private final LetvThumbnailUtils thumbnailUtils;
    final String uri;

    class TaskCancelledException extends Exception {
        TaskCancelledException() {
        }
    }

    public LoadAndDisplayImageTask(ImageLoaderEngine engine, ImageLoadingInfo imageLoadingInfo, Handler handler) {
        this.engine = engine;
        this.imageLoadingInfo = imageLoadingInfo;
        this.handler = handler;
        this.configuration = engine.configuration;
        this.downloader = this.configuration.downloader;
        this.thumbnailUtils = this.configuration.thumbnailUtils;
        this.networkDeniedDownloader = this.configuration.networkDeniedDownloader;
        this.slowNetworkDownloader = this.configuration.slowNetworkDownloader;
        this.decoder = this.configuration.decoder;
        this.uri = imageLoadingInfo.uri;
        this.memoryCacheKey = imageLoadingInfo.memoryCacheKey;
        this.imageAware = imageLoadingInfo.imageAware;
        this.targetSize = imageLoadingInfo.targetSize;
        this.options = imageLoadingInfo.options;
        this.listener = imageLoadingInfo.listener;
        this.progressListener = imageLoadingInfo.progressListener;
        this.syncLoading = this.options.isSyncLoading();
    }

    public void run() {
        if (!waitIfPaused() && !delayIfNeed()) {
            ReentrantLock loadFromUriLock = this.imageLoadingInfo.loadFromUriLock;
            L.d(LOG_START_DISPLAY_IMAGE_TASK, this.memoryCacheKey);
            if (loadFromUriLock.isLocked()) {
                L.d(LOG_WAITING_FOR_IMAGE_LOADED, this.memoryCacheKey);
            }
            loadFromUriLock.lock();
            try {
                checkTaskNotActual();
                Bitmap bmp = (Bitmap) this.configuration.memoryCache.get(this.memoryCacheKey);
                if (bmp == null || bmp.isRecycled()) {
                    bmp = tryLoadBitmap();
                    if (bmp != null) {
                        checkTaskNotActual();
                        checkTaskInterrupted();
                        if (this.options.shouldPreProcess()) {
                            L.d(LOG_PREPROCESS_IMAGE, this.memoryCacheKey);
                            bmp = this.options.getPreProcessor().process(bmp);
                            if (bmp == null) {
                                L.e(ERROR_PRE_PROCESSOR_NULL, this.memoryCacheKey);
                            }
                        }
                        if (bmp != null && this.options.isCacheInMemory()) {
                            L.d(LOG_CACHE_IMAGE_IN_MEMORY, this.memoryCacheKey);
                            this.configuration.memoryCache.put(this.memoryCacheKey, bmp);
                        }
                    } else {
                        return;
                    }
                }
                this.loadedFrom = LoadedFrom.MEMORY_CACHE;
                L.d(LOG_GET_IMAGE_FROM_MEMORY_CACHE_AFTER_WAITING, this.memoryCacheKey);
                if (bmp != null && this.options.shouldPostProcess()) {
                    L.d(LOG_POSTPROCESS_IMAGE, this.memoryCacheKey);
                    bmp = this.options.getPostProcessor().process(bmp);
                    if (bmp == null) {
                        L.e(ERROR_POST_PROCESSOR_NULL, this.memoryCacheKey);
                    }
                }
                checkTaskNotActual();
                checkTaskInterrupted();
                loadFromUriLock.unlock();
                runTask(new DisplayBitmapTask(bmp, this.imageLoadingInfo, this.engine, this.loadedFrom), this.syncLoading, this.handler, this.engine);
            } catch (TaskCancelledException e) {
                fireCancelEvent();
            } finally {
                loadFromUriLock.unlock();
            }
        }
    }

    private boolean waitIfPaused() {
        AtomicBoolean pause = this.engine.getPause();
        if (pause.get()) {
            synchronized (this.engine.getPauseLock()) {
                if (pause.get()) {
                    L.d(LOG_WAITING_FOR_RESUME, this.memoryCacheKey);
                    try {
                        this.engine.getPauseLock().wait();
                        L.d(LOG_RESUME_AFTER_PAUSE, this.memoryCacheKey);
                    } catch (InterruptedException e) {
                        L.e(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
                        return true;
                    }
                }
            }
        }
        return isTaskNotActual();
    }

    private boolean delayIfNeed() {
        if (!this.options.shouldDelayBeforeLoading()) {
            return false;
        }
        L.d(LOG_DELAY_BEFORE_LOADING, Integer.valueOf(this.options.getDelayBeforeLoading()), this.memoryCacheKey);
        try {
            Thread.sleep((long) this.options.getDelayBeforeLoading());
            return isTaskNotActual();
        } catch (InterruptedException e) {
            L.e(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
            return true;
        }
    }

    private Bitmap tryLoadBitmap() throws TaskCancelledException {
        Bitmap bitmap = null;
        if (this.thumbnailUtils != null && this.uri.startsWith("LetvThumbnailUtils")) {
            bitmap = this.thumbnailUtils.getThumbnailBitmap(this.uri.replace("LetvThumbnailUtils", ""));
            if (bitmap != null) {
                return bitmap;
            }
        }
        try {
            File imageFile = this.configuration.diskCache.get(this.uri);
            if (imageFile != null && imageFile.exists()) {
                L.d(LOG_LOAD_IMAGE_FROM_DISK_CACHE, this.memoryCacheKey);
                this.loadedFrom = LoadedFrom.DISC_CACHE;
                checkTaskNotActual();
                bitmap = decodeImage(Scheme.FILE.wrap(imageFile.getAbsolutePath()));
            }
            if (bitmap == null || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
                L.d(LOG_LOAD_IMAGE_FROM_NETWORK, this.memoryCacheKey);
                this.loadedFrom = LoadedFrom.NETWORK;
                String imageUriForDecoding = this.uri;
                if (this.options.isCacheOnDisk() && tryCacheImageOnDisk()) {
                    imageFile = this.configuration.diskCache.get(this.uri);
                    if (imageFile != null) {
                        imageUriForDecoding = Scheme.FILE.wrap(imageFile.getAbsolutePath());
                    }
                }
                checkTaskNotActual();
                bitmap = decodeImage(imageUriForDecoding);
                if (bitmap == null || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
                    fireFailEvent(FailType.DECODING_ERROR, null);
                }
            }
        } catch (IllegalStateException e) {
            fireFailEvent(FailType.NETWORK_DENIED, null);
        } catch (TaskCancelledException e2) {
            throw e2;
        } catch (IOException e3) {
            L.e(e3);
            fireFailEvent(FailType.IO_ERROR, e3);
        } catch (OutOfMemoryError e4) {
            L.e(e4);
            fireFailEvent(FailType.OUT_OF_MEMORY, e4);
        } catch (Throwable e5) {
            L.e(e5);
            fireFailEvent(FailType.UNKNOWN, e5);
        }
        return bitmap;
    }

    private Bitmap decodeImage(String imageUri) throws IOException {
        String str = imageUri;
        return this.decoder.decode(new ImageDecodingInfo(this.memoryCacheKey, str, this.uri, this.targetSize, this.imageAware.getScaleType(), getDownloader(), this.options));
    }

    private boolean tryCacheImageOnDisk() throws TaskCancelledException {
        L.d(LOG_CACHE_IMAGE_ON_DISK, this.memoryCacheKey);
        try {
            boolean loaded = downloadImage();
            if (!loaded) {
                return loaded;
            }
            int width = this.configuration.maxImageWidthForDiskCache;
            int height = this.configuration.maxImageHeightForDiskCache;
            if (width <= 0 && height <= 0) {
                return loaded;
            }
            L.d(LOG_RESIZE_CACHED_IMAGE_FILE, this.memoryCacheKey);
            resizeAndSaveImage(width, height);
            return loaded;
        } catch (IOException e) {
            L.e(e);
            return false;
        }
    }

    private boolean downloadImage() throws IOException {
        return this.configuration.diskCache.save(this.uri, getDownloader().getStream(this.uri, this.options.getExtraForDownloader()), this);
    }

    private boolean resizeAndSaveImage(int maxWidth, int maxHeight) throws IOException {
        File targetFile = this.configuration.diskCache.get(this.uri);
        if (targetFile == null || !targetFile.exists()) {
            return false;
        }
        Bitmap bmp = this.decoder.decode(new ImageDecodingInfo(this.memoryCacheKey, Scheme.FILE.wrap(targetFile.getAbsolutePath()), this.uri, new ImageSize(maxWidth, maxHeight), ViewScaleType.FIT_INSIDE, getDownloader(), new Builder().cloneFrom(this.options).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build()));
        if (!(bmp == null || this.configuration.processorForDiskCache == null)) {
            L.d(LOG_PROCESS_IMAGE_BEFORE_CACHE_ON_DISK, this.memoryCacheKey);
            bmp = this.configuration.processorForDiskCache.process(bmp);
            if (bmp == null) {
                L.e(ERROR_PROCESSOR_FOR_DISK_CACHE_NULL, this.memoryCacheKey);
            }
        }
        if (bmp == null) {
            return false;
        }
        boolean saved = this.configuration.diskCache.save(this.uri, bmp);
        bmp.recycle();
        return saved;
    }

    public boolean onBytesCopied(int current, int total) {
        return this.syncLoading || fireProgressEvent(current, total);
    }

    private boolean fireProgressEvent(final int current, final int total) {
        if (isTaskInterrupted() || isTaskNotActual()) {
            return false;
        }
        if (this.progressListener != null) {
            runTask(new Runnable() {
                public void run() {
                    LoadAndDisplayImageTask.this.progressListener.onProgressUpdate(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView(), current, total);
                }
            }, false, this.handler, this.engine);
        }
        return true;
    }

    private void fireFailEvent(final FailType failType, final Throwable failCause) {
        if (!this.syncLoading && !isTaskInterrupted() && !isTaskNotActual()) {
            runTask(new Runnable() {
                public void run() {
                    if (LoadAndDisplayImageTask.this.options.shouldShowImageOnFail()) {
                        LoadAndDisplayImageTask.this.imageAware.setImageDrawable(LoadAndDisplayImageTask.this.options.getImageOnFail(LoadAndDisplayImageTask.this.configuration.resources));
                    }
                    LoadAndDisplayImageTask.this.listener.onLoadingFailed(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView(), new FailReason(failType, failCause));
                }
            }, false, this.handler, this.engine);
        }
    }

    private void fireCancelEvent() {
        if (!this.syncLoading && !isTaskInterrupted()) {
            runTask(new Runnable() {
                public void run() {
                    LoadAndDisplayImageTask.this.listener.onLoadingCancelled(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView());
                }
            }, false, this.handler, this.engine);
        }
    }

    private ImageDownloader getDownloader() {
        if (this.engine.isNetworkDenied()) {
            return this.networkDeniedDownloader;
        }
        if (this.engine.isSlowNetwork()) {
            return this.slowNetworkDownloader;
        }
        return this.downloader;
    }

    private void checkTaskNotActual() throws TaskCancelledException {
        checkViewCollected();
        checkViewReused();
    }

    private boolean isTaskNotActual() {
        return isViewCollected() || isViewReused();
    }

    private void checkViewCollected() throws TaskCancelledException {
        if (isViewCollected()) {
            throw new TaskCancelledException();
        }
    }

    private boolean isViewCollected() {
        if (!this.imageAware.isCollected()) {
            return false;
        }
        L.d(LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED, this.memoryCacheKey);
        return true;
    }

    private void checkViewReused() throws TaskCancelledException {
        if (isViewReused()) {
            throw new TaskCancelledException();
        }
    }

    private boolean isViewReused() {
        boolean imageAwareWasReused;
        if (this.memoryCacheKey.equals(this.engine.getLoadingUriForView(this.imageAware))) {
            imageAwareWasReused = false;
        } else {
            imageAwareWasReused = true;
        }
        if (!imageAwareWasReused) {
            return false;
        }
        L.d(LOG_TASK_CANCELLED_IMAGEAWARE_REUSED, this.memoryCacheKey);
        return true;
    }

    private void checkTaskInterrupted() throws TaskCancelledException {
        if (isTaskInterrupted()) {
            throw new TaskCancelledException();
        }
    }

    private boolean isTaskInterrupted() {
        if (!Thread.interrupted()) {
            return false;
        }
        L.d(LOG_TASK_INTERRUPTED, this.memoryCacheKey);
        return true;
    }

    String getLoadingUri() {
        return this.uri;
    }

    static void runTask(Runnable r, boolean sync, Handler handler, ImageLoaderEngine engine) {
        if (sync) {
            r.run();
        } else if (handler == null) {
            engine.fireCallback(r);
        } else {
            handler.post(r);
        }
    }
}
