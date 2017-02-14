package cn.jpush.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import cn.jpush.android.b.a.b;
import cn.jpush.android.b.a.f;
import cn.jpush.android.data.c;
import cn.jpush.android.data.m;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import com.letv.core.constant.LiveRoomConstant;
import java.io.File;

public class PopWinActivity extends Activity {
    public static f a = null;
    private static final String[] z;
    private String b;
    private WebView c;
    private c d = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 17;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "`%s\r\u0017q6e";
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
            case 0: goto L_0x00df;
            case 1: goto L_0x00e2;
            case 2: goto L_0x00e6;
            case 3: goto L_0x00ea;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 72;
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
            case 3: goto L_0x005d;
            case 4: goto L_0x0065;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x007f;
            case 8: goto L_0x0089;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00aa;
            case 12: goto L_0x00b5;
            case 13: goto L_0x00c0;
            case 14: goto L_0x00cc;
            case 15: goto L_0x00d7;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "d8x\u0019";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "V\"o\b\te#u\u0016!r.<\u0007-rwR5\u0004Jwu\u000e<c9hA";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "l'i\u0013 Y's\u0010?o9C\f)8i\u0014";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "q!L\u000f8q>r";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "C/h\u0012)&3}\u0014)&>o@&i#<\u0013-t>}\f!|6~\f-'";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        r2 = 6;
        r1 = "u?s\u0017\u001dt;<]h";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "j6e\u000f=r";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "V;y\u0001;cwi\u0013-&3y\u0006)s;h@+i3y@!hwv\u0010=u?C\u0010'v u\u000e\u0017j6e\u000f=ryd\r$'";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "L\u0007i\u0013 Q2~";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "Q6n\u000e!h0０\u000e=j;<\r-u$}\u0007-&2r\u0014!r.=@\u000bj8o\u0005hV\"o\b\te#u\u0016!r.=";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "V;y\u0001;cw}\u0004,&;}\u0019's#<\u0012-u8i\u0012+cwv\u0010=u?C\u0010'v u\u000e\u0017j6e\u000f=ryd\r$&#s@:c$3\f)8i\u0014h'";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "E6r@&i#<\u0007-rwk\u0005*P>y\u0017ho9<\f)8i\u0014h`>p\u0005i";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00aa:
        r3[r2] = r1;
        r2 = 13;
        r1 = "`>p\u0005r)x";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b5:
        r3[r2] = r1;
        r2 = 14;
        r1 = "o3";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c0:
        r3[r2] = r1;
        r2 = 15;
        r1 = "u2}\u0012+n\u0015s\u0018\u0002g!}\":o3{\u0005\u0017";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cc:
        r3[r2] = r1;
        r2 = 16;
        r1 = "V\"o\b\te#u\u0016!r.";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        return;
    L_0x00df:
        r9 = 6;
        goto L_0x0020;
    L_0x00e2:
        r9 = 87;
        goto L_0x0020;
    L_0x00e6:
        r9 = 28;
        goto L_0x0020;
    L_0x00ea:
        r9 = 96;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.PopWinActivity.<clinit>():void");
    }

    public final void a(String str) {
        if (this.d != null && this.c != null && (this.d instanceof m)) {
            if (!ai.a(str)) {
                ((m) this.d).a = str;
                Intent intent = new Intent(this, PushActivity.class);
                intent.putExtra(z[1], this.d);
                intent.putExtra(z[0], true);
                startActivity(intent);
            }
            finish();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        g.a(this.b, LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID, this);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() != null) {
            try {
                this.d = (c) getIntent().getSerializableExtra(z[1]);
                if (this.d != null) {
                    this.b = this.d.c;
                    int identifier = getResources().getIdentifier(z[3], z[7], getPackageName());
                    if (identifier == 0) {
                        z.e(z[16], z[11]);
                        finish();
                    } else {
                        setContentView(identifier);
                        identifier = getResources().getIdentifier(z[4], z[14], getPackageName());
                        if (identifier == 0) {
                            z.e(z[16], z[8]);
                            finish();
                        } else {
                            this.c = (WebView) findViewById(identifier);
                            if (this.c == null) {
                                z.e(z[16], z[12]);
                                finish();
                            } else {
                                this.c.setScrollbarFadingEnabled(true);
                                this.c.setScrollBarStyle(33554432);
                                WebSettings settings = this.c.getSettings();
                                settings.setDomStorageEnabled(true);
                                a.a(settings);
                                this.c.setBackgroundColor(0);
                                a = new f(this, this.d);
                                this.c.removeJavascriptInterface(z[15]);
                                this.c.setWebChromeClient(new cn.jpush.android.b.a.a(z[9], b.class));
                                this.c.setWebViewClient(new c(this.d));
                                b.setWebViewHelper(a);
                            }
                        }
                    }
                    m mVar = (m) this.d;
                    String str = mVar.L;
                    String str2 = mVar.a;
                    new StringBuilder(z[6]).append(str2);
                    z.b();
                    if (TextUtils.isEmpty(str) || !new File(str.replace(z[13], "")).exists()) {
                        this.c.loadUrl(str2);
                    } else {
                        this.c.loadUrl(str);
                    }
                    g.a(this.b, 1000, this);
                    return;
                }
                z.d(z[16], z[10]);
                finish();
                return;
            } catch (Exception e) {
                z.e(z[16], z[5]);
                e.printStackTrace();
                finish();
                return;
            }
        }
        z.d(z[16], z[2]);
        finish();
    }

    protected void onDestroy() {
        if (this.c != null) {
            this.c.removeAllViews();
            this.c.destroy();
            this.c = null;
        }
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        if (this.c != null) {
            this.c.onPause();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.c != null) {
            this.c.onResume();
            b.setWebViewHelper(a);
        }
    }
}
