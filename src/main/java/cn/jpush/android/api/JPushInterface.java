package cn.jpush.android.api;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import cn.jpush.android.a;
import cn.jpush.android.data.JPushLocalNotification;
import cn.jpush.android.e;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.PushService;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.service.h;
import cn.jpush.android.util.ab;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.f;
import cn.jpush.android.util.z;
import com.letv.core.constant.LetvConstant;
import com.media.ffmpeg.FFMpegPlayer;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

public class JPushInterface {
    public static final String ACTION_ACTIVITY_OPENDED;
    public static final String ACTION_CONNECTION_CHANGE;
    public static final String ACTION_MESSAGE_RECEIVED;
    public static final String ACTION_NOTIFICATION_OPENED;
    public static final String ACTION_NOTIFICATION_RECEIVED;
    public static final String ACTION_NOTIFICATION_RECEIVED_PROXY;
    public static final String ACTION_REGISTRATION_ID;
    public static final String ACTION_RESTOREPUSH;
    public static final String ACTION_RICHPUSH_CALLBACK;
    public static final String ACTION_STATUS;
    public static final String ACTION_STOPPUSH;
    public static final String EXTRA_ACTIVITY_PARAM;
    public static final String EXTRA_ALERT;
    public static final String EXTRA_APP_KEY;
    public static final String EXTRA_CONNECTION_CHANGE;
    public static final String EXTRA_CONTENT_TYPE;
    public static final String EXTRA_EXTRA;
    public static final String EXTRA_MESSAGE;
    public static final String EXTRA_MSG_ID;
    public static final String EXTRA_NOTIFICATION_DEVELOPER_ARG0;
    public static final String EXTRA_NOTIFICATION_ID;
    public static final String EXTRA_NOTIFICATION_TITLE;
    public static final String EXTRA_NOTI_TYPE;
    public static final String EXTRA_PUSH_ID;
    public static final String EXTRA_REGISTRATION_ID;
    public static final String EXTRA_RICHPUSH_FILE_PATH;
    public static final String EXTRA_RICHPUSH_FILE_TYPE;
    public static final String EXTRA_RICHPUSH_HTML_PATH;
    public static final String EXTRA_RICHPUSH_HTML_RES;
    public static final String EXTRA_STATUS;
    public static final String EXTRA_TITLE;
    private static final Integer a = Integer.valueOf(0);
    private static e b = e.b();
    private static ConcurrentLinkedQueue<Long> c = new ConcurrentLinkedQueue();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 65;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007PQ|MhZQpLl^QfHlY";
        r0 = 64;
        r4 = r3;
    L_0x000b:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x013b;
    L_0x0014:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0019:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x042b;
            case 1: goto L_0x042f;
            case 2: goto L_0x0433;
            case 3: goto L_0x0437;
            default: goto L_0x0020;
        };
    L_0x0020:
        r9 = 29;
    L_0x0022:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x0139;
    L_0x002a:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0019;
    L_0x002e:
        ACTION_MESSAGE_RECEIVED = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001SzZKfZ";
        r0 = 65;
        goto L_0x000b;
    L_0x0036:
        EXTRA_MSG_ID = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001_yM_jG";
        r0 = 66;
        goto L_0x000b;
    L_0x003e:
        EXTRA_APP_KEY = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001SlNGnYl";
        r0 = 67;
        goto L_0x000b;
    L_0x0046:
        EXTRA_MESSAGE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007\\W{WfSKn]}TBfJpB[[gYQk";
        r0 = 68;
        goto L_0x000b;
    L_0x004e:
        ACTION_ACTIVITY_OPENDED = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007N@nJ|N";
        r0 = 69;
        goto L_0x000b;
    L_0x0056:
        ACTION_STATUS = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007S[{WoTWnJ`RZpQyXZjZ";
        r0 = 70;
        goto L_0x000b;
    L_0x005e:
        ACTION_NOTIFICATION_OPENED = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001PfI]iWj\\@fQgBPjHlQ[[{BU}Y\u0019";
        r0 = 71;
        goto L_0x000b;
    L_0x0066:
        EXTRA_NOTIFICATION_DEVELOPER_ARG0 = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001J`IXj";
        r0 = 72;
        goto L_0x000b;
    L_0x006e:
        EXTRA_TITLE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001M}\\@zM";
        r0 = 73;
        goto L_0x000b;
    L_0x0076:
        EXTRA_STATUS = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007^[aPl^@fQg";
        r0 = 74;
        goto L_0x000b;
    L_0x007e:
        ACTION_CONNECTION_CHANGE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007S[{WoTWnJ`RZpLl^QfHlY";
        r0 = 75;
        goto L_0x000b;
    L_0x0086:
        ACTION_NOTIFICATION_RECEIVED = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001LlZ]|J{\\@fQgB]k";
        r0 = 76;
        goto L_0x000b;
    L_0x008f:
        EXTRA_REGISTRATION_ID = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001X`QQpNhI\\";
        r0 = 77;
        goto L_0x000b;
    L_0x0098:
        EXTRA_RICHPUSH_FILE_PATH = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001N|N\\pWm";
        r0 = 78;
        goto L_0x000b;
    L_0x00a1:
        EXTRA_PUSH_ID = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001[qIFn";
        r0 = 79;
        goto L_0x000b;
    L_0x00aa:
        EXTRA_EXTRA = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007\\W{WfSK}WjUDzMaBWnRe_UlU";
        r0 = 80;
        goto L_0x000b;
    L_0x00b3:
        ACTION_RICHPUSH_CALLBACK = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001V}PXpNhI\\";
        r0 = 81;
        goto L_0x000b;
    L_0x00bc:
        EXTRA_RICHPUSH_HTML_PATH = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007OQ|JfOQKzU";
        r0 = 82;
        goto L_0x000b;
    L_0x00c5:
        ACTION_RESTOREPUSH = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007N@`NyHGg";
        r0 = 83;
        goto L_0x000b;
    L_0x00ce:
        ACTION_STOPPUSH = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007S[{WoTWnJ`RZpLl^QfHlYKLfEM";
        r0 = 84;
        goto L_0x000b;
    L_0x00d7:
        ACTION_NOTIFICATION_RECEIVED_PROXY = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001_jI]yW}DK_{\\Y";
        r0 = 85;
        goto L_0x000b;
    L_0x00e0:
        EXTRA_ACTIVITY_PARAM = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001]fS@jP}B@vNl";
        r0 = 86;
        goto L_0x000b;
    L_0x00e9:
        EXTRA_CONTENT_TYPE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007OQhWzIFnJ`RZ";
        r0 = 87;
        goto L_0x000b;
    L_0x00f2:
        ACTION_REGISTRATION_ID = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001]fSZj]}T[aAjUUaYl";
        r0 = 88;
        goto L_0x000b;
    L_0x00fb:
        EXTRA_CONNECTION_CHANGE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001_eXF{";
        r0 = 89;
        goto L_0x000b;
    L_0x0104:
        EXTRA_ALERT = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001PfI]iWj\\@fQgBW`P}XZ{A}T@c[";
        r0 = 90;
        goto L_0x000b;
    L_0x010d:
        EXTRA_NOTIFICATION_TITLE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001V}PXpLlN";
        r0 = 91;
        goto L_0x000b;
    L_0x0116:
        EXTRA_RICHPUSH_HTML_RES = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001PfI]iWj\\@fQgB@vNl";
        r0 = 92;
        goto L_0x000b;
    L_0x011f:
        EXTRA_NOTI_TYPE = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001PfI]iWj\\@fQgB]k";
        r0 = 93;
        goto L_0x000b;
    L_0x0128:
        EXTRA_NOTIFICATION_ID = r1;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001X`QQpJpMQ";
        r0 = 94;
        goto L_0x000b;
    L_0x0131:
        EXTRA_RICHPUSH_FILE_TYPE = r1;
        r1 = "qAKqqpn\\n|pj@pq";
        r0 = -1;
        goto L_0x000b;
    L_0x0139:
        r5 = r1;
        r1 = r7;
    L_0x013b:
        if (r5 > r6) goto L_0x0014;
    L_0x013d:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0153;
            case 1: goto L_0x015d;
            case 2: goto L_0x0166;
            case 3: goto L_0x016f;
            case 4: goto L_0x0178;
            case 5: goto L_0x0181;
            case 6: goto L_0x018a;
            case 7: goto L_0x0194;
            case 8: goto L_0x01a0;
            case 9: goto L_0x01ab;
            case 10: goto L_0x01b7;
            case 11: goto L_0x01c2;
            case 12: goto L_0x01cd;
            case 13: goto L_0x01d8;
            case 14: goto L_0x01e4;
            case 15: goto L_0x01f0;
            case 16: goto L_0x01fc;
            case 17: goto L_0x0208;
            case 18: goto L_0x0213;
            case 19: goto L_0x021e;
            case 20: goto L_0x022a;
            case 21: goto L_0x0236;
            case 22: goto L_0x0241;
            case 23: goto L_0x024c;
            case 24: goto L_0x0257;
            case 25: goto L_0x0262;
            case 26: goto L_0x026d;
            case 27: goto L_0x0278;
            case 28: goto L_0x0283;
            case 29: goto L_0x028f;
            case 30: goto L_0x029a;
            case 31: goto L_0x02a6;
            case 32: goto L_0x02b2;
            case 33: goto L_0x02bd;
            case 34: goto L_0x02c9;
            case 35: goto L_0x02d4;
            case 36: goto L_0x02df;
            case 37: goto L_0x02eb;
            case 38: goto L_0x02f7;
            case 39: goto L_0x0302;
            case 40: goto L_0x030d;
            case 41: goto L_0x0318;
            case 42: goto L_0x0323;
            case 43: goto L_0x032e;
            case 44: goto L_0x0339;
            case 45: goto L_0x0344;
            case 46: goto L_0x034f;
            case 47: goto L_0x035a;
            case 48: goto L_0x0365;
            case 49: goto L_0x0370;
            case 50: goto L_0x037b;
            case 51: goto L_0x0386;
            case 52: goto L_0x0392;
            case 53: goto L_0x039d;
            case 54: goto L_0x03a9;
            case 55: goto L_0x03b5;
            case 56: goto L_0x03c1;
            case 57: goto L_0x03cd;
            case 58: goto L_0x03d8;
            case 59: goto L_0x03e3;
            case 60: goto L_0x03ef;
            case 61: goto L_0x03fb;
            case 62: goto L_0x0407;
            case 63: goto L_0x0412;
            case 64: goto L_0x002e;
            case 65: goto L_0x0036;
            case 66: goto L_0x003e;
            case 67: goto L_0x0046;
            case 68: goto L_0x004e;
            case 69: goto L_0x0056;
            case 70: goto L_0x005e;
            case 71: goto L_0x0066;
            case 72: goto L_0x006e;
            case 73: goto L_0x0076;
            case 74: goto L_0x007e;
            case 75: goto L_0x0086;
            case 76: goto L_0x008f;
            case 77: goto L_0x0098;
            case 78: goto L_0x00a1;
            case 79: goto L_0x00aa;
            case 80: goto L_0x00b3;
            case 81: goto L_0x00bc;
            case 82: goto L_0x00c5;
            case 83: goto L_0x00ce;
            case 84: goto L_0x00d7;
            case 85: goto L_0x00e0;
            case 86: goto L_0x00e9;
            case 87: goto L_0x00f2;
            case 88: goto L_0x00fb;
            case 89: goto L_0x0104;
            case 90: goto L_0x010d;
            case 91: goto L_0x0116;
            case 92: goto L_0x011f;
            case 93: goto L_0x0128;
            case 94: goto L_0x0131;
            default: goto L_0x0149;
        };
    L_0x0149:
        r3[r2] = r1;
        r2 = 1;
        r1 = "yZr]tK[gYx";
        r0 = 0;
        r3 = r4;
        goto L_0x000b;
    L_0x0153:
        r3[r2] = r1;
        r2 = 2;
        r1 = "uLj@rz\u0015mLiDZmAI}B{\t04LrFnqK";
        r0 = 1;
        r3 = r4;
        goto L_0x000b;
    L_0x015d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "8\u000fn\\n|{wDx.";
        r0 = 2;
        r3 = r4;
        goto L_0x000b;
    L_0x0166:
        r3[r2] = r1;
        r2 = 4;
        r1 = "^kZu]AjLorN}L";
        r0 = 3;
        r3 = r4;
        goto L_0x000b;
    L_0x016f:
        r3[r2] = r1;
        r2 = 5;
        r1 = "]AhHq}K>]tyJ>OrfB]=9\u000f";
        r0 = 4;
        r3 = r4;
        goto L_0x000b;
    L_0x0178:
        r3[r2] = r1;
        r2 = 6;
        r1 = "<t.\u0004+IT.\u0005*i\u0006A\u00015";
        r0 = 5;
        r3 = r4;
        goto L_0x000b;
    L_0x0181:
        r3[r2] = r1;
        r2 = 7;
        r1 = "UClL|pV>zx`kZu@FsL14Hw_x4Zn\t04";
        r0 = 6;
        r3 = r4;
        goto L_0x000b;
    L_0x018a:
        r3[r2] = r1;
        r2 = 8;
        r1 = "<t.\u0004$IS/r-9\u0016CU/O\u001f3\u001a@=s@\u0001F$\u0002'ta%t.\u0004$IS,r-9\u001cC\u0000";
        r0 = 7;
        r3 = r4;
        goto L_0x000b;
    L_0x0194:
        r3[r2] = r1;
        r2 = 9;
        r1 = "wA0Cma\\v\u0007|zKlFtp\u0001wGiqAj\u0007PAcJ`BD}QjXG|";
        r0 = 8;
        r3 = r4;
        goto L_0x000b;
    L_0x01a0:
        r3[r2] = r1;
        r2 = 10;
        r1 = "=S6";
        r0 = 9;
        r3 = r4;
        goto L_0x000b;
    L_0x01ab:
        r3[r2] = r1;
        r2 = 11;
        r1 = "uLj@rz\u0015mLiDZmAI}B{\t04JpHxJz\u0013";
        r0 = 10;
        r3 = r4;
        goto L_0x000b;
    L_0x01b7:
        r3[r2] = r1;
        r2 = 12;
        r1 = "=\u0006";
        r0 = 11;
        r3 = r4;
        goto L_0x000b;
    L_0x01c2:
        r3[r2] = r1;
        r2 = 13;
        r1 = "9\u00065\u0001";
        r0 = 12;
        r3 = r4;
        goto L_0x000b;
    L_0x01cd:
        r3[r2] = r1;
        r2 = 14;
        r1 = "ZzRe=dZmAS{[wOtwNj@rzmk@qpJl";
        r0 = 13;
        r3 = r4;
        goto L_0x000b;
    L_0x01d8:
        r3[r2] = r1;
        r2 = 15;
        r1 = "}K>Zu{ZrM=vJ>E|fH{[=`GG=$";
        r0 = 14;
        r3 = r4;
        goto L_0x000b;
    L_0x01e4:
        r3[r2] = r1;
        r2 = 16;
        r1 = "uLj@rz\u0015mLiDZmAS{[wOtwNj@rzmk@qpJl\t04Fz\u0013";
        r0 = 15;
        r3 = r4;
        goto L_0x000b;
    L_0x01f0:
        r3[r2] = r1;
        r2 = 17;
        r1 = "uLj@rz\u0015lLnaB{yhgG";
        r0 = 16;
        r3 = r4;
        goto L_0x000b;
    L_0x01fc:
        r3[r2] = r1;
        r2 = 18;
        r1 = "uLj@rz\u0015m]rdkZu";
        r0 = 17;
        r3 = r4;
        goto L_0x000b;
    L_0x0208:
        r3[r2] = r1;
        r2 = 19;
        r1 = "@G{\tqqAy]u4@x\tiuHm\tn|@kEy4M{\tqq\\m\ti|Np\t,$\u001f.\tm[{Z3";
        r0 = 18;
        r3 = r4;
        goto L_0x000b;
    L_0x0213:
        r3[r2] = r1;
        r2 = 20;
        r1 = "ZzRe=uCwHn4NpM=`NyZ34hw_x4Zn\t|w[wFs:";
        r0 = 19;
        r3 = r4;
        goto L_0x000b;
    L_0x021e:
        r3[r2] = r1;
        r2 = 21;
        r1 = "uLj@rz\u0015mLiUCwHnUAz}|s\\>\u0004=uCwHn.";
        r0 = 20;
        r3 = r4;
        goto L_0x000b;
    L_0x022a:
        r3[r2] = r1;
        r2 = 22;
        r1 = "棔浤刮彺剐沵朦罏绵〟歰劇佂尯圵朝罾绂旟臷劼终绳扎衑〖";
        r0 = 21;
        r3 = r4;
        goto L_0x000b;
    L_0x0236:
        r3[r2] = r1;
        r2 = 23;
        r1 = "]AhHq}K>Hq}Nm\u0013=";
        r0 = 22;
        r3 = r4;
        goto L_0x000b;
    L_0x0241:
        r3[r2] = r1;
        r2 = 24;
        r1 = "ZzRe=w@p]xl[";
        r0 = 23;
        r3 = r4;
        goto L_0x000b;
    L_0x024c:
        r3[r2] = r1;
        r2 = 25;
        r1 = "]AhHq}K>]|s\\2\tj}Cr\ts{[>Zx`\u000fjHzg\u000fjAtg\u000fj@pq\u0001";
        r0 = 24;
        r3 = r4;
        goto L_0x000b;
    L_0x0257:
        r3[r2] = r1;
        r2 = 26;
        r1 = "8\u000fi@qx\u000fpFi4\\{]=uCwHn4[v@n4[wDx:";
        r0 = 25;
        r3 = r4;
        goto L_0x000b;
    L_0x0262:
        r3[r2] = r1;
        r2 = 27;
        r1 = "8\u000fjHzg\u0015";
        r0 = 26;
        r3 = r4;
        goto L_0x000b;
    L_0x026d:
        r3[r2] = r1;
        r2 = 28;
        r1 = "]AhHq}K>]|s\u0015>";
        r0 = 27;
        r3 = r4;
        goto L_0x000b;
    L_0x0278:
        r3[r2] = r1;
        r2 = 29;
        r1 = "ZzRe=z@j@{}L]t{A";
        r0 = 28;
        r3 = r4;
        goto L_0x000b;
    L_0x0283:
        r3[r2] = r1;
        r2 = 30;
        r1 = "uLj@rz\u0015yLiDZmAS{[wOtwNj@rzmk@qpJl\t'4";
        r0 = 29;
        r3 = r4;
        goto L_0x000b;
    L_0x028f:
        r3[r2] = r1;
        r2 = 31;
        r1 = "WZm]ryFdLy4Mk@qpJl\t{f@s\tnuY{M=d]{OxfJpJx4\u0002>";
        r0 = 30;
        r3 = r4;
        goto L_0x000b;
    L_0x029a:
        r3[r2] = r1;
        r2 = 32;
        r1 = "sJj{xw@lMMa\\vgr`Fx@~u[wFsVZwEyq]>\u0004=";
        r0 = 31;
        r3 = r4;
        goto L_0x000b;
    L_0x02a6:
        r3[r2] = r1;
        r2 = 33;
        r1 = "z@j@{}L]t{A";
        r0 = 32;
        r3 = r4;
        goto L_0x000b;
    L_0x02b2:
        r3[r2] = r1;
        r2 = 34;
        r1 = "]AhHq}K>Gr`Fx@~u[wFs]K2\tZ}Y{\thd\u000fJi}@p\u00073";
        r0 = 33;
        r3 = r4;
        goto L_0x000b;
    L_0x02bd:
        r3[r2] = r1;
        r2 = 35;
        r1 = "x@}HqKAq]trF}Hi}@pvtp";
        r0 = 34;
        r3 = r4;
        goto L_0x000b;
    L_0x02c9:
        r3[r2] = r1;
        r2 = 36;
        r1 = "@G{\tqqAyAi4@x\tiuHm\tpuV|L=y@lL=`GG=%\u001f.\u0007";
        r0 = 35;
        r3 = r4;
        goto L_0x000b;
    L_0x02d4:
        r3[r2] = r1;
        r2 = 37;
        r1 = "]AhHq}K>]|s\u000f$\t";
        r0 = 36;
        r3 = r4;
        goto L_0x000b;
    L_0x02df:
        r3[r2] = r1;
        r2 = 38;
        r1 = "uLj@rz\u0015wGt`\u000f3\tnpDHLogFqG'";
        r0 = 37;
        r3 = r4;
        goto L_0x000b;
    L_0x02eb:
        r3[r2] = r1;
        r2 = 39;
        r1 = "~_kZu&\u001e-";
        r0 = 38;
        r3 = r4;
        goto L_0x000b;
    L_0x02f7:
        r3[r2] = r1;
        r2 = 40;
        r1 = "8\u000f|\\txKWM'&\u0016/";
        r0 = 39;
        r3 = r4;
        goto L_0x000b;
    L_0x0302:
        r3[r2] = r1;
        r2 = 41;
        r1 = "GVm]xy\u0001rF|pcwKou]g\u0013'~_kZu&\u001e-";
        r0 = 40;
        r3 = r4;
        goto L_0x000b;
    L_0x030d:
        r3[r2] = r1;
        r2 = 42;
        r1 = "GJj\tN}C{G~q\u000fN\\n|{wDx4i@qqK";
        r0 = 41;
        r3 = r4;
        goto L_0x000b;
    L_0x0318:
        r3[r2] = r1;
        r2 = 43;
        r1 = "4\u0015>";
        r0 = 42;
        r3 = r4;
        goto L_0x000b;
    L_0x0323:
        r3[r2] = r1;
        r2 = 44;
        r1 = "GJj\tN}C{G~q\u000fN\\n|{wDx4\u0002>";
        r0 = 43;
        r3 = r4;
        goto L_0x000b;
    L_0x032e:
        r3[r2] = r1;
        r2 = 45;
        r1 = "4\u00023\t";
        r0 = 44;
        r3 = r4;
        goto L_0x000b;
    L_0x0339:
        r3[r2] = r1;
        r2 = 46;
        r1 = "]AhHq}K>Y|fNsLiq]>OrfB]14\\jHo`gq\\o4NpM=qAzara]>Zu{ZrM=vJj^xqA>\u0019=j\u000f,\u001a14\\jHo`bwGn4NpM=qAzdtz\\>Zu{ZrM=vJj^xqA>\u0019=j\u000f+\u001034";
        r0 = 45;
        r3 = r4;
        goto L_0x000b;
    L_0x0344:
        r3[r2] = r1;
        r2 = 47;
        r1 = "FJsFkq\u000fjAx4\\wExzL{\ti}B{\b";
        r0 = 46;
        r3 = r4;
        goto L_0x000b;
    L_0x034f:
        r3[r2] = r1;
        r2 = 48;
        r1 = "@G{\taFrMxf\u000fi@i|\u000fwM'";
        r0 = 47;
        r3 = r4;
        goto L_0x000b;
    L_0x035a:
        r3[r2] = r1;
        r2 = 49;
        r1 = "4GZ=z@j\tqJp\tnq[>@s4Vq\\o4NnY14ZmL=pJxHhx[>Kh}CzLo5";
        r0 = 48;
        r3 = r4;
        goto L_0x000b;
    L_0x0365:
        r3[r2] = r1;
        r2 = 50;
        r1 = "$\u001e,\u001a)!\u0019A\u0019C&\u001c";
        r0 = 49;
        r3 = r4;
        goto L_0x000b;
    L_0x0370:
        r3[r2] = r1;
        r2 = 51;
        r1 = "]AhHq}K>M|m\u000fxFoyNj\t04";
        r0 = 50;
        r3 = r4;
        goto L_0x000b;
    L_0x037b:
        r3[r2] = r1;
        r2 = 52;
        r1 = "]AhHq}K>]tyJ>OrfB]=9\u000fm]|f[VFhf\u000fmAraCz\tqq\\m\ti|Np\txzKVFhf";
        r0 = 51;
        r3 = r4;
        goto L_0x000b;
    L_0x0386:
        r3[r2] = r1;
        r2 = 53;
        r1 = "uAz[r}K0YxfBwZn}@p\u0007OQnZvM\\`PlBG{_}X";
        r0 = 52;
        r3 = r4;
        goto L_0x000b;
    L_0x0392:
        r3[r2] = r1;
        r2 = 54;
        r1 = "fJo\\xg[NLoyFmZt{Am";
        r0 = 53;
        r3 = r4;
        goto L_0x000b;
    L_0x039d:
        r3[r2] = r1;
        r2 = 55;
        r1 = "uAz[r}K0Fn:mk@qp\u0001HlOGfQg3GkUvTZ{\"\u001b.";
        r0 = 54;
        r3 = r4;
        goto L_0x000b;
    L_0x03a9:
        r3[r2] = r1;
        r2 = 56;
        r1 = "uAz[r}K0YxfBwZn}@p\u0007JFfJlBQwJlOZnRvN@`LhZQ";
        r0 = 55;
        r3 = r4;
        goto L_0x000b;
    L_0x03b5:
        r3[r2] = r1;
        r2 = 57;
        r1 = "uAz[r}K0Hmd\u0001_Ji}Yw]d";
        r0 = 56;
        r3 = r4;
        goto L_0x000b;
    L_0x03c1:
        r3[r2] = r1;
        r2 = 58;
        r1 = "uAz[r}K0YxfBwZn}@p\u0007\\Wl[zNKiWgXKcQj\\@fQg";
        r0 = 57;
        r3 = r4;
        goto L_0x000b;
    L_0x03cd:
        r3[r2] = r1;
        r2 = 59;
        r1 = "gJmZx}@p\ti}B{Fh`\u000frHosJl\ti|Np\t,pNg";
        r0 = 58;
        r3 = r4;
        goto L_0x000b;
    L_0x03d8:
        r3[r2] = r1;
        r2 = 60;
        r1 = "gJmZx}@p\ti}B{Fh`\u000frLng\u000fjA|z\u000f/\u0019n";
        r0 = 59;
        r3 = r4;
        goto L_0x000b;
    L_0x03e3:
        r3[r2] = r1;
        r2 = 61;
        r1 = "yNfghy\u000fmAraCz\t#4\u001f2\tZ}Y{\thd\u000fJi}@p\u00073";
        r0 = 60;
        r3 = r4;
        goto L_0x000b;
    L_0x03ef:
        r3[r2] = r1;
        r2 = 62;
        r1 = "uLj@rz\u0015mLiXNjLn`aq]trF}Hi}@pghyM{[=.\u000f";
        r0 = 61;
        r3 = r4;
        goto L_0x000b;
    L_0x03fb:
        r3[r2] = r1;
        r2 = 63;
        r1 = "x@}HqKAq]trF}Hi}@p";
        r0 = 62;
        r3 = r4;
        goto L_0x000b;
    L_0x0407:
        r3[r2] = r1;
        r2 = 64;
        r1 = "@G{\tpgHWM=}\\>Gr`\u000fhHq}K>\u0004=";
        r0 = 63;
        r3 = r4;
        goto L_0x000b;
    L_0x0412:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        r0 = java.lang.Integer.valueOf(r0);
        a = r0;
        r0 = cn.jpush.android.api.e.b();
        b = r0;
        r0 = new java.util.concurrent.ConcurrentLinkedQueue;
        r0.<init>();
        c = r0;
        return;
    L_0x042b:
        r9 = 20;
        goto L_0x0022;
    L_0x042f:
        r9 = 47;
        goto L_0x0022;
    L_0x0433:
        r9 = 30;
        goto L_0x0022;
    L_0x0437:
        r9 = 41;
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.JPushInterface.<clinit>():void");
    }

    private static PushNotificationBuilder a(String str) {
        new StringBuilder(z[32]).append(str);
        z.a();
        String e = a.e(e.e, str);
        if (ai.a(e)) {
            return null;
        }
        new StringBuilder(z[31]).append(e);
        z.a();
        return BasicPushNotificationBuilder.a(e);
    }

    private static void a(Context context) {
        if (context == null) {
            throw new IllegalArgumentException(z[24]);
        }
        f.l(context);
    }

    private static void a(Context context, String str, Set<String> set, TagAliasCallback tagAliasCallback, boolean z) {
        Object obj = null;
        if (context == null) {
            throw new IllegalArgumentException(z[24]);
        }
        int b;
        Set filterValidTags;
        if (e.a && !cn.jpush.android.util.a.b(context)) {
            z.b(z[4], z[22]);
        }
        if (str != null) {
            b = ab.b(str);
            if (b != 0) {
                z.b(z[4], new StringBuilder(z[23]).append(str).append(z[26]).toString());
                if (tagAliasCallback != null) {
                    tagAliasCallback.gotResult(b, str, set);
                    return;
                }
                return;
            }
        }
        if (tagAliasCallback == null && !z) {
            filterValidTags = filterValidTags(set);
        }
        if (filterValidTags != null) {
            b = ab.a(filterValidTags);
            if (b != 0) {
                z.b(z[4], z[25]);
                if (tagAliasCallback != null) {
                    tagAliasCallback.gotResult(b, str, filterValidTags);
                    return;
                }
                return;
            }
        }
        String stringTags = getStringTags(filterValidTags);
        if (str == null && stringTags == null) {
            z.e(z[4], z[20]);
            if (tagAliasCallback != null) {
                tagAliasCallback.gotResult(d.a, str, filterValidTags);
                return;
            }
            return;
        }
        if (stringTags != null) {
            String replaceAll = stringTags.replaceAll(",", "");
            if ((!ai.a(replaceAll) ? replaceAll.getBytes().length + 0 : 0) <= 1000) {
                obj = 1;
            }
            if (obj == null) {
                if (tagAliasCallback != null) {
                    tagAliasCallback.gotResult(d.h, str, filterValidTags);
                }
                z.e(z[4], z[19]);
                return;
            }
        }
        z.b(z[4], new StringBuilder(z[21]).append(str).append(z[27]).append(stringTags).toString());
        ServiceInterface.a(context, str, stringTags, new b(str, filterValidTags, tagAliasCallback));
    }

    private static void a(Context context, boolean z, String str) {
        z.b();
        a.b(context, z);
        if (z) {
            String str2 = z[8];
            if (Pattern.compile(new StringBuilder(z[6]).append(str2).append(z[10]).append(str2).append(z[13]).append(str2).append(z[12]).toString()).matcher(str).matches()) {
                str2 = a.f(context);
                if (str.equals(str2)) {
                    z.b(z[4], new StringBuilder(z[7]).append(str2).toString());
                    return;
                }
                z.b(z[4], new StringBuilder(z[11]).append(z).append(z[3]).append(str).toString());
                if (ServiceInterface.b() || !e.n) {
                    a.b(context, str);
                    return;
                }
                Intent intent = new Intent(context, PushService.class);
                intent.setAction(z[9]);
                Bundle bundle = new Bundle();
                bundle.putInt(z[1], 3);
                bundle.putString(z[0], str);
                intent.putExtras(bundle);
                context.startService(intent);
                return;
            }
            z.e(z[4], new StringBuilder(z[5]).append(str).toString());
            return;
        }
        z.b(z[4], z[2]);
    }

    static boolean a(int i) {
        if (i <= 0) {
            return false;
        }
        if (a(i) != null) {
            return true;
        }
        z.d(z[4], new StringBuilder(z[48]).append(i).append(z[49]).toString());
        return false;
    }

    public static void addLocalNotification(Context context, JPushLocalNotification jPushLocalNotification) {
        a(context);
        if (e.n) {
            Intent intent = new Intent(context, PushService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 6);
            bundle.putSerializable(z[63], jPushLocalNotification);
            intent.putExtras(bundle);
            intent.setAction(z[9]);
            context.startService(intent);
            return;
        }
        h.a(context).a(context, jPushLocalNotification);
    }

    static PushNotificationBuilder b(int i) {
        z.a(z[4], new StringBuilder(z[30]).append(i).toString());
        if (i <= 0) {
            i = a.intValue();
        }
        PushNotificationBuilder pushNotificationBuilder = null;
        try {
            pushNotificationBuilder = a(i);
        } catch (Exception e) {
            z.g();
        }
        if (pushNotificationBuilder != null) {
            return pushNotificationBuilder;
        }
        z.b();
        return new DefaultPushNotificationBuilder();
    }

    public static void clearAllNotifications(Context context) {
        a(context);
        ServiceInterface.c(context);
    }

    public static void clearLocalNotifications(Context context) {
        a(context);
        if (e.n) {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[9]);
            context.startService(intent);
            return;
        }
        h.a(context).b(context);
    }

    public static void clearNotificationById(Context context, int i) {
        a(context);
        if (i <= 0) {
            z.e(z[4], z[34]);
        } else {
            ((NotificationManager) context.getSystemService(z[33])).cancel(i);
        }
    }

    public static Set<String> filterValidTags(Set<String> set) {
        if (set == null) {
            return null;
        }
        if (set.isEmpty()) {
            return set;
        }
        Set<String> linkedHashSet = new LinkedHashSet();
        int i = 0;
        for (String str : set) {
            int i2;
            if (ai.a(str) || !ab.a(str)) {
                z.e(z[4], new StringBuilder(z[37]).append(str).toString());
                i2 = i;
            } else {
                linkedHashSet.add(str);
                i2 = i + 1;
                if (i2 >= 100) {
                    z.d(z[4], z[36]);
                    return linkedHashSet;
                }
            }
            i = i2;
        }
        return linkedHashSet;
    }

    public static boolean getConnectionState(Context context) {
        a(context);
        return cn.jpush.android.service.a.a == a.a(context);
    }

    public static String getRegistrationID(Context context) {
        a(context);
        return a.x();
    }

    public static String getStringTags(Set<String> set) {
        String str = null;
        if (set == null) {
            return null;
        }
        if (set.isEmpty()) {
            return "";
        }
        int i = 0;
        for (String str2 : set) {
            int i2;
            String str3;
            if (ai.a(str2) || !ab.a(str2)) {
                z.e(z[4], new StringBuilder(z[28]).append(str2).toString());
                i2 = i;
                str3 = str;
            } else {
                str = str == null ? str2 : str + "," + str2;
                i2 = i + 1;
                if (i2 >= 100) {
                    return str;
                }
                str3 = str;
            }
            str = str3;
            i = i2;
        }
        return str;
    }

    public static String getUdid(Context context) {
        a(context);
        return cn.jpush.android.util.a.j(context);
    }

    public static void init(Context context) {
        z.b(z[4], new StringBuilder(z[38]).append(ServiceInterface.a()).append(z[40]).toString());
        try {
            System.loadLibrary(z[39]);
        } catch (Throwable th) {
            z.e(z[4], new StringBuilder(z[41]).append(th).toString());
            th.printStackTrace();
        }
        a(context);
        if (e.a && !cn.jpush.android.util.a.b(context)) {
            z.b(z[4], z[22]);
        }
        if (e.a(context)) {
            if (a.b(context) == -1) {
                setLatestNotificationNumber(context, 5);
            }
            ServiceInterface.a(context);
        }
    }

    public static void initCrashHandler(Context context) {
        a(context);
        c.a().a(context);
    }

    public static boolean isPushStopped(Context context) {
        a(context);
        return ServiceInterface.d(context);
    }

    public static void onFragmentPause(Context context, String str) {
        a(context);
        b.b(context, str);
    }

    public static void onFragmentResume(Context context, String str) {
        a(context);
        b.a(context, str);
    }

    public static void onKillProcess(Context context) {
        b.c(context);
    }

    public static void onPause(Context context) {
        a(context);
        b.b(context);
    }

    public static void onResume(Context context) {
        a(context);
        b.a(context);
    }

    public static void removeLocalNotification(Context context, long j) {
        a(context);
        if (e.n) {
            Intent intent = new Intent(context, PushService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 7);
            bundle.putLong(z[35], j);
            intent.putExtras(bundle);
            intent.setAction(z[9]);
            context.startService(intent);
            return;
        }
        h.a(context).a(context, j);
    }

    public static void reportNotificationOpened(Context context, String str) {
        a(context);
        if (ai.a(str)) {
            z.e(z[4], new StringBuilder(z[64]).append(str).toString());
        }
        g.a(str, FFMpegPlayer.DECODE_CODECOPEN_ERROR, context);
    }

    public static void requestPermission(Context context) {
        if (VERSION.SDK_INT < 23 || !(context instanceof Activity)) {
            z.b(z[4], z[55]);
            return;
        }
        String[] strArr = new String[]{z[56], z[58], z[53]};
        String str = z[56];
        String str2 = z[58];
        String str3 = z[53];
        boolean c = cn.jpush.android.util.a.c(context, str);
        boolean c2 = cn.jpush.android.util.a.c(context, str2);
        boolean c3 = cn.jpush.android.util.a.c(context, str3);
        if (!c || !c2 || !c3) {
            try {
                Class.forName(z[57]).getDeclaredMethod(z[54], new Class[]{String[].class, Integer.TYPE}).invoke(context, new Object[]{strArr, Integer.valueOf(1)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void resumePush(Context context) {
        z.b(z[4], z[17]);
        a(context);
        if (a.l()) {
            z.b();
            ServiceInterface.a(context, false);
            return;
        }
        ServiceInterface.b(context, 1);
    }

    public static void setAlias(Context context, String str, TagAliasCallback tagAliasCallback) {
        a(context);
        setAliasAndTags(context, str, null, tagAliasCallback);
    }

    @Deprecated
    public static void setAliasAndTags(Context context, String str, Set<String> set) {
        a(context);
        a(context, str, set, null, false);
    }

    public static void setAliasAndTags(Context context, String str, Set<String> set, TagAliasCallback tagAliasCallback) {
        boolean z;
        a(context);
        long currentTimeMillis = System.currentTimeMillis();
        if (c.size() < 10) {
            c.offer(Long.valueOf(currentTimeMillis));
            z = true;
        } else if (currentTimeMillis - ((Long) c.element()).longValue() > 10000) {
            while (c.size() >= 10) {
                c.poll();
            }
            c.offer(Long.valueOf(currentTimeMillis));
            z = true;
        } else {
            z = false;
        }
        if (z) {
            a(context, str, set, tagAliasCallback, true);
            return;
        }
        z.d();
        if (tagAliasCallback != null) {
            tagAliasCallback.gotResult(d.k, str, set);
        }
    }

    public static void setDebugMode(boolean z) {
        e.a = z;
    }

    public static void setDefaultPushNotificationBuilder(BasicPushNotificationBuilder basicPushNotificationBuilder) {
        if (basicPushNotificationBuilder == null) {
            throw new IllegalArgumentException(z[29]);
        }
        ServiceInterface.a(e.e, a, basicPushNotificationBuilder);
    }

    public static void setLatestNotificationNumber(Context context, int i) {
        a(context);
        z.b(z[4], new StringBuilder(z[62]).append(i).toString());
        if (i <= 0) {
            z.e(z[4], z[61]);
        } else {
            ServiceInterface.d(context, i);
        }
    }

    public static void setPushNotificationBuilder(Integer num, BasicPushNotificationBuilder basicPushNotificationBuilder) {
        z.a(z[4], new StringBuilder(z[16]).append(num).toString());
        if (basicPushNotificationBuilder == null) {
            throw new IllegalArgumentException(z[14]);
        } else if (num.intValue() <= 0) {
            z.e(z[4], z[15]);
        } else {
            ServiceInterface.a(e.e, num, basicPushNotificationBuilder);
        }
    }

    public static void setPushTime(Context context, Set<Integer> set, int i, int i2) {
        a(context);
        if (e.a && !cn.jpush.android.util.a.b(context)) {
            z.b(z[4], z[22]);
        }
        if (set == null) {
            a(context, true, z[50]);
        } else if (set.size() == 0 || set.isEmpty()) {
            a(context, false, z[50]);
        } else if (i > i2) {
            z.e(z[4], z[52]);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer num : set) {
                if (num.intValue() > 6 || num.intValue() < 0) {
                    z.e(z[4], new StringBuilder(z[51]).append(num).toString());
                    return;
                }
                stringBuilder.append(num);
            }
            stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder.append(i);
            stringBuilder.append("^");
            stringBuilder.append(i2);
            a(context, true, stringBuilder.toString());
        }
    }

    public static void setSilenceTime(Context context, int i, int i2, int i3, int i4) {
        a(context);
        if (i < 0 || i2 < 0 || i3 < 0 || i4 < 0 || i2 > 59 || i4 > 59 || i3 > 23 || i > 23) {
            z.e(z[4], z[46]);
        } else if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            ServiceInterface.a(context, "");
            z.b(z[4], z[47]);
        } else if (ServiceInterface.a(context, i, i2, i3, i4)) {
            z.b(z[4], new StringBuilder(z[44]).append(i).append(z[43]).append(i2).append(z[45]).append(i3).append(z[43]).append(i4).toString());
        } else {
            z.e(z[4], z[42]);
        }
    }

    public static void setStatisticsEnable(boolean z) {
        b.a(z);
    }

    public static void setStatisticsSessionTimeout(long j) {
        if (j < 10) {
            z.d(z[4], z[60]);
        } else if (j > LetvConstant.VIP_OVERDUE_TIME) {
            z.d(z[4], z[59]);
        } else {
            b.a(j);
        }
    }

    public static void setTags(Context context, Set<String> set, TagAliasCallback tagAliasCallback) {
        a(context);
        setAliasAndTags(context, null, set, tagAliasCallback);
    }

    public static void stopPush(Context context) {
        z.b(z[4], z[18]);
        a(context);
        if (a.l()) {
            z.b();
            ServiceInterface.a(context, true);
            return;
        }
        ServiceInterface.a(context, 1);
    }
}
