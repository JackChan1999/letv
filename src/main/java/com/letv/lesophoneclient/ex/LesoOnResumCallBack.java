package com.letv.lesophoneclient.ex;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface LesoOnResumCallBack {
    boolean backEvent(int i, KeyEvent keyEvent);

    void clearFloat();

    boolean onHideFloat(MotionEvent motionEvent);

    void onResume_lego(Activity activity);
}
