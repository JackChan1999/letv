package cn.jpush.android.b.a;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class c extends WebChromeClient {
    private static final String[] z;
    private final String a = z[0];
    private d b;
    private boolean c;

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
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "\f4\u001b.b1?\u0015\bi75\u001c.B)3\u0014%u";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0056;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004a;
            case 1: goto L_0x004d;
            case 2: goto L_0x0050;
            case 3: goto L_0x0053;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = r4;
    L_0x0020:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002c;
    L_0x0028:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002c:
        r7 = r2;
        r2 = r9;
    L_0x002e:
        if (r7 > r8) goto L_0x0013;
    L_0x0030:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0045;
            default: goto L_0x003c;
        };
    L_0x003c:
        r5[r3] = r2;
        r0 = "e3\u001f!d&.Q!re3\u001f?d7<\u0010(de9\u001e&q)?\u0005.m<z\u001e%!5(\u001e,s )\u0002k";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0045:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004a:
        r11 = 69;
        goto L_0x0020;
    L_0x004d:
        r11 = 90;
        goto L_0x0020;
    L_0x0050:
        r11 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0020;
    L_0x0053:
        r11 = 75;
        goto L_0x0020;
    L_0x0056:
        r8 = r1;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.b.a.c.<clinit>():void");
    }

    public c(String str, Class cls) {
        this.b = new d(str, cls);
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        jsResult.confirm();
        return true;
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        jsPromptResult.confirm(this.b.a(webView, str2));
        return true;
    }

    public void onProgressChanged(WebView webView, int i) {
        if (i <= 25) {
            this.c = false;
        } else if (!this.c) {
            webView.loadUrl(this.b.a());
            this.c = true;
            Log.d(z[0], new StringBuilder(z[1]).append(i).toString());
        }
        super.onProgressChanged(webView, i);
    }
}
