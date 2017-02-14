package cn.jpush.android.util;

import android.content.Context;
import org.json.JSONArray;

final class ad implements Runnable {
    Context a;
    JSONArray b;

    public ad(Context context, JSONArray jSONArray) {
        this.a = context;
        this.b = jSONArray;
    }

    public final void run() {
        ac.b(this.a, this.b);
    }
}
