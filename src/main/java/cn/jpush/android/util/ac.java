package cn.jpush.android.util;

import android.content.Context;
import cn.jpush.android.a;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.pp.utils.NetworkUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ac {
    public static JSONObject a = null;
    private static final String b = ac.class.getSimpleName();
    private static String c = "";
    private static String d;
    private static String e;
    private static final String f = new StringBuilder(z[34]).append(d).toString();
    private static ExecutorService g = Executors.newSingleThreadExecutor();
    private static Object h = new Object();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 38;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "6\u0005G\b\u001b";
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
            case 0: goto L_0x01fc;
            case 1: goto L_0x01ff;
            case 2: goto L_0x0203;
            case 3: goto L_0x0207;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 40;
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
            case 0: goto L_0x0045;
            case 1: goto L_0x004e;
            case 2: goto L_0x0057;
            case 3: goto L_0x005f;
            case 4: goto L_0x0067;
            case 5: goto L_0x006f;
            case 6: goto L_0x0078;
            case 7: goto L_0x0081;
            case 8: goto L_0x008c;
            case 9: goto L_0x0098;
            case 10: goto L_0x00a3;
            case 11: goto L_0x00af;
            case 12: goto L_0x00bb;
            case 13: goto L_0x00c7;
            case 14: goto L_0x00d2;
            case 15: goto L_0x00dd;
            case 16: goto L_0x00e8;
            case 17: goto L_0x00f3;
            case 18: goto L_0x00fe;
            case 19: goto L_0x0109;
            case 20: goto L_0x0114;
            case 21: goto L_0x011f;
            case 22: goto L_0x012a;
            case 23: goto L_0x0135;
            case 24: goto L_0x0140;
            case 25: goto L_0x014b;
            case 26: goto L_0x0156;
            case 27: goto L_0x0162;
            case 28: goto L_0x016d;
            case 29: goto L_0x0178;
            case 30: goto L_0x0183;
            case 31: goto L_0x018e;
            case 32: goto L_0x0199;
            case 33: goto L_0x01a4;
            case 34: goto L_0x01b0;
            case 35: goto L_0x01bb;
            case 36: goto L_0x01c6;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "qB\u0012";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "tG\u0017RNkY\u001b";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "wO\u001dy^aY";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0057:
        r3[r2] = r1;
        r2 = 4;
        r1 = "e[\u0006yCaR";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005f:
        r3[r2] = r1;
        r2 = 5;
        r1 = "Q0\u000b\u0010";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0067:
        r3[r2] = r1;
        r2 = 6;
        r1 = "gD\u0018RMj_";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006f:
        r3[r2] = r1;
        r2 = 7;
        r1 = "vN\u0006IZp\u000b\u0000OI$X\u001fHOhNVE@eE\u0018CD>";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0078:
        r3[r2] = r1;
        r2 = 8;
        r1 = "$[\u0017T\\w";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0081:
        r3[r2] = r1;
        r2 = 9;
        r1 = "hD\u0011\u0006[mQ\u0013\u001c";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008c:
        r3[r2] = r1;
        r2 = 10;
        r1 = "t^\u0002\u0006KkE\u0002CFp\u000b\u0013^Ka[\u0002OGj\u0007VAArNVSX$X\u0013HL$G\u0019A\u0012";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0098:
        r3[r2] = r1;
        r2 = 11;
        r1 = "n[\u0003U@[X\u0002G\\[H\u0017E@at\u001eO[pD\u0004_\u0006nX\u0019H";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a3:
        r3[r2] = r1;
        r2 = 12;
        r1 = "wN\u0018B\bhD\u0011\u0006[hB\u0015C\u0012";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00af:
        r3[r2] = r1;
        r2 = 13;
        r1 = "q_\u0010\u000b\u0010";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00bb:
        r3[r2] = r1;
        r2 = 14;
        r1 = "sY\u0017V\bgD\u0018RImE\u0013T\baS\u0015CXpB\u0019H\u0004$L\u001fPM$^\u0006\u0006[aE\u0012\u0006DkLL";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c7:
        r3[r2] = r1;
        r2 = 15;
        r1 = "hD\u0011\u0006Lm]\u001fBM`\u000b\u001fH\\k\u000b";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00d2:
        r3[r2] = r1;
        r2 = 16;
        r1 = "gJ\u0018\u0001\\$N\u0018EG`B\u0018A\b";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00dd:
        r3[r2] = r1;
        r2 = 17;
        r1 = "$B\u0005\u0006FqG\u001a\n\bvN\u0002SZj\u000b\u0018SDh";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e8:
        r3[r2] = r1;
        r2 = 18;
        r1 = "lB\u0005RGvR)@AhN";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00f3:
        r3[r2] = r1;
        r2 = 19;
        r1 = "(\u000b\u0011O^a\u000b\u0003V\bvN\u0017B\b>";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00fe:
        r3[r2] = r1;
        r2 = 20;
        r1 = "$B\u0018RG$a\u0005IFKI\u001cCKp\u0007VAArNVSX$Y\u0013GL$\u0011";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0109:
        r3[r2] = r1;
        r2 = 21;
        r1 = "g^\u0004TMj_)UMwX\u001fIF[M\u001fJM";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x0114:
        r3[r2] = r1;
        r2 = 22;
        r1 = "gD\u0018RM|_VO[$E\u0003JD$\u0007VAArNVSX$Y\u0013GL$";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011f:
        r3[r2] = r1;
        r2 = 23;
        r1 = "$B\u0018V]px\u0002TMeFZ\u0006Om]\u0013\u0006]t\u000b\u0004CI`\u000bV\u001c";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x012a:
        r3[r2] = r1;
        r2 = 24;
        r1 = "gJ\u0018\u0001\\$Y\u0013GL$";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0135:
        r3[r2] = r1;
        r2 = 25;
        r1 = "gJ\u0018\u0001\\$D\u0006CF$";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0140:
        r3[r2] = r1;
        r2 = 26;
        r1 = "gJ\u0018\u0001\\$I\u0003OD`\u000b";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x014b:
        r3[r2] = r1;
        r2 = 27;
        r1 = "$\u0007VAArNVSX$X\u0017PM$\u0011";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0156:
        r3[r2] = r1;
        r2 = 28;
        r1 = "wJ\u0000C\bhD\u0011\u0006Aj\u000b\u0001TApN>O[pD\u0004_dkLL,";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0162:
        r3[r2] = r1;
        r2 = 29;
        r1 = "Y\u000bZ\u0006Om]\u0013\u0006]t\u000b\u0005G^a\u000bL";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x016d:
        r3[r2] = r1;
        r2 = 30;
        r1 = "gJ\u0018\u0001\\$\\\u0004O\\a\u000b";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0178:
        r3[r2] = r1;
        r2 = 31;
        r1 = "BB\u001aCXe_\u001e\u0006MvY\u0019T\bkMV}";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0183:
        r3[r2] = r1;
        r2 = 32;
        r1 = "$D\u0003RXq_%RZaJ\u001b\n\bcB\u0000C\bq[VUIrNV\u001c";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x018e:
        r3[r2] = r1;
        r2 = 33;
        r1 = "gD\u0018RM|_VO[$E\u0003JD$\u0007VAArNVSX$X\u0017PM$";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0199:
        r3[r2] = r1;
        r2 = 34;
        r1 = "l_\u0002V\u0012+\u0004\u0005RIpXXLXqX\u001e\bKj";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x01a4:
        r3[r2] = r1;
        r2 = 35;
        r1 = "w_\u0017R[*A\u0006S[l\u0005\u0015H";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01b0:
        r3[r2] = r1;
        r2 = 36;
        r1 = "l_\u0002V\u0012+\u0004";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01bb:
        r3[r2] = r1;
        r2 = 37;
        r1 = "n[\u0003U@[X\u0002G\\[H\u0017E@a\u0005\u001cUGj";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01c6:
        r3[r2] = r1;
        z = r4;
        r0 = cn.jpush.android.util.ac.class;
        r0 = r0.getSimpleName();
        b = r0;
        r0 = "";
        c = r0;
        r1 = "+]G\tZa[\u0019T\\";
        r0 = -1;
    L_0x01d9:
        r1 = r1.toCharArray();
        r2 = r1.length;
        r3 = 0;
        r4 = 1;
        if (r2 > r4) goto L_0x0218;
    L_0x01e2:
        r4 = r1;
        r5 = r3;
        r11 = r2;
        r2 = r1;
        r1 = r11;
    L_0x01e7:
        r7 = r2[r3];
        r6 = r5 % 5;
        switch(r6) {
            case 0: goto L_0x020b;
            case 1: goto L_0x020d;
            case 2: goto L_0x0210;
            case 3: goto L_0x0213;
            default: goto L_0x01ee;
        };
    L_0x01ee:
        r6 = 40;
    L_0x01f0:
        r6 = r6 ^ r7;
        r6 = (char) r6;
        r2[r3] = r6;
        r3 = r5 + 1;
        if (r1 != 0) goto L_0x0216;
    L_0x01f8:
        r2 = r4;
        r5 = r3;
        r3 = r1;
        goto L_0x01e7;
    L_0x01fc:
        r9 = 4;
        goto L_0x0020;
    L_0x01ff:
        r9 = 43;
        goto L_0x0020;
    L_0x0203:
        r9 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0020;
    L_0x0207:
        r9 = 38;
        goto L_0x0020;
    L_0x020b:
        r6 = 4;
        goto L_0x01f0;
    L_0x020d:
        r6 = 43;
        goto L_0x01f0;
    L_0x0210:
        r6 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x01f0;
    L_0x0213:
        r6 = 38;
        goto L_0x01f0;
    L_0x0216:
        r2 = r1;
        r1 = r4;
    L_0x0218:
        if (r2 > r3) goto L_0x01e2;
    L_0x021a:
        r2 = new java.lang.String;
        r2.<init>(r1);
        r1 = r2.intern();
        switch(r0) {
            case 0: goto L_0x022c;
            default: goto L_0x0226;
        };
    L_0x0226:
        d = r1;
        r1 = "+]D\tZa[\u0019T\\";
        r0 = 0;
        goto L_0x01d9;
    L_0x022c:
        e = r1;
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 34;
        r1 = r1[r2];
        r0.<init>(r1);
        r1 = d;
        r0 = r0.append(r1);
        r0 = r0.toString();
        f = r0;
        r0 = java.util.concurrent.Executors.newSingleThreadExecutor();
        g = r0;
        r0 = 0;
        a = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        h = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.ac.<clinit>():void");
    }

    public static String a() {
        long t = a.t();
        if (t == 0) {
            z.b();
            return null;
        }
        String b = a.b(t + a.b(a.w()));
        return !ai.a(b) ? t + NetworkUtils.DELIMITER_COLON + b : null;
    }

    public static String a(int i) {
        try {
            InetAddress.getByName(z[35]);
            return new StringBuilder(z[34]).append(e).toString();
        } catch (Exception e) {
            return c;
        }
    }

    public static String a(String str) {
        if (!str.startsWith(z[36])) {
            str = new StringBuilder(z[36]).append(str).toString();
        }
        if (!str.endsWith(d)) {
            str = str + d;
        }
        c = str;
        return str;
    }

    public static String a(String str, int i) {
        if (ai.a(str)) {
            z.b();
            return null;
        }
        String c = c(str);
        long t = a.t();
        if (t == 0) {
            z.b();
            return null;
        }
        c = a.b(t + a.b(a.w()) + c);
        return !ai.a(c) ? t + NetworkUtils.DELIMITER_COLON + c : null;
    }

    private static ArrayList<JSONArray> a(JSONArray jSONArray, int i) {
        int i2;
        UnsupportedEncodingException unsupportedEncodingException;
        JSONArray jSONArray2;
        int i3;
        UnsupportedEncodingException unsupportedEncodingException2;
        int i4;
        JSONArray jSONArray3;
        ArrayList<JSONArray> arrayList = new ArrayList();
        JSONArray jSONArray4 = new JSONArray();
        if (jSONArray != null && jSONArray.length() > 0) {
            int length = jSONArray.length() - 1;
            int i5 = 0;
            int i6 = 0;
            while (length >= 0) {
                JSONObject optJSONObject = jSONArray.optJSONObject(length);
                if (optJSONObject != null) {
                    try {
                        int length2 = optJSONObject.toString().getBytes(z[5]).length;
                        i2 = i6 + length2;
                        if (i2 > 204800 && length > 1) {
                            if (length > 1) {
                                return arrayList;
                            }
                            if (length == 1) {
                                try {
                                    jSONArray4.put(optJSONObject);
                                    arrayList.add(jSONArray4);
                                    return arrayList;
                                } catch (UnsupportedEncodingException e) {
                                    unsupportedEncodingException = e;
                                    jSONArray2 = jSONArray4;
                                    i3 = i2;
                                    i2 = i5;
                                }
                            }
                        }
                        if (i5 + length2 > 20480) {
                            try {
                                arrayList.add(jSONArray4);
                                jSONArray2 = new JSONArray();
                                try {
                                    jSONArray2.put(optJSONObject);
                                    i3 = i2;
                                    i2 = length2;
                                } catch (UnsupportedEncodingException e2) {
                                    unsupportedEncodingException2 = e2;
                                    i3 = i2;
                                    i2 = length2;
                                    unsupportedEncodingException = unsupportedEncodingException2;
                                    unsupportedEncodingException.getMessage();
                                    z.e();
                                    length--;
                                    i5 = i2;
                                    i4 = i3;
                                    jSONArray4 = jSONArray2;
                                    i6 = i4;
                                }
                            } catch (UnsupportedEncodingException e3) {
                                unsupportedEncodingException2 = e3;
                                jSONArray2 = jSONArray4;
                                i3 = i2;
                                i2 = length2;
                                unsupportedEncodingException = unsupportedEncodingException2;
                                unsupportedEncodingException.getMessage();
                                z.e();
                                length--;
                                i5 = i2;
                                i4 = i3;
                                jSONArray4 = jSONArray2;
                                i6 = i4;
                            }
                        } else {
                            i6 = i5 + length2;
                            try {
                                jSONArray4.put(optJSONObject);
                                jSONArray3 = jSONArray4;
                                i3 = i2;
                                i2 = i6;
                                jSONArray2 = jSONArray3;
                            } catch (UnsupportedEncodingException e4) {
                                unsupportedEncodingException = e4;
                                jSONArray3 = jSONArray4;
                                i3 = i2;
                                i2 = i6;
                                jSONArray2 = jSONArray3;
                                unsupportedEncodingException.getMessage();
                                z.e();
                                length--;
                                i5 = i2;
                                i4 = i3;
                                jSONArray4 = jSONArray2;
                                i6 = i4;
                            }
                        }
                    } catch (UnsupportedEncodingException e5) {
                        unsupportedEncodingException = e5;
                        i2 = i5;
                        i4 = i6;
                        jSONArray2 = jSONArray4;
                        i3 = i4;
                        unsupportedEncodingException.getMessage();
                        z.e();
                        length--;
                        i5 = i2;
                        i4 = i3;
                        jSONArray4 = jSONArray2;
                        i6 = i4;
                    }
                } else {
                    i2 = i5;
                    i4 = i6;
                    jSONArray2 = jSONArray4;
                    i3 = i4;
                }
                length--;
                i5 = i2;
                i4 = i3;
                jSONArray4 = jSONArray2;
                i6 = i4;
            }
            if (jSONArray4.length() > 0) {
                arrayList.add(jSONArray4);
            }
        }
        return arrayList;
    }

    public static JSONObject a(Context context, String str) {
        Closeable openFileInput;
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        if (str == null || str.length() <= 0) {
            z.b();
            return null;
        }
        String str2 = str.equals(z[11]) ? z[18] : z[21];
        if (context == null) {
            new StringBuilder(z[22]).append(str2);
            z.b();
            return null;
        }
        try {
            openFileInput = context.openFileInput(str);
            try {
                byte[] bArr = new byte[(openFileInput.available() + 1)];
                openFileInput.read(bArr);
                a(openFileInput);
                try {
                    String str3 = new String(bArr, z[5]);
                    if (!ai.a(str3)) {
                        return new JSONObject(str3);
                    }
                    new StringBuilder().append(str2).append(z[17]);
                    z.b();
                    return null;
                } catch (UnsupportedEncodingException e3) {
                    new StringBuilder(z[16]).append(str2).append(z[19]).append(e3.getMessage());
                    z.b();
                    return null;
                } catch (JSONException e4) {
                    new StringBuilder(z[26]).append(str2).append(z[20]).append(e4.getMessage());
                    z.b();
                    return null;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                try {
                    new StringBuilder(z[25]).append(str2).append(z[23]).append(e.getMessage());
                    z.b();
                    a(openFileInput);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    a(openFileInput);
                    throw th;
                }
            } catch (IOException e6) {
                e2 = e6;
                new StringBuilder(z[24]).append(str2).append(z[19]).append(e2.getMessage());
                z.b();
                a(openFileInput);
                return null;
            }
        } catch (FileNotFoundException e7) {
            e = e7;
            openFileInput = null;
            new StringBuilder(z[25]).append(str2).append(z[23]).append(e.getMessage());
            z.b();
            a(openFileInput);
            return null;
        } catch (IOException e8) {
            e2 = e8;
            openFileInput = null;
            new StringBuilder(z[24]).append(str2).append(z[19]).append(e2.getMessage());
            z.b();
            a(openFileInput);
            return null;
        } catch (Throwable th3) {
            openFileInput = null;
            th = th3;
            a(openFileInput);
            throw th;
        }
    }

    public static void a(Context context) {
        z.b();
        a(context, z[37], null);
        b(context);
    }

    public static void a(Context context, int i) {
        int i2 = 0;
        if (a != null) {
            JSONObject jSONObject = a;
            if (i >= 204800) {
                b(context);
                return;
            }
            int length;
            try {
                length = jSONObject.toString().getBytes(z[13]).length;
            } catch (UnsupportedEncodingException e) {
                length = 0;
            }
            int i3 = (length + i) - 204800;
            if (i3 > 0) {
                JSONArray optJSONArray = jSONObject.optJSONArray(z[6]);
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    try {
                        JSONObject jSONObject2;
                        JSONArray jSONArray = new JSONArray();
                        for (length = 0; length < optJSONArray.length(); length++) {
                            JSONObject jSONObject3 = optJSONArray.getJSONObject(length);
                            if (jSONObject3 != null) {
                                if (i2 >= i3) {
                                    jSONArray.put(jSONObject3);
                                }
                                i2 += jSONObject3.toString().getBytes(z[13]).length;
                            }
                        }
                        if (jSONArray.length() > 0) {
                            jSONObject.put(z[6], jSONArray);
                            jSONObject2 = jSONObject;
                        } else {
                            jSONObject2 = null;
                        }
                        a = jSONObject2;
                        a(context, z[11], jSONObject2);
                    } catch (JSONException e2) {
                    } catch (UnsupportedEncodingException e3) {
                    }
                }
            }
        }
    }

    public static void a(Context context, JSONArray jSONArray) {
        if (context == null) {
            z.b();
        } else if (jSONArray == null || jSONArray.length() <= 0) {
            z.b();
        } else {
            g.execute(new ad(context, jSONArray));
        }
    }

    public static void a(Context context, JSONObject jSONObject) {
        a(context, new JSONArray().put(jSONObject));
    }

    private static void a(Context context, JSONObject jSONObject, ArrayList<JSONArray> arrayList) {
        if (arrayList.size() <= 0) {
            b(context);
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < arrayList.size(); i++) {
            JSONArray jSONArray2 = (JSONArray) arrayList.get(i);
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                if (jSONArray2.optJSONObject(i2) != null) {
                    jSONArray.put(jSONArray2.optJSONObject(i2));
                }
            }
        }
        try {
            jSONObject.put(z[6], jSONArray);
        } catch (JSONException e) {
        }
        a = jSONObject;
        a(context, z[11], jSONObject);
    }

    private static void a(Context context, JSONObject jSONObject, JSONArray jSONArray, ArrayList<JSONArray> arrayList) {
        if (arrayList.size() == 1) {
            b(context);
        } else if (jSONArray != null && arrayList.size() > 1) {
            arrayList.remove(jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < arrayList.size(); i++) {
                JSONArray jSONArray3 = (JSONArray) arrayList.get(i);
                for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                    if (jSONArray3.optJSONObject(i2) != null) {
                        jSONArray2.put(jSONArray3.optJSONObject(i2));
                    }
                }
            }
            try {
                jSONObject.put(z[6], jSONArray2);
            } catch (JSONException e) {
            }
            a = jSONObject;
            a(context, z[11], jSONObject);
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r8, java.lang.String r9, org.json.JSONObject r10) {
        /*
        r3 = 11;
        r2 = 1;
        r1 = 0;
        r0 = cn.jpush.android.util.ai.a(r9);
        if (r0 == 0) goto L_0x000f;
    L_0x000a:
        cn.jpush.android.util.z.b();
        r0 = r1;
    L_0x000e:
        return r0;
    L_0x000f:
        r0 = z;
        r0 = r0[r3];
        r0 = r9.equals(r0);
        if (r0 == 0) goto L_0x0035;
    L_0x0019:
        r0 = z;
        r3 = 18;
        r0 = r0[r3];
        r4 = r0;
    L_0x0020:
        if (r8 != 0) goto L_0x003d;
    L_0x0022:
        r0 = new java.lang.StringBuilder;
        r2 = z;
        r3 = 33;
        r2 = r2[r3];
        r0.<init>(r2);
        r0.append(r4);
        cn.jpush.android.util.z.b();
        r0 = r1;
        goto L_0x000e;
    L_0x0035:
        r0 = z;
        r3 = 21;
        r0 = r0[r3];
        r4 = r0;
        goto L_0x0020;
    L_0x003d:
        r5 = h;
        monitor-enter(r5);
        r0 = "";
        if (r10 == 0) goto L_0x006a;
    L_0x0044:
        r0 = r10.toString();	 Catch:{ all -> 0x0087 }
        r3 = z;	 Catch:{ all -> 0x0087 }
        r6 = 11;
        r3 = r3[r6];	 Catch:{ all -> 0x0087 }
        r3 = r9.equals(r3);	 Catch:{ all -> 0x0087 }
        if (r3 == 0) goto L_0x006a;
    L_0x0054:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0082 }
        r6 = z;	 Catch:{ Exception -> 0x0082 }
        r7 = 28;
        r6 = r6[r7];	 Catch:{ Exception -> 0x0082 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x0082 }
        r6 = 1;
        r6 = r10.toString(r6);	 Catch:{ Exception -> 0x0082 }
        r3.append(r6);	 Catch:{ Exception -> 0x0082 }
        cn.jpush.android.util.z.a();	 Catch:{ Exception -> 0x0082 }
    L_0x006a:
        r3 = 0;
        r6 = 0;
        r3 = r8.openFileOutput(r9, r6);	 Catch:{ FileNotFoundException -> 0x008a, UnsupportedEncodingException -> 0x00b6, IOException -> 0x00e1, NullPointerException -> 0x010c }
        r6 = z;	 Catch:{ FileNotFoundException -> 0x013f, UnsupportedEncodingException -> 0x00b6, IOException -> 0x00e1, NullPointerException -> 0x010c }
        r7 = 5;
        r6 = r6[r7];	 Catch:{ FileNotFoundException -> 0x013f, UnsupportedEncodingException -> 0x00b6, IOException -> 0x00e1, NullPointerException -> 0x010c }
        r0 = r0.getBytes(r6);	 Catch:{ FileNotFoundException -> 0x013f, UnsupportedEncodingException -> 0x00b6, IOException -> 0x00e1, NullPointerException -> 0x010c }
        r3.write(r0);	 Catch:{ FileNotFoundException -> 0x013f, UnsupportedEncodingException -> 0x00b6, IOException -> 0x00e1, NullPointerException -> 0x010c }
        a(r3);	 Catch:{ all -> 0x0087 }
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        r0 = r2;
        goto L_0x000e;
    L_0x0082:
        r3 = move-exception;
        cn.jpush.android.util.z.h();	 Catch:{ all -> 0x0087 }
        goto L_0x006a;
    L_0x0087:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        throw r0;
    L_0x008a:
        r0 = move-exception;
        r2 = r3;
    L_0x008c:
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x013c }
        r6 = z;	 Catch:{ all -> 0x013c }
        r7 = 25;
        r6 = r6[r7];	 Catch:{ all -> 0x013c }
        r3.<init>(r6);	 Catch:{ all -> 0x013c }
        r3 = r3.append(r4);	 Catch:{ all -> 0x013c }
        r4 = z;	 Catch:{ all -> 0x013c }
        r6 = 32;
        r4 = r4[r6];	 Catch:{ all -> 0x013c }
        r3 = r3.append(r4);	 Catch:{ all -> 0x013c }
        r0 = r0.getMessage();	 Catch:{ all -> 0x013c }
        r3.append(r0);	 Catch:{ all -> 0x013c }
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x013c }
        a(r2);	 Catch:{ all -> 0x0087 }
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        r0 = r1;
        goto L_0x000e;
    L_0x00b6:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0137 }
        r6 = z;	 Catch:{ all -> 0x0137 }
        r7 = 16;
        r6 = r6[r7];	 Catch:{ all -> 0x0137 }
        r2.<init>(r6);	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r4 = z;	 Catch:{ all -> 0x0137 }
        r6 = 27;
        r4 = r4[r6];	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0137 }
        r2.append(r0);	 Catch:{ all -> 0x0137 }
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x0137 }
        a(r3);	 Catch:{ all -> 0x0087 }
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        r0 = r1;
        goto L_0x000e;
    L_0x00e1:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0137 }
        r6 = z;	 Catch:{ all -> 0x0137 }
        r7 = 30;
        r6 = r6[r7];	 Catch:{ all -> 0x0137 }
        r2.<init>(r6);	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r4 = z;	 Catch:{ all -> 0x0137 }
        r6 = 27;
        r4 = r4[r6];	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0137 }
        r2.append(r0);	 Catch:{ all -> 0x0137 }
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x0137 }
        a(r3);	 Catch:{ all -> 0x0087 }
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        r0 = r1;
        goto L_0x000e;
    L_0x010c:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0137 }
        r6 = z;	 Catch:{ all -> 0x0137 }
        r7 = 31;
        r6 = r6[r7];	 Catch:{ all -> 0x0137 }
        r2.<init>(r6);	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r4 = z;	 Catch:{ all -> 0x0137 }
        r6 = 29;
        r4 = r4[r6];	 Catch:{ all -> 0x0137 }
        r2 = r2.append(r4);	 Catch:{ all -> 0x0137 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0137 }
        r2.append(r0);	 Catch:{ all -> 0x0137 }
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x0137 }
        a(r3);	 Catch:{ all -> 0x0087 }
        monitor-exit(r5);	 Catch:{ all -> 0x0087 }
        r0 = r1;
        goto L_0x000e;
    L_0x0137:
        r0 = move-exception;
    L_0x0138:
        a(r3);	 Catch:{ all -> 0x0087 }
        throw r0;	 Catch:{ all -> 0x0087 }
    L_0x013c:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0138;
    L_0x013f:
        r0 = move-exception;
        r2 = r3;
        goto L_0x008c;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.ac.a(android.content.Context, java.lang.String, org.json.JSONObject):boolean");
    }

    private static boolean a(JSONObject jSONObject, Context context) {
        jSONObject.put(z[2], "a");
        long t = a.t();
        if (t == 0) {
            z.e();
            return false;
        }
        jSONObject.put(z[1], t);
        String q = a.q(context);
        if (ai.a(q)) {
            z.e();
            return false;
        }
        jSONObject.put(z[4], q);
        jSONObject.put(z[3], z[0]);
        return true;
    }

    public static String b(String str) {
        String str2 = null;
        try {
            str2 = c.a(str.getBytes(), 10);
        } catch (Exception e) {
            z.e();
        }
        return str2;
    }

    private static void b(Context context) {
        a = null;
        a(context, z[11], null);
    }

    static /* synthetic */ void b(Context context, JSONArray jSONArray) {
        int i = 0;
        String str = z[11];
        if (a == null) {
            a = a(context, str);
        }
        JSONObject jSONObject = a;
        JSONObject jSONObject2 = jSONObject == null ? new JSONObject() : jSONObject;
        JSONArray optJSONArray = jSONObject2.optJSONArray(z[6]);
        JSONArray jSONArray2 = optJSONArray == null ? new JSONArray() : optJSONArray;
        if (jSONArray != null) {
            try {
                if (jSONArray.length() > 0) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        jSONArray2.put(jSONArray.get(i2));
                    }
                }
            } catch (JSONException e) {
            }
        }
        if (!a.b(context)) {
            jSONObject2.put(z[6], jSONArray2);
            a(context, z[11], jSONObject2);
            return;
        }
        if (jSONArray2.length() > 0) {
            ArrayList a = a(jSONArray2, 20480);
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(a);
            try {
                new StringBuilder(z[9]).append(jSONArray2.toString().getBytes(z[13]).length);
                z.b();
            } catch (UnsupportedEncodingException e2) {
            }
            new StringBuilder(z[15]).append(a.size()).append(z[8]);
            z.b();
            int i3 = 0;
            while (i < a.size()) {
                optJSONArray = (JSONArray) a.get(i);
                if (i3 == 0) {
                    if (optJSONArray.length() <= 0) {
                        arrayList.remove(optJSONArray);
                    } else {
                        try {
                            jSONObject2.put(z[6], optJSONArray);
                            try {
                                if (a(jSONObject2, context)) {
                                    if (jSONObject2 != null) {
                                        try {
                                            new StringBuilder(z[12]).append(jSONObject2.toString(1));
                                            z.c();
                                        } catch (JSONException e3) {
                                            new StringBuilder(z[12]).append(jSONObject2.toString());
                                            z.c();
                                        }
                                    }
                                    switch (p.a(context, jSONObject2, true)) {
                                        case -5:
                                        case -4:
                                        case -3:
                                        case -2:
                                            a(context, jSONObject2, optJSONArray, arrayList);
                                            break;
                                        case -1:
                                        case LeMessageIds.MSG_DLNA_LIVE_PROTOCOL /*404*/:
                                        case 429:
                                        case LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA /*500*/:
                                            a(context, jSONObject2, arrayList);
                                            break;
                                        case 200:
                                            a(context, jSONObject2, optJSONArray, arrayList);
                                            break;
                                        case 401:
                                            b(context);
                                            boolean z = true;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                c(context, jSONObject2);
                                return;
                            } catch (Exception e4) {
                                new StringBuilder(z[14]).append(e4);
                                z.b();
                                c(context, jSONObject2);
                                return;
                            }
                        } catch (JSONException e5) {
                            new StringBuilder(z[10]).append(e5);
                            z.b();
                            a(context, jSONObject2, optJSONArray, arrayList);
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public static void b(Context context, JSONObject jSONObject) {
        try {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(z[6], jSONArray);
            if (a(jSONObject2, context)) {
                new StringBuilder(z[7]).append(jSONObject.toString());
                z.b();
                if (p.a(context, jSONObject2, true) == 200) {
                    z.b();
                }
            }
        } catch (JSONException e) {
        } catch (Exception e2) {
        }
    }

    private static String c(String str) {
        String str2 = null;
        try {
            byte[] bytes = str.getBytes(z[5]);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bytes);
            gZIPOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            str2 = a.a(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str2;
    }

    private static void c(Context context, JSONObject jSONObject) {
        a = jSONObject;
        a(context, z[11], jSONObject);
    }
}
