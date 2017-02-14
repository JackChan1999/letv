package cn.jpush.android.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.jpush.android.e;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.z;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.pp.utils.NetworkUtils;

public final class c extends WebViewClient {
    private static final String[] z;
    private final cn.jpush.android.data.c a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 22;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Jg@\u000f-HfW\u0014:|(\u0005";
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
            case 0: goto L_0x011b;
            case 1: goto L_0x011f;
            case 2: goto L_0x0123;
            case 3: goto L_0x0127;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 84;
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
            case 1: goto L_0x004e;
            case 2: goto L_0x0056;
            case 3: goto L_0x005f;
            case 4: goto L_0x0067;
            case 5: goto L_0x0070;
            case 6: goto L_0x0079;
            case 7: goto L_0x0082;
            case 8: goto L_0x008e;
            case 9: goto L_0x009a;
            case 10: goto L_0x00a6;
            case 11: goto L_0x00b1;
            case 12: goto L_0x00bc;
            case 13: goto L_0x00c7;
            case 14: goto L_0x00d3;
            case 15: goto L_0x00de;
            case 16: goto L_0x00ea;
            case 17: goto L_0x00f5;
            case 18: goto L_0x0100;
            case 19: goto L_0x010b;
            case 20: goto L_0x0116;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "z|A\u000f;rv\u000b\u0014:owK\tz~jQ\u000f55F`%\u0000";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "vsL\u0011 t";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "m{A\u0018;48";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0056:
        r3[r2] = r1;
        r2 = 4;
        r1 = "zgA\u0014;48";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005f:
        r3[r2] = r1;
        r2 = 5;
        r1 = "`0P\u000f89(\u0007X'9o";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0067:
        r3[r2] = r1;
        r2 = 6;
        r1 = "z|A\u000f;rv\u000b\u0014:owK\tz~jQ\u000f55Ap?\u001e^Qq";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x0070:
        r3[r2] = r1;
        r2 = 7;
        r1 = "sfQ\r";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0079:
        r3[r2] = r1;
        r2 = 8;
        r1 = "N`I]\"zgI\u0018tra\u0005G";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0082:
        r3[r2] = r1;
        r2 = 9;
        r1 = "o{Q\u00111&";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008e:
        r3[r2] = r1;
        r2 = 10;
        r1 = "z|A\u000f;rv\u000b\u0014:owK\tz~jQ\u000f55Wh<\u001dW";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x009a:
        r3[r2] = r1;
        r2 = 11;
        r1 = "{W\u00187o/";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a6:
        r3[r2] = r1;
        r2 = 12;
        r1 = "5!B\r";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00b1:
        r3[r2] = r1;
        r2 = 13;
        r1 = "$vL\u000f1xf\u0018\u001b5wa@";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00bc:
        r3[r2] = r1;
        r2 = 14;
        r1 = "N`LGt";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c7:
        r3[r2] = r1;
        r2 = 15;
        r1 = "z|A\u000f;rv\u000b\u0014:owK\tzzqQ\u0014;u<s4\u0011L";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00d3:
        r3[r2] = r1;
        r2 = 16;
        r1 = "5UN";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00de:
        r3[r2] = r1;
        r2 = 17;
        r1 = "z|A\u000f;rv\u000b\u0014:owK\tzzqQ\u0014;u<v8\u001a_";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 18;
        r1 = "=vL\u000f1xf\u0018\u001b5wa@";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 19;
        r1 = "k~D\u0014:4f@\u0005 ";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 20;
        r1 = "=qJ\u0013 ~|Q@";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 21;
        r1 = "5UI";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x011b:
        r9 = 27;
        goto L_0x0020;
    L_0x011f:
        r9 = 18;
        goto L_0x0020;
    L_0x0123:
        r9 = 37;
        goto L_0x0020;
    L_0x0127:
        r9 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.c.<clinit>():void");
    }

    public c(cn.jpush.android.data.c cVar) {
        this.a = cVar;
    }

    public final void onLoadResource(WebView webView, String str) {
        super.onLoadResource(webView, str);
    }

    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
    }

    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        Context context = webView.getContext();
        new StringBuilder(z[8]).append(str);
        z.c();
        try {
            String format = String.format(z[5], new Object[]{str});
            if (this.a.y) {
                context.startActivity(new Intent(z[15], Uri.parse(str)));
                g.a(this.a.c, VideoShotEditActivity.VIDEOSHOT_PIC_DOWNLOAD_FINISHD, format, e.e);
                return true;
            } else if (str.endsWith(z[16])) {
                r1 = new Intent(z[15]);
                r1.setDataAndType(Uri.parse(str), z[4]);
                webView.getContext().startActivity(r1);
                return true;
            } else if (str.endsWith(z[21]) || str.endsWith(z[12])) {
                r1 = new Intent(z[15]);
                r1.setDataAndType(Uri.parse(str), z[3]);
                webView.getContext().startActivity(r1);
                return true;
            } else {
                if (str.startsWith(z[7])) {
                    webView.loadUrl(str);
                    g.a(this.a.c, VideoShotEditActivity.VIDEOSHOT_PIC_DOWNLOAD_FINISHD, format, e.e);
                } else if (str != null && str.startsWith(z[2])) {
                    if (str.lastIndexOf(z[11]) < 0 && !str.startsWith(z[2])) {
                        str = str.indexOf("?") > 0 ? str + z[18] : str + z[13];
                        str.lastIndexOf(z[11]);
                    }
                    int indexOf = str.indexOf("?");
                    String substring = str.substring(0, indexOf);
                    String substring2 = str.substring(indexOf);
                    new StringBuilder(z[14]).append(substring);
                    z.a();
                    new StringBuilder(z[0]).append(substring2);
                    z.a();
                    r1 = null;
                    if (substring.startsWith(z[2])) {
                        String[] split = substring.split(NetworkUtils.DELIMITER_COLON);
                        if (split != null && split.length == 2) {
                            indexOf = substring2.indexOf(z[9]) + 6;
                            int indexOf2 = substring2.indexOf(z[20]);
                            String substring3 = substring2.substring(indexOf, indexOf2);
                            substring2 = substring2.substring(indexOf2 + 9);
                            String[] strArr = new String[]{split[1]};
                            r1 = new Intent(z[17]);
                            r1.setType(z[19]);
                            r1.putExtra(z[10], strArr);
                            r1.putExtra(z[6], substring3);
                            r1.putExtra(z[1], substring2);
                        }
                    }
                    if (r1 != null) {
                        context.startActivity(r1);
                    }
                    g.a(this.a.c, VideoShotEditActivity.VIDEOSHOT_PIC_DOWNLOAD_FINISHD, format, e.e);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            z.e();
            return true;
        }
    }
}
