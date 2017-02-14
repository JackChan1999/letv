package cn.jpush.android;

import android.content.Context;
import android.content.SharedPreferences;
import cn.jpush.android.helpers.b;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.f;
import cn.jpush.android.util.z;
import com.letv.mobile.lebox.heartbeat.HeartbeatService;
import java.util.Random;

public final class a extends f {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 72;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u000enfh;\u000fTbd?\u0003xdd*\u000foO`(\u001a`ux";
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
            case 0: goto L_0x0336;
            case 1: goto L_0x033a;
            case 2: goto L_0x033e;
            case 3: goto L_0x0342;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 88;
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
            case 33: goto L_0x019a;
            case 34: goto L_0x01a5;
            case 35: goto L_0x01b0;
            case 36: goto L_0x01bb;
            case 37: goto L_0x01c6;
            case 38: goto L_0x01d1;
            case 39: goto L_0x01dc;
            case 40: goto L_0x01e7;
            case 41: goto L_0x01f2;
            case 42: goto L_0x01fd;
            case 43: goto L_0x0208;
            case 44: goto L_0x0213;
            case 45: goto L_0x021e;
            case 46: goto L_0x0229;
            case 47: goto L_0x0234;
            case 48: goto L_0x023f;
            case 49: goto L_0x024a;
            case 50: goto L_0x0255;
            case 51: goto L_0x0260;
            case 52: goto L_0x026b;
            case 53: goto L_0x0276;
            case 54: goto L_0x0281;
            case 55: goto L_0x028c;
            case 56: goto L_0x0297;
            case 57: goto L_0x02a2;
            case 58: goto L_0x02ad;
            case 59: goto L_0x02b8;
            case 60: goto L_0x02c3;
            case 61: goto L_0x02ce;
            case 62: goto L_0x02d9;
            case 63: goto L_0x02e4;
            case 64: goto L_0x02ef;
            case 65: goto L_0x02fa;
            case 66: goto L_0x0305;
            case 67: goto L_0x0310;
            case 68: goto L_0x031b;
            case 69: goto L_0x0326;
            case 70: goto L_0x0331;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0006jcu\u0007\u0019bc^*\u000fzed+\u001eTdh5\u000f";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u000enfb1\u000fTye\u0007\rn~d*\u000bue";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0006jcu\u0007\u001ejwr\u0007\u0019~sb=\u0019xOu1\u0007n";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0019ndu1\u0004lOq-\u0019cOu1\u0007n";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u000enfh;\u000fTbd?\u0003xds9\u001ebo\u0007\u0003o";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\bjsj-\u001aTer=\u0018Tqe<\u0018";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u000enfh;\u000fT``+\u0019|s<";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\td~o=\tyo?5xd`,\u000f";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0006jcu\u0007\rde\u0007\u0019bc";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\u0019ndu1\u0004lOr1\u0006n~b=5{er05yl=";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u0006ic^*\u000f{s,5n~`:\u0006n";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0019q\u0007\u000fsub-\u001ent^7\u0004Tyl4\u0005lyo";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0006jcu\u0007\td~o=\tyn65iq=";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u000enfh;\u000fT}`1\u0004T}`;";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "\u000enfh;\u000fT}`1\u0004Tqo<\u0018dye\u0007\u0003o";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\u0019o{^.\u000fych7\u0004";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\u0019nbw1\tnc^4\u000b~~b0\u000foOu1\u0007n";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "\u000enfh;\u000fT}`1\u0004Tyl=\u0003";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "\u0000{er05xqw=5her,\u0005fOc-\u0003gtd*";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\u0006dwh65gb9\u0006Tdh5\u000f";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "\u0006dwh65xus.\u000fyOu1\u0007n";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "\u0006jcu\u0007\u000bgy`\u0007\u0019~sb=\u0019xOu1\u0007n";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        r2 = 23;
        r1 = "\u001a~ci\u0007\u001foye";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0121:
        r3[r2] = r1;
        r2 = 24;
        r1 = "\u0004ddh>\u0003hqu1\u0005eOd6\u000bi|d<";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012c:
        r3[r2] = r1;
        r2 = 25;
        r1 = "\u0006jcu+\u000fO`4\u0003jc";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0137:
        r3[r2] = r1;
        r2 = 26;
        r1 = "\u0003fuh";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0142:
        r3[r2] = r1;
        r2 = 27;
        r1 = "\u0006jcu\u0007\u0018n`n*\u001eTyo<\u000fs";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014d:
        r3[r2] = r1;
        r2 = 28;
        r1 = "\u000enfh;\u000fTeh<";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0158:
        r3[r2] = r1;
        r2 = 29;
        r1 = "\u0004ddh>\u0003hqu1\u0005eOo-\u0007";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0163:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\u0006jcu\u0007\rde\u0007\td~o\u0007\u001adbu";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x016e:
        r3[r2] = r1;
        r2 = 31;
        r1 = "\u0006jcu\u0007\rde\u0007\td~o\u0007\u0003{";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0179:
        r3[r2] = r1;
        r2 = 32;
        r1 = "[:#/k[%!6v[;&";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0184:
        r3[r2] = r1;
        r2 = 33;
        r1 = "\u000env`-\u0006Ob7\u0004eOh(";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x018f:
        r3[r2] = r1;
        r2 = 34;
        r1 = "\u000enfh;\u000fTqq(\u0001ni";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019a:
        r3[r2] = r1;
        r2 = 35;
        r1 = "\u0019nbw1\tnOr,\u0005{ue";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a5:
        r3[r2] = r1;
        r2 = 36;
        r1 = "\u0003fOm7\rb~^;\u0005~~u";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b0:
        r3[r2] = r1;
        r2 = 37;
        r1 = "\u000enfh;\u000fTsi9\u0004eum";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01bb:
        r3[r2] = r1;
        r2 = 38;
        r1 = "\u0019ncr1\u0005eOh<";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01c6:
        r3[r2] = r1;
        r2 = 39;
        r1 = "\u0007jh!6\u0005yg1\tjdh7\u00041";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d1:
        r3[r2] = r1;
        r2 = 40;
        r1 = "\u0003xOh55gf?\u000foOh6";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01dc:
        r3[r2] = r1;
        r2 = 41;
        r1 = "\u0004nhu\u0007\u0018bt";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01e7:
        r3[r2] = r1;
        r2 = 42;
        r1 = "\u0006jcu+\u000fOu9\r";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f2:
        r3[r2] = r1;
        r2 = 43;
        r1 = "\u0006jcu\u0007\u0018n`n*\u001eTtd.\u0003hu^1\u0004m";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x01fd:
        r3[r2] = r1;
        r2 = 44;
        r1 = "\u0002nqs,\bnqu\u0007\u0003edd*\u001cj|";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x0208:
        r3[r2] = r1;
        r2 = 45;
        r1 = "\u0006jcu\u0007\u0018n`n*\u001eT|n;\u000byn6";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x0213:
        r3[r2] = r1;
        r2 = 46;
        r1 = "\u000enfh;\u000fT}`1\u0004Tye+";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x021e:
        r3[r2] = r1;
        r2 = 47;
        r1 = "\u000enf^1\u0004m^*\u000f{Ou1\u0007n";
        r0 = 46;
        r3 = r4;
        goto L_0x0009;
    L_0x0229:
        r3[r2] = r1;
        r2 = 48;
        r1 = "\u0018nwh+\u001eyqu1\u0005eOh<";
        r0 = 47;
        r3 = r4;
        goto L_0x0009;
    L_0x0234:
        r3[r2] = r1;
        r2 = 49;
        r1 = "\u0000{er05xyr\u0007\u0004ndu!\u001an";
        r0 = 48;
        r3 = r4;
        goto L_0x0009;
    L_0x023f:
        r3[r2] = r1;
        r2 = 50;
        r1 = "\bjsj-\u001aTbd(\u0005yd^9\u000eob";
        r0 = 49;
        r3 = r4;
        goto L_0x0009;
    L_0x024a:
        r3[r2] = r1;
        r2 = 51;
        r1 = "\u0004ddh>\u000bhdh7\u0004T~t5";
        r0 = 50;
        r3 = r4;
        goto L_0x0009;
    L_0x0255:
        r3[r2] = r1;
        r2 = 52;
        r1 = "\u0018nwh+\u001enb^<\u000f}yb=5j~e*\u0005bt^1\u000e";
        r0 = 51;
        r3 = r4;
        goto L_0x0009;
    L_0x0260:
        r3[r2] = r1;
        r2 = 53;
        r1 = "\u0000{er05ho65f`n*\u001e";
        r0 = 52;
        r3 = r4;
        goto L_0x0009;
    L_0x026b:
        r3[r2] = r1;
        r2 = 54;
        r1 = "\u0018nwh+\u001enb^<\u000f}yb=5b~g7";
        r0 = 53;
        r3 = r4;
        goto L_0x0009;
    L_0x0276:
        r3[r2] = r1;
        r2 = 55;
        r1 = "\u0000{er05ho65fyq";
        r0 = 54;
        r3 = r4;
        goto L_0x0009;
    L_0x0281:
        r3[r2] = r1;
        r2 = 56;
        r1 = "\u0002dq\u0007\u0018n`n*\u001eTch+5~bm";
        r0 = 55;
        r3 = r4;
        goto L_0x0009;
    L_0x028c:
        r3[r2] = r1;
        r2 = 57;
        r1 = "\u0018nwh+\u001enb^<\u000f}yb=5b}d1";
        r0 = 56;
        r3 = r4;
        goto L_0x0009;
    L_0x0297:
        r3[r2] = r1;
        r2 = 58;
        r1 = "\u000env`-\u0006Ob7\u0004eOq7\u0018";
        r0 = 57;
        r3 = r4;
        goto L_0x0009;
    L_0x02a2:
        r3[r2] = r1;
        r2 = 59;
        r1 = " [er05Ouw1\tnYe";
        r0 = 58;
        r3 = r4;
        goto L_0x0009;
    L_0x02ad:
        r3[r2] = r1;
        r2 = 60;
        r1 = "\u0019b|d6\tn@t+\u0002_yl=";
        r0 = 59;
        r3 = r4;
        goto L_0x0009;
    L_0x02b8:
        r3[r2] = r1;
        r2 = 61;
        r1 = "\u0007[s,";
        r0 = 60;
        r3 = r4;
        goto L_0x0009;
    L_0x02c3:
        r3[r2] = r1;
        r2 = 62;
        r1 = "\u0007B@";
        r0 = 61;
        r3 = r4;
        goto L_0x0009;
    L_0x02ce:
        r3[r2] = r1;
        r2 = 63;
        r1 = "\te>k(\u001fxx/+\u000fyfd*\td~g1\r";
        r0 = 62;
        r3 = r4;
        goto L_0x0009;
    L_0x02d9:
        r3[r2] = r1;
        r2 = 64;
        r1 = "\u001a~ci,\u0003fu";
        r0 = 63;
        r3 = r4;
        goto L_0x0009;
    L_0x02e4:
        r3[r2] = r1;
        r2 = 65;
        r1 = "\u0019nbw1\tnOb7\u0004eub=\u001e";
        r0 = 64;
        r3 = r4;
        goto L_0x0009;
    L_0x02ef:
        r3[r2] = r1;
        r2 = 66;
        r1 = "\u0006hdh5\u000f";
        r0 = 65;
        r3 = r4;
        goto L_0x0009;
    L_0x02fa:
        r3[r2] = r1;
        r2 = 67;
        r1 = "\u0000{er05xyr\u0007\u0018nsd1\u001cnb^+\u001eyyo?";
        r0 = 66;
        r3 = r4;
        goto L_0x0009;
    L_0x0305:
        r3[r2] = r1;
        r2 = 68;
        r1 = "\u0006dwh65yuq7\u0018Ou1\u0007n";
        r0 = 67;
        r3 = r4;
        goto L_0x0009;
    L_0x0310:
        r3[r2] = r1;
        r2 = 69;
        r1 = "\u0018nwh+\u001enb^<\u000f}yb=5fqb";
        r0 = 68;
        r3 = r4;
        goto L_0x0009;
    L_0x031b:
        r3[r2] = r1;
        r2 = 70;
        r1 = "\te>k(\u001fxx/9\u0004obn1\u000e%QQ\b!NI";
        r0 = 69;
        r3 = r4;
        goto L_0x0009;
    L_0x0326:
        r3[r2] = r1;
        r2 = 71;
        r1 = "\u0000{er05yuf1\u0019us\u0007\tdtd";
        r0 = 70;
        r3 = r4;
        goto L_0x0009;
    L_0x0331:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0336:
        r9 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        goto L_0x0020;
    L_0x033a:
        r9 = 11;
        goto L_0x0020;
    L_0x033e:
        r9 = 16;
        goto L_0x0020;
    L_0x0342:
        r9 = 1;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.<clinit>():void");
    }

    public static String A() {
        return b.b(e.e, z[34], "");
    }

    public static String B() {
        return b.b(e.e, z[25], null);
    }

    public static String C() {
        return b.b(e.e, z[42], null);
    }

    public static long D() {
        return b.b(e.e, z[22], 0);
    }

    public static long E() {
        return b.b(e.e, z[3], 0);
    }

    public static String F() {
        return f.d(z[37], null);
    }

    public static boolean G() {
        String d = f.d(z[18], "");
        String d2 = f.d(z[15], "");
        String d3 = f.d(z[14], "");
        if (!d.isEmpty() || !d2.isEmpty() || !d3.isEmpty()) {
            return false;
        }
        z.c();
        return true;
    }

    public static String H() {
        return f.d(z[18], "");
    }

    public static String I() {
        return f.d(z[16], "");
    }

    public static cn.jpush.android.service.a a(Context context) {
        return cn.jpush.android.service.a.valueOf(b.b(context, z[8], cn.jpush.android.service.a.b.name()));
    }

    public static void a(int i) {
        f.a(z[30], i);
    }

    public static void a(long j) {
        f.a(z[21], j);
        f.a(z[20], System.currentTimeMillis());
    }

    public static void a(long j, String str, String str2, String str3, String str4) {
        a(Long.valueOf(j));
        q(str);
        r(str2);
        if (!ai.a(str3)) {
            g(str3);
        }
        f.c(z[0], str4);
    }

    public static void a(Context context, int i) {
        b.a(context, z[29], i);
    }

    public static void a(Context context, cn.jpush.android.service.a aVar) {
        b.a(context, z[8], aVar.name());
    }

    public static void a(Context context, String str) {
        b.a(context, z[10], str);
    }

    public static void a(Context context, String str, String str2) {
        b.a(context, new StringBuilder(z[19]).append(str).toString(), str2);
    }

    public static void a(Context context, boolean z) {
        b.a(context, z[12], z);
    }

    private static void a(Long l) {
        e.g = l.longValue();
        b.a(e.e, z[28], l.longValue());
    }

    public static void a(String str) {
        f.c(z[31], str);
    }

    public static void a(boolean z) {
        b.a(e.e, z[40], z);
    }

    public static boolean a() {
        long b = f.b(z[43], 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - b <= 86400000) {
            return false;
        }
        f.a(z[43], currentTimeMillis);
        return true;
    }

    public static boolean a(String str, String str2) {
        String d = f.d(z[18], "");
        String d2 = f.d(z[15], "");
        if (str.equals(d) && str2.equals(d2)) {
            z.c();
            return true;
        }
        z.c();
        return false;
    }

    public static int b(Context context) {
        int b = b.b(context, z[29], 5);
        new StringBuilder(z[39]).append(b);
        z.a();
        return b;
    }

    public static void b(int i) {
        f.a(z[38], i);
    }

    public static void b(long j) {
        f.a(z[17], j);
    }

    public static void b(Context context, int i) {
        b.a(context, z[35], i);
    }

    public static void b(Context context, String str) {
        b.a(context, z[4], str);
    }

    public static void b(Context context, boolean z) {
        b.a(context, z[24], z);
    }

    public static boolean b() {
        long b = f.b(z[45], 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - b <= com.umeng.analytics.a.h) {
            return false;
        }
        f.a(z[45], currentTimeMillis);
        return true;
    }

    public static boolean b(String str) {
        if (str == null) {
            return true;
        }
        if (str.equalsIgnoreCase(f.d(z[13], null))) {
            return false;
        }
        f.c(z[13], str);
        return true;
    }

    public static boolean b(String str, String str2) {
        String d = f.d(z[15], "");
        String d2 = f.d(z[14], "");
        if (ai.a(str2) || ai.a(d2)) {
            return str.equals(d);
        }
        if (str.equals(d) && str2.equals(d2)) {
            z.c();
            return true;
        }
        z.c();
        return false;
    }

    public static int c(Context context) {
        return b.b(context, z[35], 0);
    }

    public static void c(int i) {
        f.a(z[58], i);
    }

    public static void c(long j) {
        b.a(e.e, z[22], j);
    }

    public static void c(Context context, int i) {
        b.a(context, z[71], i);
    }

    public static void c(Context context, String str) {
        f.b(context, z[23], str);
    }

    public static void c(String str) {
        f.c(z[33], str);
    }

    public static synchronized boolean c() {
        boolean z;
        synchronized (a.class) {
            long b = f.b(z[27], 0);
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - b > 86400000) {
                f.a(z[27], currentTimeMillis);
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public static long d() {
        return f.b(z[27], 0);
    }

    public static void d(long j) {
        b.a(e.e, z[3], j);
    }

    public static void d(Context context, String str) {
        f.b(context, z[26], str);
    }

    public static void d(String str) {
        f.c(z[50], str);
    }

    public static boolean d(Context context) {
        return b.b(context, z[12], false);
    }

    public static String e() {
        return f.d(z[31], null);
    }

    public static String e(Context context) {
        return b.b(context, z[10], "");
    }

    public static String e(Context context, String str) {
        return b.b(context, new StringBuilder(z[19]).append(str).toString(), "");
    }

    public static void e(String str) {
        f.c(z[6], str);
    }

    public static int f() {
        return f.b(z[30], 0);
    }

    public static String f(Context context) {
        return b.b(context, z[4], "");
    }

    public static void f(String str) {
        f.c(z[9], str);
    }

    public static void g(String str) {
        b.a(e.e, z[2], str);
    }

    public static boolean g() {
        return System.currentTimeMillis() - f.b(z[1], 0) > 180000;
    }

    public static boolean g(Context context) {
        return b.b(context, z[24], true);
    }

    public static String h(Context context) {
        return f.c(context, z[23], "");
    }

    public static void h() {
        f.a(z[1], System.currentTimeMillis());
    }

    public static void h(String str) {
        b.a(e.e, z[34], str);
    }

    public static int i() {
        return f.b(z[38], 0);
    }

    public static String i(Context context) {
        return f.c(context, z[26], "");
    }

    public static void i(String str) {
        b.a(e.e, z[25], str);
    }

    public static long j() {
        long b = f.b(z[20], 0);
        return ((f.b(z[21], 0) - b) + System.currentTimeMillis()) / 1000;
    }

    public static void j(String str) {
        b.a(e.e, z[42], str);
    }

    public static boolean j(Context context) {
        return b.b(context, z[11], true);
    }

    public static synchronized long k() {
        long abs;
        synchronized (a.class) {
            String str = z[41];
            abs = (long) Math.abs(new Random().nextInt(32767));
            if (abs % 2 == 0) {
                abs++;
            }
            abs = f.b(str, abs) % 32767;
            f.a(z[41], abs + 2);
            abs += 2;
        }
        return abs;
    }

    public static void k(Context context) {
        f.l(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(z[63], 0);
        f.b(context, z[8], sharedPreferences.getInt(z[65], 0) == 1 ? cn.jpush.android.service.a.a.name() : cn.jpush.android.service.a.b.name());
        f.b(context, z[46], sharedPreferences.getString(z[54], ""));
        f.b(context, z[18], sharedPreferences.getString(z[57], ""));
        f.b(context, z[15], sharedPreferences.getString(z[52], ""));
        f.b(context, z[14], sharedPreferences.getString(z[69], ""));
        f.b(context, z[13], sharedPreferences.getString(z[49], ""));
        f.b(context, z[9], sharedPreferences.getString(z[67], ""));
        f.b(context, z[33], sharedPreferences.getString(z[62], ""));
        f.b(context, z[50], sharedPreferences.getString(z[56], ""));
        f.b(context, z[31], sharedPreferences.getString(z[55], ""));
        f.b(context, z[23], sharedPreferences.getString(z[23], ""));
        f.b(context, z[26], sharedPreferences.getString(z[26], ""));
        f.a(context, z[58], sharedPreferences.getInt(z[61], 0));
        f.a(context, z[30], sharedPreferences.getInt(z[53], 0));
        f.a(context, z[20], sharedPreferences.getLong(z[66], 0));
        f.a(context, z[43], sharedPreferences.getLong(z[47], 0));
        f.a(context, z[21], sharedPreferences.getLong(z[21], 0));
        f.a(context, z[45], sharedPreferences.getLong(z[68], 0));
        r(sharedPreferences.getString(z[48], ""));
        h(sharedPreferences.getString(z[70], ""));
        g(sharedPreferences.getString(z[59], ""));
        b.a(context, z[10], sharedPreferences.getString(z[60], ""));
        b.a(context, z[4], sharedPreferences.getString(z[64], ""));
        b.a(context, z[29], sharedPreferences.getInt(z[51], 5));
        b.a(context, z[35], sharedPreferences.getInt(z[35], 0));
    }

    public static void k(String str) {
        f.c(z[37], str);
    }

    public static void l(String str) {
        f.c(z[18], str);
    }

    public static boolean l() {
        return b.b(e.e, z[40], false);
    }

    public static void m() {
        b.a(e.e, z[36], b.b(e.e, z[36], -1) + 1);
    }

    public static void m(String str) {
        f.c(z[15], str);
    }

    public static int n() {
        return f.b(z[44], 290);
    }

    public static void n(String str) {
        f.c(z[14], str);
    }

    public static void o() {
        f.a(z[44], 86400);
    }

    public static void o(String str) {
        f.c(z[16], str);
    }

    public static String p() {
        return f.d(z[33], z[32]);
    }

    public static int q() {
        return f.b(z[58], (int) HeartbeatService.HEARTBEAT_INTERVAL_NOMAL);
    }

    private static void q(String str) {
        e.h = str;
        b.a(e.e, z[7], str);
    }

    public static String r() {
        return f.d(z[9], null);
    }

    private static void r(String str) {
        b.a(e.e, z[5], str);
    }

    public static long s() {
        return f.b(z[17], -1);
    }

    public static long t() {
        long j = e.g;
        if (j != 0) {
            return j;
        }
        j = b.b(e.e, z[28], 0);
        e.g = j;
        return j;
    }

    public static boolean u() {
        return t() > 0 && !ai.a(x());
    }

    public static void v() {
        a(Long.valueOf(0));
        q("");
        r("");
        g("");
        f.p(z[0]);
    }

    public static String w() {
        String str = e.h;
        if (!ai.a(str)) {
            return str;
        }
        str = b.b(e.e, z[7], "");
        e.h = str;
        return str;
    }

    public static String x() {
        return b.b(e.e, z[5], "");
    }

    public static String y() {
        return f.d(z[0], null);
    }

    public static String z() {
        return b.b(e.e, z[2], "");
    }
}
