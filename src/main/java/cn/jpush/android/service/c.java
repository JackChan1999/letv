package cn.jpush.android.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.jpush.android.util.z;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;

final class c extends Handler {
    final /* synthetic */ b a;
    private d b = null;

    public c(b bVar, Looper looper, d dVar) {
        this.a = bVar;
        super(looper);
        this.b = dVar;
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.a.a) {
            z.a();
            return;
        }
        if (this.b != null) {
            this.b.a(this.a.d, this.a.e);
        }
        this.a.c.sendEmptyMessageDelayed(0, SPConstant.DELAY_BUFFER_DURATION);
    }
}
