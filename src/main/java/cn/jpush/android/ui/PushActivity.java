package cn.jpush.android.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.jpush.android.data.c;
import cn.jpush.android.data.m;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.z;
import com.letv.core.constant.LiveRoomConstant;
import java.io.File;

public class PushActivity extends Activity {
    private static final String[] z;
    private boolean a = false;
    private String b;
    private FullScreenView c = null;
    private Handler d = new f(this);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 20;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "nCBj\u0019\rEI9MACY#\u000eE\u0002E#\u0019HLXm\u000bBP\f9\u0005DQ\f=\fNIM*\b\f";
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
            case 0: goto L_0x0100;
            case 1: goto L_0x0104;
            case 2: goto L_0x0108;
            case 3: goto L_0x010c;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
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
            case 5: goto L_0x006f;
            case 6: goto L_0x0077;
            case 7: goto L_0x0080;
            case 8: goto L_0x008a;
            case 9: goto L_0x0096;
            case 10: goto L_0x00a2;
            case 11: goto L_0x00ad;
            case 12: goto L_0x00b8;
            case 13: goto L_0x00c3;
            case 14: goto L_0x00cf;
            case 15: goto L_0x00da;
            case 16: goto L_0x00e5;
            case 17: goto L_0x00f0;
            case 18: goto L_0x00fb;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "}W_%,NVE;\u0004Y[";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "yJIm\u001dLAG,\nH\u0002[$\u0019E\u0002X%\b\rEE;\bC\u0002B,\u0000H\u0002O,\u0003CMXm\u000fH\u0002J\"\u0018CF\r";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "LAX$\u0002C@M?!L[C8\u0019dF";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0056:
        r3[r2] = r1;
        r2 = 4;
        r1 = "}NI,\u001eH\u0002M)\t\rNM4\u0002XV\f?\b^MY?\u000eH\u0002F=\u0018^Js:\bOTE(\u001arNM4\u0002XV\u00025\u0000A\u0002X\"M_G_b\u0001L[C8\u0019\r\u0003";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005f:
        r3[r2] = r1;
        r2 = 5;
        r1 = "ACU\"\u0018Y";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0067:
        r3[r2] = r1;
        r2 = 6;
        r1 = "cW@!M@G_>\fJG\f(\u0003YKX4L\ra@\"\u001eH\u0002|8\u001eEcO9\u0004[KX4L";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006f:
        r3[r2] = r1;
        r2 = 7;
        r1 = "DF";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0077:
        r3[r2] = r1;
        r2 = 8;
        r1 = "GRY>\u0005rUI/\u001bDG[\u0012\u0001L[C8\u0019";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0080:
        r3[r2] = r1;
        r2 = 9;
        r1 = "KK@(W\u0002\r";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008a:
        r3[r2] = r1;
        r2 = 10;
        r1 = "}NI,\u001eH\u0002Y>\b\rFI+\fXNXm\u000eBFIm\u0004C\u0002F=\u0018^Js:\bOTE(\u001arNM4\u0002XV\u00025\u0000A\u0003";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0096:
        r3[r2] = r1;
        r2 = 11;
        r1 = "}W_%,NVE;\u0004Y[\f*\bY\u0002b\u0018!a\u0002E#\u0019HLXl";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a2:
        r3[r2] = r1;
        r2 = 12;
        r1 = "KPC 2ZCU";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ad:
        r3[r2] = r1;
        r2 = 13;
        r1 = "OMH4";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b8:
        r3[r2] = r1;
        r2 = 14;
        r1 = "hZX?\f\rFM9\f\rK_m\u0003BV\f>\b_KM!\u0004WCN!\b\f";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c3:
        r3[r2] = r1;
        r2 = 15;
        r1 = "zC^#\u0004CE＠#\u0018AN\f \b^QM*\b\rGB9\u0004Y[\rm.AM_(M}W_%,NVE;\u0004Y[\r";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cf:
        r3[r2] = r1;
        r2 = 16;
        r1 = "dLZ,\u0001DF\f \u001eJ\u0002X4\u001dH\u0002X\"M^JC:M\u0000\u0002";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00da:
        r3[r2] = r1;
        r2 = 17;
        r1 = "OC_(,NVE;\u0004Y[\fmP\r";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e5:
        r3[r2] = r1;
        r2 = 18;
        r1 = "YM\\\f\u000eYKZ$\u0019T\u0002\fpM";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00f0:
        r3[r2] = r1;
        r2 = 19;
        r1 = "LAX$\u001bDVU";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00fb:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0100:
        r9 = 45;
        goto L_0x0020;
    L_0x0104:
        r9 = 34;
        goto L_0x0020;
    L_0x0108:
        r9 = 44;
        goto L_0x0020;
    L_0x010c:
        r9 = 77;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.PushActivity.<clinit>():void");
    }

    static /* synthetic */ void a(PushActivity pushActivity, c cVar) {
        z.c();
        if (cVar == null) {
            z.d(z[1], z[6]);
            pushActivity.finish();
        }
        m mVar = (m) cVar;
        if (mVar.H == 0) {
            int identifier = pushActivity.getResources().getIdentifier(z[8], z[5], pushActivity.getPackageName());
            if (identifier == 0) {
                z.e(z[1], z[4]);
                pushActivity.finish();
                return;
            }
            pushActivity.setContentView(identifier);
            String str = mVar.a;
            if (d.a(str)) {
                String str2 = mVar.L;
                if (mVar.p) {
                    int identifier2 = pushActivity.getResources().getIdentifier(z[3], z[7], pushActivity.getPackageName());
                    if (identifier2 == 0) {
                        z.e(z[1], z[10]);
                        pushActivity.finish();
                        return;
                    }
                    pushActivity.c = (FullScreenView) pushActivity.findViewById(identifier2);
                    pushActivity.c.initModule(pushActivity, cVar);
                    if (TextUtils.isEmpty(str2) || !new File(str2.replace(z[9], "")).exists() || pushActivity.a) {
                        pushActivity.c.loadUrl(str);
                    } else {
                        pushActivity.c.loadUrl(str2);
                    }
                }
                if (!pushActivity.a) {
                    g.a(pushActivity.b, 1000, pushActivity);
                    return;
                }
                return;
            }
            cn.jpush.android.api.m.a((Context) pushActivity, cVar, 0);
            pushActivity.finish();
        }
    }

    private void c() {
        PackageManager packageManager = getPackageManager();
        String packageName = getApplicationContext().getPackageName();
        if (packageName.isEmpty()) {
            z.d(z[1], z[2]);
            return;
        }
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage == null) {
            z.d(z[1], z[0]);
            return;
        }
        launchIntentForPackage.addFlags(335544320);
        startActivity(launchIntentForPackage);
    }

    public final void a() {
        if (this.c != null) {
            this.c.showTitleBar();
        }
    }

    public final void b() {
        try {
            ActivityManager activityManager = (ActivityManager) getSystemService(z[19]);
            ComponentName componentName = ((RunningTaskInfo) activityManager.getRunningTasks(1).get(0)).baseActivity;
            ComponentName componentName2 = ((RunningTaskInfo) activityManager.getRunningTasks(1).get(0)).topActivity;
            new StringBuilder(z[17]).append(componentName.toString());
            z.b();
            new StringBuilder(z[18]).append(componentName2.toString());
            z.b();
            if (!(componentName == null || componentName2 == null || !componentName2.toString().equals(componentName.toString()))) {
                c();
            }
        } catch (Exception e) {
            c();
        }
        finish();
    }

    public void onBackPressed() {
        if (this.c == null || !this.c.webviewCanGoBack()) {
            g.a(this.b, LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID, this);
            b();
            return;
        }
        this.c.webviewGoBack();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() != null) {
            try {
                this.a = getIntent().getBooleanExtra(z[12], false);
                c cVar = (c) getIntent().getSerializableExtra(z[13]);
                if (cVar != null) {
                    this.b = cVar.c;
                    if (cVar != null) {
                        switch (cVar.o) {
                            case 0:
                                Message message = new Message();
                                message.what = 1;
                                message.obj = cVar;
                                this.d.sendMessageDelayed(message, 500);
                                return;
                            default:
                                new StringBuilder(z[16]).append(cVar.o);
                                z.d();
                                cn.jpush.android.api.m.a((Context) this, cVar, 0);
                                finish();
                                return;
                        }
                    }
                    z.d(z[1], z[6]);
                    finish();
                    return;
                }
                z.d(z[1], z[15]);
                finish();
                return;
            } catch (Exception e) {
                z.e(z[1], z[14]);
                e.printStackTrace();
                finish();
                return;
            }
        }
        z.d(z[1], z[11]);
        finish();
    }

    protected void onDestroy() {
        if (this.c != null) {
            this.c.destory();
        }
        if (this.d.hasMessages(2)) {
            this.d.removeMessages(2);
        }
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        if (this.c != null) {
            this.c.pause();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.c != null) {
            this.c.resume();
        }
    }
}
