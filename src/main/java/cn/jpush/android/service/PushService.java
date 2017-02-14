package cn.jpush.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import cn.jpush.android.a;
import cn.jpush.android.c;
import cn.jpush.android.data.JPushLocalNotification;
import cn.jpush.android.e;
import cn.jpush.android.helpers.ConnectingHelper;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.ac;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.l;
import cn.jpush.android.util.r;
import cn.jpush.android.util.z;
import cn.jpush.b.a.b.q;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.mobile.lebox.heartbeat.HeartbeatService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public class PushService extends Service {
    public static long a;
    public static String b;
    public static boolean c = false;
    public static boolean d = false;
    private static final String[] z;
    private boolean e = true;
    private n f;
    private ExecutorService g;
    private k h;
    private m i;
    private boolean j = false;
    private int k = 0;
    private int l = 0;
    private long m = 0;
    private c n = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 72;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "*?UY\u0016\u0005|\f\u0010\u0016\u0005\u0010NW\u001e\u000e8h^YF|B_\u0017\u00059BD\u0010\u00042\u001b";
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
            case 0: goto L_0x035a;
            case 1: goto L_0x035e;
            case 2: goto L_0x0362;
            case 3: goto L_0x0366;
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
        r1 = ";)RX*\u000e.WY\u001a\u000e";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "*?UY\u0016\u0005|\f\u0010\u0016\u0005\u0018HC\u001a\u00042OU\u001a\u001f9E\u0010TK?N^\u0017\u000e?UY\u0016\u0005f";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\"/\u0001S\u0016\u00052DS\r\u00022F\u0010\u0017\u0004+\u000f\u0010>\u0002*D\u0010\f\u001b|U_Y\u00199RD\u0018\u0019(\u000f";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "*?UY\u0016\u0005|\f\u0010\u000b\u000e/UQ\u000b\u001f\bIU\u0017#9@B\r\t9@D";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "*0SU\u0018\u000f%\u0001\\\u0016\f;DTY\u00022\u000f\u0010>\u0002*D\u0010\f\u001b|U_Y\u00199RD\u0018\u0019(\u000f";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "*?UY\u0016\u0005|\f\u0010\u0016\u0005\u0014DQ\u000b\u001f>DQ\r8)BS\u001c\u000e8\u0001\u001dY\b3O^\u001c\b(H_\u0017Q";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u00053UY\u001f\u0002?@D\u0010\u00042~R\f\u00028MU\u000b";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e4>\u0010uy&;\u000ens<8\u000f";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0019(B";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e+.\u001bhc-.\u000e";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e,8\u0019so>9\u0013t~=";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u00073BQ\u001542ND\u0010\r5BQ\r\u00023O";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u00042rD\u0018\u0019(b_\u0014\u0006=OTYF|H^\r\u000e2U\n";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e+.\u000fu+.\ftc1";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "\u0019(Bo\u001d\u000e0@I";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\u00199Q_\u000b\u001f";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\n0HQ\n";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "\u00042rD\u0018\u0019(b_\u0014\u0006=OTYF|O_\rK*@\\\u0010\u000f|k`\f\u00184\u0001B\f\u00052H^\u001eKq\u0001c\u0011\u0004)MTY\u00053U\u0010\u001b\u000e|IU\u000b\u000er";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "G|Q[\u001eQ";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\u0018(N@&\u001f4SU\u0018\u000f";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e+.\fnb-";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "G|B_\u0017\u00059BD\u0010\u00042\u001b";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        r2 = 23;
        r1 = "89SF\u0010\b9\u0001R\f\u00058MUYF|";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0121:
        r3[r2] = r1;
        r2 = 24;
        r1 = "\u001b)RX&\u0018(N@\t\u000e8";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012c:
        r3[r2] = r1;
        r2 = 25;
        r1 = "\b3O^\u001c\b(H_\u0017F/UQ\r\u000e";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0137:
        r3[r2] = r1;
        r2 = 26;
        r1 = "\u00189Po\u0010\u000f";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0142:
        r3[r2] = r1;
        r2 = 27;
        r1 = "\u00073BQ\u001542ND\u0010\r5BQ\r\u00023Oo\u0010\u000f";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014d:
        r3[r2] = r1;
        r2 = 28;
        r1 = "\u001e/DB&\f.NE\u0017\u000f";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x0158:
        r3[r2] = r1;
        r2 = 29;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e:$\u0012ou:?\u0015wy-2\u0003bx8%\u001bd";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0163:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\u00053UY\u001f\u0002?@D\u0010\u00042~]\u0018\u00132T]";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x016e:
        r3[r2] = r1;
        r2 = 31;
        r1 = "\n?UY\u0016\u0005|UI\t\u000ef";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x0179:
        r3[r2] = r1;
        r2 = 32;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e8'\u0015`c&?\u001dfc";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0184:
        r3[r2] = r1;
        r2 = 33;
        r1 = "\n,Q";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x018f:
        r3[r2] = r1;
        r2 = 34;
        r1 = "\u00021~S\u0014\u000f";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019a:
        r3[r2] = r1;
        r2 = 35;
        r1 = "\u00053UY\u001f\u0002?@D\u0010\u00042~R\f\u00028MU\u000b45E";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a5:
        r3[r2] = r1;
        r2 = 36;
        r1 = "\u001f=FQ\u0015\u0002=R\u0010\u001c\u0013?D@\r\u00023O\n";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b0:
        r3[r2] = r1;
        r2 = 37;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e0%\u0015u";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01bb:
        r3[r2] = r1;
        r2 = 38;
        r1 = "\u00021~B\u001c\u001a)DC\r46R_\u0017";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01c6:
        r3[r2] = r1;
        r2 = 39;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e*?\u0013q`,8\u0014";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d1:
        r3[r2] = r1;
        r2 = 40;
        r1 = "\u000e2@R\u0015\u000e\u0003QE\n\u0003\u0003UY\u0014\u000e";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01dc:
        r3[r2] = r1;
        r2 = 41;
        r1 = "\u0006)MD\u00104(X@\u001c";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01e7:
        r3[r2] = r1;
        r2 = 42;
        r1 = ">2DH\t\u000e?UU\u001dKq\u0001Y\u0015\u00079FQ\u0015K\u0015l\u0010\u000b\u000e-TU\n\u001fr";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f2:
        r3[r2] = r1;
        r2 = 43;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0018\u00058S_\u0010\u000frH^\r\u000e2U\u001e+?\u001f";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x01fd:
        r3[r2] = r1;
        r2 = 44;
        r1 = ">2IQ\u0017\u000f0DTY\u00189SF\u0010\b9\u0001Q\u001a\u001f5N^YF|";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x0208:
        r3[r2] = r1;
        r2 = 45;
        r1 = "\b2\u000fZ\t\u001e/I\u001e\u0010\u0006r@^\u001d\u00193HTW\n?UY\u0016\u0005rh}&9\u0019pe<8\b";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x0213:
        r3[r2] = r1;
        r2 = 46;
        r1 = "\u00185MU\u0017\b9~@\f\u00184~D\u0010\u00069";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x021e:
        r3[r2] = r1;
        r2 = 47;
        r1 = "*?UY\u0016\u0005|\f\u0010\u0011\n2E\\\u001c89SF\u0010\b9`S\r\u00023O\u0010TK=BD\u0010\u00042\u001b";
        r0 = 46;
        r3 = r4;
        goto L_0x0009;
    L_0x0229:
        r3[r2] = r1;
        r2 = 48;
        r1 = "\u001f=FC";
        r0 = 47;
        r3 = r4;
        goto L_0x0009;
    L_0x0234:
        r3[r2] = r1;
        r2 = 49;
        r1 = "\u0003=OT\u0015\u000e\u000fU_\t;)RXY\n,Q\u001cY\u00189OT\u001c\u0019|DB\u000b\u0004.\u001b\u0010\\\u0018|\u0004C";
        r0 = 48;
        r3 = r4;
        goto L_0x0009;
    L_0x023f:
        r3[r2] = r1;
        r2 = 50;
        r1 = "*?UY\u0016\u0005f\u0001X\u0018\u00058MU*\u001f3Q`\f\u00184\u0001\u001dY\n,Q{\u001c\u0012f";
        r0 = 49;
        r3 = r4;
        goto L_0x0009;
    L_0x024a:
        r3[r2] = r1;
        r2 = 51;
        r1 = "*?UY\u0016\u0005|\f\u0010\u000b\u000e/UQ\u000b\u001f\u0012DD\u000e\u0004.JY\u0017\f\u001fMY\u001c\u0005(\r\u0010\t\u00028\u001b";
        r0 = 50;
        r3 = r4;
        goto L_0x0009;
    L_0x0255:
        r3[r2] = r1;
        r2 = 52;
        r1 = "%3\u0001^\u001c\u001f+NB\u0012K?N^\u0017\u000e?UY\u0016\u0005r\u0001w\u0010\u001d9\u0001E\tK(N\u0010\n\u001f=SDY\b3O^\u001c\b(H_\u0017K(IB\u001c\n8\u000f";
        r0 = 51;
        r3 = r4;
        goto L_0x0009;
    L_0x0260:
        r3[r2] = r1;
        r2 = 53;
        r1 = "G|SU\n\u001b\u001fNT\u001cQ";
        r0 = 52;
        r3 = r4;
        goto L_0x0009;
    L_0x026b:
        r3[r2] = r1;
        r2 = 54;
        r1 = "\u00042m_\u001e\u00022gQ\u0010\u00079E\u0010TK?N^\u0017\u000e?UY\u0016\u0005f";
        r0 = 53;
        r3 = r4;
        goto L_0x0009;
    L_0x0276:
        r3[r2] = r1;
        r2 = 55;
        r1 = "\u00042eU\n\u001f.NIYF|QB\u0016\b9RC0\u000ff";
        r0 = 54;
        r3 = r4;
        goto L_0x0009;
    L_0x0281:
        r3[r2] = r1;
        r2 = 56;
        r1 = "*?UY\u0016\u0005|\f\u0010\u0016\u0005\u0014DQ\u000b\u001f>DQ\r?5LU\u0016\u001e(\u0001\u001dY\u001f5LU\u0016\u001e(uY\u0014\u000e/\u001b";
        r0 = 55;
        r3 = r4;
        goto L_0x0009;
    L_0x028c:
        r3[r2] = r1;
        r2 = 57;
        r1 = "Va\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rDVa\u001c\rD";
        r0 = 56;
        r3 = r4;
        goto L_0x0009;
    L_0x0297:
        r3[r2] = r1;
        r2 = 58;
        r1 = "\"/\u0001S\u0016\u00052DS\r\u00022F\u0010\u0017\u0004+\u000f\u0010>\u0002*D\u0010\f\u001b|U_Y\u00199UB\u0000E";
        r0 = 57;
        r3 = r4;
        goto L_0x0009;
    L_0x02a2:
        r3[r2] = r1;
        r2 = 59;
        r1 = "*0SU\u0018\u000f%\u0001\\\u0016\f;DTY\u00022\u000f\u0010>\u0002*D\u0010\f\u001b|U_Y\u00199UB\u0000E";
        r0 = 58;
        r3 = r4;
        goto L_0x0009;
    L_0x02ad:
        r3[r2] = r1;
        r2 = 60;
        r1 = "G|G\\\u0018\ff";
        r0 = 59;
        r3 = r4;
        goto L_0x0009;
    L_0x02b8:
        r3[r2] = r1;
        r2 = 61;
        r1 = "\u00039@B\r\t9@DYF|KE\u0010\u000ff";
        r0 = 60;
        r3 = r4;
        goto L_0x0009;
    L_0x02c3:
        r3[r2] = r1;
        r2 = 62;
        r1 = "89OTY\u00039@B\rK>DQ\r";
        r0 = 61;
        r3 = r4;
        goto L_0x0009;
    L_0x02ce:
        r3[r2] = r1;
        r2 = 63;
        r1 = "\u0005)M\\";
        r0 = 62;
        r3 = r4;
        goto L_0x0009;
    L_0x02d9:
        r3[r2] = r1;
        r2 = 64;
        r1 = "<9\u0001V\u0016\u001e2E\u0010\r\u00039\u0001Q\t\u001b\u0017DIY\u0002/\u0001S\u0011\n2FU\u001dE|vY\u0015\u0007|SUT\u00199FY\n\u001f9S\u001e";
        r0 = 63;
        r3 = r4;
        goto L_0x0009;
    L_0x02e4:
        r3[r2] = r1;
        r2 = 65;
        r1 = "89SF\u0010\b9\u0001]\u0018\u00022\u0001D\u0011\u00199@TYF|UX\u000b\u000e=Ey\u001dQ";
        r0 = 64;
        r3 = r4;
        goto L_0x0009;
    L_0x02ef:
        r3[r2] = r1;
        r2 = 66;
        r1 = "\n0@B\u0014";
        r0 = 65;
        r3 = r4;
        goto L_0x0009;
    L_0x02fa:
        r3[r2] = r1;
        r2 = 67;
        r1 = "89SF\u0010\b9iU\u0015\u001b9S";
        r0 = 66;
        r3 = r4;
        goto L_0x0009;
    L_0x0305:
        r3[r2] = r1;
        r2 = 68;
        r1 = "!\fTC\u0011K.T^\u0017\u00022F\u0010\u001a\u00039B[\u001c\u000f|GQ\u0010\u00079E\u0011";
        r0 = 67;
        r3 = r4;
        goto L_0x0009;
    L_0x0310:
        r3[r2] = r1;
        r2 = 69;
        r1 = "*?UY\u0016\u0005f\u0001Y\u0017\u0002(\u0001`\f\u00184rU\u000b\u001d5BU";
        r0 = 68;
        r3 = r4;
        goto L_0x0009;
    L_0x031b:
        r3[r2] = r1;
        r2 = 70;
        r1 = "\u00042eY\n\b3O^\u001c\b(DTY\n2E\u0010\u000b\u000e(SIY\u00199RD\u0018\u0019(\u0001S\u0016\u00052\u0001\u001dY\u000f9MQ\u0000Q";
        r0 = 69;
        r3 = r4;
        goto L_0x0009;
    L_0x0326:
        r3[r2] = r1;
        r2 = 71;
        r1 = "*?UY\u0016\u0005|\f\u0010\u000b\u000e(SI:\u00042OU\u001a\u001f|\f\u0010\u001d\u0002/B_\u0017\u00059BD\u001c\u000f\bH]\u001c\u0018f";
        r0 = 70;
        r3 = r4;
        goto L_0x0009;
    L_0x0331:
        r3[r2] = r1;
        z = r4;
        r0 = "\n0M";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0378;
    L_0x0340:
        r3 = r0;
        r4 = r2;
        r11 = r1;
        r1 = r0;
        r0 = r11;
    L_0x0345:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x036a;
            case 1: goto L_0x036d;
            case 2: goto L_0x0370;
            case 3: goto L_0x0373;
            default: goto L_0x034c;
        };
    L_0x034c:
        r5 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
    L_0x034e:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0376;
    L_0x0356:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0345;
    L_0x035a:
        r9 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x0020;
    L_0x035e:
        r9 = 92;
        goto L_0x0020;
    L_0x0362:
        r9 = 33;
        goto L_0x0020;
    L_0x0366:
        r9 = 48;
        goto L_0x0020;
    L_0x036a:
        r5 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x034e;
    L_0x036d:
        r5 = 92;
        goto L_0x034e;
    L_0x0370:
        r5 = 33;
        goto L_0x034e;
    L_0x0373:
        r5 = 48;
        goto L_0x034e;
    L_0x0376:
        r1 = r0;
        r0 = r3;
    L_0x0378:
        if (r1 > r2) goto L_0x0340;
    L_0x037a:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        b = r0;
        r0 = 0;
        c = r0;
        r0 = 0;
        d = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.PushService.<clinit>():void");
    }

    private void a() {
        z.a(z[1], z[4]);
        if (e()) {
            z.b(z[1], z[3]);
        } else if (!this.j || d()) {
            this.i.removeMessages(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP);
            this.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
            b();
        } else {
            z.b(z[1], z[5]);
        }
    }

    private void a(long j) {
        z.b();
        new Thread(new l(this, j)).start();
    }

    static /* synthetic */ void a(PushService pushService, long j) {
        z.b(z[1], new StringBuilder(z[0]).append(j).toString());
        pushService.j = true;
        pushService.k = 0;
        pushService.l = 0;
        ConnectingHelper.sendConnectionChanged(pushService.getApplicationContext(), a.a);
        pushService.i.sendEmptyMessageDelayed(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID, 15000);
        pushService.f.a();
    }

    static /* synthetic */ void a(PushService pushService, boolean z) {
        if (z || System.currentTimeMillis() - pushService.m >= 30000) {
            z.b(z[1], z[62]);
            pushService.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
            if (!k.b.get() && pushService.j) {
                Long valueOf = Long.valueOf(a.k());
                int i = a.i();
                long t = a.t();
                short iMLoginFlag = ConnectingHelper.getIMLoginFlag();
                new StringBuilder(z[61]).append(t).append(z[60]).append(iMLoginFlag);
                z.b();
                PushProtocol.HbJPush(k.a.get(), valueOf.longValue(), i, t, iMLoginFlag);
                if (!pushService.i.hasMessages(1022)) {
                    pushService.i.sendEmptyMessageDelayed(1022, 10000);
                    return;
                }
                return;
            }
        }
        z.a();
    }

    private void a(String str, String str2) {
        try {
            new StringBuilder(z[50]).append(str2);
            z.b();
            if (ai.a(str) || ai.a(str2)) {
                String.format(z[49], new Object[]{str, str2});
                z.e();
                return;
            }
            if (this.i.hasMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID)) {
                z.a();
                this.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
            }
            ConnectingHelper.sendConnectionChanged(getApplicationContext(), a.b);
            if (this.h == null) {
                z.d();
            } else {
                this.h.a();
            }
            this.f.b();
            stopSelf();
        } catch (Exception e) {
        } finally {
            if (this.i.hasMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID)) {
                z.a();
                this.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
            }
            ConnectingHelper.sendConnectionChanged(getApplicationContext(), a.b);
            if (this.h == null) {
                z.d();
            } else {
                this.h.a();
            }
            this.f.b();
            stopSelf();
        }
    }

    private static void a(ExecutorService executorService) {
        z.a();
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                        z.a();
                    }
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                z.a();
                Thread.currentThread().interrupt();
            }
            z.a();
        }
    }

    private synchronized void b() {
        z.b(z[1], new StringBuilder(z[51]).append(Process.myPid()).toString());
        if (cn.jpush.android.util.a.b(getApplicationContext())) {
            if (!(this.g == null || this.g.isShutdown())) {
                z.b();
                a(this.g);
            }
            this.g = Executors.newSingleThreadExecutor();
            this.h = new k(getApplicationContext(), this.i, false);
            this.g.execute(this.h);
        } else {
            z.c(z[1], z[52]);
        }
    }

    static /* synthetic */ void b(PushService pushService, long j) {
        int i = 1;
        z.b(z[1], new StringBuilder(z[2]).append(j).toString());
        if (a.c(pushService.getApplicationContext()) <= 0) {
            i = 0;
        }
        if (k.a.get() != 0 || r0 == 0) {
            pushService.f.b();
            pushService.j = false;
            pushService.l = 0;
            ConnectingHelper.sendConnectionChanged(pushService.getApplicationContext(), a.b);
            a(pushService.g);
            if (cn.jpush.android.util.a.b(pushService.getApplicationContext())) {
                pushService.c();
            }
            pushService.k++;
            return;
        }
        z.b();
    }

    private void c() {
        z.b(z[1], new StringBuilder(z[71]).append(this.k).toString());
        int f = cn.jpush.android.util.a.f(getApplicationContext());
        int pow = (int) ((Math.pow(2.0d, (double) this.k) * 3.0d) * 1000.0d);
        int n = a.n();
        if (pow > (n * 1000) / 2) {
            pow = (n * 1000) / 2;
        }
        if (this.k >= 5 && f != 1) {
            z.b();
        } else if (this.i.hasMessages(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP)) {
            z.b();
        } else {
            z.b(z[1], new StringBuilder(z[70]).append(pow).toString());
            this.i.sendEmptyMessageDelayed(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP, (long) pow);
        }
    }

    static /* synthetic */ void c(PushService pushService, long j) {
        z.b(z[1], new StringBuilder(z[6]).append(j).toString());
        if (j != k.a.get()) {
            z.b();
            return;
        }
        if (pushService.i.hasMessages(1022)) {
            pushService.i.removeMessages(1022);
        }
        pushService.m = System.currentTimeMillis();
        pushService.l = 0;
        h.a(pushService.getApplicationContext()).d(pushService.getApplicationContext());
        if (a.a()) {
            l.b(pushService.getApplicationContext());
        }
        if (a.c()) {
            r.a(pushService.getApplicationContext());
        }
        if (a.j(pushService.getApplicationContext()) && a.b()) {
            g.a(pushService.getApplicationContext(), true, b, d, c);
        }
        if (e.m) {
            pushService.a(3600);
        }
    }

    static /* synthetic */ void d(PushService pushService) {
        pushService.l++;
        z.b(z[1], new StringBuilder(z[56]).append(pushService.l).toString());
        z.a(z[1], z[57]);
        if (pushService.e()) {
            z.b(z[1], z[58]);
            pushService.i.sendEmptyMessageDelayed(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID, 10000);
        } else if (!pushService.j || pushService.d()) {
            if (pushService.h != null) {
                pushService.h.a();
            }
            pushService.c();
        } else {
            z.b(z[1], z[59]);
            pushService.i.sendEmptyMessageDelayed(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID, 5000);
        }
    }

    private boolean d() {
        return this.l > 1;
    }

    private boolean e() {
        return (k.a.get() == 0 || this.j) ? false : true;
    }

    public IBinder onBind(Intent intent) {
        z.b();
        return this.n;
    }

    public void onCreate() {
        z.c();
        new StringBuilder(z[65]).append(Thread.currentThread().getId());
        z.a();
        e.l = true;
        this.n = new cn.jpush.android.helpers.c(this);
        a = Thread.currentThread().getId();
        ServiceInterface.b(getApplicationContext(), true);
        super.onCreate();
        this.i = new m(this);
        z.b(z[1], z[69]);
        if (e.a(getApplicationContext())) {
            this.e = cn.jpush.android.util.a.o(getApplicationContext());
            if (this.e) {
                this.f = new n(getApplicationContext(), this.i);
                Context applicationContext = getApplicationContext();
                String y = a.y();
                if (!(ai.a(y) || z[63].equals(y) || e.f.equalsIgnoreCase(y))) {
                    z.b(z[67], z[64]);
                    a.v();
                    ac.a(applicationContext);
                }
                cn.jpush.android.util.a.s(getApplicationContext());
            } else {
                z.d(z[1], z[68]);
            }
        }
        z.a();
        long n = 1000 * ((long) a.n());
        ((AlarmManager) getSystemService(z[66])).setInexactRepeating(0, System.currentTimeMillis() + n, n, PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0));
        if (e.m) {
            a(0);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        new StringBuilder(z[55]).append(Process.myPid());
        z.b();
        e.l = false;
        if (this.i != null) {
            this.i.removeCallbacksAndMessages(null);
        }
        cn.jpush.android.util.a.p(getApplicationContext());
        if (this.f != null) {
            n nVar = this.f;
            if (VERSION.SDK_INT >= 18) {
                nVar.quitSafely();
            } else {
                nVar.quit();
            }
        }
        if (!(this.h == null || k.a.get() == 0)) {
            this.h.a();
        }
        if (this.g != null && !this.g.isShutdown()) {
            a(this.g);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        z.b(z[1], new StringBuilder(z[13]).append(intent).append(z[19]).append(e.c).append(z[22]).append(k.a.get()).toString());
        if (this.e) {
            Bundle bundle = null;
            String str = null;
            if (intent != null) {
                str = intent.getAction();
                bundle = intent.getExtras();
            }
            if (bundle != null) {
                new StringBuilder(z[23]).append(bundle.toString());
                z.a();
            }
            if (!(str == null || bundle == null)) {
                z.b(z[1], new StringBuilder(z[47]).append(str).toString());
                int i3;
                if (z[45].equals(str)) {
                    str = bundle.getString(z[38]);
                    int i4 = bundle.getInt(z[34]);
                    if (str == null || i4 == 0) {
                        z.e(z[1], z[42]);
                    } else {
                        cn.jpush.b.a.a.g a = q.a(str, i4).a(e.f);
                        i3 = HeartbeatService.AUTO_CONNECT_CHECK_INTERVAL_NOMAL;
                        if (i4 == 3 || i4 == 4) {
                            i3 = 300000;
                        }
                        if (i4 == 1) {
                            if (ServiceInterface.d(getApplicationContext())) {
                                a.a(getApplicationContext(), true);
                                a.b(getApplicationContext(), 0);
                                b();
                            } else {
                                a.a(getApplicationContext(), false);
                            }
                        }
                        this.f.a(a, i3);
                    }
                } else if (z[43].equals(str)) {
                    cn.jpush.android.util.a.k(this);
                    if (k.a.get() == 0) {
                        a();
                    } else {
                        i3 = bundle.getInt(z[15], 0);
                        if (ai.a(bundle.getString(z[9]))) {
                            this.i.sendEmptyMessage(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
                        } else if (i3 == 0) {
                            this.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
                            if (!this.i.hasMessages(1004)) {
                                this.i.sendEmptyMessage(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
                            }
                        } else {
                            this.i.removeMessages(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
                            this.i.removeMessages(1004);
                            this.i.sendEmptyMessageDelayed(1004, (long) i3);
                        }
                    }
                    cn.jpush.android.util.a.b();
                } else if (z[29].equals(str)) {
                    cn.jpush.android.util.a.k(this);
                    str = bundle.getString(z[25]);
                    if (!ai.a(str)) {
                        if (str.equals(a.a.name())) {
                            z.b();
                            if (k.a.get() == 0) {
                                a();
                            } else {
                                this.i.sendEmptyMessage(LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID);
                            }
                            cn.jpush.android.util.a.b();
                        } else {
                            str.equals(a.b.name());
                        }
                    }
                    z.b();
                    cn.jpush.android.util.a.b();
                } else if (z[37].equals(str)) {
                    a.b(getApplicationContext(), 0);
                    if (k.a.get() == 0) {
                        b();
                    } else {
                        z.b();
                    }
                } else if (z[39].equals(str)) {
                    a.b(getApplicationContext(), 1);
                    a(bundle.getString(z[33]), e.f);
                } else if (z[21].equals(str)) {
                    bundle.getString(z[16]);
                } else if (z[11].equals(str)) {
                    if (bundle.getInt(z[28], -1) != -1) {
                        z.b();
                    }
                } else if (z[32].equals(str)) {
                    if (k.a.get() == 0) {
                        b();
                    }
                    str = bundle.getString(z[17]);
                    String string = bundle.getString(z[48]);
                    long j = bundle.getLong(z[26], 0);
                    if (!(str == null && string == null)) {
                        JSONObject jSONObject = new JSONObject();
                        if (str != null) {
                            try {
                                jSONObject.put(z[17], str);
                            } catch (JSONException e) {
                                new StringBuilder(z[36]).append(e.getMessage());
                                z.d();
                            }
                        }
                        if (string != null) {
                            jSONObject.put(z[48], string);
                        }
                        this.f.a(new cn.jpush.b.a.a.l(j, e.f, jSONObject.toString()), 20000);
                    }
                } else if (z[14].equals(str)) {
                    a.b(getApplicationContext(), 0);
                    if (k.a.get() == 0) {
                        b();
                    } else {
                        z.b();
                    }
                } else if (!(z[20].equals(str) || z[10].equals(str))) {
                    if (z[8].equals(str)) {
                        i3 = bundle.getInt(z[41]);
                        new StringBuilder(z[31]).append(i3);
                        z.a();
                        switch (i3) {
                            case 1:
                                a.a(getApplicationContext(), bundle.getString(z[35]), bundle.getString(z[7]));
                                break;
                            case 2:
                                a.a(getApplicationContext(), bundle.getInt(z[30]));
                                break;
                            case 3:
                                a.b(getApplicationContext(), bundle.getString(z[40]));
                                break;
                            case 4:
                                a.a(getApplicationContext(), bundle.getString(z[46]));
                                break;
                            case 5:
                                a.a(getApplicationContext(), bundle.getBoolean(z[24]));
                                break;
                            case 6:
                                h.a(getApplicationContext()).a(getApplicationContext(), (JPushLocalNotification) bundle.getSerializable(z[12]));
                                break;
                            case 7:
                                h.a(getApplicationContext()).a(getApplicationContext(), bundle.getLong(z[27]));
                                break;
                            case 8:
                                h.a(getApplicationContext()).b(getApplicationContext());
                                break;
                            default:
                                break;
                        }
                    }
                    new StringBuilder(z[44]).append(str);
                    z.d();
                }
            }
        } else {
            z.b(z[1], z[18]);
            this.i.sendEmptyMessageDelayed(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, 100);
        }
        return 1;
    }

    public boolean onUnbind(Intent intent) {
        z.e();
        return super.onUnbind(intent);
    }
}
