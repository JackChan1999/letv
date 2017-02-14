package cn.jpush.android.ui;

import android.os.Handler;
import android.os.Message;
import cn.jpush.android.data.c;

final class f extends Handler {
    final /* synthetic */ PushActivity a;

    f(PushActivity pushActivity) {
        this.a = pushActivity;
    }

    public final void handleMessage(Message message) {
        c cVar = (c) message.obj;
        switch (message.what) {
            case 1:
                this.a.setRequestedOrientation(1);
                PushActivity.a(this.a, cVar);
                return;
            case 2:
                this.a.b();
                return;
            default:
                return;
        }
    }
}
