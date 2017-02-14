package cn.jpush.android.b.a;

import android.webkit.WebView;
import cn.jpush.android.api.o;
import cn.jpush.android.util.z;

public class b {
    private static final String TAG;
    private static f mWebViewHelper;
    private static final String z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r1 = 0;
        r2 = "</5+c!9r\u0019h>1r\b3fr";
        r0 = -1;
    L_0x0004:
        r2 = r2.toCharArray();
        r3 = r2.length;
        r4 = 1;
        if (r3 > r4) goto L_0x004d;
    L_0x000c:
        r4 = r1;
    L_0x000d:
        r5 = r2;
        r6 = r4;
        r9 = r3;
        r3 = r2;
        r2 = r9;
    L_0x0012:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x0041;
            case 1: goto L_0x0044;
            case 2: goto L_0x0047;
            case 3: goto L_0x004a;
            default: goto L_0x0019;
        };
    L_0x0019:
        r7 = 26;
    L_0x001b:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r2 != 0) goto L_0x0027;
    L_0x0023:
        r3 = r5;
        r6 = r4;
        r4 = r2;
        goto L_0x0012;
    L_0x0027:
        r3 = r2;
        r2 = r5;
    L_0x0029:
        if (r3 > r4) goto L_0x000d;
    L_0x002b:
        r3 = new java.lang.String;
        r3.<init>(r2);
        r2 = r3.intern();
        switch(r0) {
            case 0: goto L_0x003e;
            default: goto L_0x0037;
        };
    L_0x0037:
        z = r2;
        r0 = "\u00193!\u000bP\"\u000f1\u0010j4";
        r2 = r0;
        r0 = r1;
        goto L_0x0004;
    L_0x003e:
        TAG = r2;
        return;
    L_0x0041:
        r7 = 81;
        goto L_0x001b;
    L_0x0044:
        r7 = 92;
        goto L_0x001b;
    L_0x0047:
        r7 = 82;
        goto L_0x001b;
    L_0x004a:
        r7 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x001b;
    L_0x004d:
        r4 = r1;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.b.a.b.<clinit>():void");
    }

    public static void click(WebView webView, String str, String str2, String str3) {
        if (mWebViewHelper != null) {
            mWebViewHelper.b(str, str2, str3);
        }
    }

    public static void close(WebView webView) {
        if (mWebViewHelper != null) {
            mWebViewHelper.a();
        }
    }

    public static void createShortcut(WebView webView, String str, String str2, String str3) {
        if (mWebViewHelper != null) {
            mWebViewHelper.a(str, str2, str3);
        }
    }

    public static void download(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.c(str);
        }
    }

    public static void download(WebView webView, String str, String str2) {
        if (mWebViewHelper != null) {
            mWebViewHelper.c(str, str2);
        }
    }

    public static void download(WebView webView, String str, String str2, String str3) {
        if (mWebViewHelper != null) {
            f fVar = mWebViewHelper;
            new StringBuilder(z).append(str3);
            z.a();
            fVar.c(str, str2);
        }
    }

    public static void executeMsgMessage(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.e(str);
        }
    }

    public static void setWebViewHelper(f fVar) {
        if (fVar != null) {
            mWebViewHelper = fVar;
        }
    }

    public static void showTitleBar(WebView webView) {
        if (mWebViewHelper != null) {
            mWebViewHelper.b();
        }
    }

    public static void showToast(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.d(str);
        }
    }

    public static void startActivityByIntent(WebView webView, String str, String str2) {
        if (mWebViewHelper != null) {
            mWebViewHelper.b(str, str2);
        }
    }

    public static void startActivityByName(WebView webView, String str, String str2) {
        if (mWebViewHelper != null) {
            mWebViewHelper.a(str, str2);
        }
    }

    public static void startActivityByNameWithSystemAlert(WebView webView, String str, String str2) {
        if (o.a != null) {
            o.a.a(str, str2);
        }
    }

    public static void startMainActivity(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.b(str);
        }
    }

    public static void startPushActivity(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.f(str);
        }
    }

    public static void triggerNativeAction(WebView webView, String str) {
        if (mWebViewHelper != null) {
            mWebViewHelper.a(str);
        }
    }
}
