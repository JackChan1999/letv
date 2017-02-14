package com.alipay.sdk.auth;

final class d implements Runnable {
    final /* synthetic */ AuthActivity a;

    d(AuthActivity authActivity) {
        this.a = authActivity;
    }

    public final void run() {
        AuthActivity.g(this.a);
    }
}
