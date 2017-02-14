package com.nostra13.universalimageloader.cache.disc.impl.ext;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache.Editor;
import com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache.Snapshot;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import com.nostra13.universalimageloader.utils.L;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LruDiscCache implements DiskCache {
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.PNG;
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    private static final String ERROR_ARG_NEGATIVE = " argument must be positive number";
    private static final String ERROR_ARG_NULL = " argument must be not null";
    protected int bufferSize;
    protected DiskLruCache cache;
    protected CompressFormat compressFormat;
    protected int compressQuality;
    protected final FileNameGenerator fileNameGenerator;
    private File reserveCacheDir;

    public LruDiscCache(File cacheDir, FileNameGenerator fileNameGenerator, long cacheMaxSize) throws IOException {
        this(cacheDir, null, fileNameGenerator, cacheMaxSize, 0);
    }

    public LruDiscCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator, long cacheMaxSize, int cacheMaxFileCount) throws IOException {
        this.bufferSize = 32768;
        this.compressFormat = DEFAULT_COMPRESS_FORMAT;
        this.compressQuality = 100;
        if (cacheDir == null) {
            throw new IllegalArgumentException("cacheDir argument must be not null");
        } else if (cacheMaxSize < 0) {
            throw new IllegalArgumentException("cacheMaxSize argument must be positive number");
        } else if (cacheMaxFileCount < 0) {
            throw new IllegalArgumentException("cacheMaxFileCount argument must be positive number");
        } else if (fileNameGenerator == null) {
            throw new IllegalArgumentException("fileNameGenerator argument must be not null");
        } else {
            if (cacheMaxSize == 0) {
                cacheMaxSize = Long.MAX_VALUE;
            }
            if (cacheMaxFileCount == 0) {
                cacheMaxFileCount = Integer.MAX_VALUE;
            }
            this.reserveCacheDir = reserveCacheDir;
            this.fileNameGenerator = fileNameGenerator;
            initCache(cacheDir, reserveCacheDir, cacheMaxSize, cacheMaxFileCount);
        }
    }

    private void initCache(File cacheDir, File reserveCacheDir, long cacheMaxSize, int cacheMaxFileCount) throws IOException {
        try {
            this.cache = DiskLruCache.open(cacheDir, 1, 1, cacheMaxSize, cacheMaxFileCount);
        } catch (IOException e) {
            L.e(e);
            if (reserveCacheDir != null) {
                initCache(reserveCacheDir, null, cacheMaxSize, cacheMaxFileCount);
            }
            if (this.cache == null) {
                throw e;
            }
        }
    }

    public File getDirectory() {
        return this.cache.getDirectory();
    }

    public File get(String imageUri) {
        File file = null;
        Snapshot snapshot = null;
        try {
            snapshot = this.cache.get(getKey(imageUri));
            if (snapshot != null) {
                file = snapshot.getFile(0);
            }
            if (snapshot != null) {
                snapshot.close();
            }
        } catch (IOException e) {
            L.e(e);
            if (snapshot != null) {
                snapshot.close();
            }
        } catch (Throwable th) {
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return file;
    }

    public boolean save(String imageUri, InputStream imageStream, CopyListener listener) throws IOException {
        boolean z = false;
        Editor editor = this.cache.edit(getKey(imageUri));
        if (editor != null) {
            OutputStream os = new BufferedOutputStream(editor.newOutputStream(0), this.bufferSize);
            z = false;
            try {
                z = IoUtils.copyStream(imageStream, os, listener, this.bufferSize);
            } finally {
                IoUtils.closeSilently(os);
                if (z) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
        }
        return z;
    }

    public boolean save(String imageUri, Bitmap bitmap) throws IOException {
        boolean z = false;
        Editor editor = this.cache.edit(getKey(imageUri));
        if (editor != null) {
            OutputStream os = new BufferedOutputStream(editor.newOutputStream(0), this.bufferSize);
            z = false;
            try {
                z = bitmap.compress(this.compressFormat, this.compressQuality, os);
                if (z) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            } finally {
                IoUtils.closeSilently(os);
            }
        }
        return z;
    }

    public boolean remove(String imageUri) {
        try {
            return this.cache.remove(getKey(imageUri));
        } catch (IOException e) {
            L.e(e);
            return false;
        }
    }

    public void close() {
        try {
            this.cache.close();
        } catch (IOException e) {
            L.e(e);
        }
        this.cache = null;
    }

    public void clear() {
        try {
            this.cache.delete();
        } catch (IOException e) {
            L.e(e);
        }
        try {
            initCache(this.cache.getDirectory(), this.reserveCacheDir, this.cache.getMaxSize(), this.cache.getMaxFileCount());
        } catch (IOException e2) {
            L.e(e2);
        }
    }

    private String getKey(String imageUri) {
        return this.fileNameGenerator.generate(imageUri);
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setCompressFormat(CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
    }

    public void setCompressQuality(int compressQuality) {
        this.compressQuality = compressQuality;
    }
}
