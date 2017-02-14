package com.letv.android.client.live.event;

import android.content.Intent;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveEvent {

    public static class OnActivityResultEvent {
        public Intent intent;
        public int requestCode;
        public int resultCode;

        public OnActivityResultEvent(int requestCode, int resultCode, Intent intent) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.intent = intent;
        }
    }

    public LiveEvent() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }
}
