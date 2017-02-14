package cn.jpush.b.a.a;

import cn.jpush.android.util.g;
import cn.jpush.b.a.c.a;
import cn.jpush.b.a.c.b;
import com.letv.core.constant.LiveRoomConstant;
import java.nio.ByteBuffer;

public final class k extends h {
    private static final String[] z;
    long a;
    String b;
    String c;
    String d;
    String i;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "P\u000e(#xl\u0018>srp\u00194!7/K8<sgQ";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0070;
            case 1: goto L_0x0072;
            case 2: goto L_0x0075;
            case 3: goto L_0x0078;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 23;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0063;
            case 5: goto L_0x006b;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = ".K66dq\n<6-";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = ".K+2dq\u001c4!s8";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = ".K)6pK\u000fa";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\"F{";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "Y9>4~q\u001f>!Eg\u0018+<yq\u000e\u0006s:\"\u0001.:s8";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        r2 = 6;
        r1 = ".K?6ak\b>\u001as8";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x006b:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0070:
        r9 = 2;
        goto L_0x001f;
    L_0x0072:
        r9 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x001f;
    L_0x0075:
        r9 = 91;
        goto L_0x001f;
    L_0x0078:
        r9 = 83;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.k.<clinit>():void");
    }

    public k(e eVar, ByteBuffer byteBuffer) {
        super(eVar, byteBuffer);
    }

    public final long a() {
        return this.a;
    }

    protected final void b() {
        super.b();
        a(this.a);
        a(this.b);
        a(this.c);
        a(this.d);
    }

    protected final void c() {
        super.c();
        if (this.g > 0) {
            b.b(new StringBuilder(z[0]).append(this.g).append(z[1]).append(this.h).toString());
            return;
        }
        ByteBuffer byteBuffer = this.f;
        if (this.g == 0) {
            this.a = g.d(byteBuffer, this);
            this.b = a.a(byteBuffer, this);
            this.c = a.a(byteBuffer, this);
            this.d = a.a(byteBuffer, this);
        } else if (this.g == LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID) {
            this.i = a.a(byteBuffer, this);
        }
    }

    public final String g() {
        return this.b;
    }

    public final String h() {
        return this.c;
    }

    public final String i() {
        return this.d;
    }

    public final String toString() {
        return new StringBuilder(z[5]).append(this.a).append(z[2]).append(this.b).append(z[3]).append(this.c).append(z[6]).append(this.d).append(z[4]).append(super.toString()).toString();
    }
}
