package cn.jpush.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.data.b;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.e;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.m;
import cn.jpush.android.util.z;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.core.messagebus.config.LeMessageIds;
import java.io.File;

public class PushReceiver extends BroadcastReceiver {
    public static c a = null;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 52;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "MoQ>\n/lo?\u001c5C`(\u001ciJo\u001b\u00182VdcP";
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
            case 0: goto L_0x025f;
            case 1: goto L_0x0263;
            case 2: goto L_0x0267;
            case 3: goto L_0x026a;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
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
            case 33: goto L_0x019b;
            case 34: goto L_0x01a6;
            case 35: goto L_0x01b1;
            case 36: goto L_0x01bc;
            case 37: goto L_0x01c7;
            case 38: goto L_0x01d2;
            case 39: goto L_0x01dd;
            case 40: goto L_0x01e8;
            case 41: goto L_0x01f3;
            case 42: goto L_0x01fe;
            case 43: goto L_0x0209;
            case 44: goto L_0x0214;
            case 45: goto L_0x021f;
            case 46: goto L_0x022b;
            case 47: goto L_0x0236;
            case 48: goto L_0x0241;
            case 49: goto L_0x024c;
            case 50: goto L_0x0257;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = ")Ju\"\u001f.F`\"\u0016)zu2\t\"";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bh%\r\"Kue7\bqH\r0\u0004dU\u00026\tzH\u0005*\u0013dM\u0007&\u0004iH\b2\u0002a";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "M皡\u000b$\u0017\u0017Dt8\u001co\f\u000b";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "(KS.\u001a\"Lw.Yj\u0005";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bh%\r\"Kue7\bqH\r0\u0004dU\u00026\tzN\u001b<\t`E";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "&Ke9\u0016.A/\"\u00173@o?W&Fu\"\u0016)\u000bT\u0018<\u0015zQ\u0019<\u0014`O\u001f";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0015@b.\u00101@ek\u00172Imk\u0010)Qd%\rgGs$\u0018#F`8\ri\u0005F\"\u000f\"\u0005t;Y7Wn(\u001c4Vh%\u001ei";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bO\u0004-\u000ecH\b8\u0013lN\u0005&\u0013|Q\u000e";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0014@o/Y%Wn*\u001d$Dr?Y3J!*\t7\u001f!";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\rut8\u0011g廟讯隍扩gvE\u0000Y斱劅下纔诘交砤令讏佉揯逤敉柗ほg/\u000b梋洲剷圍\u000b";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "i\u0005E$Y)Ju#\u0010)B/";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0017Pr#+\"Fd\"\u000f\"W";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0004Jo%\u001c$Qh$\u0017gVu*\r\"\u0005b#\u0018)Bd/Y3J!fY";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "&Ke9\u0016.A/\"\u00173@o?W&Fu\"\u0016)\u000bW\u0002<\u0010";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "7Db \u0018 @;";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\u0000@uk\u000e5Jo,Y#Du*Y4Qs\"\u0017 \u0005g9\u0016*\u0005h%\r\"KuqY";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = ")JB$\u0017)@b?\u00101Lu2";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bL\u0018>\u0018lE";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bh%\r\"Kue7\bqH\r0\u0004dU\u00026\tzS\u000e:\u0002lW\u000e=\u0018uS\u0004!\u001e";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "*Vf\u0014\u0010#";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "&Uq\u0002\u001d";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "#@c>\u001e\u0018Kn?\u0010!Lb*\r.Jo";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        r2 = 23;
        r1 = "\tJ!\n:\u0013lN\u0005&\tjU\u0002?\u000ef@\u001f0\bk^\u0004)\u0002kD\u000fY#@g\"\u0017\"A!\"\u0017gH`%\u0010!@r?UgJq.\u0017gQi.Y#@g*\f+Q!&\u0018.K!*\u001a3Lw\"\r>";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0121:
        r3[r2] = r1;
        r2 = 24;
        r1 = "&Uq";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012c:
        r3[r2] = r1;
        r2 = 25;
        r1 = "M皡\u000b$\u0017\u0015@r>\u0014\"\r(A\u0016)u`>\n\"\r(A";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0137:
        r3[r2] = r1;
        r2 = 26;
        r1 = "4Qs\u0005->UdkDg";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0142:
        r3[r2] = r1;
        r2 = 27;
        r1 = "\u0015@b.\u00101@ek\u0017(\u0005`(\r.Jok\u0010)Qd%\rgGs$\u0018#F`8\ri\u0005F\"\u000f\"\u0005t;Y7Wn(\u001c4Vh%\u001ei";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014d:
        r3[r2] = r1;
        r2 = 28;
        r1 = "3\\q.";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0158:
        r3[r2] = r1;
        r2 = 29;
        r1 = "M皡\u000b$\u0017\u0015@r>\u0014\"\r(A";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0163:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\u0006fU\u00026\tzO\u0004-\u000ecH\b8\u0013lN\u0005&\u0015`B\u000e0\u0011`E\u0014)\u0015jY\u0012Yg\u0005o$\r.Ch(\u00183Ln%&3\\q.Yz\u0005";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x016e:
        r3[r2] = r1;
        r2 = 31;
        r1 = "M/札掛祃厭圍弁厚截卂犓怀乀冃珷\t叐幈截卂丨伛冱珉i";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0179:
        r3[r2] = r1;
        r2 = 32;
        r1 = "(Qi.\u000bgKd?\u000e(Wjk\n3Du.Yj\u0005";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0184:
        r3[r2] = r1;
        r2 = 33;
        r1 = "MoQ>\n/lo?\u001c5C`(\u001ciJo\u0019\u001c4Pl.Qn/K\u001b\f4MH%\r\"Wg*\u001a\"\u000bn%)&Pr.Qn";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x018f:
        r3[r2] = r1;
        r2 = 34;
        r1 = "旾泰丬泪杰豄甍";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019b:
        r3[r2] = r1;
        r2 = 35;
        r1 = "4@o/\u001c5le";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a6:
        r3[r2] = r1;
        r2 = 36;
        r1 = "$K/!\t2Vie\u0018)As$\u0010#\u000bh%\r\"Kue7\bqH\r0\u0004dU\u00026\tzN\u001b<\t`E\u0014)\u0015jY\u0012";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b1:
        r3[r2] = r1;
        r2 = 37;
        r1 = "*@r8\u0018 @";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01bc:
        r3[r2] = r1;
        r2 = 38;
        r1 = "&Uq'\u0010$Du\"\u0016)\nw%\u001diDo/\u000b(Lee\t&Fj*\u001e\"\b`9\u001a/Lw.";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01c7:
        r3[r2] = r1;
        r2 = 39;
        r1 = "iUd9\u0014.Vr\"\u0016)\u000bK\u001b,\u0014m^\u0006<\u0014v@\f<";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d2:
        r3[r2] = r1;
        r2 = 40;
        r1 = "3J`8\r\u0013@y?";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01dd:
        r3[r2] = r1;
        r2 = 41;
        r1 = "&Fu\"\u000f.Qx";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01e8:
        r3[r2] = r1;
        r2 = 42;
        r1 = "MoQ>\n/lo?\u001c5C`(\u001ciJo\u0019\u001c4Pl.Qn";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f3:
        r3[r2] = r1;
        r2 = 43;
        r1 = ")@u<\u00165NH%\u001f(";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x01fe:
        r3[r2] = r1;
        r2 = 44;
        r1 = "&Ke9\u0016.A/%\u001c3\u000bb$\u0017)\u000bB\u00047\t`B\u001f0\u0011lU\u0012&\u0004m@\u0005>\u0002";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x0209:
        r3[r2] = r1;
        r2 = 45;
        r1 = ")Ju\u0014\u00182Qn9\f)";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x0214:
        r3[r2] = r1;
        r2 = 46;
        r1 = "&Ke9\u0016.A/\"\u00173@o?W&Fu\"\u0016)\u000bC\u00046\u0013zB\u00044\u0017iD\u001f<\u0003";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x021f:
        r3[r2] = r1;
        r2 = 47;
        r1 = "}\u0005F.\rgKnk\u001d&Q`k\u001f5Jlk\u0010)Qd%\ri";
        r0 = 46;
        r3 = r4;
        goto L_0x0009;
    L_0x022b:
        r3[r2] = r1;
        r2 = 48;
        r1 = "%Je2";
        r0 = 47;
        r3 = r4;
        goto L_0x0009;
    L_0x0236:
        r3[r2] = r1;
        r2 = 49;
        r1 = "&Ke9\u0016.A/\"\u00173@o?W&Fu\"\u0016)\u000bQ\n:\fdF\u000e&\u0015`L\u0004/\u0002a";
        r0 = 48;
        r3 = r4;
        goto L_0x0009;
    L_0x0241:
        r3[r2] = r1;
        r2 = 50;
        r1 = "\u0000Juk\u0018)\u0005d&\t3\\!%\u00163Lg\"\u001a&Qh$\u0017k\u0005e$\u0017`Q!8\u0011(R!\"\rf";
        r0 = 49;
        r3 = r4;
        goto L_0x0009;
    L_0x024c:
        r3[r2] = r1;
        r2 = 51;
        r1 = "&Ke9\u0016.A/\"\u00173@o?W&Fu\"\u0016)\u000bQ\n:\fdF\u000e&\u0006aE\u000e=";
        r0 = 50;
        r3 = r4;
        goto L_0x0009;
    L_0x0257:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        return;
    L_0x025f:
        r9 = 71;
        goto L_0x0020;
    L_0x0263:
        r9 = 37;
        goto L_0x0020;
    L_0x0267:
        r9 = 1;
        goto L_0x0020;
    L_0x026a:
        r9 = 75;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.PushReceiver.<clinit>():void");
    }

    public void onReceive(Context context, Intent intent) {
        boolean z = false;
        if (intent == null) {
            z.d(z[12], z[7]);
            return;
        }
        try {
            String action = intent.getAction();
            z.b(z[12], new StringBuilder(z[4]).append(action).toString());
            if (!e.a(context.getApplicationContext())) {
                return;
            }
            if (action.equals(z[46])) {
                m.c(context);
                ServiceInterface.c(context, LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA);
            } else if (action.equals(z[51])) {
                r1 = intent.getDataString();
                if (r1 == null) {
                    z.d(z[12], action + z[47]);
                } else if (r1.trim().length() <= 8 || !r1.startsWith(z[15])) {
                    z.d(z[12], new StringBuilder(z[16]).append(r1).toString());
                } else {
                    String substring = r1.substring(8);
                    Object a = b.a(context, substring);
                    g.a(context, r.a(substring));
                    try {
                        if (!TextUtils.isEmpty(a)) {
                            String[] split = a.split(",");
                            if (split.length > 0) {
                                r1 = split[0];
                                g.a(r1, 1002, context);
                                r2 = split.length >= 2 ? split[1] : "";
                                action = "";
                                if (split.length >= 3) {
                                    action = split[2];
                                }
                                z.e();
                                if (!(r2.equals(z[45]) || a.a(context, substring, r2))) {
                                    z.b();
                                    g.a(r1, 1100, context);
                                }
                                if (ai.a(action)) {
                                    action = r1;
                                }
                                cn.jpush.android.api.m.a(context, action);
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                        z.e();
                    }
                }
            } else if (action.equals(z[49])) {
                r1 = intent.getDataString();
                if (r1 == null) {
                    z.d(z[12], action + z[47]);
                } else if (r1.trim().length() <= 8 || !r1.startsWith(z[15])) {
                    z.d(z[12], new StringBuilder(z[16]).append(r1).toString());
                } else {
                    g.a(context, r.b(r1.substring(8)));
                }
            } else if (action.equals(z[6])) {
                ServiceInterface.b(context);
            } else if (action.equals(z[2])) {
                c cVar = (c) intent.getSerializableExtra(z[48]);
                if (cVar != null && cVar.a()) {
                    g.a(cVar.c, VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_CHANGE_VOICE, context);
                    i iVar = (i) cVar;
                    r1 = new Intent();
                    r1.addFlags(268435456);
                    r1.setAction(z[14]);
                    r1.setDataAndType(Uri.fromFile(new File(iVar.P)), z[38]);
                    context.startActivity(r1);
                }
            } else if (action.startsWith(z[19])) {
                r0 = intent.getIntExtra(z[1], 0);
                new StringBuilder(z[30]).append(r0);
                z.b();
                if (r0 == 0) {
                    if (ServiceInterface.e(context)) {
                        z.c();
                        return;
                    } else if (!a.m(context)) {
                        z.c();
                        return;
                    }
                }
                action = intent.getStringExtra(z[37]);
                if (ai.a(action)) {
                    z.d(z[12], z[50]);
                    return;
                }
                cn.jpush.android.data.a a2 = d.a(context, action, intent.getStringExtra(z[21]), intent.getStringExtra(z[35]), intent.getStringExtra(z[20]));
                if (a2 == null) {
                    z.d();
                    return;
                }
                a2.h = true;
                d.a(context, a2);
                abortBroadcast();
            } else if (action.startsWith(z[36])) {
                if (intent.getBooleanExtra(z[22], false)) {
                    CharSequence stringExtra = intent.getStringExtra(z[41]);
                    int intExtra = intent.getIntExtra(z[28], -1);
                    View view;
                    TextView textView;
                    if (intExtra == -1) {
                        CharSequence stringExtra2 = intent.getStringExtra(z[40]);
                        Toast makeText = Toast.makeText(context, stringExtra, 1);
                        view = makeText.getView();
                        if (view instanceof LinearLayout) {
                            view = ((LinearLayout) view).getChildAt(0);
                            if (view instanceof TextView) {
                                textView = (TextView) view;
                                if (!ai.a(stringExtra2)) {
                                    textView.setText(stringExtra2);
                                }
                                textView.setTextSize(13.0f);
                            }
                        }
                        makeText.show();
                        return;
                    } else if (!ai.a(stringExtra)) {
                        String str = z[10];
                        r2 = z[29];
                        action = z[34];
                        r1 = z[42];
                        switch (intExtra) {
                            case 1:
                                r2 = z[3];
                                action = z[34];
                                r1 = z[0];
                                break;
                            case 2:
                                r2 = z[25];
                                action = z[34];
                                r1 = z[33];
                                break;
                        }
                        CharSequence stringBuilder = new StringBuilder();
                        stringBuilder.append(str).append(stringExtra).append(r2).append(action).append(r1).append(z[31]);
                        CharSequence spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
                        intExtra = str.length();
                        int length = stringExtra.length() + intExtra;
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-13527041), intExtra, length, 33);
                        intExtra = length + 2;
                        int length2 = (r2.length() + intExtra) - 2;
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-13527041), intExtra, length2, 33);
                        r0 = action.length() + length2;
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-13527041), r0, r1.length() + r0, 33);
                        Toast makeText2 = Toast.makeText(context, stringExtra, 1);
                        view = makeText2.getView();
                        if (view instanceof LinearLayout) {
                            view = ((LinearLayout) view).getChildAt(0);
                            if (view instanceof TextView) {
                                textView = (TextView) view;
                                if (spannableStringBuilder != null) {
                                    textView.setText(spannableStringBuilder);
                                }
                                textView.setTextSize(13.0f);
                            }
                        }
                        makeText2.show();
                        return;
                    } else {
                        return;
                    }
                }
                action = intent.getStringExtra(z[18]);
                if (!ai.a(action)) {
                    r1 = intent.getStringExtra(z[8]);
                    new StringBuilder(z[26]).append(r1);
                    z.b();
                    if (r1 != null && "1".equals(r1)) {
                        z = true;
                    }
                    if (true != z) {
                        g.a(action, 1000, context);
                    }
                }
                if (a.a(context, z[5], true)) {
                    r1 = new Intent(z[5]);
                    r1.putExtras(intent.getExtras());
                    action = intent.getStringExtra(z[24]);
                    if (ai.a(action)) {
                        action = context.getPackageName();
                    }
                    r1.addCategory(action);
                    new StringBuilder(z[9]).append(action);
                    z.c();
                    context.sendBroadcast(r1, action + z[39]);
                    return;
                }
                z.b(z[12], z[23]);
                a.e(context);
            } else if (action.equalsIgnoreCase(z[44])) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(z[43]);
                if (networkInfo == null) {
                    z.d();
                    return;
                }
                new StringBuilder(z[13]).append(networkInfo.toString());
                z.b();
                if (2 == networkInfo.getType() || 3 == networkInfo.getType()) {
                    z.b();
                    return;
                }
                boolean z2;
                if (intent.getBooleanExtra(z[17], false)) {
                    z.b();
                    b.b = false;
                    ServiceInterface.g(context);
                    z2 = false;
                } else if (State.CONNECTED == networkInfo.getState()) {
                    z.b();
                    ServiceInterface.f(context);
                    b.b = true;
                    if (DownloadService.a()) {
                        DownloadService.a(context);
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                } else if (State.DISCONNECTED == networkInfo.getState()) {
                    z.b();
                    b.b = false;
                    ServiceInterface.g(context);
                    z2 = false;
                } else {
                    new StringBuilder(z[32]).append(networkInfo.getState()).append(z[11]);
                    z2 = false;
                    z.b();
                }
                a.a(context, z2);
            }
        } catch (NullPointerException e2) {
            z.d(z[12], z[27]);
        }
    }
}
