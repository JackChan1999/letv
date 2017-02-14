package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.tencent.open.a.f;

/* compiled from: ProGuard */
public abstract class b extends Dialog {
    protected a jsBridge;
    @SuppressLint({"NewApi"})
    protected final WebChromeClient mChromeClient = new WebChromeClient(this) {
        final /* synthetic */ b a;

        {
            this.a = r1;
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (consoleMessage == null) {
                return false;
            }
            f.c("WebConsole", consoleMessage.message() + " -- From  111 line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
            if (VERSION.SDK_INT > 7) {
                this.a.onConsoleMessage(consoleMessage == null ? "" : consoleMessage.message());
            }
            return true;
        }

        public void onConsoleMessage(String str, int i, String str2) {
            f.c("WebConsole", str + " -- From 222 line " + i + " of " + str2);
            if (VERSION.SDK_INT == 7) {
                this.a.onConsoleMessage(str);
            }
        }
    };

    protected abstract void onConsoleMessage(String str);

    public b(Context context) {
        super(context);
    }

    public b(Context context, int i) {
        super(context, i);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.jsBridge = new a();
    }
}
