package cn.jpush.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.core.messagebus.config.LeMessageIds;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.json.JSONObject;

public final class p {
    public static boolean a = false;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 46;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = ">\u0000F\u001a<?EF\t*=\nZ\u001f<m\u0003U\u000558\u0017QLtm";
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
            case 0: goto L_0x0221;
            case 1: goto L_0x0225;
            case 2: goto L_0x0229;
            case 3: goto L_0x022d;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 89;
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
            case 15: goto L_0x00d5;
            case 16: goto L_0x00e1;
            case 17: goto L_0x00ed;
            case 18: goto L_0x00f9;
            case 19: goto L_0x0104;
            case 20: goto L_0x010f;
            case 21: goto L_0x011a;
            case 22: goto L_0x0125;
            case 23: goto L_0x0130;
            case 24: goto L_0x013b;
            case 25: goto L_0x0146;
            case 26: goto L_0x0151;
            case 27: goto L_0x015c;
            case 28: goto L_0x0167;
            case 29: goto L_0x0172;
            case 30: goto L_0x017d;
            case 31: goto L_0x0188;
            case 32: goto L_0x0193;
            case 33: goto L_0x019e;
            case 34: goto L_0x01a9;
            case 35: goto L_0x01b4;
            case 36: goto L_0x01bf;
            case 37: goto L_0x01ca;
            case 38: goto L_0x01d5;
            case 39: goto L_0x01e0;
            case 40: goto L_0x01ec;
            case 41: goto L_0x01f7;
            case 42: goto L_0x0202;
            case 43: goto L_0x020d;
            case 44: goto L_0x0219;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u000e\t[\u001f<";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "$\u0001Q\u0002-$\u0011M";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\f\u0006W\t)9Hq\u0002:\"\u0001]\u0002>";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0002\u0011\\\t+m\u0012F\u00037*EF\t*=\nZ\u001f<m\u0016@\r-8\u0016\u0014Ay";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u001f\u0000E\u0019<>\u0011\u0014\u001c89\r\u0014\b6(\u0016\u0014\u000269EQ\u00140>\u0011\u000eLm}Q\u0014Ay";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u000e\nZ\u0002<.\u0011]\u00037";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "aEA\u001e5w";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\u0005\u0011@\u001c\u0011(\tD\t+";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u000e\u0004@\u000f1m6g \t(\u0000F97;\u0000F\u0005?$\u0000P)!.\u0000D\u00180\"\u000b\u0018L19\u0011DL:!\fQ\u0002-m\u0000L\t:8\u0011QL<?\u0017[\u001ex";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = ",\u0006@\u00056#_\\\u0018-=\"Q\u0018y`E";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u00181rAa";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0018\u0016Q\u001et\f\u0002Q\u0002-";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u00075a?\u0011`6p'";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\f\u0006W\t)9Hw\u00048?\u0016Q\u0018";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "\u001d*g8";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "qYR\r0!\u0000PRg";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d5:
        r3[r2] = r1;
        r2 = 17;
        r1 = "qYR\r0!\u0000P3.$\u0011\\3+(\u0011F\u0005<>[\n";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e1:
        r3[r2] = r1;
        r2 = 18;
        r1 = "qYQ\u001e+\"\u0017\nR";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ed:
        r3[r2] = r1;
        r2 = 19;
        r1 = "qYZ\t-:\nF\u0007<?\u0017[\u001egs";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f9:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\u000e\nZ\u0018<#\u0011\u0019 <#\u0002@\u0004";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0104:
        r3[r2] = r1;
        r2 = 21;
        r1 = "\u0015Hu\u001c)`.Q\u0015";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010f:
        r3[r2] = r1;
        r2 = 22;
        r1 = "\u000e\nZ\u0018<#\u0011\u0019)7.\nP\u00057*";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011a:
        r3[r2] = r1;
        r2 = 23;
        r1 = "\u001d\u0017[\u00186.\nX)!.\u0000D\u00180\"\u000b\u000e";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0125:
        r3[r2] = r1;
        r2 = 24;
        r1 = "\u000e\rU\u001e*(\u0011";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0130:
        r3[r2] = r1;
        r2 = 25;
        r1 = "*\u001f]\u001c";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013b:
        r3[r2] = r1;
        r2 = 26;
        r1 = "\u000f\u0004G\u0005:m";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0146:
        r3[r2] = r1;
        r2 = 27;
        r1 = "\f\u0010@\u00046?\fN\r-$\nZ";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0151:
        r3[r2] = r1;
        r2 = 28;
        r1 = ",\u0015D\u00000.\u0004@\u00056#J^\r*\"\u000b";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x015c:
        r3[r2] = r1;
        r2 = 29;
        r1 = "\u0004*q\u0014:(\u0015@\u00056#_P\t;8\u0002";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0167:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\f\u0006W\t)9";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0172:
        r3[r2] = r1;
        r2 = 31;
        r1 = ">\u0011U\u0018,>EW\u0003=(_";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017d:
        r3[r2] = r1;
        r2 = 32;
        r1 = "\u000e\u0004@\u000f1m$G\u001f<?\u0011]\u00037\b\u0017F\u0003+m\u0011[L8;\n]\by%\u0011@\u001cy.\t[\u001f<m\u0006F\r*%E\u0019L";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0188:
        r3[r2] = r1;
        r2 = 33;
        r1 = "\u001e\u0000F\u001a<?EF\t*=\nZ\u001f<m\u0003U\u000558\u0017QVm}U\u0014Ay";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0193:
        r3[r2] = r1;
        r2 = 34;
        r1 = ",\u0006@\u00056#_\\\u0018-=6]\u0001)!\u0000s\t-mH\u0014";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019e:
        r3[r2] = r1;
        r2 = 35;
        r1 = "\u001f\u0000E\u0019<>\u0011\u0014\u000269EU\u0019-%\nF\u0005#(\u0001\u000eXi|E\u0019L";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a9:
        r3[r2] = r1;
        r2 = 36;
        r1 = "\u001e\u0000F\u001a<?EQ\u001e+\"\u0017\u0014Ay";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b4:
        r3[r2] = r1;
        r2 = 37;
        r1 = ".\nZ\n5$\u0006@Vm}\\\u0014Ay";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01bf:
        r3[r2] = r1;
        r2 = 38;
        r1 = "?\u0000E\u0019<>\u0011\u0014\u00180 \u0000[\u0019-wQ\u0004Ty`E";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01ca:
        r3[r2] = r1;
        r2 = 39;
        r1 = "#\n@L8.\u0006Q\u001c-,\u0007X\tcyU\u0002Ltm";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d5:
        r3[r2] = r1;
        r2 = 40;
        r1 = ".\bC\r)";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01e0:
        r3[r2] = r1;
        r2 = 41;
        r1 = "~\u0002C\r)";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01ec:
        r3[r2] = r1;
        r2 = 42;
        r1 = "8\u000b]\u001b8=";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f7:
        r3[r2] = r1;
        r2 = 43;
        r1 = ",\u000bP\u001e6$\u0001\u001a\u001c<?\b]\u001f*$\nZB\u0018\u000e&q?\n\u0012+q8\u000e\u000273\n\u0019$`)";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x0202:
        r3[r2] = r1;
        r2 = 44;
        r1 = ".\nZ\u0002<.\u0011]\u001a09\u001c";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x020d:
        r3[r2] = r1;
        r2 = 45;
        r1 = "|U\u001a\\w}K\u0005[k";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x0219:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        return;
    L_0x0221:
        r9 = 77;
        goto L_0x0020;
    L_0x0225:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0020;
    L_0x0229:
        r9 = 52;
        goto L_0x0020;
    L_0x022d:
        r9 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.p.<clinit>():void");
    }

    public static int a(Context context, JSONObject jSONObject, boolean z) {
        ProtocolException e;
        InputStream inputStream;
        Throwable th;
        IOException e2;
        AssertionError e3;
        String a = ac.a(2);
        if (URLUtil.isHttpUrl(a)) {
            HttpURLConnection a2 = a(context, a);
            a2.setConnectTimeout(30000);
            a2.setReadTimeout(30000);
            a2.setDoOutput(true);
            a2.setDoInput(true);
            a2.setUseCaches(false);
            try {
                a2.setRequestMethod(z[15]);
            } catch (ProtocolException e4) {
                e4.printStackTrace();
            }
            a = "";
            if (jSONObject != null) {
                a = jSONObject.toString();
            }
            if (ai.a(a)) {
                z.b();
                return -2;
            }
            boolean z2;
            if (a2 == null) {
                z.e();
                z2 = false;
            } else {
                a2.setRequestProperty(z[30], z[28]);
                a2.setRequestProperty(z[3], z[25]);
                a2.setRequestProperty(z[22], z[25]);
                a2.setRequestProperty(z[21], a.q(context));
                String a3 = a == null ? ac.a() : ac.a(a, 2);
                if (ai.a(a3)) {
                    z.b();
                    z2 = false;
                } else {
                    a3 = ac.b(a3);
                    if (ai.a(a3)) {
                        z.b();
                        z2 = false;
                    } else {
                        a2.setRequestProperty(z[27], new StringBuilder(z[26]).append(a3).toString());
                        a2.setRequestProperty(z[24], z[11]);
                        z2 = true;
                    }
                }
            }
            if (z2) {
                try {
                    byte[] bytes = a.getBytes(z[11]);
                    OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    OutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    gZIPOutputStream.write(bytes);
                    gZIPOutputStream.close();
                    bytes = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    a2.setRequestProperty(z[20], String.valueOf(bytes.length));
                    byteArrayOutputStream = a2.getOutputStream();
                    byteArrayOutputStream.write(bytes);
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                    try {
                        inputStream = a2.getInputStream();
                        try {
                            int responseCode = a2.getResponseCode();
                            new StringBuilder(z[31]).append(responseCode);
                            z.b();
                            switch (responseCode) {
                                case 200:
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e5) {
                                        }
                                    }
                                    if (a2 != null) {
                                        a2.disconnect();
                                    }
                                    return 200;
                                case 401:
                                    z.d();
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e6) {
                                        }
                                    }
                                    if (a2 != null) {
                                        a2.disconnect();
                                    }
                                    return 401;
                                case LeMessageIds.MSG_DLNA_LIVE_PROTOCOL /*404*/:
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e7) {
                                        }
                                    }
                                    if (a2 != null) {
                                        a2.disconnect();
                                    }
                                    return LeMessageIds.MSG_DLNA_LIVE_PROTOCOL;
                                case 429:
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e8) {
                                        }
                                    }
                                    if (a2 != null) {
                                        a2.disconnect();
                                    }
                                    return 429;
                                default:
                                    if (responseCode / 100 == 5) {
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (Exception e9) {
                                            }
                                        }
                                        if (a2 != null) {
                                            a2.disconnect();
                                        }
                                        return LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA;
                                    }
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e10) {
                                        }
                                    }
                                    if (a2 != null) {
                                        a2.disconnect();
                                    }
                                    return -5;
                            }
                        } catch (ProtocolException e11) {
                            e4 = e11;
                            try {
                                new StringBuilder(z[23]).append(e4.getMessage());
                                z.e();
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Exception e12) {
                                    }
                                }
                                if (a2 != null) {
                                    a2.disconnect();
                                }
                                return -1;
                            } catch (Throwable th2) {
                                th = th2;
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Exception e13) {
                                    }
                                }
                                if (a2 != null) {
                                    a2.disconnect();
                                }
                                throw th;
                            }
                        } catch (IOException e14) {
                            e2 = e14;
                            new StringBuilder(z[29]).append(e2.getMessage());
                            z.e();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e15) {
                                }
                            }
                            if (a2 != null) {
                                a2.disconnect();
                            }
                            return -1;
                        } catch (AssertionError e16) {
                            e3 = e16;
                            new StringBuilder(z[32]).append(e3.toString());
                            z.e();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e17) {
                                }
                            }
                            if (a2 != null) {
                                a2.disconnect();
                            }
                            return -1;
                        }
                    } catch (ProtocolException e18) {
                        e4 = e18;
                        inputStream = null;
                        new StringBuilder(z[23]).append(e4.getMessage());
                        z.e();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (a2 != null) {
                            a2.disconnect();
                        }
                        return -1;
                    } catch (IOException e19) {
                        e2 = e19;
                        inputStream = null;
                        new StringBuilder(z[29]).append(e2.getMessage());
                        z.e();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (a2 != null) {
                            a2.disconnect();
                        }
                        return -1;
                    } catch (AssertionError e20) {
                        e3 = e20;
                        inputStream = null;
                        new StringBuilder(z[32]).append(e3.toString());
                        z.e();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (a2 != null) {
                            a2.disconnect();
                        }
                        return -1;
                    } catch (Throwable th3) {
                        th = th3;
                        inputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (a2 != null) {
                            a2.disconnect();
                        }
                        throw th;
                    }
                } catch (UnsupportedEncodingException e21) {
                    e21.printStackTrace();
                    return -4;
                } catch (IOException e22) {
                    e22.printStackTrace();
                    return -4;
                }
            }
            z.b();
            return -3;
        }
        z.e();
        return -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r10, int r11, long r12) {
        /*
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 34;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));
        if (r0 < 0) goto L_0x001e;
    L_0x0017:
        r0 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
        r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x0020;
    L_0x001e:
        r12 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
    L_0x0020:
        r3 = 0;
        r1 = 0;
        r0 = 0;
        r2 = 0;
        r4 = r0;
    L_0x0025:
        r0 = new java.net.URL;	 Catch:{ SSLPeerUnverifiedException -> 0x0270, Exception -> 0x0269, AssertionError -> 0x0260 }
        r0.<init>(r10);	 Catch:{ SSLPeerUnverifiedException -> 0x0270, Exception -> 0x0269, AssertionError -> 0x0260 }
        r0 = r0.openConnection();	 Catch:{ SSLPeerUnverifiedException -> 0x0270, Exception -> 0x0269, AssertionError -> 0x0260 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SSLPeerUnverifiedException -> 0x0270, Exception -> 0x0269, AssertionError -> 0x0260 }
        r3 = z;	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r5 = 6;
        r3 = r3[r5];	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r5 = z;	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r6 = 1;
        r5 = r5[r6];	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r0.addRequestProperty(r3, r5);	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r3 = z;	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r5 = 3;
        r3 = r3[r5];	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r5 = z;	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r6 = 2;
        r5 = r5[r6];	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r0.setRequestProperty(r3, r5);	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r3 = r0.getInputStream();	 Catch:{ SSLPeerUnverifiedException -> 0x0275, Exception -> 0x026d, AssertionError -> 0x0263 }
        r5 = r0.getResponseCode();	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r5 != r1) goto L_0x008a;
    L_0x0056:
        if (r3 == 0) goto L_0x008a;
    L_0x0058:
        r1 = new java.lang.String;	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        r6 = cn.jpush.android.util.ah.a(r3);	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        r7 = z;	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        r8 = 11;
        r7 = r7[r8];	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        r1.<init>(r6, r7);	 Catch:{ SSLPeerUnverifiedException -> 0x00be, Exception -> 0x00e3, AssertionError -> 0x00fa, all -> 0x0125 }
        if (r3 == 0) goto L_0x006c;
    L_0x0069:
        r3.close();	 Catch:{ IOException -> 0x0085 }
    L_0x006c:
        if (r0 == 0) goto L_0x0071;
    L_0x006e:
        r0.disconnect();
    L_0x0071:
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r5 < r0) goto L_0x014a;
    L_0x0075:
        r0 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r5 >= r0) goto L_0x014a;
    L_0x0079:
        if (r1 != 0) goto L_0x027e;
    L_0x007b:
        cn.jpush.android.util.z.g();	 Catch:{ Exception -> 0x013e }
        r0 = z;	 Catch:{ Exception -> 0x013e }
        r1 = 18;
        r0 = r0[r1];	 Catch:{ Exception -> 0x013e }
    L_0x0084:
        return r0;
    L_0x0085:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x006c;
    L_0x008a:
        r4 = r4 + 1;
        r1 = 5;
        if (r4 < r1) goto L_0x00a6;
    L_0x008f:
        r1 = z;
        r2 = 17;
        r1 = r1[r2];
        if (r3 == 0) goto L_0x009a;
    L_0x0097:
        r3.close();	 Catch:{ IOException -> 0x00a1 }
    L_0x009a:
        if (r0 == 0) goto L_0x009f;
    L_0x009c:
        r0.disconnect();
    L_0x009f:
        r0 = r1;
        goto L_0x0084;
    L_0x00a1:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x009a;
    L_0x00a6:
        if (r3 == 0) goto L_0x00ab;
    L_0x00a8:
        r3.close();	 Catch:{ IOException -> 0x00b9 }
    L_0x00ab:
        if (r0 == 0) goto L_0x028d;
    L_0x00ad:
        r0.disconnect();
        r1 = r3;
        r3 = r0;
        r0 = r4;
    L_0x00b3:
        java.lang.Thread.sleep(r12);	 Catch:{ InterruptedException -> 0x013a }
        r4 = r0;
        goto L_0x0025;
    L_0x00b9:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00ab;
    L_0x00be:
        r1 = move-exception;
        r1 = r0;
        r0 = r3;
    L_0x00c1:
        r3 = z;	 Catch:{ all -> 0x0252 }
        r5 = 8;
        r3 = r3[r5];	 Catch:{ all -> 0x0252 }
        r5 = z;	 Catch:{ all -> 0x0252 }
        r6 = 9;
        r5 = r5[r6];	 Catch:{ all -> 0x0252 }
        cn.jpush.android.util.z.e(r3, r5);	 Catch:{ all -> 0x0252 }
        if (r0 == 0) goto L_0x00d5;
    L_0x00d2:
        r0.close();	 Catch:{ IOException -> 0x00de }
    L_0x00d5:
        if (r1 == 0) goto L_0x0288;
    L_0x00d7:
        r1.disconnect();
        r3 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x00b3;
    L_0x00de:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x00d5;
    L_0x00e3:
        r1 = move-exception;
        r1 = r3;
    L_0x00e5:
        cn.jpush.android.util.z.g();	 Catch:{ all -> 0x0258 }
        if (r1 == 0) goto L_0x00ed;
    L_0x00ea:
        r1.close();	 Catch:{ IOException -> 0x00f5 }
    L_0x00ed:
        if (r0 == 0) goto L_0x0284;
    L_0x00ef:
        r0.disconnect();
        r3 = r0;
        r0 = r4;
        goto L_0x00b3;
    L_0x00f5:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x00ed;
    L_0x00fa:
        r1 = move-exception;
        r9 = r1;
        r1 = r3;
        r3 = r0;
        r0 = r9;
    L_0x00ff:
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x025d }
        r6 = z;	 Catch:{ all -> 0x025d }
        r7 = 32;
        r6 = r6[r7];	 Catch:{ all -> 0x025d }
        r5.<init>(r6);	 Catch:{ all -> 0x025d }
        r0 = r0.toString();	 Catch:{ all -> 0x025d }
        r5.append(r0);	 Catch:{ all -> 0x025d }
        cn.jpush.android.util.z.e();	 Catch:{ all -> 0x025d }
        if (r1 == 0) goto L_0x0119;
    L_0x0116:
        r1.close();	 Catch:{ IOException -> 0x0120 }
    L_0x0119:
        if (r3 == 0) goto L_0x0281;
    L_0x011b:
        r3.disconnect();
        r0 = r4;
        goto L_0x00b3;
    L_0x0120:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0119;
    L_0x0125:
        r1 = move-exception;
        r9 = r1;
        r1 = r3;
        r3 = r0;
        r0 = r9;
    L_0x012a:
        if (r1 == 0) goto L_0x012f;
    L_0x012c:
        r1.close();	 Catch:{ IOException -> 0x0135 }
    L_0x012f:
        if (r3 == 0) goto L_0x0134;
    L_0x0131:
        r3.disconnect();
    L_0x0134:
        throw r0;
    L_0x0135:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x012f;
    L_0x013a:
        r4 = move-exception;
        r4 = r0;
        goto L_0x0025;
    L_0x013e:
        r0 = move-exception;
        cn.jpush.android.util.z.f();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x014a:
        r0 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r5 < r0) goto L_0x01ff;
    L_0x014e:
        r0 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r5 >= r0) goto L_0x01ff;
    L_0x0152:
        r0 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r0 != r5) goto L_0x016f;
    L_0x0156:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 33;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 16;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x016f:
        r0 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r0 != r5) goto L_0x018c;
    L_0x0173:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 35;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x018c:
        r0 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        if (r0 != r5) goto L_0x01a8;
    L_0x0190:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 5;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x01a8:
        r0 = 406; // 0x196 float:5.69E-43 double:2.006E-321;
        if (r0 != r5) goto L_0x01c5;
    L_0x01ac:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 39;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x01c5:
        r0 = 408; // 0x198 float:5.72E-43 double:2.016E-321;
        if (r0 != r5) goto L_0x01e2;
    L_0x01c9:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 38;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x01e2:
        r0 = 409; // 0x199 float:5.73E-43 double:2.02E-321;
        if (r0 != r5) goto L_0x027b;
    L_0x01e6:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 37;
        r1 = r1[r2];
        r0.<init>(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x01ff:
        r0 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r5 < r0) goto L_0x022d;
    L_0x0203:
        r0 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
        if (r5 >= r0) goto L_0x022d;
    L_0x0207:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 36;
        r1 = r1[r2];
        r0.<init>(r1);
        r0 = r0.append(r5);
        r1 = z;
        r2 = 7;
        r1 = r1[r2];
        r0 = r0.append(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x022d:
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r2 = 4;
        r1 = r1[r2];
        r0.<init>(r1);
        r0 = r0.append(r5);
        r1 = z;
        r2 = 7;
        r1 = r1[r2];
        r0 = r0.append(r1);
        r0.append(r10);
        cn.jpush.android.util.z.b();
        r0 = z;
        r1 = 18;
        r0 = r0[r1];
        goto L_0x0084;
    L_0x0252:
        r2 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r2;
        goto L_0x012a;
    L_0x0258:
        r2 = move-exception;
        r3 = r0;
        r0 = r2;
        goto L_0x012a;
    L_0x025d:
        r0 = move-exception;
        goto L_0x012a;
    L_0x0260:
        r0 = move-exception;
        goto L_0x00ff;
    L_0x0263:
        r3 = move-exception;
        r9 = r3;
        r3 = r0;
        r0 = r9;
        goto L_0x00ff;
    L_0x0269:
        r0 = move-exception;
        r0 = r3;
        goto L_0x00e5;
    L_0x026d:
        r3 = move-exception;
        goto L_0x00e5;
    L_0x0270:
        r0 = move-exception;
        r0 = r1;
        r1 = r3;
        goto L_0x00c1;
    L_0x0275:
        r3 = move-exception;
        r9 = r1;
        r1 = r0;
        r0 = r9;
        goto L_0x00c1;
    L_0x027b:
        r0 = r2;
        goto L_0x0084;
    L_0x027e:
        r0 = r1;
        goto L_0x0084;
    L_0x0281:
        r0 = r4;
        goto L_0x00b3;
    L_0x0284:
        r3 = r0;
        r0 = r4;
        goto L_0x00b3;
    L_0x0288:
        r3 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x00b3;
    L_0x028d:
        r1 = r3;
        r3 = r0;
        r0 = r4;
        goto L_0x00b3;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.p.a(java.lang.String, int, long):java.lang.String");
    }

    private static HttpURLConnection a(Context context, String str) {
        try {
            URL url = new URL(str);
            if (context.getPackageManager().checkPermission(z[43], context.getPackageName()) == 0) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(z[44])).getActiveNetworkInfo();
                if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (extraInfo != null && (extraInfo.equals(z[40]) || extraInfo.equals(z[41]) || extraInfo.equals(z[42]))) {
                        return (HttpURLConnection) url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress(z[45], 80)));
                    }
                }
            }
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void a(HttpURLConnection httpURLConnection, boolean z) {
        if (httpURLConnection == null) {
            z.e();
        }
        if (z) {
            try {
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod(z[15]);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty(z[12], z[13]);
        httpURLConnection.setRequestProperty(z[14], z[11]);
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
    }

    public static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.equals(z[18]) || str.equals(z[16]) || str.equals(z[17]) || str.equals(z[19]);
    }

    public static byte[] a(String str, int i, long j, int i2) {
        byte[] bArr = null;
        for (int i3 = 0; i3 < 4; i3++) {
            bArr = b(str, 5, 5000);
            if (bArr != null) {
                break;
            }
        }
        return bArr;
    }

    private static byte[] b(String str, int i, long j) {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2;
        InputStream inputStream;
        int i2;
        Throwable th;
        int responseCode;
        if (i <= 0 || i > 10) {
            i = 1;
        }
        if (j < 200 || j > 60000) {
            j = SPConstant.DELAY_BUFFER_DURATION;
        }
        new StringBuilder(z[10]).append(str);
        z.b();
        int i3 = 0;
        InputStream inputStream2 = null;
        HttpURLConnection httpURLConnection3 = null;
        while (true) {
            InputStream inputStream3;
            try {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection.setRequestProperty(z[3], z[2]);
                    httpURLConnection.addRequestProperty(z[6], z[1]);
                    inputStream3 = httpURLConnection.getInputStream();
                } catch (SSLPeerUnverifiedException e) {
                    InputStream inputStream4 = inputStream2;
                    httpURLConnection2 = httpURLConnection;
                    inputStream = inputStream4;
                    try {
                        z.e(z[8], z[9]);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                            httpURLConnection3 = httpURLConnection2;
                            inputStream2 = inputStream;
                        } else {
                            httpURLConnection3 = httpURLConnection2;
                            inputStream2 = inputStream;
                        }
                        i2 = i3 + 1;
                        if (i2 >= i) {
                            try {
                                Thread.sleep(((long) i2) * j);
                                i3 = i2;
                            } catch (InterruptedException e3) {
                                i3 = i2;
                            }
                        } else {
                            httpURLConnection3.disconnect();
                            return null;
                        }
                    } catch (Throwable th2) {
                        httpURLConnection3 = httpURLConnection2;
                        inputStream2 = inputStream;
                        th = th2;
                    }
                } catch (Exception e4) {
                    try {
                        z.g();
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e5) {
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                            httpURLConnection3 = httpURLConnection;
                        } else {
                            httpURLConnection3 = httpURLConnection;
                        }
                        i2 = i3 + 1;
                        if (i2 >= i) {
                            httpURLConnection3.disconnect();
                            return null;
                        }
                        Thread.sleep(((long) i2) * j);
                        i3 = i2;
                    } catch (Throwable th22) {
                        httpURLConnection3 = httpURLConnection;
                        th = th22;
                    }
                }
                try {
                    byte[] a = ah.a(inputStream3);
                    responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200 && inputStream3 != null) {
                        break;
                    }
                    if (inputStream3 != null) {
                        try {
                            inputStream3.close();
                        } catch (IOException e6) {
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                        inputStream2 = inputStream3;
                        httpURLConnection3 = httpURLConnection;
                    } else {
                        inputStream2 = inputStream3;
                        httpURLConnection3 = httpURLConnection;
                    }
                    i2 = i3 + 1;
                    if (i2 >= i) {
                        httpURLConnection3.disconnect();
                        return null;
                    }
                    Thread.sleep(((long) i2) * j);
                    i3 = i2;
                } catch (SSLPeerUnverifiedException e7) {
                    httpURLConnection2 = httpURLConnection;
                    inputStream = inputStream3;
                    z.e(z[8], z[9]);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                        httpURLConnection3 = httpURLConnection2;
                        inputStream2 = inputStream;
                    } else {
                        httpURLConnection3 = httpURLConnection2;
                        inputStream2 = inputStream;
                    }
                    i2 = i3 + 1;
                    if (i2 >= i) {
                        Thread.sleep(((long) i2) * j);
                        i3 = i2;
                    } else {
                        httpURLConnection3.disconnect();
                        return null;
                    }
                } catch (Exception e8) {
                    inputStream2 = inputStream3;
                    z.g();
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                        httpURLConnection3 = httpURLConnection;
                    } else {
                        httpURLConnection3 = httpURLConnection;
                    }
                    i2 = i3 + 1;
                    if (i2 >= i) {
                        httpURLConnection3.disconnect();
                        return null;
                    }
                    Thread.sleep(((long) i2) * j);
                    i3 = i2;
                } catch (Throwable th222) {
                    inputStream2 = inputStream3;
                    httpURLConnection3 = httpURLConnection;
                    th = th222;
                }
            } catch (SSLPeerUnverifiedException e9) {
                inputStream = inputStream2;
                httpURLConnection2 = httpURLConnection3;
                z.e(z[8], z[9]);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection2 != null) {
                    httpURLConnection3 = httpURLConnection2;
                    inputStream2 = inputStream;
                } else {
                    httpURLConnection2.disconnect();
                    httpURLConnection3 = httpURLConnection2;
                    inputStream2 = inputStream;
                }
                i2 = i3 + 1;
                if (i2 >= i) {
                    Thread.sleep(((long) i2) * j);
                    i3 = i2;
                } else {
                    httpURLConnection3.disconnect();
                    return null;
                }
            } catch (Exception e10) {
                httpURLConnection = httpURLConnection3;
                z.g();
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection3 = httpURLConnection;
                } else {
                    httpURLConnection.disconnect();
                    httpURLConnection3 = httpURLConnection;
                }
                i2 = i3 + 1;
                if (i2 >= i) {
                    httpURLConnection3.disconnect();
                    return null;
                }
                Thread.sleep(((long) i2) * j);
                i3 = i2;
            } catch (Throwable th3) {
                th = th3;
            }
        }
        i3 = httpURLConnection.getContentLength();
        if (inputStream3 != null) {
            try {
                inputStream3.close();
            } catch (IOException e11) {
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        if (200 == responseCode) {
            if (i3 == 0) {
                try {
                    z.b();
                    return null;
                } catch (Exception e12) {
                    z.g();
                    return null;
                }
            } else if (a.length >= i3) {
                return a;
            } else {
                z.b();
                return null;
            }
        } else if (400 == responseCode) {
            new StringBuilder(z[0]).append(str);
            z.b();
            return null;
        } else if (LeMessageIds.MSG_DLNA_LIVE_PROTOCOL == responseCode) {
            new StringBuilder(z[5]).append(str);
            z.b();
            return null;
        } else {
            new StringBuilder(z[4]).append(responseCode).append(z[7]).append(str);
            z.b();
            return null;
        }
        if (httpURLConnection3 != null) {
            httpURLConnection3.disconnect();
        }
        throw th;
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException e13) {
            }
        }
        if (httpURLConnection3 != null) {
            httpURLConnection3.disconnect();
        }
        throw th;
    }
}
