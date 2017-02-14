package cn.jpush.android.util;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.media.TransportMediator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.InstrumentedListActivity;
import cn.jpush.android.api.m;
import cn.jpush.android.data.c;
import cn.jpush.android.e;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.AlarmReceiver;
import cn.jpush.android.service.DaemonService;
import cn.jpush.android.service.DownloadService;
import cn.jpush.android.service.PushReceiver;
import cn.jpush.android.service.PushService;
import cn.jpush.android.service.s;
import cn.jpush.android.service.t;
import cn.jpush.android.ui.PopWinActivity;
import cn.jpush.android.ui.PushActivity;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.security.auth.x500.X500Principal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class a {
    public static int a = 1;
    private static List<String> b;
    private static final X500Principal c = new X500Principal(z[162]);
    private static long d = 0;
    private static final ArrayList<String> e = new ArrayList();
    private static final ArrayList<String> f = new ArrayList();
    private static final ArrayList<String> g;
    private static PushReceiver h;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 163; // 0xa3 float:2.28E-43 double:8.05E-322;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@vhlT/";
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
            case 0: goto L_0x0767;
            case 1: goto L_0x076a;
            case 2: goto L_0x076e;
            case 3: goto L_0x0772;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 56;
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
            case 5: goto L_0x006d;
            case 6: goto L_0x0075;
            case 7: goto L_0x007e;
            case 8: goto L_0x0088;
            case 9: goto L_0x0093;
            case 10: goto L_0x009f;
            case 11: goto L_0x00aa;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c1;
            case 14: goto L_0x00cc;
            case 15: goto L_0x00d7;
            case 16: goto L_0x00e3;
            case 17: goto L_0x00ee;
            case 18: goto L_0x00f9;
            case 19: goto L_0x0104;
            case 20: goto L_0x010f;
            case 21: goto L_0x011b;
            case 22: goto L_0x0126;
            case 23: goto L_0x0131;
            case 24: goto L_0x013c;
            case 25: goto L_0x0147;
            case 26: goto L_0x0152;
            case 27: goto L_0x015d;
            case 28: goto L_0x0168;
            case 29: goto L_0x0173;
            case 30: goto L_0x017e;
            case 31: goto L_0x0189;
            case 32: goto L_0x0194;
            case 33: goto L_0x019f;
            case 34: goto L_0x01aa;
            case 35: goto L_0x01b5;
            case 36: goto L_0x01c0;
            case 37: goto L_0x01cb;
            case 38: goto L_0x01d6;
            case 39: goto L_0x01e1;
            case 40: goto L_0x01ec;
            case 41: goto L_0x01f8;
            case 42: goto L_0x0204;
            case 43: goto L_0x020f;
            case 44: goto L_0x021a;
            case 45: goto L_0x0225;
            case 46: goto L_0x0230;
            case 47: goto L_0x023b;
            case 48: goto L_0x0246;
            case 49: goto L_0x0251;
            case 50: goto L_0x025c;
            case 51: goto L_0x0268;
            case 52: goto L_0x0273;
            case 53: goto L_0x027e;
            case 54: goto L_0x0289;
            case 55: goto L_0x0294;
            case 56: goto L_0x029f;
            case 57: goto L_0x02aa;
            case 58: goto L_0x02b6;
            case 59: goto L_0x02c1;
            case 60: goto L_0x02cc;
            case 61: goto L_0x02d7;
            case 62: goto L_0x02e2;
            case 63: goto L_0x02ed;
            case 64: goto L_0x02f8;
            case 65: goto L_0x0304;
            case 66: goto L_0x030f;
            case 67: goto L_0x031a;
            case 68: goto L_0x0325;
            case 69: goto L_0x0330;
            case 70: goto L_0x033c;
            case 71: goto L_0x0347;
            case 72: goto L_0x0352;
            case 73: goto L_0x035d;
            case 74: goto L_0x0368;
            case 75: goto L_0x0373;
            case 76: goto L_0x037e;
            case 77: goto L_0x0389;
            case 78: goto L_0x0394;
            case 79: goto L_0x039f;
            case 80: goto L_0x03aa;
            case 81: goto L_0x03b5;
            case 82: goto L_0x03c0;
            case 83: goto L_0x03cb;
            case 84: goto L_0x03d6;
            case 85: goto L_0x03e1;
            case 86: goto L_0x03ed;
            case 87: goto L_0x03f8;
            case 88: goto L_0x0404;
            case 89: goto L_0x0410;
            case 90: goto L_0x041b;
            case 91: goto L_0x0426;
            case 92: goto L_0x0431;
            case 93: goto L_0x043d;
            case 94: goto L_0x0448;
            case 95: goto L_0x0453;
            case 96: goto L_0x045e;
            case 97: goto L_0x0469;
            case 98: goto L_0x0474;
            case 99: goto L_0x047f;
            case 100: goto L_0x048b;
            case 101: goto L_0x0496;
            case 102: goto L_0x04a2;
            case 103: goto L_0x04ad;
            case 104: goto L_0x04b8;
            case 105: goto L_0x04c3;
            case 106: goto L_0x04ce;
            case 107: goto L_0x04d9;
            case 108: goto L_0x04e4;
            case 109: goto L_0x04ef;
            case 110: goto L_0x04fa;
            case 111: goto L_0x0505;
            case 112: goto L_0x0510;
            case 113: goto L_0x051b;
            case 114: goto L_0x0526;
            case 115: goto L_0x0531;
            case 116: goto L_0x053c;
            case 117: goto L_0x0547;
            case 118: goto L_0x0552;
            case 119: goto L_0x055e;
            case 120: goto L_0x0569;
            case 121: goto L_0x0574;
            case 122: goto L_0x057f;
            case 123: goto L_0x058b;
            case 124: goto L_0x0596;
            case 125: goto L_0x05a1;
            case 126: goto L_0x05ac;
            case 127: goto L_0x05b7;
            case 128: goto L_0x05c2;
            case 129: goto L_0x05ce;
            case 130: goto L_0x05da;
            case 131: goto L_0x05e5;
            case 132: goto L_0x05f1;
            case 133: goto L_0x05fc;
            case 134: goto L_0x0607;
            case 135: goto L_0x0612;
            case 136: goto L_0x061d;
            case 137: goto L_0x0628;
            case 138: goto L_0x0633;
            case 139: goto L_0x063e;
            case 140: goto L_0x0649;
            case 141: goto L_0x0654;
            case 142: goto L_0x065f;
            case 143: goto L_0x066a;
            case 144: goto L_0x0675;
            case 145: goto L_0x0680;
            case 146: goto L_0x068b;
            case 147: goto L_0x0696;
            case 148: goto L_0x06a1;
            case 149: goto L_0x06ac;
            case 150: goto L_0x06b7;
            case 151: goto L_0x06c2;
            case 152: goto L_0x06cd;
            case 153: goto L_0x06d8;
            case 154: goto L_0x06e3;
            case 155: goto L_0x06ee;
            case 156: goto L_0x06fa;
            case 157: goto L_0x0705;
            case 158: goto L_0x0711;
            case 159: goto L_0x071c;
            case 160: goto L_0x0728;
            case 161: goto L_0x0733;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "m\u000bJ";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001ejC/wo}^:vbvG\"lclI<rw}";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "V\u001cVVK@\u0007_U";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eoT'gugC6gujH/okR!aqC";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "e\u000fG\u0010\u0017u\u0017@\u001f[j\u000f@C\u0017h\u000bG\u001fOj\u000f]\u0000\u0017g\nWB]u\u001d";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "q\u0007UY";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        r2 = 7;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eyE-vckY9zvqY=gqlC";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0075:
        r3[r2] = r1;
        r2 = 8;
        r1 = "g\u0000WBWo\n\u0013]YeNRT\\t\u000b@C\u0002";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007e:
        r3[r2] = r1;
        r2 = 9;
        r1 = "o\u001aZ]]";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0088:
        r3[r2] = r1;
        r2 = 10;
        r1 = "b\u000fGQ";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0093:
        r3[r2] = r1;
        r2 = 11;
        r1 = "r\u0017CU";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "e\u000f]\u0010Vi\u001a\u0013VQh\n\u0013";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00aa:
        r3[r2] = r1;
        r2 = 13;
        r1 = "v\u001c\\S]u\u001d\u0013^Yk\u000b\t";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "g\u0000WBWo\nlY\\";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c1:
        r3[r2] = r1;
        r2 = 15;
        r1 = "K*\u0006";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cc:
        r3[r2] = r1;
        r2 = 16;
        r1 = "^@\u0006\u0000\u0001";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 17;
        r1 = "v\u001b@Xgh\u000bGGWt\u0005lSWh\u0000VSLc\n";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e3:
        r3[r2] = r1;
        r2 = 18;
        r1 = "l\u001d\\^}~\rV@Lo\u0001]\u0010\u0015&";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ee:
        r3[r2] = r1;
        r2 = 19;
        r1 = "e\u0000\u001dZHs\u001d[\u001eQk@R^\\t\u0001ZT\u0016g\rGYWh@z}gT+``wH=v";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f9:
        r3[r2] = r1;
        r2 = 20;
        r1 = "G\u0000WBWo\nfDQj";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0104:
        r3[r2] = r1;
        r2 = 21;
        r1 = "G\rGYWhN\u001e\u0010Kc\u0000W~]r\u0019\\BSE\u0006R^_c\ng_qK";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010f:
        r3[r2] = r1;
        r2 = 22;
        r1 = "v\u001b@Xgr\u0001lYUY\nRDY";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011b:
        r3[r2] = r1;
        r2 = 23;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@ey}Q";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0126:
        r3[r2] = r1;
        r2 = 24;
        r1 = "g\u001eC\\Qe\u000fGYWhAE^\\(\u000f]TJi\u0007W\u001eHg\rXQ_cCRB[n\u0007EU";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0131:
        r3[r2] = r1;
        r2 = 25;
        r1 = "d\u0001WI";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013c:
        r3[r2] = r1;
        r2 = 26;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@FY\u0016V\u001b@Xye\u001aZFQr\u0017";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0147:
        r3[r2] = r1;
        r2 = 27;
        r1 = "o\u001df@\\g\u001aVf]t\u001dZ_V";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0152:
        r3[r2] = r1;
        r2 = 28;
        r1 = "c\u0003CDA&\u001eRBYk\u001d";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x015d:
        r3[r2] = r1;
        r2 = 29;
        r1 = "6D\u0003";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0168:
        r3[r2] = r1;
        r2 = 30;
        r1 = "e\u0001^\u001eYh\nA_Qb@_QMh\r[UJ(\u000fPDQi\u0000\u001dyvU:r|tY={jR-fd";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0173:
        r3[r2] = r1;
        r2 = 31;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016c\u0016GBY(\u001d[_Jr\rFD\u0016O-|~gT+`mT-v";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017e:
        r3[r2] = r1;
        r2 = 32;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016c\u0016GBY(\u001d[_Jr\rFD\u0016O guvR";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0189:
        r3[r2] = r1;
        r2 = 33;
        r1 = "b\u001bC\\Qe\u000fGU";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0194:
        r3[r2] = r1;
        r2 = 34;
        r1 = "S\u0000VHHc\rGU\\<NZ^Ng\u0002ZT\u0018s\u001c_\u0010\u0015&";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019f:
        r3[r2] = r1;
        r2 = 35;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016c\u0016GBY(\u001d[_Jr\rFD\u0016H/~u";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01aa:
        r3[r2] = r1;
        r2 = 36;
        r1 = "V\u001c\\S]u\u001d\\B";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b5:
        r3[r2] = r1;
        r2 = 37;
        r1 = ")\u001eA_[)\rCEQh\b\\";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01c0:
        r3[r2] = r1;
        r2 = 38;
        r1 = "Z@";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01cb:
        r3[r2] = r1;
        r2 = 39;
        r1 = "H\u0001GY^o\rRDQi\u0000\u0013GYuNWYKg\f_U\\&\fJ\u0010rV\u001b@Xqh\u001aVB^g\rV\u001eKc\u001acEKn:Z]]&O";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d6:
        r3[r2] = r1;
        r2 = 40;
        r1 = "E\u001bAB]h\u001a\u0013DQk\u000b\u0013YK&\u0001FD\u0018i\b\u0013DPcNCEKnNGYUcN\u001e\u0010";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01e1:
        r3[r2] = r1;
        r2 = 41;
        r1 = "Z0";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01ec:
        r3[r2] = r1;
        r2 = 42;
        r1 = "v\u001b@X\u0018r\u0007^U\u0018o\u001d\u0013Ｊ";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f8:
        r3[r2] = r1;
        r2 = 43;
        r1 = "v\u0006\\^]";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x0204:
        r3[r2] = r1;
        r2 = 44;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001ejC/wohN!}ugU:rd}";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x020f:
        r3[r2] = r1;
        r2 = 45;
        r1 = "e\u0001]^]e\u001aZFQr\u0017";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x021a:
        r3[r2] = r1;
        r2 = 46;
        r1 = ")\nRDY)";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x0225:
        r3[r2] = r1;
        r2 = 47;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@~qqH";
        r0 = 46;
        r3 = r4;
        goto L_0x0009;
    L_0x0230:
        r3[r2] = r1;
        r2 = 48;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016e\u000fGU_i\u001cJ\u001etG;}spC<";
        r0 = 47;
        r3 = r4;
        goto L_0x0009;
    L_0x023b:
        r3[r2] = r1;
        r2 = 49;
        r1 = "(\u001eFCPY\u001bWY\\";
        r0 = 48;
        r3 = r4;
        goto L_0x0009;
    L_0x0246:
        r3[r2] = r1;
        r2 = 50;
        r1 = "A\u0001G\u0010Kb\rRB\\&\bZ\\]&\u001dRF]bNFTQbN\u001e\u0010";
        r0 = 49;
        r3 = r4;
        goto L_0x0009;
    L_0x0251:
        r3[r2] = r1;
        r2 = 51;
        r1 = "j\u001aV";
        r0 = 50;
        r3 = r4;
        goto L_0x0009;
    L_0x025c:
        r3[r2] = r1;
        r2 = 52;
        r1 = "s\u0000X^Wq\u0000";
        r0 = 51;
        r3 = r4;
        goto L_0x0009;
    L_0x0268:
        r3[r2] = r1;
        r2 = 53;
        r1 = "e\n^Q";
        r0 = 52;
        r3 = r4;
        goto L_0x0009;
    L_0x0273:
        r3[r2] = r1;
        r2 = 54;
        r1 = "a\u001d^";
        r0 = 53;
        r3 = r4;
        goto L_0x0009;
    L_0x027e:
        r3[r2] = r1;
        r2 = 55;
        r1 = "K\u000fZ^\u0018e\u0002RCK&\u0007@\u0010";
        r0 = 54;
        r3 = r4;
        goto L_0x0009;
    L_0x0289:
        r3[r2] = r1;
        r2 = 56;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@cq{M/tugT+~nC*";
        r0 = 55;
        r3 = r4;
        goto L_0x0009;
    L_0x0294:
        r3[r2] = r1;
        r2 = 57;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001evI:zvqE/gywH1au{C'eu|Y>a`_";
        r0 = 56;
        r3 = r4;
        goto L_0x0009;
    L_0x029f:
        r3[r2] = r1;
        r2 = 58;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@fc}T1cb}U+}d";
        r0 = 57;
        r3 = r4;
        goto L_0x0009;
    L_0x02aa:
        r3[r2] = r1;
        r2 = 59;
        r1 = "v\u000fP[Ya\u000b";
        r0 = 58;
        r3 = r4;
        goto L_0x0009;
    L_0x02b6:
        r3[r2] = r1;
        r2 = 60;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@cq{M/tugG*wu|";
        r0 = 59;
        r3 = r4;
        goto L_0x0009;
    L_0x02c1:
        r3[r2] = r1;
        r2 = 61;
        r1 = "g\u0000WBWo\n\u001d^]r@P_Vh@pvH+pdqP'gigE&r~C";
        r0 = 60;
        r3 = r4;
        goto L_0x0009;
    L_0x02cc:
        r3[r2] = r1;
        r2 = 62;
        r1 = "S\u0000X^Wq\u0000";
        r0 = 61;
        r3 = r4;
        goto L_0x0009;
    L_0x02d7:
        r3[r2] = r1;
        r2 = 63;
        r1 = "2\t";
        r0 = 62;
        r3 = r4;
        goto L_0x0009;
    L_0x02e2:
        r3[r2] = r1;
        r2 = 64;
        r1 = "4\t";
        r0 = 63;
        r3 = r4;
        goto L_0x0009;
    L_0x02ed:
        r3[r2] = r1;
        r2 = 65;
        r1 = "5\t";
        r0 = 64;
        r3 = r4;
        goto L_0x0009;
    L_0x02f8:
        r3[r2] = r1;
        r2 = 66;
        r1 = "诱剞\u0013`Wt\u001aR\\\u0018丌菙句悘皼匃呣咿qHv%VI幎曲旞r^\\t\u0001ZTug\u0000ZV]u\u001a盋庤孯殳";
        r0 = 65;
        r3 = r4;
        goto L_0x0009;
    L_0x0304:
        r3[r2] = r1;
        r2 = 67;
        r1 = "L>FCP揖祔Ｉ匵吵咊/C@sc\u0017举匉酵";
        r0 = 66;
        r3 = r4;
        goto L_0x0009;
    L_0x030f:
        r3[r2] = r1;
        r2 = 68;
        r1 = "h\u0001GY^o\rRDQi\u0000";
        r0 = 67;
        r3 = r4;
        goto L_0x0009;
    L_0x031a:
        r3[r2] = r1;
        r2 = 69;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001evI:zvqE/gywH1|`}H+wohT!ki";
        r0 = 68;
        r3 = r4;
        goto L_0x0009;
    L_0x0325:
        r3[r2] = r1;
        r2 = 70;
        r1 = "b\u000bQE_Y\u0000\\DQ`\u0007PQLo\u0001]";
        r0 = 69;
        r3 = r4;
        goto L_0x0009;
    L_0x0330:
        r3[r2] = r1;
        r2 = 71;
        r1 = "r\u0001RCLR\u000bKD";
        r0 = 70;
        r3 = r4;
        goto L_0x0009;
    L_0x033c:
        r3[r2] = r1;
        r2 = 72;
        r1 = "b\u000bESQc1ZTga\u000b]UJg\u001aVT";
        r0 = 71;
        r3 = r4;
        goto L_0x0009;
    L_0x0347:
        r3[r2] = r1;
        r2 = 73;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eoT'gugU+gdqH)`";
        r0 = 72;
        r3 = r4;
        goto L_0x0009;
    L_0x0352:
        r3[r2] = r1;
        r2 = 74;
        r1 = "k\u0001F^Lc\n";
        r0 = 73;
        r3 = r4;
        goto L_0x0009;
    L_0x035d:
        r3[r2] = r1;
        r2 = 75;
        r1 = "\"J";
        r0 = 74;
        r3 = r4;
        goto L_0x0009;
    L_0x0368:
        r3[r2] = r1;
        r2 = 76;
        r1 = "d\u000f@UZg\u0000W";
        r0 = 75;
        r3 = r4;
        goto L_0x0009;
    L_0x0373:
        r3[r2] = r1;
        r2 = 77;
        r1 = ")\nRDY)\u000fC@\u0017";
        r0 = 76;
        r3 = r4;
        goto L_0x0009;
    L_0x037e:
        r3[r2] = r1;
        r2 = 78;
        r1 = ")\u001dJCLc\u0003\u001cQHvA";
        r0 = 77;
        r3 = r4;
        goto L_0x0009;
    L_0x0389:
        r3[r2] = r1;
        r2 = 79;
        r1 = "a\u001d^\u001eNc\u001c@YWh@QQKc\fR^\\";
        r0 = 78;
        r3 = r4;
        goto L_0x0009;
    L_0x0394:
        r3[r2] = r1;
        r2 = 80;
        r1 = "E\u001bAB]h\u001a\u0013@S&\u0007]CLg\u0002_U\\&\u001eRDP<N";
        r0 = 79;
        r3 = r4;
        goto L_0x0009;
    L_0x039f:
        r3[r2] = r1;
        r2 = 81;
        r1 = "#\u001d\u001d@]t\u0003ZCKo\u0001]\u001erV;`xgK+`cyA+";
        r0 = 80;
        r3 = r4;
        goto L_0x0009;
    L_0x03aa:
        r3[r2] = r1;
        r2 = 82;
        r1 = "D\u001b]TTcN@XWs\u0002W\u0010Vi\u001a\u0013R]&\u0000F\\T&\b\\B\u0018u\u000b]Tzt\u0001RT[g\u001dG\u001e";
        r0 = 81;
        r3 = r4;
        goto L_0x0009;
    L_0x03b5:
        r3[r2] = r1;
        r2 = 83;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@r`hM+j";
        r0 = 82;
        r3 = r4;
        goto L_0x0009;
    L_0x03c0:
        r3[r2] = r1;
        r2 = 84;
        r1 = "T\u000b_UYu\u000bW\u0010Og\u0005V\u0010Ti\rX\u0010\u0015&\u0003Z\\To\u001dVSWh\n@\n";
        r0 = 83;
        r3 = r4;
        goto L_0x0009;
    L_0x03cb:
        r3[r2] = r1;
        r2 = 85;
        r1 = "(\u001eFCPY\nVFQe\u000bZT";
        r0 = 84;
        r3 = r4;
        goto L_0x0009;
    L_0x03d6:
        r3[r2] = r1;
        r2 = 86;
        r1 = "S:u\u001d\u0000";
        r0 = 85;
        r3 = r4;
        goto L_0x0009;
    L_0x03e1:
        r3[r2] = r1;
        r2 = 87;
        r1 = "p\u000bAo[i\nV";
        r0 = 86;
        r3 = r4;
        goto L_0x0009;
    L_0x03ed:
        r3[r2] = r1;
        r2 = 88;
        r1 = "h\u000f^U";
        r0 = 87;
        r3 = r4;
        goto L_0x0009;
    L_0x03f8:
        r3[r2] = r1;
        r2 = 89;
        r1 = "v\u0005T";
        r0 = 88;
        r3 = r4;
        goto L_0x0009;
    L_0x0404:
        r3[r2] = r1;
        r2 = 90;
        r1 = "p\u000bAoVg\u0003V";
        r0 = 89;
        r3 = r4;
        goto L_0x0009;
    L_0x0410:
        r3[r2] = r1;
        r2 = 91;
        r1 = "K/p\u0010Yb\nA\u0010Qh\b\\\u001d\u0015+C\u0013";
        r0 = 90;
        r3 = r4;
        goto L_0x0009;
    L_0x041b:
        r3[r2] = r1;
        r2 = 92;
        r1 = "H\u0001\u0013DYt\tVD\u0018t\u000bPUQp\u000bA\u0011";
        r0 = 91;
        r3 = r4;
        goto L_0x0009;
    L_0x0426:
        r3[r2] = r1;
        r2 = 93;
        r1 = "?Y\u0004\u0004\\3XW\u0006\u00004\u000b\u0006\u0004\u0001e";
        r0 = 92;
        r3 = r4;
        goto L_0x0009;
    L_0x0431:
        r3[r2] = r1;
        r2 = 94;
        r1 = "棆津刃零戨&=w{\u0018本涕劓绯讙以硯〱炉凃柣睥试惵";
        r0 = 93;
        r3 = r4;
        goto L_0x0009;
    L_0x043d:
        r3[r2] = r1;
        r2 = 95;
        r1 = "L>FCP揖祔Ｉ缊尩给诏仐砱";
        r0 = 94;
        r3 = r4;
        goto L_0x0009;
    L_0x0448:
        r3[r2] = r1;
        r2 = 96;
        r1 = "g\rGYNo\u001aJ";
        r0 = 95;
        r3 = r4;
        goto L_0x0009;
    L_0x0453:
        r3[r2] = r1;
        r2 = 97;
        r1 = "E\u0001^@Wh\u000b]Dqh\b\\K";
        r0 = 96;
        r3 = r4;
        goto L_0x0009;
    L_0x045e:
        r3[r2] = r1;
        r2 = 98;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001e|g\u000b^_VU\u000bAFQe\u000b";
        r0 = 97;
        r3 = r4;
        goto L_0x0009;
    L_0x0469:
        r3[r2] = r1;
        r2 = 99;
        r1 = "c\u0000WxWs\u001c";
        r0 = 98;
        r3 = r4;
        goto L_0x0009;
    L_0x0474:
        r3[r2] = r1;
        r2 = 100;
        r1 = "&\u0010\u0013";
        r0 = 99;
        r3 = r4;
        goto L_0x0009;
    L_0x047f:
        r3[r2] = r1;
        r2 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r1 = "u\u001aRBLK\u0007]C";
        r0 = 100;
        r3 = r4;
        goto L_0x0009;
    L_0x048b:
        r3[r2] = r1;
        r2 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r1 = "E\u001bAB]h\u001a\u0013DQk\u000b\u0013YK&\u0007]\u0010Ln\u000b\u0013BYh\tV\u0010W`N@YTc\u0000PU\u0018r\u0007^U\u0018+N";
        r0 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0496:
        r3[r2] = r1;
        r2 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        r1 = "u\u001aRBLN\u0001FB";
        r0 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04a2:
        r3[r2] = r1;
        r2 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
        r1 = "c\u0000WDuo\u0000@";
        r0 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04ad:
        r3[r2] = r1;
        r2 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        r1 = "A\u0001G\u0010Kb\rRB\\&\bZ\\]&\u001dRF]bNWUNo\rVy\\&C\u0013";
        r0 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04b8:
        r3[r2] = r1;
        r2 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        r1 = "a\u000bG\u0010\\c\u0018ZS]&\u0007W\u0010\u0018u\n\u0013SYt\n\u0013VQj\u000b\u0013@Yr\u0006\u0013VYo\u0002";
        r0 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04c3:
        r3[r2] = r1;
        r2 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        r1 = "H\u0001\u0013DYt\tVD\u0018u\u000bAFQe\u000b@\u0011";
        r0 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04ce:
        r3[r2] = r1;
        r2 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        r1 = "A\u0007EU\u0018s\u001e\u0013DW&\u001dGQJrNR@H&\b\\B\u0018o\u0000EQTo\n\u0013@Yt\u000f^C\u0018+NCQ[m\u000fTUvg\u0003V\n";
        r0 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04d9:
        r3[r2] = r1;
        r2 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        r1 = "H;|\u0018e\u0001]D]~\u001a";
        r0 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04e4:
        r3[r2] = r1;
        r2 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        r1 = "]^n\u001a";
        r0 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04ef:
        r3[r2] = r1;
        r2 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        r1 = "]^\u001e\te}_\u0006M";
        r0 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x04fa:
        r3[r2] = r1;
        r2 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
        r1 = "]\u000f\u001eV\b+Wr\u001d~[\u0015\u0002\u0004E";
        r0 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0505:
        r3[r2] = r1;
        r2 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@~cY'w";
        r0 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0510:
        r3[r2] = r1;
        r2 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@~ukU/tu";
        r0 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x051b:
        r3[r2] = r1;
        r2 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@pvR+}dgR7cu";
        r0 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0526:
        r3[r2] = r1;
        r2 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        r1 = "U\u000b]T\u0018d\u001c\\Q\\e\u000f@D\u0018r\u0001\u0013QHvT\u0013";
        r0 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0531:
        r3[r2] = r1;
        r2 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@gylJ+";
        r0 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x053c:
        r3[r2] = r1;
        r2 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001euC=`qC1au{C'eu|";
        r0 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0547:
        r3[r2] = r1;
        r2 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@uytC1cqlN";
        r0 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0552:
        r3[r2] = r1;
        r2 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        r1 = "s\u001aU\u001d\u0000";
        r0 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x055e:
        r3[r2] = r1;
        r2 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        r1 = "k\u0001WUT";
        r0 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0569:
        r3[r2] = r1;
        r2 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        r1 = "b\u000bEY[c";
        r0 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0574:
        r3[r2] = r1;
        r2 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r1 = "h\u000bGGWt\u0005";
        r0 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x057f:
        r3[r2] = r1;
        r2 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        r1 = "s\u001c_";
        r0 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x058b:
        r3[r2] = r1;
        r2 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        r1 = "e\u0006R^Vc\u0002";
        r0 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0596:
        r3[r2] = r1;
        r2 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        r1 = "g\u0000WBWo\n`TSP\u000bACQi\u0000";
        r0 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05a1:
        r3[r2] = r1;
        r2 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        r1 = "L>fcpY/c`sC7";
        r0 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05ac:
        r3[r2] = r1;
        r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r1 = "PY\u001d\u0001";
        r0 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05b7:
        r3[r2] = r1;
        r2 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r1 = "^\u0007R_Uo";
        r0 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05c2:
        r3[r2] = r1;
        r2 = 130; // 0x82 float:1.82E-43 double:6.4E-322;
        r1 = "t\u0001\u001d]Qs\u0007\u001dEQ(\u0018VBKo\u0001]\u001eVg\u0003V";
        r0 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05ce:
        r3[r2] = r1;
        r2 = 131; // 0x83 float:1.84E-43 double:6.47E-322;
        r1 = "t\u0001\u001d@Ji\nFSL(\fAQVb";
        r0 = 130; // 0x82 float:1.82E-43 double:6.4E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05da:
        r3[r2] = r1;
        r2 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r1 = "d\u001cR^\\&S\u0013";
        r0 = 131; // 0x83 float:1.84E-43 double:6.47E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05e5:
        r3[r2] = r1;
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        r1 = "t\u0001\u001dRMo\u0002W\u001eNc\u001c@YWh@Z^[t\u000b^UVr\u000f_";
        r0 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05f1:
        r3[r2] = r1;
        r2 = 134; // 0x86 float:1.88E-43 double:6.6E-322;
        r1 = "g\rGYWhTPX]e\u0005eQTo\n~QVo\bVCL";
        r0 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x05fc:
        r3[r2] = r1;
        r2 = 135; // 0x87 float:1.89E-43 double:6.67E-322;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@qwR1puV\"vd}B";
        r0 = 134; // 0x86 float:1.88E-43 double:6.6E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0607:
        r3[r2] = r1;
        r2 = 136; // 0x88 float:1.9E-43 double:6.7E-322;
        r1 = "R\u0006V\u0010Hc\u001c^YKu\u0001Z^\u0018o\u001d\u0013B]w\u001bZB]bN\u001e\u0010Yh\nA_Qb@CUJk\u0007@CQi\u0000\u001dgjO:vo}^:vbvG\"lclI<rw}";
        r0 = 135; // 0x87 float:1.89E-43 double:6.67E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0612:
        r3[r2] = r1;
        r2 = 137; // 0x89 float:1.92E-43 double:6.77E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bN@UJp\u0007PU\u0002&";
        r0 = 136; // 0x88 float:1.9E-43 double:6.7E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x061d:
        r3[r2] = r1;
        r2 = 138; // 0x8a float:1.93E-43 double:6.8E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNZ^Lc\u0000G\u0010^o\u0002GUJ&\b\\B\u0018V\u001b@Xkc\u001cEY[cT\u0013SV(\u0004CEKn@R^\\t\u0001ZT\u0016o\u0000GUVr@auO=guj";
        r0 = 137; // 0x89 float:1.92E-43 double:6.77E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0628:
        r3[r2] = r1;
        r2 = 139; // 0x8b float:1.95E-43 double:6.87E-322;
        r1 = "Q\u000b\u0013B]e\u0001^]]h\n\u0013IWsNRT\\&\u001a[U\u0018v\u000bA]Qu\u001dZ_V&C\u0013";
        r0 = 138; // 0x8a float:1.93E-43 double:6.8E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0633:
        r3[r2] = r1;
        r2 = 140; // 0x8c float:1.96E-43 double:6.9E-322;
        r1 = "R\u0006V\u0010Hc\u001c^YKu\u0001Z^\u0018o\u001d\u0013B]w\u001bZB]bN\u001e\u0010Yh\nA_Qb@CUJk\u0007@CQi\u0000\u001dgjO:vokC:gyvA=";
        r0 = 139; // 0x8b float:1.95E-43 double:6.87E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x063e:
        r3[r2] = r1;
        r2 = 141; // 0x8d float:1.98E-43 double:6.97E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNZ^Lc\u0000G\u0010^o\u0002GUJ&\b\\B\u0018V\u001b@Xjc\rVYNc\u001c\t\u0010[h@Y@Mu\u0006\u001dQVb\u001c\\Y\\(\u0007]D]h\u001a\u001d~wR'uy{G:zvY<vs}O8vtgV<|ha";
        r0 = 140; // 0x8c float:1.96E-43 double:6.9E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0649:
        r3[r2] = r1;
        r2 = 142; // 0x8e float:1.99E-43 double:7.0E-322;
        r1 = "R\u0006V\u0010Hc\u001c^YKu\u0001Z^\u0018o\u001d\u0013B]w\u001bZB]bN\u001e\u0010";
        r0 = 141; // 0x8d float:1.98E-43 double:6.97E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0654:
        r3[r2] = r1;
        r2 = 143; // 0x8f float:2.0E-43 double:7.07E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNZ^Lc\u0000G\u0010^o\u0002GUJ&T\u0013SV(\u0004CEKn@R^\\t\u0001ZT\u0016o\u0000GUVr@auhI<g";
        r0 = 142; // 0x8e float:1.99E-43 double:7.0E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x065f:
        r3[r2] = r1;
        r2 = 144; // 0x90 float:2.02E-43 double:7.1E-322;
        r1 = "V\u001b@Xjc\rVYNc\u001c\u0013CPi\u001b_T\u0018h\u0001G\u0010Pg\u0018V\u0010Qh\u001aV^L&\bZ\\Lc\u001c\u0013\u001d\u0015&\u000f]TJi\u0007W\u001eQh\u001aV^L(\u000fPDQi\u0000\u001drwI:lswK>ulC*\u001f\u0010hj\u000bRC]&\u001cV]Wp\u000b\u0013DPcNZ^Lc\u0000G\u0010^o\u0002GUJ&\u0007]\u0010yh\nA_Qb#R^Q`\u000b@D\u0016~\u0003_";
        r0 = 143; // 0x8f float:2.0E-43 double:7.07E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x066a:
        r3[r2] = r1;
        r2 = 145; // 0x91 float:2.03E-43 double:7.16E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001ejC)zclC<";
        r0 = 144; // 0x90 float:2.02E-43 double:7.1E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0675:
        r3[r2] = r1;
        r2 = 146; // 0x92 float:2.05E-43 double:7.2E-322;
        r1 = "(\u001eVBUo\u001d@YWh@y`mU&l}}U=rw}";
        r0 = 145; // 0x91 float:2.03E-43 double:7.16E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0680:
        r3[r2] = r1;
        r2 = 147; // 0x93 float:2.06E-43 double:7.26E-322;
        r1 = "B\u000bEUTi\u001eVB\u0018u\u0006\\ETbN@UL&/C@sc\u0017\u0013YV&/]TJi\u0007W}Yh\u0007UUKr@K]T";
        r0 = 146; // 0x92 float:2.05E-43 double:7.2E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x068b:
        r3[r2] = r1;
        r2 = 148; // 0x94 float:2.07E-43 double:7.3E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNAU[c\u0007EUJ<N";
        r0 = 147; // 0x93 float:2.06E-43 double:7.26E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0696:
        r3[r2] = r1;
        r2 = 149; // 0x95 float:2.09E-43 double:7.36E-322;
        r1 = "H\u0001\u0013DYt\tVD\u0018g\rGYNo\u001aZC\u0019";
        r0 = 148; // 0x94 float:2.07E-43 double:7.3E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06a1:
        r3[r2] = r1;
        r2 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013YVr\u000b]D\u0018`\u0007_D]tNU_J&*RUUi\u0000`UJp\u0007PU\u0002&\r]\u001eRv\u001b@X\u0016g\u0000WBWo\n\u001dYVr\u000b]D\u0016B\u000fV]Wh=VBNo\rV";
        r0 = 149; // 0x95 float:2.09E-43 double:7.36E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06ac:
        r3[r2] = r1;
        r2 = 151; // 0x97 float:2.12E-43 double:7.46E-322;
        r1 = "_\u0001F\u0010Kn\u0001F\\\\&\u0003R[]&\u0003RYV&\u000fPDQp\u0007GI\u0018c\u0016GUVb\u001d\u0013yVu\u001aAEUc\u0000GU\\G\rGYNo\u001aJ\u0010\u0010L>FCP/B\u0013_Ln\u000bAGQu\u000b\u0013IWsNDYTjN]_L&\u001dVU\u0018s\u001dVB\u0018e\u0002ZSS&\u000f]T\u0018s\u001dVB\u0018g\rGYNcNGYUcN@DYt\u001a\u0013_V&\u001cV@Wt\u001a\u0013YV&>\\BLg\u0002\u001d\u0010";
        r0 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06b7:
        r3[r2] = r1;
        r2 = 152; // 0x98 float:2.13E-43 double:7.5E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNRSLo\u0018ZDA<N";
        r0 = 151; // 0x97 float:2.12E-43 double:7.46E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06c2:
        r3[r2] = r1;
        r2 = 153; // 0x99 float:2.14E-43 double:7.56E-322;
        r1 = "*N\\DPc\u001cDYKcNJ_M&\rR^\u0018h\u0001G\u0010Ti\rRD]&\u001a[U\u0018b\u000bEY[c\u001d\u001d";
        r0 = 152; // 0x98 float:2.13E-43 double:7.5E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06cd:
        r3[r2] = r1;
        r2 = 154; // 0x9a float:2.16E-43 double:7.6E-322;
        r1 = "R\u0006V\u0010Hc\u001c^YKu\u0007\\^\u0018u\u0006\\ETbNQU\u0018b\u000bUYVc\n\u0013\u001d\u0018";
        r0 = 153; // 0x99 float:2.14E-43 double:7.56E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06d8:
        r3[r2] = r1;
        r2 = 155; // 0x9b float:2.17E-43 double:7.66E-322;
        r1 = "G\u0000WBWo\n~QVo\bVCL(\u0016^\\\u0018k\u0007@CQh\t\u0013B]w\u001bZB]bNZ^Lc\u0000G\u0010^o\u0002GUJ&\b\\B\u0018V\u001b@Xye\u001aZFQr\u0017\t\u0010[h@Y@Mu\u0006\u001dQVb\u001c\\Y\\(\u001bZ\u001ehs\u001d[q[r\u0007EYL";
        r0 = 154; // 0x9a float:2.16E-43 double:7.6E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06e3:
        r3[r2] = r1;
        r2 = 156; // 0x9c float:2.19E-43 double:7.7E-322;
        r1 = "e\u0000\u001dZHs\u001d[\u001eYh\nA_Qb@Z^Lc\u0000G\u001ejC>|bl";
        r0 = 155; // 0x9b float:2.17E-43 double:7.66E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06ee:
        r3[r2] = r1;
        r2 = 157; // 0x9d float:2.2E-43 double:7.76E-322;
        r1 = "v\u0001DUJ";
        r0 = 156; // 0x9c float:2.19E-43 double:7.7E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x06fa:
        r3[r2] = r1;
        r2 = 158; // 0x9e float:2.21E-43 double:7.8E-322;
        r1 = "Y$cEKn";
        r0 = 157; // 0x9d float:2.2E-43 double:7.76E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0705:
        r3[r2] = r1;
        r2 = 159; // 0x9f float:2.23E-43 double:7.86E-322;
        r1 = "u\u001aRDMu";
        r0 = 158; // 0x9e float:2.21E-43 double:7.8E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0711:
        r3[r2] = r1;
        r2 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        r1 = "g\u0000WBWo\n\u001dYVr\u000b]D\u0016g\rGYWh@qqlR+aigE&r~C*";
        r0 = 159; // 0x9f float:2.23E-43 double:7.86E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x071c:
        r3[r2] = r1;
        r2 = 161; // 0xa1 float:2.26E-43 double:7.95E-322;
        r1 = "v\u0002FW_c\n";
        r0 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0728:
        r3[r2] = r1;
        r2 = 162; // 0xa2 float:2.27E-43 double:8.0E-322;
        r1 = "E \u000eqVb\u001c\\Y\\&*VRMaB|\ryh\nA_QbBp\rmU";
        r0 = 161; // 0xa1 float:2.26E-43 double:7.95E-322;
        r3 = r4;
        goto L_0x0009;
    L_0x0733:
        r3[r2] = r1;
        z = r4;
        r0 = 1;
        a = r0;
        r2 = new java.util.ArrayList;
        r2.<init>();
        b = r2;
        r1 = "5[\u000b\u0006\u000f5^\u0002\u0003\u000f?[\u000b\t\r";
        r0 = -1;
    L_0x0744:
        r1 = r1.toCharArray();
        r3 = r1.length;
        r4 = 0;
        r5 = 1;
        if (r3 > r5) goto L_0x0783;
    L_0x074d:
        r5 = r1;
        r6 = r4;
        r11 = r3;
        r3 = r1;
        r1 = r11;
    L_0x0752:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x0776;
            case 1: goto L_0x0778;
            case 2: goto L_0x077b;
            case 3: goto L_0x077e;
            default: goto L_0x0759;
        };
    L_0x0759:
        r7 = 56;
    L_0x075b:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r1 != 0) goto L_0x0781;
    L_0x0763:
        r3 = r5;
        r6 = r4;
        r4 = r1;
        goto L_0x0752;
    L_0x0767:
        r9 = 6;
        goto L_0x0020;
    L_0x076a:
        r9 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x0020;
    L_0x076e:
        r9 = 51;
        goto L_0x0020;
    L_0x0772:
        r9 = 48;
        goto L_0x0020;
    L_0x0776:
        r7 = 6;
        goto L_0x075b;
    L_0x0778:
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x075b;
    L_0x077b:
        r7 = 51;
        goto L_0x075b;
    L_0x077e:
        r7 = 48;
        goto L_0x075b;
    L_0x0781:
        r3 = r1;
        r1 = r5;
    L_0x0783:
        if (r3 > r4) goto L_0x074d;
    L_0x0785:
        r3 = new java.lang.String;
        r3.<init>(r1);
        r1 = r3.intern();
        switch(r0) {
            case 0: goto L_0x0812;
            case 1: goto L_0x081c;
            case 2: goto L_0x0826;
            case 3: goto L_0x0830;
            case 4: goto L_0x083a;
            case 5: goto L_0x0849;
            case 6: goto L_0x0853;
            case 7: goto L_0x085d;
            default: goto L_0x0791;
        };
    L_0x0791:
        r2.add(r1);
        r2 = b;
        r1 = "6^\u0007\t\u0001?^\u0002\u0000\u000e2^\u0003\u0000\b";
        r0 = -1;
    L_0x0799:
        r1 = r1.toCharArray();
        r3 = r1.length;
        r4 = 0;
        r5 = 1;
        if (r3 > r5) goto L_0x07c9;
    L_0x07a2:
        r5 = r1;
        r6 = r4;
        r11 = r3;
        r3 = r1;
        r1 = r11;
    L_0x07a7:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x07bc;
            case 1: goto L_0x07be;
            case 2: goto L_0x07c1;
            case 3: goto L_0x07c4;
            default: goto L_0x07ae;
        };
    L_0x07ae:
        r7 = 56;
    L_0x07b0:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r1 != 0) goto L_0x07c7;
    L_0x07b8:
        r3 = r5;
        r6 = r4;
        r4 = r1;
        goto L_0x07a7;
    L_0x07bc:
        r7 = 6;
        goto L_0x07b0;
    L_0x07be:
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x07b0;
    L_0x07c1:
        r7 = 51;
        goto L_0x07b0;
    L_0x07c4:
        r7 = 48;
        goto L_0x07b0;
    L_0x07c7:
        r3 = r1;
        r1 = r5;
    L_0x07c9:
        if (r3 > r4) goto L_0x07a2;
    L_0x07cb:
        r3 = new java.lang.String;
        r3.<init>(r1);
        r1 = r3.intern();
        switch(r0) {
            case 0: goto L_0x07e0;
            case 1: goto L_0x07e9;
            default: goto L_0x07d7;
        };
    L_0x07d7:
        r2.add(r1);
        r2 = b;
        r1 = "6^\u0003\u0000\b6^\u0003\u0000\b6^\u0003\u0000";
        r0 = 0;
        goto L_0x0799;
    L_0x07e0:
        r2.add(r1);
        r2 = b;
        r1 = "6^\u0003\u0000\b6^\u0003\u0000\b6^\u0003\u0000\b";
        r0 = 1;
        goto L_0x0799;
    L_0x07e9:
        r2.add(r1);
        r0 = new javax.security.auth.x500.X500Principal;
        r1 = z;
        r2 = 162; // 0xa2 float:2.27E-43 double:8.0E-322;
        r1 = r1[r2];
        r0.<init>(r1);
        c = r0;
        r0 = 0;
        d = r0;
        r0 = new java.util.ArrayList;
        r0.<init>();
        e = r0;
        r0 = new java.util.ArrayList;
        r0.<init>();
        f = r0;
        r2 = e;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eqH:vbvC:";
        r0 = 0;
        goto L_0x0744;
    L_0x0812:
        r2.add(r1);
        r2 = e;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eoG%votI-x";
        r0 = 1;
        goto L_0x0744;
    L_0x081c:
        r2.add(r1);
        r2 = e;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eyE-vckY vdoI<xokR/gu";
        r0 = 2;
        goto L_0x0744;
    L_0x0826:
        r2.add(r1);
        r2 = f;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001enO,aqlC";
        r0 = 3;
        goto L_0x0744;
    L_0x0830:
        r2.add(r1);
        r2 = f;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001e{N/}w}Y9zvqY=gqlC";
        r0 = 4;
        goto L_0x0744;
    L_0x083a:
        r2.add(r1);
        r2 = new java.util.ArrayList;
        r2.<init>();
        g = r2;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eyE-vckY(z~}Y\"|syR'|~";
        r0 = 5;
        goto L_0x0744;
    L_0x0849:
        r2.add(r1);
        r2 = g;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eyE-vckY-|qjU+l|wE/gywH";
        r0 = 6;
        goto L_0x0744;
    L_0x0853:
        r2.add(r1);
        r2 = g;
        r1 = "g\u0000WBWo\n\u001d@]t\u0003ZCKo\u0001]\u001eyE-vckY\"|syR'|~gC6gbyY-|}uG wc";
        r0 = 7;
        goto L_0x0744;
    L_0x085d:
        r2.add(r1);
        r0 = g;
        r1 = z;
        r2 = 7;
        r1 = r1[r2];
        r0.add(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.a.<clinit>():void");
    }

    private static boolean A(Context context) {
        try {
            Intent intent = new Intent(z[47]);
            intent.setPackage(context.getPackageName());
            intent.addCategory(z[48]);
            ActivityInfo activityInfo = context.getPackageManager().resolveActivity(intent, 0).activityInfo;
            if (activityInfo == null) {
                return false;
            }
            Class cls = Class.forName(activityInfo.name);
            return cls == null ? false : InstrumentedActivity.class.isAssignableFrom(cls) || InstrumentedListActivity.class.isAssignableFrom(cls);
        } catch (Exception e) {
            return false;
        }
    }

    private static void B(Context context) {
        z.c();
        if (h == null) {
            h = new PushReceiver();
        }
        context.registerReceiver(h, new IntentFilter(z[58]));
        context.registerReceiver(h, new IntentFilter(z[61]));
        try {
            IntentFilter intentFilter = new IntentFilter(z[60]);
            intentFilter.addDataScheme(z[59]);
            IntentFilter intentFilter2 = new IntentFilter(z[56]);
            intentFilter2.addDataScheme(z[59]);
            IntentFilter intentFilter3 = new IntentFilter(z[57]);
            intentFilter3.setPriority(1000);
            intentFilter3.addCategory(context.getPackageName());
            context.registerReceiver(h, intentFilter);
            context.registerReceiver(h, intentFilter2);
            context.registerReceiver(h, intentFilter3);
        } catch (Exception e) {
        }
    }

    public static int a(Context context, float f) {
        return (int) (context.getResources().getDisplayMetrics().density * f);
    }

    public static Intent a(Context context, c cVar) {
        Intent intent = new Intent(context, PopWinActivity.class);
        intent.putExtra(z[25], cVar);
        intent.addFlags(335544320);
        return intent;
    }

    public static Intent a(Context context, c cVar, boolean z) {
        Intent intent = new Intent();
        intent.putExtra(z[27], z);
        intent.putExtra(z[25], cVar);
        intent.setAction(z[26]);
        intent.addCategory(context.getPackageName());
        intent.addFlags(335544320);
        return intent;
    }

    public static String a(int i) {
        return (i == 1 || i == 2 || i == 8) ? z[54] : (i == 4 || i == 7 || i == 5 || i == 6) ? z[53] : i == 13 ? z[51] : z[52];
    }

    public static String a(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return z[29];
        }
        int i = displayMetrics.widthPixels;
        return i + "*" + displayMetrics.heightPixels;
    }

    public static String a(Context context, String str) {
        Object obj;
        String str2 = VERSION.RELEASE + "," + Integer.toString(VERSION.SDK_INT);
        String str3 = Build.MODEL;
        String a = s.a(context, z[79], z[76]);
        String str4 = Build.DEVICE;
        String F = cn.jpush.android.a.F();
        if (ai.a(F)) {
            F = " ";
        }
        StringBuilder append = new StringBuilder().append(str2).append(z[75]).append(str3).append(z[75]).append(a).append(z[75]).append(str4).append(z[75]).append(F).append(z[75]).append(str).append(z[75]);
        F = context.getApplicationInfo().sourceDir;
        if (ai.a(F)) {
            z.e();
            obj = null;
        } else {
            new StringBuilder(z[80]).append(F);
            z.b();
            if (F.startsWith(z[78])) {
                int i = 1;
            } else if (F.startsWith(z[77])) {
                obj = null;
            } else {
                z.c();
                obj = null;
            }
        }
        return append.append(obj != null ? 1 : 0).append(z[75]).append(a(context)).toString();
    }

    public static String a(String str) {
        int i = 0;
        try {
            MessageDigest instance = MessageDigest.getInstance(z[15]);
            char[] toCharArray = str.toCharArray();
            byte[] bArr = new byte[toCharArray.length];
            for (int i2 = 0; i2 < toCharArray.length; i2++) {
                bArr[i2] = (byte) toCharArray[i2];
            }
            byte[] digest = instance.digest(bArr);
            StringBuffer stringBuffer = new StringBuffer();
            while (i < digest.length) {
                int i3 = digest[i] & 255;
                if (i3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i3));
                i++;
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            z.b();
            return "";
        }
    }

    public static String a(byte[] bArr) {
        try {
            byte[] digest = MessageDigest.getInstance(z[15]).digest(bArr);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            z.b();
            return "";
        }
    }

    public static JSONObject a(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[10], str2);
            jSONObject.put(z[11], str);
            jSONObject.put(z[9], cn.jpush.android.a.j());
            return jSONObject;
        } catch (Exception e) {
            e.getMessage();
            z.e();
            return null;
        }
    }

    public static JSONObject a(String str, JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[10], jSONArray);
            jSONObject.put(z[11], str);
            jSONObject.put(z[9], cn.jpush.android.a.j());
            return jSONObject;
        } catch (Exception e) {
            e.getMessage();
            z.e();
            return null;
        }
    }

    public static void a(Context context, String str, Bundle bundle) {
        if (bundle == null) {
            z.e(z[20], z[82]);
            return;
        }
        Intent intent = new Intent(str);
        bundle.putString(z[83], cn.jpush.android.a.A());
        intent.putExtras(bundle);
        intent.addCategory(context.getPackageName());
        context.sendBroadcast(intent, String.format(z[81], new Object[]{r1}));
    }

    public static void a(Context context, String str, String str2, int i) {
        Uri parse = Uri.parse(str2);
        if (parse == null) {
            new StringBuilder(z[34]).append(str2);
            z.b();
            return;
        }
        Parcelable intent = new Intent(z[23], parse);
        intent.setFlags(335544320);
        Parcelable fromContext = ShortcutIconResource.fromContext(context, i);
        Intent intent2 = new Intent(z[30]);
        intent2.putExtra(z[33], false);
        intent2.putExtra(z[35], str);
        intent2.putExtra(z[32], intent);
        intent2.putExtra(z[31], fromContext);
        context.sendBroadcast(intent2);
    }

    public static void a(Context context, String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        if (str2 != null) {
            bundle.putString(str2, str3);
        }
        a(context, str, bundle);
    }

    public static void a(Context context, String str, String str2, byte[] bArr) {
        Bundle bundle = new Bundle();
        if (!(str2 == null || bArr == null)) {
            bundle.putSerializable(str2, bArr);
        }
        a(context, str, bundle);
    }

    public static void a(Context context, boolean z) {
        z.b(z[20], z[21]);
        try {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(z[17], z);
            bundle.putString(z[22], jSONObject.toString());
            a(context, z[19], bundle);
        } catch (JSONException e) {
            new StringBuilder(z[18]).append(e.getMessage());
            z.d();
        }
    }

    public static void a(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName(z[86]);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(2);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
    }

    public static boolean a() {
        boolean equals = Environment.getExternalStorageState().equals(z[74]);
        if (!equals) {
            z.b();
        }
        return equals;
    }

    private static boolean a(Context context, Class<?> cls) {
        if (!context.getPackageManager().queryBroadcastReceivers(new Intent(context, cls), 0).isEmpty()) {
            return true;
        }
        z.e(z[20], z[92]);
        return false;
    }

    public static boolean a(Context context, String str, String str2) {
        if (context == null) {
            throw new IllegalArgumentException(z[109]);
        } else if (TextUtils.isEmpty(str)) {
            new StringBuilder(z[108]).append(str);
            z.d();
            return false;
        } else {
            Intent l = l(context, str);
            if (l == null) {
                try {
                    if (TextUtils.isEmpty(str2)) {
                        z.b();
                        return false;
                    }
                    l = new Intent();
                    l.setClassName(str, str2);
                    l.addCategory(z[48]);
                } catch (Exception e) {
                    z.g();
                    return false;
                }
            }
            l.setFlags(268435456);
            context.startActivity(l);
            return true;
        }
    }

    public static boolean a(Context context, String str, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(str);
        intent.addCategory(context.getPackageName());
        return !packageManager.queryBroadcastReceivers(intent, 0).isEmpty();
    }

    public static String b(Context context, String str) {
        String str2 = VERSION.RELEASE + "," + Integer.toString(VERSION.SDK_INT);
        String str3 = Build.MODEL;
        String a = s.a(context, z[79], z[76]);
        String str4 = Build.DEVICE;
        Object F = cn.jpush.android.a.F();
        if (ai.a(F)) {
            F = " ";
        }
        String c = c(context);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[TransportMediator.KEYCODE_MEDIA_PLAY], str2);
            jSONObject.put(z[LeMessageIds.MSG_ALBUM_FETCH_PLAY_NEXT_CONTROLLER], str3);
            jSONObject.put(z[76], a);
            jSONObject.put(z[122], str4);
            jSONObject.put(z[125], F);
            jSONObject.put(z[123], c);
            jSONObject.put(z[124], str);
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }

    public static String b(String str) {
        try {
            byte[] digest = MessageDigest.getInstance(z[15]).digest(str.getBytes(z[LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT]));
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            z.b();
            return "";
        }
    }

    public static void b() {
        try {
            WakeLock b = t.a().b();
            if (b == null) {
                return;
            }
            if (b.isHeld()) {
                try {
                    b.release();
                    long currentTimeMillis = System.currentTimeMillis() - d;
                    d = 0;
                    new StringBuilder(z[84]).append(currentTimeMillis);
                    z.a();
                    return;
                } catch (RuntimeException e) {
                    z.h();
                    return;
                }
            }
            z.a();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            z.b();
        } catch (Exception e3) {
            e3.printStackTrace();
            z.b();
        }
    }

    public static void b(Context context, c cVar) {
        try {
            Intent intent = new Intent(z[LiveType.PLAY_LIVE_HK_MUSIC]);
            intent.putExtra(z[83], cVar.n);
            intent.putExtra(z[114], cVar.i);
            intent.putExtra(z[115], cVar.j);
            intent.putExtra(z[117], cVar.k);
            intent.putExtra(z[0], cVar.l);
            intent.putExtra(z[113], cVar.c);
            if (cVar.e()) {
                intent.putExtra(z[119], cVar.C);
            }
            intent.addCategory(cVar.m);
            context.sendBroadcast(intent, String.format(z[81], new Object[]{cVar.m}));
            new StringBuilder(z[116]).append(String.format(z[81], new Object[]{cVar.m}));
            z.c();
            g.a(cVar.c, 1018, context);
        } catch (Exception e) {
            e.getMessage();
            z.e();
        }
    }

    public static void b(Context context, String str, String str2) {
        a(context, str, z[0], str2);
    }

    public static void b(Context context, String str, String str2, int i) {
        Notification notification;
        Intent intent = new Intent(context, PushReceiver.class);
        intent.setAction(z[69]);
        intent.putExtra(z[70], true);
        intent.putExtra(z[96], str2);
        intent.putExtra(z[11], i);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(z[68]);
        int i2 = -1;
        try {
            i2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 256).applicationInfo.icon;
        } catch (NameNotFoundException e) {
        }
        if (i2 < 0) {
            i2 = 17301586;
        }
        Object obj = z[95];
        Object obj2 = z[94];
        long currentTimeMillis = System.currentTimeMillis();
        if (VERSION.SDK_INT >= 11) {
            notification = new Builder(context.getApplicationContext()).setContentTitle(obj).setContentText(obj2).setContentIntent(broadcast).setSmallIcon(i2).setTicker(str).setWhen(currentTimeMillis).getNotification();
            notification.flags = 34;
        } else {
            Notification notification2 = new Notification(i2, str, currentTimeMillis);
            notification2.flags = 34;
            m.a(notification2, context, obj, obj2, broadcast);
            notification = notification2;
        }
        if (notification != null) {
            notificationManager.notify(str.hashCode(), notification);
        }
    }

    public static boolean b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(z[45])).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean b(Context context, Class<?> cls) {
        if (!context.getPackageManager().queryIntentServices(new Intent(context, cls), 0).isEmpty()) {
            return true;
        }
        z.e(z[20], z[107]);
        return false;
    }

    private static boolean b(Context context, String str, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(str);
        if (z) {
            intent.addCategory(context.getPackageName());
        }
        return !packageManager.queryIntentServices(intent, 0).isEmpty();
    }

    public static int c(String str) {
        String[] split = str.split(z[38]);
        return Integer.parseInt(split[2]) + ((Integer.parseInt(split[0]) << 16) + (Integer.parseInt(split[1]) << 8));
    }

    public static String c() {
        StringBuffer stringBuffer = new StringBuffer();
        if (new File(z[37]).exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(z[37])));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else if (readLine.contains(z[36])) {
                        int indexOf = readLine.indexOf(NetworkUtils.DELIMITER_COLON);
                        if (indexOf >= 0 && indexOf < readLine.length() - 1) {
                            stringBuffer.append(readLine.substring(indexOf + 1).trim());
                        }
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
            }
        }
        return stringBuffer.toString();
    }

    public static String c(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(z[45])).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return z[62];
            }
            String typeName = activeNetworkInfo.getTypeName();
            String subtypeName = activeNetworkInfo.getSubtypeName();
            return typeName == null ? z[62] : subtypeName != null ? typeName + "," + subtypeName : typeName;
        } catch (Exception e) {
            e.printStackTrace();
            return z[62];
        }
    }

    public static void c(Context context, String str, String str2) {
        if (g(context)) {
            Notification notification;
            z.b();
            Intent intent = new Intent(context, PushReceiver.class);
            intent.setAction(z[69]);
            intent.putExtra(z[70], true);
            intent.putExtra(z[71], str2);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(z[68]);
            int i = -1;
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 256).applicationInfo.icon;
            } catch (NameNotFoundException e) {
            }
            if (i < 0) {
                i = 17301586;
            }
            Object obj = z[67];
            Object obj2 = z[66];
            long currentTimeMillis = System.currentTimeMillis();
            if (VERSION.SDK_INT >= 11) {
                notification = new Builder(context.getApplicationContext()).setContentTitle(obj).setContentText(obj2).setContentIntent(broadcast).setSmallIcon(i).setTicker(str).setWhen(currentTimeMillis).getNotification();
                notification.flags = 34;
            } else {
                Notification notification2 = new Notification(i, str, currentTimeMillis);
                notification2.flags = 34;
                m.a(notification2, context, obj, obj2, broadcast);
                notification = notification2;
            }
            if (notification != null) {
                notificationManager.notify(str.hashCode(), notification);
            }
        }
    }

    public static boolean c(Context context, String str) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
                        return true;
                    }
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(z[28]);
    }

    public static String d() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            z.d();
            e.printStackTrace();
        }
        return "";
    }

    public static String d(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(z[45])).getActiveNetworkInfo();
            return activeNetworkInfo == null ? "" : activeNetworkInfo.getTypeName().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean d(Context context, String str) {
        try {
            context.getPackageManager().getReceiverInfo(new ComponentName(context.getPackageName(), str), 128);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private static boolean d(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(str2);
        intent.setPackage(context.getPackageName());
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(intent, 0)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && activityInfo.name.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean d(String str) {
        if (ai.a(str) || str.length() < 10) {
            return false;
        }
        int i = 0;
        while (i < b.size()) {
            if (str.equals(b.get(i)) || str.startsWith((String) b.get(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    private static String e() {
        String str = null;
        try {
            str = Environment.getExternalStorageDirectory().getPath();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e2) {
        }
        return !ai.a(str) ? str + z[46] : str;
    }

    private static String e(String str) {
        FileOutputStream fileOutputStream;
        Throwable th;
        String e = e();
        if (ai.a(e)) {
            z.e();
            return null;
        }
        File file = new File(e);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e2) {
                z.i();
            }
        }
        if (ai.a(f())) {
            z.e(z[20], z[106]);
            return null;
        }
        file = new File(e + z[85]);
        if (file.exists()) {
            try {
                file.delete();
            } catch (SecurityException e3) {
                return null;
            }
        }
        try {
            file.createNewFile();
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.flush();
                    z.c();
                    if (fileOutputStream == null) {
                        return str;
                    }
                    try {
                        fileOutputStream.close();
                        return str;
                    } catch (IOException e4) {
                        return str;
                    }
                } catch (IOException e5) {
                    try {
                        z.i();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e6) {
                            }
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e7) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (IOException e8) {
                fileOutputStream = null;
                z.i();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return null;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                fileOutputStream = null;
                th = th4;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e9) {
            z.i();
            return null;
        }
    }

    public static void e(Context context) {
        h(context, null);
    }

    public static void e(Context context, String str) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction(z[23]);
        intent.setDataAndType(Uri.fromFile(new File(str)), z[24]);
        context.startActivity(intent);
    }

    public static int f(Context context) {
        Intent intent = null;
        if (context == null) {
            return -1;
        }
        Intent registerReceiver;
        try {
            registerReceiver = context.registerReceiver(null, new IntentFilter(z[160]));
        } catch (SecurityException e) {
            e.printStackTrace();
            registerReceiver = intent;
        } catch (Exception e2) {
            e2.printStackTrace();
            registerReceiver = intent;
        }
        if (registerReceiver == null) {
            return -1;
        }
        int intExtra = registerReceiver.getIntExtra(z[159], -1);
        Object obj = (intExtra == 2 || intExtra == 5) ? 1 : null;
        return obj != null ? registerReceiver.getIntExtra(z[161], -1) : -1;
    }

    private static int f(String str) {
        if (ai.a(str)) {
            z.d();
            return 0;
        } else if (Pattern.matches(z[LiveType.PLAY_LIVE_OTHER], str)) {
            z.c();
            return 0;
        } else if (Pattern.matches(z[111], str)) {
            z.c();
            return 1;
        } else if (!Pattern.matches(z[112], str)) {
            return 0;
        } else {
            z.c();
            return 2;
        }
    }

    private static String f() {
        String e = e();
        return e == null ? null : e + z[85];
    }

    public static boolean f(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private static String g() {
        String f = f();
        if (ai.a(f)) {
            z.e(z[20], z[106]);
            return null;
        }
        File file = new File(f);
        if (file.exists()) {
            try {
                ArrayList a = o.a(new FileInputStream(file));
                if (a.size() > 0) {
                    f = (String) a.get(0);
                    new StringBuilder(z[105]).append(f);
                    z.c();
                    return f;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String g(Context context, String str) {
        String str2;
        if (VERSION.SDK_INT >= 23) {
            str2 = "";
            try {
                Object readLine = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec(z[5]).getInputStream())).readLine();
                if (!TextUtils.isEmpty(readLine)) {
                    str2 = readLine.trim();
                    new StringBuilder(z[8]).append(str2);
                    z.b();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str2;
        } else if (!c(context, z[7])) {
            return str;
        } else {
            try {
                str2 = ((WifiManager) context.getSystemService(z[6])).getConnectionInfo().getMacAddress();
                return !ai.a(str2) ? str2 : str;
            } catch (Exception e2) {
                z.i();
                return str;
            }
        }
    }

    public static boolean g(Context context) {
        boolean z = false;
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            CertificateFactory instance = CertificateFactory.getInstance(z[16]);
            int i = 0;
            while (i < signatureArr.length) {
                boolean equals = ((X509Certificate) instance.generateCertificate(new ByteArrayInputStream(signatureArr[i].toByteArray()))).getSubjectX500Principal().equals(c);
                if (equals) {
                    return equals;
                }
                i++;
                z = equals;
            }
            return z;
        } catch (NameNotFoundException e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public static String h(Context context) {
        String i = cn.jpush.android.a.i(context);
        if (!ai.a(i) && d(i)) {
            return i;
        }
        i = w(context);
        cn.jpush.android.a.d(context, i);
        return i;
    }

    public static void h(Context context, String str) {
        Intent intent = new Intent(z[47]);
        String packageName = context.getPackageName();
        intent.setPackage(packageName);
        if (!ai.a(str)) {
            intent.putExtra(z[0], str);
        }
        intent.addCategory(z[48]);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        new StringBuilder(z[55]).append(resolveActivity.activityInfo.name);
        z.c();
        intent.setClassName(packageName, resolveActivity.activityInfo.name);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static String i(Context context) {
        return Secure.getString(context.getContentResolver(), z[14]);
    }

    public static String i(Context context, String str) {
        try {
            if (c(context, z[44])) {
                str = ((TelephonyManager) context.getSystemService(z[43])).getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String j(Context context) {
        String str = null;
        String z = cn.jpush.android.a.z();
        if (ai.a(z)) {
            z = n(context, z);
            if (ai.a(z)) {
                if (!a()) {
                    z.e();
                } else if (c(context, z[4]) && (VERSION.SDK_INT < 23 || (c(context, z[4]) && c(context, z[2])))) {
                    str = g();
                }
                if (ai.a(str)) {
                    str = VERSION.SDK_INT < 23 ? i(context, str) : "";
                    String i = i(context);
                    String g = g(context, "");
                    z = UUID.randomUUID().toString();
                    str = a(str + i + g + z);
                    if (ai.a(str)) {
                        str = z;
                    }
                    cn.jpush.android.a.g(str);
                    a = b.a - 1;
                    o(context, str);
                    m(context, str);
                    return str;
                }
                a = b.c - 1;
                o(context, str);
                cn.jpush.android.a.g(str);
                return str;
            }
            a = b.b - 1;
            m(context, z);
            cn.jpush.android.a.g(z);
            return z;
        }
        a = b.d - 1;
        return z;
    }

    public static void j(Context context, String str) {
        if (!ai.a(str)) {
            o(context, str);
            m(context, str);
            cn.jpush.android.a.g(str);
        }
    }

    public static void k(Context context) {
        try {
            WakeLock newWakeLock = ((PowerManager) context.getSystemService(z[157])).newWakeLock(1, e.c + z[158]);
            newWakeLock.setReferenceCounted(false);
            t.a().a(newWakeLock);
            if (t.a().b().isHeld()) {
                z.a();
                return;
            }
            t.a().b().acquire();
            d = System.currentTimeMillis();
            z.a();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            z.b();
        } catch (Exception e2) {
            e2.printStackTrace();
            z.b();
        }
    }

    private static boolean k(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(z[28]);
        }
        try {
            context.getPackageManager().getPermissionInfo(str, 128);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private static Intent l(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.getPackageInfo(str, 256) != null) {
                return packageManager.getLaunchIntentForPackage(str);
            }
        } catch (NameNotFoundException e) {
        }
        return null;
    }

    public static JSONArray l(Context context) {
        ArrayList a = s.a(context, true);
        JSONArray jSONArray = new JSONArray();
        try {
            Iterator it = a.iterator();
            while (it.hasNext()) {
                aa aaVar = (aa) it.next();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(z[88], aaVar.a);
                jSONObject.put(z[89], aaVar.b);
                jSONObject.put(z[90], aaVar.c);
                jSONObject.put(z[87], aaVar.d);
                jSONArray.put(jSONObject);
            }
        } catch (JSONException e) {
        }
        return jSONArray;
    }

    private static String m(Context context, String str) {
        return (a() && c(context, z[4])) ? VERSION.SDK_INT < 23 ? e(str) : (c(context, z[4]) && c(context, z[2])) ? e(str) : null : null;
    }

    public static boolean m(Context context) {
        try {
            if (cn.jpush.android.a.g(context)) {
                String f = cn.jpush.android.a.f(context);
                if (ai.a(f)) {
                    z.c();
                    return true;
                }
                new StringBuilder(z[42]).append(f);
                z.c();
                String[] split = f.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                String str = split[0];
                String str2 = split[1];
                char[] toCharArray = str.toCharArray();
                String[] split2 = str2.split(z[41]);
                Calendar instance = Calendar.getInstance();
                int i = instance.get(7);
                int i2 = instance.get(11);
                for (char valueOf : toCharArray) {
                    if (i == Integer.valueOf(String.valueOf(valueOf)).intValue() + 1) {
                        int intValue = Integer.valueOf(split2[0]).intValue();
                        int intValue2 = Integer.valueOf(split2[1]).intValue();
                        if (i2 >= intValue && i2 <= intValue2) {
                            return true;
                        }
                    }
                }
                z.c(z[20], new StringBuilder(z[40]).append(f).toString());
                return false;
            }
            z.c(z[20], z[39]);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static String n(Context context, String str) {
        if (c(context, z[73])) {
            try {
                str = System.getString(context.getContentResolver(), z[72]);
            } catch (Exception e) {
                z.e();
            }
        }
        return str;
    }

    public static boolean n(Context context) {
        Object e = cn.jpush.android.a.e(context);
        if (TextUtils.isEmpty(e)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(e);
            int optInt = jSONObject.optInt(z[103], -1);
            int optInt2 = jSONObject.optInt(z[101], -1);
            int optInt3 = jSONObject.optInt(z[99], -1);
            int optInt4 = jSONObject.optInt(z[104], -1);
            if (optInt < 0 || optInt2 < 0 || optInt3 < 0 || optInt4 < 0 || optInt2 > 59 || optInt4 > 59 || optInt3 > 23 || optInt > 23) {
                return false;
            }
            Calendar instance = Calendar.getInstance();
            int i = instance.get(11);
            int i2 = instance.get(12);
            if (optInt < optInt3) {
                if ((i <= optInt || i >= optInt3) && ((i != optInt || i2 < optInt2) && (i != optInt3 || i2 > optInt4))) {
                    return false;
                }
            } else if (optInt == optInt3) {
                if (i != optInt || i2 < optInt2) {
                    return false;
                }
                if (i2 > optInt4) {
                    return false;
                }
            } else if (optInt <= optInt3) {
                return false;
            } else {
                if ((i <= optInt || i > 23) && ((i < 0 || i >= optInt3) && (i != optInt || i2 < optInt2))) {
                    if (i != optInt3) {
                        return false;
                    }
                    if (i2 > optInt4) {
                        return false;
                    }
                }
            }
            z.c(z[20], new StringBuilder(z[102]).append(optInt).append(NetworkUtils.DELIMITER_COLON).append(optInt2).append(z[100]).append(optInt3).append(NetworkUtils.DELIMITER_COLON).append(optInt4).toString());
            return true;
        } catch (JSONException e2) {
            return false;
        }
    }

    private static String o(Context context, String str) {
        if (c(context, z[73])) {
            try {
                if (System.putString(context.getContentResolver(), z[72], str)) {
                    return str;
                }
            } catch (Exception e) {
                z.e();
            }
        }
        return null;
    }

    public static boolean o(Context context) {
        boolean z;
        boolean z2;
        z.b(z[20], z[134]);
        if (b(context, PushService.class)) {
            if (p(context, PushService.class.getCanonicalName())) {
                z.a();
                e.n = true;
            } else {
                z.a();
                e.n = false;
            }
            if (!b(context, z[145], false)) {
                z.e(z[20], z[138]);
                z = false;
            } else if (b(context, z[156], false)) {
                if (!b(context, DaemonService.class)) {
                    z.d(z[20], new StringBuilder(z[137]).append(DaemonService.class.getCanonicalName()).toString());
                    e.m = false;
                } else if (b(context, z[98], true)) {
                    e.m = true;
                } else {
                    z.d(z[20], z[150]);
                    e.m = false;
                }
                if (b(context, DownloadService.class)) {
                    if (a(context, PushReceiver.class)) {
                        if (d(context, PushReceiver.class.getCanonicalName(), z[135])) {
                            z.d(z[20], z[144]);
                        }
                        if (!a(context, z[57], true)) {
                            z.e(z[20], z[141]);
                            z = false;
                        } else if (a(context, AlarmReceiver.class)) {
                            if (context.getPackageManager().queryIntentActivities(new Intent(context, PushActivity.class), 0).isEmpty()) {
                                z.e(z[20], z[149]);
                                z = false;
                            } else {
                                z = true;
                            }
                            if (z) {
                                String str = z[26];
                                PackageManager packageManager = context.getPackageManager();
                                Intent intent = new Intent(str);
                                intent.addCategory(context.getPackageName());
                                if (!packageManager.queryIntentActivities(intent, 0).isEmpty()) {
                                    str = context.getPackageName() + z[146];
                                    if (k(context, str)) {
                                        e.add(str);
                                        Iterator it = e.iterator();
                                        while (it.hasNext()) {
                                            str = (String) it.next();
                                            if (!c(context.getApplicationContext(), str)) {
                                                z.e(z[20], new StringBuilder(z[142]).append(str).toString());
                                                z = false;
                                                break;
                                            }
                                        }
                                        if (VERSION.SDK_INT < 23) {
                                            if (!c(context.getApplicationContext(), z[4])) {
                                                z.e(z[20], z[136]);
                                                z = false;
                                            } else if (!c(context.getApplicationContext(), z[73])) {
                                                z.e(z[20], z[140]);
                                                z = false;
                                            }
                                        }
                                        it = f.iterator();
                                        while (it.hasNext()) {
                                            str = (String) it.next();
                                            if (!c(context.getApplicationContext(), str)) {
                                                new StringBuilder(z[139]).append(str);
                                                z.d();
                                            }
                                        }
                                        it = g.iterator();
                                        while (it.hasNext()) {
                                            str = (String) it.next();
                                            if (!c(context.getApplicationContext(), str)) {
                                                new StringBuilder(z[139]).append(str).append(z[153]);
                                                z.d();
                                            }
                                        }
                                    } else {
                                        z.e(z[20], new StringBuilder(z[154]).append(str).toString());
                                        z = false;
                                    }
                                } else {
                                    z.e(z[20], z[155]);
                                    z = false;
                                }
                            } else {
                                z.e(z[20], new StringBuilder(z[152]).append(PushActivity.class.getCanonicalName()).toString());
                                z = false;
                            }
                        } else {
                            z.e(z[20], new StringBuilder(z[148]).append(AlarmReceiver.class.getCanonicalName()).toString());
                            z = false;
                        }
                    } else {
                        z.e(z[20], new StringBuilder(z[148]).append(PushReceiver.class.getCanonicalName()).toString());
                        B(context);
                    }
                    z = true;
                } else {
                    z.e(z[20], new StringBuilder(z[137]).append(DownloadService.class.getCanonicalName()).toString());
                    z = false;
                }
            } else {
                z.e(z[20], z[143]);
                z = false;
            }
        } else {
            z.e(z[20], new StringBuilder(z[137]).append(PushService.class.getCanonicalName()).toString());
            z = false;
        }
        if (!ai.a(cn.jpush.android.a.A())) {
            z2 = true;
        } else if (ai.a(e.f)) {
            z.d(z[20], z[147]);
            z2 = false;
        } else {
            cn.jpush.android.a.h(e.f);
            z2 = true;
        }
        if (!(e.k || A(context))) {
            z.d(z[20], z[151]);
        }
        return z2 && z;
    }

    public static void p(Context context) {
        if (h != null && !d(context, PushReceiver.class.getCanonicalName())) {
            try {
                context.unregisterReceiver(h);
            } catch (Exception e) {
                e.getMessage();
                z.e();
            }
        }
    }

    private static boolean p(Context context, String str) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context.getPackageName(), str), 128);
            new StringBuilder(z[13]).append(serviceInfo.processName);
            z.a();
            if (serviceInfo.processName.contains(context.getPackageName() + NetworkUtils.DELIMITER_COLON)) {
                return true;
            }
        } catch (NameNotFoundException e) {
        } catch (NullPointerException e2) {
            new StringBuilder(z[12]).append(str);
            z.a();
        }
        return false;
    }

    public static String q(Context context) {
        String str = e.f;
        if (ai.a(str)) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                    str = applicationInfo.metaData.getString(z[TransportMediator.KEYCODE_MEDIA_PAUSE]);
                }
            } catch (NameNotFoundException e) {
            }
        }
        return str;
    }

    public static String r(Context context) {
        String str = "";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(z[45])).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return z[52];
            }
            if (activeNetworkInfo.getType() == 1) {
                return z[6];
            }
            if (activeNetworkInfo.getType() == 0) {
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype == 4 || subtype == 1 || subtype == 2) {
                    return z[64];
                }
                if (subtype == 3 || subtype == 8 || subtype == 6 || subtype == 5 || subtype == 12) {
                    return z[65];
                }
                if (subtype == 13) {
                    return z[63];
                }
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void s(Context context) {
        if (!cn.jpush.android.a.G()) {
            String i = i(context, "");
            String H = cn.jpush.android.a.H();
            String i2 = i(context);
            if (ai.a(i2)) {
                i2 = " ";
            }
            String g = g(context, "");
            if (ai.a(g)) {
                g = " ";
            }
            int f = f(H);
            int f2 = f(i);
            if (f == 0 || f2 == 0) {
                if (cn.jpush.android.a.b(i2, g)) {
                    return;
                }
            } else if (1 != f || 2 != f2) {
                if (2 != f || 1 != f2) {
                    if (f == f2) {
                        if (i.equals(H)) {
                            if (cn.jpush.android.a.a(i, i2)) {
                                return;
                            }
                        } else if (cn.jpush.android.a.b(i2, g)) {
                            return;
                        }
                    }
                }
                return;
            } else {
                return;
            }
            z.b();
            cn.jpush.android.a.v();
            o(context, "");
            m(context, "");
        }
    }

    public static List<ComponentName> t(Context context) {
        z.b();
        List<ComponentName> arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(z[98]);
        List queryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            return null;
        }
        for (int i = 0; i < queryIntentServices.size(); i++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServices.get(i)).serviceInfo;
            String str = serviceInfo.name;
            String str2 = serviceInfo.packageName;
            if (serviceInfo.exported && serviceInfo.enabled && !e.c.equals(str2)) {
                new StringBuilder(z[97]).append(str2).append("/").append(str).append("}");
                z.b();
                arrayList.add(new ComponentName(str2, str));
            }
        }
        return arrayList;
    }

    public static boolean u(Context context) {
        CharSequence a = s.a(context, z[131]);
        new StringBuilder(z[132]).append(a);
        z.b();
        CharSequence a2 = s.a(context, z[130]);
        if (!(TextUtils.isEmpty(a) || !z[129].equals(a) || TextUtils.isEmpty(a2))) {
            Object a3 = s.a(context, z[133]);
            if (!TextUtils.isEmpty(a3) && a3.startsWith(z[128])) {
                z.d();
                return false;
            }
        }
        return true;
    }

    private static String v(Context context) {
        String str = null;
        if (c(context, z[7])) {
            try {
                String g = g(context, "");
                if (!(g == null || g.equals(""))) {
                    new StringBuilder(z[91]).append(g);
                    z.c();
                    str = a(g + Build.MODEL);
                }
            } catch (Exception e) {
                z.i();
            }
        }
        return str;
    }

    private static String w(Context context) {
        try {
            String i = i(context, "");
            if (d(i)) {
                return i;
            }
            i = i(context);
            if (!ai.a(i) && d(i) && !z[93].equals(i.toLowerCase(Locale.getDefault()))) {
                return i;
            }
            i = v(context);
            if (!ai.a(i) && d(i)) {
                return i;
            }
            i = x(context);
            return i == null ? " " : i;
        } catch (Exception e) {
            z.i();
            return x(context);
        }
    }

    private static String x(Context context) {
        z.b();
        String string = context.getSharedPreferences(z[3], 0).getString(z[1], null);
        if (!ai.a(string)) {
            return string;
        }
        if (!a()) {
            return z(context);
        }
        string = cn.jpush.android.a.h(context);
        return string == null ? (VERSION.SDK_INT < 23 || (c(context, z[4]) && c(context, z[2]))) ? y(context) : z(context) : string;
    }

    private static String y(Context context) {
        Throwable th;
        FileOutputStream fileOutputStream = null;
        String e = e();
        e = e == null ? null : e + z[49];
        File file = !ai.a(e) ? new File(e) : null;
        if (file != null) {
            try {
                if (file.exists()) {
                    ArrayList a = o.a(new FileInputStream(file));
                    if (a.size() > 0) {
                        e = (String) a.get(0);
                        cn.jpush.android.a.c(context, e);
                        new StringBuilder(z[50]).append(e);
                        z.c();
                        return e;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        e = ai.b(UUID.nameUUIDFromBytes((System.currentTimeMillis()).getBytes()).toString());
        cn.jpush.android.a.c(context, e);
        if (file != null) {
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream2;
                try {
                    fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(e.getBytes());
                        fileOutputStream2.flush();
                        z.c();
                        if (fileOutputStream2 == null) {
                            return e;
                        }
                        try {
                            fileOutputStream2.close();
                            return e;
                        } catch (IOException e3) {
                            return e;
                        }
                    } catch (IOException e4) {
                        fileOutputStream = fileOutputStream2;
                        try {
                            z.i();
                            if (fileOutputStream != null) {
                                return e;
                            }
                            try {
                                fileOutputStream.close();
                                return e;
                            } catch (IOException e5) {
                                return e;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e6) {
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        throw th;
                    }
                } catch (IOException e7) {
                    z.i();
                    if (fileOutputStream != null) {
                        return e;
                    }
                    fileOutputStream.close();
                    return e;
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream2 = null;
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    throw th;
                }
            } catch (IOException e8) {
                z.i();
                return e;
            }
        }
        z.e();
        return e;
    }

    private static String z(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(z[3], 0);
        String string = sharedPreferences.getString(z[1], null);
        if (string != null) {
            return string;
        }
        string = UUID.randomUUID().toString();
        Editor edit = sharedPreferences.edit();
        edit.putString(z[1], string);
        edit.commit();
        return string;
    }
}
