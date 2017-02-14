package com.letv.lemallsdk.api.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class PersistentCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private final SharedPreferences cookiePrefs;
    private final ConcurrentHashMap<String, Cookie> cookies = new ConcurrentHashMap();

    public PersistentCookieStore(Context context) {
        int i = 0;
        this.cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        String storedCookieNames = this.cookiePrefs.getString(COOKIE_NAME_STORE, null);
        if (storedCookieNames != null) {
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            int length = cookieNames.length;
            while (i < length) {
                String name = cookieNames[i];
                String encodedCookie = this.cookiePrefs.getString(new StringBuilder(COOKIE_NAME_PREFIX).append(name).toString(), null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        this.cookies.put(name, decodedCookie);
                    }
                }
                i++;
            }
            clearExpired(new Date());
        }
    }

    public void addCookie(Cookie cookie) {
        String name = cookie.getName() + cookie.getDomain();
        if (cookie.isExpired(new Date())) {
            this.cookies.remove(name);
        } else {
            this.cookies.put(name, cookie);
        }
        Editor prefsWriter = this.cookiePrefs.edit();
        prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", this.cookies.keySet()));
        prefsWriter.putString(new StringBuilder(COOKIE_NAME_PREFIX).append(name).toString(), encodeCookie(new SerializableCookie(cookie)));
        prefsWriter.commit();
    }

    public void clear() {
        this.cookies.clear();
        Editor prefsWriter = this.cookiePrefs.edit();
        for (String name : this.cookies.keySet()) {
            prefsWriter.remove(new StringBuilder(COOKIE_NAME_PREFIX).append(name).toString());
        }
        prefsWriter.remove(COOKIE_NAME_STORE);
        prefsWriter.commit();
    }

    public boolean clearExpired(Date date) {
        boolean clearedAny = false;
        Editor prefsWriter = this.cookiePrefs.edit();
        for (Entry<String, Cookie> entry : this.cookies.entrySet()) {
            String name = (String) entry.getKey();
            if (((Cookie) entry.getValue()).isExpired(date)) {
                this.cookies.remove(name);
                prefsWriter.remove(new StringBuilder(COOKIE_NAME_PREFIX).append(name).toString());
                clearedAny = true;
            }
        }
        if (clearedAny) {
            prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", this.cookies.keySet()));
        }
        prefsWriter.commit();
        return clearedAny;
    }

    public List<Cookie> getCookies() {
        return new ArrayList(this.cookies.values());
    }

    protected String encodeCookie(SerializableCookie cookie) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(os).writeObject(cookie);
            return byteArrayToHexString(os.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    protected Cookie decodeCookie(String cookieStr) {
        Cookie cookie = null;
        try {
            cookie = ((SerializableCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(cookieStr))).readObject()).getCookie();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }

    protected String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (byte element : b) {
            int v = element & 255;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    protected byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
