package com.alipay.sdk.auth;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

final class a implements DownloadListener {
    final /* synthetic */ AuthActivity a;

    a(AuthActivity authActivity) {
        this.a = authActivity;
    }

    public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }
}
