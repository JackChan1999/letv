package cn.jpush.android.helpers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import cn.jpush.android.a;
import cn.jpush.android.e;
import cn.jpush.android.service.PushProtocol;
import cn.jpush.android.service.SisInfo;
import cn.jpush.android.util.ac;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.r;
import cn.jpush.android.util.z;
import cn.jpush.b.a.a.d;
import cn.jpush.b.a.a.h;
import cn.jpush.b.a.a.i;
import cn.jpush.b.a.a.k;
import com.google.gson.jpush.ab;
import com.google.gson.jpush.t;
import com.google.gson.jpush.w;
import com.letv.core.constant.LiveRoomConstant;
import java.net.InetAddress;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectingHelper {
    private static final List<String> a;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 78;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "72*\u001b{7)!\u0011";
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
            case 0: goto L_0x03b7;
            case 1: goto L_0x03bb;
            case 2: goto L_0x03bf;
            case 3: goto L_0x03c3;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 30;
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
            case 9: goto L_0x0093;
            case 10: goto L_0x009e;
            case 11: goto L_0x00a9;
            case 12: goto L_0x00b4;
            case 13: goto L_0x00bf;
            case 14: goto L_0x00cb;
            case 15: goto L_0x00d7;
            case 16: goto L_0x00e2;
            case 17: goto L_0x00ee;
            case 18: goto L_0x00f9;
            case 19: goto L_0x0105;
            case 20: goto L_0x0111;
            case 21: goto L_0x011c;
            case 22: goto L_0x0127;
            case 23: goto L_0x0132;
            case 24: goto L_0x013d;
            case 25: goto L_0x0149;
            case 26: goto L_0x0155;
            case 27: goto L_0x0160;
            case 28: goto L_0x016b;
            case 29: goto L_0x0176;
            case 30: goto L_0x0181;
            case 31: goto L_0x018d;
            case 32: goto L_0x0198;
            case 33: goto L_0x01a3;
            case 34: goto L_0x01ae;
            case 35: goto L_0x01b9;
            case 36: goto L_0x01c4;
            case 37: goto L_0x01cf;
            case 38: goto L_0x01da;
            case 39: goto L_0x01e5;
            case 40: goto L_0x01f0;
            case 41: goto L_0x01fb;
            case 42: goto L_0x0206;
            case 43: goto L_0x0211;
            case 44: goto L_0x021c;
            case 45: goto L_0x0227;
            case 46: goto L_0x0232;
            case 47: goto L_0x023d;
            case 48: goto L_0x0248;
            case 49: goto L_0x0253;
            case 50: goto L_0x025f;
            case 51: goto L_0x026a;
            case 52: goto L_0x0276;
            case 53: goto L_0x0281;
            case 54: goto L_0x028d;
            case 55: goto L_0x0299;
            case 56: goto L_0x02a5;
            case 57: goto L_0x02b1;
            case 58: goto L_0x02bd;
            case 59: goto L_0x02c9;
            case 60: goto L_0x02d4;
            case 61: goto L_0x02df;
            case 62: goto L_0x02ea;
            case 63: goto L_0x02f5;
            case 64: goto L_0x0301;
            case 65: goto L_0x030c;
            case 66: goto L_0x0318;
            case 67: goto L_0x0323;
            case 68: goto L_0x032e;
            case 69: goto L_0x0339;
            case 70: goto L_0x0344;
            case 71: goto L_0x0350;
            case 72: goto L_0x035b;
            case 73: goto L_0x0366;
            case 74: goto L_0x0371;
            case 75: goto L_0x037c;
            case 76: goto L_0x0387;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0015>0\u001cq:}iUm13 6q:3!\u0016j=2*6v53#\u0010z";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u00172*\u001b{7)-\u001by\u001c8(\u0005{&";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "73j\u001fn!.,[:96\u001aw0s-\u001bj130[]\u001b\u0013\n0]\u0000\u0014\u000b;";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "')%\u0001{t47Up;)d\u0016v53#\u0010ztpd";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "73j\u001fn!.,[:96\u001aw0s\u0007:P\u001a\u0018\u0007!W\u001b\u0013\u001b6V\u0015\u0013\u00030";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0007\u0014\u0017UL1>!\u001ch=3#[0z";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0007\u0014\u0017UL1>!\u001ch19d&j&4*\u0012$t";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\u001380Um=.d\u001cp22d\u0010l&26U3t.-\u0006V;.0O";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u00002d\u0012{ }7\u001cmtpd\u001dq')~";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "x}4\u001al g";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0093:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u0012<-\u0019{0}0\u001a>&87\u001ar\"8d\u001dq')d\u0011p'}iU";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009e:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0011%'\u0010n 4+\u001b>#5!\u001b>71+\u0006{t( \u0005>'2'\u001e{ }iU";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a9:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0017<*Up;)d\u0012{ }7\u001cmt4*\u0013qt;6\u001ast5+\u0006jn}";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b4:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u001380Um=.d\u001cp22d\u0006k7>!\u0010zt*-\u0001vt5+\u0006jn}";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00bf:
        r3[r2] = r1;
        r2 = 15;
        r1 = "x}7\u0010r1>0\u001cq:g";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 16;
        r1 = "x}%\u0005n\u001f8=O";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\u00182#\u001cpt;%\u001cr19d\u0002w 5d\u0006{&+!\u0007>1/6\u001altpd\u0016q08~";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e2:
        r3[r2] = r1;
        r2 = 18;
        r1 = "x}7\u0011u\u000286\u0006w;3~";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ee:
        r3[r2] = r1;
        r2 = 19;
        r1 = "\u00182#\u001cpt*-\u0001vtpd\u001fk=9~";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f9:
        r3[r2] = r1;
        r2 = 20;
        r1 = "x}!\u0007l;/~";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0105:
        r3[r2] = r1;
        r2 = 21;
        r1 = "x}\"\u00193g";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x0111:
        r3[r2] = r1;
        r2 = 22;
        r1 = "fsu[-";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011c:
        r3[r2] = r1;
        r2 = 23;
        r1 = "82#\u001cptpd\u001fk=9~";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0127:
        r3[r2] = r1;
        r2 = 24;
        r1 = "\u00182#\u001cpt;%\u001cr19d\u0002w 5d9q7<(U{&/+\u0007>y}'\u001az1g";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0132:
        r3[r2] = r1;
        r2 = 25;
        r1 = "\u00182#\u001cpt;%\u001cr19d\u0002w 5d\u0007{ (6\u001b>72 \u0010$";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013d:
        r3[r2] = r1;
        r2 = 26;
        r1 = "t1+\u0012w:\u000f!\u0006n;37\u0010>=.d\u001bk81";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0149:
        r3[r2] = r1;
        r2 = 27;
        r1 = "x}7\u0010l\"86!w98";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0155:
        r3[r2] = r1;
        r2 = 28;
        r1 = "\u00182#\u001cpt.1\u0016}18 U3t.-\u0011$";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0160:
        r3[r2] = r1;
        r2 = 29;
        r1 = "=0rA0>-1\u0006vz>*";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x016b:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\u001d32\u0014r=9d\u0011{2<1\u0019jt>+\u001bpz";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0176:
        r3[r2] = r1;
        r2 = 31;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt*-\u0001vt9!\u0013!10U3t44O";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0181:
        r3[r2] = r1;
        r2 = 32;
        r1 = "x}4\u001al gwE.d";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x018d:
        r3[r2] = r1;
        r2 = 33;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt*-\u0001vt5%\u0007z72 \u0010zt5+\u0006jtpd\u001cnn";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0198:
        r3[r2] = r1;
        r2 = 34;
        r1 = "72*\u001b{7)-\u001ap";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x01a3:
        r3[r2] = r1;
        r2 = 35;
        r1 = ">.+\u001b[,>!\u0005j=2*U3t";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01ae:
        r3[r2] = r1;
        r2 = 36;
        r1 = "\u0015>0\u001cq:}iUm13 &{&+!\u0007J=0!\u0007";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b9:
        r3[r2] = r1;
        r2 = 37;
        r1 = "$(7\u001dA82#\u001cp\u000b1+\u00168\u00020\u001cs1";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01c4:
        r3[r2] = r1;
        r2 = 38;
        r1 = "$(7\u001dA82#\u001cp\u000b.!\u0007h1/\u001b\u0001w98";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01cf:
        r3[r2] = r1;
        r2 = 39;
        r1 = "73j\u001fn!.,[w9s%\u001bz&2-\u001105>0\u001cq:s\r8A\u0006\u0018\u0017%Q\u001a\u000e\u0001";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01da:
        r3[r2] = r1;
        r2 = 40;
        r1 = "$(7\u001dA 2\u001b\u001cs\u000b9%\u0001";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01e5:
        r3[r2] = r1;
        r2 = 41;
        r1 = "08\"\u0014k8)d\u001dj -~";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01f0:
        r3[r2] = r1;
        r2 = 42;
        r1 = "\u001d32\u0014r=9d\u0006w'}6\u0010m$2*\u0006{x}\"\u0014w88 Uj;}4\u0014l'8d?M\u001b\u0013j";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01fb:
        r3[r2] = r1;
        r2 = 43;
        r1 = "=-7";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x0206:
        r3[r2] = r1;
        r2 = 44;
        r1 = "=-7U&/j\u0019{:}iU";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x0211:
        r3[r2] = r1;
        r2 = 45;
        r1 = "$<6\u0006{\u000747\u001cp22d\u0016l5.,O$";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x021c:
        r3[r2] = r1;
        r2 = 46;
        r1 = " 5!Ut'2*U3t";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x0227:
        r3[r2] = r1;
        r2 = 47;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt;%\u001cr19dX>&80O";
        r0 = 46;
        r3 = r4;
        goto L_0x0009;
    L_0x0232:
        r3[r2] = r1;
        r2 = 48;
        r1 = "\u00068#\u001cm 86Um!>'\u0010{0}iUt!4 O";
        r0 = 47;
        r3 = r4;
        goto L_0x0009;
    L_0x023d:
        r3[r2] = r1;
        r2 = 49;
        r1 = "\u00068#\u001cm 86Ux54(\u0010ztpd\u0007{ g";
        r0 = 48;
        r3 = r4;
        goto L_0x0009;
    L_0x0248:
        r3[r2] = r1;
        r2 = 50;
        r1 = "\u00013!\rn1>0\u0010zn}6\u0010y=.0\u0007 4+\u001bW0r.\u0000w0}7\u001dq!1 Up;)d\u0017{t8)\u0005j-sd";
        r0 = 49;
        r3 = r4;
        goto L_0x0009;
    L_0x0253:
        r3[r2] = r1;
        r2 = 51;
        r1 = "x}'\u0019w130<p22~";
        r0 = 50;
        r3 = r4;
        goto L_0x0009;
    L_0x025f:
        r3[r2] = r1;
        r2 = 52;
        r1 = "\u00068#\u001cm 86Ui=),O>?8=O";
        r0 = 51;
        r3 = r4;
        goto L_0x0009;
    L_0x026a:
        r3[r2] = r1;
        r2 = 53;
        r1 = "x} \u0010h=>!<zn";
        r0 = 52;
        r3 = r4;
        goto L_0x0009;
    L_0x0276:
        r3[r2] = r1;
        r2 = 54;
        r1 = "!3/\u001bq#3";
        r0 = 53;
        r3 = r4;
        goto L_0x0009;
    L_0x0281:
        r3[r2] = r1;
        r2 = 55;
        r1 = "x}%\u0005u\u000286\u0006w;3~";
        r0 = 54;
        r3 = r4;
        goto L_0x0009;
    L_0x028d:
        r3[r2] = r1;
        r2 = 56;
        r1 = "x}!\rj\u001f8=O";
        r0 = 55;
        r3 = r4;
        goto L_0x0009;
    L_0x0299:
        r3[r2] = r1;
        r2 = 57;
        r1 = "x}6\u0010y=.0\u0007 4+\u001bW0g";
        r0 = 56;
        r3 = r4;
        goto L_0x0009;
    L_0x02a5:
        r3[r2] = r1;
        r2 = 58;
        r1 = "卑呐~U";
        r0 = 57;
        r3 = r4;
        goto L_0x0009;
    L_0x02b1:
        r3[r2] = r1;
        r2 = 59;
        r1 = "py";
        r0 = 58;
        r3 = r4;
        goto L_0x0009;
    L_0x02bd:
        r3[r2] = r1;
        r2 = 60;
        r1 = "t乓d4n$\u0016!\f$";
        r0 = 59;
        r3 = r4;
        goto L_0x0009;
    L_0x02c9:
        r3[r2] = r1;
        r2 = 61;
        r1 = "$<7\u0006i;/ O";
        r0 = 60;
        r3 = r4;
        goto L_0x0009;
    L_0x02d4:
        r3[r2] = r1;
        r2 = 62;
        r1 = "\u00068#\u001cm 86UX54(\u0010zt*-\u0001vt.!\u0007h1/d\u0010l&26U3t>+\u0011{n";
        r0 = 61;
        r3 = r4;
        goto L_0x0009;
    L_0x02df:
        r3[r2] = r1;
        r2 = 63;
        r1 = "\u00013,\u0014p01!\u0011>'86\u0003{&}6\u0010m$2*\u0006{t86\u0007q&}'\u001az1}iU";
        r0 = 62;
        r3 = r4;
        goto L_0x0009;
    L_0x02ea:
        r3[r2] = r1;
        r2 = 64;
        r1 = "73j\u001fn!.,[:96\u001aw0s-\u001bj130[L\u0011\u001a\r&J\u0006\u001c\u0010<Q\u001a";
        r0 = 63;
        r3 = r4;
        goto L_0x0009;
    L_0x02f5:
        r3[r2] = r1;
        r2 = 65;
        r1 = "乙卤鄉";
        r0 = 64;
        r3 = r4;
        goto L_0x0009;
    L_0x0301:
        r3[r2] = r1;
        r2 = 66;
        r1 = "\u00182'\u0014rt86\u0007q&} \u0010m7/-\u0005j=2*O>";
        r0 = 65;
        r3 = r4;
        goto L_0x0009;
    L_0x030c:
        r3[r2] = r1;
        r2 = 67;
        r1 = "x})\u0010m'<#\u0010$";
        r0 = 66;
        r3 = r4;
        goto L_0x0009;
    L_0x0318:
        r3[r2] = r1;
        r2 = 68;
        r1 = "73j\u001fn!.,[:96\u001aw0s\u00160Y\u001d\u000e\u0010'_\u0000\u0014\u000b;A\u001d\u0019";
        r0 = 67;
        r3 = r4;
        goto L_0x0009;
    L_0x0323:
        r3[r2] = r1;
        r2 = 69;
        r1 = "$5+\u001b{";
        r0 = 68;
        r3 = r4;
        goto L_0x0009;
    L_0x032e:
        r3[r2] = r1;
        r2 = 70;
        r1 = "\u0001\u001b";
        r0 = 69;
        r3 = r4;
        goto L_0x0009;
    L_0x0339:
        r3[r2] = r1;
        r2 = 71;
        r1 = "\u0007('\u0016{19d\u0001qt24\u0010pt>+\u001bp1>0\u001cq:}iUw$g";
        r0 = 70;
        r3 = r4;
        goto L_0x0009;
    L_0x0344:
        r3[r2] = r1;
        r2 = 72;
        r1 = "x}-\u001bz1%~";
        r0 = 71;
        r3 = r4;
        goto L_0x0009;
    L_0x0350:
        r3[r2] = r1;
        r2 = 73;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt*-\u0001vt24\u0001w;37U3t44O";
        r0 = 72;
        r3 = r4;
        goto L_0x0009;
    L_0x035b:
        r3[r2] = r1;
        r2 = 74;
        r1 = "\u0012<-\u0019{0}3\u001cj<}%\u0019rt>+\u001bp's";
        r0 = 73;
        r3 = r4;
        goto L_0x0009;
    L_0x0366:
        r3[r2] = r1;
        r2 = 75;
        r1 = "\u001d32\u0014r=9d\u0018=3d\u0016q:3";
        r0 = 74;
        r3 = r4;
        goto L_0x0009;
    L_0x0371:
        r3[r2] = r1;
        r2 = 76;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt*-\u0001vt0%\u001cptpd\u001cnn";
        r0 = 75;
        r3 = r4;
        goto L_0x0009;
    L_0x037c:
        r3[r2] = r1;
        r2 = 77;
        r1 = "\u001b-!\u001b>72*\u001b{7)-\u001apt*-\u0001vt1%\u0006jt:+\u001aztpd\u001cnn";
        r0 = 76;
        r3 = r4;
        goto L_0x0009;
    L_0x0387:
        r3[r2] = r1;
        z = r4;
        r6 = new java.util.ArrayList;
        r6.<init>();
        a = r6;
        r0 = "'s.\u0005k'5j\u0016p";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x03d5;
    L_0x039d:
        r3 = r0;
        r4 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x03a2:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x03c7;
            case 1: goto L_0x03ca;
            case 2: goto L_0x03cd;
            case 3: goto L_0x03d0;
            default: goto L_0x03a9;
        };
    L_0x03a9:
        r5 = 30;
    L_0x03ab:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x03d3;
    L_0x03b3:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x03a2;
    L_0x03b7:
        r9 = 84;
        goto L_0x0020;
    L_0x03bb:
        r9 = 93;
        goto L_0x0020;
    L_0x03bf:
        r9 = 68;
        goto L_0x0020;
    L_0x03c3:
        r9 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x0020;
    L_0x03c7:
        r5 = 84;
        goto L_0x03ab;
    L_0x03ca:
        r5 = 93;
        goto L_0x03ab;
    L_0x03cd:
        r5 = 68;
        goto L_0x03ab;
    L_0x03d0:
        r5 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x03ab;
    L_0x03d3:
        r1 = r0;
        r0 = r3;
    L_0x03d5:
        if (r1 > r2) goto L_0x039d;
    L_0x03d7:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        r6.add(r0);
        r2 = a;
        r1 = "'47[t$(7\u001d0=2";
        r0 = -1;
    L_0x03e8:
        r1 = r1.toCharArray();
        r3 = r1.length;
        r4 = 0;
        r5 = 1;
        if (r3 > r5) goto L_0x0419;
    L_0x03f1:
        r5 = r1;
        r6 = r4;
        r11 = r3;
        r3 = r1;
        r1 = r11;
    L_0x03f6:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x040b;
            case 1: goto L_0x040e;
            case 2: goto L_0x0411;
            case 3: goto L_0x0414;
            default: goto L_0x03fd;
        };
    L_0x03fd:
        r7 = 30;
    L_0x03ff:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r1 != 0) goto L_0x0417;
    L_0x0407:
        r3 = r5;
        r6 = r4;
        r4 = r1;
        goto L_0x03f6;
    L_0x040b:
        r7 = 84;
        goto L_0x03ff;
    L_0x040e:
        r7 = 93;
        goto L_0x03ff;
    L_0x0411:
        r7 = 68;
        goto L_0x03ff;
    L_0x0414:
        r7 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x03ff;
    L_0x0417:
        r3 = r1;
        r1 = r5;
    L_0x0419:
        if (r3 > r4) goto L_0x03f1;
    L_0x041b:
        r3 = new java.lang.String;
        r3.<init>(r1);
        r1 = r3.intern();
        switch(r0) {
            case 0: goto L_0x0430;
            case 1: goto L_0x0439;
            default: goto L_0x0427;
        };
    L_0x0427:
        r2.add(r1);
        r2 = a;
        r1 = "1<7\fj;0!\u0006m5:![};0";
        r0 = 0;
        goto L_0x03e8;
    L_0x0430:
        r2.add(r1);
        r2 = a;
        r1 = "elw[-esuB0em|";
        r0 = 1;
        goto L_0x03e8;
    L_0x0439:
        r2.add(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.ConnectingHelper.<clinit>():void");
    }

    private static int a(long j) {
        String p = a.p();
        int q = a.q();
        z.b(z[2], new StringBuilder(z[31]).append(p).append(z[10]).append(q).toString());
        if (p == null || q == 0) {
            z.b(z[2], z[30]);
            return -1;
        }
        int a = a(j, p, q);
        if (a == 0) {
            return a;
        }
        try {
            InetAddress a2 = a(z[29]);
            if (a2 == null) {
                throw new Exception();
            }
            String hostAddress = a2.getHostAddress();
            new StringBuilder(z[33]).append(hostAddress).append(z[32]);
            z.b();
            return a(j, hostAddress, 3000);
        } catch (Exception e) {
            z.b();
            return a;
        }
    }

    private static int a(long j, String str, int i) {
        int InitPush = PushProtocol.InitPush(j, str, i);
        if (InitPush != 0) {
            z.b(z[2], new StringBuilder(z[47]).append(InitPush).toString());
        }
        return InitPush;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static cn.jpush.android.service.SisInfo a(android.content.Context r11, boolean r12, int r13, int r14) {
        /*
        r9 = 12;
        r8 = 2;
    L_0x0003:
        r0 = cn.jpush.android.util.a.r(r11);
        r0 = cn.jpush.android.a.b(r0);
        if (r12 != 0) goto L_0x0021;
    L_0x000d:
        if (r0 != 0) goto L_0x0021;
    L_0x000f:
        r0 = cn.jpush.android.a.g();
        if (r0 != 0) goto L_0x0021;
    L_0x0015:
        cn.jpush.android.util.z.c();
        r0 = cn.jpush.android.a.r();
        r0 = parseSisInfo(r0);
    L_0x0020:
        return r0;
    L_0x0021:
        r3 = "";
        r1 = 0;
        r2 = new java.net.DatagramSocket;	 Catch:{ AssertionError -> 0x01ef, Exception -> 0x01e8, all -> 0x01c3 }
        r2.<init>();	 Catch:{ AssertionError -> 0x01ef, Exception -> 0x01e8, all -> 0x01c3 }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r4 = new byte[r0];	 Catch:{ AssertionError -> 0x01f3, Exception -> 0x01ec }
        r0 = a;	 Catch:{ AssertionError -> 0x01f3, Exception -> 0x01ec }
        r0 = r0.get(r13);	 Catch:{ AssertionError -> 0x01f3, Exception -> 0x01ec }
        r0 = (java.lang.String) r0;	 Catch:{ AssertionError -> 0x01f3, Exception -> 0x01ec }
        r1 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = 2;
        r1 = r1[r3];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 9;
        r5 = r5[r6];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3.<init>(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 10;
        r5 = r5[r6];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r14);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 15;
        r5 = r5[r6];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r13);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.toString();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.util.z.b(r1, r3);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        if (r13 > r8) goto L_0x00ba;
    L_0x006e:
        r1 = a(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        if (r1 != 0) goto L_0x00be;
    L_0x0074:
        r1 = new java.lang.Exception;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = 11;
        r4 = r4[r5];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3.<init>(r4);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.append(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r3.toString();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1.<init>(r3);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        throw r1;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
    L_0x008d:
        r1 = move-exception;
        r10 = r1;
        r1 = r2;
        r2 = r0;
        r0 = r10;
    L_0x0092:
        r3 = z;	 Catch:{ all -> 0x01e5 }
        r4 = 2;
        r3 = r3[r4];	 Catch:{ all -> 0x01e5 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01e5 }
        r5 = z;	 Catch:{ all -> 0x01e5 }
        r6 = 8;
        r5 = r5[r6];	 Catch:{ all -> 0x01e5 }
        r4.<init>(r5);	 Catch:{ all -> 0x01e5 }
        r2 = r4.append(r2);	 Catch:{ all -> 0x01e5 }
        r2 = r2.toString();	 Catch:{ all -> 0x01e5 }
        cn.jpush.android.util.z.a(r3, r2, r0);	 Catch:{ all -> 0x01e5 }
        r13 = r13 + 1;
        if (r1 == 0) goto L_0x00b4;
    L_0x00b1:
        r1.close();	 Catch:{ AssertionError -> 0x01ad }
    L_0x00b4:
        r0 = 4;
        if (r13 >= r0) goto L_0x01e0;
    L_0x00b7:
        r12 = 1;
        goto L_0x0003;
    L_0x00ba:
        r1 = java.net.InetAddress.getByName(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
    L_0x00be:
        r3 = cn.jpush.android.e.f;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = cn.jpush.android.a.t();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = a(r11, r3, r6);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = new java.net.DatagramPacket;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r5.<init>(r3, r6, r1, r14);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1 = 6000; // 0x1770 float:8.408E-42 double:2.9644E-320;
        r2.setSoTimeout(r1);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r2.send(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1 = new java.net.DatagramPacket;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1.<init>(r4, r3);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = 2;
        r3 = r3[r4];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = 6;
        r4 = r4[r5];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.util.z.b(r3, r4);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r2.receive(r1);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = r1.getLength();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = new byte[r3];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1 = r1.getData();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = 0;
        r5 = 0;
        r6 = r3.length;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        java.lang.System.arraycopy(r1, r4, r3, r5, r6);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1 = new java.lang.String;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1.<init>(r3);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = 7;
        r4 = r4[r5];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3.<init>(r4);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r3.append(r1);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.util.z.c();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r1 = parseSisInfo(r1);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        if (r1 == 0) goto L_0x013f;
    L_0x0119:
        r3 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = 2;
        r3 = r3[r4];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 14;
        r5 = r5[r6];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4.<init>(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = r4.append(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = r4.toString();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.util.z.c(r3, r4);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.a.h();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
    L_0x0137:
        if (r2 == 0) goto L_0x013c;
    L_0x0139:
        r2.close();	 Catch:{ AssertionError -> 0x0198 }
    L_0x013c:
        r0 = r1;
        goto L_0x0020;
    L_0x013f:
        r3 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = 2;
        r3 = r3[r4];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r5 = z;	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r6 = 13;
        r5 = r5[r6];	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4.<init>(r5);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = r4.append(r0);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        r4 = r4.toString();	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        cn.jpush.android.util.z.d(r3, r4);	 Catch:{ AssertionError -> 0x008d, Exception -> 0x015b }
        goto L_0x0137;
    L_0x015b:
        r1 = move-exception;
        r3 = r0;
        r0 = r1;
    L_0x015e:
        r1 = z;	 Catch:{ all -> 0x01e3 }
        r4 = 2;
        r1 = r1[r4];	 Catch:{ all -> 0x01e3 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01e3 }
        r5 = z;	 Catch:{ all -> 0x01e3 }
        r6 = 8;
        r5 = r5[r6];	 Catch:{ all -> 0x01e3 }
        r4.<init>(r5);	 Catch:{ all -> 0x01e3 }
        r3 = r4.append(r3);	 Catch:{ all -> 0x01e3 }
        r3 = r3.toString();	 Catch:{ all -> 0x01e3 }
        cn.jpush.android.util.z.a(r1, r3, r0);	 Catch:{ all -> 0x01e3 }
        r13 = r13 + 1;
        if (r2 == 0) goto L_0x00b4;
    L_0x017d:
        r2.close();	 Catch:{ AssertionError -> 0x0182 }
        goto L_0x00b4;
    L_0x0182:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = z;
        r2 = r2[r9];
        r1.<init>(r2);
        r0 = r0.toString();
        r1.append(r0);
        cn.jpush.android.util.z.e();
        goto L_0x00b4;
    L_0x0198:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;
        r3 = z;
        r3 = r3[r9];
        r2.<init>(r3);
        r0 = r0.toString();
        r2.append(r0);
        cn.jpush.android.util.z.e();
        goto L_0x013c;
    L_0x01ad:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = z;
        r2 = r2[r9];
        r1.<init>(r2);
        r0 = r0.toString();
        r1.append(r0);
        cn.jpush.android.util.z.e();
        goto L_0x00b4;
    L_0x01c3:
        r0 = move-exception;
        r2 = r1;
    L_0x01c5:
        if (r2 == 0) goto L_0x01ca;
    L_0x01c7:
        r2.close();	 Catch:{ AssertionError -> 0x01cb }
    L_0x01ca:
        throw r0;
    L_0x01cb:
        r1 = move-exception;
        r2 = new java.lang.StringBuilder;
        r3 = z;
        r3 = r3[r9];
        r2.<init>(r3);
        r1 = r1.toString();
        r2.append(r1);
        cn.jpush.android.util.z.e();
        goto L_0x01ca;
    L_0x01e0:
        r0 = 0;
        goto L_0x0020;
    L_0x01e3:
        r0 = move-exception;
        goto L_0x01c5;
    L_0x01e5:
        r0 = move-exception;
        r2 = r1;
        goto L_0x01c5;
    L_0x01e8:
        r0 = move-exception;
        r2 = r1;
        goto L_0x015e;
    L_0x01ec:
        r0 = move-exception;
        goto L_0x015e;
    L_0x01ef:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0092;
    L_0x01f3:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0092;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.ConnectingHelper.a(android.content.Context, boolean, int, int):cn.jpush.android.service.SisInfo");
    }

    private static InetAddress a(String str) {
        InetAddress inetAddress = null;
        a aVar = new a(str);
        try {
            aVar.start();
            aVar.join(3000);
            inetAddress = aVar.a();
        } catch (InterruptedException e) {
            z.e();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return inetAddress;
    }

    private static byte[] a(Context context, String str, long j) {
        String networkOperator;
        int intValue;
        String c = cn.jpush.android.util.a.c(context);
        String str2 = "";
        try {
            networkOperator = ((TelephonyManager) context.getSystemService(z[69])).getNetworkOperator();
        } catch (Exception e) {
            e.printStackTrace();
            networkOperator = str2;
        }
        str2 = z[70] + c;
        try {
            intValue = Integer.valueOf(networkOperator).intValue();
        } catch (Exception e2) {
            intValue = 0;
        }
        byte[] bArr = new byte[128];
        System.arraycopy(new byte[]{(byte) 0, Byte.MIN_VALUE}, 0, bArr, 0, 2);
        cn.jpush.b.a.c.a.a(bArr, str2, 2);
        cn.jpush.b.a.c.a.a(bArr, intValue, 34);
        cn.jpush.b.a.c.a.a(bArr, Integer.parseInt(((int) (2147483647L & j))), 38);
        if (str.length() > 50) {
            str = str.substring(0, 49);
        }
        cn.jpush.b.a.c.a.a(bArr, str, 42);
        cn.jpush.b.a.c.a.a(bArr, z[22], 92);
        cn.jpush.b.a.c.a.a(bArr, 0, 102);
        return bArr;
    }

    public static short getIMLoginFlag() {
        return (short) 1;
    }

    public static int login(Context context, long j) {
        byte[] bArr = new byte[128];
        long t = a.t();
        String b = ai.b(a.w());
        String A = a.A();
        int c = cn.jpush.android.util.a.c(z[22]);
        z.c(z[2], new StringBuilder(z[19]).append(t).append(z[16]).append(A).append(z[18]).append(c).toString());
        long currentTimeMillis = System.currentTimeMillis();
        short iMLoginFlag = getIMLoginFlag();
        new StringBuilder(z[23]).append(t).append(z[21]).append(iMLoginFlag);
        z.b();
        int LogPush = PushProtocol.LogPush(j, a.k(), bArr, t, b, A, (long) c, iMLoginFlag);
        long currentTimeMillis2 = System.currentTimeMillis();
        int i = 0;
        if (LogPush == 0 || LogPush == 9999) {
            i iVar = (i) d.a(bArr);
            if (iVar == null) {
                z.d(z[2], new StringBuilder(z[25]).append(LogPush).append(z[26]).toString());
                r.a(context, LogPush, currentTimeMillis2 - currentTimeMillis, 1);
                return -1;
            }
            iVar.toString();
            z.b();
            i = iVar.g;
            if (i == 0) {
                int a = iVar.a();
                long g = ((long) iVar.g()) * 1000;
                a.b(a);
                a.a(g);
                z.c(z[2], new StringBuilder(z[28]).append(a).append(z[27]).append(g).toString());
                sendServerTimer(context, g);
                c = 0;
            } else if (i == 10000) {
                z.d(z[2], new StringBuilder(z[24]).append(i).append(z[20]).append(iVar.h).toString());
                c = 1;
            } else {
                z.d(z[2], new StringBuilder(z[17]).append(i).append(z[20]).append(iVar.h).toString());
                c = 0;
            }
        } else {
            c = 1;
            z.d(z[2], new StringBuilder(z[25]).append(LogPush).toString());
        }
        r.a(context, LogPush, currentTimeMillis2 - currentTimeMillis, c);
        return i == 10000 ? -1 : LogPush;
    }

    public static synchronized boolean openConnection(Context context, long j, SisInfo sisInfo) {
        boolean z;
        synchronized (ConnectingHelper.class) {
            String e;
            int f;
            if (sisInfo == null) {
                e = a.e();
                f = a.f();
                if (e != null) {
                    z.b(z[2], new StringBuilder(z[77]).append(e).append(z[10]).append(f).toString());
                    if (a(j, e, f) == 0) {
                        z = true;
                    }
                }
                z = a(j) == 0;
            } else {
                int i;
                String mainConnIp = sisInfo.getMainConnIp();
                f = sisInfo.getMainConnPort();
                z.b(z[2], new StringBuilder(z[76]).append(mainConnIp).append(z[10]).append(f).toString());
                if (mainConnIp == null || f == 0) {
                    i = -1;
                    z.d(z[2], z[75]);
                } else {
                    i = a(j, mainConnIp, f);
                }
                if (i != 0) {
                    int i2 = 0;
                    for (String e2 : sisInfo.getOptionConnIp()) {
                        int intValue = ((Integer) sisInfo.getOptionConnPort().get(i2)).intValue();
                        z.b(z[2], new StringBuilder(z[73]).append(e2).append(z[10]).append(intValue).append(z[72]).append(i2).toString());
                        f = a(j, e2, intValue);
                        int i3;
                        if (f == 0) {
                            i3 = f;
                            f = intValue;
                            mainConnIp = e2;
                            i = i3;
                            break;
                        }
                        i2++;
                        i3 = f;
                        f = intValue;
                        mainConnIp = e2;
                        i = i3;
                    }
                }
                if (i != 0) {
                    i = a(j);
                }
                if (i == 0) {
                    a.a(mainConnIp);
                    a.a(f);
                    z.c(z[2], new StringBuilder(z[71]).append(mainConnIp).append(z[10]).append(f).toString());
                    z = true;
                } else {
                    z.d(z[2], z[74]);
                    z = false;
                }
            }
        }
        return z;
    }

    public static SisInfo parseSisInfo(String str) {
        try {
            SisInfo fromJson = SisInfo.fromJson(str);
            if (fromJson == null) {
                z.d(z[2], z[42]);
                return null;
            }
            w a = new ab().a(str);
            new StringBuilder(z[46]).append(a.toString());
            z.a();
            if (a instanceof com.google.gson.jpush.z) {
                com.google.gson.jpush.z h = a.h();
                if (h.a(z[43])) {
                    a = h.b(z[43]);
                    if (a instanceof t) {
                        t i = a.i();
                        new StringBuilder(z[44]).append(i.a());
                        z.a();
                        if (i.a() >= 3) {
                            String c = i.a(2).c();
                            new StringBuilder(z[41]).append(c);
                            z.b();
                            ac.a(c);
                        }
                    }
                } else {
                    z.e();
                }
            }
            fromJson.parseAndSet(str);
            return !fromJson.isInvalidSis() ? fromJson : null;
        } catch (Exception e) {
            z.d(z[2], new StringBuilder(z[45]).append(e).toString());
            return null;
        }
    }

    public static boolean register(Context context, long j, boolean z) {
        byte[] bArr = new byte[128];
        String a = h.a(context);
        String b = h.b(context);
        String a2 = cn.jpush.android.util.a.a(context, z[22]);
        String j2 = cn.jpush.android.util.a.j(context);
        String i = cn.jpush.android.util.a.i(context);
        String g = cn.jpush.android.util.a.g(context, " ");
        String i2 = cn.jpush.android.util.a.i(context, " ");
        String str = Build.SERIAL;
        if (ai.a(j2)) {
            j2 = " ";
        }
        if (ai.a(i)) {
            i = " ";
        }
        if (ai.a(g)) {
            g = " ";
        }
        if (ai.a(i2)) {
            i2 = " ";
        }
        if (ai.a(str) || z[54].equalsIgnoreCase(str)) {
            str = " ";
        }
        a.l(i2);
        a.m(i);
        a.n(g);
        str = cn.jpush.android.util.a.a + z[59] + j2 + z[59] + i2 + z[59] + i + z[59] + g + z[59] + str;
        new StringBuilder(z[52]).append(a).append(z[55]).append(b).append(z[51]).append(a2).append(z[56]).append(str);
        z.b();
        if (PushProtocol.RegPush(j, a.k(), a, b, a2, str) == -991) {
            return false;
        }
        int RecvPush = PushProtocol.RecvPush(j, bArr, 30);
        if (RecvPush > 0) {
            h a3 = d.a(bArr);
            if (a3 == null) {
                z.e();
                return false;
            }
            a3.toString();
            z.b();
            if (a3.d() != 0) {
                z.e();
                return false;
            }
            k kVar = (k) a3;
            RecvPush = kVar.g;
            a.c(context, RecvPush);
            if (RecvPush == 0) {
                long a4 = kVar.a();
                g = kVar.g();
                i2 = kVar.h();
                a = kVar.i();
                z.c(z[2], new StringBuilder(z[48]).append(a4).append(z[57]).append(i2).append(z[53]).append(a).toString());
                new StringBuilder(z[61]).append(g);
                z.a();
                if (ai.a(i2) || 0 == a4) {
                    z.e(z[2], z[50]);
                }
                cn.jpush.android.util.a.j(context, a);
                a.a(a4, g, i2, a, e.f);
                e.g = a4;
                e.h = g;
                if (!z) {
                    cn.jpush.android.util.a.a(context, z[64], z[68], i2);
                }
                return true;
            }
            z.e(z[2], new StringBuilder(z[62]).append(RecvPush).append(z[67]).append(kVar.h).toString());
            i = cn.jpush.android.service.r.a(RecvPush);
            if (i != null) {
                z.d(z[2], new StringBuilder(z[66]).append(i).toString());
            }
            if (LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID == RecvPush) {
                a.o();
            } else if (LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID == RecvPush) {
                z.c();
            } else if (LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID == RecvPush) {
                j2 = new StringBuilder(z[58]).append(e.c).append(z[60]).append(e.f).append(z[65]).toString();
                cn.jpush.android.util.a.c(context, j2, j2);
                a.o();
            } else if (1009 == RecvPush) {
                a.o();
            } else {
                new StringBuilder(z[63]).append(RecvPush);
                z.c();
            }
        } else {
            z.e(z[2], new StringBuilder(z[49]).append(RecvPush).toString());
        }
        return false;
    }

    public static void sendConnectionChanged(Context context, cn.jpush.android.service.a aVar) {
        boolean z = true;
        z.b(z[2], z[1]);
        if (aVar == a.a(context)) {
            new StringBuilder(z[4]).append(aVar);
            z.a();
            return;
        }
        a.a(context, aVar);
        Bundle bundle = new Bundle();
        String str = z[5];
        if (!aVar.name().equals(z[0])) {
            z = false;
        }
        bundle.putBoolean(str, z);
        cn.jpush.android.util.a.a(context, z[3], bundle);
    }

    public static void sendConnectionToHandler(Message message, long j) {
        Bundle bundle = new Bundle();
        bundle.putLong(z[34], j);
        message.setData(bundle);
        message.sendToTarget();
    }

    public static void sendServerTimer(Context context, long j) {
        z.b(z[2], z[36]);
        try {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(z[38], j);
            jSONObject.put(z[37], System.currentTimeMillis());
            bundle.putString(z[40], jSONObject.toString());
            cn.jpush.android.util.a.a(context, z[39], bundle);
        } catch (JSONException e) {
            new StringBuilder(z[35]).append(e.getMessage());
            z.d();
        }
    }

    public static synchronized SisInfo sendSis(Context context) {
        SisInfo a;
        synchronized (ConnectingHelper.class) {
            a = a(context, false, 0, 19000);
        }
        return a;
    }
}
