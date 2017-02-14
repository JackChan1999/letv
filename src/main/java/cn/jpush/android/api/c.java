package cn.jpush.android.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import cn.jpush.android.util.a;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.regex.PatternSyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class c implements UncaughtExceptionHandler {
    private static c b = new c();
    private static final String[] z;
    public boolean a = false;
    private UncaughtExceptionHandler c = null;
    private Context d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 16;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "7l\u0010\u000b,.g\u0001\u0017!$";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x00d5;
            case 1: goto L_0x00d9;
            case 2: goto L_0x00dd;
            case 3: goto L_0x00e1;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 69;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            case 7: goto L_0x007d;
            case 8: goto L_0x0087;
            case 9: goto L_0x0092;
            case 10: goto L_0x009d;
            case 11: goto L_0x00a8;
            case 12: goto L_0x00b3;
            case 13: goto L_0x00be;
            case 14: goto L_0x00c9;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0004q\u0001\u001d55`\r\u0016";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "/l\u0016\u000f*3b\u0016\u00015$";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\"f\u0017\u00161";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0004{\u0010\u00177";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "7l\u0010\u000b,.g\f\u0019($";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "2}\u0003\u001b.5{\u0003\u001b ";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = ",l\u0011\u000b$&l";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "/|\u000e\u0014";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\"{\u0003\u000b-5`\u000f\u001d";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "+y\u0017\u000b-\u001e|\f\u001b$4n\n\f 9j\u0007\b1(f\f'#(e\u0007";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "5p\u0012\u001d";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\"{\u0003\u000b-\u001ee\r\u001f";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "(}\u000b\u0015 ";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\"{\u0003\u000b--f\u0005\u000b";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "/l\u0016\u000f*3b=\f<1l";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        z = r4;
        r0 = new cn.jpush.android.api.c;
        r0.<init>();
        b = r0;
        return;
    L_0x00d5:
        r9 = 65;
        goto L_0x0020;
    L_0x00d9:
        r9 = 9;
        goto L_0x0020;
    L_0x00dd:
        r9 = 98;
        goto L_0x0020;
    L_0x00e1:
        r9 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.c.<clinit>():void");
    }

    private c() {
    }

    public static c a() {
        return b;
    }

    private JSONArray a(Context context, Throwable th, String str) {
        return a(context, d(context), th, str);
    }

    private JSONArray a(Context context, JSONArray jSONArray, Throwable th, String str) {
        int length;
        Object obj;
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String stringWriter2 = stringWriter.toString();
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        Object obj2 = str + th.toString();
        try {
            String[] split = obj2.split(NetworkUtils.DELIMITER_COLON);
            if (split.length > 1) {
                length = split.length - 1;
                while (length >= 0) {
                    if (split[length].endsWith(z[1]) || split[length].endsWith(z[4])) {
                        obj2 = split[length];
                        break;
                    }
                    length--;
                }
            }
        } catch (NullPointerException e) {
        } catch (PatternSyntaxException e2) {
        }
        Object obj3 = null;
        length = 0;
        while (length < jSONArray.length()) {
            try {
                JSONObject optJSONObject = jSONArray.optJSONObject(length);
                if (optJSONObject != null && stringWriter2.equals(optJSONObject.getString(z[6]))) {
                    optJSONObject.put(z[3], optJSONObject.getInt(z[3]) + 1);
                    optJSONObject.put(z[9], System.currentTimeMillis());
                    obj = optJSONObject;
                    break;
                }
                obj3 = null;
                length++;
            } catch (JSONException e3) {
                return jSONArray;
            } catch (NameNotFoundException e4) {
                return jSONArray;
            }
        }
        length = 0;
        obj = obj3;
        if (obj != null) {
            jSONArray = a(jSONArray, length);
            jSONArray.put(obj);
            return jSONArray;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(z[9], System.currentTimeMillis());
        jSONObject.put(z[6], stringWriter2);
        jSONObject.put(z[7], obj2);
        jSONObject.put(z[3], 1);
        if (!(this.d == null && context == null)) {
            jSONObject.put(z[2], a.c(context));
        }
        if (this.d != null) {
            PackageInfo packageInfo = this.d.getPackageManager().getPackageInfo(this.d.getPackageName(), 1);
            if (packageInfo != null) {
                obj2 = packageInfo.versionName == null ? z[8] : packageInfo.versionName;
                String str2 = packageInfo.versionCode;
                jSONObject.put(z[5], obj2);
                jSONObject.put(z[0], str2);
            }
        }
        jSONArray.put(jSONObject);
        return jSONArray;
    }

    private static JSONArray a(JSONArray jSONArray, int i) {
        if (jSONArray == null) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            if (i2 != i) {
                try {
                    jSONArray2.put(jSONArray.get(i2));
                } catch (JSONException e) {
                }
            }
        }
        return jSONArray2;
    }

    private static void a(Context context, JSONArray jSONArray) {
        String jSONArray2 = jSONArray.toString();
        if (jSONArray2 != null && jSONArray2.length() > 0 && context != null) {
            try {
                FileOutputStream openFileOutput = context.openFileOutput(z[10], 0);
                openFileOutput.write(jSONArray2.getBytes());
                openFileOutput.flush();
                openFileOutput.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e2) {
            }
        }
    }

    public static void b(Context context) {
        if (context != null) {
            File file = new File(context.getFilesDir(), z[10]);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private static JSONArray d(Context context) {
        if (!new File(context.getFilesDir(), z[10]).exists()) {
            return null;
        }
        JSONArray jSONArray;
        try {
            FileInputStream openFileInput = context.openFileInput(z[10]);
            byte[] bArr = new byte[1024];
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int read = openFileInput.read(bArr);
                if (read == -1) {
                    break;
                }
                stringBuffer.append(new String(bArr, 0, read));
            }
            if (stringBuffer.toString().length() > 0) {
                jSONArray = new JSONArray(stringBuffer.toString());
                return jSONArray;
            }
        } catch (Exception e) {
        }
        jSONArray = null;
        return jSONArray;
    }

    public final void a(Context context) {
        this.d = context;
        if (this.c == null) {
            this.c = Thread.getDefaultUncaughtExceptionHandler();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.a = true;
    }

    public final void a(Throwable th, String str) {
        if (this.a && this.d != null) {
            JSONArray a = a(this.d, th, str);
            b(this.d);
            a(this.d, a);
        }
    }

    public final JSONObject c(Context context) {
        JSONObject jSONObject = null;
        if (this.a) {
            JSONArray d = d(context);
            if (d != null) {
                jSONObject = new JSONObject();
                try {
                    jSONObject.put(z[14], d);
                    jSONObject.put(z[13], cn.jpush.android.a.j());
                    jSONObject.put(z[11], z[12]);
                    jSONObject.put(z[15], a.c(context));
                } catch (JSONException e) {
                }
            }
        }
        return jSONObject;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        JSONArray a = a(this.d, th, "");
        b(this.d);
        a(this.d, a);
        if (this.c != this) {
            this.c.uncaughtException(thread, th);
        }
        throw new RuntimeException(th);
    }
}
