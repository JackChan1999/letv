package cn.jpush.android.b.a;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import cn.jpush.android.util.z;
import com.google.gson.jpush.k;
import com.google.gson.jpush.w;
import com.google.gson.jpush.y;
import com.letv.core.messagebus.config.LeMessageIds;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public final class d {
    private static final String[] z;
    private HashMap<String, Method> a;
    private String b;
    private String c;
    private k d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 35;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "fM";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
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
            case 0: goto L_0x019f;
            case 1: goto L_0x01a3;
            case 2: goto L_0x01a7;
            case 3: goto L_0x01ab;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 2;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0012;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
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
            case 15: goto L_0x00d4;
            case 16: goto L_0x00df;
            case 17: goto L_0x00ea;
            case 18: goto L_0x00f5;
            case 19: goto L_0x0100;
            case 20: goto L_0x010b;
            case 21: goto L_0x0116;
            case 22: goto L_0x0121;
            case 23: goto L_0x012c;
            case 24: goto L_0x0137;
            case 25: goto L_0x0142;
            case 26: goto L_0x014d;
            case 27: goto L_0x0158;
            case 28: goto L_0x0163;
            case 29: goto L_0x016e;
            case 30: goto L_0x0179;
            case 31: goto L_0x0184;
            case 32: goto L_0x018f;
            case 33: goto L_0x019a;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "f@";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "f\\";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "s|OonUEmxc";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "f_";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0010/a{qM/y}g\u0019xiltPj{.vV/nk\"_f~}v\u0019m|cTjxkp\u0015/{gnU/nk\"In}";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "Tjxfm]'";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "fA";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "B-oaf\\-6.']#,,p\\|ybv\u001b5,+qD";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "e-";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "Wz`b";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u0019lmbn\u0019eal\u0003/";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0019}i}wU{6";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0019fbgvPn`gxX{eal\u0019miikW-%5tX},o?B~ykw\\5WS.Zn`b`Xlg4dLaozkVa$'yOn~.f\u0004N~|c@!||mM`xwr\\!bkZj\"mcUc$op^zaklM| >+\u0002ym|\"Z2h qQfjz*\u00104zop\u0019j1j,Jgehv\u0011&7zjP|\"w\\ziUad!m~rUv$zjP| j+\u0002fj&#\\&wjgUjxk\"Mge},Hzi{gblQsD4";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u0019lmbn\u0019j~|mK#,cgJ|mig\u0003be}q\u0019bizjVk,`cTj.stX},k?bR7hmK'zop\u0019g1?9Q3j n\\akzj\u0002g'%+Bym|\"Z2jUjd4zop\u0019e1z{Ijch\"Z4iUg\u0017ci`eMgQ3h\u0002fj&h\u00042.hwWlxgmW-%utX},j?X!}{gLj\"bgWhxf9X!}{gLjWj_\u0004l7hYQR1jDym|\"^2F]Mw!|opJj$~pVb|z*s\\C@,J{~gl^fjw*BbizjVk6h,Jgehv\u0011& z{Ij4g\u0015n~iq\u0003iq'+\u00104eh*^!oaf\\.1<2\t&wzjK`{,";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "PafkaMjh.lXbi.aXa,`mM/nk\"Wz`b";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\u0019lmbn\u0019j~|mK#,mm]j6,)^!oaf\\$.\"\"Tj}c^j6,)^!~kqLcxsp\\{y|l\u0019h\"|gJz`z\u0002@ndgZ{\"igM@{`RK`|kpMvBoo\\|$o+\u0017ic|GXld&dLaozkVa$j+Bym|\"Z2mUfd4eh*Mv|km_/o3?\u0004-j{lZ{eal\u001b)*j#\u00042.mcUcnoaR-%ucbkQ3dLaozkVa$'yKjx{pW/o cI`w*X#Wj_\u0017lc`aX{$OpKnu rK`xav@i qUfok,Zn`b*X}k{o\\ax}.\t&%'Dr%5`\u0017";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "X!)}?";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "SnzoqZ}e~v\u0003'j{lZ{eal\u0011m%uaVaan\\!`ae\u0011-";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "\u0014\"!#/\u0014\"!#/";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "_zbmvP`b&+Bym|\"_2M|pXv\"~pV{cz{Ij\"}nPli aXc`&cKhycgW{\"2\u00104eh*_!`kl^{d23\u0010txfpVx.";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "\u0004n7mmW|cbg\u0017cci*\u001b";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "\u0019fbgvPn`gxX{eal\u0019jbj \u0010r%&uPahau\u00104";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        r2 = 23;
        r1 = "Paez\"S|,kpK`~4";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0121:
        r3[r2] = r1;
        r2 = 24;
        r1 = "\u0010/{gvQ/zonPk,~cKnakv\\}";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012c:
        r3[r2] = r1;
        r2 = 25;
        r1 = "W`x.dVzbj\"Tjxfm]'";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0137:
        r3[r2] = r1;
        r2 = 26;
        r1 = "Tjxfm]/ivgZzxk\"\\}~ap\u0003";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0142:
        r3[r2] = r1;
        r2 = 27;
        r1 = "WzalgK";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014d:
        r3[r2] = r1;
        r2 = 28;
        r1 = "Zn`b\"]nxo\"\\b|z{";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0158:
        r3[r2] = r1;
        r2 = 29;
        r1 = "VmfkaM";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0163:
        r3[r2] = r1;
        r2 = 30;
        r1 = "X}k}";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x016e:
        r3[r2] = r1;
        r2 = 31;
        r1 = "Mv|kq";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0179:
        r3[r2] = r1;
        r2 = 32;
        r1 = "Tjxfm]";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0184:
        r3[r2] = r1;
        r2 = 33;
        r1 = "[`cbgXa";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x018f:
        r3[r2] = r1;
        r2 = 34;
        r1 = "J{~gl^";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019a:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x019f:
        r9 = 57;
        goto L_0x001f;
    L_0x01a3:
        r9 = 15;
        goto L_0x001f;
    L_0x01a7:
        r9 = 12;
        goto L_0x001f;
    L_0x01ab:
        r9 = 14;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.b.a.d.<clinit>():void");
    }

    public d(String str, Class cls) {
        try {
            if (TextUtils.isEmpty(str)) {
                throw new Exception(z[15]);
            }
            this.b = str;
            this.a = new HashMap();
            Method[] declaredMethods = cls.getDeclaredMethods();
            StringBuilder stringBuilder = new StringBuilder(z[18]);
            stringBuilder.append(this.b);
            stringBuilder.append(z[13]);
            for (Method method : declaredMethods) {
                if (method.getModifiers() == 9) {
                    String a = a(method);
                    if (a != null) {
                        this.a.put(a, method);
                        stringBuilder.append(String.format(z[17], new Object[]{method.getName()}));
                    }
                }
            }
            stringBuilder.append(z[20]);
            stringBuilder.append(this.b);
            stringBuilder.append(z[14]);
            stringBuilder.append(this.b);
            stringBuilder.append(z[16]);
            stringBuilder.append(this.b);
            stringBuilder.append(z[21]);
            stringBuilder.append(this.b);
            stringBuilder.append(z[22]);
            this.c = stringBuilder.toString();
            new StringBuilder(z[19]).append(stringBuilder.toString());
            z.b();
        } catch (Exception e) {
            Log.e(z[3], new StringBuilder(z[23]).append(e.getMessage()).toString());
        }
    }

    private String a(String str, int i, Object obj) {
        String str2;
        if (obj == null) {
            str2 = z[10];
        } else if (obj instanceof String) {
            str2 = "\"" + ((String) obj).replace("\"", z[9]) + "\"";
        } else if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Boolean) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof JSONObject)) {
            str2 = String.valueOf(obj);
        } else {
            if (this.d == null) {
                this.d = new k();
            }
            k kVar = this.d;
            if (obj == null) {
                w wVar = y.a;
                Appendable stringWriter = new StringWriter();
                kVar.a(wVar, stringWriter);
                str2 = stringWriter.toString();
            } else {
                Type type = obj.getClass();
                Object stringWriter2 = new StringWriter();
                kVar.a(obj, type, stringWriter2);
                str2 = stringWriter2.toString();
            }
        }
        str2 = String.format(z[8], new Object[]{Integer.valueOf(i), str2});
        Log.d(z[3], this.b + z[11] + str + z[12] + str2);
        return str2;
    }

    private static String a(Method method) {
        String name = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        int length = parameterTypes.length;
        if (length <= 0 || parameterTypes[0] != WebView.class) {
            Log.w(z[3], new StringBuilder(z[6]).append(name).append(z[5]).toString());
            return null;
        }
        for (int i = 1; i < length; i++) {
            Class cls = parameterTypes[i];
            name = cls == String.class ? name + z[2] : (cls == Integer.TYPE || cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE) ? name + z[7] : cls == Boolean.TYPE ? name + z[0] : cls == JSONObject.class ? name + z[1] : name + z[4];
        }
        return name;
    }

    public final String a() {
        return this.c;
    }

    public final String a(WebView webView, String str) {
        if (TextUtils.isEmpty(str)) {
            return a(str, LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA, z[28]);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(z[32]);
            JSONArray jSONArray = jSONObject.getJSONArray(z[31]);
            JSONArray jSONArray2 = jSONObject.getJSONArray(z[30]);
            int length = jSONArray.length();
            Object[] objArr = new Object[(length + 1)];
            int i = 0;
            objArr[0] = webView;
            int i2 = 0;
            while (i2 < length) {
                String str2;
                int i3;
                String optString = jSONArray.optString(i2);
                int i4;
                if (z[34].equals(optString)) {
                    optString = string + z[2];
                    objArr[i2 + 1] = jSONArray2.isNull(i2) ? null : jSONArray2.getString(i2);
                    i4 = i;
                    str2 = optString;
                    i3 = i4;
                } else if (z[27].equals(optString)) {
                    string = string + z[7];
                    i3 = ((i * 10) + i2) + 1;
                    str2 = string;
                } else if (z[33].equals(optString)) {
                    optString = string + z[0];
                    objArr[i2 + 1] = Boolean.valueOf(jSONArray2.getBoolean(i2));
                    i4 = i;
                    str2 = optString;
                    i3 = i4;
                } else if (z[29].equals(optString)) {
                    optString = string + z[1];
                    objArr[i2 + 1] = jSONArray2.isNull(i2) ? null : jSONArray2.getJSONObject(i2);
                    i4 = i;
                    str2 = optString;
                    i3 = i4;
                } else {
                    i4 = i;
                    str2 = string + z[4];
                    i3 = i4;
                }
                i2++;
                string = str2;
                i = i3;
            }
            Method method = (Method) this.a.get(string);
            if (method == null) {
                return a(str, LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA, new StringBuilder(z[25]).append(string).append(z[24]).toString());
            }
            if (i > 0) {
                Class[] parameterTypes = method.getParameterTypes();
                while (i > 0) {
                    i2 = i - ((i / 10) * 10);
                    Class cls = parameterTypes[i2];
                    if (cls == Integer.TYPE) {
                        objArr[i2] = Integer.valueOf(jSONArray2.getInt(i2 - 1));
                    } else if (cls == Long.TYPE) {
                        objArr[i2] = Long.valueOf(Long.parseLong(jSONArray2.getString(i2 - 1)));
                    } else {
                        objArr[i2] = Double.valueOf(jSONArray2.getDouble(i2 - 1));
                    }
                    i /= 10;
                }
            }
            return a(str, 200, method.invoke(null, objArr));
        } catch (Exception e) {
            return e.getCause() != null ? a(str, LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA, new StringBuilder(z[26]).append(e.getCause().getMessage()).toString()) : a(str, LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA, new StringBuilder(z[26]).append(e.getMessage()).toString());
        }
    }
}
