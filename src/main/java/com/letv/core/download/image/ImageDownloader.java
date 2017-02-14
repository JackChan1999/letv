package com.letv.core.download.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.utils.StoreUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    public static final int PLACEHOLD_NULL = -2;
    private static ImageDownloader instance;
    private static ImageRequestQueue mRequestQueue;
    private CustomBitmapMaker mBitmapMaker;
    private BitmapCache mCache;
    private Context mContext;
    public String mLocalPath;
    private Map<String, SoftReference<Bitmap>> mPlaceHolderMap = new HashMap();

    public enum BitmapStyle {
        ROUND,
        CORNER
    }

    public static class CustomConfig {
        public final int cornerRadius;
        public final boolean placeholderKeepIntact;
        public final BitmapStyle style;

        public CustomConfig(BitmapStyle style, int cornerRadius) {
            this(style, cornerRadius, false);
        }

        public CustomConfig(BitmapStyle style, int cornerRadius, boolean placeholderKeepIntact) {
            this.style = style;
            this.cornerRadius = cornerRadius;
            this.placeholderKeepIntact = placeholderKeepIntact;
        }

        public String getKey(String url) {
            if (TextUtils.isEmpty(url)) {
                return "";
            }
            return url + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.style + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.cornerRadius;
        }
    }

    private ImageDownloader() {
        closeCache();
        init();
    }

    public static synchronized ImageDownloader getInstance() {
        ImageDownloader imageDownloader;
        synchronized (ImageDownloader.class) {
            if (instance == null) {
                instance = new ImageDownloader();
            }
            instance.setContext(BaseApplication.getInstance());
            if (mRequestQueue == null || !mRequestQueue.isRunning()) {
                instance.init();
            }
            imageDownloader = instance;
        }
        return imageDownloader;
    }

    private void setContext(Context context) {
        this.mContext = context;
    }

    private void init() {
        this.mCache = new BitmapCache(this.mContext);
        this.mBitmapMaker = new CustomBitmapMaker();
        mRequestQueue = new ImageRequestQueue(this.mCache, this.mBitmapMaker);
        mRequestQueue.start();
    }

    public void onDestory() {
        mRequestQueue.stop();
        this.mCache.clearMemoryCache();
        instance = null;
    }

    public void fluchCache() {
        if (this.mCache != null && this.mCache.getDiscCache() != null) {
            this.mCache.getDiscCache().fluchCache();
        }
    }

    public void closeCache() {
        if (this.mCache != null && this.mCache.getDiscCache() != null) {
            this.mCache.getDiscCache().closeCache();
        }
    }

    public void download(String url) {
        doDownload(null, url, null, -1, ScaleType.CENTER, true, null, false, null);
    }

    public void download(View view, String url) {
        doDownload(view, url, null, -1, ScaleType.CENTER, true, null, false, null);
    }

    public void download(View view, String url, CustomConfig customConfig) {
        doDownload(view, url, null, -1, ScaleType.CENTER, true, null, false, customConfig);
    }

    public void download(String url, ImageDownloadStateListener listener) {
        doDownload(null, url, listener, -1, ScaleType.CENTER, true, null, false, null);
    }

    public void download(String url, ImageDownloadStateListener listener, CustomConfig customConfig) {
        doDownload(null, url, listener, -1, ScaleType.CENTER, true, null, false, customConfig);
    }

    public void download(String url, ImageDownloadStateListener listener, String localPath) {
        download(null, url, listener, -1, ScaleType.CENTER, true, null, false, localPath);
    }

    public void download(View view, String url, int placeholder) {
        doDownload(view, url, null, placeholder, ScaleType.CENTER, true, null, false, null);
    }

    public void download(View view, String url, int placeholder, CustomConfig customConfig) {
        doDownload(view, url, null, placeholder, ScaleType.CENTER, true, null, false, customConfig);
    }

    public void download(View view, String url, int placeholder, ScaleType scaleType, CustomConfig customConfig) {
        doDownload(view, url, null, placeholder, scaleType, true, null, false, customConfig);
    }

    public void download(View view, String url, ImageDownloadStateListener listener, CustomConfig customConfig) {
        doDownload(view, url, listener, -1, ScaleType.CENTER, true, null, false, customConfig);
    }

    public void download(View view, String url, int placeholder, ScaleType placeScaleType) {
        doDownload(view, url, null, placeholder, placeScaleType, true, null, false, null);
    }

    public void download(View view, String url, int placeholder, boolean downLoad) {
        doDownload(view, url, null, placeholder, ScaleType.CENTER, downLoad, null, false, null);
    }

    public void download(View view, String url, int placeholder, boolean downLoad, boolean doAnim) {
        doDownload(view, url, null, placeholder, ScaleType.CENTER, downLoad, null, doAnim, null);
    }

    public void download(View view, String url, int placeholder, ScaleType scaleType, boolean downLoad, boolean doAnim) {
        doDownload(view, url, null, placeholder, scaleType, downLoad, null, doAnim, null);
    }

    public void download(View view, String url, int placeholder, BitmapConfig config) {
        doDownload(view, url, null, placeholder, ScaleType.CENTER, true, config, false, null);
    }

    @SuppressLint({"InlinedApi"})
    private void doDownload(View view, String url, ImageDownloadStateListener listener, int placeholder, ScaleType placeScaleType, boolean downLoad, BitmapConfig config, boolean doAnim, CustomConfig customConfig) {
        if (view != null) {
            view.setTag(R.id.view_tag, url);
            if (view.getTag(R.id.custom_image) instanceof CustomConfig) {
                customConfig = (CustomConfig) view.getTag(R.id.custom_image);
            }
        }
        if (TextUtils.isEmpty(url)) {
            setPlaceHolder(view, placeholder, placeScaleType, customConfig);
            callback(listener, null, 2);
            return;
        }
        callback(listener, null, 0);
        Bitmap bitmap = null;
        if (this.mCache != null) {
            bitmap = this.mCache.getBitmapFromMemoryCache(url, customConfig);
        }
        if (bitmap != null) {
            setBitmapForView(view, bitmap, url, false);
            callback(listener, bitmap, 1);
            if (view != null) {
                callback(view, listener, bitmap, 1, this.mLocalPath);
                return;
            }
            return;
        }
        setPlaceHolder(view, placeholder, placeScaleType, customConfig);
        if (downLoad) {
            mRequestQueue.add(new DownloadRequest(url, view, listener, config, doAnim, customConfig));
        }
    }

    @SuppressLint({"InlinedApi"})
    public void download(View view, String url, ImageDownloadStateListener listener, int placeholder, ScaleType placeScaleType, boolean downLoad, BitmapConfig config, boolean doAnim, String localPath) {
        if (TextUtils.isEmpty(url)) {
            setPlaceHolder(view, placeholder, placeScaleType, null);
            callback(listener, null, 2, localPath);
            return;
        }
        if (view != null) {
            view.setTag(R.id.view_tag, url);
        }
        callback(listener, null, 0, localPath);
        Bitmap bitmap = null;
        if (this.mCache != null) {
            bitmap = this.mCache.getBitmapFromMemoryCache(url, null);
        }
        if (bitmap != null) {
            setBitmapForView(view, bitmap, url, false);
            callback(listener, bitmap, 1, localPath);
            return;
        }
        setPlaceHolder(view, placeholder, placeScaleType, null);
        if (downLoad) {
            mRequestQueue.add(new DownloadRequest(url, view, listener, config, doAnim, null, localPath));
        }
    }

    private void setPlaceHolder(View view, int placeholder, ScaleType placeScaleType, CustomConfig customConfig) {
        if (view != null) {
            if (placeholder > 0) {
                if (customConfig != null && !customConfig.placeholderKeepIntact) {
                    String key = customConfig.getKey(placeholder + "");
                    SoftReference<Bitmap> srBitmap = (SoftReference) this.mPlaceHolderMap.get(key);
                    if (srBitmap != null) {
                        if (srBitmap.get() == null || ((Bitmap) srBitmap.get()).isRecycled()) {
                            this.mPlaceHolderMap.remove(key);
                        } else if (view instanceof ImageView) {
                            ((ImageView) view).setScaleType(ScaleType.FIT_XY);
                            ((ImageView) view).setImageBitmap((Bitmap) srBitmap.get());
                            return;
                        } else {
                            view.setBackgroundDrawable(new BitmapDrawable((Bitmap) srBitmap.get()));
                            return;
                        }
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), placeholder);
                    if (bitmap != null) {
                        bitmap = this.mBitmapMaker.createCustomBitmap(bitmap, customConfig);
                        this.mPlaceHolderMap.put(key, new SoftReference(bitmap));
                        if (view instanceof ImageView) {
                            ((ImageView) view).setScaleType(ScaleType.FIT_XY);
                            ((ImageView) view).setImageBitmap(bitmap);
                            return;
                        }
                        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                } else if (view instanceof ImageView) {
                    ((ImageView) view).setScaleType(placeScaleType);
                    ((ImageView) view).setImageResource(placeholder);
                } else {
                    view.setBackgroundResource(placeholder);
                }
            } else if (placeholder != -2) {
            } else {
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageBitmap(null);
                } else {
                    view.setBackgroundDrawable(null);
                }
            }
        }
    }

    static void callback(ImageDownloadStateListener listener, Bitmap bitmap, int status) {
        if (listener != null) {
            if (status == 0) {
                listener.loading();
            } else if (status == 1) {
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap = null;
                }
                listener.loadSuccess(bitmap);
            } else {
                listener.loadFailed();
            }
        }
    }

    static void callback(ImageDownloadStateListener listener, Bitmap bitmap, int status, String localPath) {
        if (listener != null) {
            if (status == 0) {
                listener.loading();
            } else if (status == 1) {
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap = null;
                }
                listener.loadSuccess(bitmap, localPath);
            } else {
                listener.loadFailed();
            }
        }
    }

    static void callback(View view, ImageDownloadStateListener listener, Bitmap bitmap, int status, String localPath) {
        if (listener != null) {
            if (status == 0) {
                listener.loading();
            } else if (status != 1) {
                listener.loadFailed();
            } else if (bitmap != null && bitmap.isRecycled()) {
                listener.loadSuccess(view, null, localPath);
            }
        }
    }

    static void setBitmapForView(View view, Bitmap bitmap, String url, boolean doAnim) {
        if (view != null && bitmap != null && !TextUtils.isEmpty(url) && !bitmap.isRecycled() && view.getTag(R.id.view_tag) != null && view.getTag(R.id.view_tag).toString().equals(url)) {
            view.setTag(R.id.view_result, Boolean.valueOf(true));
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (imageView.getTag(R.id.imageview_atts) == null || !imageView.getTag(R.id.imageview_atts).toString().contains("background")) {
                    if (imageView.getTag(R.id.scale_type) instanceof ScaleType) {
                        imageView.setScaleType((ScaleType) imageView.getTag(R.id.scale_type));
                    } else {
                        imageView.setScaleType(ScaleType.FIT_XY);
                    }
                    imageView.setImageBitmap(bitmap);
                } else {
                    view.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }
            } else {
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            if (doAnim) {
                view.startAnimation(AnimationUtils.loadAnimation(BaseApplication.getInstance(), R.anim.fade_in));
            }
        }
    }

    public boolean isBitmapExistInDisk(String url) {
        return (this.mCache == null || this.mCache.getBitmapFromDiscCache(url, null) == null) ? false : true;
    }

    public void cancel(String url) {
        if (mRequestQueue != null) {
            mRequestQueue.cancel(url);
        }
    }

    public static void deleteAllFile(Context context, CacheCleanListener listener) {
        if (StoreUtils.isSdcardAvailable()) {
            new Thread(new 1(context, listener)).start();
        } else {
            listener.onError();
        }
    }
}
