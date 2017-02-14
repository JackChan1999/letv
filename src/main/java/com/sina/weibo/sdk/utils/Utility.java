package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.utils.AidTask.AidInfo;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.UUID;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class Utility {
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle parseUri(String uri) {
        try {
            return decodeUrl(new URI(uri).getQuery());
        } catch (Exception e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            for (String parameter : s.split("&")) {
                String[] v = parameter.split(SearchCriteria.EQ);
                try {
                    params.putString(URLDecoder.decode(v[0], "UTF-8"), URLDecoder.decode(v[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    public static boolean isChineseLocale(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            if (Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) || Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TAIWAN.equals(locale)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static String generateGUID() {
        return UUID.randomUUID().toString().replace(NetworkUtils.DELIMITER_LINE, "");
    }

    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, 64);
            for (Signature toByteArray : packageInfo.signatures) {
                byte[] str = toByteArray.toByteArray();
                if (str != null) {
                    return MD5.hexdigest(str);
                }
            }
            return null;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static String safeString(String orignal) {
        return TextUtils.isEmpty(orignal) ? "" : orignal;
    }

    public static String getAid(Context context, String appKey) {
        AidInfo aidInfo = AidTask.getInstance(context).getAidSync(appKey);
        if (aidInfo != null) {
            return aidInfo.getAid();
        }
        return "";
    }

    public static String generateUA(Context ctx) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Build.MANUFACTURER).append(NetworkUtils.DELIMITER_LINE).append(Build.MODEL);
        buffer.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        buffer.append(VERSION.RELEASE);
        buffer.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        buffer.append("weibosdk");
        buffer.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        buffer.append("0031105000");
        buffer.append("_android");
        return buffer.toString();
    }
}
