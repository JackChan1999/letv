package com.letv.core.network.volley.toolbox;

import android.content.Context;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.download.image.DiskLruCache;
import com.letv.core.download.image.DiskLruCache.Editor;
import com.letv.core.download.image.DiskLruCache.Snapshot;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.listener.VolleyCache;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.MD5;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class VolleyDiskCache implements VolleyCache<String> {
    private final String mCacheKey;
    private Context mContext;
    private DiskLruCache mDiskLruCache;

    public VolleyDiskCache() {
        this(null);
    }

    public VolleyDiskCache(String cacheKey) {
        this(cacheKey, false);
    }

    public VolleyDiskCache(String cacheKey, boolean keep) {
        this.mContext = BaseApplication.getInstance();
        this.mCacheKey = cacheKey;
        if (!keep) {
            this.mDiskLruCache = Volley.getInstance().getDiskLurCache();
        }
    }

    private String checkKey(VolleyRequest<?> request) {
        String key = this.mCacheKey;
        if (TextUtils.isEmpty(this.mCacheKey)) {
            if (!TextUtils.isEmpty(request.getUrl())) {
                key = MD5.MD5Encode(request.getUrl());
            }
        } else if (this.mCacheKey.contains("/")) {
            key = MD5.MD5Encode(this.mCacheKey);
        }
        return appendCacheKeyWithLangParam(key);
    }

    private String appendCacheKeyWithLangParam(String key) {
        String country = LetvUtils.getCountry();
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(country) || country.equals(LetvUtils.COUNTRY_CHINA)) {
            return key;
        }
        String postFix;
        if (country.equals(LetvUtils.COUNTRY_TAIWAN)) {
            postFix = "cht";
        } else if (country.equals(LetvUtils.COUNTRY_HONGKONG)) {
            postFix = "cht";
        } else {
            postFix = "en";
        }
        return key + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + postFix;
    }

    public synchronized String get(VolleyRequest<?> request) {
        String apiFileCache;
        IOException e;
        Throwable th;
        String key = checkKey(request);
        if (TextUtils.isEmpty(key) || this.mDiskLruCache == null) {
            apiFileCache = FileUtils.getApiFileCache(this.mContext, key);
        } else {
            Snapshot snapshot = null;
            try {
                snapshot = this.mDiskLruCache.get(key);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (snapshot == null) {
                apiFileCache = FileUtils.getApiFileCache(this.mContext, key);
            } else {
                FileInputStream fis = null;
                ByteArrayOutputStream byteArrayOutputStream = null;
                try {
                    fis = (FileInputStream) snapshot.getInputStream(0);
                    if (fis == null) {
                        apiFileCache = FileUtils.getApiFileCache(this.mContext, key);
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } else {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        try {
                            byte[] b = new byte[1024];
                            while (true) {
                                int n = fis.read(b);
                                if (n == -1) {
                                    break;
                                }
                                bos.write(b, 0, n);
                            }
                            byte[] data = bos.toByteArray();
                            if (data == null) {
                                if (bos != null) {
                                    try {
                                        bos.close();
                                    } catch (Exception e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                if (fis != null) {
                                    try {
                                        fis.close();
                                    } catch (IOException e32) {
                                        e32.printStackTrace();
                                    }
                                }
                                apiFileCache = null;
                            } else {
                                apiFileCache = new String(data);
                                if (bos != null) {
                                    try {
                                        bos.close();
                                    } catch (Exception e222) {
                                        e222.printStackTrace();
                                    }
                                }
                                if (fis != null) {
                                    try {
                                        fis.close();
                                    } catch (IOException e322) {
                                        e322.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException e4) {
                            e322 = e4;
                            byteArrayOutputStream = bos;
                            try {
                                e322.printStackTrace();
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (Exception e2222) {
                                        e2222.printStackTrace();
                                    }
                                }
                                if (fis != null) {
                                    try {
                                        fis.close();
                                    } catch (IOException e3222) {
                                        e3222.printStackTrace();
                                    }
                                }
                                apiFileCache = null;
                                return apiFileCache;
                            } catch (Throwable th2) {
                                th = th2;
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (Exception e22222) {
                                        e22222.printStackTrace();
                                    }
                                }
                                if (fis != null) {
                                    try {
                                        fis.close();
                                    } catch (IOException e32222) {
                                        e32222.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            byteArrayOutputStream = bos;
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            if (fis != null) {
                                fis.close();
                            }
                            throw th;
                        }
                    }
                } catch (IOException e5) {
                    e32222 = e5;
                    e32222.printStackTrace();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                    apiFileCache = null;
                    return apiFileCache;
                }
            }
        }
        return apiFileCache;
    }

    public synchronized void add(VolleyRequest<?> request, String data) {
        String key = checkKey(request);
        if (!(TextUtils.isEmpty(key) || TextUtils.isEmpty(data))) {
            try {
                if (this.mDiskLruCache == null) {
                    FileUtils.saveApiFileCache(this.mContext, key, data);
                } else {
                    Editor editor = this.mDiskLruCache.edit(key);
                    if (editor != null) {
                        editor.set(0, data);
                        editor.commit();
                    } else {
                        FileUtils.saveApiFileCache(this.mContext, key, data);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void remove(VolleyRequest<?> request) {
        String key = checkKey(request);
        if (!(TextUtils.isEmpty(key) || this.mDiskLruCache == null)) {
            try {
                if (!this.mDiskLruCache.remove(key)) {
                    FileUtils.deleteApiFileCache(this.mContext, key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void clear() {
        if (this.mDiskLruCache != null) {
            try {
                this.mDiskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
