package cn.jpush.android.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

final class a implements OnClickListener {
    final /* synthetic */ FullScreenView a;

    a(FullScreenView fullScreenView) {
        this.a = fullScreenView;
    }

    public final void onClick(View view) {
        if (this.a.mContext != null) {
            ((Activity) this.a.mContext).onBackPressed();
        }
    }
}
