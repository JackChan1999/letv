package com.soundink.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.letv.core.constant.LiveRoomConstant;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class e {
    private static HttpClient b;
    private static SharedPreferences c;
    public boolean a = false;

    public boolean k() {
        return this.a;
    }

    static String a(String str) throws JSONException {
        return new JSONObject(str).optString("auth_token", "");
    }

    public void a(boolean z) {
        this.a = z;
    }

    protected static SoundInkAgent e() {
        return new SoundInkAgent("", 2000, j.a(2000), 0);
    }

    public static String a(String str, String str2) {
        if (str2 != null) {
            try {
                if (!str.equals("")) {
                    return new SimpleDateFormat(str2).format(Long.valueOf(Long.parseLong(String.valueOf(String.valueOf(str).substring(0, 10))) * 1000));
                }
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    protected static void a(Context context) {
        if (c == null) {
            c = context.getSharedPreferences("soundink", 0);
        }
    }

    protected static SoundInkAgent f() {
        return new SoundInkAgent("", 1000, j.a(1000), 0);
    }

    protected static SoundInkAgent g() {
        return new SoundInkAgent("", LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, j.a(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID), 0);
    }

    protected static String b() {
        return c.getString("apply_code", "");
    }

    protected static SoundInkAgent h() {
        return new SoundInkAgent("", 1002, j.a(1002), 0);
    }

    static synchronized HttpClient a() {
        HttpClient defaultHttpClient;
        synchronized (e.class) {
            if (b == null) {
                try {
                    KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
                    instance.load(null, null);
                    f fVar = new f(instance);
                    fVar.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    HttpParams basicHttpParams = new BasicHttpParams();
                    HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setContentCharset(basicHttpParams, "ISO-8859-1");
                    HttpProtocolParams.setUseExpectContinue(basicHttpParams, true);
                    ConnManagerParams.setTimeout(basicHttpParams, 10000);
                    HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
                    HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
                    SchemeRegistry schemeRegistry = new SchemeRegistry();
                    schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTP_TAG, PlainSocketFactory.getSocketFactory(), 80));
                    schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, fVar, 443));
                    b = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                } catch (Exception e) {
                    e.printStackTrace();
                    defaultHttpClient = new DefaultHttpClient();
                }
            }
            defaultHttpClient = b;
        }
        return defaultHttpClient;
    }

    protected static SoundInkAgent i() {
        return new SoundInkAgent("", 1001, j.a(1001), 0);
    }

    protected static void a(String str, long j) {
        a(str, String.valueOf(j));
    }

    protected static String e(String str) {
        try {
            byte[] digest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE).digest(str.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder(digest.length << 1);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (Throwable e2) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e2);
        }
    }

    protected static long b(String str) {
        if (c.getString(str, "").equals("")) {
            return 0;
        }
        return Long.parseLong(c.getString(str, ""));
    }

    public static boolean b(Context context) {
        if (context != null) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    protected static void c(String str) {
        Object obj = null;
        String[] split = b().split(",");
        for (String equalsIgnoreCase : split) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                obj = 1;
                break;
            }
        }
        if (obj == null) {
            a("apply_code", b() + str + ",");
        }
    }

    protected static String[] c() {
        return b().split(",");
    }

    protected static void d(String str) {
        a("apply_code", b().replace(new StringBuilder(String.valueOf(str)).append(",").toString(), ""));
    }

    protected static void d() {
        if (c != null) {
            c.edit().clear();
        }
    }

    protected static long j() {
        String substring = new StringBuilder(String.valueOf(System.currentTimeMillis())).toString().substring(0, 10);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return Long.parseLong(substring);
    }

    private static void a(String str, Object obj) {
        Editor edit = c.edit();
        if (obj.getClass() == Boolean.class) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        if (obj.getClass() == String.class) {
            edit.putString(str, (String) obj);
        }
        if (obj.getClass() == Integer.class) {
            edit.putInt(str, ((Integer) obj).intValue());
        }
        if (obj.getClass() == Float.class) {
            edit.putFloat(str, (float) ((Float) obj).intValue());
        }
        if (obj.getClass() == Long.class) {
            edit.putLong(str, (long) ((Long) obj).intValue());
        }
        edit.commit();
    }
}
