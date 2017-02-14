package cn.jpush.android.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import cn.jpush.android.api.d;
import cn.jpush.android.e;
import cn.jpush.android.helpers.ConnectingHelper;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import cn.jpush.b.a.a.b;
import cn.jpush.b.a.a.g;
import cn.jpush.b.a.a.h;
import cn.jpush.b.a.a.l;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.core.constant.LiveRoomConstant;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public final class n extends HandlerThread {
    private static final String[] z;
    private Map<Long, p> a = new ConcurrentHashMap();
    private Deque<p> b = new LinkedBlockingDeque();
    private Deque<p> c = new LinkedBlockingDeque();
    private Context d;
    private Handler e;
    private Handler f;
    private boolean g = false;
    private int h = 0;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 32;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "U\u0018=exH\u0018)[gD\b3`";
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
            case 0: goto L_0x0182;
            case 1: goto L_0x0186;
            case 2: goto L_0x018a;
            case 3: goto L_0x018e;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 20;
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
            case 10: goto L_0x009e;
            case 11: goto L_0x00a9;
            case 12: goto L_0x00b5;
            case 13: goto L_0x00c0;
            case 14: goto L_0x00cb;
            case 15: goto L_0x00d6;
            case 16: goto L_0x00e1;
            case 17: goto L_0x00ec;
            case 18: goto L_0x00f7;
            case 19: goto L_0x0102;
            case 20: goto L_0x010e;
            case 21: goto L_0x0119;
            case 22: goto L_0x0124;
            case 23: goto L_0x012f;
            case 24: goto L_0x013a;
            case 25: goto L_0x0146;
            case 26: goto L_0x0151;
            case 27: goto L_0x015c;
            case 28: goto L_0x0167;
            case 29: goto L_0x0172;
            case 30: goto L_0x017d;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "U\u0018=exH\u0018)[qS\u000b5vwN\u001d?";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "H\u0014\u0005p}L\u001c5q`";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "B\u0017tndT\n2*uO\u001d(k}EW3j`D\u0017.*@`>\u0005EXh8\t[@h4\u001fKAu";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "H\u0014\u0005gyE";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "B\u0017tndT\n2*}LW;jpS\u00163`:@\u001a.m{OW\u0013IKs<\tT[o*\u001f";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "`\u001a.m{OYw${O07P}L\u001c5q`u\u0016\bawD\u0010,af\rY3iWL\u001d`";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "S\u0010>";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "`\u001a.m{OYw${O+?uaD\n.P}L\u001c5q`\u0001Tz";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\rY.lfD\u0018>Mp\u001b";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\rY.myD\u0016/p.";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "s\u001c+qqR\r3jsu\u0011(auE";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009e:
        r3[r2] = r1;
        r2 = 12;
        r1 = "`\u001a.m{OYw$gD\u0017>VqP\f?w`h\u0017.afO\u00186$9\u0001\u001a5jzD\u001a.m{OC";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a9:
        r3[r2] = r1;
        r2 = 13;
        r1 = "s\u001c+qqR\r3jsu\u0011(auEY)puS\r?`4\fY.lfD\u0018>Mp\u001b";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b5:
        r3[r2] = r1;
        r2 = 14;
        r1 = "`\u001a.m{OYw$fD\n.kfD*?j`p\f?qq\u0001TzwqO\r\u000bqqT\u001c\tmnDC";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c0:
        r3[r2] = r1;
        r2 = 15;
        r1 = "`\u001a.m{OYw${O=3wwN\u00174awU\u001c>";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 16;
        r1 = "`\u001a.m{OYw$dS\u00104pFD\b/agU\u00104cW@\u001a2a4\fY)mnDC";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d6:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\u0001Tz";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e1:
        r3[r2] = r1;
        r2 = 18;
        r1 = "`\u001a.m{OYw$dS\u00104pFD\b/agU\u00104cET\u001c/a4\fY)mnDC";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ec:
        r3[r2] = r1;
        r2 = 19;
        r1 = "\rY)mp\u001b";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f7:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\rY0q}EC";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0102:
        r3[r2] = r1;
        r2 = 21;
        r1 = "s\u001c+qqR\rztuS\u00187w4\fY9ip\u001b";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010e:
        r3[r2] = r1;
        r2 = 22;
        r1 = "I\u001c;v`C\u001c;p4\fY<huFC";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0119:
        r3[r2] = r1;
        r2 = 23;
        r1 = "`\u001a.m{OYw$gD\u0017>G{L\u0014;jpv\u0010.lXN\u001e=aph\u0017";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0124:
        r3[r2] = r1;
        r2 = 24;
        r1 = "`\u001a.m{OYw${O55csD\u001d\u0013j";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012f:
        r3[r2] = r1;
        r2 = 25;
        r1 = "`\u001a.m{OYw${O*?j`u\u00107a{T\rz)4";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013a:
        r3[r2] = r1;
        r2 = 26;
        r1 = "s\u001c.vm\u0001\r5$gD\u0017>$fD\b/agUYw$";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0146:
        r3[r2] = r1;
        r2 = 27;
        r1 = "`\u001a.m{OYw$dS\u00104pGD\u0017.UaD\f?$9\u0001\n3~q\u001b";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0151:
        r3[r2] = r1;
        r2 = 28;
        r1 = "`\u001a.m{OYw$fD\n?jps\u001c+qqR\r3jsp\f?qq\u0001Tzw}[\u001c`";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x015c:
        r3[r2] = r1;
        r2 = 29;
        r1 = "`\u001a.m{OYw$|@\u0017>hqs\u001c)t{O\n?$9\u0001\u001a5jzD\u001a.m{OC";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0167:
        r3[r2] = r1;
        r2 = 30;
        r1 = "`\u001a.m{OYw$qO\u001d\tazU-3iqN\f.$9\u0001\u000b3`.";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0172:
        r3[r2] = r1;
        r2 = 31;
        r1 = "\rY(agQ\u00164wq\u001b";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017d:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0182:
        r9 = 33;
        goto L_0x0020;
    L_0x0186:
        r9 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x0020;
    L_0x018a:
        r9 = 90;
        goto L_0x0020;
    L_0x018e:
        r9 = 4;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.n.<clinit>():void");
    }

    public n(Context context, Handler handler) {
        super(z[11]);
        this.d = context;
        this.f = handler;
        start();
        this.e = new o(getLooper(), this);
    }

    private synchronized p a(Long l) {
        p pVar;
        z.b();
        pVar = null;
        for (p pVar2 : this.c) {
            p pVar22;
            if (l.longValue() == pVar22.a.e().a().longValue()) {
                this.c.remove(pVar22);
            } else {
                pVar22 = pVar;
            }
            pVar = pVar22;
        }
        return pVar;
    }

    static /* synthetic */ void a(n nVar, long j, Object obj) {
        h hVar = (h) obj;
        new StringBuilder(z[29]).append(j).append(z[31]).append(hVar.toString());
        z.b();
        if (j != k.a.get()) {
            z.d();
        }
        Object a = hVar.e().a();
        p a2 = nVar.a((Long) a);
        if (a2 == null) {
            z.d();
        } else {
            a = a2.a.e().a();
            new StringBuilder(z[30]).append(a);
            z.b();
            nVar.e.removeMessages(7404, a);
        }
        p pVar = (p) nVar.a.get(a);
        if (pVar != null) {
            nVar.d(pVar);
        } else {
            z.d();
        }
    }

    static /* synthetic */ void a(n nVar, p pVar) {
        new StringBuilder(z[25]).append(pVar.toString());
        z.a();
        nVar.a(pVar.a.e().a());
        if (pVar.b > 0) {
            if (nVar.g) {
                new StringBuilder(z[26]).append(pVar.toString());
                z.a();
                pVar.a();
                nVar.b(pVar);
            } else {
                z.a();
                nVar.b.offerFirst(pVar);
            }
            if (pVar.c >= 2) {
                nVar.f.sendEmptyMessageDelayed(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID, SPConstant.DELAY_BUFFER_DURATION);
                return;
            }
            return;
        }
        nVar.a(pVar);
    }

    static /* synthetic */ void a(n nVar, g gVar, int i) {
        z.b(z[11], new StringBuilder(z[12]).append(k.a.get()).append(z[10]).append(i).append(z[9]).append(Thread.currentThread().getId()).toString());
        gVar.toString();
        z.a();
        Long a = gVar.e().a();
        Object obj = (gVar.d() == 100 && cn.jpush.android.helpers.h.a(((b) gVar).a().b())) ? 1 : null;
        p pVar = new p(gVar, i);
        if (obj == null) {
            nVar.a.put(a, pVar);
        }
        if (i > 10000) {
            z.a();
            nVar.e.sendMessageDelayed(Message.obtain(nVar.e, 7403, pVar.a.e().a()), (long) pVar.b);
        }
        if (k.b.get() || !nVar.g) {
            z.c();
            nVar.b.offerLast(pVar);
            return;
        }
        pVar.a();
        nVar.b(pVar);
    }

    private void a(p pVar) {
        new StringBuilder(z[8]).append(pVar.toString());
        z.b();
        int d = pVar.a.d();
        Long a = pVar.a.e().a();
        d(pVar);
        switch (d) {
            case 2:
                return;
            case 10:
                g gVar = pVar.a;
                z.b();
                Intent intent = new Intent();
                intent.addCategory(e.c);
                intent.setAction(z[3]);
                intent.putExtra(z[1], d.b);
                intent.putExtra(z[0], gVar.e().a().longValue());
                this.d.sendBroadcast(intent);
                return;
            case 100:
                d = ((b) pVar.a).a().b();
                long longValue = a.longValue();
                new StringBuilder(z[6]).append(d);
                z.b();
                switch (d) {
                    case 1:
                        cn.jpush.android.helpers.h.c(e.e);
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean(z[2], true);
                bundle.putLong(z[7], longValue);
                bundle.putInt(z[4], d);
                a.a(this.d, z[5], bundle);
                return;
            default:
                z.b();
                return;
        }
    }

    static /* synthetic */ void b(n nVar) {
        new StringBuilder(z[28]).append(nVar.b.size());
        z.b();
        nVar.c();
        nVar.d();
        while (true) {
            p pVar = (p) nVar.b.pollFirst();
            if (pVar == null) {
                return;
            }
            if (pVar.a.d() == 2) {
                nVar.b.remove(pVar);
                nVar.a.remove(pVar.a.e().a());
            } else {
                pVar.a();
                nVar.b(pVar);
            }
        }
    }

    private void b(p pVar) {
        z.b(z[11], z[23]);
        z.a(z[11], pVar.toString());
        g gVar = pVar.a;
        Long a = gVar.e().a();
        int d = gVar.d();
        long t = cn.jpush.android.a.t();
        z.b(z[11], new StringBuilder(z[21]).append(d).append(z[19]).append(this.h).append(z[20]).append(t).toString());
        switch (d) {
            case 2:
                short iMLoginFlag = ConnectingHelper.getIMLoginFlag();
                new StringBuilder(z[22]).append(iMLoginFlag);
                z.b();
                PushProtocol.HbJPush(k.a.get(), a.longValue(), this.h, t, iMLoginFlag);
                d = 0;
                break;
            case 10:
                PushProtocol.TagAlias(k.a.get(), a.longValue(), this.h, t, e.f, ((l) gVar).a());
                d = 0;
                break;
            case 100:
                gVar.d(this.h);
                gVar.b(t);
                PushProtocol.IMProtocol(k.a.get(), gVar.f(), 0);
                if (!cn.jpush.android.helpers.h.a(((b) gVar).a().b())) {
                    d = 0;
                    break;
                } else {
                    d = 1;
                    break;
                }
            default:
                d = 0;
                z.d();
                break;
        }
        if (d == 0) {
            c(pVar);
            z.b();
            this.e.sendMessageDelayed(Message.obtain(this.e, 7404, a), 9800);
            return;
        }
        z.a();
    }

    private void c() {
        int i = 0;
        int size = this.b != null ? this.b.size() : 0;
        new StringBuilder(z[18]).append(size);
        z.a();
        for (p pVar : this.b) {
            i++;
            new StringBuilder().append(i).append("/").append(size).append(z[17]).append(pVar.toString());
            z.a();
        }
    }

    private synchronized void c(p pVar) {
        Object obj;
        z.b();
        long longValue = pVar.a.e().a().longValue();
        for (p pVar2 : this.c) {
            if (pVar2.a.e().a().longValue() == longValue) {
                obj = 1;
                break;
            }
        }
        obj = null;
        if (obj == null) {
            this.c.offerLast(pVar);
            if (this.c != null) {
                new StringBuilder(z[27]).append(this.c.size());
                z.a();
            }
        }
    }

    private void d() {
        int i = 0;
        int size = this.a != null ? this.a.size() : 0;
        new StringBuilder(z[16]).append(size);
        z.a();
        for (p pVar : this.a.values()) {
            i++;
            new StringBuilder().append(i).append("/").append(size).append(z[17]).append(pVar.toString());
            z.a();
        }
    }

    private void d(p pVar) {
        z.a();
        Long a = pVar.a.e().a();
        if (((p) this.a.remove(a)) == null) {
            z.d();
        }
        this.b.remove(pVar);
        this.e.removeMessages(7403, a);
    }

    public final void a() {
        z.b(z[11], z[24]);
        this.g = true;
        this.h = cn.jpush.android.a.i();
        this.e.sendEmptyMessage(7405);
    }

    public final void a(long j, Object obj) {
        ConnectingHelper.sendConnectionToHandler(Message.obtain(this.e, 7402, obj), j);
    }

    public final void a(g gVar, int i) {
        Message.obtain(this.e, 7401, i, 0, gVar).sendToTarget();
    }

    public final void b() {
        z.a(z[11], z[15]);
        k.b.set(false);
        this.h = 0;
        this.g = false;
        new StringBuilder(z[14]).append(this.c.size());
        z.b();
        this.e.removeMessages(7404);
        while (true) {
            p pVar = (p) this.c.pollLast();
            if (pVar != null) {
                this.b.offerFirst(pVar);
            } else {
                c();
                d();
                return;
            }
        }
    }

    public final void run() {
        new StringBuilder(z[13]).append(Thread.currentThread().getId());
        z.b();
        super.run();
    }
}
