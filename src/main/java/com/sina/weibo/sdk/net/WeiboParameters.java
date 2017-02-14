package com.sina.weibo.sdk.net;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Set;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class WeiboParameters {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private String mAppKey;
    private LinkedHashMap<String, Object> mParams = new LinkedHashMap();

    public WeiboParameters(String appKey) {
        this.mAppKey = appKey;
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public LinkedHashMap<String, Object> getParams() {
        return this.mParams;
    }

    public void setParams(LinkedHashMap<String, Object> params) {
        this.mParams = params;
    }

    @Deprecated
    public void add(String key, String val) {
        this.mParams.put(key, val);
    }

    @Deprecated
    public void add(String key, int value) {
        this.mParams.put(key, String.valueOf(value));
    }

    @Deprecated
    public void add(String key, long value) {
        this.mParams.put(key, String.valueOf(value));
    }

    @Deprecated
    public void add(String key, Object val) {
        this.mParams.put(key, val.toString());
    }

    public void put(String key, String val) {
        this.mParams.put(key, val);
    }

    public void put(String key, int value) {
        this.mParams.put(key, String.valueOf(value));
    }

    public void put(String key, long value) {
        this.mParams.put(key, String.valueOf(value));
    }

    public void put(String key, Bitmap bitmap) {
        this.mParams.put(key, bitmap);
    }

    public void put(String key, Object val) {
        this.mParams.put(key, val.toString());
    }

    public Object get(String key) {
        return this.mParams.get(key);
    }

    public void remove(String key) {
        if (this.mParams.containsKey(key)) {
            this.mParams.remove(key);
            this.mParams.remove(this.mParams.get(key));
        }
    }

    public Set<String> keySet() {
        return this.mParams.keySet();
    }

    public boolean containsKey(String key) {
        return this.mParams.containsKey(key);
    }

    public boolean containsValue(String value) {
        return this.mParams.containsValue(value);
    }

    public int size() {
        return this.mParams.size();
    }

    public String encodeUrl() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : this.mParams.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String value = this.mParams.get(key);
            if (value instanceof String) {
                String param = value;
                if (!TextUtils.isEmpty(param)) {
                    try {
                        sb.append(URLEncoder.encode(key, "UTF-8") + SearchCriteria.EQ + URLEncoder.encode(param, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                LogUtil.i("encodeUrl", sb.toString());
            }
        }
        return sb.toString();
    }

    public boolean hasBinaryData() {
        for (String key : this.mParams.keySet()) {
            Object value = this.mParams.get(key);
            if (!(value instanceof ByteArrayOutputStream)) {
                if (value instanceof Bitmap) {
                }
            }
            return true;
        }
        return false;
    }
}
