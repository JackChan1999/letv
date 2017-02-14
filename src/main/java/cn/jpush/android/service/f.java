package cn.jpush.android.service;

import android.os.Handler;
import android.os.Message;
import cn.jpush.android.api.m;

final class f extends Handler {
    final /* synthetic */ DownloadService a;

    f(DownloadService downloadService) {
        this.a = downloadService;
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        m.b(this.a, message.what);
    }
}
