package cn.jpush.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import cn.jpush.android.a;
import cn.jpush.android.api.c;
import cn.jpush.android.e;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class l {
    private static final String a = l.class.getSimpleName();
    private static Map<String, String> b = null;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 17;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "tq}ETbgesMi";
        r0 = -1;
        r4 = r3;
    L_0x000a:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002f;
    L_0x0013:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0018:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x00e8;
            case 1: goto L_0x00eb;
            case 2: goto L_0x00ef;
            case 3: goto L_0x00f3;
            default: goto L_0x001f;
        };
    L_0x001f:
        r9 = 34;
    L_0x0021:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002d;
    L_0x0029:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0018;
    L_0x002d:
        r5 = r1;
        r1 = r7;
    L_0x002f:
        if (r5 > r6) goto L_0x0013;
    L_0x0031:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0045;
            case 1: goto L_0x004e;
            case 2: goto L_0x0056;
            case 3: goto L_0x005e;
            case 4: goto L_0x0067;
            case 5: goto L_0x006f;
            case 6: goto L_0x0077;
            case 7: goto L_0x0080;
            case 8: goto L_0x008a;
            case 9: goto L_0x0095;
            case 10: goto L_0x00a0;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c1;
            case 14: goto L_0x00cd;
            case 15: goto L_0x00d8;
            default: goto L_0x003d;
        };
    L_0x003d:
        r3[r2] = r1;
        r2 = 1;
        r1 = "meciJXqslKdpIsLaz";
        r0 = 0;
        r3 = r4;
        goto L_0x000a;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "upeuNrauL";
        r0 = 1;
        r3 = r4;
        goto L_0x000a;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "fefETbgesMivy~G";
        r0 = 2;
        r3 = r4;
        goto L_0x000a;
    L_0x0056:
        r3[r2] = r1;
        r2 = 4;
        r1 = "ktx}Wfrs";
        r0 = 3;
        r3 = r4;
        goto L_0x000a;
    L_0x005e:
        r3[r2] = r1;
        r2 = 5;
        r1 = "s|{Xh{s";
        r0 = 4;
        r3 = r4;
        goto L_0x000a;
    L_0x0067:
        r3[r2] = r1;
        r2 = 6;
        r1 = "fefEIbl";
        r0 = 5;
        r3 = r4;
        goto L_0x000a;
    L_0x006f:
        r3[r2] = r1;
        r2 = 7;
        r1 = "fefETbgesMi{wwG";
        r0 = 6;
        r3 = r4;
        goto L_0x000a;
    L_0x0077:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\\Kw7XF8L*\u000f>J8G";
        r0 = 7;
        r3 = r4;
        goto L_0x000a;
    L_0x0080:
        r3[r2] = r1;
        r2 = 9;
        r1 = "5;'4\u0011";
        r0 = 8;
        r3 = r4;
        goto L_0x000a;
    L_0x008a:
        r3[r2] = r1;
        r2 = 10;
        r1 = "hfIlGufuL";
        r0 = 9;
        r3 = r4;
        goto L_0x000a;
    L_0x0095:
        r3[r2] = r1;
        r2 = 11;
        r1 = "decEKisy";
        r0 = 10;
        r3 = r4;
        goto L_0x000a;
    L_0x00a0:
        r3[r2] = r1;
        r2 = 12;
        r1 = "d}wtLby";
        r0 = 11;
        r3 = r4;
        goto L_0x000a;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "jzrN";
        r0 = 12;
        r3 = r4;
        goto L_0x000a;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "nawG";
        r0 = 13;
        r3 = r4;
        goto L_0x000a;
    L_0x00c1:
        r3[r2] = r1;
        r2 = 15;
        r1 = "slf";
        r0 = 14;
        r3 = r4;
        goto L_0x000a;
    L_0x00cd:
        r3[r2] = r1;
        r2 = 16;
        r1 = "cp`sAbJtDh";
        r0 = 15;
        r3 = r4;
        goto L_0x000a;
    L_0x00d8:
        r3[r2] = r1;
        z = r4;
        r0 = cn.jpush.android.util.l.class;
        r0 = r0.getSimpleName();
        a = r0;
        r0 = 0;
        b = r0;
        return;
    L_0x00e8:
        r9 = 7;
        goto L_0x0021;
    L_0x00eb:
        r9 = 21;
        goto L_0x0021;
    L_0x00ef:
        r9 = 22;
        goto L_0x0021;
    L_0x00f3:
        r9 = 26;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.l.<clinit>():void");
    }

    public static String a(Context context) {
        return context.getSharedPreferences(z[1], 0).getString(z[0], null);
    }

    public static void a(Context context, String str) {
        Editor edit = context.getSharedPreferences(z[1], 0).edit();
        edit.putString(z[0], str);
        edit.commit();
    }

    private static void a(Context context, Map<String, String> map) {
        if (map != null) {
            Set<String> keySet = map.keySet();
            if (keySet != null && keySet.size() > 0) {
                Editor edit = context.getSharedPreferences(z[1], 0).edit();
                for (String str : keySet) {
                    edit.putString(str, (String) map.get(str));
                }
                edit.commit();
            }
        }
    }

    public static void b(Context context) {
        z.b();
        if (a.u()) {
            Map c = c(context);
            if (c != null && !c.isEmpty()) {
                Map hashMap;
                JSONObject jSONObject;
                if (b == null) {
                    hashMap = new HashMap();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(z[1], 0);
                    String string = sharedPreferences.getString(z[11], null);
                    String string2 = sharedPreferences.getString(z[2], null);
                    String string3 = sharedPreferences.getString(z[12], null);
                    String string4 = sharedPreferences.getString(z[6], null);
                    String string5 = sharedPreferences.getString(z[3], null);
                    String string6 = sharedPreferences.getString(z[7], null);
                    String string7 = sharedPreferences.getString(z[4], null);
                    String string8 = sharedPreferences.getString(z[5], null);
                    String string9 = sharedPreferences.getString(z[0], null);
                    String string10 = sharedPreferences.getString(z[13], null);
                    if (!ai.a(string)) {
                        hashMap.put(z[11], string);
                    }
                    if (!ai.a(string2)) {
                        hashMap.put(z[2], string2);
                    }
                    if (!ai.a(string3)) {
                        hashMap.put(z[12], string3);
                    }
                    if (!ai.a(string4)) {
                        hashMap.put(z[6], string4);
                    }
                    if (!ai.a(string5)) {
                        hashMap.put(z[3], string5);
                    }
                    if (!ai.a(string6)) {
                        hashMap.put(z[7], string6);
                    }
                    if (!ai.a(string7)) {
                        hashMap.put(z[10], string7);
                    }
                    if (!ai.a(string7)) {
                        hashMap.put(z[4], string7);
                    }
                    if (!ai.a(string8)) {
                        hashMap.put(z[5], string8);
                    }
                    if (!ai.a(string9)) {
                        hashMap.put(z[0], string9);
                    }
                    if (!ai.a(string10)) {
                        hashMap.put(z[13], string10);
                    }
                    b = hashMap;
                }
                hashMap = b;
                Object obj = (hashMap == null || hashMap.isEmpty()) ? 1 : !c.equals(hashMap) ? 1 : null;
                if (obj != null) {
                    b = c;
                    a(context, c);
                    try {
                        jSONObject = new JSONObject(c);
                        jSONObject.put(z[14], a.j());
                        jSONObject.put(z[15], z[16]);
                        ac.a(context, jSONObject);
                    } catch (JSONException e) {
                        e.getMessage();
                        z.e();
                        return;
                    }
                }
                jSONObject = c.a().c(context);
                if (jSONObject != null) {
                    JSONObject jSONObject2 = new JSONObject(c);
                    if (jSONObject2 != null && jSONObject2.length() > 0) {
                        try {
                            jSONObject.put(z[16], jSONObject2);
                        } catch (JSONException e2) {
                        }
                    }
                    ac.a(context, jSONObject);
                    c.b(context);
                }
            }
        }
    }

    private static Map<String, String> c(Context context) {
        String str;
        String locale;
        Exception exception;
        Exception exception2;
        context.getPackageManager();
        context.getPackageName();
        Map<String, String> hashMap = new HashMap();
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        try {
            str2 = a.c();
            str3 = a.a(context);
            str4 = a.F();
            str5 = a.A();
            str6 = e.i;
            str7 = e.j.replaceAll(z[8], EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            str8 = VERSION.RELEASE;
            str10 = z[9];
            str = Build.MODEL;
            try {
                locale = context.getResources().getConfiguration().locale.toString();
            } catch (Exception e) {
                exception = e;
                locale = null;
                exception2 = exception;
                exception2.getMessage();
                z.e();
                if (!ai.a(str2)) {
                    hashMap.put(z[11], str2);
                }
                if (!ai.a(str3)) {
                    hashMap.put(z[2], str3);
                }
                if (!ai.a(str4)) {
                    hashMap.put(z[12], str4);
                }
                if (!ai.a(str5)) {
                    hashMap.put(z[6], str5);
                }
                if (!ai.a(str6)) {
                    hashMap.put(z[3], str6);
                }
                if (!ai.a(str7)) {
                    hashMap.put(z[7], str7);
                }
                if (!ai.a(str8)) {
                    hashMap.put(z[10], str8);
                }
                if (!ai.a(locale)) {
                    hashMap.put(z[4], locale);
                }
                if (!ai.a(str9)) {
                    hashMap.put(z[5], str9);
                }
                if (!ai.a(str10)) {
                    hashMap.put(z[0], str10);
                }
                if (!ai.a(str)) {
                    hashMap.put(z[13], str);
                }
                return hashMap;
            }
            try {
                long rawOffset = ((long) TimeZone.getDefault().getRawOffset()) / com.umeng.analytics.a.h;
                str9 = rawOffset > 0 ? "+" + rawOffset : rawOffset < 0 ? new StringBuilder(NetworkUtils.DELIMITER_LINE).append(rawOffset).toString() : rawOffset;
            } catch (Exception e2) {
                exception2 = e2;
                exception2.getMessage();
                z.e();
                if (ai.a(str2)) {
                    hashMap.put(z[11], str2);
                }
                if (ai.a(str3)) {
                    hashMap.put(z[2], str3);
                }
                if (ai.a(str4)) {
                    hashMap.put(z[12], str4);
                }
                if (ai.a(str5)) {
                    hashMap.put(z[6], str5);
                }
                if (ai.a(str6)) {
                    hashMap.put(z[3], str6);
                }
                if (ai.a(str7)) {
                    hashMap.put(z[7], str7);
                }
                if (ai.a(str8)) {
                    hashMap.put(z[10], str8);
                }
                if (ai.a(locale)) {
                    hashMap.put(z[4], locale);
                }
                if (ai.a(str9)) {
                    hashMap.put(z[5], str9);
                }
                if (ai.a(str10)) {
                    hashMap.put(z[0], str10);
                }
                if (ai.a(str)) {
                    hashMap.put(z[13], str);
                }
                return hashMap;
            }
        } catch (Exception e3) {
            exception = e3;
            str = null;
            locale = null;
            exception2 = exception;
            exception2.getMessage();
            z.e();
            if (ai.a(str2)) {
                hashMap.put(z[11], str2);
            }
            if (ai.a(str3)) {
                hashMap.put(z[2], str3);
            }
            if (ai.a(str4)) {
                hashMap.put(z[12], str4);
            }
            if (ai.a(str5)) {
                hashMap.put(z[6], str5);
            }
            if (ai.a(str6)) {
                hashMap.put(z[3], str6);
            }
            if (ai.a(str7)) {
                hashMap.put(z[7], str7);
            }
            if (ai.a(str8)) {
                hashMap.put(z[10], str8);
            }
            if (ai.a(locale)) {
                hashMap.put(z[4], locale);
            }
            if (ai.a(str9)) {
                hashMap.put(z[5], str9);
            }
            if (ai.a(str10)) {
                hashMap.put(z[0], str10);
            }
            if (ai.a(str)) {
                hashMap.put(z[13], str);
            }
            return hashMap;
        }
        if (ai.a(str2)) {
            hashMap.put(z[11], str2);
        }
        if (ai.a(str3)) {
            hashMap.put(z[2], str3);
        }
        if (ai.a(str4)) {
            hashMap.put(z[12], str4);
        }
        if (ai.a(str5)) {
            hashMap.put(z[6], str5);
        }
        if (ai.a(str6)) {
            hashMap.put(z[3], str6);
        }
        if (ai.a(str7)) {
            hashMap.put(z[7], str7);
        }
        if (ai.a(str8)) {
            hashMap.put(z[10], str8);
        }
        if (ai.a(locale)) {
            hashMap.put(z[4], locale);
        }
        if (ai.a(str9)) {
            hashMap.put(z[5], str9);
        }
        if (ai.a(str10)) {
            hashMap.put(z[0], str10);
        }
        if (ai.a(str)) {
            hashMap.put(z[13], str);
        }
        return hashMap;
    }
}
