package io.fabric.sdk.android.services.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TreeMap;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public final class UrlUtils {
    public static final String UTF8 = "UTF8";

    private UrlUtils() {
    }

    public static TreeMap<String, String> getQueryParams(URI uri, boolean decode) {
        return getQueryParams(uri.getRawQuery(), decode);
    }

    public static TreeMap<String, String> getQueryParams(String paramsString, boolean decode) {
        TreeMap<String, String> params = new TreeMap();
        if (paramsString != null) {
            for (String nameValuePairString : paramsString.split("&")) {
                String[] nameValuePair = nameValuePairString.split(SearchCriteria.EQ);
                if (nameValuePair.length == 2) {
                    if (decode) {
                        params.put(urlDecode(nameValuePair[0]), urlDecode(nameValuePair[1]));
                    } else {
                        params.put(nameValuePair[0], nameValuePair[1]);
                    }
                } else if (!TextUtils.isEmpty(nameValuePair[0])) {
                    if (decode) {
                        params.put(urlDecode(nameValuePair[0]), "");
                    } else {
                        params.put(nameValuePair[0], "");
                    }
                }
            }
        }
        return params;
    }

    public static String urlEncode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, UTF8);
        } catch (UnsupportedEncodingException unlikely) {
            throw new RuntimeException(unlikely.getMessage(), unlikely);
        }
    }

    public static String urlDecode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLDecoder.decode(s, UTF8);
        } catch (UnsupportedEncodingException unlikely) {
            throw new RuntimeException(unlikely.getMessage(), unlikely);
        }
    }

    public static String percentEncode(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String encoded = urlEncode(s);
        int encodedLength = encoded.length();
        int i = 0;
        while (i < encodedLength) {
            char c = encoded.charAt(i);
            if (c == '*') {
                sb.append("%2A");
            } else if (c == '+') {
                sb.append("%20");
            } else if (c == '%' && i + 2 < encodedLength && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                sb.append('~');
                i += 2;
            } else {
                sb.append(c);
            }
            i++;
        }
        return sb.toString();
    }
}
