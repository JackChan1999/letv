package cn.jpush.android.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import cn.jpush.android.a;
import cn.jpush.android.data.c;
import cn.jpush.android.data.d;
import cn.jpush.android.e;
import cn.jpush.android.service.PushProtocol;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.ae;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.aj;
import cn.jpush.b.a.a.h;
import cn.jpush.b.a.a.j;
import cn.jpush.b.a.a.m;
import com.google.gson.jpush.ab;
import com.google.gson.jpush.ac;
import com.google.gson.jpush.af;
import com.google.gson.jpush.w;
import com.google.gson.jpush.z;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONObject;

public final class f {
    private static Queue<d> a = new ConcurrentLinkedQueue();
    private static ab b = new ab();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 27;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "S@-e)NF9";
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
            case 0: goto L_0x0159;
            case 1: goto L_0x015d;
            case 2: goto L_0x0161;
            case 3: goto L_0x0165;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 76;
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
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005e;
            case 4: goto L_0x0066;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x007f;
            case 8: goto L_0x0089;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00aa;
            case 12: goto L_0x00b5;
            case 13: goto L_0x00c0;
            case 14: goto L_0x00cb;
            case 15: goto L_0x00d6;
            case 16: goto L_0x00e2;
            case 17: goto L_0x00ed;
            case 18: goto L_0x00f8;
            case 19: goto L_0x0103;
            case 20: goto L_0x010e;
            case 21: goto L_0x011a;
            case 22: goto L_0x0125;
            case 23: goto L_0x0130;
            case 24: goto L_0x013b;
            case 25: goto L_0x0146;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0017C9vzA\u00128'zC\u0012d\".\u0011Clu(A\u0011h&.\u0012Fjsx\u0010D";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "uK9h<EF(u(\u001a\u0005)~'NJ+~lPP/xlMV;08YU90a";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "M@/c-G@";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "pP/x\u0001EV/q+Eu./EV/>";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005e:
        r3[r2] = r1;
        r2 = 5;
        r1 = "S@2t)Rl8";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0066:
        r3[r2] = r1;
        r2 = 6;
        r1 = "TD;q ID/O?ET5t";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "AF(y#N\u001f.u/EL*u(pP/x\u0001EV/q+E\u00051c+iA|-l";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "CKrz<UV4>-NA.%D\u000b\u0012_\u0018ic\u0015S\rtl\u0013^\u0013t|\fU";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u000eU9b!IV/y#N\u000b\u0016@\u0019sm\u0003]\tsv\u001dW\t";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "dP,|%CD(u(\u0000H/wb\u0000b5f)\u0000P,0<RJ?u?SL2wl\r\u0005";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "CKrz<UV4>-NA.%D\u000b5~8EK(>\u0018ab\u0003Q\u0000id\u000fO\u000fai\u0010R\rcn";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "m@/c-G@|V%EI8cl\r\u0005=`<iAf";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00aa:
        r3[r2] = r1;
        r2 = 13;
        r1 = "TD;q ID/O)RW3b/OA9";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b5:
        r3[r2] = r1;
        r2 = 14;
        r1 = "PW3s)SV\u001eq?IF\u0019~8IQ%08YU9*";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c0:
        r3[r2] = r1;
        r2 = 15;
        r1 = "CKrz<UV4>-NA.%D\u000b5~8EK(>\u0002oq\u0015V\u0005cd\bY\u0003nz\u000eU\u000fel\nU\bu\u000e_\u0014y";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\f\u00051c+cJ2d)NQf";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d6:
        r3[r2] = r1;
        r2 = 17;
        r1 = "tL1ulTJ|`>OF9c?\u0000W9s)IS9tlMV;>";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e2:
        r3[r2] = r1;
        r2 = 18;
        r1 = "TD;q ID/0!SB\u001f\"T@2dv";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ed:
        r3[r2] = r1;
        r2 = 19;
        r1 = "CJ8u";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f8:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\f\u0005/u\"D@.Y(\u001a";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0103:
        r3[r2] = r1;
        r2 = 21;
        r1 = "AU,Y(";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010e:
        r3[r2] = r1;
        r2 = 22;
        r1 = "sP?s)EA|d#\u0000W9`#RQ|b)C@5f)D\u0005q0";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011a:
        r3[r2] = r1;
        r2 = 23;
        r1 = "PD.c)nJ.}-L\u0005q0lMV;Y(\u001a";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0125:
        r3[r2] = r1;
        r2 = 24;
        r1 = "MV;D5P@|-l";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0130:
        r3[r2] = r1;
        r2 = 25;
        r1 = "MV;O%D";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013b:
        r3[r2] = r1;
        r2 = 26;
        r1 = "\f\u00051c+iA|-l";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0146:
        r3[r2] = r1;
        z = r4;
        r0 = new java.util.concurrent.ConcurrentLinkedQueue;
        r0.<init>();
        a = r0;
        r0 = new com.google.gson.jpush.ab;
        r0.<init>();
        b = r0;
        return;
    L_0x0159:
        r9 = 32;
        goto L_0x0020;
    L_0x015d:
        r9 = 37;
        goto L_0x0020;
    L_0x0161:
        r9 = 92;
        goto L_0x0020;
    L_0x0165:
        r9 = 16;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.f.<clinit>():void");
    }

    private static long a(String str) {
        try {
            w a = b.a(str);
            if (a instanceof z) {
                a = a.h().b(z[0]);
                if (a != null && (a instanceof ac)) {
                    return a.e();
                }
                cn.jpush.android.util.z.e();
                return 0;
            }
            cn.jpush.android.util.z.e();
            return 0;
        } catch (af e) {
            cn.jpush.android.util.z.e();
            return 0;
        }
    }

    public static void a(Context context, Handler handler, long j, h hVar) {
        j jVar = (j) hVar;
        int a = jVar.a();
        long g = jVar.g();
        if (PushProtocol.MsgResponse(j, 0, a.t(), (byte) a, g, jVar.e().a().longValue(), a.i()) != 0) {
            cn.jpush.android.util.z.b();
        } else {
            new StringBuilder(z[22]).append(g);
            cn.jpush.android.util.z.b();
        }
        long g2 = jVar.g();
        int a2 = jVar.a();
        String h = jVar.h();
        new StringBuilder(z[24]).append(a2).append(z[26]).append(g2);
        cn.jpush.android.util.z.b();
        LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(h));
        try {
            String readLine = lineNumberReader.readLine();
            if (readLine == null) {
                cn.jpush.android.util.z.e();
                return;
            }
            String readLine2 = lineNumberReader.readLine();
            if (readLine2 == null) {
                cn.jpush.android.util.z.e();
                return;
            }
            int length = (readLine.length() + readLine2.length()) + 2;
            if (h.length() > length + 1) {
                h = h.substring(length);
            } else {
                cn.jpush.android.util.z.b();
                h = "";
            }
            new StringBuilder(z[12]).append(readLine).append(z[20]).append(readLine2).append(z[16]).append(h);
            cn.jpush.android.util.z.a();
            Intent intent;
            switch (a2) {
                case 0:
                case 2:
                    try {
                        new StringBuilder(z[23]).append(g2);
                        cn.jpush.android.util.z.b();
                        if (ServiceInterface.e(context)) {
                            cn.jpush.android.util.z.c();
                            return;
                        }
                        aj ajVar = new aj(z[4], z[17]);
                        if (TextUtils.isEmpty(readLine) || TextUtils.isEmpty(readLine2)) {
                            cn.jpush.android.util.z.d();
                        } else if (TextUtils.isEmpty(h)) {
                            cn.jpush.android.util.z.e();
                        } else {
                            new StringBuilder(z[7]).append(g2);
                            cn.jpush.android.util.z.b();
                            c a3 = d.a(context, h, readLine, readLine2, g2);
                            if (a3 != null) {
                                d dVar = new d(a3, a3);
                                if (a.contains(dVar)) {
                                    new StringBuilder(z[10]).append(dVar);
                                    cn.jpush.android.util.z.e();
                                } else {
                                    if (a.size() >= 200) {
                                        a.poll();
                                    }
                                    a.offer(dVar);
                                    a2 = 0;
                                    if (readLine2.equalsIgnoreCase(z[1])) {
                                        d.a(context, (cn.jpush.android.data.a) a3);
                                    } else if (a3.e) {
                                        a2 = 1;
                                        if (a3.b == 4) {
                                            a2 = 3;
                                        }
                                    } else {
                                        a2 = 2;
                                    }
                                    String str = g2;
                                    new StringBuilder(z[14]).append(a2);
                                    cn.jpush.android.util.z.b();
                                    if ((a2 & 1) != 0) {
                                        cn.jpush.android.util.z.b();
                                        intent = new Intent(z[15]);
                                        intent.putExtra(z[5], readLine2);
                                        intent.putExtra(z[21], readLine);
                                        intent.putExtra(z[3], h);
                                        intent.putExtra(z[25], str);
                                        intent.putExtra(z[8], a3.g);
                                        intent.addCategory(readLine);
                                        context.sendOrderedBroadcast(intent, readLine + z[9]);
                                    } else {
                                        cn.jpush.android.util.z.b();
                                        if (!(ai.a(a3.i) && ai.a(a3.l))) {
                                            cn.jpush.android.util.a.b(context, a3);
                                        }
                                    }
                                }
                            }
                        }
                        ajVar.a();
                        return;
                    } catch (Exception e) {
                        cn.jpush.android.util.z.i();
                        return;
                    }
                case 3:
                case 5:
                case 6:
                case 21:
                case 22:
                    return;
                case 4:
                    g.a(context);
                    return;
                case 9:
                    g.b(context);
                    return;
                case 20:
                    Message.obtain(handler, 1009, new m(Long.valueOf(a(h)).longValue(), jVar.e().b(), 0, null, 0)).sendToTarget();
                    try {
                        JSONObject jSONObject = new JSONObject(h);
                        int optInt = jSONObject.optInt(z[19], cn.jpush.android.api.d.i);
                        long optLong = jSONObject.optLong(z[0]);
                        intent = new Intent();
                        intent.addCategory(e.c);
                        intent.setAction(z[11]);
                        intent.putExtra(z[13], optInt);
                        intent.putExtra(z[6], optLong);
                        context.sendBroadcast(intent);
                        return;
                    } catch (Exception e2) {
                        new StringBuilder(z[18]).append(h);
                        cn.jpush.android.util.z.d();
                        return;
                    }
                case MotionEventCompat.AXIS_GENERIC_13 /*44*/:
                    ae.a(context);
                    return;
                default:
                    new StringBuilder(z[2]).append(a2);
                    cn.jpush.android.util.z.b();
                    return;
            }
        } catch (IOException e3) {
            cn.jpush.android.util.z.i();
        }
    }
}
