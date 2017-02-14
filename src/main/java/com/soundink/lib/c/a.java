package com.soundink.lib.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.letv.pp.utils.NetworkUtils;
import java.util.UUID;

public class a {
    private static UUID a;

    public a(Context context) {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("device_id.xml", 0);
                    String string = sharedPreferences.getString("device_id", null);
                    if (string != null) {
                        a = UUID.fromString(string);
                    } else {
                        UUID nameUUIDFromBytes;
                        string = Secure.getString(context.getContentResolver(), "android_id");
                        if (string != null) {
                            try {
                                if (!"9774d56d682e549c".equals(string)) {
                                    a = UUID.nameUUIDFromBytes(string.getBytes("utf8"));
                                    sharedPreferences.edit().putString("device_id", a.toString()).commit();
                                }
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        }
                        string = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                        if (string != null) {
                            nameUUIDFromBytes = UUID.nameUUIDFromBytes(string.getBytes("utf8"));
                        } else {
                            nameUUIDFromBytes = UUID.randomUUID();
                        }
                        a = nameUUIDFromBytes;
                        sharedPreferences.edit().putString("device_id", a.toString()).commit();
                    }
                }
            }
        }
    }

    public static UUID a() {
        return a;
    }

    public static String b() {
        return a.toString().replace(NetworkUtils.DELIMITER_LINE, "");
    }
}
