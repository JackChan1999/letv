package com.flurry.sdk;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.apache.http.client.utils.URIUtils;

public class jr {
    private static final Pattern a = Pattern.compile("/");

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI i = i(str);
        if (i == null) {
            return str;
        }
        String scheme = i.getScheme();
        if (TextUtils.isEmpty(scheme)) {
            return IDataSource.SCHEME_HTTP_TAG + str;
        }
        String toLowerCase = scheme.toLowerCase();
        if (scheme == null || scheme.equals(toLowerCase)) {
            return str;
        }
        int indexOf = str.indexOf(scheme);
        return indexOf >= 0 ? toLowerCase + str.substring(scheme.length() + indexOf) : str;
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI i = i(str);
        if (i == null) {
            return str;
        }
        i = i.normalize();
        if (i.isOpaque()) {
            return str;
        }
        i = a(i.getScheme(), i.getAuthority(), "/", null, null);
        if (i != null) {
            return i.toString();
        }
        return str;
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI i = i(str);
        if (i == null) {
            return str;
        }
        i = i.normalize();
        if (i.isOpaque()) {
            return str;
        }
        i = URIUtils.resolve(i, "./");
        if (i != null) {
            return i.toString();
        }
        return str;
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return str;
        }
        URI i = i(str);
        if (i == null) {
            return str;
        }
        URI normalize = i.normalize();
        i = i(str2);
        if (i == null) {
            return str;
        }
        URI normalize2 = i.normalize();
        if (normalize.isOpaque() || normalize2.isOpaque()) {
            return str;
        }
        String scheme = normalize.getScheme();
        String scheme2 = normalize2.getScheme();
        if (scheme2 == null && scheme != null) {
            return str;
        }
        if (scheme2 != null && !scheme2.equals(scheme)) {
            return str;
        }
        scheme = normalize.getAuthority();
        scheme2 = normalize2.getAuthority();
        if (scheme2 == null && scheme != null) {
            return str;
        }
        if (scheme2 != null && !scheme2.equals(scheme)) {
            return str;
        }
        CharSequence path = normalize.getPath();
        CharSequence path2 = normalize2.getPath();
        String[] split = a.split(path, -1);
        String[] split2 = a.split(path2, -1);
        int length = split.length;
        int length2 = split2.length;
        int i2 = 0;
        while (i2 < length2 && i2 < length && split[i2].equals(split2[i2])) {
            i2++;
        }
        scheme2 = normalize.getQuery();
        String fragment = normalize.getFragment();
        StringBuilder stringBuilder = new StringBuilder();
        if (i2 == length2 && i2 == length) {
            path = normalize2.getQuery();
            CharSequence fragment2 = normalize2.getFragment();
            boolean equals = TextUtils.equals(scheme2, path);
            boolean equals2 = TextUtils.equals(fragment, fragment2);
            if (equals && equals2) {
                scheme = null;
                scheme2 = null;
            } else {
                if (!equals || TextUtils.isEmpty(fragment)) {
                    Object obj = scheme2;
                } else {
                    path = null;
                }
                if (TextUtils.isEmpty(path) && TextUtils.isEmpty(fragment)) {
                    stringBuilder.append(split[length - 1]);
                    scheme = fragment;
                } else {
                    path2 = path;
                    scheme = fragment;
                }
            }
            fragment = scheme;
        } else {
            int i3 = length - 1;
            length2--;
            for (int i4 = i2; i4 < length2; i4++) {
                stringBuilder.append("..");
                stringBuilder.append("/");
            }
            while (i2 < i3) {
                stringBuilder.append(split[i2]);
                stringBuilder.append("/");
                i2++;
            }
            if (i2 < length) {
                stringBuilder.append(split[i2]);
            }
            if (stringBuilder.length() == 0) {
                stringBuilder.append(".");
                stringBuilder.append("/");
            }
        }
        i = a(null, null, stringBuilder.toString(), scheme2, fragment);
        if (i != null) {
            return i.toString();
        }
        return str;
    }

    private static URI i(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static URI a(String str, String str2, String str3, String str4, String str5) {
        try {
            return new URI(str, str2, str3, str4, str5);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static boolean d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getScheme() == null || !parse.getScheme().equals("market")) {
            return false;
        }
        return true;
    }

    public static boolean e(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getScheme() == null) {
            return false;
        }
        if (parse.getScheme().equals(IDataSource.SCHEME_HTTP_TAG) || parse.getScheme().equals(IDataSource.SCHEME_HTTPS_TAG)) {
            return true;
        }
        return false;
    }

    public static boolean f(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getHost() == null || !parse.getHost().equals("play.google.com") || parse.getScheme() == null || !parse.getScheme().startsWith(IDataSource.SCHEME_HTTP_TAG)) {
            return false;
        }
        return true;
    }

    public static boolean g(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str));
        if (mimeTypeFromExtension == null || !mimeTypeFromExtension.startsWith("video/")) {
            return false;
        }
        return true;
    }

    public static boolean h(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        URI i = i(str);
        if (i == null) {
            return false;
        }
        if (i.getScheme() == null || IDataSource.SCHEME_HTTP_TAG.equalsIgnoreCase(i.getScheme()) || IDataSource.SCHEME_HTTPS_TAG.equalsIgnoreCase(i.getScheme())) {
            return true;
        }
        return false;
    }

    public static String b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (!(new URI(str).isAbsolute() || TextUtils.isEmpty(str2))) {
                    URI uri = new URI(str2);
                    str = uri.getScheme() + "://" + uri.getHost() + str;
                }
            } catch (Exception e) {
            }
        }
        return str;
    }
}
