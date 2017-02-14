package cn.jpush.android.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.b.a.b;
import cn.jpush.android.b.a.f;
import cn.jpush.android.data.c;
import cn.jpush.android.data.m;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;

public class FullScreenView extends LinearLayout {
    private static final String TAG;
    public static f webViewHelper = null;
    private static final String[] z;
    private OnClickListener btnBackClickListener = new a(this);
    private ImageButton imgBtnBack;
    private final Context mContext;
    private WebView mWebView;
    private RelativeLayout rlTitleBar;
    private TextView tvTitle;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 9;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "w\u001a\b|9R\u001d\u0001u\u0004g\u0006\u0001g";
        r0 = 8;
        r4 = r3;
    L_0x000b:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x0036;
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
            case 0: goto L_0x008e;
            case 1: goto L_0x0091;
            case 2: goto L_0x0094;
            case 3: goto L_0x0097;
            default: goto L_0x0020;
        };
    L_0x0020:
        r9 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
    L_0x0022:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x0034;
    L_0x002a:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0019;
    L_0x002e:
        TAG = r1;
        r1 = "]\u0000\u0005t?C\u0003^";
        r0 = -1;
        goto L_0x000b;
    L_0x0034:
        r5 = r1;
        r1 = r7;
    L_0x0036:
        if (r5 > r6) goto L_0x0014;
    L_0x0038:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x004c;
            case 1: goto L_0x0054;
            case 2: goto L_0x005c;
            case 3: goto L_0x0065;
            case 4: goto L_0x006d;
            case 5: goto L_0x0075;
            case 6: goto L_0x007d;
            case 7: goto L_0x0086;
            case 8: goto L_0x002e;
            default: goto L_0x0044;
        };
    L_0x0044:
        r3[r2] = r1;
        r2 = 1;
        r1 = "X\u0002\u0003B\u0003R\u0007\u0014e\u0019Y-\u0010~(P\f\u000f";
        r0 = 0;
        r3 = r4;
        goto L_0x000b;
    L_0x004c:
        r3[r2] = r1;
        r2 = 2;
        r1 = "X\u000b";
        r0 = 1;
        r3 = r4;
        goto L_0x000b;
    L_0x0054:
        r3[r2] = r1;
        r2 = 3;
        r1 = "W\u001a\b|=T\r2y\u000fF";
        r0 = 2;
        r3 = r4;
        goto L_0x000b;
    L_0x005c:
        r3[r2] = r1;
        r2 = 4;
        r1 = "{?\u0011c\u0002f\n\u0006";
        r0 = 3;
        r3 = r4;
        goto L_0x000b;
    L_0x0065:
        r3[r2] = r1;
        r2 = 5;
        r1 = "C\u00036y\tY\u001f\u0011c\u0002e\u0006\u0010|\u000fs\u000e\u0016";
        r0 = 4;
        r3 = r4;
        goto L_0x000b;
    L_0x006d:
        r3[r2] = r1;
        r2 = 6;
        r1 = "B\n\u0005b\tY-\u000bh P\u0019\u0005R\u0018X\u000b\u0003u5";
        r0 = 5;
        r3 = r4;
        goto L_0x000b;
    L_0x0075:
        r3[r2] = r1;
        r2 = 7;
        r1 = "a\u0003\u0001q\u0019TO\u0011c\u000f\u0011\u000b\u0001v\u000bD\u0003\u00100\t^\u000b\u00010\u0003_O\u000e`\u001fB\u0007;g\u000fS\u0019\ru\u001dn\u0003\u0005i\u0005D\u001bJh\u0007]N";
        r0 = 6;
        r3 = r4;
        goto L_0x000b;
    L_0x007d:
        r3[r2] = r1;
        r2 = 8;
        r1 = "E\u00196y\tY\u001f\u0011c\u0002e\u0006\u0010|\u000f";
        r0 = 7;
        r3 = r4;
        goto L_0x000b;
    L_0x0086:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        webViewHelper = r0;
        return;
    L_0x008e:
        r9 = 49;
        goto L_0x0022;
    L_0x0091:
        r9 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        goto L_0x0022;
    L_0x0094:
        r9 = 100;
        goto L_0x0022;
    L_0x0097:
        r9 = 16;
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.FullScreenView.<clinit>():void");
    }

    public FullScreenView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    private void quitFullScreen() {
        try {
            LayoutParams attributes = ((Activity) this.mContext).getWindow().getAttributes();
            attributes.flags &= -1025;
            ((Activity) this.mContext).getWindow().setAttributes(attributes);
            ((Activity) this.mContext).getWindow().clearFlags(512);
        } catch (Exception e) {
            z.d();
        }
    }

    public void destory() {
        removeAllViews();
        if (this.mWebView != null) {
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
    }

    public void initModule(Context context, c cVar) {
        m mVar = (m) cVar;
        CharSequence charSequence = mVar.E;
        setFocusable(true);
        this.mWebView = (WebView) findViewById(getResources().getIdentifier(z[3], z[2], context.getPackageName()));
        this.rlTitleBar = (RelativeLayout) findViewById(getResources().getIdentifier(z[5], z[2], context.getPackageName()));
        this.tvTitle = (TextView) findViewById(getResources().getIdentifier(z[8], z[2], context.getPackageName()));
        this.imgBtnBack = (ImageButton) findViewById(getResources().getIdentifier(z[1], z[2], context.getPackageName()));
        if (this.mWebView == null || this.rlTitleBar == null || this.tvTitle == null || this.imgBtnBack == null) {
            z.e(TAG, z[7]);
            ((Activity) this.mContext).finish();
        }
        if (1 == mVar.G) {
            this.rlTitleBar.setVisibility(8);
            ((Activity) context).getWindow().setFlags(1024, 1024);
        } else {
            this.tvTitle.setText(charSequence);
            this.imgBtnBack.setOnClickListener(this.btnBackClickListener);
        }
        this.mWebView.setScrollbarFadingEnabled(true);
        this.mWebView.setScrollBarStyle(33554432);
        WebSettings settings = this.mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        a.a(settings);
        webViewHelper = new f(context, cVar);
        this.mWebView.removeJavascriptInterface(z[6]);
        this.mWebView.setWebChromeClient(new cn.jpush.android.b.a.a(z[4], b.class));
        this.mWebView.setWebViewClient(new c(cVar));
        b.setWebViewHelper(webViewHelper);
    }

    public void loadUrl(String str) {
        if (this.mWebView != null) {
            new StringBuilder(z[0]).append(str);
            z.b();
            this.mWebView.loadUrl(str);
        }
    }

    public void pause() {
        if (this.mWebView != null) {
            this.mWebView.onPause();
        }
    }

    public void resume() {
        if (this.mWebView != null) {
            this.mWebView.onResume();
            b.setWebViewHelper(webViewHelper);
        }
    }

    public void showTitleBar() {
        if (this.rlTitleBar != null && this.rlTitleBar.getVisibility() == 8) {
            this.rlTitleBar.setVisibility(0);
            quitFullScreen();
            this.imgBtnBack.setOnClickListener(this.btnBackClickListener);
            if (this.mWebView != null) {
                this.mWebView.postDelayed(new b(this), 1000);
            }
        }
    }

    public boolean webviewCanGoBack() {
        return this.mWebView != null ? this.mWebView.canGoBack() : false;
    }

    public void webviewGoBack() {
        if (this.mWebView != null) {
            this.mWebView.goBack();
        }
    }
}
