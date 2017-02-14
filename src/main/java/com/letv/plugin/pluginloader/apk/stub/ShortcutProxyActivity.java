package com.letv.plugin.pluginloader.apk.stub;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import java.net.URISyntaxException;

public class ShortcutProxyActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            if (intent != null) {
                Intent forwordIntent = getForwarIntent();
                if (forwordIntent != null) {
                    forwordIntent.addFlags(268435456);
                    forwordIntent.putExtras(intent);
                    if (VERSION.SDK_INT >= 15) {
                        forwordIntent.setSelector(null);
                    }
                    if (ApkManager.getInstance().isConnected()) {
                        if (isPlugin(forwordIntent)) {
                            startActivity(forwordIntent);
                        }
                        finish();
                        return;
                    }
                    waitAndStart(forwordIntent);
                    return;
                }
                finish();
                return;
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private boolean isPlugin(Intent intent) {
        try {
            String pkg;
            if (intent.getComponent() == null || intent.getComponent().getPackageName() == null) {
                pkg = ApkManager.getInstance().resolveIntent(intent, null, 0).resolvePackageName;
            } else {
                pkg = intent.getComponent().getPackageName();
            }
            if (pkg == null || !ApkManager.getInstance().isPluginPackage(pkg)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitAndStart(final Intent forwordIntent) {
        new Thread() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r3 = this;
                r1 = com.letv.plugin.pluginloader.apk.pm.ApkManager.getInstance();	 Catch:{ Exception -> 0x001e }
                r1.waitForConnected();	 Catch:{ Exception -> 0x001e }
                r1 = com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.this;	 Catch:{ Exception -> 0x001e }
                r2 = r2;	 Catch:{ Exception -> 0x001e }
                r1 = r1.isPlugin(r2);	 Catch:{ Exception -> 0x001e }
                if (r1 == 0) goto L_0x0018;
            L_0x0011:
                r1 = com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.this;	 Catch:{ Exception -> 0x001e }
                r2 = r2;	 Catch:{ Exception -> 0x001e }
                r1.startActivity(r2);	 Catch:{ Exception -> 0x001e }
            L_0x0018:
                r1 = com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.this;
                r1.finish();
            L_0x001d:
                return;
            L_0x001e:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ all -> 0x0028 }
                r1 = com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.this;
                r1.finish();
                goto L_0x001d;
            L_0x0028:
                r1 = move-exception;
                r2 = com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.this;
                r2.finish();
                throw r1;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.apk.stub.ShortcutProxyActivity.1.run():void");
            }
        }.start();
    }

    private Intent getForwarIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                Intent forwordIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
                String intentUri = intent.getStringExtra(ApkConstant.EXTRA_TARGET_INTENT_URI);
                if (intentUri != null) {
                    try {
                        return Intent.parseUri(intentUri, 0);
                    } catch (URISyntaxException e) {
                    }
                } else if (forwordIntent != null) {
                    return forwordIntent;
                }
            } catch (Exception e2) {
            }
        }
        return null;
    }
}
