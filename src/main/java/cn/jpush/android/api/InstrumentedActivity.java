package cn.jpush.android.api;

import android.app.Activity;

public class InstrumentedActivity extends Activity {
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }
}
