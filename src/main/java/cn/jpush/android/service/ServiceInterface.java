package cn.jpush.android.service;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import cn.jpush.android.a;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.api.b;
import cn.jpush.android.api.d;
import cn.jpush.android.api.m;
import cn.jpush.android.data.c;
import cn.jpush.android.e;
import cn.jpush.android.util.z;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceInterface {
    private static boolean a = false;
    private static final String[] z;

    public class TagAliasOperator extends BroadcastReceiver {
        private static TagAliasOperator a;
        private static Object c = new Object();
        private static final String[] z;
        private ConcurrentHashMap<Long, b> b = new ConcurrentHashMap();
        private AtomicBoolean d = new AtomicBoolean(false);

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r0 = 15;
            r3 = new java.lang.String[r0];
            r2 = 0;
            r1 = "74~nlx%nh{(4dp";
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
                case 0: goto L_0x00cc;
                case 1: goto L_0x00d0;
                case 2: goto L_0x00d4;
                case 3: goto L_0x00d8;
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
                case 8: goto L_0x0088;
                case 9: goto L_0x0094;
                case 10: goto L_0x009f;
                case 11: goto L_0x00aa;
                case 12: goto L_0x00b5;
                case 13: goto L_0x00c0;
                default: goto L_0x003c;
            };
        L_0x003c:
            r3[r2] = r1;
            r2 = 1;
            r1 = "\n%unw.%d+p746y{?)e{*%r'>;!xeq,`ujr4`cel='xj=2Dn}=)`nl";
            r0 = 0;
            r3 = r4;
            goto L_0x0009;
        L_0x0044:
            r3[r2] = r1;
            r2 = 2;
            r1 = "\u000b%d}w;%_ej=2pj}=";
            r0 = 1;
            r3 = r4;
            goto L_0x0009;
        L_0x004c:
            r3[r2] = r1;
            r2 = 3;
            r1 = ";.8an-3~%6$ddw<nej=.b%J\u0019\u0007IJR\u0011\u0001ET]\u0019\fZI_\u001b\u000b";
            r0 = 2;
            r3 = r4;
            goto L_0x0009;
        L_0x0054:
            r3[r2] = r1;
            r2 = 4;
            r1 = ";.8an-3~%6$ddw<nej=.b%J\u0019\u0007IJR\u0011\u0001ETJ\u0011\rSDK\f";
            r0 = 3;
            r3 = r4;
            goto L_0x0009;
        L_0x005c:
            r3[r2] = r1;
            r2 = 5;
            r1 = "+%b_?\u0001xo_4)wx>=z";
            r0 = 4;
            r3 = r4;
            goto L_0x0009;
        L_0x0064:
            r3[r2] = r1;
            r2 = 6;
            r1 = ",!qjr1!eTm=1o";
            r0 = 5;
            r3 = r4;
            goto L_0x0009;
        L_0x006c:
            r3[r2] = r1;
            r2 = 7;
            r1 = "\u000b%bJr1!eJp<\u0014wlmx4f{75b+l1$,";
            r0 = 6;
            r3 = r4;
            goto L_0x0009;
        L_0x0074:
            r3[r2] = r1;
            r2 = 8;
            r1 = "R#wgr\u001a!u`09,jmb";
            r0 = 7;
            r3 = r4;
            goto L_0x0009;
        L_0x007d:
            r3[r2] = r1;
            r2 = 9;
            r1 = "x2o$";
            r0 = 8;
            r3 = r4;
            goto L_0x0009;
        L_0x0088:
            r3[r2] = r1;
            r2 = 10;
            r1 = "x4wl_4)wx]9,zi;+e+$";
            r0 = 9;
            r3 = r4;
            goto L_0x0009;
        L_0x0094:
            r3[r2] = r1;
            r2 = 11;
            r1 = "+\u0014wlmb";
            r0 = 10;
            r3 = r4;
            goto L_0x0009;
        L_0x009f:
            r3[r2] = r1;
            r2 = 12;
            r1 = "\u000b%bJr1!eJp<\u0014wlmx&ew+(61>=2ddl\u001b/rn$";
            r0 = 11;
            r3 = r4;
            goto L_0x0009;
        L_0x00aa:
            r3[r2] = r1;
            r2 = 13;
            r1 = ",!qjr1!eT{*2yy}7$s";
            r0 = 12;
            r3 = r4;
            goto L_0x0009;
        L_0x00b5:
            r3[r2] = r1;
            r2 = 14;
            r1 = ",!qjr1!e+}9,zi;+6bmx.cgrc`dbze";
            r0 = 13;
            r3 = r4;
            goto L_0x0009;
        L_0x00c0:
            r3[r2] = r1;
            z = r4;
            r0 = new java.lang.Object;
            r0.<init>();
            c = r0;
            return;
        L_0x00cc:
            r9 = 88;
            goto L_0x0020;
        L_0x00d0:
            r9 = 64;
            goto L_0x0020;
        L_0x00d4:
            r9 = 22;
            goto L_0x0020;
        L_0x00d8:
            r9 = 11;
            goto L_0x0020;
            */
            throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.ServiceInterface.TagAliasOperator.<clinit>():void");
        }

        private TagAliasOperator() {
        }

        private b a(long j) {
            return (b) this.b.get(Long.valueOf(j));
        }

        public static TagAliasOperator a() {
            synchronized (c) {
                if (a == null) {
                    a = new TagAliasOperator();
                }
            }
            return a;
        }

        private void b(long j) {
            this.b.remove(Long.valueOf(j));
        }

        private synchronized void b(Context context) {
            if (this.d.get() && this.b != null && this.b.isEmpty()) {
                try {
                    context.unregisterReceiver(this);
                } catch (Throwable e) {
                    z.a(z[2], z[1], e);
                } catch (Throwable e2) {
                    z.a(z[2], z[0], e2);
                }
                this.d.set(false);
            }
            z.a();
        }

        public final void a(Context context) {
            if (this.d.get()) {
                z.b();
                return;
            }
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addCategory(e.c);
                intentFilter.addAction(z[4]);
                intentFilter.addAction(z[3]);
                context.registerReceiver(this, intentFilter);
                this.d.set(true);
            } catch (Exception e) {
                new StringBuilder(z[5]).append(e.getMessage());
                z.e();
            }
        }

        public final void a(Long l, b bVar) {
            this.b.put(l, bVar);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                z.e();
                return;
            }
            long longExtra = intent.getLongExtra(z[6], -1);
            int intExtra = intent.getIntExtra(z[13], 0);
            if (longExtra == -1) {
                z.e();
                return;
            }
            new StringBuilder(z[9]).append(longExtra).append(z[10]).append(this.b.toString());
            z.e();
            if (z[4].equals(intent.getAction())) {
                new StringBuilder(z[7]).append(longExtra);
                z.a();
                b a = a(longExtra);
                if (a != null) {
                    TagAliasCallback tagAliasCallback = a.c;
                    b(longExtra);
                    if (tagAliasCallback != null) {
                        tagAliasCallback.gotResult(d.b, a.a, a.b);
                    }
                } else {
                    new StringBuilder(z[14]).append(longExtra);
                    z.d();
                }
            } else {
                new StringBuilder(z[12]).append(intExtra).append(z[9]).append(longExtra);
                z.a();
                b a2 = a(longExtra);
                if (a2 != null) {
                    TagAliasCallback tagAliasCallback2 = a2.c;
                    b(longExtra);
                    if (intExtra == 0) {
                        longExtra = System.currentTimeMillis();
                        String stringTags = JPushInterface.getStringTags(a2.b);
                        new StringBuilder(z[11]).append(stringTags).append(z[8]).append(a2.a);
                        z.a();
                        if (stringTags != null) {
                            a.j(stringTags);
                            a.d(longExtra);
                        }
                        if (a2.a != null) {
                            a.i(a2.a);
                            a.c(longExtra);
                        }
                    }
                    if (tagAliasCallback2 != null) {
                        tagAliasCallback2.gotResult(intExtra, a2.a, a2.b);
                    }
                } else {
                    new StringBuilder(z[14]).append(longExtra);
                    z.d();
                }
            }
            a().b(context);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 47;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "!H\u001f\u0007N1D,\u0012U!I,\u0016I?D";
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
            case 0: goto L_0x022a;
            case 1: goto L_0x022e;
            case 2: goto L_0x0232;
            case 3: goto L_0x0236;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 32;
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
            case 16: goto L_0x00e0;
            case 17: goto L_0x00eb;
            case 18: goto L_0x00f6;
            case 19: goto L_0x0101;
            case 20: goto L_0x010c;
            case 21: goto L_0x0117;
            case 22: goto L_0x0122;
            case 23: goto L_0x012d;
            case 24: goto L_0x0138;
            case 25: goto L_0x0144;
            case 26: goto L_0x0150;
            case 27: goto L_0x015b;
            case 28: goto L_0x0166;
            case 29: goto L_0x0171;
            case 30: goto L_0x017c;
            case 31: goto L_0x0187;
            case 32: goto L_0x0192;
            case 33: goto L_0x019d;
            case 34: goto L_0x01a8;
            case 35: goto L_0x01b3;
            case 36: goto L_0x01be;
            case 37: goto L_0x01c9;
            case 38: goto L_0x01d4;
            case 39: goto L_0x01df;
            case 40: goto L_0x01ea;
            case 41: goto L_0x01f5;
            case 42: goto L_0x0200;
            case 43: goto L_0x020c;
            case 44: goto L_0x0217;
            case 45: goto L_0x0222;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "?T\u001f\u0016I\rU\n\u0012E";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Lm\u0007m'+\u0002s<!e\u0001r";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "3Q\u0003";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Li\u001ch'";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\"T\u0000\n!U\u001c\u0012P7E";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0006I\u0016BS7S\u0005\u000bC7\u0001\u001a\u0011\u0000!U\u001c\u0012P7E_BI&\u0001\u0004\u000bL>\u0001\u0014\u000bV7\u0001\u0006\u0012\u00003M\u001fBT:DS\u0003C&H\u001c\fSrT\u001d\u0016I>\u0001\n\rUrB\u0012\u000eLrS\u0016\u0011U?D#\u0017S:\u0001\u001e\u0007T:N\u0017BT=\u0001\u0001\u0007S'L\u0016BT:DS\u0011E W\u001a\u0001E|";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0001D\u0001\u0014I1D:\fT7S\u0015\u0003C7";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "!D\u0001\u0014I1DS\u000bSrS\u0006\fN;O\u0014BA>S\u0016\u0003D+";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Lr\u0017r'-r\u0017q&1h";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "7O\u0017*O'S";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "!U\u0012\u0010T\u001fH\u001d\u0011";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "!U\u0012\u0010T\u001aN\u0006\u0010";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "7O\u0017\u0016m;O\u0000";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "!D\u0002=I6";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "X\u0001\u001c\u000eD\u0006@\u0014\u0011r7P\u0006\u0007S&u\u001a\u000fEh";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "r\u0001\u001d\u0007W3M\u001a\u0003Sh";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d5:
        r3[r2] = r1;
        r2 = 17;
        r1 = "+X\n\u001b\r\u001fl^\u0006Dri;XM?\u001b\u0000\u0011";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e0:
        r3[r2] = r1;
        r2 = 18;
        r1 = "!@\u001e\u0007\u0000 D\u0002\u0017E!USBO>E2\u000eI3RI";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00eb:
        r3[r2] = r1;
        r2 = 19;
        r1 = "X\u0001\u001c\u000eD\u0013M\u001a\u0003S\u0000D\u0002\u0017E!U'\u000bM7\u001b";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f6:
        r3[r2] = r1;
        r2 = 20;
        r1 = "3M\u001a\u0003S";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0101:
        r3[r2] = r1;
        r2 = 21;
        r1 = "!D\u0007BT3F\u0000BA<ES\u0011E&\u0001\u0012\u000eI3RS\u0015A!\u0001\u001d\u0017L>";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010c:
        r3[r2] = r1;
        r2 = 22;
        r1 = "3M\u001a\u0003Sr\r\u0007\u0003G!\u0001\u0004\u0003SrR\u0012\u000fErV\u001a\u0016HrM\u0012\u0011TrR\u0006\u0001C7R\u0000N\u00006NS\fO&\u0001\u001d\u0007E6\u0001\u0010\rN<D\u0010\u0016\u0000!D\u0001\u0014E ";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0117:
        r3[r2] = r1;
        r2 = 23;
        r1 = "X\u0001\u001c\u000eD\u0006@\u0014\u0011\u001a";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0122:
        r3[r2] = r1;
        r2 = 24;
        r1 = "&@\u0014\u0011";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012d:
        r3[r2] = r1;
        r2 = 25;
        r1 = "&@\u0014BA>H\u0012\u0011\u0000 H\u0017B\u001dr";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0138:
        r3[r2] = r1;
        r2 = 26;
        r1 = "rS\u0016\u0016\u001a";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0144:
        r3[r2] = r1;
        r2 = 27;
        r1 = "rO\u0016\u0015\u0000&@\u0014\u0011\u001a";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0150:
        r3[r2] = r1;
        r2 = 28;
        r1 = "X\u0001\u001d\u0007W\u0000D\u0002\u0017E!U'\u000bM7\u001b";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x015b:
        r3[r2] = r1;
        r2 = 29;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007La\u001eh21\u0006`41";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0166:
        r3[r2] = r1;
        r2 = 30;
        r1 = "\u001cT\u001f\u000e\u00001N\u001d\u0016E*U_BP>D\u0012\u0011ErH\u001d\u000bTrk#\u0017S:\u0000";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0171:
        r3[r2] = r1;
        r2 = 31;
        r1 = "<N\u0007\u000bF;B\u0012\u0016I=O,\u0000U;E\u001f\u0007R\rH\u0017";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017c:
        r3[r2] = r1;
        r2 = 32;
        r1 = "<N\u0007\u000bF;B\u0012\u0016I=O,\u0000U;E\u001f\u0007R";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0187:
        r3[r2] = r1;
        r2 = 33;
        r1 = " U\u0010=D7M\u0012\u001b";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0192:
        r3[r2] = r1;
        r2 = 34;
        r1 = " U\u0010";
        r0 = 33;
        r3 = r4;
        goto L_0x0009;
    L_0x019d:
        r3[r2] = r1;
        r2 = 35;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Lr\u0006b";
        r0 = 34;
        r3 = r4;
        goto L_0x0009;
    L_0x01a8:
        r3[r2] = r1;
        r2 = 36;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Ls\u0006n#2u\u0001i";
        r0 = 35;
        r3 = r4;
        goto L_0x0009;
    L_0x01b3:
        r3[r2] = r1;
        r2 = 37;
        r1 = "!D\u0001\u0014I1DS\u0003L D\u0012\u0006YrR\u0007\rP";
        r0 = 36;
        r3 = r4;
        goto L_0x0009;
    L_0x01be:
        r3[r2] = r1;
        r2 = 38;
        r1 = "<N\u0007\u000bF;B\u0012\u0016I=O,\u000fA*O\u0006\u000f";
        r0 = 37;
        r3 = r4;
        goto L_0x0009;
    L_0x01c9:
        r3[r2] = r1;
        r2 = 39;
        r1 = "!D\u0007BN=U\u001a\u0004I1@\u0007\u000bO<\u0001\u001e\u0003XrO\u0006\u000f\u0000h\u0001";
        r0 = 38;
        r3 = r4;
        goto L_0x0009;
    L_0x01d4:
        r3[r2] = r1;
        r2 = 40;
        r1 = "6D\u0010\u0010E3R\u0016,O&H\u0015\u000bC3U\u001a\rNh";
        r0 = 39;
        r3 = r4;
        goto L_0x0009;
    L_0x01df:
        r3[r2] = r1;
        r2 = 41;
        r1 = "1O]\bP'R\u001bLA<E\u0001\rI6\u000f\u001a\fT7O\u0007Lc\u001do='c\u0006h%+t\u000b~0*a\u001cf6";
        r0 = 40;
        r3 = r4;
        goto L_0x0009;
    L_0x01ea:
        r3[r2] = r1;
        r2 = 42;
        r1 = "1N\u001d\fE1U\u001a\rNR\u0007\u0003T7";
        r0 = 41;
        r3 = r4;
        goto L_0x0009;
    L_0x01f5:
        r3[r2] = r1;
        r2 = 43;
        r1 = "\"H\u0017X";
        r0 = 42;
        r3 = r4;
        goto L_0x0009;
    L_0x0200:
        r3[r2] = r1;
        r2 = 44;
        r1 = "~\u0001\u0000\u0016O\"u\n\u0012Eh";
        r0 = 43;
        r3 = r4;
        goto L_0x0009;
    L_0x020c:
        r3[r2] = r1;
        r2 = 45;
        r1 = "`\u000fBL\u0013";
        r0 = 44;
        r3 = r4;
        goto L_0x0009;
    L_0x0217:
        r3[r2] = r1;
        r2 = 46;
        r1 = "0N\u0017\u001b";
        r0 = 45;
        r3 = r4;
        goto L_0x0009;
    L_0x0222:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        return;
    L_0x022a:
        r9 = 82;
        goto L_0x0020;
    L_0x022e:
        r9 = 33;
        goto L_0x0020;
    L_0x0232:
        r9 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0020;
    L_0x0236:
        r9 = 98;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.ServiceInterface.<clinit>():void");
    }

    public static String a() {
        return z[45];
    }

    public static void a(Context context) {
        if (!e(context)) {
            try {
                Intent intent = new Intent(context, PushService.class);
                intent.setAction(z[4]);
                intent.putExtra(z[3], context.getPackageName());
                context.startService(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void a(Context context, int i) {
        if (1 == h(context)) {
            z.b(z[7], z[37]);
            return;
        }
        b(context, false);
        a.b(context, 1);
        Intent intent = new Intent(context, PushService.class);
        intent.setAction(z[36]);
        intent.putExtra(z[3], context.getPackageName());
        context.startService(intent);
    }

    public static void a(Context context, c cVar) {
        z.a();
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(z[46], cVar);
        context.startService(intent);
    }

    public static void a(Context context, Integer num, BasicPushNotificationBuilder basicPushNotificationBuilder) {
        if (context == null) {
            z.e(z[7], z[30]);
        } else if (b() || !e.n) {
            a.a(context, num, basicPushNotificationBuilder.toString());
        } else {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[2]);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 1);
            bundle.putString(z[31], num);
            bundle.putString(z[32], basicPushNotificationBuilder.toString());
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static void a(Context context, String str) {
        if (context != null && !e(context)) {
            if (b() || !e.n) {
                a.a(context, str);
                return;
            }
            Intent intent = new Intent(context, PushService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 4);
            bundle.putString(z[0], str);
            intent.setAction(z[2]);
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static void a(Context context, String str, String str2, b bVar) {
        if (!e(context)) {
            if (!(context instanceof Application)) {
                context = context.getApplicationContext();
            }
            if (e.a(context)) {
                boolean z;
                String B = a.B();
                String C = a.C();
                if (str2 == null && str == null) {
                    z.e(z[7], z[21]);
                    z = false;
                } else {
                    int i;
                    boolean z2;
                    if (str2 != null && str != null) {
                        i = 1;
                        z2 = ((B == null || str.equals(B)) && (C == null || str2.equals(C))) ? false : true;
                    } else if (str == null) {
                        i = 2;
                        z2 = (C == null || str2.equals(C)) ? false : true;
                    } else {
                        i = 3;
                        z2 = (B == null || str.equals(B)) ? false : true;
                    }
                    long D = a.D();
                    long E = a.E();
                    long currentTimeMillis = System.currentTimeMillis();
                    z = (z2 || ((i != 3 || currentTimeMillis - D < com.umeng.analytics.a.h) && ((i != 2 || currentTimeMillis - E < com.umeng.analytics.a.h) && (i != 1 || (currentTimeMillis - D < com.umeng.analytics.a.h && currentTimeMillis - E < com.umeng.analytics.a.h))))) ? z2 : true;
                    if (!z) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(z[17]);
                        z.d(z[7], new StringBuilder(z[18]).append(B).append(z[16]).append(str).append(z[23]).append(C).append(z[27]).append(str2).append(z[19]).append(simpleDateFormat.format(new Date(D))).append(z[15]).append(simpleDateFormat.format(new Date(E))).append(z[28]).append(simpleDateFormat.format(new Date(currentTimeMillis))).append(z[26]).append(z).toString());
                    }
                }
                if (z) {
                    long k = a.k();
                    if (!(bVar == null || bVar.c == null)) {
                        TagAliasOperator.a().a(Long.valueOf(k), bVar);
                    }
                    new StringBuilder(z[25]).append(k);
                    z.c();
                    TagAliasOperator.a().a(context);
                    Intent intent = new Intent(context, PushService.class);
                    intent.setAction(z[29]);
                    intent.putExtra(z[20], str);
                    intent.putExtra(z[24], str2);
                    intent.putExtra(z[14], k);
                    context.startService(intent);
                    return;
                }
                z.e(z[7], z[22]);
                if (bVar.c != null) {
                    bVar.c.gotResult(0, bVar.a, bVar.b);
                }
            }
        }
    }

    public static void a(Context context, boolean z) {
        if (context != null) {
            if (b() || !e.n) {
                a.a(context, z);
                return;
            }
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[2]);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 5);
            bundle.putBoolean(z[5], z);
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static boolean a(Context context, int i, int i2, int i3, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[12], i);
            jSONObject.put(z[11], i2);
            jSONObject.put(z[10], i3);
            jSONObject.put(z[13], i4);
            a(context, jSONObject.toString());
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static void b(Context context) {
        if (!e(context)) {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[35]);
            Bundle bundle = new Bundle();
            bundle.putString(z[34], z[34]);
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static void b(Context context, int i) {
        if (h(context) == 0) {
            z.b(z[7], z[8]);
            return;
        }
        b(context, true);
        a.b(context, 0);
        Intent intent = new Intent(context, PushService.class);
        intent.setAction(z[9]);
        intent.putExtra(z[3], context.getPackageName());
        context.startService(intent);
    }

    static void b(Context context, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context.getApplicationContext(), PushReceiver.class);
        ComponentName componentName2 = new ComponentName(context.getApplicationContext(), AlarmReceiver.class);
        if (z) {
            z.a();
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
            packageManager.setComponentEnabledSetting(componentName2, 1, 1);
            return;
        }
        z.a();
        packageManager.setComponentEnabledSetting(componentName, 2, 1);
        packageManager.setComponentEnabledSetting(componentName2, 2, 1);
    }

    public static boolean b() {
        return e.o != null;
    }

    public static void c(Context context) {
        m.a(context);
    }

    public static void c(Context context, int i) {
        if (!e(context)) {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[35]);
            Bundle bundle = new Bundle();
            bundle.putString(z[34], z[34]);
            bundle.putInt(z[33], i);
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static void d(Context context, int i) {
        if (context != null) {
            int b = a.b(context);
            if (i < b) {
                new StringBuilder(z[40]).append(b - i);
                z.a();
                m.a(context, i);
            }
            new StringBuilder(z[39]).append(i);
            z.a();
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[2]);
            Bundle bundle = new Bundle();
            bundle.putInt(z[1], 2);
            bundle.putInt(z[38], i);
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static boolean d(Context context) {
        return h(context) > 0;
    }

    public static boolean e(Context context) {
        boolean d = d(context);
        if (d) {
            z.b(z[7], z[6]);
        }
        return d;
    }

    public static void f(Context context) {
        if (!e(context)) {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[41]);
            Bundle bundle = new Bundle();
            bundle.putString(z[42], a.a.name());
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    public static void g(Context context) {
        if (!e(context)) {
            Intent intent = new Intent(context, PushService.class);
            intent.setAction(z[41]);
            Bundle bundle = new Bundle();
            bundle.putString(z[42], a.b.name());
            intent.putExtras(bundle);
            context.startService(intent);
        }
    }

    private static int h(Context context) {
        int c = a.c(context);
        new StringBuilder(z[43]).append(Process.myPid()).append(z[44]).append(c);
        z.b();
        return c;
    }
}
