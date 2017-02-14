package cn.jpush.android.b.a;

import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import cn.jpush.android.api.o;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;

public final class e {
    private static final String[] z;
    private Context a;
    private WindowManager b;
    private WebView c;
    private ImageButton d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "y\u00170nlN\u000b<8dY\u0006u l@\u001au'~\r\u0016;8lA\u00161b-j\u0016#+-X\u000f{`";
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
            case 0: goto L_0x006b;
            case 1: goto L_0x006e;
            case 2: goto L_0x0071;
            case 3: goto L_0x0074;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 13;
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
            case 0: goto L_0x0045;
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005e;
            case 4: goto L_0x0066;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "y\u00170nlN\u000b<8dY\u0006u l@\u001au'~\r\u0011 \"a\r\u0010'nh@\u000f!7!\r8<8h\r\n%`#";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0000Rxc \r\u000f4<l@\fut-";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "N\u0011{$}X\f=`lC\u001b'!dIQ\u0014\rYd)\u001c\u001aTr/\u0014\u001cL`";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "~\u0006&:h@>9+Y(0,[D\u001a\"\rlA\u00137/nF";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005e:
        r3[r2] = r1;
        r2 = 5;
        r1 = "L\u001c!'bC_xc \r\f!/Y>6:d[\u0016!7OT14#h\u0000Rxc \u0000Rx/nY\u0016#'yT14#h\rEu";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0066:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x006b:
        r9 = 45;
        goto L_0x0020;
    L_0x006e:
        r9 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x0020;
    L_0x0071:
        r9 = 85;
        goto L_0x0020;
    L_0x0074:
        r9 = 78;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.b.a.e.<clinit>():void");
    }

    public final void a(String str, String str2) {
        new StringBuilder(z[5]).append(str).append(z[2]).append(str2);
        z.b();
        if (ai.a(str)) {
            z.e(z[4], z[1]);
        }
        if (this.a != null) {
            try {
                Class cls = Class.forName(str);
                if (cls != null) {
                    Intent intent = new Intent(this.a, cls);
                    intent.putExtra(z[3], str2);
                    intent.setFlags(268435456);
                    this.a.startActivity(intent);
                    z.b();
                    o.a(this.b, this.c, this.d);
                }
            } catch (Exception e) {
                z.e(z[4], z[0]);
            }
        }
    }
}
